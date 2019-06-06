package es.aragon.midas.process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

/**
 * Clase que implementa la tarea de un proceso correspondiente a un flujo BPMN
 * @author  Susana Deza
 * @version  1.0
 */
public class Tarea {
    /**
    * Estados de la tarea
    */	
	public enum EstadoTarea { 
		/** Estado inicial de la tarea cuando se acaba de crear. */ 		CREADA,	       
		/** Tarea creada pero no asignada a usuario. */ 					SIN_ASIGNAR,   
		/** Tarea asignada a un usuario para su ejecucion en el flujo. */	ASIGNADA,      
		/** Tarea con la ejecucion suspendida. */ 							SUSPENDIDA,    
		/** Tarea completada, fuera de la ejecucion del flujo. */ 			COMPLETADA,    
		/** Tarea eliminada de la ejecucion. */ 							ELIMINADA      
	}
	
	private Proceso proceso;
	private Task task;
	private EstadoTarea estado; 

	// CONSTRUCTOR
    /**
    * Constructor para la tarea
    * @param proceso Proceso en el que se circunscribe la tarea
    * @param task Tarea
    * @see  <a href="http://activiti.org/javadocs/org/activiti/engine/task/Task.html">org.activiti.engine.task.Task</a>
    */	
	public Tarea(Proceso proceso, Task task) {
		this.setProceso(proceso);
		this.setTask(task);
		this.setEstado(Tarea.EstadoTarea.CREADA);
		// Actualizar estado con los valores propios del task, si procede   ???
		// Asi se evitaria tener que refrescar en el motor (getBandejaEntrada) y proceso (getTareasActivas) o en otros sitios donde se 
		// recuperen objetos Task y se quieran convertir a objetos Tarea
		this.refrescaEstado();
	}
	
	// GETTERS Y SETTERS
	public Proceso getProceso(){
		return this.proceso;
	}
	
	public void setTask(Task t){
		this.task = t;
	}

	public Task getTask(){
		return this.task;
	}
	
	public void setProceso(Proceso p){
		this.proceso = p;
	}

	public void setEstado(EstadoTarea e){
		this.estado = e;
	}
	
	public EstadoTarea getEstado(){
		return this.estado;
	}
	
	// MeTODOS
	
    /**
    * Refresca el estado de la tarea, comprobando la asignacion a usuario, 
    * si esta o no suspendida o si ha sido eliminada, bien por completarse o bien por borrado
    * @return Estado de la tarea
    * @see EstadoTarea
    */		
	public EstadoTarea refrescaEstado (){
		String asignado = null;
		String eliminado = null;
		
		// Obtener el id de la tarea
		String idTarea = this.task.getId();		
		
		// Consultar la tarea al TaskService
		List<Task> tareas = this.proceso.getMotor().getTaskService().createTaskQuery().taskId(idTarea).list();	
		if (tareas.size()==1) {
			for (Task t : tareas) {		
				asignado = t.getAssignee();
			}
		}
		else{
			asignado = null;
			// Obtener el id de la ejecucion de la tarea
			String idExeTarea = this.task.getExecutionId();	
			
		    // La tarea ha podido ser eliminada del TaskService, consultar la razon al historico
			List<HistoricTaskInstance> tareasHco = this.proceso.getMotor().getHistoryService()
																		  .createHistoricTaskInstanceQuery()																		  
																		  .executionId(idExeTarea)	
																		  .finished()
																		  .list();					
			if (tareasHco.size()>0) {
				for (HistoricTaskInstance t : tareasHco) {		
					if (t.getId().equals(idTarea)){
						eliminado = t.getDeleteReason();
					}
				}
			}		
		}		
		
		// Evaluar los posibles estados		
		if (this.task.isSuspended()){
			this.estado = Tarea.EstadoTarea.SUSPENDIDA;
		}
		if (asignado!=null){
			this.estado = Tarea.EstadoTarea.ASIGNADA;
		}		
		if (eliminado!=null){
			if (eliminado.equals("completed")){
				this.estado = Tarea.EstadoTarea.COMPLETADA;
			}
			if (eliminado.equals("deleted")){
				this.estado = Tarea.EstadoTarea.ELIMINADA;
			}			
		}
				
		//System.out.println("Proceso "+this.proceso.getProcessInstance().getProcessInstanceId()+" - Tarea "+this.task.getName()+": Estado "+this.estado);
		return this.estado;
	}
	
    /**
    * Devuelve la lista de grupos candidatos de una tarea
    * @return Lista con los identificadores de los grupos
    */
	public List<String> getGruposTarea() {	
		List<String> grupos = new ArrayList<String>();
		
		
		ProcessDefinition processDefinition = this.getProceso()
												  .getMotor()
												  .getRepositoryService()
												  .getProcessDefinition(this.proceso.getProcessInstance().getProcessDefinitionId());
		String taskDefinitionKey = this.task.getTaskDefinitionKey();
		TaskDefinition taskDefinition = ((ProcessDefinitionEntity) processDefinition).getTaskDefinitions().get(taskDefinitionKey);
		
		Set<Expression> candidateUserIdExpressions = taskDefinition.getCandidateGroupIdExpressions();		       
        for (org.activiti.engine.delegate.Expression expression : candidateUserIdExpressions) {
        	grupos.add(expression.getExpressionText());       
        }		
        
		return grupos;
	}
	    
