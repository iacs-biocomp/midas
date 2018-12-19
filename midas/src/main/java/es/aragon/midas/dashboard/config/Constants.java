package es.aragon.midas.dashboard.config;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Constants {
	
	final public static BigDecimal UNO = new BigDecimal("1");
	final public static BigDecimal CERO = new BigDecimal("0");

	final public static String SI = "si";
	final public static String NO = "no";
	final public static String ERROR = "error";
	final public static String SUCCESS = "success";
	final public static String SHOW = "show";
	
	
	
	
	private static final String frameRegex = "(.*)\\{frame_id\\}(.*)";	
	public static final Pattern FRAMEPATTERN = Pattern.compile(frameRegex);
	public static final String LS = System.getProperty("line.separator");
	private static final String bodyRegex = "(.*)\\{body\\}(.*)";	
	public static final Pattern BODYPATTERN = Pattern.compile(bodyRegex);
	private static final String srcRegex = "(.*(?:src|href)=\")(.*)(\".*)";	
	public static final Pattern SRCPATTERN = Pattern.compile(srcRegex);
	private static final String titleRegex = "(.*)\\{title\\}(.*)";	
	public static final Pattern TITLEPATTERN = Pattern.compile(titleRegex);
	private static final String textRegex = "(.*)\\{text\\}(.*)";	
	public static final Pattern TEXTPATTERN = Pattern.compile(textRegex);
	private static final String usrRegex = "(.*)\\{(cias|zbs|sectorn?|role|service|token)\\}(.*)";	
	public static final Pattern USRPATTERN = Pattern.compile(usrRegex);
	private static final String urlRegex = "(.*)\\{url\\}(.*)";	
	public static final Pattern URLPATTERN = Pattern.compile(urlRegex);
	private static final String headerRegex = "(.*)\\{header\\}(.*)";	
	public static final Pattern HEADERPATTERN = Pattern.compile(headerRegex);
	private static final String dataRegex = "(.*)\\{data\\}(.*)";	
	public static final Pattern DATAPATTERN = Pattern.compile(dataRegex);
	private static final String shinyRegex = "(.*)\\{ext_(.*)\\}(.*)";	
	public static final Pattern SHINYPATTERN = Pattern.compile(shinyRegex);
}
