/**
 * 
 */
package es.aragon.midas.dashboard.renderer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;


import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.dashboard.jpa.DBSeriesData;
import es.aragon.midas.logging.Logger;
import es.aragon.midas.dashboard.config.Constants;
import es.aragon.midas.dashboard.dao.IDBDataDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author carlos
 *
 */
public class MorrisLineRenderer implements IRenderer {
	

    protected Logger log = new Logger();
	
    private IDBDataDAO dao;

    
    
    
	/* (non-Javadoc)
	 * @see es.aragon.iacs.biganPortal.renderer.IRenderer#render(java.lang.StringBuilder, java.lang.StringBuilder, int)
	 */
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user) {

		String basePath = AppProperties.getParameter("midas.staticContent.path");
		String snippet = null;					// frame snippet source
		BufferedReader frameReader = null;		// frame snippet reader
	    String frameLine = null;
	    int order = frame.getOrder();
	    
		snippet = frame.getDBFrameType().getSnippet();
		ArrayList<String> yKeys = new ArrayList<String>();
		ArrayList<String> yLabels = new ArrayList<String>();


		try {
			dao = (IDBDataDAO) new InitialContext().lookup("java:module/DBDataDAO");
		} catch (NamingException e) {
			log.error("Error accediendo a EJB BiganDataDAO");
		}
		
		
		try {
			try {
				frameReader = new BufferedReader(new FileReader (basePath + "dashboard/frames/" + snippet));
				log.debug(" Leyendo frame " + snippet);
				
				// insert frame lines
				while((frameLine = frameReader.readLine()) != null) {

					Matcher titleMatcher = Constants.TITLEPATTERN.matcher(frameLine);
					Matcher frameMatcher = Constants.FRAMEPATTERN.matcher(frameLine);
				
		        	if (frameMatcher.matches()) {
		        		
		        		content.append(frameMatcher.group(1) + order);
		        		content.append(frameMatcher.group(2));
		        		
		        	} else if (titleMatcher.matches()) {
		        		content.append(titleMatcher.group(1));
		        		content.append(frame.getTitle());
		        		content.append(titleMatcher.group(2));
		        		
		        	} else {
		        		content.append(frameLine);
		        	}
		        	content.append(Constants.LS);
		
				}
				
				script.append("$(function () {");
				script.append("\"use strict\";");
				script.append("var lineChart" + order + " = new Morris.Line({");
				script.append("element: 'morris-line-chart-" + order + "',");
				script.append("data: 	[");
				

				//DATA

				log.debug("dao: " + dao.toString());
				
				List<DBSeriesData> data = dao.getData(frame.getDBQuery().getQuery());
				Iterator<DBSeriesData> it = data.iterator();
				
				DBSeriesData datum = null;
				String serieAct = "";
				String serie = "";
				boolean firstSerie = true;
				
				if (it.hasNext()) {
					datum = (DBSeriesData) it.next();
					serie = datum.getXvalue();
				}
				
				while (it.hasNext()) {
					serieAct = serie;
					script.append("{y: '" + serie + "', " + datum.getYkey() + ": " + datum.getYvalue());
					if (firstSerie) {
						yKeys.add("'" + datum.getYkey() + "'");
						yLabels.add("'" + datum.getYlabel() + "'");
					}
					datum = (DBSeriesData) it.next();
					serie = datum.getXvalue();
					while (it.hasNext() && serie.equals(serieAct) ) {
						script.append(", " + datum.getYkey() + ": " + datum.getYvalue());
						if (firstSerie) {
							yKeys.add(",'" + datum.getYkey() + "'");
							yLabels.add(",'" + datum.getYlabel() + "'");
						}
						datum = (DBSeriesData) it.next();
						serie = datum.getXvalue();
					}
					
					if (it.hasNext()) {
						script.append("},");
					} else {
						script.append(", " + datum.getYkey() + ": " + datum.getYvalue() + "}");
					}
					firstSerie = false;
				}
				// END of DATA

				script.append("],");
				script.append("xkey: 'y',");
				script.append("ykeys:  [");
				for (String key: yKeys) {
					script.append(key);
				}
				script.append("],");
				script.append("labels: [");
				for (String label: yLabels) {
					script.append(label);
				}
				script.append("],");
				script.append("hideHover: 'auto',");
				script.append("resize: true,");
				script.append("lineColors: ['#4682B4', '#3CB371', '#8B8B00', '#CD8500', '#CD3700', '#388E8E', '#C67171', '#8E388E'],");
				script.append("});});");
				
				
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
