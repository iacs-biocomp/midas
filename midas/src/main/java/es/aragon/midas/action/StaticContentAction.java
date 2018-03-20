package es.aragon.midas.action;

import java.io.FileInputStream;
import java.io.OutputStream;
import es.aragon.midas.config.AppProperties;

/**
 * Envia por defecto a la página inicial de la aplicación
 * @author Carlos
 *
 */
public class StaticContentAction extends MidasContentSupport {

	private static final long serialVersionUID = 1L;

	{
	setGrantRequired("PUBLIC");
	}

	String basePath = AppProperties.getParameter("midas.staticContent.path");
	String src;

	
	public void setSrc(String src) {
	    this.src = src;    
	}
	
	
	
	/**
	 * Método por defecto
	 */
	public String execute() {
		try {
			FileInputStream fis = new FileInputStream(basePath + src);
			log.debug("sirviendo archivo " + basePath + src);
			OutputStream os = response.getOutputStream();
			org.apache.commons.io.IOUtils.copy(fis, os);
			os.flush();
			fis.close();
			os.close();
		} catch(Exception fnfe) {
			return ERROR;
		}
		return null;
	}



	

}
