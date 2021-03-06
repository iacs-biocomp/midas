package es.aragon.midas.interceptors;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.config.MidUserSessions;
import es.aragon.midas.logging.Logger;

/**
 * 
 * @author carlos
 *
 */
public class MidasSessionListener implements HttpSessionListener {
    
	private Logger log = new Logger();

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// do nothing
	}

	/**
	 * 
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		MidUserSessions us = MidUserSessions.getInstance();
		MidUser user = (MidUser) arg0.getSession().getAttribute(Constants.USER);
		if (us == null) {
			log.warn("MidUserSession is null");
		} else if (user == null) {
			log.warn("Session destroyed with null user");
		} else {
			log.debug("Eliminando sesión del usuario " + user.getUserName());
			us.removeUserSession(user.getUserName());
		}
	}
	

}
