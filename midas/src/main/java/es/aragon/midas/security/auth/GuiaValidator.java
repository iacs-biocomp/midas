package es.aragon.midas.security.auth;

import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.GuiaConnection;


/**
 * LoginValidator a trav�s de los servicios GUIA.
 * 
 *
 * @author carlos
 *
 */
public class GuiaValidator extends LoginValidatorBase {

	private Throwable guiaException;

    
    /**
     * 
     * @param username
     * @param password
     * @return
     */
    protected boolean specificValidation(String username, String password) {
        boolean retval = false;
        try {
	    	GuiaConnection con = new GuiaConnection();
	        AuthGuiaResponse resp = null;
	        String response = con.auth(username.toLowerCase(), password);
	        if (response != null) {
	            resp = con.xmlMapping(response);
	        }
	        
	        if (resp != null && resp.getResult().equals("OK")) {
	        	retval = true;
	        } else {
	        	retval = false;
	        }  
	    } catch(Exception e) {
	        log.error("Error conectando a GUIA.", e);
	        retval = false;
	    }
        
        return retval;
    
    }
  
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.aragon.midas.security.auth.LoginValidator#getException()
	 */
	public Throwable getException() {
		return guiaException;
	}

}
