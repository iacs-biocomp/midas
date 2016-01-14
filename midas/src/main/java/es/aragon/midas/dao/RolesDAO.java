package es.aragon.midas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.config.MidRole;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.Logger;

@Stateless
public class RolesDAO {

    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();    

    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    
    static Logger log = new Logger();
    
	/**
	 * devuelve una lista con los roles (BD) de un usuario 
	 * @param username
	 * @return
	 */
	public List<MidRole> find (String username) {
		// selecciona los roles cuyos usuarios asociados (userroles) 
		// tienen por nombre el indicado en el parametro
		String jpql = "select r FROM MidRole r "
				+ "JOIN r.midUserList ur "
				+ "WHERE ur.userName = :username";


		Query query = midasEntityManager.createQuery(jpql).setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query.getResultList();		
		return roles;
	}
	
	/**
	 * Devuelve una lista con todos los roles
	 * @return
	 */
	public List<MidRole> findAll () {
		Query query = midasEntityManager.createNamedQuery("MidRole.findAll");
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query.getResultList();		
		return roles;
	}
	

	/**
	 * Devuelve la lista de roles no asignados al usuario
	 * @param user
	 * @return
	 */
	public List<MidRole> findAllUngiven (MidUser user) {
		Query query = midasEntityManager.createNamedQuery("MidRole.findAllUngiven");
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query
								.setParameter("userName", user.getUserName())
								.getResultList();		
		return roles;
	}
	
	
	public List<MidRole> findRoleGrants () {
		String jpql = "SELECT DISTINCT r FROM MidRole r JOIN FETCH r.midGrantList";
		Query query = midasEntityManager.createQuery(jpql);
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query.getResultList();		
		return roles;
	}
	
	public List<MidRole> findRoleContexts () {
		String jpql = "SELECT DISTINCT r FROM MidRole r JOIN FETCH r.midContextList";
		Query query = midasEntityManager.createQuery(jpql);
		@SuppressWarnings("unchecked")
		List<MidRole> roles = query.getResultList();		
		return roles;
	}
	
	public void save(MidRole nuevoRol){
		midasEntityManager.merge(nuevoRol);
	}

	public void delete(MidRole rol) {
		midasEntityManager.remove(midasEntityManager.merge(rol));
	}
	
	public MidRole findByRoleId(String roleId){
		Query query = midasEntityManager.createNamedQuery("MidRole.findByRoleId");
		MidRole rol = (MidRole) query.setParameter("roleId", roleId).getSingleResult();		
		return rol; 
	}
	
}
