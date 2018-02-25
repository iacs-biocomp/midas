package es.aragon.midas.action;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.config.AppProperties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;

import es.aragon.midas.dashboard.jpa.DBDashboard;
import es.aragon.midas.dashboard.jpa.DBFrame;
import es.aragon.midas.dashboard.renderer.*;
import es.aragon.midas.dashboard.config.Constants;
import es.aragon.midas.dashboard.dao.IDBDashboardDAO;


public class DashboardAction extends MidasActionSupport {


	private static final long serialVersionUID = 5054997216428264049L;
	private static final String AUDIT_ENTITY = "Dashboard";

	private static final String orderRegex = "(.*)\\{order:(\\d+)\\}(.*)";	
	private static final Pattern orderPattern = Pattern.compile(orderRegex);

	

	//VARIABLES DE LISTADO
	private DBDashboard dashboard;
	protected StringBuilder content;
	protected StringBuilder script;
	protected StringBuilder scriptGlobal;
	
	private long dbNumber;
	
	//DESDE ESTE EJB REALIZAMOS LA OBTENCI�N DE DATOS DE LA BASE DE DATOS
	@EJB(name="DBDashboardDAO")
	private IDBDashboardDAO dashboardDao;

	{
		setGrantRequired("PUBLIC");
	}
		
	
	/* <-------------------- ACCIONES -----------------> */
	
	/**
	 * 
	 * @return Devuelve el listado de las zonas
	 */
	public String show() {

		String basePath = AppProperties.getParameter("midas.staticContent.path");

		BufferedReader templateReader = null;	// template reader
    	String template;	    				// template for dashboard
    	List<DBFrame> frames;				// list of frames in template
    	DBFrame frame = null;				// Each frame in dashboard
    	Set<String> jscripts = new HashSet<String>();
		BufferedReader jsReader = null;		// frame snippet reader
		
		audit.log("DBS", AUDIT_ENTITY, user.getUserName(), "El usuario ha accedido a visualizar el DashBoard " + dbNumber);
		
		try{
			dashboard = dashboardDao.findById(dbNumber);
			if (dashboard == null)
				throw new Exception("no existe el dashboard " + dbNumber);
			
		    
			if (!user.isGranted(dashboard.getGrant())) {
		    	return Constants.ERROR;	
		    }
			
			
			
			try {
		    	template = dashboard.getTemplate();
		    	frames = dashboard.getDBFrames();
		    	
		    	templateReader = new BufferedReader(new FileReader (basePath + "dashboard/templates/" + template));
		    	String templateLine = null;
			    content = new StringBuilder();
			    script = new StringBuilder();
			    scriptGlobal = new StringBuilder();
			    String ls = System.getProperty("line.separator");
	
		        while((templateLine = templateReader.readLine()) != null) {

		        	Matcher orderMatcher = orderPattern.matcher(templateLine);
		        	
		        	// transform line
		        	if (orderMatcher.matches()) {
		        		
		        		content.append(orderMatcher.group(1));
		        		
		        		// get the frame by order
		        		int order = Integer.parseInt(orderMatcher.group(2));

		        		for (DBFrame fr : frames) {

		        			// seleccionamos el frame con el número de orden indicado en la plantilla.
		        			if (fr.getOrder() == order) {
		        				
		        				// controlamos que el usuario esté autorizado
		        				if (user.isGranted(fr.getGrant())) {
			        				frame = fr;
			        				String rendererName = frame.getDBFrameType().getRenderer();
			        				IRenderer r = instantiate(rendererName, IRenderer.class);
			        				
			        				if (r != null && r instanceof IRenderer) {
			        					r.render(content, script, frame, this.user);
			        					
			        					String js = frame.getDBFrameType().getJs();
			        					if (js != null && !js.isEmpty()) {
			        						jscripts.add(js);
			        					}
			        				
			        				} else {
			        					log.error("No se ha encontrado la clase " + rendererName);
			        				}
		        				} else {
		        					log.warn("El usuario " + user.getUserName() + " no está autorizado a visualizar el frame " + dashboard.getId() + "/" + order);
		        				}
		        					
		        			}
		        		}

			        	content.append(orderMatcher.group(3));

		        	} else { //no match {order:} in template
			        	content.append(templateLine);
		        	}
		        	
		        	content.append(ls);
		        }

		    } finally {
		    	if (templateReader != null)
		    			templateReader.close();
			
		    }
			
			
			
			// Insert jscripts once
			for (String js: jscripts) {
				
				// If frame_type js, insert now
				try {
				    String jsLine = null;
				    
					if (js != null && !js.isEmpty()) {
						log.debug(" Leyendo frame script " + js);
						jsReader = new BufferedReader(new FileReader (basePath + "dashboard/scripts/" + js));
						
						// insert frame lines
						while((jsLine = jsReader.readLine()) != null) {
							scriptGlobal.append(jsLine);
							scriptGlobal.append(Constants.LS);
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
			}
			scriptGlobal.append(script);
			return Constants.SHOW;
			
		} catch(Exception e){
			log.error(" Se ha lanzado una excepción: " + e, e);
			
			return Constants.ERROR;
		}

	}

	
	
	
	
	
	

	/**
	 * Capture db parameter from URL and set into internal dbNumber var
	 * @param db
	 */
	public void setDb(long db) {
		dbNumber = db;
	}
	
	
	
	/**
	 * returns content as string
	 * @return content as string
	 */
	public String getContent() {
		return content.toString();
	}
	

	/**
	 * returns script as string
	 * @return script as string
	 */
	public String getScript() {
		return scriptGlobal.toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return dashboard.getDescription();
	}
		
	
	public <T> T instantiate(final String className, final Class<T> type){
	    try{
	        return type.cast(Class.forName(className).newInstance());
	    } catch(InstantiationException
	          | IllegalAccessException
	          | ClassNotFoundException e){
	        throw new IllegalStateException(e);
	    }
	}
	
	
	
	
	
	
}
