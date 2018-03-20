/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.dao;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.logging.Logger;
import java.util.List;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Carlos
 */
public class GrantsLoader {
    private Logger log = new Logger();
    
    public Set<String> grantLdapRole(String ldapRole) {
        try {
            IGrantsDAO dao = (IGrantsDAO) new InitialContext().lookup("java:module/GrantsDAO");
            return dao.grantsByLdapRole(ldapRole);
        } catch (NamingException ne) {
            log.error("Imposible obtener GrantsDAO en MidUser", ne);
            return null;
        }
    }    
    
    public Set<String> obtainGrants(String userName) {
       try {
            IGrantsDAO dao = (IGrantsDAO) new InitialContext().lookup("java:module/GrantsDAO");
            return dao.grantsByUser(userName);
        } catch (NamingException ne) {
            log.error("Imposible obtener GrantsDAO en MidUser", ne);
            return null;
        }
    }    
    
    
    public List<MidContext> obtainContexts(String userName) {
        try {
            ContextsDAO dao = (ContextsDAO) new InitialContext().lookup("java:module/ContextsDAO");
            return dao.contextsByUser(userName);
        } catch (NamingException ne) {
            log.error("Imposible obtener ContextsDAO en MidUser", ne);
            return null;
        }

    }
    
    public MidRole getRoleByLdap(String ldapRole) {
        try {
            IGrantsDAO dao = (IGrantsDAO) new InitialContext().lookup("java:module/GrantsDAO");
            log.debug("Recuperando role para ldap/cat: " + ldapRole);
            MidRole r = dao.getRoleByLdap(ldapRole);
            if (r != null) {
                log.debug("Rol recuperado:  " + r.getRoleId());
            }
            return r;
        } catch (NamingException ne) {
            log.error("Imposible obtener GrantsDAO en MidUser", ne);
            return null;
        }    	
    }
    
}
