/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.ws.guia;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name="guiaWS")
public class InfoUserResponse {
    private String service;
    private String result;
    private String msg;
    private InfoUserDetails infoUser;
    
    
    public InfoUserResponse() {
    	
    }
    
    public InfoUserResponse(String result, String msg) {
    	this.result = result;
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
    @XmlElement(name="infoUser")
    public InfoUserDetails getInfoUser() {
        return infoUser;
    }

    /**
     * @param authGuia the authGuia to set
     */
    public void setInfoUser(InfoUserDetails infoUser) {
        this.infoUser = infoUser;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

       
}
