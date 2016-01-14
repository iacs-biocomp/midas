package es.aragon.midas.process;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

/**
 * Clase que implementa el proceso correspondiente a un flujo BPMN 2.0
 * @author  Susana Deza
 * @version  1.0
 */
public class Proceso {
	private Motor motor;
	private ProcessInstance processInstance;

	
    /**
    * Constructor para el proceso (instancia de proceso)
    * @param motor Define el motor de Activiti donde correrá el proceso
    * @param instancia Instancia del proceso BPMN que se representara con el objeto proceso
    * @see <a href="http://activiti.org/javadocs/org/activiti/engine/runtime/ProcessInstance.html">org.activiti.engine.runtime.ProcessInstance</a>
    */
	public Proceso(Motor motor, ProcessInstance instancia) {
		this.setMotor(motor);
		this.setProcessInstance(instancia);
	}
	
	public Motor getMotor(){
		return this.motor;
	}
	
	public void setMotor(Motor m){
		this.motor = m;
	}
	
	public ProcessInstance getProcessInstance(){
		return this.processInstance;
	}
	
	public void setProcessInstance(ProcessInstance p){
		this.processInstance = p;
	}
	
		
    /**
    * Devuelve la lista de nodos activos de la instancia de proceso
    * @return Lista de identificadores correspondientes a los nodos activos
    */
	public List<String> getListaIdNodosActivos(){				
		return(this.motor.getRuntimeService().getActiveActivityIds(this.processInstance.getProcessInstanceId()));
	}
	
    /**
    * Devuelve la información solicitada de un nodo de la instancia de proceso
    * @param nodo Identificador del nodo
    * @param info Información a obtener del nodo: NODE_NAME (nombre), CODE_TYPE (tipo), NODE_DESC (descripción)
    * @return Información solicitada por info
    */
	public String getInfoNodo(String nodo, String info) {		
		PvmActivity targetActivity = null;
		String retInfo = null;
		
        ReadOnlyProcessDefinition procDef = ((RepositoryServiceImpl) this.motor.getRepositoryService())
        										.getDeployedProcessDefinition(this.processInstance.getProcessDefinitionId());
        targetActivity = procDef.findActivity(nodo);	    
		if(targetActivity != null)
		{		
			retInfo = (String) targetActivity.getProperty(info);
		}
			    		
		return retInfo;
	}	
	
    /**
    * Devuelve la lista de tareas de usuario correspondientes a una lista de nodos de la instancia de proceso
    * @param listaNodos Lista de identificadores de los nodos 
    * @return Lista de tareas de activiti (org.activiti.engine.task) correspondientes a los nodos pasados por parámetro
    */
	// Devolver la lista de tareas de usuario correspondients a una lista de nodos de un proceso
	public List<Task> getUserTaskNodos(List<String> listaNodos){		
		List<Task> listaTareas = new ArrayList<Task>();
		Task tarea = null;
	
		// Only get tasks of active activity is a user-task 
		for (String nodo : listaNodos){
			if(this.motor.USER_TASK_NODE_TYPE.equals(this.getInfoNodo(nodo, this.motor.NODE_TYPE))){
				tarea = this.motor.getTaskService().createTaskQuery().executionId(this.processInstance.getProcessInstanceId()).singleResult();
				listaTareas.add(tarea);
			}
		}		
		return(listaTareas);
	}	
	 
    /**
    * Devuelve la tarea de usuario correspondiente a un nodo de la instancia de proceso
    * @param nodo Identificador del nodo
    * @return Tarea de activiti (org.activiti.engine.task) correspondiente al nodo pasado por parámetro
    */
	public Task getUserTaskNodo(String nodo){				
		Task tarea = null;
		
		if(this.motor.USER_TASK_NODE_TYPE.equals(this.getInfoNodo(nodo, this.motor.NODE_TYPE))){
			tarea = this.motor.getTaskService().createTaskQuery().executionId(this.processInstance.getProcessInstanceId()).singleResult();
		}	
		return(tarea);
	}	
	 	
