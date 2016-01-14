/**
 * 
 */
package es.aragon.midas.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import es.aragon.midas.process.Tarea.EstadoTarea;

/**
 * Clase que implementa el motor donde se ejecutarán procesos correspondientes a flujos BPMN 2.0
 * @author  Susana Deza
 * @version  1.0
 */
public class Motor {
	// Constantes publicas de tipos de tareas
	/**
	 * Constante que indica el tipo de tarea USER_TASK
	 */
	public final String USER_TASK_NODE_TYPE = "userTask";	
	/**
	 * Constante que indica el tipo de tarea RECEIVE_TASK
	 */
	public final String RECEIVE_TASK_NODE_TYPE = "receiveTask";
	
	// Constantes publicas de ficheros adjuntos
	/**
	 * Tipo del adjunto
	 */	
	public final String ATTACHMENT_TYPE = "type";
	/**
	 * Identificador de instancia de proceso donde se encuentra el adjunto
	 */	
	public final String ATTACHMENT_PROCESS_INSTANCE_ID="ProcessInstanceId";
	/**
	 * Identificador de tarea donde se encuentra el adjunto
	 */
	public final String ATTACHMENT_TASK_ID="TaskId";
	/**
	 * Descripción del adjunto
	 */	
	public final String ATTACHMENT_DESCRIPTION="Description";
	/**
	 * Nombre del adjunto
	 */	
	public final String ATTACHMENT_NAME="Name"; 	
	
	// Constantes publicas de nodos de proceso
	/**
	 * Nombre del nodo
	 */	
	public final String NODE_NAME = "name";
	/**
	 * Tipo del nodo
	 */	
	public final String NODE_TYPE = "type";
	/**
	 * Descripción del nodo
	 */		
	public final String NODE_DESC = "documentation";
	
	//ATRIBUTOS
	private ProcessEngine processEngine;		
	private RuntimeService runtimeService;
	private RepositoryService repositoryService;
	private TaskService taskService;
	private ManagementService managementService;
	private IdentityService identityService;
	private HistoryService historyService;
	private FormService formService;
	
	// CONSTRUCTOR
    /**
    * Constructor para el motor definido en activiti-cfg.xml. 
    * Establece los servicios: RuntimeService, RepositoryService, TaskService, ManagementService, IdentityService, HistoryService, FormService
    * @see <a href="http://activiti.org/javadocs/org/activiti/engine/ProcessEngine.html">org.activiti.engine.ProcessEngine</a>
    */	
	public Motor(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
		setRuntimeService(processEngine.getRuntimeService());
		setRepositoryService(processEngine.getRepositoryService());
		setTaskService(processEngine.getTaskService());
		setManagementService(processEngine.getManagementService());
		setIdentityService(processEngine.getIdentityService());
		setHistoryService(processEngine.getHistoryService());
		setFormService(processEngine.getFormService());
		}

	//SETTERS
	private void setFormService(FormService fs) {
		this.formService = fs;		
	}

	private void setHistoryService(HistoryService hs) {
		this.historyService = hs;		
	}

	private void setIdentityService(IdentityService is) {
		this.identityService = is;		
	}

	private void setManagementService(ManagementService ms) {
		this.managementService = ms;	
	}

	private void setTaskService(TaskService ts) {
		this.taskService = ts;		
	}

	private void setRepositoryService(RepositoryService rs) {
		this.repositoryService = rs;		
	}

	private void setRuntimeService(RuntimeService rs) {
		this.runtimeService = rs;		
	}
	
	//GETTERS
	public FormService getFormService() {
		return(this.formService);		
	}

	public HistoryService getHistoryService() {
		return(this.historyService);	
	}

	public IdentityService getIdentityService() {
		return(this.identityService);		
	}

	public ManagementService getManagementService() {
		return(this.managementService);	
	}

	public TaskService getTaskService() {
		return(this.taskService);		
	}

	public RepositoryService getRepositoryService() {
		return(this.repositoryService);	
	}

	public RuntimeService getRuntimeService() {
		return(this.runtimeService);		
	}	
	
	public ProcessEngine getProcessEngine() {
		return(this.processEngine);		
	}
	