    /**
    * Asigna una tarea a un usuario. Si el usuario no pertenece 
    * a alguno de los grupos candidatos establecidos en la tarea, lanza una excepcion
    * @param usuario Identificador del usuario al que se asignara la tarea
    * @throws Exception
    */
	public void asignar(String usuario) throws Exception{				
		//Obtener los grupos candidatos de la tarea
		List <String> grupos = this.getGruposTarea();
		Boolean usuarioValido = false;
		
		// Para los grupos candidatos, comprobar si el usuario pertenece a alguno
		for (String grupo : grupos) {	
			usuarioValido = this.proceso.getMotor().esUsuarioGrupo(usuario, grupo);
			if (usuarioValido) {
				break;
			}
		}
		
		// Asignar la tarea
		if (!usuarioValido){
			try{
				throw new Exception("ERROR: el usuario "+usuario+" no pertenece a los grupos candidatos de la tarea.");
			}
			catch (Exception e){
				throw e;
			}
		}
		else{
			this.proceso.getMotor().getTaskService().setOwner(this.task.getId(), usuario); 
			this.proceso.getMotor().getTaskService().claim(this.task.getId(), usuario);		
			this.refrescaEstado();
		}		
	}	
	
    /**
    *  Desasigna la tarea
    */
	public void desasignar (){
		this.proceso.getMotor().getTaskService().unclaim(this.task.getId());
		this.refrescaEstado();
	}
	
    /**
    *  Completa la tarea
    *  @param variables Variables pasadas a la tarea; puede ser null
    */
	public void completar (Map<String,Object> variables){
		this.proceso.getMotor().getTaskService().complete(this.task.getId(), variables);
		this.refrescaEstado();
	}
	
	/**
	 * Adjunta documento a la tarea 
	 * @param nombreDoc Nombre del documento
	 * @param tipoDoc Tipo del documento
	 * @param descripcionDoc Descripcion del adjunto
	 * @param contenidoDoc Contenido del documento
	 * @return Identificador del adjunto 
	 */		
	public String adjuntarDocProcesoTarea(String nombreDoc, 
										  String tipoDoc, 
							  			  String descripcionDoc,
										  InputStream contenidoDoc) {		
		Attachment adjunto = this.getProceso().getMotor().getTaskService()
														 .createAttachment(tipoDoc, 
																		   this.task.getId(),    //adjuntar a la tarea
																		   null,       			  //adjuntar al proceso
																		   nombreDoc, 
																		   descripcionDoc, 
																		   contenidoDoc
																		  );										
		return adjunto.getId();
	}	

	/**
	 * Recupera un documento adjunto
	 * @param idAdjunto Identificador del adjunto a recuperar
	 * @return Contenido del adjunto 
	 */	
	public InputStream getContenidoAdjunto(String idAdjunto) {						
		return(this.proceso.getMotor().getTaskService().getAttachmentContent(idAdjunto));
	}
	
	/**
	 * Elimina un documento adjunto
	 * @param idAdjunto Identificador del adjunto a eliminar
	 */		
	public void borrarAdjunto(String idAdjunto) {						
		this.proceso.getMotor().getTaskService().deleteAttachment(idAdjunto);
	}	
	
	/**
	 * Recupera informacion de un documento adjunto
	 * @param idAdjunto Identificador del adjunto a eliminar
	 * @param info Informacion a obtener del adjunto: ATTACHMENT_TYPE (tipo), 
	 * 												  ATTACHMENT_PROCESS_INSTANCE_ID (identificador del proceso al que se adjunta), 
	 * 												  ATTACHMENT_TASK_ID (identificador de la tarea a la que se adjunta), 
	 * 												  ATTACHMENT_DESCRIPTION (descripcion del adjunto),
	 * 												  ATTACHMENT_NAME (nombre del adjunto)  
	 * @return Informacion solicitada por info
	 */
	public String getInfoAdjunto (String idAdjunto, String info){		
		String retorno=null;
		Attachment adjunto = this.proceso.getMotor().getTaskService().getAttachment(idAdjunto);
		
		if (info.equals(this.proceso.getMotor().ATTACHMENT_TYPE)) {
			retorno = adjunto.getType();
		}
		else if (info.equals(this.proceso.getMotor().ATTACHMENT_PROCESS_INSTANCE_ID)) {
			retorno = adjunto.getProcessInstanceId();
		}	
		else if (info.equals(this.proceso.getMotor().ATTACHMENT_TASK_ID)) {
			retorno = adjunto.getTaskId();
		}		
		else if (info.equals(this.proceso.getMotor().ATTACHMENT_NAME)) {
			retorno = adjunto.getName();
		}
		else if (info.equals(this.proceso.getMotor().ATTACHMENT_DESCRIPTION)) {
			retorno = adjunto.getDescription();
		}       
		
		return (retorno);
	}
	
}
