package es.aragon.midas.mongo.filter;

/**
 * Clase con la estructura de los criterios por los que se deben ordenar los
 * resultados de una consulta mongo. Contiene field, que será el campo del JSON
 * por el cual se ordenará; y contiene type que indicará si ordenar de forma
 * ascendiente o descendiente.
 * 
 * @author ARTURO
 * @since 4.3.0
 * 
 */
public class MongoSort {

	// Tipos de sort que se pueden aplicar
	/**
	 * Orden ascendiente(1,2,3,4,...)
	 */
	public static final int TYPE_ASCENDING = 1;
	
	/**
	 * Orden descendiente(...,4,3,2,1)
	 */
	public static final int TYPE_DESCENDING = -1;

	private String field;
	private int type;

	public MongoSort(String field, int type) {
		this.field = field;
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