    /**
    * Devuelve el estado de la tarea correspondiente a un nodo de la instancia de proceso
    * @param nodo Identificador del nodo
    * @return Estado de la tarea (Tarea.EstadoTarea) correspondiente al nodo pasado por parámetro
    */
	public Tarea.EstadoTarea getUserTaskEstadoNodo(String nodo){				
		Task tarea = null;
		Tarea.EstadoTarea estado=Tarea.EstadoTarea.SIN_ASIGNAR;
		
		if(this.motor.USER_TASK_NODE_TYPE.equals(this.getInfoNodo(nodo, this.motor.NODE_TYPE))){
			tarea = this.motor.getTaskService().createTaskQuery().executionId(this.processInstance.getProcessInstanceId()).singleResult();
			if (tarea.getAssignee().isEmpty()){
				estado = Tarea.EstadoTarea.SIN_ASIGNAR;
			}
			else {
				estado = Tarea.EstadoTarea.ASIGNADA;
			}
		}		
		return(estado);
	}	
	
	
	/**
	 * Envía señal a la instancia de proceso
	 * @param variables Variables a pasar al proceso necesarias para su ejecución
	 */
	public void signal(Map<String, Object> variables){
		this.motor.getRuntimeService().signal(this.processInstance.getId(), variables);		
	}
	
	/**
	 * Devuelve el total de instancias de proceso terminadas respecto de una definición de proceso
	 * @param ProcessDefinitionId Identificador de la definición de proceso
	 * @return Total de instancias de proceso terminadas
	 */	
	public long getCountInstanciasDefProcesoTerminado(String ProcessDefinitionId){
		 return(this.motor.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionId(ProcessDefinitionId).finished().count());
	}

	/**
	 * Devuelve el total de instancias de proceso sin terminar respecto de una definición de proceso
	 * @param ProcessDefinitionId Identificador de la definición de proceso
	 * @return Total de instancias de proceso sin terminar
	 */		
	public long getCountInstanciasDefProcesoSinTerminar(String ProcessDefinitionId){ 				  	   	    					
		 return(this.motor.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionId(ProcessDefinitionId).unfinished().count());
	}		
	
	/**
	 * Devuelve la lista de tareas activas de la instancia de proceso
	 * @return Lista de tareas (objeto Tarea)
	 */		
	// Devolver la lista de tareas activas del proceso
	public List<Tarea> getTareasActivas(){    
		List<Task> listaTar = motor.getTaskService().createTaskQuery()
				 .processInstanceId(this.getProcessInstance().getProcessInstanceId())
				 .list();	
		
		List <Tarea> lista = new ArrayList<Tarea>();

		//Objeto proceso
		Proceso proceso = new Proceso(motor, processInstance);
		
		for (Task tar : listaTar) {
			if (tar.getProcessInstanceId().equals(this.processInstance.getProcessInstanceId())){	
				// Obtener un objeto Tarea 
				Tarea tarea = new Tarea (proceso, tar);
				
				// Actualizar el estado de la tarea
				//tarea.refrescaEstado();  
				
				lista.add(tarea);
			}
		}			
		
		return(lista);
	}

	/**
	 * Devuelve el total de tareas activas de la instancia de proceso
	 * @return Total de tareas activas
	 */				
	public long getCountTareasActivas(){					
	    return this.getTareasActivas().size();	    		
	}	
		
	/**
	 * Devuelve el total de tareas resueltas de la instancia de proceso
	 * @return Total de tareas activas
	 */		
	public long getCountTareasResueltas(){	
		return this.getTareasResueltas().size();
	}	

	/**
	 * Devuelve la lista de tareas resueltas de la instancia de proceso
	 * @return Lista con los identificadores de las tareas resueltas
	 */	
	public List<String> getTareasResueltas(){	
		List<HistoricTaskInstance> listaTar = this.motor.getHistoryService().createHistoricTaskInstanceQuery()
				.finished()
				.processDefinitionId(this.processInstance.getProcessDefinitionId())
				.list();
		
		List <String> lista = new ArrayList<String>();
		for (HistoricTaskInstance tar : listaTar) {
			if (tar.getProcessInstanceId().equals(this.processInstance.getProcessInstanceId())){
				lista.add(tar.getName());
			}
		}			
		
		return(lista);	
	}
	
