package es.aragon.midas.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.Base64;
import es.aragon.midas.util.FileEncoder;

public class LdapUtils {
	private static Logger log;
	static {
		log = new Logger();
	}
	
	/*public static void main (String args[]) throws NamingException{
		getUserLdap("admin-dep@salud.dga.es", "ryc1852!", new FiltroLdap("jimunnoz@salud.aragon.es", null));
		
	}*/
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param filtros
	 * @return
	 * @throws NamingException
	 */
	public static UserLdap getUserLdap(String username, String password, FiltroLdap filtros) throws NamingException{
		LdapContext ctx = null;
		String ldapUrl = AppProperties.getParameter("midas.ldap.server");
		String baseDN = AppProperties.getParameter("midas.ldap.baseDN");
		UserLdap userLdap = null;
		
		// Crea la conexión con LDAP
		try {
			ctx = LdapUtils.getLdapContext(ldapUrl, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setCountLimit(1);

		// Busca si el usuario existe en el LDAP
		String filts = filtros.getFilters();
		NamingEnumeration<SearchResult> resultados = ctx.search(baseDN, filts, searchControls);
		if (resultados.hasMoreElements()) {	
			userLdap = new UserLdap(resultados.next());
		}
		
		return userLdap;
	}
	
	/**
	 * Comprueba si un nombre de usuario existe en el LDAP
	 * 
	 * @param userName
	 *            Nombre de usuario que comprueba en el AD
	 * @return True si el nombre existe, false si no existe
	 * @throws NamingException
	 */
	public static UserLdap getUserLogin(String mail) throws NamingException {
		String validUserName = AppProperties.getParameter("midas.ldap.validUser");
		String validPassword;
		
		try {
			// Desencripta la contraseña en AES recuperada de base de datos que
			// la almacena en Base64
			validPassword = FileEncoder.decryptString(new String(Base64
					.decode(AppProperties.getParameter("midas.ldap.validPassword"))));
		} catch (Exception e) {
			validPassword = "";
			e.printStackTrace();
			log.error("Error al obtener el password válido de la tabla de properties", e);
		}

		FiltroLdap filtros = new FiltroLdap(mail, null);
		UserLdap userLdap= getUserLdap(validUserName, validPassword, filtros);
		
		if(userLdap != null){
			return userLdap;
		}else{
			return null;
		}
	}

	private static LdapContext getLdapContext(String ldapUrl,
			String userPrincipal, String userPwd) throws Exception {
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userPrincipal);
		env.put(Context.SECURITY_CREDENTIALS, userPwd);
		env.put(Context.REFERRAL, "follow");

		return new InitialLdapContext(env, null);
	}

	/**
	 * Obtiene el mensaje de error que devuelve a partir de una excepcion de
	 * LDAP
	 * @param e
	 * 		Excepción obtenida al autenticar contra el LDAP
	 * @return Descripción del error que devuelve el LDAP
	 */
	public static String getDescError(Throwable e) {
		String portal = AppProperties.getParameter("midas.ldap.portal");
		String message = e.getMessage();

		if (message.contains("data 525")) {
			return "Usuario y/o contraseña erróneos";
		}
		if (message.contains("data 52e")) {
			return "Usuario y/o contraseña erróneos";
		}
		if (message.contains("data 530")) {
			return "No se puede acceder en éstos momentos";
		}
		if (message.contains("data 531")) {
			return "No se puede acceder desde éste equipo";
		}
		if (message.contains("data 532")) {
			return "La contraseña ha caducado."
					+ " <br><a href=\""
					+ portal
					+ "\">Diríjase al portal del empleado para cambiar la contraseña</a>";
		}
		if (message.contains("data 533")) {
			return "Cuenta desactivada en el AD";
		}
		if (message.contains("data 534")) {
			return "El usuario no está autorizado para acceder desde éste equipo";
		}
		if (message.contains("data 701")) {
			return "Cuenta caducada";
		}
		if (message.contains("data 773")) {
			return "Se necesita cambiar la contraseña."
					+ " <br><a href=\""
					+ portal
					+ "\">Diríjase al portal del empleado para cambiar la contraseña</a>";
		}
		if (message.contains("data 775")) {
			return "La cuenta ha sido bloqueada";
		}

		return "Error desconocido";
	}

}
