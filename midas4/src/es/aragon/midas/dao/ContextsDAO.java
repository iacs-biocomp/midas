/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.logging.Logger;

/**
 *
 * @author j2ee.salud
 */
@Stateless
public class ContextsDAO {
        @PersistenceContext(unitName="midas4")
        private EntityManager midasEntityManager;

	static Logger log = new Logger();                
        
    public List<MidContext> contextsByUser(String username) {
        try {
            String sql = "SELECT co.*" +
                    "  FROM mid_contexts co, mid_user_context uc, mid_users us" +
                    " WHERE co.cx_id = uc.cx_id" +
                    "   AND us.user_name = uc.user_name" +
                    "   AND us.user_name = ?" +
                    " UNION " +
                    " SELECT co.*" +
                    "  FROM mid_contexts co, mid_role_context rc, mid_userroles ur, mid_users us" +
                    " WHERE co.cx_id = rc.cx_id" +
                    "   AND us.user_name = ur.ur_name" +
                    "   AND rc.role_id = ur.ur_role" +
                    "   AND us.user_name = ?";
            Query query = midasEntityManager.createNativeQuery(sql,MidContext.class)
                    .setParameter(1, username)
                    .setParameter(2, username);

            @SuppressWarnings("unchecked")
            List<MidContext> contexts = (List<MidContext>) query.getResultList();
            return contexts;
        } catch (Exception e) {
            log.error("Error obteniendo contextos de usuario", e);
            return null;
        }
    }
    
	public List<MidContext> findAll () {
		Query query = midasEntityManager.createNamedQuery("MidContext.findAll");
		@SuppressWarnings("unchecked")
		List<MidContext> contexts = query.getResultList();		
		return contexts;
	}

	public MidContext findByCxId(Integer cxId) {
		Query query = midasEntityManager.createNamedQuery("MidContext.findByCxId");
		MidContext context = (MidContext) query.setParameter("cxId",cxId).getSingleResult();		
		return context; 
	}
	
	public void save(MidContext context){
		midasEntityManager.merge(context);
	}

	public void delete(MidContext context) {
		midasEntityManager.remove(midasEntityManager.merge(context));
	}
}
