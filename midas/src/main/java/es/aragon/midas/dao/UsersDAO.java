package es.aragon.midas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.config.MidRole;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.logging.Logger;

@Stateless
public class UsersDAO {

	@PersistenceContext(unitName = "midas4")
	private EntityManager midasEntityManager;
	// private EntityManager em =
	// ConnectionFactory.getMidasEMF().createEntityManager();

	static Logger log = new Logger();

	/**
	 * 
	 * @param username
	 * @return
	 */
	public MidUser find(String username) {
		return (MidUser) midasEntityManager.find(MidUser.class, username);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public MidUser create(MidUser user) {
		try {
			midasEntityManager.persist(user);
		} catch (Exception e) {
			log.error("Error creando usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public MidUser update(MidUser user) {
		try {
			midasEntityManager.merge(user);
		} catch (Exception e) {
			log.error("Error actualizando usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

	/**
	 * 
	 * @return
	 */
	public List<MidUser> findAll() {
		Query query = midasEntityManager.createNamedQuery("MidUser.findAll");
		@SuppressWarnings("unchecked")
		List<MidUser> users = query.getResultList();
		return users;
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public MidUser findByUserName(String userName) {
		return (MidUser) midasEntityManager
				.createNamedQuery("MidUser.findByUserName")
				.setParameter("userName", userName).getSingleResult();
	}

	/**
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public MidUser addUserRole(MidUser user, MidRole role) {
		try {
			user.getMidRoleList().add(role);
			role.getMidUserList().add(user);
			midasEntityManager.merge(role);
		} catch (Exception e) {
			log.error("Error añadiendo rol " + role.getRoleId()
					+ " al  usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

	/**
	 * Borra un rol de un usuario
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public MidUser deleteUserRole(MidUser user, MidRole role) {
		try {
			user.getMidRoleList().remove(role);
			role.getMidUserList().remove(user);
			// Elimina haciendo merge en la entidad role ya que es la entidad
			// maestra en la relacion(no contiene el mappedBy)
			midasEntityManager.merge(role);
		} catch (Exception e) {
			log.error("Error eliminando rol " + role.getRoleId()
					+ " al  usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public MidUser addUserContext(MidUser user, MidContext context) {
		try {
			if (!user.getMidContextList().contains(context)) {
				user.getMidContextList().add(context);
				context.getMidUserList().add(user);
				log.debug("Añadiendo contexto " + context.getCxId()
						+ " al  usuario " + user.getUserName());
				midasEntityManager.merge(context);
			} else {
				log.warn("No se añade contexto preasignado");
			}
		} catch (Exception e) {
			log.error("Error añadiendo contexto " + context.getCxId()
					+ " al  usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

	/**
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public MidUser deleteUserContext(MidUser user, MidContext context) {
		try {
			user.getMidContextList().remove(context);
			context.getMidUserList().remove(user);
			// Elimina haciendo merge en la entidad context ya que es la entidad
			// maestra en la relacion(no contiene el mappedBy)
			midasEntityManager.merge(context);
		} catch (Exception e) {
			log.error("Error eliminando contexto " + context.getCxId()
					+ " al  usuario " + user.getUserName(), e);
			user = null;
		}
		return user;
	}

}
