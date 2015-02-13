package es.aragon.midas.dao;

	// TODO implementar completamente esta clase
import es.aragon.midas.config.MidReport;
import es.aragon.midas.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;



@Stateless
public class ReportsDAO {
    
    //private EntityManager em = ConnectionFactory.getMidasEMF().createEntityManager();    
    @PersistenceContext(unitName="midas4")
    private EntityManager midasEntityManager;
    
    /**
     * 
     */
	static Logger log = new Logger();

	/**
	 * 
	 */
	public ReportsDAO() {
	}

	/**
	 * 
	 * @param code
	 * @param value
	 */
	public void update (String code, String value) {
		MidReport newReport = (MidReport)midasEntityManager.find(MidReport.class, code);
		newReport.setValue(value);
                midasEntityManager.merge(newReport);
	}
	
	/**
	 * 
	 * @param codigo
	 * @param valor
	 * @param descripcion
	 */
	public void create (String codigo, String valor, String descripcion) {
		MidReport newReport = new MidReport(codigo, valor, descripcion);
		midasEntityManager.persist(newReport);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
        public HashMap<String, MidReport> find() throws Exception {
            log.debug("Obteniendo relacion de informes");

            HashMap<String, MidReport> rep = new HashMap<String, MidReport>();
            Query query = midasEntityManager.createNamedQuery("MidReport.findAll", MidReport.class);

            @SuppressWarnings("unchecked")
            List<MidReport> reports = query.getResultList();

            for (MidReport p : reports) {
                rep.put(p.getId(), p);
            }
            return rep;
        }
        
        /**
         * Devuelve una propiedad por ID
         * @param id
         * @return 
         */
        public MidReport findById(String id) {
            return (MidReport) midasEntityManager.createNamedQuery("MidReport.findById")
                    .setParameter("id", id)
                    .getSingleResult();
        }

        
        /**
         * Devuelve una conexion a BD a partir del Entity Manager, para su uso en Jasper Reports
         * @return
         */
        public Connection getConnection() {
        	Connection connection = null; 
        	Session session = (Session) midasEntityManager.getDelegate();
        	SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        	ConnectionProvider cp = sfi.getConnectionProvider();
        	try {
        		connection = cp.getConnection();
        	}catch(SQLException sqle) {
        		log.error("Error devolviendo conexion a BD", sqle);
        	}
        	return connection;
        }
        
        /**
         * Devuelve una conexion a BD a partir del Entity Manager, para su uso en Jasper Reports
         * @return
         */
        public void closeConnection(Connection conn) {
        	Session session = (Session) midasEntityManager.getDelegate();
        	SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        	ConnectionProvider cp = sfi.getConnectionProvider();
        	try {
        		cp.closeConnection(conn);
        	}catch(SQLException sqle) {
        		log.error("Error liberando conexion a BD", sqle);
        	}
        }
        
        
}
