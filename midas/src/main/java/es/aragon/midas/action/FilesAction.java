package es.aragon.midas.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FileUtils;

import es.aragon.midas.vo.FileMetadata;

/**
 * Control de ficheros y navegación por el servidor
 * @author Jorge Landa
 */
public class FilesAction extends MidasActionSupport {
	static final long serialVersionUID = 1L;
	
	//Variables estaticas
	public static final String FORMAT_DATE_HOUR_SNS = "yyyy-MM-dd-HH-mm-ss";
    private final String CARPETA_VERSIONES = "versiones";
    private final String EXTENSION_VERSIONES = ".bak";
	
    //Variables de navegación
	private String path = "/";
	private InputStream inputStream;
	private String fileName;
	
	//Variables de subida de un fichero
	private File uploadFile;
	private String filename;
	private String contentType;			// ??
	
	//Variables de listas de contenido del directorio y segmentación en el path
	private List<FileMetadata> three = new ArrayList<FileMetadata>();
	private List<String> redirect = new ArrayList<String>();
	
	{
		setGrantRequired("ADMIN");
	}
	
	/**
	 * Redirige a la pagina de listado de Archivos
	 * @return
	 * @throws FileNotFoundException 
	 */
	public String list() throws FileNotFoundException{
		setRedirect();
		String result = setThree();
		
		/*
		 * Si al hacer click en una ruta, el resultado es un fichero, se lanza
		 * 		el proceso para descargarlo
		 * 	Sino, se entra en el directorio y se carga su contenido para ser mostrado
		 */
		if(result.equals("File")){
			return "download";
		}else{
			return "files";
		}
	}
	
	/**
	 * Metodo que se encarga de subir un fichero al servidor
	 * @return
	 * @throws IOException 
	 */
	public String upload() throws IOException{
		setRedirect();
		
		/** Si el path termina en / lo devuelve integro, sino le añade "/" */
		if(!path.endsWith("/")) {
			path += "/";
        }
		
		//Establece la ruta de las versiones, al path actual le añade la carpeta versiones
        String rutaVersiones = path + CARPETA_VERSIONES;
		
        if(uploadFile != null){
        	File newFile = new File(path, filename);
        	/** Si el fichero existe procedemos a crear su .bak*/
            if(newFile.exists()){
            	File carpetaVersiones = new File(rutaVersiones);
            	/** Comprobamos si la carpeta de versiones ya existe, sino se crea*/
            	if(!carpetaVersiones.exists()){
            		carpetaVersiones.mkdirs();
            	}
            	
            	//Establece el nuevo nombre para la versión .bak, nombre del fichero + fecha de creación  + .bak
            	String nuevoNombre = filename + getDateFormatted(getDateNow(), FORMAT_DATE_HOUR_SNS) + EXTENSION_VERSIONES;
            	
            	File moveFile = new File(path, nuevoNombre);
            	try {
            		//Copia el nuevo fichero al que se va a mover
            		FileUtils.copyFile(newFile, moveFile);
            		//Copia el fichero a mover a la ruta de versiones
    				FileUtils.copyFileToDirectory(moveFile, carpetaVersiones);
    				//Borra el fichero de la carpeta origen
    				moveFile.delete();
    			} catch (IOException e) {
    				log.error("Error: " + e);
    				throw e;
    			}
            }
            
            /** Se intenta copiar el fichero que vamos a subir en el servidor*/
            try {
    			FileUtils.copyFile(uploadFile, newFile);	
    		} catch (IOException e) {
    			log.error("Error copiando el archivo");
    			throw e;
    		}
            
            //Recargamos el árbol de contenido del directorio en el que nos encontramos
            three.clear();
    		String result = setThree();
    		
    		//Comprobante basico de ficheros
    		if(result.equals("File")){
    			return "download";
    		}else{
    			return "files";
    		}
        }
        /** Control de Error de fichero nulo. Se excluye la acción*/
        else{
            //Recargamos el árbol de contenido del directorio en el que nos encontramos
        	three.clear();
    		String result = setThree();
    		
    		//Comprobante basico de ficheros
    		if(result.equals("File")){
    			return "download";
    		}else{
    			return "files";
    		}
        }
	}
	
