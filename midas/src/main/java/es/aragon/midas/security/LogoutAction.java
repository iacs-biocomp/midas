/**
 *
 */
package es.aragon.midas.security;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.config.Constants;
import es.aragon.midas.exception.MidasJPAException;
import es.aragon.midas.logging.IAccessLogger;
import es.aragon.midas.util.Utils;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.SessionAware;

/**
 * @author carlos
 * 
 */
public class LogoutAction extends MidasActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	@Inject
	private IAccessLogger aLog;
	private HttpServletRequest request;
	Map<String, Object> session; 

	private static final String AUDIT_ENTITY = "Logout";		
	{
		setGrantRequired("PUBLIC");
	}

	/**
	 * Carga el autenticador seleccionado y autentica al usuario que intenta
	 * logarse
	 */
	@Override
	public String execute() throws MidasJPAException {
		aLog.setUser(getUser().getUserName());

		
		if (user.isAliased()) {
			log.debug("Saliendo de aliasing como usuario" + user.getUserName());
			audit.log("ALX", AUDIT_ENTITY, user.getActualUser().getUserName(), "El usuario recupera su identidad. Ya no es " + user.getUserName());

			session.put(Constants.USER, user.getActualUser());
			return SUCCESS;
			
		} else {
		
			try {
				aLog.exit();
			} catch (Exception ex) {
				// TODO Log error
			}
			//request.getSession().invalidate();
			session.clear();
			
			return LOGOUT;
		}
	}

	/**
	 * Asigna la sesion en la que se esta ejecutando el autenticador
	 */
	public void setSession(Map<String, Object> session) {
		//session.clear();
		this.session = session;
	}

	/**
	 * 
	 * @param accessLogger_
	 */
	public void setAccessLogService(IAccessLogger accessLogger_) {
		this.aLog = accessLogger_;
	}
}
