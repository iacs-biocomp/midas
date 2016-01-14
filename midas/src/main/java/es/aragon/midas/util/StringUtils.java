package es.aragon.midas.util;

public class StringUtils {

	public static boolean nb (String s){
		if (s == null || s.equals(""))
			return true;
		else
			return false;
	}

	public static String nvl(String source, String ifNullVal, String elseVal) {
		return (source == null ? ifNullVal : elseVal);
	}
	
}
