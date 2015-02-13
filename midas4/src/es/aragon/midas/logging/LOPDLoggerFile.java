/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

/**
 *
 * @author j2ee.salud
 */
public class LOPDLoggerFile implements ILOPDLogger {

    String user = "";
    String idd = "";
    private org.apache.log4j.Logger logLOPD;
    
    public LOPDLoggerFile(){
        this.logLOPD = org.apache.log4j.Logger.getLogger("lopdlog");
    }
    

    /**
     * Reasigna el nombre de usuario.
     */
    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void setIdd(String idd) {
        this.idd = idd;
    }

    /**
     * Registra un debug en el log LOPD.
     */
    @Override
    public void log(String key, String desc, String a, String b) {
        logLOPD.debug(key + " | " + desc);
    }
}
