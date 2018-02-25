package es.aragon.midas.dashboard.dao;

import java.util.List;

import es.aragon.midas.dashboard.jpa.DBFrame;


/**
 * Interfaz para la gestión de las queries registradas en la tabla bigan_queries
 * @author carlos
 *
 */
public interface IDBFrameDAO {

	/**
	 * Devuelve todas las queries
	 * @return Lista de queries Bigan
	 */
    public List<DBFrame> find();
    
	/**
	 * Busca una query por ID (clave primaria)
	 * @return BiganQuery buscada
	 */
    public DBFrame findById(int id);
    

}