    /**
    * Inicia el proceso correspondiente a un flujo BPMN
    * @param nbProceso Nombre del proceso asignado al flujo en el diagrama BPMN 
    * @param usuario Identificación del usuario que se autentica para iniciar el proceso
    * @param variables Variables pasadas al flujo; puede ser null
    * @return Proceso iniciado
    * @throws ActivitiObjectNotFoundException - Cuando no existe el despliegue de la definición de proceso 
    */
	public Proceso iniciaProceso(String nbProceso, 
										 String usuario,
										 Map<String,Object> variables)
    throws Exception{		
		IdentityService identityService = this.getProcessEngine().getIdentityService();						  
		identityService.setAuthenticatedUserId(usuario);
		ProcessInstance proceso = this.getRuntimeService().startProcessInstanceByKey(nbProceso, variables);		
		
		return (new Proceso(this, proceso));
	}
	
    /**
    * Devuelve si un usuario pertenece al sistema
    * @param usuario Identificación del usuario 
    * @param pswd Contraseña del usuario
    * @return Si el usuario/contraseña existe en el sistema: true; si no, false
    */
	public boolean esUsuarioSistema(String usuario, String pswd){
	  return(identityService.checkPassword(usuario, pswd));	
	}
	
    /**
    * Devuelve si un usuario pertenece al grupo
    * @param usuario Identificación del usuario que se quiere comprobar
    * @param grupo Grupo en el que se buscará el usuario
    * @return Si el usuario existe en el grupo: true; si no, false
    */
	public Boolean esUsuarioGrupo(String usuario, String grupo){
		Boolean rtdo = false;
		
		//Obener los grupos a los que pertenece el usuario
		List<Group> groups = identityService.createGroupQuery().groupMember(usuario).groupId(grupo).list();
		for (Group group : groups ) {
			if(group.getId().equals(grupo)){
				return true;
			}
		}
		return rtdo;
    }	

    /**
    * Devuelve los identificadores de los grupos de un usuario
    * @param usuario Identificación del usuario 
    * @return Lista con los identificadores de los grupos a los que pertenece el usuario 
    */
	public List<String> getGruposUsuario(String usuario){		
		List <String> grupos = new ArrayList<String>();
		
		//Obener los grupos del usuario
		List<Group> gruposUsuario = identityService.createGroupQuery().groupMember(usuario).list();		
		for (Group grupo : gruposUsuario ) {						
			grupos.add(grupo.getId());
		}
		return grupos;
    }	

	/**
	 * Dada una lista de tareas y un estado, devuelve el total de las tareas por cada uno de los grupos del usuario 
	 * @param usr Usuario del que se obtendrán los grupos
	 * @param listaTareas Lista de tareas que se comprobarán
	 * @param estado Estado de las tareas a comprobar
	 * @return Colección de pares Grupo-Recuento, donde Grupo es el identificador de cada uno de los grupos a los que pertenece el usuario
	 */
	public Map<String, Long> getCountTareasGruposUsuarioEstado(String usr, List<Tarea> listaTareas, EstadoTarea estado){
		Map<String, Long> recuentoTareasGrupos = new HashMap <String, Long>();
		
		// Recuperar la lista con los grupos del usuario
		List<String> gruposUsr = this.getGruposUsuario(usr);

		List<Tarea> tareasEstado = this.getTareasEstado(listaTareas, estado);
		// De cada grupo, contar las tareas que pueden ser ejecutadas por ese grupo		
		for (String grupoUsr : gruposUsr) {
			int recuento = 0;
			for (Tarea tareaEstado : tareasEstado) {
				if (tareaEstado.getGruposTarea().contains(grupoUsr)){
					recuento +=1;
				}
			}
			recuentoTareasGrupos.put(grupoUsr, (long) recuento);
		}
		
		return recuentoTareasGrupos;
	}
	
	
    /**
    * Devuelve lista de tareas asociadas a un grupo: activas, asignadas o no a usuarios, sin completar
    * @param grupo Identificación de grupo de usuarios
    * @param asignadas Indica si se solicitan las tareas activas asignadas a usuarios del grupo (true), las no asignadas (false) o todas ellas (null)
    * @return Lista con las tareas de activiti que pertenezcan a usuarios del grupo y estén activas
    * @see  <a href="http://activiti.org/javadocs/org/activiti/engine/task/Task.html">org.activiti.engine.task.Task</a>
    */
	public List<Task> getTasksGrupo(String grupo, Boolean asignadas){
		String condicion = null;
		
		// Seleccionar las tareas de un grupo
		String query = "SELECT task.* " +
					   "FROM ACT_RU_TASK task, ACT_RU_IDENTITYLINK link "+
					   "WHERE task.ID_ = link.TASK_ID_ " +					   
					   "  AND link.GROUP_ID_ IN('" + grupo + "')";
		
		if (asignadas==null){
			// Seleccionar TODAS las tareas activas (ASIGNADAS o NO)
			condicion = "";
		}
		else if (asignadas){
			// Seleccionar las tareas activas ASIGNADAS
			condicion = "  AND task.ASSIGNEE_ is not null" ;
		}
		else{
			// Seleccionar las tareas activas NO ASIGNADAS
			condicion = "  AND task.ASSIGNEE_ is null" ;
		}
		
		query += condicion;		
		return(taskService.createNativeTaskQuery().sql(query).list());
	}	
	
