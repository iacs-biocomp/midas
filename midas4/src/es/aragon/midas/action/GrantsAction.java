package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import org.apache.commons.validator.GenericValidator;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.dao.GrantsDAO;

public class GrantsAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="GrantsDAO")
	GrantsDAO grantsDAO;
	
	private MidGrant grant = new MidGrant();

	
	@Override
	public String execute() {
		return "grants";
	}

	public String list() {
		return "grants";
	}
	
	public String nuevo() {
		if(grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			grantsDAO.save(grant);
		}
		return "grants";
	}
	
	public String borrar() {
		if(grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			grantsDAO.delete(grant);
		}
		return "grants";
	}
	
	public MidGrant getGrant() {
		return grant;
	}

	public void setGrant(MidGrant grant) {
		this.grant = grant;
	}
	
	
	public List<MidGrant> getGrants(){
		return grantsDAO.findAll();
	}	

}
