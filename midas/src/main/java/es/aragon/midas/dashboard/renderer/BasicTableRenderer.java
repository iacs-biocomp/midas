/**
 * 
 */
package es.aragon.midas.dashboard.renderer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;


import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.dashboard.config.Constants;
import es.aragon.midas.dashboard.dao.IDBDataDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author carlos
 *
 */
public class BasicTableRenderer implements IRenderer {
	

    protected Logger log = new Logger();
	
    private IDBDataDAO dao;

    
    
	/* (non-Javadoc)
	 * @see es.aragon.iacs.biganPortal.renderer.IRenderer#render(java.lang.StringBuilder, java.lang.StringBuilder, int)
	 */
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user) {

		String basePath = AppProperties.getParameter("midas.staticContent.path");
		String snippet = null;					// frame snippet source
		String js = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
		BufferedReader jsReader = null;		// frame snippet reader
	    String frameLine = null;
	    int order = frame.getOrder();
	    int suborder = frame.getSuborder();
	    snippet = frame.getDBFrameType().getSnippet();
		js = frame.getDBFrameType().getJs();
	    

		try {
			dao = (IDBDataDAO) new InitialContext().lookup("java:module/DBDataDAO");
			log.debug("dao: " + dao.toString());
		
		} catch (NamingException e) {
			log.error("Error accediendo a EJB BiganDataDAO");
		}
		
		
		try {
			try {
				frameReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "dashboard/frames/" + snippet), "UTF-8"));
				log.debug(" Leyendo frame " + snippet);
				
				// insert frame lines
				while((frameLine = frameReader.readLine()) != null) {

					Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);
					Matcher headerMatcher = Constants.HEADERPATTERN.matcher(frameLine);
					Matcher dataMatcher = Constants.DATAPATTERN.matcher(frameLine);
					Matcher frameMatcher = Constants.FRAMEPATTERN.matcher(frameLine);	
					
				
		        	if (headerMatcher.matches()) {
		        		
		        		content.append(headerMatcher.group(1));
		        		content.append(getHeaders(frame));
		        		content.append(headerMatcher.group(2));
		        		
		        	} else if (titleMatcher.matches()) {
		        		content.append(titleMatcher.group(1));
		        		content.append(frame.getTitle());
		        		content.append(titleMatcher.group(2));
		        		
		        	} else if (dataMatcher.matches()) {
		        		content.append(dataMatcher.group(1));

						//List<Object[]> data = dao.getRawData(frame.getDBQuery().getQuery());
						List<Object[]> data = new ArrayList<Object[]>();
						List<String> headers = new ArrayList<String>();
						dao.getHeaders(frame.getDBQuery().getQuery(), headers, data);
						
						content.append("<thead><tr>");
						
						for (String c : headers){
							content.append("<th>" + c + "</th>");
						}
		        		
						content.append("</tr></thead><tbody>");

						for (Object[] row : data ) {
							content.append("<tr>");
							for (Object col : row) {
								content.append("<td>" + col + "</td>");
							}
							content.append("</tr>");
						}
						content.append("</tbody>");
		        		
		        		content.append(dataMatcher.group(2));
		        		
		        	} else if (frameMatcher.matches()) {
		        		content.append(frameMatcher.group(1) + order + "_" + suborder);
		        		content.append(frameMatcher.group(2));
		        	
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
			
			
/*			// If frame_type js, insert now
			try {
				
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
		    }*/
			
			
			// leemos y enviamos javascript del frame
			js = frame.getJs();			
			
			try {
				if (js != null  && !js.isEmpty()) {
					log.debug(" Leyendo contenido de " + js);
					jsReader = new BufferedReader(new InputStreamReader(new FileInputStream(basePath + "dashboard/scripts/" + js), "UTF-8"));
					while((frameLine = jsReader.readLine()) != null) {
						script.append(frameLine);
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
	 * Gets columns field from query table, and returns it as html header element
	 * @param frame whose query we are guessing
	 * @return string with html table header element
	 */
	private String getHeaders(DBFrame frame) {
		String header = "";
		String [] cols = frame.getDBQuery().getColumns().split(",");
	   for (String c : cols){
		   header += "<th>" + c + "</th>";
	   }
	   return header;

	}

}
