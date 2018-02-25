package es.aragon.midas.dashboard.renderer;

import es.aragon.midas.config.MidUser;
import es.aragon.midas.dashboard.jpa.DBFrame;

public interface IRenderer {
	
	public void render(StringBuilder content, StringBuilder script, DBFrame frame, MidUser user);

}
