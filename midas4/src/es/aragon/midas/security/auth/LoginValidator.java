package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;

public interface LoginValidator {

	/**
         * M�todo de autenticaci�n para sistemas de validaci�n por usuario/contrase?a
         * @param username
         * @param password
         * @return 
         */
        MidUser authenticate(String username, String password);
        
        /**
         * M�todo de autenticaci�n para sistemas de ticketing, SSO, autenticacion delegada...
         * @param ticket
         * @return 
         */
	MidUser authenticate(String ticket);
       
	
}
