package es.aragon.midas.util;

import es.aragon.midas.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Utilidades para determinar IPs de conexiones de origen
 * @author carlos
 *
 */
public class IPUtils  {
    static Logger log = new Logger();
    
    /**
     * Devuelve la IP de origen de una conexion HTTP a partir del request
     * @param request
     * @return IP de la conexion
     */
    public static String clientIP(HttpServletRequest request) {
        String xForwarded = request.getHeader("X-FORWARDED-FOR");
        String forwarded = request.getHeader("FORWARDED");
        String via = request.getHeader("VIA");
        String client = request.getHeader("CLIENT-IP");
    
        String dirIP = request.getRemoteAddr();
        if (xForwarded != null) {
            dirIP = xForwarded;
        } else if (forwarded != null) {
            dirIP = forwarded;
        } else if (via != null) {
            dirIP = via;
        } else if (client != null) {
            dirIP = client;
        }
        
        log.debug("Extrayendo IP:" +
            "\n * IP fasica:       " + request.getRemoteAddr() +
            "\n * X-FORWARDED-FOR: " + xForwarded +
            "\n * FORWARDED:       " + forwarded +
            "\n * VIA:             " + via +
            "\n * CLIENT-IP:       " + client
        );
        
        //if (dirIP == null) dirIP = request.getRemoteAddr();
        return dirIP;
    }
}