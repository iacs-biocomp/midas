/**
 *
 */
package es.aragon.midas.security;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.exception.MidasJPAException;
import es.aragon.midas.logging.IAccessLogger;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 * @author carlos
 * 
 */
public class LogoutAction extends MidasActionSupport implements
		ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;
	@Inject
	private IAccessLogger aLog;
	private HttpServletRequest request;

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
		try {
			aLog.exit();
		} catch (Exception ex) {
			// TODO Log error
		}
		request.getSession().invalidate();
		return SUCCESS;
	}

	/**
	 * Asigna la sesión en la que se está ejecutando el autenticador
	 */
	public void setSession(Map<String, Object> session) {
		session.clear();
	}

	/**
	 * 
	 * @param hsr
	 */
	public void setServletRequest(HttpServletRequest hsr) {
		this.request = hsr;
	}

	/**
	 * 
	 * @param accessLogger_
	 */
	public void setAccessLogService(IAccessLogger accessLogger_) {
		this.aLog = accessLogger_;
	}
}
