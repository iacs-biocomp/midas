/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

import javax.enterprise.inject.Alternative;

import org.apache.log4j.MDC;

/**
 *
 * @author j2ee.salud
 */
@Alternative
public class AccessLoggerFile extends AccessLoggerBase {
    
    private org.apache.log4j.Logger logAccceso;

    public AccessLoggerFile() {
       logAccceso = org.apache.log4j.Logger.getLogger("acceso");
    }

    
    
    /**
     * @param user the user to set
     */
    @Override
    public void setUser(String user) {
        MDC.put("username", user);
    }


    
    /**
     * @param ip the ip to set
     */
    @Override
    public void setIp(String ip) {
        MDC.put("ip", ip);
    }
    
    
    
    /**
     * Efectúa el registro en la tabla MID_LOGIN
     * @param cod 
     */
    protected void register(String cod) {
        logAccceso.debug(cod);
    }
    
}
