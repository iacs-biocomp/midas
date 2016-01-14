package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import org.apache.commons.validator.GenericValidator;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.dao.GrantsDAO;


/**
 * Action que gestiona los grants de una aplicación
 * @author Carlos
 *
 */
public class GrantsAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="GrantsDAO")
	GrantsDAO grantsDAO;
	
	private MidGrant grant = new MidGrant();


	/* *****************************************
	 * Métodos de action
	 *******************************************/	
	
	
	/**
	 * Método por defecto. Lleva a la página principal de gestión de permisos
	 */
	@Override
	public String execute() {
		return "grants";
	}

	
	/**
	 * Redirige a la página de listado
	 * @return
	 */
	public String list() {
		return "grants";
	}
	
	
	/**
	 * Guarda un nuevo permiso (asignado previamente a "grant")
	 * @return
	 */
	public String nuevo() {
		if(grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			grantsDAO.save(grant);
		}
		return "grants";
	}
	
	
	/**
	 * Borra un permiso (guardado en "grant")
	 * @return
	 */
	public String borrar() {
		if(grant != null && !GenericValidator.isBlankOrNull(grant.getGrId())){
			grantsDAO.delete(grant);
		}
		return "grants";
	}
	

	/* *****************************************
	 * Métodos SET / GET
	 *******************************************/
	
	
	/**
	 * Obtiene un permiso (JPA -> JSP)
	 * @return
	 */
	public MidGrant getGrant() {
		return grant;
	}

	
	/**
	 * Asigna un permiso a la variable "grant" (JSP -> JPA)
	 * @param grant
	 */
	public void setGrant(MidGrant grant) {
		this.grant = grant;
	}
	
	
	/**
	 * Recupera la lista de permisos desde BD
	 * @return
	 */
	public List<MidGrant> getGrants(){
		return grantsDAO.findAll();
	}	

}
