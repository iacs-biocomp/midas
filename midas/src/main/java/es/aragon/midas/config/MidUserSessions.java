package es.aragon.midas.config;

import java.util.ArrayList;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import es.aragon.midas.logging.Logger;

/**
 * Esta clase encapsula todas las sesiones de usuario activas en una máquina. 
 * En cada sesión, se guarda el objeto "User" del usuario que ha abierto la sesión,
 * junto con otra información útil para el análisis de accesos y usuarios activos.
 * 
 * @author carlos
 *
 */
public class MidUserSessions {

	/**
	 * HashMap con las sesiones activas en el sistema (una por usuario)
	 */
	private final static HashMap<String,Object> sessions = new HashMap<String,Object>();
	
    Logger log = new Logger();
	private static MidUserSessions instance;

	
	/**
	 * Devuelve el hashmap con las sesiones activas en el sistema
	 * @return
	 */
	public HashMap<String,Object> getUserSessionsMap() {
		return sessions;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static MidUserSessions getInstance() {
		if (instance == null)
			instance = new MidUserSessions();
		return instance;
	}
	
	/**
	 * Activa una sesión de un usuario
	 * 
	 * @param username
	 * @param user
	 */
	public void setUserSession(String username, MidUser user) {
		sessions.put(username, user);
	}
	
	/**
	 * Devuelve la sesión de un usuario indicado
	 * 
	 * @param username
	 * @return
	 */
	public MidUser getUserSession(String username) {
		return (MidUser) sessions.get(username);
	}

	
	/**
	 * Elimina una sesión de usuario de la lista de sesiones activas
	 * @param username
	 */
	public void removeUserSession(String username) {
		sessions.remove(username);
	}
	
	
	/**
	 * Devuelve todas las sesiones activas en el sistema
	 * @return
	 */
	public List<MidUser> getAllUserSessions() {
		List<MidUser> ses = new ArrayList<MidUser>(); 
		sessions.forEach((k,v) -> ses.add((MidUser)v));
		return ses;
	}
	
	

	/**
	 * Devuelve un token para el usuario solicitante
	 * @param userName Nombre del usuario que solicita el token
	 * @return el token solicitado, incluido timestamp
	 */
	public String getUserCode(String userName) {
		if (!sessions.containsKey(userName))
			return "Invalid Session";
		
		MidUser ses = (MidUser)sessions.get(userName);

		log.debug("Usuario rescatado: " + ses.getUserName());
		String s = userName + "|" + ses.getLastLogin().getTime() + "|" + GregorianCalendar.getInstance().getTimeInMillis();
		log.debug("Code: " + s + "-" + Base64.getEncoder().encodeToString(s.getBytes()));
		return Base64.getEncoder().encodeToString(s.getBytes());
	}
	
	
	/**
	 * Devuelve un MidUser a partir de un código token. Si el usuario no tiene sesión abierta, los datos no se corresponden con la sesión abierta,
	 * o el token tiene más de 10 minutos, devuelve un user con el nombre "Usuario no autorizado".
	 * 
	 * @param code token a validar
	 * @return El objeto usuario que ha generado el token
	 */
	public MidUser getUserByCode(String code) {

		String scode = new String(Base64.getDecoder().decode(code));
		log.trace(scode);
	    String[] result = scode.split("\\|");

	    log.trace("Solicitud de datos de " + result[0] + "|" + result[1] + "|" + result[2]);
		MidUser ses = (MidUser)sessions.get(result[0]);
	    log.trace("Recuperada sesion de usuario " + ses.getUserName());

		// En modo DEBUG no validamos el token
		if(!AppProperties.getParameter(Constants.DEBUG_MODE).equals("true")) {
			if (ses != null) {
				if ( Long.parseLong(result[1]) == ses.getLastLogin().getTime() ) {
						if (Long.parseLong(result[2]) - GregorianCalendar.getInstance().getTimeInMillis() < 300000 ) {
						} else {
							ses = new MidUser();
							ses.setUserName("Usuario no autorizado");
							log.trace("Tiempo excedido");
						}
				} else {
					ses = new MidUser();
					ses.setUserName("Usuario no autorizado");
					log.trace("No coincide lastLogin: " + result[1] + "<->" + ses.getLastLogin().getTime());
				}
			} else {
				log.trace("Usuario no recuperado");
				ses = new MidUser();
				ses.setUserName("Usuario no autorizado");
			}
		}
		return ses;
	}	

}
