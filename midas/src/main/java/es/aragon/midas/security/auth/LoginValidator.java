package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;

public interface LoginValidator {

	/**
	 * Método de autenticación para sistemas de validación por
	 * usuario/contraseña
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public MidUser authenticate(String username, String password);

	/**
	 * Método de autenticación para sistemas de ticketing, SSO, autenticacion
	 * delegada...
	 * 
	 * @param ticket
	 * @return
	 */
	public MidUser authenticate(String ticket);

	/**
	 * Obtiene la excepción obtenida al autenticar
	 * 
	 * @return Objeto con el codigo del error al autenticar
	 */
	public Throwable getException();

}
