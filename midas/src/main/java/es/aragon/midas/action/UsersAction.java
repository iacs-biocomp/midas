package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import org.apache.commons.validator.GenericValidator;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.ContextsDAO;
import es.aragon.midas.dao.RolesDAO;
import es.aragon.midas.dao.UsersDAO;


/**
 * Acciones para la gestión de usuarios de una aplicación
 * @author Carlos
 *
 */
public class UsersAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="RolesDAO")
	RolesDAO rolesDAO;
	
	@EJB(name="ContextsDAO")
	ContextsDAO contextsDAO;
	
	@EJB(name="UsersDAO")
	UsersDAO usersDAO;
	
	private MidUser userMod = new MidUser();
	private MidRole userRol = new MidRole();
	private MidContext userContext = new MidContext();
	
	private MidUser userNew = new MidUser();
	
	/* *****************************************
	 * Métodos de action
	 *******************************************/		
	
	/**
	 * Acción por defecto. Lleva a la página principal de gestión de usuarios
	 */
	@Override
	public String execute() {
		return "users";
	}

	
	
	/**
	 * Redirige a la página de listado de usuarios
	 * @return
	 */
	public String list() {
		return "users";
	}
	
	
	/**
	 * Guarda en BD un nuevo usuario
	 * @return
	 */
	public String nuevo() {
		if(!GenericValidator.isBlankOrNull(userNew.getUserName()) && null == usersDAO.find(userNew.getUserName())){
			userNew.setUserName(userNew.getUserName().toUpperCase());
			userNew.setActive('Y');
			usersDAO.create(userNew);
			userMod = usersDAO.findByUserName(userNew.getUserName());
		}
		return "users";
	}
	
	
	/**
	 * Activa/desactiva un usuario
	 * @return
	 */
	public String activar() {
		Character activo = userMod.getActive();
		if(!GenericValidator.isBlankOrNull(userMod.getUserName()) && activo != null){
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userMod.setActive(activo);
			usersDAO.update(userMod);
			userMod = new MidUser();
		}
		return "users";
	}
	
	
	
	/**
	 * Selecciona un usuario
	 * @return
	 */
	public String select() {
		if(!GenericValidator.isBlankOrNull(userMod.getUserName())){
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}
	

	
	/**
	 * Asocia un rol a un usuario
	 * @return
	 */
	public String nuevoUR() {
		if(!GenericValidator.isBlankOrNull(userMod.getUserName()) && !GenericValidator.isBlankOrNull(userRol.getRoleId())){
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userRol = rolesDAO.findByRoleId(userRol.getRoleId());
			if(!userRol.getMidUserList().contains(userMod)){
				usersDAO.addUserRole(userMod, userRol);
			}
		}
		return "users";
	}
	

	
	/**
	 * Elimina un rol a un usuario
	 * @return
	 */
	public String borrarUR() {
		if(!GenericValidator.isBlankOrNull(userMod.getUserName()) && !GenericValidator.isBlankOrNull(userRol.getRoleId())){
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userRol = rolesDAO.findByRoleId(userRol.getRoleId());
			if(userRol.getMidUserList().contains(userMod)){
				usersDAO.deleteUserRole(userMod, userRol);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}
	
	
	/**
	 * A�ade un contexto a un usuario
	 * @return
	 */
	public String nuevoUC() {
		if(!GenericValidator.isBlankOrNull(userMod.getUserName()) && userContext.getCxId() != null){
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userContext = contextsDAO.findByCxId(userContext.getCxId());
			if(!userContext.getMidUserList().contains(userMod)){
				usersDAO.addUserContext(userMod, userContext);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}
	
	
	/**
	 * Elimina un contexto a un usuario
	 * @return
	 */
	public String borrarUC() {
		if(!GenericValidator.isBlankOrNull(userMod.getUserName()) && userContext.getCxId() != null){
			userMod = usersDAO.findByUserName(userMod.getUserName());
			userContext = contextsDAO.findByCxId(userContext.getCxId());
			if(userContext.getMidUserList().contains(userMod)){
				usersDAO.deleteUserContext(userMod, userContext);
			}
			userMod = usersDAO.findByUserName(userMod.getUserName());
		}
		return "users";
	}
	
	
	
	/* *****************************************
	 * SET/GET
	 *******************************************/		
	
	
	/**
	 * Devuelve el usuario desde variable de sesión
	 * @return
	 */
	public MidUser getUserMod() {
		return userMod;
	}
	
	
	/**
	 * Asocia un usuario a una variable de sesión
	 * @param user
	 */
	public void setUserMod(MidUser user) {
		this.userMod = user;
	}
	
	
	/**
	 * Devuelve la lista de usuarios
	 * @return
	 */
	public List<MidUser> getUsers(){
		return usersDAO.findAll();
	}
	
	/**
	 * Devuelve la lista de roles desde BD
	 * @return
	 */
	public List<MidRole> getRoles(){
		return rolesDAO.findAll();
	}
	
	
	/**
	 * Devuelve la lista de roles no asignados aún, desde BD
	 * @return
	 */
	public List<MidRole> getRolesUngiven(){
		return rolesDAO.findAllUngiven(userMod);
	}
	
	
	/**
	 * Devuelve la lista de contextos desde BD
	 * @return
	 */
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}

	
	/**
	 * Devuelve el rol de usuario
	 * @return
	 */
	public MidRole getUserRol() {
		return userRol;
	}

	
	
	/**
	 * Asigna el rol de usuario
	 * @param userRol
	 */
	public void setUserRol(MidRole userRol) {
		this.userRol = userRol;
	}

	
	/**
	 * Devuelve el contexto de un usuario
	 * @return
	 */
	public MidContext getUserContext() {
		return userContext;
	}

	
	/**
	 * Asigna el contexto de un usuario
	 * @param userContext
	 */
	public void setUserContext(MidContext userContext) {
		this.userContext = userContext;
	}

	
	/**
	 * Devuelve el nuevo usuario
	 * @return
	 */
	public MidUser getUserNew() {
		return userNew;
	}

	
	/**
	 * Asigna el nuevo usuario
	 * @param userNew
	 */
	public void setUserNew(MidUser userNew) {
		this.userNew = userNew;
	}

}
