package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;

public interface LoginValidator {

	/**
         * Método de autenticación para sistemas de validación por usuario/contraseña
         * @param username
         * @param password
         * @return 
         */
        MidUser authenticate(String username, String password);
        
        /**
         * Método de autenticación para sistemas de ticketing, SSO, autenticacion delegada...
         * @param ticket
         * @return 
         */
	MidUser authenticate(String ticket);
       
	
}
