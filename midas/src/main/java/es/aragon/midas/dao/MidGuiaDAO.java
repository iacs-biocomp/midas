package es.aragon.midas.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.aragon.midas.config.MidGuia;

@Stateless
public class MidGuiaDAO implements IMidGuiaDAO {

	@PersistenceContext(unitName = "midas4")
	private EntityManager midasEntityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.aragon.midas.dao.IMidGuiaDAO#findByAppDst(java.lang.String)
	 */
	public MidGuia findByAppDst(String appDst) {
		return (MidGuia) midasEntityManager
				.createNamedQuery("MidGuia.findByAppDst")
				.setParameter("appDst", appDst).getSingleResult();
	}

}
