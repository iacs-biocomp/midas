package es.aragon.midas.logging;


/**
 * Logger para registrar los accesos e intentos de acceso al sistema
 * @author j2ee.salud
 */
public interface IAccessLogger {
    /**
     * Registra un error no controlado
     */
    public void error() throws Exception;

    /**
     * Registra un username o password en blanco
     */
    public void blank() throws Exception;
    /**
     * Registra una autenticación fallida
     */
    public void fail() throws Exception;

    /**
     * Registra un acceso correcto
     */
    public void access() throws Exception;

    /**
     * Registra un intento de acceso no autorizado
     */
    public void noAutorizado() throws Exception;
    
    /**
     * Registra una salida de la aplicación
     */
    public void exit() throws Exception;
    
    
    /**
     * @param user the user to set
     */
    public void setUser(String user);

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip);
        
}
