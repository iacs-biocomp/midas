package es.aragon.midas.config;

import es.aragon.midas.dao.AppPropertiesDAO;
import es.aragon.midas.exception.MidasException;
import java.util.*;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Clase que encapsula y permite el acceso a parametros de la aplicacion a
 * partir del fichero de propiedades ubicado en la ruta interna
 * midasApplication.properties o desde Base de Datos, y que cachea esas
 * propiedades para ser utilizadas desde cualquier punto de la aplicación
 */
//TODO eliminar printStackTrace y gestionar excepciones
public class AppProperties {

    /**
     * Ficheros de properties de la aplicacion
     */
    private static String FICHERO_CONFIG = "midasApplication";
    /**
     * Cache de ficheros de properties
     */
    private static HashMap<String, MidAppProperty> tablaProperties = new HashMap<String, MidAppProperty>();
    // TODO Gestión Logger
    //private static Logger objLog = Logger.getLogger("debuglog");
    // Indica si los parametros estan en base de datos o en fichero
    private static boolean parameterDatabased = 
            EnvProperties.getProperty(Constants.CFG_PARAMDB).equals("true");

    static {
        try {
            // cargarProperties(FICHERO_CONFIG);
            cargarPropertiesBD();
        } catch (MidasException e) {
            System.out.println("Error cargando propiedades de aplicación");
            //objLog.error("Error cargando propiedades de aplicación", e);
        }
    }

    /**
     * Constructor vacío
     */
    public AppProperties() {
    }

    /**
     * carga las propiedades desde el fichero de properties
     *
     * @param rutaFicheroProperties
     */
    private static void cargarProperties(String rutaFicheroProperties)
            throws MidasException {
        cargarProperties(rutaFicheroProperties, null);
    }

    /**
     * Lee un fichero de propiedades
     *
     * @param ficheroProperties
     * @param locale
     */
    private static synchronized void cargarProperties(String ficheroProperties,
            Locale locale) throws MidasException {
        PropertyResourceBundle propertyRB = null;

        // Leer el fichero de propiedades
        try {
            if (locale == null) {
                propertyRB = (PropertyResourceBundle) PropertyResourceBundle
                        .getBundle(ficheroProperties);
            } else {
                propertyRB = (PropertyResourceBundle) PropertyResourceBundle
                        .getBundle(ficheroProperties, locale);
            }
        } catch (MissingResourceException me) {
            String msg = "Ha sido imposible recuperar el fichero de propiedades de la conexión.";
            throw new MidasException(msg, me);
        }

        // Recuperar propiedades
        Enumeration<String> keys = propertyRB.getKeys();
        while (keys.hasMoreElements()) {
            String aKey = (String) keys.nextElement();
            tablaProperties.put(aKey,
                    new MidAppProperty(aKey, propertyRB.getString(aKey), ""));
        }
    }

    /**
     * Lee las propiedades desde Base de Datos
     *
     */
    private static synchronized void cargarPropertiesBD() throws MidasException {
        try {
            if (parameterDatabased) {
                try {
                    AppPropertiesDAO dao = (AppPropertiesDAO) new InitialContext().lookup("java:module/AppPropertiesDAO");
                    //objLog.debug("Cargando parametros de BD");
                    tablaProperties = dao.find();
                } catch (Exception e) {
                    //objLog.error("Error conectando con AppPropertiesDAO.", e);
                    cargarProperties(FICHERO_CONFIG);
                }
            } else {
                cargarProperties(FICHERO_CONFIG);
            }
        } catch (Exception e) {
            String msg = "Ha sido imposible recuperar el fichero de propiedades de la conexión.";
            throw new MidasException(msg, e);           
        }
    }


    
    /**
     * Recarga el fichero de propiedades en la ruta por defecto
     */
    public static void reload() {
        try {
            cargarPropertiesBD();
        } catch (MidasException e) {
          Logger.getLogger("debuglog").error("Error recargando parametros", e);
        }
    }

    
    
    /**
     * Devuelve la hashtable de cache de properties.
     */
    public static HashMap<String, MidAppProperty> getCacheParameters() {
        @SuppressWarnings("unchecked")
        HashMap<String, MidAppProperty> hm = (HashMap<String, MidAppProperty>) tablaProperties.clone();
        return hm;
    }


    /**
     * Este método devuelve el valor de configuración asociado a la key
     * introducida. Si la clave no existe, devuelve null.
     *
     * @param keyValorConfiguracion
     * @return
     */
    public static String getParameter(String key) {
    	if (tablaProperties.containsKey(key))
    		return tablaProperties.get(key).getValue();
    	else
    		return null;
    }


    /**
     * Añade un parámetro a la lista de parametros del sistema
     *
     * @param codigo
     * @param valor
     * @param descripcion
     */
    public void addParameter(String codigo, String valor, String descripcion)
            throws MidasException {
        try {
            AppPropertiesDAO dao = new AppPropertiesDAO();
            dao.create(codigo, valor, descripcion);
            reload();
        } catch (Exception e) {
            throw new MidasException("Error añadiendo parametro", e);
        }
    }


    /**
     * Actualiza un parámetro de la lista de parametros del sistema
     *
     * @param codigo
     * @param valor
     */
    public void updateParameter(String codigo, String valor)
            throws MidasException {
        AppPropertiesDAO dao = new AppPropertiesDAO();
        try {
            dao.update(codigo, valor);
            reload();
        } catch (Exception e) {
            throw new MidasException("Error actualizando parametro", e);
        }
    }
}
