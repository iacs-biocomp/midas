/**
 * 
 */
package es.aragon.midas.dashboard.renderer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.dashboard.config.Constants;
import es.aragon.midas.dashboard.util.DashboardUtils;


/**
 * @author carlos
 *
 */
public class BasicRenderer implements IRenderer {
	

    protected Logger log = new Logger();
	
    
	/* (non-Javadoc)
	 * @see es.aragon.iacs.biganPortal.renderer.IRenderer#render(java.lang.StringBuilder, java.lang.StringBuilder, int)
	 */
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user) {
		
		String basePath = AppProperties.getParameter("midas.staticContent.path");
		DashboardUtils utils = new DashboardUtils();
		String snippet = null;					// frame snippet source
		String js = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
		BufferedReader jsReader = null;		// frame snippet reader
	    String frameLine = null;
	    int order = frame.getOrder();
	    int suborder = frame.getSuborder();
	    
		snippet = frame.getDBFrameType().getSnippet();
		js = frame.getDBFrameType().getJs();

		
		// Insert frametype snippet
		try {
			try {
				frameReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "dashboard/frames/" + snippet), "UTF-8"));
						//new FileReader (basePath + "dashboard/frames/" + snippet));

				log.debug(" Leyendo frame " + order + "_" + suborder + ": " + snippet);
				
				// insert frame lines
				while((frameLine = frameReader.readLine()) != null) {
					
					Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);
					Matcher textMatcher = Constants.TEXTPATTERN.matcher(frameLine);
					Matcher urlMatcher = Constants.URLPATTERN.matcher(frameLine);
					Matcher frameMatcher = Constants.FRAMEPATTERN.matcher(frameLine);	
					Matcher bodyMatcher = Constants.BODYPATTERN.matcher(frameLine);					
					
		        	if (titleMatcher.matches()) {
		        		content.append(titleMatcher.group(1));
		        		content.append(frame.getTitle());
		        		content.append(titleMatcher.group(2));
		        		
		        	} else 	if (bodyMatcher.matches()) {
		        		
		        		content.append(bodyMatcher.group(1));
		        		insertBody(content, script, frame);
		        		content.append(bodyMatcher.group(2));
		        		
		        	} else if (textMatcher.matches()) {
		        		content.append(textMatcher.group(1));
		        		if (frame.getComment() != null)
		        			content.append(frame.getComment());
		        		content.append(textMatcher.group(2));
		        		
		        	} else if (urlMatcher.matches()) {
		        		
		        		content.append(urlMatcher.group(1));
		        		content.append(utils.parseUrl(utils.parseUserData(frame.getPath(), user)));
		        		content.append(urlMatcher.group(2));
		        		
		        	} else if (frameMatcher.matches()) {
		        		content.append(frameMatcher.group(1) + order + "_" + suborder);
		        		content.append(frameMatcher.group(2));
		        	} else {
		        		content.append(frameLine);
		        	}
		        	content.append(Constants.LS);
		
				}
				
			} catch (FileNotFoundException fnfe) {
				
				log.error("Fichero no encontrado:" + order + "_" + suborder + ": " + snippet);
				
			} catch (IOException ioe) {
				
				log.error("Error leyendo fichero:" + snippet);
	
		    } finally {
				
		    	frameReader.close();
		    }	

			
			// If frame_type js, insert now
/*			try {
				
				if (js != null && !js.isEmpty()) {
					log.debug(" Leyendo frame script " + js);
					jsReader = new BufferedReader(new FileReader (basePath + "dashboard/scripts/" + js));
					
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
				if (jsReader != null)
					jsReader.close();
		    }
*/
			
			
			// leemos y enviamos javascript del frame
			js = frame.getJs();			
			
			try {
				if (js != null  && !js.isEmpty()) {
					log.debug(" Leyendo contenido de " + js);
					jsReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "dashboard/scripts/" + js), "UTF-8"));
					//jsReader = new BufferedReader(new FileReader (basePath + "dashboard/scripts/" + js));
					while((frameLine = jsReader.readLine()) != null) {
						Matcher frameMatcher = Constants.FRAMEPATTERN.matcher(frameLine);	
						Matcher urlMatcher = Constants.URLPATTERN.matcher(frameLine);
						Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);

						if (frameMatcher.matches()) {
			        		script.append(frameMatcher.group(1) + order + "_" + suborder);
			        		script.append(frameMatcher.group(2));
						} else if (urlMatcher.matches()) {
							script.append(urlMatcher.group(1));
							script.append(utils.parseUrl(utils.parseUserData(frame.getPath(), user)));
			        		script.append(urlMatcher.group(2));
						} else if (titleMatcher.matches()) {
							script.append(titleMatcher.group(1));
							script.append(frame.getTitle());
							script.append(titleMatcher.group(2));
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
		    	if (jsReader != null)
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

		
		try {
			// leemos y enviamos snippet del frame
			try {
				log.debug(" Leyendo contenido de " + snippet);
				frameReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "html/" + snippet), "UTF-8"));
				//frameReader = new BufferedReader(new FileReader (basePath + "dashboard/html/" + snippet));
				while((frameLine = frameReader.readLine()) != null) {
					content.append(frameLine);
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
