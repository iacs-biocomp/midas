	package es.aragon.midas.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.aragon.midas.dashboard.jpa.DBSeriesData;


/**
 * Implementación de la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */

@Stateless
public class DBDataDAO implements IDBDataDAO {
	
	@PersistenceContext(unitName = "midas4")
	private EntityManager em;

    @Override
    public List<DBSeriesData> getData(String queryStr) {
    	
		List<DBSeriesData> list = new ArrayList<DBSeriesData>();
    	Query query = em.createNativeQuery(queryStr);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		
		for (Object[] row : data ) {
		
			DBSeriesData record = new DBSeriesData((String)row[0], (String)row[1], (String)row[2], (String)row[3]);
			list.add(record);
		
		}
		return list;
	}


    @Override
    public List<Object[]> getRawData(String queryStr) {
    	
    	Query query = em.createNativeQuery(queryStr);
		@SuppressWarnings("unchecked")
		List<Object[]> data = query.getResultList();
		return data;
	}    
    
    
    
}