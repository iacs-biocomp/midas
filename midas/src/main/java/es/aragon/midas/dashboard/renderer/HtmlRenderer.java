/**
 * 
 */
package es.aragon.midas.dashboard.renderer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;


import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.dashboard.util.DashboardUtils;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.dashboard.config.Constants;


/**
 * @author carlos
 *
 */
public class HtmlRenderer implements IRenderer {
	

    protected Logger log = new Logger();
	


    
    
	/* (non-Javadoc)
	 * @see es.aragon.iacs.biganPortal.renderer.IRenderer#render(java.lang.StringBuilder, java.lang.StringBuilder, int)
	 */
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user) {

		String basePath = AppProperties.getParameter("midas.staticContent.path");
		String snippet = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
	    String frameLine = null;
		String js = null;					// frame snippet source
		BufferedReader jsReader = null;		// frame snippet reader
		DashboardUtils utils = new DashboardUtils();
		
	    snippet = frame.getDBFrameType().getSnippet();
	    int order = frame.getOrder();		
		js = frame.getDBFrameType().getJs();
		
		
		try {
			try {
				frameReader = new BufferedReader(new FileReader (basePath + "dashboard/frames/" + snippet));
				log.debug(" Leyendo frame " + snippet);
				
				// insert frame lines
				while((frameLine = frameReader.readLine()) != null) {

					Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);
					Matcher bodyMatcher = Constants.BODYPATTERN.matcher(frameLine);
				
		        	if (bodyMatcher.matches()) {
		        		
		        		content.append(bodyMatcher.group(1));
		        		insertBody(content, script, frame);
		        		content.append(bodyMatcher.group(2));
		        		
		        	} else if (titleMatcher.matches()) {
		        		content.append(titleMatcher.group(1));
		        		content.append(frame.getTitle());
		        		content.append(titleMatcher.group(2));
		        		
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
			
			try {
				
				if (js != null) {
					jsReader = new BufferedReader(new FileReader (basePath + "dashboard/scripts/" + js));
					log.debug(" Leyendo frame " + js);
					
					// insert frame lines
					while((frameLine = jsReader.readLine()) != null) {
						
						Matcher urlMatcher = Constants.URLPATTERN.matcher(frameLine);
						Matcher frameMatcher = Constants.FRAMEPATTERN.matcher(frameLine);					
						
						if (urlMatcher.matches()) {
			        		
							script.append(urlMatcher.group(1));
							script.append(utils.parseUserData(frame.getPath(), user));
							script.append(urlMatcher.group(2));
			        		
			        	} else if (frameMatcher.matches()) {

			        		script.append(frameMatcher.group(1) + order);
			        		script.append(frameMatcher.group(2));
			        	} else {
			        		script.append(frameLine);
			        	}
						script.append(Constants.LS);
			
					}
					
				}
				
				
			} catch (FileNotFoundException fnfe) {
	
				log.error("Fichero no encontrado:" + js);
				
			} catch (IOException ioe) {
				
				log.error("Error leyendo fichero:" + js);
	
		    } finally {
				
		    	jsReader.close();
		    }			
			
		} catch (Exception e) {

			log.error("Excepción no controlada " + e, e);
			e.printStackTrace();

		}
		

	}
	

	
	/**
	 * Inserta un snippet de html en el bloque {body}
	 * @param content
	 * @param frame
	 */
	private void insertBody(StringBuilder content,  StringBuilder script, DBFrame frame) {
		String basePath = AppProperties.getParameter("midas.staticContent.path");
		String snippet = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
	    String frameLine = null;

	    snippet = frame.getPath();
		String js = frame.getJs();
		
		try {
			// leemos y enviamos snippet del frame
			try {
				frameReader = new BufferedReader(new FileReader (basePath + "dashboard/html/" + snippet));
				log.debug(" Leyendo contenido de " + snippet);
				while((frameLine = frameReader.readLine()) != null) {
					content.append(frameLine);
				}
		
			} catch (FileNotFoundException fnfe) {
				
				log.error("Fichero no encontrado:" + snippet);
				
			} catch (IOException ioe) {
				
				log.error("Error leyendo fichero:" + snippet);
	
		    } finally {
				
		    	frameReader.close();
			
		    }
			// leemos y enviamos javascript del frame
			try {
				if (js != null) {
					frameReader = new BufferedReader(new FileReader (basePath + "dashboard/scripts/" + js));
					log.debug(" Leyendo contenido de " + js);
					while((frameLine = frameReader.readLine()) != null) {
						script.append(frameLine);
					}
				}
			} catch (FileNotFoundException fnfe) {
				
				log.error("Fichero no encontrado:" + js);
				
			} catch (IOException ioe) {
				
				log.error("Error leyendo fichero:" + js);
	
		    } finally {
				
		    	frameReader.close();
			
		    }
			
		} catch (Exception e) {

			log.error("Excepción no controlada " + e, e);
			e.printStackTrace();

		}		
		
		
	}

}
