package es.aragon.midas.util;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;


/**
 */
public class PasswordHash {

  public static String getPasswordHash(String password) {
     byte[] buf;
     byte[] digest1;
     String passCalc="";
     MessageDigest algorithm;

     try {
       buf = password.getBytes("ISO-8859-1");
       algorithm = MessageDigest.getInstance("SHA-1");
       algorithm.reset();
       algorithm.update(buf);
       digest1 = algorithm.digest();
       passCalc = new String(Base64.encodeBase64(digest1));
     } catch (Exception e) {
       System.err.println("Error en el algoritmo Hash de password");
       System.err.println(e.getMessage());
     }
     return passCalc;    
  }
  
}
