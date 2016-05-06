package es.aragon.midas.dao;

import es.aragon.midas.config.MidGuia;

public interface IMidGuiaDAO {
	
	/**
	 * Obtiene los datos para conectarse a otro sistema mediante GUIA.
	 * @param appDst Nombre de la aplicación para la conexión de GUIA. 
	 * @return Objeto con los datos necesarios para generar un token de GUIA.
	 */
	public MidGuia findByAppDst(String appDst);

}
