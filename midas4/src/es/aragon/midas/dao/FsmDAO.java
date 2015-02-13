/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.dao;

import es.aragon.midas.config.MidFsmEvents;
import es.aragon.midas.logging.Logger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
@Stateless
public class FsmDAO {
    @PersistenceContext(unitName = "midas4")
    private EntityManager em;
    static Logger log = new Logger();
    
    
    public List<MidFsmEvents> getEvents(){
        @SuppressWarnings("unchecked")
        List <MidFsmEvents> list = em.createNamedQuery("MidFsmEvents.findAll")
                        .getResultList();
        return list;
    }
}
