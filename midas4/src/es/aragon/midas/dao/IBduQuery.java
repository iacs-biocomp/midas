/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.dao;

import es.aragon.midas.vo.IPerson;

/**
 *
 * @author Carlos
 */
public interface IBduQuery {
 
    public IPerson getByCIA(String pCia);
    public IPerson getByDNI(String pDni);
}
