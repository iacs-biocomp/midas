/**
 *
 */
package es.aragon.midas.action;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Menu;
import es.aragon.midas.config.MidMenu;
import es.aragon.midas.config.MidMessage;
import es.aragon.midas.config.MidNotification;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.IMessagesDAO;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.UserAware;

/**
 * @author carlos
 *
 */
public abstract class MidasActionSupport extends ActionSupport 
    implements UserAware, ServletRequestAware, ServletResponseAware {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    public static final String FORBIDDEN = "forbidden";
    public static final String EDITFORM = "editform";
    public static final String SHOWFORM = "showform";
    public static final String ADDFORM = "addform";
    public static final String LIST = "list";
    public static final String LOGOUT = "logout";
    
    
    /**
     * User vinculado a la sesión que ejecuta la acción
     */
    protected MidUser user;
    /**
     *
     */
    private String grantRequired = "SUPER";
    /**
     *
     */
    public MidMenu userMenu;
    /**
     *
     */
    public MidMenu mainMenu = Menu.getMainMenu();
    /**
     * 
     */
    public String version = AppProperties.getParameter("midas.appVersion");
    /**
     *
     */
    protected Logger log = new Logger();

    @Inject
    protected ILOPDLogger audit;
    
    protected HttpServletRequest request;

    protected String pathinfo;
	private HttpServletResponse response;
	
	@Inject
	protected IMessagesDAO msgDAO;
	
	protected List<MidMessage> messages;
	protected int messagesNumber;
	protected List<MidNotification> notifications;
	protected int notificationsNumber;
	
    
    /*
     * (non-Javadoc) @see
     * es.aragon.midas.security.UserAware#setUser(es.aragon.midas.security.User)
     */
    public void setUser(MidUser user) {
        this.user = user;
        log.setUser(user.getUserName());
        audit.setUser(user.getUserName());
        audit.setIdd(user.getIdd());
        
        //insert menu
        if (Constants.TRUE.equals(EnvProperties.getProperty(Constants.CFG_MENUDB) ) ) {
            userMenu = Menu.getCustomizedMenu(user);
        	log.debug("Asociando menu");
            if (pathinfo != null) {
            	userMenu.setAllInactive();
            	userMenu.searchActive(pathinfo);
            	log.debug("Pathinfo " + pathinfo);
            }
            else
            	log.debug("pathinfo es nulo");
        }
        
        //response.setHeader("midas_user", user.getUserName());
/*
        // insert notifications
        log.debug("Comprobando sistema de notificaciones");
        if (Constants.TRUE.equals(AppProperties.getParameter(Constants.NOTIFICATIONS_MANAGER)) ) {
            log.debug("Sistema de notificaciones activo");
            try {
            	notifications = msgDAO.findNotByReceiverStatus(user.getUserName(), Constants.MSG_SENT);
            	notificationsNumber = notifications.size();
            } catch (Exception e) {
            	log.error("Error obteniendo MessagesDAO o leyendo notificaciones");
            }
        } else {
        	log.debug("Sistema de notificaciones inactivo");
        }

        
        // insert messages
        log.debug("Comprobando sistema de mensajería");
        if (Constants.TRUE.equals(AppProperties.getParameter(Constants.MESSAGES_MANAGER) ) 
        		|| user.isGranted(Constants.ROLE_SUPPORT)) {
            log.debug("Sistema de mensajería activo");
            try {
            	messages = msgDAO.findByReceiverStatus(user.getUserName(), Constants.MSG_SENT);
            	messagesNumber = messages.size();
            } catch (Exception e) {
            	log.error("Error obteniendo MessagesDAO o leyendo mensajes");
            }
        } else {
        	log.debug("Sistema de mensajería inactivo");
        }
  */  
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
     * @return
     */
    public MidMenu getMainMenu() {
        return mainMenu;
    }
    
    
    /**
     * 
     * @return
     */
    public String getVersion(){
    	return version;
    }

    
    /**
     * 
     * @return
     */
    public MidMenu getUserMenu() {
        return userMenu;
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

	
    
    /**
     * Notifications management
     */
    
    /**
     * Devuelve el valor de la constante midas.messages.active
     * @return "true" o "false"
     */
    public String getNotificationsActive() {
    	return AppProperties.getParameter(Constants.NOTIFICATIONS_MANAGER);
    }
    
    /**
     * Devuelve todos los mensajes del usuario
     * @return List con los mensajes del usuario
     */
    public List<MidNotification> getNotifications() {
		return notifications.subList(0, notifications.size() > 5 ? 5 : notifications.size());
	}

	
    /**
     * Devuelve el número de mensajes no leídos del usuario
     * @return numero de mensajes
     */
    public int getNotificationsNumber() {
		return notificationsNumber;
	}    
    
    /**
     * Messages management
     */
    
    /**
     * Devuelve el valor de la constante midas.messages.active
     * @return "true" o "false"
     */
    public String getMessagesActive() {
    	if (user.isGranted("SUPPORT"))
    		return "true";
    	else	
    		return AppProperties.getParameter(Constants.MESSAGES_MANAGER);
    }
    
    /**
     * Devuelve todos los mensajes del usuario
     * @return List con los mensajes del usuario
     */
    public List<MidMessage> getMessages() {
		return messages.subList(0, messages.size() > 5 ? 5 : messages.size());
	}

	
    /**
     * Devuelve el número de mensajes no leídos del usuario
     * @return numero de mensajes
     */
    public int getMessagesNumber() {
		return messagesNumber;
	}    
    

    /**
     * Devuelve el valor de la constante midas.messages.active
     * @return "true" o "false"
     */
    public String getCauFormActive() {
    	if (user.isGranted("SUPPORT"))
    		return "false";
    	else
    		return AppProperties.getParameter(Constants.CAUFORM_MANAGER);
    }    
    
}
