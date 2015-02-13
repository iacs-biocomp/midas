package es.aragon.midas.security.auth;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;

/**
 * LoginValidator que autentica contra LDAP.
 *
 * @author carlos
 *
 */
public class LDAPValidator implements LoginValidator {

    Logger log = new Logger();

	@Override
    public MidUser authenticate(String username, String password) {
    	UsersDAO dao;
    	MidUser savedUser;

    	if (StringUtils.nb(username) || StringUtils.nb(password)) {
            return null;
        } else {
            try {
	            dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
	            savedUser = dao.find(username);
	            if (savedUser == null) {// el usuario no existia previamente
	                savedUser = new MidUser();
	                savedUser.setActive('Y');
	                savedUser.setUserName(username);
	                savedUser.setName(username);
	                dao.create(savedUser);
	            }
	            
                DirContext dirCtx;
                String userDN = username + AppProperties.getParameter(Constants.CFG_LDAP_DOMAIN);
                Properties env = new Properties();
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                env.put(Context.PROVIDER_URL, AppProperties.getParameter(Constants.CFG_LDAP_SERVER)); 
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.SECURITY_PRINCIPAL, userDN);
                env.put(Context.SECURITY_CREDENTIALS, password);
                dirCtx = new InitialDirContext(env);

                String[] attrIDs = {"cn"};
                SearchControls ctls = new SearchControls();
                ctls.setReturningAttributes(attrIDs);
                ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
                String usersContainer = "OU=Salud,DC=salud,DC=dga,DC=es";
                NamingEnumeration<SearchResult> answer = dirCtx.search(usersContainer, "(objectclass=group)", ctls);
                while (answer.hasMore()) {
                    SearchResult rslt = (SearchResult) answer.next();
                    Attributes attrs = rslt.getAttributes();
                    System.out.println(attrs.get("cn"));
                }
                dirCtx.close();
                
            } catch (Exception e) {
                log.error("Error conectando a LDAP.", e);
                return null;
            }
            return savedUser;
        }
    }

    @Override
    public MidUser authenticate(String ticket) {
        return null;
    }
}
