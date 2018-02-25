package es.aragon.midas.vo;

import java.math.BigDecimal;


/**
 * Define la estructura de un fichero
 * @author Jorge Landa
 *
 */
public class FileMetadata implements Comparable<FileMetadata>{

	private String tipo;
	private String nombre;
	private BigDecimal size;
	private BigDecimal bits = new BigDecimal("1024");
	
	/**
	 * Llamada de declaracion
	 */
	public FileMetadata() {
		
	}
	
	/**
	 * Constructor del fichero
	 * @param tipo
	 * @param nombre
	 * @param size
	 */
	public FileMetadata(String tipo, String nombre, long size){
		this.tipo = tipo;
		this.nombre = nombre;
		this.size = new BigDecimal(size);

		if(size > 0){
			this.size = this.size.divide(bits);
			this.size.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

	}
	
	/**
	 * Devuelve el tipo de fichero que es
	 * @return
	 */
	public String getTipo(){
		return this.tipo;
	}
	
	/**
	 * Establece el tipo de fichero que es
	 * @param tipo
	 */
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	/**
	 * Devuelve el nombre del fichero
	 * @return
	 */
	public String getNombre(){
		return this.nombre;
	}
	
	/**
	 * Establece el nombre del fichero
	 * @param nombre
	 */
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el tamaño del fichero
	 * @return
	 */
	public BigDecimal getSize(){		
		return this.size;
	}
	
	/**
	 * Establece el tamaño del fichero
	 * @param size
	 */
	public void setSize(long size){
		this.size = new BigDecimal(size);
		this.size.setScale(2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * Ordenacion: este metodo nos ayuda a ordenar
	 * 		la estructura de directorios, primero carpetas luego ficheros.
	 */
	public int compareTo(FileMetadata o) {
		if(tipo.compareTo(o.tipo) > 0)
			return 1;
		else if(tipo.compareTo(o.tipo) == 0)
			return 0;
		else
			return -1;
	}
}
