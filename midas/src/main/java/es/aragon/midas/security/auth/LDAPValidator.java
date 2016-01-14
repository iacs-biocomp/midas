package es.aragon.midas.security.auth;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

/**
 * LoginValidator que autentica contra LDAP.
 *
 * @author carlos
 *
 */
public class LDAPValidator extends LoginValidatorBase {
    
    
    /**
     * Validación contra LDAP
     * @param username
     * @param password
     * @return
     */
    protected boolean specificValidation(String username, String password) {
	    DirContext dirCtx;
	    boolean retval = false;
	    String userDN = username + AppProperties.getParameter(Constants.CFG_LDAP_DOMAIN);
	    Properties env = new Properties();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, AppProperties.getParameter(Constants.CFG_LDAP_SERVER)); 
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, userDN);
	    env.put(Context.SECURITY_CREDENTIALS, password);
	    try {
		    dirCtx = new InitialDirContext(env);
		
		    String[] attrIDs = {"cn"};
		    SearchControls ctls = new SearchControls();
		    ctls.setReturningAttributes(buildAttrFilter(attrIDs));
		    ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		    String usersContainer = "OU=Salud,DC=salud,DC=dga,DC=es";
		    NamingEnumeration<SearchResult> answer = dirCtx.search(usersContainer, "(objectclass=group)", ctls);
		    while (answer.hasMore()) {
		        SearchResult rslt = (SearchResult) answer.next();
		        Attributes attrs = rslt.getAttributes();
		        System.out.println(attrs.get("cn"));
		    }
		    dirCtx.close();
		    retval = true;
	    } catch (NamingException ne) {
            log.error("Error conectando a LDAP.", ne);	    	
	    	retval = false;
	    } 

	    return retval;
    }
    
	/**
	 *  Filter attributes passed to LDAP method
	 * @param s
	 * @return
	 */
    private String[] buildAttrFilter(String[] s){
    	String[] _temp = {"cn"}; 
    	if (s != null) {
    		return s;
    	} else { 
    		return _temp;
    	}
    }    
    
}
