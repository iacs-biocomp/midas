package es.aragon.midas.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import es.aragon.midas.logging.Logger;


/**
 * 
 * @author carlos
 * Clase para comprimir y descomprimir archivos usando el algoritmo ZIP
 */
public class FileZip {
  private static Logger log = new Logger();
  private static final int BUFFER = 2048;

/*  public static void main(String[] args) {
    final String SOURCE_ZIPDIR = "F:/knpcode/Parent.zip";
    // creating the destination dir using the zip file path
    // by truncating the ".zip" part
    String DESTINATION_DIR = SOURCE_ZIPDIR.substring(0, SOURCE_ZIPDIR.lastIndexOf('.'));
    //System.out.println("" + DESTINATION_DIR);
    extract(SOURCE_ZIPDIR, DESTINATION_DIR);
  }
*/
  
  /**
   * Extrae un fichero zip en una carpeta indicada
   * @param source
   * @param dest
   */
  public static int extract(String source, String dest){
    try {
      log.trace("descomprimiendo fichero " + source);	
      File root = new File(dest);
      if(!root.exists()){
    	log.trace("Creando directorio " + root.getName());  
        root.mkdir();
      }
//      BufferedOutputStream bos = null;
      // zipped input
      FileInputStream fis = new FileInputStream(source);
      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
      ZipEntry entry;
      while((entry = zis.getNextEntry()) != null) {
        String fileName = entry.getName();
        log.trace("extrayendo ... " + dest + File.separator + fileName);  
        
        File file = new File(dest + File.separator + fileName);
        if (!entry.isDirectory()) {
          extractFileContentFromArchive(file, zis);
        }
        else{
          if(!file.exists()){
            file.mkdirs();
          }
        }
        zis.closeEntry();
      }
      zis.close();
      log.debug("Desccomprimido fichero " + source);
      return 0;
    } catch(Exception e) {
      log.error("Error descomprimiendo fichero", e);	
      e.printStackTrace();
      return 1;
    }
  }
	
  /**
   * Extrae el contenido de un fichero. Esta función es llamada por unzip
   * @param file
   * @param zis
   * @throws IOException
   */
  private static void extractFileContentFromArchive(File file, ZipInputStream zis) throws IOException{
    file.getParentFile().mkdirs();
    file.createNewFile();
	FileOutputStream fos = new FileOutputStream(file);
    BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
    int len = 0;
    byte data[] = new byte[BUFFER];
    while ((len = zis.read(data, 0, BUFFER)) != -1) {
      bos.write(data, 0, len);
    }
    bos.flush();
    bos.close();
  }
}	
	
	
