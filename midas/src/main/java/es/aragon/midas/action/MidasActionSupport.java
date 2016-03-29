/**
 *
 */
package es.aragon.midas.action;

import javax.inject.Inject;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import es.aragon.midas.config.Menu;
import es.aragon.midas.config.MidMenu;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.UserAware;

/**
 * @author carlos
 *
 */
public abstract class MidasActionSupport extends ActionSupport 
    implements UserAware {

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
    protected Logger log = new Logger();

    @Inject
    protected ILOPDLogger audit;
    
    
    /*
     * (non-Javadoc) @see
     * es.aragon.midas.security.UserAware#setUser(es.aragon.midas.security.User)
     */
    public void setUser(MidUser user) {
        this.user = user;
        if (EnvProperties.getProperty(Constants.CFG_MENUDB).equals("true")) {
            userMenu = Menu.getCustomizedMenu(user);
        }
        log.setUser(user.getUserName());
        audit.setUser(user.getUserName());
        audit.setIdd(user.getIdd());
    }

    /**
     * Devuelve el user vinculado a la sesi�n que ejecuta esta acci�n.
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

    protected void setGrantRequired(String grantRequired) {
        this.grantRequired = grantRequired;
    }

    public MidMenu getMainMenu() {
        return mainMenu;
    }

    public MidMenu getUserMenu() {
        return userMenu;
    }
    
    public void setLopdLogService (ILOPDLogger lopdLogService) {
    	audit = lopdLogService;
    }
}
