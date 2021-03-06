/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.action;

import com.opensymphony.xwork2.ActionSupport;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

/**
 *
 * @author j2ee.salud
 */
public class LoggerLevelChangerAction extends MidasActionSupport {

    private static final long serialVersionUID = 1L;
    private static final String LOGLEVEL_PROPERTY = "LOGLEVEL";
    private Map<String, String> loggers;
    private String logName;
    private String logLevel;
    private InputStream fileInputStream;
    
    
    @Override
    public String execute() {
    	return change();
    }
    
    
    public String change() {
        // Si especificamos logName y logLevel, modificamos ese log puntualmente
        if (!StringUtils.nb(logName) && !StringUtils.nb(logLevel)) {
            setLogLevel(logName, logLevel);
            addActionMessage("Nivel del log '" + logName + "' fijado a " + logLevel);
            loggers = getActiveLoggers();
            return INPUT;
        }
        
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


    public String download() {
    	log.debug("Download log");
    	File logFile = null;
    	Logger logger = null;

    	@SuppressWarnings("unchecked")
    	Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
        
    	while (allLoggers.hasMoreElements()) {
            Logger l = (Logger) allLoggers.nextElement();
            // only show loggers that have appenders attached to them
            if (l.getAllAppenders().hasMoreElements()) {
        		log.debug(l.getName());
        		logger = l;
            }
    	}    	
    	
    	@SuppressWarnings("unchecked")
		Enumeration<Appender> appenders = logger.getAllAppenders();
        while (appenders.hasMoreElements()) {
            Appender appender = (Appender) appenders.nextElement();
            log.debug(appender.getName());
            // only show loggers that have appenders attached to them
            if (appender instanceof FileAppender) {
            	log.debug("Appender es FileAppender");
            	logFile = new File( ( (FileAppender) appender).getFile());
            	try {
            		fileInputStream = new FileInputStream(logFile);
            	} catch (Exception e) {
            		Log.error("No se ha encontrado el fichero de log ", e);            	
            		return ERROR;
            	}
            }
        }   	
    	return "download";
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
    
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	
}