	//<!------- Getters and Setters
	
	/**
	 * Devuelve el Path
	 * 		Si el path termina en / lo devuelve integro
	 * 		sino le añade /
	 * @return
	 */
	public String getPath(){
		if(path.endsWith("/")) {
			return path;
        }else{
        	return path + "/";
        }
	}
	
	/**
	 * Establece el path
	 * @param path
	 */
	public void setPath(String path){
		this.path = path;
	}
	
	/**
	 * Devuelve el resultado del árbol de contenidos de un directorio
	 * 		establece el arbol de directorio o descarga el fichero
	 * @return
	 * @throws FileNotFoundException 
	 */
	public String setThree() throws FileNotFoundException{
		File file = new File(path);
		
		/** Si el fichero es un directorio lo recorremos y añadimos su contenido al listado de directorio */
		if(file.isDirectory()){
			if(file.listFiles() != null){
				for (File fichero : file.listFiles()) {
					if(fichero.isDirectory()){
						three.add(new FileMetadata("D", fichero.getName(), fichero.length()));
					}else{
						three.add(new FileMetadata("F", fichero.getName(), fichero.length()));
					}
				}
			}
			
			/*
			 * Proceso de ordenación de ficheros del directorio
			 * 		El directorio es ordenado para que muestre primero los Directorios
			 * 	Y despues los ficheros
			 */
			Collections.sort(three);
			
			return "Directory";
			
		}
		/** Si el fichero es un fichero lo descargamos */
		else if(file.isFile()){	
			try {
				setInputStream(new FileInputStream(file));
				setFileName(file.getName());
			} catch (FileNotFoundException e) {
				throw e;
			}
			return "File";
		/** Si el fichero es un directorio devuelve el resultado */
		}else{
			return "Directory";
		}
	}
	
	/**
	 * Devuelve el arbol de directorios
	 * @return
	 */
	public List<FileMetadata> getThree(){
		return three;
	}
	
	/**
	 * Establece el redirect.
	 * 		Divive el Path según los / y nos permite la navegación por las distintas
	 *      subdirectorios.
	 */
	public void setRedirect(){
		//Se comprueba si el path es la ruta principal "/"
		if(path.length() == 1){
			redirect.add("/");
		}else{
			//Divide el path dividido por los "/"
			String [] splits = path.split("/");
			for (String split : splits){
				if(split.equals("")){
					redirect.add("/");
				}else{
					redirect.add(split);
				}
			}
		}
	}
	
	/**
	 * Devuelve el redirect
	 * @return
	 */
	public List<String> getRedirect(){
		return redirect;
	}

	/**
	 * Devuelve el inputStream del fichero
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Establece el inputStream del fichero
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Devuelve el nombre del fichero seleccionado
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Establece el nombre del fichero seleccionado
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Devuelve el fichero a subir al servidor
	 * @return
	 */
	public File getUploadFile(){
		return uploadFile;
	}
	
	/**
	 * Establece el fichero a subir al servidor
	 * @param uploadFile
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	/**
	 * Establece el nombre del fichero a subir al servidor
	 * @param filename
	 */
	public void setUploadFileFileName(String filename){
		this.filename = filename;
	}
	
	/**
	 * Establece el contentType del fichero a subir al servidor
	 * @param contentType
	 */
	public void setUploadFileContentType(String contentType) {
        this.contentType = contentType;
     }
	
	/**
	 * Establece el formato "yyyy-MM-dd-HH-mm-ss"
	 * para la fecha de los archivos .bak cuando subimos un fichero
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static String getDateFormatted(Date fecha, String formato){
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
        // En este caso se obtiene la fecha de hoy.
        String stringDate = null;
        if (fecha != null) {
            try {
                stringDate = formatter.format(fecha);
            } catch (Exception e) {
                stringDate = null;
            }
        }
        return stringDate;
	}
	
	/**
	 * Devuelve la fecha actual del sistema
	 * @return
	 */
    public static Date getDateNow() {
        GregorianCalendar calendar = new GregorianCalendar();
        Date currentDate = calendar.getTime();
        return currentDate;
    }
}
