package es.aragon.midas.util;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SqlUtil {

	public final static String JAVA_SDF = "dd/MM/yyyy";
	public final static String SQL_SDF = "dd/mm/yyyy";
	public final static String JAVA_SDF_DATETIME = "dd/MM/yyyy HH:mm:ss";
	public final static String SQL_SDF_DATETIME = "dd/mm/yyyy HH24:MI:SS";

	/*
	 * TRABAJO CON FECHAS
	 */

	/**
	 * @param date
	 * @return cadena con el formato "dd/MM/yyyy"
	 */
	public static String dateToStr (Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(JAVA_SDF);
		String fechaStr = null;
		try {
			fechaStr = sdf.format(date);
		}
		catch (Exception e) { }
		return fechaStr;
	}

	/**
	 * @param date Fecha.
	 * @return Valor de la fecha con formato dd/mm/yy HH24:MI:SS para realizar la consulta de SQL.
	 */
	public static String dateToStrConHorasMinsSegundos(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(JAVA_SDF_DATETIME);
		String fechaStr = null;
		try {
			fechaStr = sdf.format(date);
		}
		catch (Exception e) { }
		return fechaStr;
	}

	/**
	 * @param date Cadena de texto con una fecha con formato dd/mm/yyyy
	 * @return Objeto Date
	 */
	public static String dateStrToSQL(String date){
		String fechaStr = "";
		try {
			fechaStr = "to_date('" + date.trim() + "','"+SQL_SDF+"')";
		}
		catch (Exception e) { }

		return fechaStr;
	}

	/**
	 * @param date Cadena de texto con una fecha con formato dd/mm/yyyy
	 * @return Objeto Date
	 */
	public static String dateToSQL(Date date){
		String fechaStr = "";
		try {
			fechaStr = "to_date('" + dateToStr(date) + "','"+SQL_SDF_DATETIME+"')";
		}
		catch (Exception e) { }

		return fechaStr;
	}

	/**
	 * @param date Cadena de texto con una fecha con formato dd/mm/yyyy
	 * @return Objeto Date
	 */
	public static String dateTimeToSQL(Date date){
		String fechaStr = "";
		try {
			fechaStr = "to_date('" + dateToStrConHorasMinsSegundos(date) + "','"+SQL_SDF_DATETIME+"')";
		}
		catch (Exception e) { }

		return fechaStr;
	}

	/**
	 * @param campo Campo de la base de datos.
	 * @param filtro Fecha en formato String.
	 * @param cadena Cadena con la que se quiere encadenar la consulta.
	 * @return String para el tratamiento en base de datos.
	 */
	public static String fechaAnteriorToSql(String campo, String filtro, String cadena) {
		String sql = "";
		if(filtro!=null && !filtro.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += campo + " <= " + dateStrToSQL(filtro);
		}
		return sql;
	}

	/**
	 * @param campo Campo de la base de datos.
	 * @param filtro Fecha en formato String.
	 * @param cadena Cadena con la que se quiere encadenar la consulta.
	 * @return String para el tratamiento en base de datos.
	 */
	public static String fechaPosteriorToSql(String campo, String filtro, String cadena) {
		String sql = "";
		if(filtro!=null && !filtro.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += campo + " >= " + dateStrToSQL(filtro);
		}
		return sql;
	}

	/**
	 * @param campo
	 * @param filtroFechaDesde
	 * @param filtroFechaHasta
	 * @param cadena
	 * @return Filtro SQL para comparar una fecha entre dos
	 */
	public static String fechasEntreToSql(String campo, String filtroFechaDesde, String filtroFechaHasta, String cadena) {
		String sql = "";
		if(filtroFechaDesde!= null && filtroFechaHasta != null && !filtroFechaDesde.equals("") && !filtroFechaHasta.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += campo + " BETWEEN " + dateStrToSQL(filtroFechaDesde) + " AND " + dateStrToSQL(filtroFechaHasta) + " ";
		}
		return sql;
	}

	/*
	 * TRABAJO CON STRINGS
	 */

	/**
	 * @param campo
	 * @param filtro
	 * @param cadena
	 * @return Filtro con el formato: where filtro = 'cadena'
	 */
	public static String stringToSql(String campo, String filtro, String cadena) {
		String sql = "";
		if(filtro!= null && !filtro.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += campo + " = '" + filtro + "'";
		}
		return sql;
	}

	/**
	 * @param campo
	 * @param filtro
	 * @param cadena
	 * @return Filtro con el formato: WHERE filtro = IN cadena
	 */
	public static String stringInToSql(String campo, String filtro, String cadena) {
		String sql = "";
		if(filtro!= null && !filtro.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += campo + " IN " + filtro;
		}
		return sql;
	}

	/**
	 * @param campo
	 * @param filtro
	 * @param cadena
	 * @return Filtro SQL que comprueba un booleano
	 */
	public static String booleanToSql(String campo, boolean filtro, String cadena) {
		String sql = "";
		if(filtro) {
			sql = insertWhereAnd(cadena);
			sql += campo + " = 'Si'";
		}
		return sql;
	}

	/**
	 * @param campo
	 * @param filtro
	 * @param cadena
	 * @return Filtro SQL para comparar texto con un LIKE
	 */
	public static String stringLikeToSql(String campo, String filtro, String cadena) {
		String sql = "";
		if(filtro!= null && !filtro.equals("")) {
			sql = insertWhereAnd(cadena);
			sql += "lower(" + campo + ") LIKE '%" + filtro.toLowerCase() + "%'";
		}
		return sql;
	}

	/**
	 * @param cadena
	 * @return Inserta un where o un and si ya hay un where
	 */
	public static String insertWhereAnd(String cadena) {
		String sql;
		if(cadena.toLowerCase().indexOf("where") != -1) {
			sql = " and ";
		}
		else {
			sql = " where ";
		}
		return sql;
	}

	/**
	 * @param cadena
	 * @return Cadena entre comillas simples y con comillas convenientemente escapadas
	 */
	public static String strToSQL(String cadena){
		String cadenaSQL = "''";
		try {
			cadenaSQL = "'" +  cadena.replaceAll("\"", "").replaceAll("'", "''") + "'";
		}
		catch (Exception e) { }
		return cadenaSQL;
	}

	/**
	 * Combierte un array de String en una cadena de texto entre paréntesis con
	 * cada string encerrado entre comillas y separado por comas, ideal para poder
	 * ser utilizado con el operador IN.
	 * @param array String[]
	 * @return Cadena con el formato siguiente: ('cadena[0]', 'cadena[1]', ..., 'cadena[N]')
	 */
	public static String strArrayToSql(String[] array) {
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < array.length; i++) {
			sb.append("'"+array[i]+"'");
			if (i < array.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");

		return sb.toString();
	}

	/**
	 * Obtiene un String de una columna del ResultSet y si es nulo devuelve el defaultValue
	 * @param rs ResultSet
	 * @param columnName Nombre de la columna
	 * @param defaultValue Valor en caso de que sea nulo
	 * @return
	 */
	public static String getStringDefault(ResultSet rs, String columnName, String defaultValue) {
		String value;
		try{
			value = rs.getString(columnName);
			if (value == null) {
				value = defaultValue;
			}
		}catch(Exception e) {
			value = defaultValue;
		}
		return value;
	}

	/*
	 * TRABAJO CON BOOLEANOS
	 */

	/**
	 * @param campo
	 * @param filtro
	 * @param cadena
	 * @param trueValue Si el campo vale trueValue, es cierto
	 * @return Filtro SQL que comprueba un booleano
	 */
	public static String booleanToSql(String campo, boolean filtro, String cadena, String trueValue) {
		String sql = "";
		if(filtro) {
			sql = insertWhereAnd(cadena);
			sql += campo + " = '"+trueValue+"'";
		}
		return sql;
	}



}
