package es.aragon.midas.util;

import java.text.*;
import java.text.DateFormat;
import java.util.*;

public class MidasFormat  {

  /**
   * Formatea una fecha.
   * Data una fecha, devuelve un String en formato "dd/mm/aaaa".
   */
  public static String dateFormat(Date x) {
    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new java.util.Locale("es", "ES"));
    if (x == null)
      return "";
    else 
      return df.format(x);
  }
  
  
  /**
   * Formatea una hora.
   * Data una hora, devuelve un String en formato "hh:mm".
   */
  public static String timeFormat(Date x) {
    DateFormat df2 = DateFormat.getTimeInstance(DateFormat.SHORT, new java.util.Locale("es", "ES"));
    if (x == null)
      return "";
    else
      return df2.format(x);
  }

  public static Date parseDate(String inp) {
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date;
    try {
     date = (Date)formatter.parse(inp);
    } catch (ParseException pe) {
      date = null;
    }
    return date;
  }

  public static String toString(Object o) {
    String retval;
    if (o == null) 
      retval = "";
    else
      retval = o.toString();
    return retval;
  }
  
  public static String toSiNo(Object o) {
    if (toString(o).equals("-1")) {
      return "Si";
    } else {
      return "No";
    }
    
  }

  public static String toSiNo(boolean b) {
    if (b) {
      return "Si";
    } else {
      return "No";
    }
  }


  public static String decimalFormat(Object n) {
    DecimalFormat pc = new DecimalFormat("##0.00");
    double val = ((java.math.BigDecimal)n).doubleValue();
    return pc.format(val);
  }


   public static String decimalFormat(double n) {
    DecimalFormat pc = new DecimalFormat("##0.00");
    return pc.format(n);
  }



  public static String integerFormat(Object n) {
    DecimalFormat pc = new DecimalFormat("##0");
    double val = ((java.math.BigDecimal)n).doubleValue();
    return pc.format(val);
  }

  /**
   * Convierte cadenas de texto que representan fechas
   * en objetos java.sql.Date.
   */
  public static java.sql.Date stringToSQLDate(String s) 
    throws ParseException {
    SimpleDateFormat daytime = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date p;
    Date parsed = new Date();
    parsed = daytime.parse(s);
    p = new java.sql.Date(parsed.getTime());
    return p;
  }


  public static String dateToString(Date d) {
    SimpleDateFormat daytime = new SimpleDateFormat("dd/MM/yyyy");
    return daytime.format(d);
  }



}