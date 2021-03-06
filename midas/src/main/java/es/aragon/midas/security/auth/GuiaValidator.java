package es.aragon.midas.security.auth;



import es.aragon.midas.config.MidUser;
import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.GuiaConnection;


/**
 * LoginValidator a traves de los servicios GUIA.
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
    protected boolean delegatedValidation(String username, String password, MidUser user, boolean checkPassword) {
        boolean retval = false;

        log.trace("Usando GUIAValidator");
        
        if (checkPassword) {

        	try {
		    	GuiaConnection con = new GuiaConnection();
		        AuthGuiaResponse resp = null;
	
		        String response = con.auth(username.toLowerCase(), password);
		        if (response != null) {
		        	log.trace(response);
		            resp = con.xmlMapping(response);
		        }
		        
		        if (resp != null && resp.getResult().equals("OK")) {
		        	retval = true;
		        } else {
		        	log.error("GUIA: Resultado no OK. " + response);
		        	retval = false;
		        }

        	} catch(Exception e) {
		        log.error("Error conectando a GUIA.", e);
		        retval = false;
		    }
        
        } else {
        	retval = true;
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
