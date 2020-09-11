package es.aragon.midas.security.auth;

import javax.naming.InitialContext;

import org.jboss.resteasy.util.Encode;
import java.util.Base64;
import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dao.UsersDAO;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.util.StringUtils;

/**
 * LoginValidator que unicamente comprueba que username y password son no nulos.
 * Para pruebas basicas de autenticacion.
 *
 * @author carlos
 *
 */
public abstract class LoginValidatorBase implements LoginValidator {

    Logger log = new Logger();
    MidUser savedUser;
    
    public MidUser authenticate(String username, String password) {
        UsersDAO dao; 


        // Si la propiedad  'ignorepass' es nula o FALSE, debemos verificar el password 
        boolean checkPassword = (AppProperties.getParameter(Constants.CFG_AUTH_IGNOREPASS) == null
        		|| AppProperties.getParameter(Constants.CFG_AUTH_IGNOREPASS).equals(Constants.FALSE));
        
        
        // Si usuario o contraseña son nulos, el procedimiento no es válido
        if (StringUtils.nb(username) || StringUtils.nb(password)) {
            return null;
            
        } else {
            try {
                dao = (UsersDAO) new InitialContext().lookup("java:module/UsersDAO");
                // Buscamos el usuario en la B.D. Si no existe previamente, creamos un usuario básico
                savedUser = dao.find(username);
                if (savedUser == null) {// el usuario no existia previamente
                    savedUser = new MidUser();
                    savedUser.setActive('Y');
                    savedUser.setUserName(username);
                    savedUser.setName(username);
                    savedUser.setAuthMode('S');
                    dao.create(savedUser);
                }
                
                // Si no autentica, devolvemos NULL
                if (!delegatedValidation(username, password, savedUser, checkPassword)) {
                	byte[] encodedBytes = Base64.getEncoder().encode(("cred" + password).getBytes());
            		log.trace(new String(encodedBytes));
                	savedUser = null;
                }
                
            } catch (Exception e) {
                log.error("Error autenticando usuario ", e);
                return null;
            }
          return savedUser;
        }
    }


    // Por defecto, authenticate a partir de ticket, devuelve nulo
    public MidUser authenticate(String ticket) {
        return null;
    }

    // Declaración abstracta del método de validación por usuario y password
    protected abstract boolean delegatedValidation(String username, String password, MidUser user, boolean checkPassword);

}



