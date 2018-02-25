package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;

public interface LoginValidator {

	/**
	 * Metodo de autenticacion para sistemas de validacion por
	 * usuario/contraseña
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public MidUser authenticate(String username, String password);

	/**
	 * Metodo de autenticacion para sistemas de ticketing, SSO, autenticacion
	 * delegada...
	 * 
	 * @param ticket
	 * @return
	 */
	public MidUser authenticate(String ticket);

	/**
	 * Obtiene la excepcion obtenida al autenticar
	 * 
	 * @return Objeto con el codigo del error al autenticar
	 */
	public Throwable getException();

}
