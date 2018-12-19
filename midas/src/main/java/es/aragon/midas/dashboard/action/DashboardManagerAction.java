package es.aragon.midas.dashboard.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.dashboard.dao.IDBDashboardDAO;
import es.aragon.midas.dashboard.jpa.DBDashboard;
import es.aragon.midas.config.AppProperties;;


public class DashboardManagerAction extends MidasActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013990616646110411L;
	private static final String TEMPLATES = "templates";
	
	{
		setGrantRequired("DBOARD_ADMIN");
	}

	@EJB(name="DBDashboardDAO")
	IDBDashboardDAO dBoardDao ;
	
	/**
	 * Ejecución por defecto. Lleva a la página principal de dashboards
	 */
	@Override
	public String execute() {
		return LIST;
	}

	
	/**
	 * Muestra el listado de dashboards definidos.
	 * @return
	 */
	public String list() {
		return LIST;
	}
	
	/**
	 * Muestra el listado de plantillas definid1s.
	 * @return
	 */
	public String templates() {
		return TEMPLATES;
	}
	
	
	/**
	 * Returns the complete list of dashboards
	 * @return
	 */
	public List<DBDashboard> getDbList(){
		return dBoardDao.find();
	}
	
	
	/**
	 * Returns the list of files in "templates" static directory
	 *
	 * @return
	 */
	public List<Template> getTemplates() {
		String basePath = AppProperties.getParameter("midas.staticContent.path");
		
		File templateDir = new File(basePath + "dashboard/templates/");
		List<String> names = new ArrayList<String>(Arrays.asList(templateDir.list()));
		Collections.sort(names);
		
		List<Template> templates = new ArrayList<Template>();
		
		for(String name: names) {
	    	try {
		    	BufferedReader templateReader = new BufferedReader(new FileReader (basePath + "dashboard/templates/" + name));
		    	String templateLine = null;
		    	StringBuffer content = new StringBuffer();

		        	while((templateLine = templateReader.readLine()) != null) {
			        	content.append(templateLine);
			        }
			        templates.add(new Template(name, content));
		        templateReader.close();
	        } catch (IOException e) {
	        	log.error("Error leyendo plantilla", e);
	        }
		}
		return templates;
	}
	
	
	/**
	 * 
	 * @author carlos
	 *
	 */
	private class Template {
		private String name;
		private StringBuffer content;
		
		public Template(String n, StringBuffer c) {
			setName(n);
			setContent(c);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public StringBuffer getContent() {
			return content;
		}

		public void setContent(StringBuffer content) {
			this.content = content;
		}
	}
	
}
