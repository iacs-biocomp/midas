package es.aragon.midas.action;

public class HomeAction extends MidasActionSupport {

	private static final long serialVersionUID = 1L;

	{
	setGrantRequired("PUBLIC");
	}

	public String execute() {
		return SUCCESS;
	}
	

}
