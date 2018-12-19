/**
 * 
 */
package es.aragon.midas.dashboard.renderer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.dashboard.config.Constants;


/**
 * @author carlos
 *
 */
public class InfoPanelRenderer implements IRenderer {
	

    protected Logger log = new Logger();
	
    
	/* (non-Javadoc)
	 * @see es.aragon.iacs.biganPortal.renderer.IRenderer#render(java.lang.StringBuilder, java.lang.StringBuilder, int)
	 */
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user) {
		
		String basePath = AppProperties.getParameter("midas.staticContent.path");

		String snippet = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
	    String frameLine = null;

	    
		snippet = frame.getDBFrameType().getSnippet();

		
		try {
			try {
				frameReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "dashboard/frames/" + snippet), "UTF-8"));
				log.debug(" Leyendo frame " + snippet);
				
				// insert frame lines
				while((frameLine = frameReader.readLine()) != null) {
					
					Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);
					Matcher textMatcher = Constants.TEXTPATTERN.matcher(frameLine);
					
		        	if (titleMatcher.matches()) {
		        		content.append(titleMatcher.group(1));
		        		content.append(frame.getTitle());
		        		content.append(titleMatcher.group(2));
		        		
		        	} else if (textMatcher.matches()) {
		        		content.append(textMatcher.group(1));
		        		content.append(frame.getComment());
		        		content.append(textMatcher.group(2));
		        		
		        	} else {
		        		content.append(frameLine);
		        	}
		        	content.append(Constants.LS);
		
				}
				
				
			} catch (FileNotFoundException fnfe) {
	
				log.error("Fichero no encontrado:" + snippet);
				
			} catch (IOException ioe) {
				
				log.error("Error leyendo fichero:" + snippet);
	
		    } finally {
				
		    	frameReader.close();
			
		    }
			
		} catch (Exception e) {

			log.error("Excepción no controlada " + e, e);
			e.printStackTrace();

		}
		

	}

}
