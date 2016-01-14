package es.aragon.midas.util;

import java.io.*;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;


public class FileEncoder  {

  /**
   * Clave de encriptacion.
   */
  private static String key = "@#12aK%_";



  /**
   * Encripta un fichero.
   * @param inFile Nombre del fichero a encriptar.
   * @param outFile Nombre del fichero encriptado.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void encrypt(String inFile, String outFile)
    throws FileNotFoundException, IOException
  {
    try {
      FileInputStream fis = new FileInputStream(new File(inFile));
      FileOutputStream fos = new FileOutputStream(new File(outFile));
      DESKeySpec dke = new DESKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, skf.generateSecret(dke));
      CipherOutputStream cos = new CipherOutputStream(fos, cipher);
      GZIPOutputStream gzos = new GZIPOutputStream(cos);

      byte[] buffer = new byte[1024];
      int counter = 0;
      
      try {
        counter = fis.read(buffer);
        while (counter > 0) {
          gzos.write(buffer, 0, counter);
          counter = fis.read(buffer);
        }
      } catch (Exception eof) {
        eof.printStackTrace();
      }

      gzos.close();
      cos.close();
      fos.close();
      fis.close();
    }
    catch (Exception ex) {
      throw new IOException(ex.getMessage());
    }
  }


  /**
   * Encripta un fichero. El fichero resultante tiene como nombre el mismo
   * del original añadiendo la extension ".des".
   * @param inFile Nombre del fichero.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void encrypt(String inFile)
    throws FileNotFoundException, IOException
  {
    encrypt(inFile, inFile+".des");
  }

  /**
   * Encripta un fichero. El fichero resultante tiene como nombre el mismo
   * del original añadiendo la extension ".des".
   * @param inFile Nombre del fichero.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void encode(String inFile)
    throws FileNotFoundException, IOException
  {
    encrypt(inFile, inFile+".des");
  }



  /**
   * Desencripta un fichero.
   * @param inFile Nombre del fichero a desencriptar.
   * @param outFile Nombre del fichero desencriptado.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void decrypt(String inFile, String outFile)
    throws FileNotFoundException, IOException
  {
    try {
      FileInputStream fis = new FileInputStream(new File(inFile));
      FileOutputStream fos = new FileOutputStream(new File(outFile));
      DESKeySpec dke = new DESKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skf.generateSecret(dke));
      CipherInputStream cis = new CipherInputStream(fis, cipher);
      GZIPInputStream gzis = new GZIPInputStream(cis);

      byte[] buffer = new byte[1024];
      int counter = 0;

      while (true) {
        counter = gzis.read(buffer);
        if (counter == -1) break;
        fos.write(buffer,0,counter);
      }

      gzis.close();
      cis.close();
      fis.close();
      fos.close();
    }

    catch (Exception ex) {
      throw new IOException(ex.getMessage());
    }
  }


  /**
   * Desencripta un fichero. El fichero resultante tiene como nombre el mismo
   * del original eliminando la extension, que supuestamente es ".des".
   * @param inFile Nombre del fichero.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void decrypt(String inFile)
    throws FileNotFoundException, IOException
  {
     decrypt(inFile, inFile.substring(0, inFile.length() - 4));
  }


  /**
   * Desencripta un fichero. El fichero resultante tiene como nombre el mismo
   * del original eliminando la extension, que supuestamente es ".des".
   * @param inFile Nombre del fichero.
   * @throws java.io.FileNotFoundException
   * @throws java.io.IOException
   */
  public static void decode(String inFile)
    throws FileNotFoundException, IOException
  {
     decrypt(inFile, inFile.substring(0, inFile.length() - 4));
  }

  /**
   * Encripta una cadena de texto.
   */
  public static String encryptString(String source)
  {
    String retval = null;
    if (source.length() < 16) {
      for  (int i = source.length() ; i < 16 ; i++)
        source += " ";
    }
    
    try{
      ByteArrayInputStream bais = new ByteArrayInputStream (source.getBytes("ISO-8859-1"));
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DESKeySpec dke = new DESKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, skf.generateSecret(dke));
      CipherOutputStream cos = new CipherOutputStream(baos, cipher);
      
      byte[] buffer = new byte[1024];
      int counter = 0;
      
      counter = bais.read(buffer);
      cos.write(buffer, 0, counter);

      cos.flush();
      retval = new String(Base64.encodeBase64(baos.toByteArray()));
      cos.close();
      baos.close();
      bais.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
      return retval;
  }


  /**
   * Desencripta una cadena de texto
   */

   public static String decryptString(String source)
  {
    String retval = null;
    
    try{
      ByteArrayInputStream bais = new ByteArrayInputStream (Base64.decodeBase64(source.getBytes()));
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DESKeySpec dke = new DESKeySpec(key.getBytes("UTF-8"));
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skf.generateSecret(dke));
      CipherInputStream cis = new CipherInputStream(bais, cipher);
      
      byte[] buffer = new byte[1024];
      int counter = 0;
      
      counter = cis.read(buffer);
      baos.write(buffer, 0, counter);

      baos.flush();
      retval = baos.toString("ISO-8859-1").trim();
      baos.close();
      cis.close();
      bais.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
      return retval;
  }



}