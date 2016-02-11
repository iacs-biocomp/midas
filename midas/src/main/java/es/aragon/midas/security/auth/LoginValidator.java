package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;

public interface LoginValidator {

	/**
	 * M�todo de autenticaci�n para sistemas de validaci�n por
	 * usuario/contrase�a
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public MidUser authenticate(String username, String password);

	/**
	 * M�todo de autenticaci�n para sistemas de ticketing, SSO, autenticacion
	 * delegada...
	 * 
	 * @param ticket
	 * @return
	 */
	public MidUser authenticate(String ticket);

	/**
	 * Obtiene la excepci�n obtenida al autenticar
	 * 
	 * @return Objeto con el codigo del error al autenticar
	 */
	public Throwable getException();

}
