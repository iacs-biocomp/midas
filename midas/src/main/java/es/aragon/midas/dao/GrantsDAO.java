package es.aragon.midas.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.config.MidGrant;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.logging.Logger;


@Stateless
public class GrantsDAO implements IGrantsDAO {

    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();  
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    @PersistenceContext(unitName="midas4")
    private EntityManager em2;  
        
	static Logger log = new Logger();        
    
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#grantsByLdapRole(java.lang.String)
	 */
	@Override
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
				String grantToAdd = g.getGrId().toUpperCase().trim();
				if (!grants.contains(grantToAdd)) {
					grants.add(grantToAdd);
					getSubGrants(grantToAdd, grants);
				}
			}
		} catch (Exception e){
            log.error("Error obteniendo grants de rol LDAP " + role, e);
        }
        return grants;
	}

	
	
	
	/**
	 * Obtenemos los subroles de un rol, y los insertamos en el Set
	 * @param roleId
	 * @param set
	 */
	private void getSubGrants(String roleId, Set<String> set) {
		try {
			
			Query query2 = em2.createNamedQuery("findGrantsByRole")
					.setParameter("role_id", roleId);
			@SuppressWarnings("unchecked")
			List<MidGrant> mg = query2.getResultList();
			for(MidGrant g : mg  ) {
				String grantToAdd = g.getGrId().toUpperCase().trim();
				if (!set.contains(grantToAdd)) {
					set.add(grantToAdd);
					getSubGrants(grantToAdd, set);
				}
			}

		} catch (Exception e) {
            log.error("Error obteniendo subroles de rol " + roleId, e);
		}		
		
	}	
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#grantsByUser(java.lang.String)
	 */
	@Override
	public Set<String> grantsByUser (String username) {
		Set<String> grants = new HashSet<String>(0);
		
		// leemos los roles del usuario
		try {
			Query query1 = midasEntityManager.createNamedQuery("MidRole.findByUser")
					.setParameter("userName", username);
			@SuppressWarnings("unchecked")
			List<MidRole> mr = query1.getResultList();

			for (MidRole rs : mr) {
				getSubGrants(rs.getRoleId().toUpperCase().trim(), grants);
			}
		} catch (Exception e) {
                    log.error("Error obteniendo grants de usuario " + username, e);
		}
		
		return grants; 
	}
	
	
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#findAll()
	 */
	@Override
	public List<MidGrant> findAll () {
		Query query = midasEntityManager.createNamedQuery("MidGrant.findAll");
		@SuppressWarnings("unchecked")
		List<MidGrant> grants = query.getResultList();		
		return grants;
	}
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#findByGrId(java.lang.String)
	 */
	@Override
	public MidGrant findByGrId(String grId){
		Query query = midasEntityManager.createNamedQuery("MidGrant.findByGrId");
		MidGrant grant = (MidGrant) query.setParameter("grId",grId).getSingleResult();		
		return grant; 
	}
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#save(es.aragon.midas.config.MidGrant)
	 */
	@Override
	public void save(MidGrant grant){
		midasEntityManager.merge(grant);
	}


	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#delete(es.aragon.midas.config.MidGrant)
	 */
	@Override
	public void delete(MidGrant grant) {
		midasEntityManager.remove(midasEntityManager.merge(grant));
		
	}

	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#getRoleByLdap(String)
	 */
	@Override	
	public MidRole getRoleByLdap(String roleLdap) {
		Query query = midasEntityManager.createNamedQuery("MidRolesLdap.findRoleByLdap")
                .setParameter("roleLdap", roleLdap);

		MidRole r = null;

		try{
			r = (MidRole) query.getSingleResult();
		} catch (NoResultException nre){
			//Ignore this because as per our logic this is ok!
		}
		
		return r;
	}	
	
	/* (non-Javadoc)
	 * @see es.aragon.midas.dao.IGrantsDAO#findAll()
	 */
	@Override
	public List<MidRole> findAllRoles () {
		Query query = midasEntityManager.createNamedQuery("MidRole.findAll");
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query.getResultList();		
		return roles;
	}	
	
}
