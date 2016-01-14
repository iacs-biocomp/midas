package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import org.apache.commons.validator.GenericValidator;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.config.MidGrant;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.dao.ContextsDAO;
import es.aragon.midas.dao.GrantsDAO;
import es.aragon.midas.dao.RolesDAO;


/**
 * Acciones para la gestión de roles de aplicación
 * @author Carlos
 *
 */
public class RolesAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="RolesDAO")
	RolesDAO rolesDAO;
	
	@EJB(name="GrantsDAO")
	GrantsDAO grantsDAO;
	
	@EJB(name="ContextsDAO")
	ContextsDAO contextsDAO;
	
	private MidRole rol = new MidRole();
	private MidGrant grant = new MidGrant();
	private MidContext context = new MidContext();

	
	/* *****************************************
	 * Métodos de action
	 *******************************************/		
	
	/**
	 * Método por defecto. Redirige a página principal de gestión de roles
	 */
	@Override
	public String execute() {
		return "roles";
	}

	
	/**
	 * Redirige a listado de roles
	 * @return
	 */
	public String list() {
		return "roles";
	}
	
	
	/**
	 * Guarda en BD un nuevo rol
	 * @return
	 */
	public String nuevo() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId())){
			rolesDAO.save(rol);
		}
		return "roles";
	}
	
	
	
	/**
	 * Guarda en BD un nuevo rol-grant
	 * @return
	 */
	public String nuevoRG() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId()) 
			&&	grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			MidRole role_ = rolesDAO.findByRoleId(rol.getRoleId());
			MidGrant grant_ = grantsDAO.findByGrId(grant.getGrId());
			if(!grant_.getMidRoleList().contains(role_)){
				grant_.getMidRoleList().add(role_);
				grantsDAO.save(grant_);
			}
		}
		return "roles";
	}
	
	
	
	/**
	 * Guarda en BD un nuevo rol-context
	 * @return
	 */
	public String nuevoRC() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId()) 
			&&	context != null && context.getCxId() != null){
			MidRole role_ = rolesDAO.findByRoleId(rol.getRoleId());
			MidContext context_ = contextsDAO.findByCxId(context.getCxId());
			if(!context_.getMidRoleList().contains(role_)){
				context_.getMidRoleList().add(role_);
				contextsDAO.save(context_);
			}
		}
		return "roles";
	}
	
	
	
	/**
	 * Borra un rol de BD
	 * @return
	 */
	public String borrar() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId())){
			rolesDAO.delete(rol);
		}
		return "roles";
	}
	
	
	/**
	 * Borra de BD un rol-grant
	 * @return
	 */
	public String borrarRG() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId()) 
			&&	grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			MidRole role_ = rolesDAO.findByRoleId(rol.getRoleId());
			MidGrant grant_ = grantsDAO.findByGrId(grant.getGrId());
			grant_.getMidRoleList().remove(role_);
			grantsDAO.save(grant_);
		}
		return "roles";
	}
	
	
	
	/**
	 * Borra de BD un rol-context
	 * @return
	 */
	public String borrarRC() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId()) 
			&&	context != null && context.getCxId() != null){
			MidRole role_ = rolesDAO.findByRoleId(rol.getRoleId());
			MidContext context_ = contextsDAO.findByCxId(context.getCxId());
			context_.getMidRoleList().remove(role_);
			contextsDAO.save(context_);
		}
		return "roles";
	}
	
	
	/* *****************************************
	 * SET / GET
	 *******************************************/		
	
	
	
	/**
	 * Devuelve la lista de roles
	 * @return
	 */
	public List<MidRole> getRoles(){
		return rolesDAO.findAll();
	}
	

	/**
	 * Devuelve la lista de grants
	 * @return
	 */
	public List<MidGrant> getGrants(){
		return grantsDAO.findAll();
	}
	

	
	/**
	 * Devuelve la lista de rol-grants
	 * @return
	 */
	public List<MidRole> getRolesGrants(){
		return rolesDAO.findRoleGrants();
	}
	
	
	/**
	 * Devuelve la lista de rol-context
	 * @return
	 */
	public List<MidRole> getRolesContexts(){
		return rolesDAO.findRoleContexts();
	}
	
	
	/**
	 * Devuelve la lista de contextos
	 * @return
	 */
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}

	
	
	/**
	 * Devuelve un rol (JPA->JSP)
	 * @return
	 */
	public MidRole getRol() {
		return rol;
	}

	
	/**
	 * Asigna un rol a la variable (JSP->JPA)
	 * @param rol
	 */
	public void setRol(MidRole rol) {
		this.rol = rol;
	}

	
	/**
	 * Obtiene un grant de la variable de sesión 
	 * @return
	 */
	public MidGrant getGrant() {
		return grant;
	}

	/**
	 * Asigna un grant a la variable de sesión
	 * @param grant
	 */
	public void setGrant(MidGrant grant) {
		this.grant = grant;
	}

	
	/**
	 * Devuelve un contexto de la variable de sesión
	 * @return
	 */
	public MidContext getContext() {
		return context;
	}

	/**
	 * Asigna un contexto a la variable de sesión
	 * @param context
	 */
	public void setContext(MidContext context) {
		this.context = context;
	}

}
