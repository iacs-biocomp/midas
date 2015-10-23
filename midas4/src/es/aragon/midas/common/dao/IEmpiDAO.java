package es.aragon.midas.common.dao;


import es.aragon.midas.common.jpa.ComVUsuario;

/**
 * Implements basic DAO methods to connect to EMPI 
 * @author Carlos
 *
 */
public interface IEmpiDAO {

	/**
	 * 
	 * @param cia
	 * @return
	 */
	public abstract ComVUsuario getPersonaByCIA(String cia);
	
	/**
	 * 
	 * @param nhc
	 * @param hospital
	 * @return
	 */
	public ComVUsuario getPersonaByNHC(String nhc, String hospital);
	
}