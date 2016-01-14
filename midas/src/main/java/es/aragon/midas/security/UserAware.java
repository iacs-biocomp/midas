package es.aragon.midas.security;

import es.aragon.midas.config.MidUser;

public interface UserAware {
	void setUser(MidUser user);
}
