package es.aragon.midas.config;

import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Clase con diversas utilidades y constantes.
 */
public class CtxProperties {
    /** Propiedades de la aplicaci�n **/
    static Properties appProperties;
    

	/**
	 * Obtiene las propiedades configuradas en el SetupServlet
	 * @param request
	 * @return Properties
	 */
	public static Properties getAppProperties(ServletRequest request) {
		Properties p = null;
		try{
			ServletContext ctx = ((HttpServletRequest) request).getSession().getServletContext();
            if (ctx != null) {
                p = (Properties) ctx.getAttribute("configProperties");
            }
		} catch(Exception e) {
		  System.err.println("Error al obtener las propiedades de la aplicación: "+e.getLocalizedMessage());
		  e.printStackTrace();
		}

		return p;
	}
    
    
    /**
     * Obtiene las propiedades configuradas en el SetupServlet
     * @return Properties
     */
    public static Properties getAppProperties() {
        return appProperties;
    }
    

	/**
	 * Obtiene el valor de una propiedad a partir del archivo de propiedades de la aplicaci�n.
	 * @param request
	 * @param propertyName
	 * @return
	 */
	public static String getFromAppProperties(ServletRequest request, String propertyName) {
		String value = null;
		try{
			Properties p = getAppProperties(request);
            if (p != null) {
                p.getProperty(propertyName);
            }
		}catch(Exception e) {
		  System.err.println("Error al obtener la propiedad del properties: "+e.getLocalizedMessage());
		  e.printStackTrace();
		}

		return value;
	}
    
    
    /**
     * Obtiene el valor de una propiedad a partir del archivo de propiedades de la aplicaci�n.
     * @param propertyName Nombre de la propiedad
     * @return String con el valor de la propiedad
     */
    public static String getFromAppProperties(String propertyName) {
        String value = null;
        try{
            if (appProperties != null) {
                value = appProperties.getProperty(propertyName);
            }
        }catch(Exception e) {
            System.err.println("Error al obtener la propiedad del properties: "+e.getLocalizedMessage());
            e.printStackTrace();
        }

        return value;
    }
    

	/**
	 * Obtiene el valor de una propiedad a partir del archivo web.xml
	 * @param request
	 * @param propertyName
	 * @return
	 */
	public static String getFromWebContextProperties(ServletRequest request, String propertyName) {
		String value = null;
		try{
			ServletContext ctx = ((HttpServletRequest) request).getSession().getServletContext();
            if (ctx != null) {
                value = ctx.getInitParameter(propertyName);
            }
		}catch(Exception e) {
		  System.err.println("Error al obtener la propiedad del web.xml: "+e.getLocalizedMessage());
		  e.printStackTrace();
		}

		return value;
	}


	/**
	 * Trata de obtener el valor de una propiedad siguiendo los siguientes casos:
	 *   1- Intenta obtener la propiedad del archivo de propiedades que carga el SetupServlet
	 *   2- Si falla, intenta obtenerla de un context-param del web.xml
	 *   3- En caso contrario devuelve el valor por defecto
	 * @param request ServletRequest
	 * @param propertyName Nombre de la propiedad
	 * @param defaultValue Valor por defecto
	 * @return String con el valor de la propiedad obtenido
	 */
	public static String getProperty(ServletRequest request, String propertyName, String defaultValue) {
		//Primero intenta obtener la propiedad del archivo de propiedades
		String tmpValue = getFromAppProperties(request, propertyName);
        
        //Si falla, lo intenta con el web.xml
        if (tmpValue == null) {
            tmpValue = getFromWebContextProperties(request, propertyName);
            //Si tambi�n falla, devuelve el valor por defecto
            if (tmpValue == null) {
                tmpValue = defaultValue;
            }
        }

		return tmpValue;
	}
}
