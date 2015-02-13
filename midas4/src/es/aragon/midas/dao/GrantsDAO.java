package es.aragon.midas.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.logging.Logger;


@Stateless
public class GrantsDAO {

    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();  
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    
        
	static Logger log = new Logger();        
    
	/**
	 * Devuelve un HashSet con todos los permisos de un grupo LDAP
	 * @param role
	 * @return
	 */
	public Set<String> grantsByLdapRole (String role) {
		Set<String> grants = new HashSet<String>(0);

                // selecciona los roles cuyos usuarios asociados (userroles) 
		// tienen por nombre el indicado en el parametro
		try {
			Query query = midasEntityManager.createNamedQuery("findGrantsByLdap")
                                .setParameter("roleLdap", role);
			@SuppressWarnings("unchecked")
			List<MidGrant> gr = query.getResultList();
			for(MidGrant g : gr  ) {
				grants.add(g.getGrId().toUpperCase().trim());
			}
		} catch (Exception e){
                    log.error("Error obteniendo grants de rol LDAP " + role, e);
                }
        	return grants;
	}

	
	/**
	 * Devuelve un HashSet con todos los permisos de un usuario
	 * @param username
	 * @return
	 */
	public Set<String> grantsByUser (String username) {
		Set<String> grants = new HashSet<String>(0);
		try {
			Query query = midasEntityManager.createNamedQuery("findGrantsByUser")
							.setParameter("username", username);
			@SuppressWarnings("unchecked")
			List<MidGrant> gr = query.getResultList();
			for(MidGrant g : gr  ) {
				grants.add(g.getGrId().toUpperCase().trim());
			}
		} catch (Exception e) {
                    log.error("Error obteniendo grants de usuario " + username, e);
		}
		
		return grants;
	}
	
	public List<MidGrant> findAll () {
		Query query = midasEntityManager.createNamedQuery("MidGrant.findAll");
		@SuppressWarnings("unchecked")
		List<MidGrant> grants = query.getResultList();		
		return grants;
	}
	
	public MidGrant findByGrId(String grId){
		Query query = midasEntityManager.createNamedQuery("MidGrant.findByGrId");
		MidGrant grant = (MidGrant) query.setParameter("grId",grId).getSingleResult();		
		return grant; 
	}
	
	public void save(MidGrant grant){
		midasEntityManager.merge(grant);
	}


	public void delete(MidGrant grant) {
		midasEntityManager.remove(midasEntityManager.merge(grant));
		
	}

	
}
