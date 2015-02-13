package es.aragon.midas.util;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import es.aragon.midas.exception.MidasException;
import es.aragon.midas.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

// TODO Revisar esta clase. Logs y stacktraces

/**
 * Clase con funciones ï¿½tiles para la gestiï¿½n de conexiones a base de datos
 *
 * @author carlos
 *
 */
public class ConnectionFactory {

    /**
     * Caché de datasources
     */
    private static HashMap<String, DataSource> dataSources = new HashMap<String, DataSource>();
    private static Logger log = new Logger();
    private static final String jndiDataSourceName = EnvProperties.getProperty(Constants.CFG_JNDIDS);
    //private static final EntityManagerFactory midasEMF = configureEntityManagerFactory(EnvProperties.getProperty(Constants.CFG_JPAPU));
    //private static final EntityManagerFactory appEMF = configureEntityManagerFactory(EnvProperties.getProperty(Constants.CFG_APPPU));
    //private static final EntityManagerFactory midasEMF = null;
    //private static final EntityManagerFactory appEMF = null;

    public ConnectionFactory() {
    }

    /**
     * @param dataSourceName Nombre de la fuente de datos.
     * @param logger Log.
     * @return Objeto de tipo DataSource.
     * @throws SQLException
     */
    public static DataSource getDataSource(String dataSourceName) {
        DataSource ds = null;

        if (dataSources.get(dataSourceName) != null) {
            return dataSources.get(dataSourceName);
        } else {
            try {
                InitialContext initialContext = new InitialContext();
                Object o = initialContext.lookup(dataSourceName);
                if (o != null) {
                    if (o instanceof DataSource) {
                        ds = (DataSource) o;
                        System.out.println("Creando datasource desde JNDI");
                    } else { // o existe pero no es un Datasource
                        throw new MidasException("Error al recuperar el objeto \"" + dataSourceName + "\": No implementa la interfaz DataSource.");
                    }
                } else {	// o no existe
                    ds = getDataSourceFromParams(dataSourceName);
                }
                initialContext.close();
            } catch (Exception e) {
                try {
                    ds = getDataSourceFromParams(dataSourceName);
                } catch (SQLException sqle) {
                    log.error("Error en ConnectionFactory.getDatasource: " + e, sqle);
                }
            }
            if (ds != null) {
                dataSources.put(dataSourceName, ds);
            }
        }
        return ds;
    }

    /**
     * Genera un datasource a partir de parámetros en midasEnvironment.properties
     * @param name
     * @return
     * @throws SQLException 
     */
    public static DataSource getDataSourceFromParams(String name) throws SQLException {
        // TODO cambiar OraclePool por DBCP Pool
        System.out.println("Creando datasource desde fichero");
        OracleConnectionPoolDataSource ods;
        ods = new OracleConnectionPoolDataSource();
        ods.setDriverType(EnvProperties.getProperty(name + "DriverType"));
        ods.setServerName(EnvProperties.getProperty(name + "ServerName"));
        ods.setServiceName(EnvProperties.getProperty(name + "DatabaseName"));
        String port = EnvProperties.getProperty(name + "PortNumber");
        try {
            int iport = Integer.parseInt(port);
            ods.setPortNumber(iport);
        } catch (NumberFormatException nfe) {
            log.error("No se ha podido leer el puerto del datasource", nfe);
            log.error("Compruebe la configuración de midasEnvironment.properties");
            log.error("Compruebe la configuración de los Datasources en el Server");
        }
        ods.setUser(EnvProperties.getProperty(name + "User"));
        ods.setPassword(EnvProperties.getProperty(name + "Password"));
        return ods;
    }

    
    /**
     * Obtiene el datasource definido en la variable jndiDataSourceName (defecto)
     * @return 
     */
    public static DataSource getDataSource() {
        return getDataSource(jndiDataSourceName);
    }

    
    /**
     * Devuelve el nombre del Data source
     *
     * @return
     */
    public static String getDefaultDataSourceName() {
        return jndiDataSourceName;
    }

    
    /**
     * Obtiene una conexión del datasource nombrado
     * @param dataSourceName Fuente de datos.
     * @param logger Log.
     * @return Objeto de tipo Connection.
     * @throws SQLException
     */
    public static Connection getConnection(String dataSourceName) {
        Connection conn = null;
        DataSource ds;

        try {
            ds = getDataSource(dataSourceName);
            conn = ds.getConnection();
        } catch (Exception e) {
            log.error("Error en ConnectionFactory.getConexion: " + e, e);
        }
        return conn;
    }

    
    /**
     * Obtiene una conexion del datasource por defecto
     * @return 
     */
    public static Connection getConnection() {
        return getConnection(jndiDataSourceName);
    }

