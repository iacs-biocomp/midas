/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

import org.apache.log4j.MDC;

/**
 *
 * @author j2ee.salud
 */
public class AccessLoggerFile implements IAccessLogger{
    
    private org.apache.log4j.Logger logAccceso;

    public AccessLoggerFile() {
       logAccceso = org.apache.log4j.Logger.getLogger("acceso");
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
    private void register(String cod) throws Exception {
        logAccceso.debug(cod);
    }
    
}
