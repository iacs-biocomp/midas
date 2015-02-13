package es.aragon.midas.action;

import java.util.List;

import javax.ejb.EJB;

import es.aragon.midas.config.MidContext;
import es.aragon.midas.dao.ContextsDAO;

public class ContextsAction extends MidasActionSupport {
	
	static final long serialVersionUID = 1L;

	{
		setGrantRequired("ADMIN");
	}
	
	@EJB(name="ContextsDAO")
	ContextsDAO contextsDAO;
	
	private MidContext context = new MidContext();

	@Override
	public String execute() {
		return "contexts";
	}

	public String list() {
		return "contexts";
	}
	
	public String nuevo() {
		if(context != null && context.getCxId() != null){
			contextsDAO.save(context);
		}
		return "contexts";
	}
	
	public String borrar() {
		if(context != null && context.getCxId() != null){
			contextsDAO.delete(context);
		}
		return "contexts";
	}
	
	public MidContext getContext() {
		return context;
	}

	public void setContext(MidContext context) {
		this.context = context;
	}
	
	public List<MidContext> getContexts(){
		return contextsDAO.findAll();
	}
}
