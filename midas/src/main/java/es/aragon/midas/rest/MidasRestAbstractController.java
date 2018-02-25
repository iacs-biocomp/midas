package es.aragon.midas.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.MDC;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.Logger;

public abstract class MidasRestAbstractController {

	/**
    *
    */
    protected Logger log = new Logger();
    
    
	
    @Context
    protected HttpServletRequest request;

    protected MidUser user = null;
    
    
    public MidasRestAbstractController() {
    }
	
    
    /**
     * 
     */
    protected void setUser() {
    	user = (MidUser) request.getSession().getAttribute(Constants.USER);
        MDC.put("action", "rest");
        if (user != null)
        	log.setUser(user.getUserName());
    }
    
}
