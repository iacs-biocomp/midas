/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.logging;

import javax.enterprise.inject.Alternative;

/**
 *
 * @author j2ee.salud
 */
@Alternative
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
    public void setUser(String user) {
        this.user = user;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    /**
     * Registra un debug en el log LOPD.
     */
    public void log(String key, String desc, String a, String b) {
        logLOPD.debug(key + " | " + desc);
    }
}
