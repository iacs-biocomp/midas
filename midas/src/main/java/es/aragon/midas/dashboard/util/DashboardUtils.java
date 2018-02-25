package es.aragon.midas.dashboard.util;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;

import es.aragon.midas.dashboard.config.Constants;
import es.aragon.midas.config.MidUser;

/**
 * 
 * @author carlos
 *
 */
public class DashboardUtils {
	
	public static HashMap<String, String> sectores = new HashMap<String, String>(10);
	
	
	public DashboardUtils() {
		sectores.put("AL", "42");
		sectores.put("BA", "12");
		sectores.put("CA", "32");
		sectores.put("HU", "11");
		sectores.put("TE", "41");
		sectores.put("Z1", "51");
		sectores.put("Z2", "21");
		sectores.put("Z3", "31");
		sectores.put("06", "06");
		sectores.put("GI", "");
	}
	
	/**
	 * 
	 * @param s
	 * @param user
	 * @return
	 */
	public String parseUserData (String s, MidUser user) {
		Matcher usrMatcher = Constants.USRPATTERN.matcher(s);

    	while (usrMatcher.matches()) {
    		String insertCode = usrMatcher.group(2);
    		String insertValue = null;
    		
    		if ("cias".equals(insertCode)) {
    			
    			if (!user.getContextValues("CIAS").isEmpty())
    				insertValue = "&CIAS=" + user.getContextValues("CIAS");
    			
    		} else if ("zbs".equals(insertCode)) {
    			Set<String> ucs = user.getContextSet("CSSUC");
				String tucs = null;
    			for (String uc: ucs) {
    				if (!uc.isEmpty() && uc.startsWith("X60")) {
    					tucs = uc.substring(3);
    					insertValue = "&zbs=" + tucs;
    					break;
    				}
    			}    			

    		} else if ("service".equals(insertCode)) {
    			Set<String> ucs = user.getContextSet("CSSUC");
				String tucs = null;
    			for (String uc: ucs) {
    				if (!uc.isEmpty() && !uc.startsWith("X60")) {
    					tucs = uc.substring(0,4);
    					insertValue = "&service=" + tucs;
    					break;
    				}
    			}    			
    		} else if ("sector".equals(insertCode)) {
    			if (user.getInfoUser() != null && !user.getInfoUser().getSecId().isEmpty())
    				insertValue = "&sector=" + sectores.get(user.getInfoUser().getSecId());

    		} else if ("role".equals(insertCode)) {
    			if (user.isGranted("FULL_COORD"))
    				insertValue = "&role=full_coord";
    			else if (user.isGranted("COORD_AE"))
    				insertValue = "&role=coord_ae";
    			else if (user.isGranted("ENF_AE"))
    				insertValue = "&role=enf_ae";
    			else if (user.isGranted("FEA_AE"))
    				insertValue = "&role=fea_ae";
    			else if (user.isGranted("SECTOR_COORD"))
    				insertValue = "&role=sector_coord";
    			else if (user.isGranted("AP_COORD"))
    				insertValue = "&role=ap_coord";
    			else if (user.isGranted("ENF_AP"))
    				insertValue = "&role=enf_ap";
    			else if (user.isGranted("FAC_AP"))
    				insertValue = "&role=fac_ap";
    		}
    		if (insertValue != null)
    			s = usrMatcher.group(1) + insertValue + usrMatcher.group(3);
    		else
    			s = usrMatcher.group(1) + usrMatcher.group(3);
    			
    		usrMatcher = Constants.USRPATTERN.matcher(s);
    	}
    	return s;
	}
}
