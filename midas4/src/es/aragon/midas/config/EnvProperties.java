package es.aragon.midas.config;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import es.aragon.midas.exception.MidasException;

/**
 * Gestiona las propiedades con que opera el entorno MIDAS Lee y gestinoa las
 * propiedades guardadas en el fichero midasEnvironment.properties
 * 
 * @author carlos
 * 
 */
public class EnvProperties {

	/**
	 * Fichero de properties del entorno Midas de la aplicacion
	 */
	public static final String FICHERO_CONFIG = "midasEnvironment";

	/**
	 * Cache de ficheros de properties
	 */
	private static Properties tablaProperties = new Properties();

	static {
		try {
			cargarProperties(FICHERO_CONFIG);
		} catch (MidasException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Constructor vacío
	 */
	private EnvProperties() {
	}

	/**
	 * Recoge el fichero de properties
	 * 
	 * @param rutaFicheroProperties
	 */
	public static void cargarProperties(String rutaFicheroProperties)
			throws MidasException {
		cargarProperties(rutaFicheroProperties, null);
	}

	/**
	 * Lee un fichero de propiedades
	 * 
	 * @param ficheroProperties
	 * @param locale
	 */
	public static synchronized void cargarProperties(String ficheroProperties,
			Locale locale) throws MidasException {
		PropertyResourceBundle propertyRB = null;

		// Leer el fichero de propiedades
		try {
			if (locale == null) {
				propertyRB = (PropertyResourceBundle) PropertyResourceBundle
						.getBundle(ficheroProperties, Locale.getDefault(), Thread.currentThread().getContextClassLoader());
			} else {
				propertyRB = (PropertyResourceBundle) PropertyResourceBundle
						.getBundle(ficheroProperties, locale, Thread.currentThread().getContextClassLoader());
			}
		} catch (MissingResourceException me) {
			String msg = "Ha sido imposible recuperar el fichero de propiedades de la conexión.";

			throw new MidasException(msg, me);
		}

		// Recuperar propiedades
		Enumeration<String> keys = propertyRB.getKeys();
		String aKey = null;
		tablaProperties.clear();
		
		while (keys.hasMoreElements()) {
			aKey = (String) keys.nextElement();
			tablaProperties.put(aKey, propertyRB.getString(aKey));
		}
	}

	/**
	 * Devuelve la cache de properties.
	 */
	public static Properties getCacheProperties() {
		return (Properties) tablaProperties.clone();
	}

	/**
	 * Este método devuelve el valor de configuración asociado a la key
	 * introducida.
	 * 
	 * @param keyValorConfiguracion
	 * @return
	 */
	public static String getProperty(String keyValorConfiguracion) {
		return tablaProperties.getProperty(keyValorConfiguracion);
	}

	/**
	 * Añade un parámetro a la lista de parametros del sistema
	 * 
	 * @param codigo
	 * @param valor
	 * @param descripcion
	 */
	public static void addProperty(String codigo, String valor) throws MidasException {
		tablaProperties.put(codigo, valor);
	}

	/**
	 * Actualiza un parámetro de la lista de parametros del sistema
	 * 
	 * @param codigo
	 * @param valor
	 */
	public static void updateProperty(String codigo, String valor)
			throws MidasException {
		tablaProperties.setProperty(codigo, valor);
	}

}