	/**
	 * Devuelve el inicio de la instancia de proceso
	 * @return Fecha y hora de inicio 
	 */		
	// Devuelve el inicio (date) de un proceso
	public Date getInicioProceso(){		
		HistoricProcessInstance historicProcessInstance = this.motor.getHistoryService()
																	.createHistoricProcessInstanceQuery()
																	.processInstanceId(this.processInstance.getId())
																	.singleResult();		
		return(historicProcessInstance.getStartTime());		
	}

	/**
	 * Devuelve el final de la instancia de proceso
	 * @return Fecha y hora de finalización del proceso 
	 */		
	public Date getFinProceso(){		
		HistoricProcessInstance historicProcessInstance = this.motor.getHistoryService()
																	.createHistoricProcessInstanceQuery()
																	.processInstanceId(this.processInstance.getId())
																	.singleResult();		
		return(historicProcessInstance.getEndTime());		
	}

	/**
	 * Devuelve la duración de la instancia de proceso
	 * @return Duración en milisegundos del proceso
	 */		
	public Long getDuracionProceso(){		
		HistoricProcessInstance historicProcessInstance = this.motor.getHistoryService()
																	.createHistoricProcessInstanceQuery()
																	.processInstanceId(this.processInstance.getId())
																	.singleResult();		
		return(historicProcessInstance.getDurationInMillis());		
	}		
	
	/**
	 * Adjunta documento a la instancia de proceso 
	 * @param nombreDoc Nombre del documento
	 * @param tipoDoc Tipo del documento
	 * @param descripcionDoc Descripción del adjunto
	 * @param contenidoDoc Contenido del documento
	 * @return Identificador del adjunto 
	 */		
	public String adjuntarDocProcesoTarea(String nombreDoc, 
										  String tipoDoc, 
							  			  String descripcionDoc,
										  InputStream contenidoDoc) {		
		
		Attachment adjunto = this.motor.getTaskService().createAttachment( tipoDoc, 
																		   null,       //adjuntar a la tarea
																		   this.processInstance.getProcessInstanceId(),  //adjuntar al proceso
																		   nombreDoc, 
																		   descripcionDoc, 
																		   contenidoDoc
																		  );		    				
		return adjunto.getId();
	}	

	/**
	 * Recupera un documento adjunto a la instancia de proceso 
	 * @param idAdjunto Identificador del adjunto a recuperar
	 * @return Contenido del adjunto 
	 */	
	public InputStream getContenidoAdjunto(String idAdjunto) {						
		return(this.motor.getTaskService().getAttachmentContent(idAdjunto));
	}
	
	/**
	 * Elimina un documento adjunto de la instancia de proceso
	 * @param idAdjunto Identificador del adjunto a eliminar
	 */		
	public void borrarAdjunto(String idAdjunto) {						
		this.motor.getTaskService().deleteAttachment(idAdjunto);
	}	
	
	/**
	 * Recupera información de un documento adjunto a la instancia de proceso
	 * @param idAdjunto Identificador del adjunto a eliminar
	 * @param info Información a obtener del adjunto: ATTACHMENT_TYPE (tipo), 
	 * 												  ATTACHMENT_PROCESS_INSTANCE_ID (identificador del proceso al que se adjuntó), 
	 * 												  ATTACHMENT_TASK_ID (identificador de la tarea a la que se adjuntó), 
	 * 												  ATTACHMENT_DESCRIPTION (descripción del adjunto),
	 * 												  ATTACHMENT_NAME (nombre del adjunto)  
	 * @return Información solicitada por info
	 */			
	public String getInfoAdjunto (String idAdjunto, String info){		
		String retorno=null;
		Attachment adjunto = this.motor.getTaskService().getAttachment(idAdjunto);
		
		if (info.equals(this.motor.ATTACHMENT_TYPE)) {
			retorno = adjunto.getType();
		}
		else if (info.equals(this.motor.ATTACHMENT_PROCESS_INSTANCE_ID)) {
			retorno = adjunto.getProcessInstanceId();
		}	
		else if (info.equals(this.motor.ATTACHMENT_TASK_ID)) {
			retorno = adjunto.getTaskId();
		}		
		else if (info.equals(this.motor.ATTACHMENT_NAME)) {
			retorno = adjunto.getName();
		}
		else if (info.equals(this.motor.ATTACHMENT_DESCRIPTION)) {
			retorno = adjunto.getDescription();
		}       
		
		return (retorno);
	}
}
