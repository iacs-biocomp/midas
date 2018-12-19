package es.aragon.midas.security.auth;

import java.util.ListIterator;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.ldap.FiltroLdap;
import es.aragon.midas.ldap.LdapUtils;
import es.aragon.midas.ldap.UserLdap;

import javax.naming.NamingException;

/**
 * LoginValidator que autentica contra LDAP.
 * 
 * @author carlos
 * 
 */
public class LDAPValidator extends LoginValidatorBase {

	private Throwable ldapException;

	/**
	 * Validación contra LDAP
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	protected boolean specificValidation(String username, String password, boolean checkPassword) {
		// Borra anteriores excepciones del LDAP
		ldapException = null;

		boolean retval = false;
		String userDN = username + AppProperties.getParameter(Constants.CFG_LDAP_DOMAIN);
		
		try {
			FiltroLdap filtros = new FiltroLdap(null, username.toLowerCase());
			UserLdap userByLdap = null;
			
			if (checkPassword) {
				userByLdap = LdapUtils.getUserLdap(userDN, password, filtros);
				log.debug("Validando al usuario " + username);
				
				if (userByLdap != null)
					retval = true;

			} else {
				//userByLdap = LdapUtils.getUserByName(username);
				retval = true;
			} 
			
			
/*			No tomamos roles ni permisos desde LDAP directamente. si fuera necesario, los cogeríamos desde GUIA
 * 
 * 			ListIterator<String> it = userByLdap.getGroupsLDAP().listIterator();
			while(it.hasNext()){
				String m = it.next();
				log.debug("ROL COGIDO DE LDAP: " + m);
				savedUser.grantLdapRole(m);
			}
*/		
		} catch (NamingException ne) {
			log.error("Error conectando a LDAP.", ne);
			retval = false;
			ldapException = ne;
		}

		return retval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.aragon.midas.security.auth.LoginValidator#getException()
	 */
	public Throwable getException() {
		return ldapException;
	}

}
