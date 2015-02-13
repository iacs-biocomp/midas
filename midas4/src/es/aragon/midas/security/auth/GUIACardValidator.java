package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;
import es.aragon.midas.ws.guia.AuthGuiaDetails;
import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.AuthGuiaTicketResponse;
import es.aragon.midas.ws.guia.GuiaConnection;
import javax.naming.InitialContext;

/**
 *
 * @author j2ee.salud
 */
public class GUIACardValidator implements LoginValidator {

    Logger log = new Logger();
        
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
    
   @Override
    public MidUser authenticate(String username, String password) {

       if (StringUtils.nb(username) || StringUtils.nb(password)) {
            return null;
        } else {
            //UsersDAO dao = new UsersDAO();
            try {
                UsersDAO dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
        
                MidUser savedUser = dao.find(username);
                if (savedUser == null) {// el usuario no existia previamente
                    savedUser = new MidUser();
                    savedUser.setActive('Y');
                    savedUser.setUserName(username);
                    savedUser.setName(username);
                    dao.create(savedUser);				
                }

                GuiaConnection con = new GuiaConnection();
                AuthGuiaResponse resp = null;
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
                    //TODO MidRoles from LDAP
                    //TODO setactive savedUser.setActive(details.);
                    return savedUser;
                } else {
                    return null;
                }
            } catch(Exception e) {
                log.error("Error conectando a GUIA.", e);
                return null;
            }
            
        } 
    }    
    
}
