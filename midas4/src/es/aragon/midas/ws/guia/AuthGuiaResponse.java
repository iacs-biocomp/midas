/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.ws.guia;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name="guiaWS")
public class AuthGuiaResponse {
    private String result;
    private String msg;
    private String service;
    
    private AuthGuiaDetails authGUIA;
    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the authGuia
     */
    public AuthGuiaDetails getAuthGUIA() {
        return authGUIA;
    }

    /**
     * @param authGuia the authGuia to set
     */
    public void setAuthGUIA(AuthGuiaDetails authGuia) {
        this.authGUIA = authGuia;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }
        
}
