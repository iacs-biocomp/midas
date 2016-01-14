package es.aragon.midas.common.jpa;

import java.io.Serializable;

import java.util.Date;


/**
 * The persistent class for the MPI_RASGO database table.
 * 
 */

public class MpiRasgo implements Serializable {
	private static final long serialVersionUID = 1L;

	private MpiRasgoPK id;

	private Date datmod;

	private String valnor;

	private String valras;

	/**
	 * Constructor
	 */
	public MpiRasgo() {
	}

	/**
	 * Obtiene la clave primaria
	 * @return
	 */
	public MpiRasgoPK getId() {
		return this.id;
	}

	/**
	 * Asigna la clave primaria
	 * @param id
	 */
	public void setId(MpiRasgoPK id) {
		this.id = id;
	}

	/**
	 * Obtiene fecha de modificaci�n
	 * @return
	 */
	public Date getDatmod() {
		return this.datmod;
	}

	
	/**
	 * Establece la fecha de modificaci�n
	 * @param datmod
	 */
	public void setDatmod(Date datmod) {
		this.datmod = datmod;
	}

	/**
	 * Obtiene el valor normalizado
	 * @return
	 */
	public String getValnor() {
		return this.valnor;
	}

	/**
	 * Establece el valor normalizado
	 * @param valnor
	 */
	public void setValnor(String valnor) {
		this.valnor = valnor;
	}

	
	/**
	 * Obtiene el valor del rasgo
	 * @return
	 */
	public String getValras() {
		return this.valras;
	}

	/**
	 * Establece el valor del rasgo
	 * @param valras
	 */
	public void setValras(String valras) {
		this.valras = valras;
	}

}