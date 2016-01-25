package es.aragon.midas.security.auth;

import javax.naming.InitialContext;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;

/**
 * LoginValidator que unicamente comprueba que username y password son no nulos.
 * Para pruebas básicas de autenticación.
 *
 * @author carlos
 *
 */
public class NullValidator implements LoginValidator {

    Logger log = new Logger();
    

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
            } catch (Exception e) {
                log.error("Error autenticando usuario NullValidator", e);
                return null;
            }
          return savedUser;
        }
    }

    public MidUser authenticate(String ticket) {
        return null;
    }
}
