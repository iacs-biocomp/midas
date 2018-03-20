/**
 *
 */
package es.aragon.midas.action;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.UserAware;

/**
 * Acción Struts Midas básica, sin decoraciones (menú, mensajería...).
 * Acción base para contenidos internos en otras páginas (div, iframe...)
 * 
 * @author carlos
 *
 */
public abstract class MidasContentSupport extends ActionSupport 
    implements UserAware, ServletRequestAware, ServletResponseAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4119339719042513159L;


    /**
     *
     */
    public static final String FORBIDDEN = "forbidden";
    public static final String EDITFORM = "editform";
    public static final String SHOWFORM = "showform";
    public static final String ADDFORM = "addform";
    
    
    /**
     * User vinculado a la sesión que ejecuta la acción
     */
    protected MidUser user;
    /**
     *
     */
    private String grantRequired = "SUPER";
    
    
    
    protected Logger log = new Logger();

    @Inject
    protected ILOPDLogger audit;
    
    protected HttpServletRequest request;

    protected String pathinfo;
    protected HttpServletResponse response;
	
    /*
     * (non-Javadoc) @see
     * es.aragon.midas.security.UserAware#setUser(es.aragon.midas.security.User)
     */
    public void setUser(MidUser user) {
        this.user = user;
        log.setUser(user.getUserName());
        audit.setUser(user.getUserName());
        audit.setIdd(user.getIdd());
     }

    /**
     * Devuelve el user vinculado a la sesion que ejecuta esta accion.
     *
     * @return
     */
    protected MidUser getUser() {
        return user;
    }

    /**
     *
     */
    public String getGrantRequired() {
        return grantRequired;
    }

   /**
    * 
    * @param grantRequired
    */
    protected void setGrantRequired(String grantRequired) {
        this.grantRequired = grantRequired;
    }

    
    
    /**
     * 
     * @param lopdLogService
     */
    public void setLopdLogService (ILOPDLogger lopdLogService) {
    	audit = lopdLogService;
    }

    
    /**
     * 
     */
    public void setServletRequest(HttpServletRequest request) {
      this.request = request;  
      this.pathinfo = request.getRequestURI().substring(request.getContextPath().length() + 1) + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
      }
    

    /**
     * 
     * @param response
     */
    public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
    
    /**
     * 
     * @return
     */
    public HttpServletResponse getServletResponse() {
		return this.response;
	}

    
}
