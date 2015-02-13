/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

/**
 *
 * @author j2ee.salud
 */
public interface ILOPDLogger {

    /**
     * Reasigna el nombre de usuario.
     */
    void setUser(String user);

    void setIdd(String idd);
    
    /**
     * Registra un debug en el log LOPD.
     */
    public void log(String _oper, String _entidad, String _pk, String _data);
    
    
    
}
