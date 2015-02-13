package es.aragon.midas.action;

public class ReloadAction extends MidasActionSupport {

	private static final long serialVersionUID = 1L;

	{
	setGrantRequired("PUBLIC");
	}

    @Override
	public String execute() {
            es.aragon.midas.config.AppProperties.reload();
            return SUCCESS;
	}
	

}