    /**
    * Devuelve lista de tareas activas asignadas a un usuario
    * @param usuario Identificación del usuario
    * @return Lista con las tareas activas de activiti que estén asignadas al usuario
    * @see  <a href="http://activiti.org/javadocs/org/activiti/engine/task/Task.html">org.activiti.engine.task.Task</a>
    */
	public List<Task> getTasksUsuario(String usuario){			
		return(taskService.createTaskQuery().taskAssignee(usuario).list());		
	}

    /**
    * De la lista de tareas indicada, devuelve las que tienen un estado concreto
    * @param tareas Lista de tareas a evaluar el estado
    * @param estado Estado a comprobar
    * @return Lista con las tareas que tengan el estado indicado
    */
	public List<Tarea> getTareasEstado(List<Tarea> tareas, EstadoTarea estado){
		List<Tarea> retorno = new ArrayList<Tarea>();
		
		for (Tarea tarea : tareas) {
			if (tarea.getEstado().equals(estado)){
				retorno.add(tarea);
			}
		}
		return(retorno);		
	}
	
    /**
    * Despliega un recurso en el repositorio de Activiti
    * @param nbDespliegue Nombre que se dará al despliegue
    * @param recurso Recurso que se desplegará
    * @throws Exception
    */	
	public void desplegar(String nbDespliegue, String recurso) throws Exception {
			repositoryService.createDeployment()
						 	 .name(nbDespliegue)
							 .addClasspathResource(recurso)
				             .deploy();
	}
	
    /**
    * Despliega dos recursos en el repositorio de Activiti
    * @param nbDespliegue Nombre que se dará al despliegue
    * @param recurso1 Primer recurso que se desplegará
    * @param recurso2 Segundo recurso que se desplegará
    * @throws Exception
    */	
	public void desplegar(String nbDespliegue, String recurso1, String recurso2) throws Exception {
		repositoryService.createDeployment()
					 	 .name(nbDespliegue)
						 .addClasspathResource(recurso1)
						 .addClasspathResource(recurso2)
			             .deploy();
	}
	
    /**
    * Elimina los despliegues correspondientes a una definición de proceso y sus objetos asociados (instancias de proceso, variables, etc)
    * @param nbProceso Proceso al que corresponden los despliegues a eliminar
    * @throws Exception
    */
	// Eliminar despliegue correspondiente a una definición de proceso
	public void eliminarDespliegue(String nbProceso) throws Exception {		
		List<ProcessDefinition> procesos = repositoryService.createProcessDefinitionQuery().list();
	    for (ProcessDefinition proceso : procesos) {	    	
	    	  if (nbProceso.equals(proceso.getKey())) {		        
		        //Eliminar despliegue
		            repositoryService.deleteDeployment(proceso.getDeploymentId(), true);		            
       
	    	  }
		}	   
	}	
	
    /**
    * Devuelve la lista de id's de proceso que están corriendo de una definición de proceso
    * @param ProcessDefinitionId Identificador del proceso a consultar
    * @return Lista de los identificadores de proceso
    */
	// Devuelve la lista de id's de proceso que están corriendo de una definición de proceso
	public List<String> getIdProcesosDefPro(String ProcessDefinitionId){		
		List<String> lista = new ArrayList<String>();		
		
		List<HistoricProcessInstance> procesos = historyService.createHistoricProcessInstanceQuery().unfinished().list();	
		
	    for (HistoricProcessInstance proceso : procesos) {
	    	if (proceso.getProcessDefinitionId().equals(ProcessDefinitionId)){
	    		lista.add(proceso.getId());	
	    	}	    		   
		}	  		
	    return lista;	    		
	}
	

    /**
    * Devuelve, de entre una lista de tareas, el total de las mismas que tienen un estado determinado 
    * @param tareas Lista de identificadores de las tareas a consultar
    * @param estado Estado de la tarea 
    * @return Recuento de las tareas con el estado indicado
    * @see Tarea
    */
	public long getCountTareasEstado(List<Tarea> tareas, EstadoTarea estado){
		long total=0;
		for (Tarea tarea : tareas) {
			if (tarea.getEstado().equals(estado)){
				total+=1;
			}
		}
		return (total);
	}
	
