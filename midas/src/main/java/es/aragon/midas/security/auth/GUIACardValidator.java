package es.aragon.midas.security.auth;

import java.util.List;
import java.util.ListIterator;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.util.StringUtils;
import es.aragon.midas.ws.guia.AuthGuiaDetails;
import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.AuthGuiaTicketResponse;
import es.aragon.midas.ws.guia.GuiaConnection;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.naming.InitialContext;

/**
 *
 * @author j2ee.salud
 */
@Priority(100) @Alternative
public class GUIACardValidator extends LoginValidatorBase {
	
	private Throwable guiaException;
        
    @Override
    public MidUser authenticate(String ticket) {
        MidUser savedUser = null;
        if (StringUtils.nb(ticket)) {
            return null;
        } else {
            try {
                GuiaConnection con = new GuiaConnection();
                String respuesta = con.verifyTicket(ticket);
                if (respuesta != null) {
                    AuthGuiaTicketResponse response = con.xmlMappingTicketAnswer(respuesta);
                    if (response != null && response.getResult().equalsIgnoreCase("OK")) {
                        //UsersDAO dao = new UsersDAO();
                        UsersDAO dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
                        savedUser = dao.find(response.getId().toUpperCase());
                        if (savedUser == null) {// el usuario no existia previamente
                            savedUser = new MidUser();
                            savedUser.setActive('Y');
                            savedUser.setUserName(response.getId());
                            savedUser.setName(response.getId());
                            dao.create(savedUser);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error conectando a GUIA.", e);
                return null;
            }
            return savedUser;
        }
    }
    
    

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    public boolean delegatedValidation(String username, String password, MidUser user, boolean checkPassword) {
    	boolean retval;
    	
    	try {
            GuiaConnection con = new GuiaConnection();
            AuthGuiaResponse resp = null;
    		List<String> LdapRoles;
            String response = con.auth(username.toLowerCase(), password);
            if (response != null) {
                resp = con.xmlMapping(response);
            }
            if (resp != null && resp.getResult().equals("OK")) {
                AuthGuiaDetails details = resp.getAuthGUIA();
                if (details.getName().isEmpty()) {
                	savedUser.setName(details.getLogin());
                } else {
                	savedUser.setName(details.getName());
                }
                savedUser.setLastname1(details.getSurname1());
                savedUser.setLastname2(details.getSurname2());
                savedUser.setIdd(details.getNif());
                // MidRoles from LDAP
                LdapRoles = details.getGroupsLDAPList();
				ListIterator<String> it = LdapRoles.listIterator();
				while(it.hasNext()){
					String m = it.next();
					log.debug("ROL COGIDO DE LDAP: " + m);
					savedUser.grantLdapRole(m);
				}
                
                //TODO setactive savedUser.setActive(details.);
                retval = true;
            } else {
                retval = false;
            }
        } catch(Exception e) {
            log.error("Error conectando a GUIA.", e);
            retval = false;
        }
    	return retval;
    	
    }
    
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.aragon.midas.security.auth.LoginValidator#getException()
	 */
	public Throwable getException() {
		return guiaException;
	}
    
}
