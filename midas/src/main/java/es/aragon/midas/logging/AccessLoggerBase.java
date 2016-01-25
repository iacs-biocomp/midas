/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

import es.aragon.midas.exception.MidasJPAException;

/**
 *
 * @author j2ee.salud
 */
public abstract class AccessLoggerBase implements IAccessLogger{
    
    public AccessLoggerBase() {
    }
    /**
     * Registra un error no controlado
     */
    public void error() throws Exception {
        register("ERROR");
    }

    /**
     * Registra un username o password en blanco
     */
    public void blank() throws Exception {
        register("BLANCO");
    }

    /**
     * Registra una autenticaci�n fallida
     */
    public void fail() throws Exception {
        register("PASSWD");
    }

    /**
     * Registra un acceso correcto
     */
    public void access() throws Exception {
        register("ACCESO");
    }

    /**
     * Registra un intento de acceso no autorizado
     */
    public void noAutorizado() throws Exception {
        register("NO_AUT");
    }
    
    
    
    /**
     * Registra una salida de la aplicación
     */
    public void exit() throws Exception {
        register("EXIT");
    }
    
    
    /**
     * @param user the user to set
     */
    abstract public void setUser(String user) ;

    /**
     * @param ip the ip to set
     */
    abstract public void setIp(String ip);
    
    /**
     * Efectúa el registro en la tabla MID_LOGIN
     * @param cod 
     */
    abstract protected void register(String cod) throws MidasJPAException;
    
}
