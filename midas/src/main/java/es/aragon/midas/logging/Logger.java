package es.aragon.midas.logging;

import java.util.Calendar;
import org.apache.log4j.MDC;

/**
 * Wrapper de log4j para MIDAS. Encapsula el login de log4j, automatizando la
 * salida de usuario e ip. Modo de uso: Se crea un nuevo log con alguno de los
 * constructores definidos, y se utiliza igual que los logs propios de log4j
 * (warn, debug, info...) La aplicacion que lo use ha de haber ejecutado el
 * SetupServlet para su inicializacion
 */
public class Logger {

    private org.apache.log4j.Logger myLog;

    public Logger() {
        myLog = org.apache.log4j.Logger.getLogger("debuglog");
        MDC.put("id_ejecucion", Calendar.getInstance().getTimeInMillis());
    }

    public Logger(String clase) {
        myLog = org.apache.log4j.Logger.getLogger(clase);
        MDC.put("id_ejecucion", Calendar.getInstance().getTimeInMillis());
    }

    /**
     * Reasigna el nombre de usuario.
     */
    public void setUser(String user) {
        MDC.put("username", user);
    }

    /**
     * Asigna en el MDC la clave primaria de la entidad
     * @param pk 
     */
    public void setPK(String pk) {
        MDC.put("pk", pk);
    }
    
    
    /**
     * Registra un mensaje de info.
     */
    public void info(Object o) {
        myLog.info(o);
    }

    /**
     * Registra un mensaje de error.
     */
    public void error(Object o) {
        myLog.error(o);
    }

    /**
     * Registra un mensaje de depuracion.
     */
    public void debug(Object o) {
        myLog.debug(o);
    }

    /**
     * Registra un mensaje de traza.
     */
    public void trace(Object o) {
        myLog.trace(o);
    }

    /**
     * Registra un mensaje de advertencia.
     */
    public void warn(Object o) {
        myLog.warn(o);
    }

    /**
     * Registra un mensaje de error grave.
     */
    public void fatal(Object o) {
        myLog.fatal(o);
    }

    /**
     * Registra un mensaje de error y su StackTrace.
     */
    public void error(Object o, Throwable e) {
        myLog.error(o, e);
    }

    /**
     * Registra un mensaje de error grave y su StackTrace.
     */
    public void fatal(Object o, Throwable e) {
        myLog.fatal(o, e);
    }

    /**
     * Registra un mensaje de advertencia y su StackTrace.
     */
    public void warn(Object o, Throwable e) {
        myLog.warn(o, e);
    }
}
