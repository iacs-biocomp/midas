package es.aragon.midas.action;


/**
 * Acción para la recarga las propiedades desde BD o fichero
 * @author Carlos
 *
 */
public class ReloadAction extends MidasActionSupport {

	private static final long serialVersionUID = 1L;

	{
	setGrantRequired("PUBLIC");
	}

    /**
     * Método por defecto. Ejecuta la recarga y vuelve a la página por defecto.
     */
	@Override
	public String execute() {
            es.aragon.midas.config.AppProperties.reload();
            return SUCCESS;
	}
	

}