    /**
    * Devuelve las tareas que tiene asignadas un usuario y que por lo tanto están en su bandeja de entrada; 
    * también devuelve las posibles tareas que podrían asignársele por pertenecer al grupo al que pertenece la tarea activa
    * @param usuario Identificador del usuario
    * @return Lista de tareas asignadas o que pueden serlo por pertenecer el usuario al grupo
    */
	public List<Tarea> getBandejaEntrada(String usuario){
		List<Tarea> tareas = new ArrayList <Tarea >();
		
		// Tareas del usuario
		List<Task> tareasAsignadas = this.getTasksUsuario(usuario);  // consultar las tareas asignadas	
		for (Task task : tareasAsignadas) {
			// Obtener un objeto Proceso a partir del identificador de proceso de la tarea
			Proceso proceso = new Proceso(this, runtimeService.createProcessInstanceQuery()
															  .processInstanceId(task.getProcessInstanceId())
															  .singleResult());
			// Obtener un objeto Tarea a partir del task
			Tarea tarea = new Tarea (proceso, task);
			//tarea.refrescaEstado();  
			
			// Añadir la tarea a la lista
			tareas.add(tarea);
		}

		//Obtener los grupos del sistema
		List <Group> grupos = this.getIdentityService().createGroupQuery().list();
				
		//Añadir las tareas del grupo si el usuario pertenece a alguno 
		for (Group grupo : grupos) {	
			if (this.esUsuarioGrupo(usuario, grupo.getId())){
				// Tareas del grupo del usuario
				List<Task> tareasGrupo = this.getTasksGrupo(grupo.getId(), false); 
				
				for (Task task : tareasGrupo) {
					// Obtener un objeto Proceso a partir del identificador de proceso de la tarea
					Proceso proceso = new Proceso(this, runtimeService.createProcessInstanceQuery()
																	  .processInstanceId(task.getProcessInstanceId())
																	  .singleResult());
					// Obtener un objeto Tarea a partir del task
					Tarea tarea = new Tarea (proceso, task);
					//tarea.refrescaEstado();  
					
					// Añadir la tarea a la lista
					tareas.add(tarea);
				}
			}
		}		
				
		return tareas;
	}
	
	  /**
	    * Devuelve las tareas que un usuario tiene en cola
	    * @param usuario Identificador del usuario
	    * @return Lista de tareas en cola
	    */
	public List<Tarea> getBandejaEntradaCola(String usuario){		
		return this.getTareasEstado(this.getBandejaEntrada(usuario), Tarea.EstadoTarea.CREADA);   // Lista de tareas en cola
	}	

	
	  /**
	    * Devuelve las tareas que un usuario tiene en cola de uno de los grupos a los que pertenece
	    * @param usuario Identificador del usuario
	    * @param grupo Grupo del usuario 
	    * @return Lista de tareas en cola para el grupo
	    */
	public List<Tarea> getBandejaEntradaCola(String usuario, String grupo) throws Exception{
		List<Tarea> tareasEnCola = this.getTareasEstado(this.getBandejaEntrada(usuario), Tarea.EstadoTarea.CREADA);

		// Desglose de las tareas en cola para el grupo del usuario
		List<Tarea> tareasEnColaGrupo = new ArrayList <Tarea> ();
		
		// Recuperar los grupos del usuario
		List<String> gruposUsr = this.getGruposUsuario(usuario);
		
		// Si el usuario no tiene el grupo asignado, lanzar una excepción
		if (gruposUsr.contains(grupo)){
			for (Tarea tareaEnCola : tareasEnCola) {
				if (tareaEnCola.getGruposTarea().contains(grupo)){
					tareasEnColaGrupo.add(tareaEnCola);
				}
			}			
		}
		else{
			new Exception("ERROR: el usuario"+usuario+" no pertenece al grupo "+grupo+".");
		}
		
		return (tareasEnColaGrupo);  
	}	

	  /**
	    * Devuelve las tareas que un usuario tiene asignadas
	    * @param usuario Identificador del usuario
	    * @return Lista de tareas asignadas
	    */
	public List<Tarea> getBandejaEntradaAsig(String usuario){		
		return this.getTareasEstado(this.getBandejaEntrada(usuario), Tarea.EstadoTarea.ASIGNADA);   // Lista de tareas asignadas
	}	
}
