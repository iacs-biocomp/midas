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
public class AccessLoggerDB extends AccessLoggerBase {

    private String user;
    private String ip;
    
    /**
     * Constructor
     */
    public AccessLoggerDB() {
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
     * Efectua el registro en la tabla MID_LOGIN
     * @param cod 
     */
    protected void register(String cod) throws MidasJPAException {
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
