package es.aragon.midas.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.ILOPDLogger;
import es.aragon.midas.logging.Logger;

public abstract class MidasRestAbstractController {

	/**
    *
    */
    protected Logger log = new Logger();
    
    @Inject
    protected ILOPDLogger audit;
    
    @Context
    protected HttpServletRequest request;

    protected MidUser user = null;
    private String grant = "PUBLIC";
    
    public MidasRestAbstractController() {
    }
	
    

    
    protected void setGrantRequired(String grant) {
    	this.grant = grant;
    }
    
    
    /**
     * 
     */
    protected boolean setUser() {
    	boolean isValid = true;
    	user = (MidUser) request.getSession().getAttribute(Constants.USER);

        if (user == null) {
        	isValid = false;

        } else {

        	log.setUser(user.getUserName());
        	log.setAction(request.getPathInfo());
        	if (!user.isGranted(grant)) {
        		isValid =  false;
        		user = null;
        	}
        audit.setUser(user.getUserName());
        audit.setIdd(user.getIdd());

        }
        return isValid;
    }
    

}
