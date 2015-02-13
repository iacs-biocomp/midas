package es.aragon.midas.logging;

import es.aragon.midas.config.MidLogin;
import es.aragon.midas.dao.AccessLoggerDAO;
import es.aragon.midas.exception.MidasJPAException;
import java.util.Calendar;
import javax.naming.InitialContext;


/**
 * Logger para registrar los accesos e intentos de acceso al sistema
 * @author carlos
 */
public class AccessLoggerDB implements IAccessLogger{

    private String user;
    private String ip;
    
    /**
     * Constructor
     */
    public AccessLoggerDB() {
    }
    

    /**
     * Registra un error no controlado
     */
    @Override
    public void error() throws Exception {
        register("ERROR");
    }

    /**
     * Registra un username o password en blanco
     */
    @Override
    public void blank() throws Exception {
        register("BLANCO");
    }

    /**
     * Registra una autenticación fallida
     */
    @Override
    public void fail() throws Exception {
        register("PASSWD");
    }

    /**
     * Registra un acceso correcto
     */
    @Override
    public void access() throws Exception {
        register("ACCESO");
    }

    /**
     * Registra un intento de acceso no autorizado
     */
    @Override
    public void noAutorizado() throws Exception {
        register("NO_AUT");
    }
    
    /**
     * Registra una salida de la aplicación
     */
    @Override
    public void exit() throws Exception {
        register("EXIT");
    }
    
    
    /**
     * @param user the user to set
     */
    @Override
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param ip the ip to set
     */
    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    /**
     * Efectúa el registro en la tabla MID_LOGIN
     * @param cod 
     */
    private void register(String cod) throws MidasJPAException {
        MidLogin ml = new MidLogin();
        ml.setLgDate(Calendar.getInstance().getTime());
        ml.setLgIp(ip);
        ml.setLgUser(user);
        ml.setLgCod(cod);
        try {
            AccessLoggerDAO dao = (AccessLoggerDAO) new InitialContext().lookup("java:module/AccessLoggerDAO");
            dao.persist(ml);
        } catch (Exception e) {
            throw new MidasJPAException("Imposible registrar acceso", e);
        }
    }    
}