    /**
     * Libera la conexion
     * @param con Objeto de tipo Connection.
     * @param log Log.
     */
    public static void releaseConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            log.error("Error en ConnectionFactory.cerrarConexiones: " + e, e);
        }
    }

    /**
     * Cierra el statement y libera la conexion
     *
     * @param con Objeto de tipo connection.
     * @param stmt Objeto de tipo Statement.
     * @param log log.
     */
    public static void releaseConnection(Connection con, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            log.error("Error en ConnectionFactory.cerrarConexiones: " + e, e);
        }
    }

    /**
     * Cierra resultset y statement y libera la conexion
     *
     * @param con Objeto de tipo connection.
     * @param stmt Objeto de tipo Statement.
     * @param rs Objeto de tipo ResultSet.
     * @param log log.
     */
    public static void releaseConnection(Connection con, Statement stmt,
            ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqle) {
            log.error("Error cerrando ResultSet", sqle);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException sqle) {
            log.error("Error cerrando Statement", sqle);
        }
        // ConnectionUtils.cerrarConexion(conexion);
        if (con != null) {
            releaseConnection(con);
        }
    }


    /**
     * Comprueba la disponibilidad de la conexiï¿½n con la base de datos.
     *
     * @param dsName DataSource name
     * @param log Log
     * @return Devuelve true si la base de datos estï¿½ disponible o false en
     * caso contrario.
     */
    public static boolean testConexion(String dsName) {
        Connection conn = null;
        boolean ret = false;
        try {
            conn = getConnection(dsName);
            if (conn != null && !conn.isClosed()) {
                log.info("La conexiï¿½n con la base de datos funciona correctamente");
                ret = true;
            } else {
                log.error("La base de datos no parece estar disponible");
                ret = false;
            }
        } catch (Exception ex) {
            log.error(
                    "La base de datos no parece estar disponible: "
                    + ex.getLocalizedMessage(), ex);
            ret = false;
        } finally {
            releaseConnection(conn);
        }

        return ret;
    }

    
    /**
     * Cambia el esquema activo en una conexion abierta
     * @param conn
     * @param schema
     * @throws SQLException
     */
    public static void setSchema(Connection conn, String schema)
            throws SQLException {
        if (schema != null) {
            Statement stmtAlter = conn.createStatement();
            stmtAlter.execute("alter session set current_schema = " + schema);
        }
    }


    
    
    
    /*
    Métodos de la antigua version de Midas. Borrar cuando se esté seguro de que no son necesarios
    * 
    * private static EntityManagerFactory configureEntityManagerFactory(String pu) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PersistenceUnitProperties.JTA_DATASOURCE, jndiDataSourceName);
        properties.put(PersistenceUnitProperties.SESSION_CUSTOMIZER, "es.aragon.midas.util.JPAEclipseLinkSessionCustomizer");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(pu, properties);
        return emf;
    }

   
    public static EntityManagerFactory getMidasEMF() {
        // Alternatively, you could look up in JNDI here
        return midasEMF;
    }

//    public static EntityManagerFactory getEMF() {
//        // Alternatively, you could look up in JNDI here
//        return appEMF;
//    }
 */
    public static void shutdown() {
        // Close caches and connection pools
        //        midasEMF.close();
        //        appEMF.close();
    }
    
}
