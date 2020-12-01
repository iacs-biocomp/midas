package es.aragon.midas.security.auth;



import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.ws.guia.AuthGuiaResponse;
import es.aragon.midas.ws.guia.GuiaConnection;


/**
 * LoginValidator a traves de los servicios GUIA.
 * 
 *
 * @author carlos
 *
 */

public class GuiaCertValidator extends LoginValidatorBase {

	private Throwable guiaException;

    
    /**
     * 
     * @param username
     * @param password
     * @return
     */
    protected boolean delegatedValidation(String username, String password, MidUser user, boolean checkPassword) {
        boolean retval = false;

        log.trace("Usando GUIACERTValidator");
        
        if (checkPassword) {

        	try {
		    	GuiaConnection con = new GuiaConnection();
		        AuthGuiaResponse resp = null;
	
		        String response = con.auth(username.toLowerCase(), encryptData(password));
		        if (response != null) {
		        	log.trace(response);
		            resp = con.xmlMapping(response);
		        }
		        
		        if (resp != null && resp.getResult().equals("OK")) {
		        	retval = true;
		        } else {
		        	log.error("GUIA: Resultado no OK. " + response);
		        	retval = false;
		        }

        	} catch(Exception e) {
		        log.error("Error conectando a GUIA.", e);
		        retval = false;
		    }
        
        } else {
        	retval = true;
        }
        
        return retval;
    
    }
    
    
    /**
     * 
     * @param pass
     * @return
     * @throws Exception
     */
    public static String encryptData(String pass) throws Exception {
        BigInteger modulus = new
        BigInteger("BC6E2F7399CC7EAD886EEB09FD7668174961AF6FF40E74EE51DBBE1299F721078BBF5084DA00B591A4D94E89A84AC23752F28E070657E93E4AB24A355D36455FAFCF398F832D339CE104C5C0E34982437F4D21745242481479D491087DA9AEE3EA82730DC3F712104116457861EB944367EBC578727001E4E40F10FF6365BA03",
16);
        BigInteger exponent = new BigInteger("010001",16);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey pub = factory.generatePublic(spec);
        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.ENCRYPT_MODE, pub);

        return Base64.getEncoder().encodeToString(rsa.doFinal(pass.getBytes()));

    }    
  

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.aragon.midas.security.auth.LoginValidator#getException()
	 */
	public Throwable getException() {
		return guiaException;
	}

}
