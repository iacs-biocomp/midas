	package es.aragon.midas.dashboard.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import es.aragon.midas.dashboard.jpa.DBSeriesData;
import es.aragon.midas.logging.Logger;


/**
 * Implementación de la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */

@Stateless
public class DBDataDAO implements IDBDataDAO {
	
	@PersistenceContext(unitName = "midas4")
	private EntityManager em;

	Logger log = new Logger();
	
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
    
    
    @Override    
    public void getHeaders(String queryStr, List<String> headers, List<Object[]> data) {
    	
		Session session = em.unwrap(Session.class);
		session.doWork(new Work() {
		    @Override
		    public void execute(Connection connection) throws SQLException {
		    	try {
    		    	java.sql.Statement st = connection.createStatement();
    		    	java.sql.ResultSet rs = st.executeQuery(queryStr);
    		    	java.sql.ResultSetMetaData md = rs.getMetaData();
    		    	int count = md.getColumnCount();
    		    	for (int i=1; i <= count; ++i) {
    		    		headers.add(md.getColumnLabel(i));
    		    	}
    		    	
    		    	while (rs.next()) {
    		    		Object [] row = new Object[count];
        		    	for (int i=0; i < count; ++i) {
        		    		row[i] = rs.getObject(i+1);
        		    	}
        		    	data.add(row);
    		    	}
    		    } catch (SQLException e) {
		    		log.error("Error obteniendo nombres de columnas", e);
		    	}

		    }
		});    		
    }    
    
}