package es.aragon.midas.util;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class Utils {
    private final static String[] LETRAS_DNI = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
    
    public final static String JAVA_DATE_FORMAT = "dd/MM/yyyy";
    public final static String JAVA_TIME_FORMAT = "HH:mm";
    public final static String JAVA_DATETIME_FORMAT = JAVA_DATE_FORMAT+" "+JAVA_TIME_FORMAT;
    
    public final static String SQL_DATE_FORMAT = "dd/MM/yyyy";
    public final static String SQL_TIME_FORMAT = "HH24:MI:SS";
    public final static String SQL_DATETIME_FORMAT = SQL_DATE_FORMAT+" "+SQL_TIME_FORMAT;
    
    public final static String HL7_DATE_FORMAT = "yyyyMMdd000000";
    public final static String HL7_TIME_FORMAT = "HHmmss";
    public final static String HL7_DATETIME_FORMAT = "yyyyMMddHHmmss";
    
    
    /**
     * Parse a string into a Date
     * @param date
     * @return
     */
    public static Date parseDate(String date, String format){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Parse a string into a Date
     * @param date
     * @return
     */
    public static String formatDate(Date date, String format){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }catch(Exception e){
            return null;
        }
    }
    
    
	/**
	 * Convert the elements of a list into String and join them using the
	 * separator
	 *
	 * @param list A list of objects
	 * @param separator String used as element separator
	 * @return String with concatenated elements
	 */
	public static String joinList(List<String> list, String separator) {
		StringBuffer sb = new StringBuffer();
		try{
			int i = 0;
			for (; i < list.size()-1; i++) {
				sb.append(list.get(i)+separator);
			}
			sb.append(list.get(i));
		}catch(Exception ex) {}
		return sb.toString();
	}


	/**
	 * Make first letter in upper case
	 * @param str
	 * @return
	 */
	public static String firstUpper(String str) {
		if (str == null || str.length() < 2){
			return str;
		}
		String first = str.substring(0,1).toUpperCase();
		String rest = str.substring(1);
		return first+rest;
	}


	/**
	 * Strips first item, using "sep" as item separator
	 * @param str String with items
	 * @param sep String item separator
	 * @return String without the first element
	 */
	public static String stripFirst(String str, String sep) {
		return str.substring(str.indexOf(sep)+1);
	}


	/**
	 * Strips last item, using "sep" as item separator
	 * @param str String with items
	 * @param sep String item separator
	 * @return String without the last element
	 */
	public static String stripLast(String str, String sep) {
		return str.substring(0, str.lastIndexOf(sep));
	}


	/**
	 * Converts a name in the style XXX_YYY in something like xxxYyy
	 * @param str
	 * @return
	 */
	public static String nameDbToBean(String str, boolean stripPrefix){
		if (str == null || str.trim().length() < 1){
			return str;
		}
		StringBuffer sb = new StringBuffer();
		str = str.toLowerCase().trim();
		String[] parts = str.split("_");
		for (int i = 0; i < parts.length; i++){
			if (i == 0){
				if (!stripPrefix){
					sb.append(parts[i]);
				}
			}else if(i == 1 && stripPrefix){
				sb.append(parts[i]);
			}else{
				sb.append(firstUpper(parts[i]));
			}
		}

		return sb.toString();
	}


	public static String nameDbToBean(String str){
		return nameDbToBean(str, false);
	}

	/**
	 * Converts a name in the style xxxYyy or XxxYyy in something like XXX_YYY
	 * @param str
	 * @return
	 */
	public static String nameBeanToDb(String str){
		if (str == null || str.trim().length() < 1){
			return str;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if (Character.isUpperCase(c) && i > 0){
				sb.append("_");
			}
			sb.append(c);
		}

		return sb.toString().toUpperCase();
	}

	/**
	 * Search for a value in a bean and if it doesn't exist, look up for it at HashMap.
     * If it doesn't exist in bean neither in HashMap, returns default value.
	 * @param name Name of the property
	 * @param bean Bean object. It can be null
	 * @param moreProperties HashMap with additional properties. It can be null
     * @param defaultVal Default value
	 * @return
	 */
	public static Object getValue(String name, Object bean, Map<String, Object> moreProperties, Object defaultVal) {
		Object value = null;
		try{
		    value = PropertyUtils.getProperty(bean, name);
		}catch(Exception ex1){
			try{
			    value = moreProperties.get(name);
			}catch(Exception ex2){
                value = defaultVal;
            }
		}

		return value;
	}
    
    
    /**
     * Search for a value in a bean and if it doesn't exist, look up for it at HashMap.
     * If it doesn't exist in bean neither in HashMap, returns null.
     * @param name Name of the property
     * @param bean Bean object. It can be null
     * @param moreProperties HashMap with additional properties. It can be null
     * @return
     */
    public static Object getValue(String name, Object bean, Map<String, Object> moreProperties) {
        return getValue(name, bean, moreProperties, null);
    }


	/**
	 * Generates a string with <code>len</code> lenght filled with <code>c</code>
     * character.
	 * @param len Generated string's lenght
	 * @param c Filling character
	 * @return String
	 */
	public static String genStr(int len, char c){
		if (len <= 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++){
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Fill a string with <code>filchar</code> character
	 * @param len Lenght of the resulting string
	 * @param str Original string
	 * @param fillchar Character to fill with
	 * @param direction -1 for left padding, 1 for right padding and 0 for both.
	 * @return
	 */
	public static String fill(int len, String str, char fillchar, int direction){
		String ret = str;
		if (str == null){
			return ret;
		}
        
        //Fill from left
        if (direction < 0){
			ret = genStr(len-str.length(), fillchar)+str;
        //Fill from right
        }else if (direction > 0){
			ret = str + genStr(len-str.length(), fillchar);
        //Fill both
        }else{
			String tmp = genStr((len-str.length())/2, fillchar);
			ret = tmp+str+tmp;
			if (ret.length() < len){
				ret = fillchar+ret;
			}
		}

		return ret;
	}

	/**
	 *
	 * @param len
	 * @param str
	 * @param fillchar
	 * @return
	 */
	public static String fillLeft(int len, String str, char fillchar){
		return fill(len, str, fillchar, -1);
	}

	/**
	 *
	 * @param len
	 * @param str
	 * @param fillchar
	 * @return
	 */
	public static String fillRight(int len, String str, char fillchar){
		return fill(len, str, fillchar, 1);
	}

	/**
	 *
	 * @param len
	 * @param str
	 * @param fillchar
	 * @return
	 */
	public static String fillBoth(int len, String str,char fillchar){
		return fill(len, str, fillchar, 0);
	}
    
    
    /**
     * Calcula la letra del NIF
     * @param dni
     * @return
     */
    public static String getLetraNIF(long dni){
        return LETRAS_DNI[(int)(dni % 23)];
    }
    
    /**
     * Comprueba si el DNI acaba en letra, y si no se la calcula
     * @param dni
     * @return DNI con la letra del NIF
     */
    public static String getNIF(String dni){
        try{
            long tmp = Long.parseLong(dni.trim().toLowerCase(), 10);
            return tmp+getLetraNIF(tmp);
        }catch(Exception e){
            return dni;
        }
    }
}
