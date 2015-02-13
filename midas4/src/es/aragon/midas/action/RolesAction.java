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
	
	@Override
	public String execute() {
		return "roles";
	}

	public String list() {
		return "roles";
	}
	
	public String nuevo() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId())){
			rolesDAO.save(rol);
		}
		return "roles";
	}
	
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
	
	public String borrar() {
		if(rol != null && !GenericValidator.isBlankOrNull(rol.getRoleId())){
			rolesDAO.delete(rol);
		}
		return "roles";
	}
	
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
	
	public List<MidRole> getRoles(){
		return rolesDAO.findAll();
	}
	
	public List<MidGrant> getGrants(){
		return grantsDAO.findAll();
	}
	
	public List<MidRole> getRolesGrants(){
		return rolesDAO.findRoleGrants();
	}
	
	public List<MidRole> getRolesContexts(){
		return rolesDAO.findRoleContexts();
	}
	
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}

	public MidRole getRol() {
		return rol;
	}

	public void setRol(MidRole rol) {
		this.rol = rol;
	}

	public MidGrant getGrant() {
		return grant;
	}

	public void setGrant(MidGrant grant) {
		this.grant = grant;
	}

	public MidContext getContext() {
		return context;
	}

	public void setContext(MidContext context) {
		this.context = context;
	}

}
