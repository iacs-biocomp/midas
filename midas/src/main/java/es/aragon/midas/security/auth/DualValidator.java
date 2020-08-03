package es.aragon.midas.security.auth;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.ldap.FiltroLdap;
import es.aragon.midas.ldap.LdapUtils;
import es.aragon.midas.ldap.UserLdap;

import javax.enterprise.inject.Alternative;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * LoginValidator que autentica contra LDAP o base de datos dependiendo del valor de auth_mode.
 * 
 * @author carlos
 */
@Alternative
public class DualValidator extends LoginValidatorBase {
    UsersDAO dao; 
	
	private Throwable ldapException;

	/**
	 * Validación contra LDAP
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	protected boolean delegatedValidation(String username, String password, MidUser user, boolean checkPassword) {
		// Borra anteriores excepciones del LDAP
		ldapException = null;

		boolean retval = false;

		char authMode = user.getAuthMode().charValue();
		
		
		switch (authMode) {

			case 'S':
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
				break;
			
			case 'L': 
	            try {
				    dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
					retval = dao.validateLocalUser(username, password);
	            } catch (Exception e) {
	                log.error("Error accediendo a UsersDAO o validando usuario local ", e);
	                retval = false;
	            }
				break;
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
