/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.exception;

/**
 *
 * @author carlos
 */
public class MidasJPAException extends MidasException {
    
  /**
	 * 
	 */
	private static final long serialVersionUID = 4191851569708782214L;

	/**
	 * 
	 * @param message
	 * @param ex
	 */
	public MidasJPAException (String message, Exception ex) {
		super(message, ex);
		
	}
	
	/**
	 * 
	 * @param message
	 */
	public MidasJPAException (String message) {
		super(message);
	}
    
}
