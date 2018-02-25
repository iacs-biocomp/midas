/**
 *
 */
package es.aragon.midas.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.security.LoginAction;
import es.aragon.midas.security.UserAware;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.MDC;
import org.apache.struts2.StrutsStatics;

/**
 * @author carlos
 *
 */
public class AuthenticationInterceptor implements Interceptor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Logger log = new Logger();
    
    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation)
            throws Exception {
        Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
        MidUser user = (MidUser) session.get(Constants.USER);
        Action action = (Action) actionInvocation.getAction();
        
        boolean needsAuthentication = AppProperties.getParameter(Constants.CFG_AUTHENTICATE).equals("true");

        if (!needsAuthentication) {
            user = new MidUser();
            user.grant("PUBLIC");
            user.setUserName("Anonimo");
            session.put(Constants.USER, user);
            return actionInvocation.invoke();
        }
        
       if (user == null) {
            //The user has not logged in yet
            if (action instanceof LoginAction) {
                // el usuario intenta logarse. Dejamos pasar
                log.debug("Accediendo a login");
                return actionInvocation.invoke();
            } else {
                // Si esta marcada autenticacion por certificado, insertamos la URL en el request
                if (AppProperties.getParameter(Constants.CFG_AUTH_CERT).equals("true")) {
                     String URLAuthGUIACard = AppProperties.getParameter(Constants.URL_GUIA_CARD);
                     HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
                     request.setAttribute("URLAuthGUIACard", URLAuthGUIACard);
                } 
                // redirigimos a login
                return Action.LOGIN;
            }
        } else {
        	if (action instanceof LoginAction) {
        		return Constants.INDEX;
        	}
        	
        	// el usuario esta logado
            if (action instanceof UserAware) {
                ((UserAware) action).setUser(user);
            }
            MDC.put("action", actionInvocation.getInvocationContext().getName());
            log.debug("Accediendo a " + actionInvocation.getInvocationContext().getName());

            if (action instanceof MidasActionSupport) {
                String grantRequired = ((MidasActionSupport) action).getGrantRequired();
                if (!user.isGranted(grantRequired) && !user.isGranted(Constants.SUPER)) {
                    log.debug("Acceso denegado. Requerido: " + grantRequired);
                    return Constants.FORBIDDEN;
                }
            }
        }
        return actionInvocation.invoke();
    }
}
