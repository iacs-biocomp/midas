/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.ws.bdu;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.vo.IPerson;
import java.net.URL;

/**
 *
 * @author Carlos
 */
public class BduQuery implements es.aragon.midas.dao.IBduQuery {
    ConsultaBDU_Service servicio;
    GetByCIAElement cia = new GetByCIAElement();
    GetByCIAResponseElement respcia;
    GetByDNIElement dni = new GetByDNIElement();
    GetByDNIResponseElement respdni;
    
    public BduQuery() {
    	try{
        	URL url = new URL(AppProperties.getParameter("midas.bduws.url"));
        	servicio = new ConsultaBDU_Service(url);
    	} catch (Exception e) {
    		servicio = new ConsultaBDU_Service();
    	}
    }
    
    /**
     *
     * @param pCia
     * @return
     */
    @Override
    public IPerson getByCIA(String pCia){
        cia.setCIA(pCia);
        respcia = servicio.getConsultaBDUSoapHttpPort().getByCIA(cia);
        IPerson persona = respcia.getResult();
        return persona;
    }
    
    
    /**
     * 
     * @param pDni
     * @return 
     */
    @Override
    public IPerson getByDNI(String pDni) {
        dni.setDni(pDni);
        respdni = servicio.getConsultaBDUSoapHttpPort().getByDNI(dni);
        IPerson persona = respdni.getResult();
        return persona;
    }
    
}
