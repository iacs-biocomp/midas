package es.aragon.midas.common.jpa;

import java.io.Serializable;


/**
 * The primary key class for the MPI_RASGO database table.
 * 
 */

public class MpiRasgoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String uip;

	private String coddom;

	private long codras;

	/**
	 * Constructor
	 */
	public MpiRasgoPK() {
	}
	
	
	/**
	 * Obtiene el UIP
	 * @return
	 */
	public String getUip() {
		return this.uip;
	}
	
	/**
	 * Establece el UIP
	 * @param uip
	 */
	public void setUip(String uip) {
		this.uip = uip;
	}
	
	
	/**
	 * Obtiene el codigo de dominio
	 * @return
	 */
	public String getCoddom() {
		return this.coddom;
	}
	
	/**
	 * Establece el codigo de dominio
	 * @param coddom
	 */
	public void setCoddom(String coddom) {
		this.coddom = coddom;
	}
	
	
	/**
	 * Obtiene el codigo de rasgo
	 * @return
	 */
	public long getCodras() {
		return this.codras;
	}
	
	
	/**
	 * Establece el codigo de rasgo
	 * @param codras
	 */
	public void setCodras(long codras) {
		this.codras = codras;
	}

	
	/**
	 * Compara dos claves
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MpiRasgoPK)) {
			return false;
		}
		MpiRasgoPK castOther = (MpiRasgoPK)other;
		return 
			this.uip.equals(castOther.uip)
			&& this.coddom.equals(castOther.coddom)
			&& (this.codras == castOther.codras);
	}


	/**
	 * Genera el hashcode
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.uip.hashCode();
		hash = hash * prime + this.coddom.hashCode();
		hash = hash * prime + ((int) (this.codras ^ (this.codras >>> 32)));
		
		return hash;
	}
}