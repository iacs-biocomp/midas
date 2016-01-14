package es.aragon.midas.security.auth;

import javax.naming.InitialContext;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;

/**
 * LoginValidator que unicamente comprueba que username y password son no nulos.
 * Para pruebas b�sicas de autenticaci�n.
 *
 * @author carlos
 *
 */
public abstract class LoginValidatorBase implements LoginValidator {

    Logger log = new Logger();
    MidUser savedUser;  
    
    //@Override
    @Override
    public MidUser authenticate(String username, String password) {
        UsersDAO dao; 

        
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
                
                if (!specificValidation(username, password)) {
                	savedUser = null;
                }
                
            } catch (Exception e) {
                log.error("Error autenticando usuario ", e);
                return null;
            }
          return savedUser;
        }
    }

    @Override
    public MidUser authenticate(String ticket) {
        return null;
    }


    protected abstract boolean specificValidation(String username, String password);

}


