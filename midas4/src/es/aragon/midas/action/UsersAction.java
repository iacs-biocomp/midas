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
	
	@Override
	public String execute() {
		return "users";
	}

	public String list() {
		return "users";
	}
	
	public String nuevo() {
		if(!GenericValidator.isBlankOrNull(userNew.getUserName()) && null == usersDAO.find(userNew.getUserName())){
			userNew.setUserName(userNew.getUserName().toUpperCase());
			userNew.setActive('Y');
			usersDAO.create(userNew);
			userMod = usersDAO.findByUserName(userNew.getUserName());
		}
		return "users";
	}
	
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
	 * Añade un contexto a un usuario
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
	
	
	
	public MidUser getUserMod() {
		return userMod;
	}
	
	public void setUserMod(MidUser user) {
		this.userMod = user;
	}
	
	public List<MidUser> getUsers(){
		return usersDAO.findAll();
	}
	
	public List<MidRole> getRoles(){
		return rolesDAO.findAll();
	}
	
	public List<MidRole> getRolesUngiven(){
		return rolesDAO.findAllUngiven(userMod);
	}
	
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}

	/*public List<MidContext> getContextsUngiven(){
		return contextsDAO.findAllUngiven(userMod);
	}*/
	
	public MidRole getUserRol() {
		return userRol;
	}

	public void setUserRol(MidRole userRol) {
		this.userRol = userRol;
	}

	public MidContext getUserContext() {
		return userContext;
	}

	public void setUserContext(MidContext userContext) {
		this.userContext = userContext;
	}

	public MidUser getUserNew() {
		return userNew;
	}

	public void setUserNew(MidUser userNew) {
		this.userNew = userNew;
	}

}
