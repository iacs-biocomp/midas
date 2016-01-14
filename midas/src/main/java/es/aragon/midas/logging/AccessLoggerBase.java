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
     * Registra una autenticaci�n fallida
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
    abstract public void setUser(String user) ;

    /**
     * @param ip the ip to set
     */
    @Override
    abstract public void setIp(String ip);
    
    /**
     * Efectúa el registro en la tabla MID_LOGIN
     * @param cod 
     */
    abstract protected void register(String cod) throws MidasJPAException;
    
}
