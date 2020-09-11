/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.action;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.util.StringUtils;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author j2ee.salud
 */
public class LoggerAction extends MidasActionSupport {

    private static final long serialVersionUID = 1L;
    private static final String LOGLEVEL_PROPERTY = "LOGLEVEL";
    private Map<String, String> loggers;
    private String logName;
    private String logLevel;
   

    public String get() {

    	loggers = getActiveLoggers();
        if (loggers == null || loggers.isEmpty()) {
            addActionError("No se encontraron loggers");
            return INPUT;
        }

        // Si no especificamos logLevel, leemos el logLevel de properties
        if (logLevel == null) {
            AppProperties.reload();
            logLevel = AppProperties.getParameter(LOGLEVEL_PROPERTY);
        }

        return INPUT;
    }

    public String list() {

    	loggers = getActiveLoggers();
        if (loggers == null || loggers.isEmpty()) {
            addActionError("No se encontraron loggers");
            return INPUT;
        }

        // Si no especificamos logLevel, leemos el logLevel de properties
        if (logLevel == null) {
            AppProperties.reload();
            logLevel = AppProperties.getParameter(LOGLEVEL_PROPERTY);
        }

        return INPUT;
    }

    
    
    
    /* Beans de presentación */
    public Map<String, String> getLoggers() {
        return loggers;
    }
    
    public void setLoggers(Map<String, String> loggers) {
        this.loggers = loggers;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    private Map<String,String> getActiveLoggers(){
        Map<String,String> loggers_ = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
		Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
        while (allLoggers.hasMoreElements()) {
            Logger logger = (Logger) allLoggers.nextElement();
            // only show loggers that have appenders attached to them
            if (logger.getAllAppenders().hasMoreElements()) {
                loggers_.put(logger.getName(), logger.getName() + " (" + logger.getLevel() + ")");
            }
        }
        return loggers_;
    }    
    
    
    
    /* Metodos de negocio */
    private void setLogLevel(String loggerName, String level) {
        if ("debug".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.DEBUG);
        } else if ("info".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.INFO);
        } else if ("error".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.ERROR);
        } else if ("fatal".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.FATAL);
        } else if ("warn".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.WARN);
        } else if ("trace".equalsIgnoreCase(level)) {
            Logger.getLogger(loggerName).setLevel(Level.TRACE);
        }
    }
    

}
