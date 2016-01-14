package es.aragon.midas.action;


import java.sql.Connection;

import javax.ejb.EJB;

import es.aragon.midas.action.MidasActionSupport;
import es.aragon.midas.config.AppProperties;
import es.aragon.midas.dao.ReportsDAO;



/**
 * Clase que implementa las funciones basicas para la gestion de Reports JASPER
 * como un tipo de resultado en el modelo Struts2.
 * 
 * Las Actions de una aplicacion que gestionen la generacione de informes Jasper, deben extender esta clase.
 * Ademas, es importante que asignen el valor del informe a generar (setReportID), y el formato del informe, 
 * salvo que se quiera utilizar el formato por defecto (PDF).
 * 
 * La asignacion del ReportID puede hacerse explicitamente, o implicitamente desde parametros de llamada a la Action, o 
 * desde formulario JSP (asignacion automatica del parametro desde el Value Stack).
 * 
 * 
 * @author Carlos
 *
 */
public class MidasJasperAction extends MidasActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ruta base de los informes Jasper
	 */
	private String jasperPath;
	
	/**
	 * Ruta relativa del informe a generar
	 */
	private String reportPath;
	
	/**
	 * Ruta completa al informe (base + relativa)
	 */
	private String fullPath;
	
	/**
	 * Formato de salida del informe. Por defectom PDF
	 */
	private String format = "PDF";
	
	/**
	 * Conexion a B.D. para la generaci�n del informe
	 */
	private Connection connection;
	
	/**
	 * ID del informe a generar, a partir de la tabla MID_REPORTS
	 */
	private String reportID;
	

	
	@EJB(name="ReportsDAO")
	ReportsDAO ReportsDAO;
	
	
	/**
	 * cons. por defecto.
	 */
	public MidasJasperAction() {
		super();
		jasperPath = AppProperties.getParameter("midas.jasperPath");
	}
	

	/**
	 * Ejecucion del informe por defecto
	 */
	@Override
	public String execute() {
		return SUCCESS;
	}
	
	/**
	 * Devuelve el formato del informe
	 * @return
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Establece el formato del informe a generar:
	 * Posibles valores: 
	 * <ul>
	 * 	<li>PDF</li> 
	 * 	<li>HTML</li> 
	 * 	<li>RTF</li> 
	 * 	<li>XLS</li> 
	 * 	<li>XML</li> 
	 * </ul>
	 * 
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	/**
	 * Establece el ID del informe, a partir de la tabla MID_REPORTS
	 * @param id
	 */
	public void setReportID(String id) {
		reportID = id;
		reportPath = ReportsDAO.findById(id).getValue();
	}
	
	/**
	 * Devuelve el ID del informe a generar.
	 * @return
	 */
	public String getReportID() {
		return reportID;
	}
	
	
	/**
	 * Establece el full path del fichero *.jasper
	 * @param fp
	 */
	public void setFullPath(String fp) {
		fullPath = fp;
	}
	
	/**
	 * Obtiene el full path del fichero *.jasper. Por defecto, jasperPath + reportPath.
	 * @return
	 */
	public String getFullPath() {
		//if (fullPath.isEmpty())
			fullPath = jasperPath + reportPath;
		return fullPath;
	}
	
	/**
	 * Devuelve la conexion a la B.D., obtenida desde el Entity Manager del paquete Midas
	 * @return
	 */
	public Connection getConnection() {
		connection = ReportsDAO.getConnection();		
		return connection;
	}

	
	/**
	 * Libera la conexion a la B.D., obtenida desde el Entity Manager del paquete Midas
	 * @return
	 */
	public void closeConnection(Connection conn) {
		ReportsDAO.closeConnection(conn);		
	}	
	
	/**
	 * Asigna una conexion a B.D.
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
