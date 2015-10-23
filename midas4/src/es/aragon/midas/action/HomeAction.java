package es.aragon.midas.action;

/**
 * Envia por defecto a la página inicial de la aplicación
 * @author Carlos
 *
 */
public class HomeAction extends MidasActionSupport {

	private static final long serialVersionUID = 1L;

	{
	setGrantRequired("PUBLIC");
	}

	
	/**
	 * Método por defecto
	 */
	public String execute() {
		return SUCCESS;
	}
	

}
