/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.dao;

import es.aragon.midas.config.MidLogin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
@Stateless
public class AccessLoggerDAO {
    
    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();    
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;    
    
    
    public void persist(MidLogin ml) {
        midasEntityManager.persist(ml);
    }
    
}
