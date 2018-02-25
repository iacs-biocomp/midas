package es.aragon.midas.dashboard.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.dashboard.jpa.DBFrame;

/**
 * Implementación de la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */
@Stateless
public class DBFrameDAO implements IDBFrameDAO {
	
	@PersistenceContext(unitName = "midas4")
	private EntityManager em;


	/** 
	 * Returns all queries in table
 	 * @return list of all queries registered in table
 	 */
	public List<DBFrame> find(){
    	Query query = em.createQuery("select bfr from DBFrame bfr");
    	@SuppressWarnings("unchecked")
    	List<DBFrame> encontrados = query.getResultList();
    	
    	return encontrados;
    }

    
    /**
     * Gets a query from database by ID
     * @param id ID PK from query table
     * @return BiganQuery with query definition
     */
    public DBFrame findById(int id) {
    	return em.find(DBFrame.class, id);
    }

}