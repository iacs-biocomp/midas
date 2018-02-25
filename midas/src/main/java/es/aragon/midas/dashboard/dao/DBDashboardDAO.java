package es.aragon.midas.dashboard.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.dashboard.jpa.DBDashboard;

/**
 * Implementación de la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */
@Stateless
public class DBDashboardDAO implements IDBDashboardDAO {
	
	@PersistenceContext(unitName = "midas4")
	private EntityManager em;


	/** 
	 * Returns all queries in table
 	 * @return list of all queries registered in table
 	 */
	public List<DBDashboard> find(){
    	Query query = em.createQuery("select bdb from DBDashboard bdb");
    	@SuppressWarnings("unchecked")
    	List<DBDashboard> encontrados = query.getResultList();
    	
    	return encontrados;
    }

    
    /**
     * Gets a query from database by ID
     * @param id ID PK from query table
     * @return BiganQuery with query definition
     */
    public DBDashboard findById(long id) {
    	return em.find(DBDashboard.class, id);
    }

}