package es.aragon.midas.security.auth;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;
import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.GuiaConnection;
import javax.naming.InitialContext;

/**
 * LoginValidator a través de los servicios GUIA.
 * 
 *
 * @author carlos
 *
 */
public class GuiaValidator implements LoginValidator {

    @Override
    public MidUser authenticate(String username, String password) {

        Logger log = new Logger();
        
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
    
    @Override
    public MidUser authenticate(String ticket) {
        return null;
    }    
}
