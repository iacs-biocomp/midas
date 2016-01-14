package es.aragon.midas.common.dao;

import es.aragon.midas.common.jpa.IComVUsuario; 

import java.util.List;

import javax.ejb.Local;

/**
 * Implements basic DAO methods to connect to BDU 
 * @author Carlos
 *
 */
@Local
public interface IBduDAO {

    /**
     * 
     * @return
     */
	public abstract List<IComVUsuario> getUsuarios();
    
	
	
	/**
     * 
     * @param cia
     * @return
     */
	public abstract IComVUsuario getUsuarioByCIA(String cia);	
    
	
	
	/**
     * 
     * @param nif
     * @return
     */
	public abstract IComVUsuario getUsuarioByNIF(String nif);
   
	
	
	/**
     * 	
     * @param nom
     * @param ape1
     * @param ape2
     * @return
     */
	public List<IComVUsuario> getUsuariosByNombre(String nom, String ape1, String ape2);

}