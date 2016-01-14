package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.dao.ContextsDAO;



/**
 * Acción que implementa los métodos para el mantenimiento de contextos
 * @author Carlos
 *
 */
public class ContextsAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="ContextsDAO")
	ContextsDAO contextsDAO;
	
	private MidContext context = new MidContext();

	
	/**
	 * Ejecución por defecto. Lleva a la página principal de contextos
	 */
	@Override
	public String execute() {
		return "contexts";
	}

	
	/**
	 * Muestra el listado de contextos definidos.
	 * @return
	 */
	public String list() {
		return "contexts";
	}
	
	
	/**
	 * Guarda en BD un nuevo contexto creado
	 * @return
	 */
	public String nuevo() {
		if(context != null && context.getCxId() != null){
			contextsDAO.save(context);
		}
		return "contexts";
	}
	
	
	/**
	 * Borra de BD el contexto indicado en la variable context
	 * @return
	 */
	public String borrar() {
		if(context != null && context.getCxId() != null){
			contextsDAO.delete(context);
		}
		return "contexts";
	}
	
	
	
	/**
	 * Struts. Obtiene el contexto en sesión (JPA -> JSP)
	 * @return
	 */
	public MidContext getContext() {
		return context;
	}

	
	
	/**
	 * Struts. Asigna el contexto en sesión (JSP -> JPA)
	 * @param context
	 */
	public void setContext(MidContext context) {
		this.context = context;
	}
	
	
	/**
	 * Devuelve una lista de contextos (JPA -> JSP)
	 * @return
	 */
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}
}
