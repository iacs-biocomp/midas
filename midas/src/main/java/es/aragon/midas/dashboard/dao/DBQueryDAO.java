package es.aragon.midas.dashboard.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.dashboard.jpa.DBQuery;

/**
 * Implementación de la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */
@Stateless
public class DBQueryDAO implements IDBQueryDAO {
	
	@PersistenceContext(unitName = "midas4")
	private EntityManager em;


	/** 
	 * Returns all queries in table
 	 * @return list of all queries registered in table
 	 */
	public List<DBQuery> find(){
    	Query query = em.createQuery("select bq from DBQuery bq");
    	@SuppressWarnings("unchecked")
    	List<DBQuery> encontrados = query.getResultList();
    	
    	return encontrados;
    }

    
    /**
     * Gets a query from database by ID
     * @param id ID PK from query table
     * @return BiganQuery with query definition
     */
    public DBQuery findById(int id) {
    	return em.find(DBQuery.class, id);
    }

}