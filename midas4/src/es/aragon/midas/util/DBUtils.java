package es.aragon.midas.util;

import es.aragon.midas.logging.Logger;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * <h1>Referencia r�pida</h1>
 * <ul>
 * <li>Generar una cadena de llamada a procedimiento:
 * <code>genCallString()</code></li>
 * <li>Insertar un bean: <code>callInsertBeanProcedure()</code></li>
 * <li>Obtener un bean por su PK: <code>callGetBeanProcedure()</code></li>
 * <li>Buscar varios beans: <code>callFindBeansProcedure()</code></li>
 * <li>Modificar un bean: <code>callUpdateBeanProcedure()</code></li>
 * <li>Borrar un bean: <code>callDeleteBeanProcedure()</code></li>
 * </ul>
 * 
 * <h1>Introducci�n</h1> Esta clase contiene m�todos de utilidad para invocar
 * procedimientos almacenados en una base de datos Oracle.
 * 
 * Los m�todos de esta clase cubren las siguientes operaciones:
 * <ul>
 * <li>Obtener informaci�n del procedimiento almacenado</li>
 * <li>Construir la cadena de llamada al procedimiento. Esta cadenas tienen la
 * forma: "{call proc_name(?,?,?)}"</li>
 * <li>Rellenar los par�metros de entrada y registrar los par�metros de salida</li>
 * <li>Invocar el procedimiento</li>
 * <li>Recoger los datos devueltos</li>
 * <li>Rellenar un bean con los datos recogidos</li>
 * </ul>
 * 
 * Para que todo funcione correctamente se debe seguir una nomenclatura de
 * nombres, bastante sencilla y de f�cil cumplimiento.
 * 
 * <h1>Nomenclatura de nombres</h1> Los nombres de los par�metros de las
 * funciones deben estar escritos separando cada parte del nombre por guiones.
 * Por ejemplo, un par�metro que acepte el nombre de un usuario deber�a llamarse
 * "NOMBRE_USUARIO". El nombre de la propiedad del bean deber� seguir los
 * est�ndares de nomenclatura de java, lo que significa que se escribir� todo
 * junto con la primera letra de cada componente en may�sculas. El ejemplo
 * anterior quedar�a como "nombreUsuario". Los m�todos de esta clase se encargan
 * de convertir entre los nombres cada vez que lo necesitan.
 * 
 * <h1>Tipos de datos</h1> Por otro lado, los tipos de datos tambi�n ser
 * similares. En los beans de la parte java se deben usar clases para los tipos
 * de datos, nunca los tipos b�sicos, o se producir�an NullPointerException en
 * caso de que se devuelva un valor NULL. Adem�s para los n�meros se deben usar
 * tipos de doble precisi�n, como "Double" o "Long".
 * 
 * <table>
 * <thead>
 * <tr>
 * <th>Oracle</th>
 * <th>Java</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>INTEGER, BIGINT, etc</td>
 * <td>Long</td>
 * </tr>
 * <tr>
 * <td>DOUBLE, DECIMAL, etc</td>
 * <td>Double</td>
 * </tr>
 * <tr>
 * <td>NUMBER</td>
 * <td>Long o Double</td>
 * </tr>
 * <tr>
 * <td>DATE</td>
 * <td>Date</td>
 * </tr>
 * <tr>
 * <td>TIMESTAMP</td>
 * <td>Date</td>
 * </tr>
 * <tr>
 * <td>ARRAY</td>
 * <td>Array de tipos b�sicos</td>
 * </tr>
 * <tr>
 * <td>CHAR y VARCHAR2</td>
 * <td>String</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * <h1>Creaci�n de la cadena de llamada al procedimiento</h1> Las cadenas de
 * llamada a procedimiento tienen todas las siguiente forma:
 * <code>{call esquema.paquete.procedimiento(?,?,?)}</code>
 * 
 * Cada "?" representa un par�metro de entrada o salida. El m�todo
 * <code>genCallString()</code> es el encargado de generar esta cadena a partir
 * de los datos del procedimiento. Para calcularla obtiene los metadatos del
 * procedimiento almacenado y extrae el n�mero de columnas del procedimiento. Si
 * se va a utilizar m�ltiples veces un reducido n�mero de llamadas, este m�todo
 * puede almacenar las cadenas generadas en un ArrayList y as� devolverlas la
 * pr�xima vez que se necesiten sin necesidad de volver a generarlas.
 * 
 * <h1>Invocaci�n del procedimiento</h1> El m�todo principal de invocaci�n a
 * procedimientos almacenados es <code>callProcedure()</code>. Este m�todo se
 * encarga de llamar al procedimiento de generaci�n de la cadena de llamada a
 * procedimiento, de rellenar los par�metros de entrada, registrar los de salida
 * y devolver los datos. Si alguno de los par�metros de salida es un tipo de
 * referencia, devolver� un ResultSet para extraer los datos del cursor. En caso
 * de tipos b�sicos realizar� las conversiones anteriormente descritas.
 * 
 * <h1>Wrappers de <code>callProcedure</code></h1> Este m�todo es muy potente
 * pero tambi�n muy complejo, por lo que hay una serie de wrappers para llamar a
 * ciertos tipos de procedimientos muy habituales, como guardar un Bean en la
 * base de datos, o eliminar un registro. Los wrappers disponibles son los
 * siguientes:
 * <ul>
 * <li><code>callDeleteBeanProcedure></code>: Invoca un procedimiento almacenado
 * que realizar� un "DELETE" de una fila de datos. Generalmente suelen recibir
 * un �nico par�metro (la PK) y no devuelven nada. El bean que se pasa como
 * par�metro de este m�todo debe llevar rellenada la propiedad que guarde la PK
 * de una fila</li>
 * 
 * <li><code>callFindBeansProcedure</code>: Invoca un procedumiento que acepta
 * tantos par�metros como campos de b�squeda. Los par�metros de b�squeda los
 * puede coger este m�todo de dos partes: de las propiedades del bean o del
 * <code>HashMap</code> que se pasan como par�metros. Los valores
 * <code>null</code> los convierte autom�ticamente en "%".</li>
 * 
 * <li><code>callGetBeanProcedure</code>: Invoka un procedumiento que obtiene
 * una �nica l�nea de datos de una tabla con la Primary Key especificada. Los
 * datos son devueltos en un par�metro de salida de tipo referencia (cursor).
 * Los valores devueltos en ese cursor se mapean automaticamente en las
 * propiedades del bean suministrado.</li>
 * 
 * <li><code>callInsertBeanProcedure</code>: Invoca a un procedimiento que har�
 * un INSERT de los datos del bean en la tabla correspondiente de la base de
 * datos. El procedimiento almacenado debe tener tantos par�metros de entrada
 * como columnas se almacenen en la tabla correspondiente de la base de datos.
 * Las propiedades del bean se extraen autom�ticamente y se pasan como
 * par�metros. Si la propiedad que almacena el valor de la PK es nula, deber�a
 * generarse este valor en el procedimiento almacenado antes de hacer un insert,
 * y devolverlo al realizar la inserci�n.</li>
 * 
 * <li><code>callSimpleGetProcedure</code>: Llama a un procedimiento simple que
 * devuelve uno o varios valores simples a partir de los par�metros indicados.
 * Los valores de los par�metros se pasan en un <code>HashMap</code>. Las claves
 * del hash ser�n los nombres de los par�metros del procedimiento <b>siguiendo
 * el estandar java</b>, como si fueran las propiedades de un bean. Este
 * procedimiento devuelve los datos obtenidos como otro <code>HashMap</code>,
 * donde los nombres de las claves son el nombre de los par�metros de salida del
 * procecimiento, y el valor, el valor devuelto</li>
 * 
 * <li><code>callSimpleProcedure</code>: Este es el procedimiento m�s sencillo,
 * y sirve para ejecutar un procedimiento almacenado que no devuelva ning�n
 * valor. De forma similar al anterior, los par�metros del procedimiento
 * almacenado se pasan mapeados en un <code>HashMap</code>.</li>
 * 
 * <li><code>callUpdateBeanProcedure</code>: Invoca a un procedimiento
 * almacenado que realizar� un UPDATE sobre una �nica fila de una base de datos.
 * Los valores del bean que se pasa de par�metro se usar�n para rellenar los
 * par�metros de entrada del procedimiento, y todos los par�metros rellenados se
 * usar�n para machacar los datos existente.</li>
 * </ul>
 * 
 * @author rcorral
 */
public class DBUtils {

	// private final static boolean COLUMN_PREFIX = true;

	// Debug option. When it is true, writes A LOT of debug lines
	// public static boolean TRACE = true;
	public static final boolean TRACE = false;

	static Logger log = new Logger();

	// If true, call strings are stored in a HashMap and build only one time
	public static final boolean cachedCalls = true;
	// Map where call strings are stored if cachedCalls == true
	public static final HashMap<String, String> callCache = new HashMap<String, String>();

	/**
	 * Prints all data in a ResultSet into a string. Useful for debugging.
	 * 
	 * @param rs
	 * @return String with printable data
	 * @throws SQLException
	 */
	public static String printData(ResultSet rs) throws SQLException {
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData rsmd;
		rsmd = rs.getMetaData();

		sb.append("Printing data...\n");
		// Datos
		int c = 1;
		while (rs.next()) {
			sb.append("Result number: ").append(c++).append("\n");
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				sb.append("- ").append(rsmd.getColumnName(i)).append("(").append(rsmd.getColumnTypeName(i)).append("): ").append(rs.getString(i)).append("\n");
			}
		}

		if (c == 1) {
			sb.append("-- No data returned! --");
		}

		return sb.toString();
	}

	/**
	 * Get Procedure info for a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Package name (it can be <code>null</code>)
	 * @param procName
	 *            Procedure name
	 * @return ResultSet with procedure's useful information
	 * @throws SQLException
	 */
	public static ResultSet getProcInfo(Connection con, String schema,
			String pkg, String procName) throws SQLException {
		ResultSet procInfo = null;

		// Separate schema and package
		String pkgName = pkg.toUpperCase();
		if (schema == null || schema.trim().equals("")) {
			schema = "%";
		}

		DatabaseMetaData dbmd = con.getMetaData();
		// If package is empty
		if (pkgName != null && !pkgName.trim().equals("")) {
			procInfo = dbmd.getProcedureColumns(pkgName, schema,
					procName.toUpperCase(), "%");
		} else if (schema != null && !schema.trim().equals("")) {
			procInfo = dbmd.getProcedureColumns("%", schema,
					procName.toUpperCase(), "%");
		}
		return procInfo;
	}

	/**
	 * Get Procedure info for a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 *            Procedure's schema
	 * @param procName
	 *            Procedure name
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getProcInfo(Connection con, String schema,
			String procName) throws SQLException {
		return getProcInfo(con, schema, null, procName);
	}

	/**
	 * Get Procedure info for a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure name
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getProcInfo(Connection con, String procName)
			throws SQLException {
		return getProcInfo(con, null, null, procName);
	}

	/**
	 * Obtains values from a ResultSet using pocedure's metadata to infer types
	 * 
	 * @param rs
	 *            ResultSet
	 * @param col
	 *            Column number, starting from 1
	 * @return Object with the extracted value
	 * @throws SQLException
	 */
	public static Object getValueFromRs(ResultSet rs, int col)
			throws SQLException {
		if (rs == null) {
			log.error("ResultSet is null");
			return null;
		}
		Object value = null;
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			int type = rsmd.getColumnType(col);
			switch (type) {
			case Types.NUMERIC:
			case Types.DECIMAL:
			case Types.DOUBLE:
			case Types.FLOAT:
			case Types.REAL:
				BigDecimal decimal = rs.getBigDecimal(col);
				if (decimal != null) {
					double dval = decimal.doubleValue();
					long lval = decimal.longValue();

					// If number hasn't decimal values, is a long type
					if (lval == dval) {
						value = lval;
					} else {
						value = dval;
					}
				}
				break;
			case Types.INTEGER:
			case Types.BIGINT:
			case Types.SMALLINT:
				value = rs.getLong(col);
				break;
			case OracleTypes.DATE:
				/*
				 * java.sql.Date date = rs.getDate(col); if (date != null){
				 * value = new Date(date.getTime()); } break;
				 */
			case OracleTypes.TIMESTAMP:
				Timestamp tstamp = rs.getTimestamp(col);
				if (tstamp != null) {
					value = new Date(tstamp.getTime());
				}
				break;
			default:
				// By default gets data as strings
				value = rs.getString(col);
				break;
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}

		if (TRACE)
			log.trace("getValueFromRs - "
					+ rsmd.getColumnName(col)
					+ "("
					+ (value != null ? value.getClass().getName()
							: "<null type>") + "): " + value);
		return value;
	}

	/**
	 * Set one param's value for a stored procedure
	 * 
	 * @param cstm
	 *            CallableStatement
	 * @param paramNumber
	 *            Param number into the call. Starts with 1.
	 * @param paramType
	 *            Param type (OracleType)
	 * @param value
	 *            Object with the value
	 * @param findProc
	 *            If <code>true</code>, null values are converted to '%' string
	 *            and null values are converted to "%" values
	 * @throws SQLException
	 */
	public static void setProcParam(CallableStatement cstm, int paramNumber,
			int paramType, Object value, boolean findProc) throws SQLException {

		if (cstm == null) {
			log.error("CallableStatement is null");
			return;
		}

		if (TRACE)
			log.trace("setProcParam - " + paramNumber + ": "
					+ (value == null ? "<null>" : "'" + value + "'")
					+ " (tipo: " + paramType + ")");

		// Check null values
		if (value == null) {
			if (findProc) {
				if (TRACE)
					log.trace(" > '%'");
				cstm.setString(paramNumber, "%");
				return;
			} else {
				if (TRACE)
					log.trace(" > null");
				cstm.setNull(paramNumber, paramType);
				return;
			}
		}

		// Check type
		try {
			switch (paramType) {
			// Real numbers
			case Types.NUMERIC:
			case Types.DECIMAL:
			case Types.DOUBLE:
			case Types.FLOAT:
			case Types.REAL:
				if (TRACE)
					log.trace(" > REAL NUMBER");
				if (value instanceof Integer || value instanceof Long) {
					cstm.setLong(paramNumber, (Long) value);
				} else {
					cstm.setDouble(paramNumber, (Double) value);
				}
				break;
			// Integer numbers
			case Types.INTEGER:
			case Types.BIGINT:
			case Types.SMALLINT:
				if (TRACE)
					log.trace(" > INTEGER NUMBER");
				cstm.setLong(paramNumber, (Long) value);
				break;
			case Types.DATE:
				/*
				 * if (TRACE)log.trace(" > DATE"); java.sql.Date tmpDate = new
				 * java.sql.Date(((Date)value).getTime());
				 * cstm.setDate(paramNumber, tmpDate); break;
				 */
			case Types.TIMESTAMP:
				if (TRACE)
					log.trace(" > TIMESTAMP");
				Timestamp tmpTimestamp = new Timestamp(((Date) value).getTime());
				cstm.setTimestamp(paramNumber, tmpTimestamp);
				break;
			case Types.ARRAY:
			case Types.OTHER:
				if (TRACE)
					log.trace(" > ARRAY/OTHER");
				if (value instanceof oracle.sql.ARRAY) {
					cstm.setArray(paramNumber, (ARRAY) value);
				}
				break;
			default:
				if (TRACE)
					log.trace(" > CHAR/VARCHAR");
				// Formatea las fechas
				if (value instanceof Date) {
					cstm.setString(paramNumber, Utils.formatDate((Date) value,
							Utils.SQL_DATETIME_FORMAT));
				} else {
					cstm.setString(paramNumber, value.toString());
				}
				break;
			}
		} catch (Exception ex) {
			log.error("setProcParam: " + ex, ex);
		}
	}

	/**
	 * Set one param's value for a stored procedure
	 * 
	 * @param cstm
	 *            CallableStatement
	 * @param paramNumber
	 *            Param number into the call. Starts with 1.
	 * @param paramType
	 *            Param type (OracleType)
	 * @param value
	 *            Object with the value
	 * @throws SQLException
	 */
	public static void setProcParam(CallableStatement cstm, int paramNumber,
			int paramType, Object value) throws SQLException {
		setProcParam(cstm, paramNumber, paramType, value, false);
	}

	/**
	 * Fill a bean with data in a ResultSet
	 * 
	 * @param rs
	 *            ResultSet
	 * @param bean
	 *            Bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fillBean(ResultSet rs, T bean) throws Exception {
		log.debug("iniciando el fillBean" + bean.toString());
		ResultSetMetaData rsmd = rs.getMetaData();
		T ret = bean;

		// Main loop. Iterates through resultset metadata
		int i = 1;
		log.debug("fillbean - numero de columnas " + rsmd.getColumnCount());
		for (; i <= rsmd.getColumnCount(); i++) {
			String dbColName = rsmd.getColumnName(i).toLowerCase();
			String beanColName = Utils.nameDbToBean(dbColName, true);

			Object value = getValueFromRs(rs, i);
			try {
				// Get column with prefix name
				PropertyUtils.setProperty(bean, beanColName, value);
			} catch (java.lang.NoSuchMethodException e) {
				try {
					// If it fails, get column without prefix name
					beanColName = Utils.nameDbToBean(dbColName, false);
					PropertyUtils.setProperty(bean, beanColName, value);
				} catch (java.lang.NoSuchMethodException e2) {
					log.debug("No se ha encontrado la propiedad " + beanColName
							+ " para el bean tipo " + bean.getClass().getName());
				}
			}

		}
		log.debug("fillbean - fin del for de las columnas");
		// log.debug("Readed "+(i-1)+" columns");
		ret = (T) BeanUtils.cloneBean(bean);
		log.debug("Finalizando el fillBean");
		return ret;
	}

	/**
	 * Generate call string for a stored procedure. Be careful: if procedure
	 * returns one or more <code>ResultSet</code>(cursors) they muast be <b>last
	 * parameters</b>.
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Package's name or <code>null</code> if the procedure doesn't
	 *            belongs to any package
	 * @param schema
	 *            Packages's schema
	 * @param procName
	 *            Procedure's name
	 * @return String with the generated call string
	 * @throws Exception
	 */
	public static String genCallString(Connection con, String schema,
			String pkg, String procName) throws Exception {

		StringBuilder sb = new StringBuilder();
		ArrayList<String> array = new ArrayList<String>();
		ResultSet procInfo = null;
		boolean refFound = false;
		int paramNum = 0;

		// Adjust package name
		String tmpPkg = "";
		if (pkg != null && !pkg.equals("")) {
			pkg = pkg.trim();
			tmpPkg = pkg + ".";
		}
		if (schema != null && !pkg.trim().equals("")) {
			tmpPkg = schema.trim() + "." + pkg + ".";
		}

		try {
			String callPath = tmpPkg + procName;

			// If cache is enabled an exist, return it
			if (cachedCalls && callCache.containsKey(callPath)) {
				log.debug("Returning cached call for '" + callPath + "'...");
				return callCache.get(callPath);
				// Else, build the call
			} else {
				log.debug("Generating call for '" + callPath + "'...");
				sb.append("{call ").append(callPath).append("(");

				procInfo = getProcInfo(con, schema, pkg, procName);

				// For each parameter add "?"

				while (procInfo.next()) {
					String paramName = procInfo.getString("COLUMN_NAME");
					int colType = procInfo.getInt("COLUMN_TYPE");

					// DATA_TYPE == 1111 is CURSOR REF
					if (paramName != null && !"null".equals(paramName)) {
						if (procInfo.getString("TYPE_NAME")
								.equals("REF CURSOR")) {
							array.add("?");
							refFound = true;
						} else if (colType == 1 || (colType == 2 && !refFound)) {
							array.add("?");
						}
					}
					paramNum++;
				}
				procInfo.close();

				sb.append(Utils.joinList(array, ", "));
				sb.append(")}");

				// Almacena en cache
				if (cachedCalls) {
					callCache.put(callPath, sb.toString());
				}
			}
			log.debug("Call generated (" + paramNum + "): " + sb.toString());
		} catch (Exception ex) {
			log.error("genCallString: " + ex.getLocalizedMessage(), ex);
		}

		return sb.toString();
	}

	/**
	 * Generate call string for a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 *            Schema
	 * @param procName
	 *            Procedure's name
	 * @return String
	 * @throws Exception
	 */
	public static String genCallString(Connection con, String schema,
			String procName) throws Exception {
		return genCallString(con, schema, null, procName);
	}

	/**
	 * Generate call string for a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure's name
	 * @return String
	 * @throws Exception
	 */
	public static String genCallString(Connection con, String procName)
			throws Exception {
		return genCallString(con, null, null, procName);
	}

	/**
	 * Get columns info for a table
	 * 
	 * @param con
	 *            Connection
	 * @param tableName
	 *            Table's name
	 * @return ResultSet with columns useful information
	 * @throws SQLException
	 */
	public static ResultSet getTableColumns(Connection con, String schema,
			String tableName) throws SQLException {
		ResultSet tableInfo;

		if (schema == null || schema.trim().equals("")) {
			schema = "%";
		}

		DatabaseMetaData dbmd = con.getMetaData();
		tableInfo = dbmd.getColumns("%", schema, tableName.toUpperCase(), "%");

		return tableInfo;
	}

	/**
	 * Get primary keys info for a table
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 *            Table�s schema
	 * @param tableName
	 *            Table's name
	 * @return ResultSet with primary keys useful information
	 * @throws SQLException
	 * 
	 *             public static ResultSet getTablePKs(Connection con, String
	 *             schema, String tableName) throws SQLException { ResultSet
	 *             pkInfo; tableName = tableName.toUpperCase();
	 * 
	 *             if (schema == null || schema.trim().equals("")){ schema =
	 *             "%"; }
	 * 
	 *             OracleDatabaseMetaData dbmd =
	 *             (OracleDatabaseMetaData)con.getMetaData(); pkInfo =
	 *             dbmd.getPrimaryKeys("%", schema, tableName);
	 * 
	 *             return pkInfo; }
	 */

	/**
	 * Call a stored procedure. It's the main method for calling a procedure.
	 * this is a very complex method. Beause of that there are severan wrappers
	 * with names starting with "call" prefix. Use them instead.
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 *            Procedure's schame or <code>null</code>.
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto mapped to param
	 *            names. It can be <code>null</code>.
	 * @param moreProperties
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @param returnResultSet
	 *            If <code>true</code>, last params are
	 *            <code>IN OUT CURSOR</code> param
	 * @param findProc
	 *            If <code>true</code>, parameter is used in a find-like method:
	 *            <code>null</code> values are converted to "%" values, and bean
	 *            values are ignored.
	 * @return Map<String, Object> with the returned objects (it can be empty)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callProcedure(Connection con,
			String schema, String pkg, String procName, Object bean,
			HashMap<String, Object> moreProperties, boolean returnResultSet,
			boolean findProc) throws Exception {
		ResultSet procInfo;
		CallableStatement cstm;

		ArrayList<Object[]> outParams = new ArrayList<Object[]>();
		HashMap<String, Object> ret = new HashMap<String, Object>();

		if (moreProperties == null) {
			moreProperties = new HashMap<String, Object>();
		}

		// Prepare CallableStatement
		log.debug("Generating call ...");
		String call = genCallString(con, schema, pkg, procName);

		// cstm.setFetchSize(50);
		// cstm = con.prepareCall(call);
		cstm = con.prepareCall(call, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);

		// Get proc info
		procInfo = getProcInfo(con, schema, pkg, procName);

		// Set IN parameters and register OUT parameters
		log.debug("Setting and registering parameters...");
		int c = 1; // Param number

		while (procInfo.next()) {
			// Get column info
			String paramName = procInfo.getString("COLUMN_NAME");
			int dataType = procInfo.getInt("DATA_TYPE");
			int colType = procInfo.getInt("COLUMN_TYPE");
			String strColType = procInfo.getString("TYPE_NAME");

			Object obj[] = new Object[2];
			if (paramName != null && !"null".equalsIgnoreCase(paramName)) {
				if (TRACE)
					log.trace(" - TIPO: " + dataType);
				// OUT cursor
				if (returnResultSet && strColType.equals("REF CURSOR")) {
					if (TRACE)
						log.trace("  - " + c + ": " + paramName + "("
								+ strColType + "): <OUT CURSOR>");
					cstm.registerOutParameter(c, OracleTypes.CURSOR);
					obj[0] = c;
					obj[1] = paramName;
					outParams.add(obj);

					// OUT parameter
				} else if (!returnResultSet
						&& colType == DatabaseMetaData.procedureColumnOut) {
					if (TRACE)
						log.trace("  - " + c + ": " + paramName + "("
								+ strColType + "): <OUT PARAMETER>");
					cstm.registerOutParameter(c, dataType);
					obj[0] = c;
					obj[1] = paramName;
					outParams.add(obj);

					// IN/OUT parameter
				} else if (!returnResultSet
						&& colType == DatabaseMetaData.procedureColumnInOut) {
					if (TRACE)
						log.trace("  - " + c + ": " + paramName + "("
								+ strColType + "): <IN/OUT PARAMETER>");
					// Register output parameter
					cstm.registerOutParameter(c, dataType);
					obj[0] = c;
					obj[1] = paramName;
					outParams.add(obj);

					// Register input data
					String propertyName = Utils.nameDbToBean(paramName);// Utils.nameDbToBean(paramName,
																		// true);
					Object value = null;
					if (findProc) {
						Object tmp = moreProperties.get(propertyName);
						if (tmp != null) {
							value = tmp;
						}
					} else {
						value = Utils.getValue(propertyName.trim(), bean,
								moreProperties);
					}
					// Only register input data if isn't null
					if (value != null) {
						setProcParam(cstm, c, dataType, value, findProc);
					}

					// IN parameters
				} else if (colType == DatabaseMetaData.procedureColumnIn) {
					if (TRACE)
						log.trace("  - " + c + ": " + paramName + "("
								+ strColType + "): <IN PARAMETER>");
					String propertyName = Utils.nameDbToBean(paramName);// Utils.nameDbToBean(paramName,
																		// true);
					// If procedures is for searching, null values are converted
					// to "%"
					Object value = null;
					if (findProc) {
						Object tmp = moreProperties.get(propertyName);
						if (tmp != null) {
							value = tmp;
						}
					} else {
						value = Utils.getValue(propertyName.trim(), bean,
								moreProperties);
					}
					setProcParam(cstm, c, dataType, value, findProc);

					// Unkown type
				} else {
					if (TRACE)
						log.debug("  - " + c + ": " + paramName + "("
								+ strColType + "): UNKNOWN TYPE '" + colType
								+ "'");
				}
			}
			c++;
		}

		log.debug("Calling stored procedure...");
		cstm.setFetchSize(100);
		// boolean res = cstm.execute();
		cstm.execute();

		log.debug("Saving results");
		for (Object[] tmp : outParams) {
			Object obj = cstm.getObject((Integer) tmp[0]);
			if (TRACE)
				log.trace("Obtaining result in " + tmp[0] + ":" + tmp[1] + " ("
						+ (obj != null ? obj.getClass().toString() : "<null>")
						+ ")");
			ret.put((String) tmp[1], obj);
		}

		log.debug("Stored procedures sucessfully called");
		return ret;
	}

	/**
	 * Call a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 *            Procedure's schema
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It can be <code>null</code>.
	 * @param moreProperties
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @param returnResultSet
	 *            If true, return result sets from cursors
	 * @return HashMap<String, Object> with the returned objects (it can be
	 *         empty)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callProcedure(Connection con,
			String schema, String procName, Object bean,
			HashMap<String, Object> moreProperties, boolean returnResultSet)
			throws Exception {
		return callProcedure(con, schema, null, procName, bean, moreProperties,
				returnResultSet, false);
	}

	/**
	 * Call a stored procedure
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It can be <code>null</code>.
	 * @param moreProperties
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @param getBean
	 *            If not <code>null</code>, last param is a
	 *            <code>IN OUT CURSOR</code> param
	 * @param
	 * @return HashMap<String, Object> with the returned objects (it can be
	 *         empty)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callProcedure(Connection con,
			String procName, Object bean,
			HashMap<String, Object> moreProperties, boolean returnResultSet)
			throws Exception {
		return callProcedure(con, null, null, procName, bean, moreProperties,
				returnResultSet, false);
	}

	/**
	 * Call a simple stored procedure without OUT parameters
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @throws Exception
	 */
	public static void callSimpleProcedure(Connection con, String schema,
			String pkg, String procName, HashMap<String, Object> params)
			throws Exception {
		callProcedure(con, schema, pkg, procName, null, params, false, false);
	}

	/**
	 * Call a simple stored procedure without OUT parameters
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @throws Exception
	 */
	public static void callSimpleProcedure(Connection con, String pkg,
			String procName, HashMap<String, Object> params) throws Exception {
		callProcedure(con, null, pkg, procName, null, params, false, false);
	}

	/**
	 * Call a simple stored procedure without OUT parameters
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @throws Exception
	 */
	public static void callSimpleProcedure(Connection con, String procName,
			HashMap<String, Object> params) throws Exception {
		callProcedure(con, null, null, procName, null, params, false, false);
	}

	/**
	 * Call a stored procedure that gets info from DB
	 * 
	 * @param con
	 *            Connection
	 * @param schema
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @return ArrayList with the returned objects (it can be an empty list)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callSimpleGetProcedure(
			Connection con, String schema, String pkg, String procName,
			HashMap<String, Object> params) throws Exception {
		return callProcedure(con, schema, pkg, procName, null, params, false,
				false);
	}

	/**
	 * Call a stored procedure that gets info from DB
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @return ArrayList with the returned objects (it can be an empty list)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callSimpleGetProcedure(
			Connection con, String pkg, String procName,
			HashMap<String, Object> params) throws Exception {
		return callProcedure(con, null, pkg, procName, null, params, false,
				false);
	}

	/**
	 * Call a stored procedure that gets info from DB
	 * 
	 * @param con
	 *            Connection
	 * @param procName
	 *            Procedure's name
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @return ArrayList with the returned objects (it can be an empty list)
	 * @throws Exception
	 */
	public static HashMap<String, Object> callSimpleGetProcedure(
			Connection con, String procName, HashMap<String, Object> params)
			throws Exception {
		return callProcedure(con, null, null, procName, null, params, false,
				false);
	}

	/**
	 * Call a stored procedure that gets a bean.
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object where the data will be stored. Properties are auto
	 *            mapped to param names. It <em>can't</em> be <code>null</code>,
	 *            but y usaully is empty.
	 * @param params
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @return ArrayList with the returned objects (it can be an empty list)
	 * @throws NoSuchElementException
	 *             Thrown when no data is get
	 * @throws Exception
	 */
	public static <T> void callGetBeanProcedure(Connection con, String schema,
			String pkg, String procName, T bean, HashMap<String, Object> params)
			throws Exception {
		// Call method
		HashMap<String, Object> map = callProcedure(con, schema, pkg, procName,
				bean, params, true, false);
		boolean empty = true;

		// Check results
		log.debug("callFindBeansProcedure - Preparando para recorrer el resultset");
		if (map != null && !map.isEmpty()) {
			ResultSet rs = null;
			// Search for ResultSet object
			for (String key : map.keySet()) {
				log.debug("callFindBeansProcedure - Recorriendo el map");
				Object obj = map.get(key);
				if (obj instanceof ResultSet) {
					rs = (ResultSet) obj;
					log.debug("callFindBeansProcedure - Preparando para recorrer el resultset");
					if (rs.next()) {
						empty = false;
						fillBean(rs, bean);
						rs.close();
					} else {
						bean = null;
					}
					log.debug("callFindBeansProcedure - Finalizando el recorrido del resultset");
					break;
				}
			}

		}
		log.debug("callFindBeansProcedure - Finalizado el recorrido del map");
		if (empty) {
			throw new NoSuchElementException("Bean not found");
		}

	}

	/**
	 * Call a stored procedure that gets several Java Beans with the especified
	 * filters.
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if not applicable.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Empty Bean object.
	 * @param filterBy
	 *            HashMap with additional properties. It can be
	 *            <code>null</code>.
	 * @return List with all found beans or null if there aren't occurrences
	 * @throws Exception
	 */
	public static <T> ArrayList<T> callFindBeansProcedure(Connection con,
			String schema, String pkg, String procName, T bean,
			HashMap<String, Object> filterBy) throws Exception {
		ArrayList<T> ret = new ArrayList<T>();

		// Call method
		HashMap<String, Object> map = callProcedure(con, schema, pkg, procName,
				bean, filterBy, true, true);

		// Check result
		log.debug("callFindBeansProcedure - Preparando para recorrer el map");
		if (map != null && map.size() > 0) {
			ResultSet rs = null;
			// Search for ResultSet object
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				log.debug("callFindBeansProcedure - Recorriendo el map");
				if (obj instanceof ResultSet) {
					rs = (ResultSet) obj;
					// Get all results
					log.debug("callFindBeansProcedure - Preparando para recorrer el resultset");
					while (rs.next()) {
						log.debug("Entra por el while del rs.next");
						try {

							ret.add(fillBean(rs, bean));
						} catch (Exception ex) {
							log.error(ex, ex);
						}
					}
					log.debug("callFindBeansProcedure - Finalizando el recorrido del resultset");
					rs.close();
				}
			}
			log.debug("callFindBeansProcedure - Finalizado el recorrido del map");
		}
		return ret;
	}

	/**
	 * Call a stored procedure updates bean data
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It can be <code>null</code>.
	 * @throws Exception
	 */
	public static void callUpdateBeanProcedure(Connection con, String schema,
			String pkg, String procName, Object bean) throws Exception {
		callProcedure(con, schema, pkg, procName, bean, null, false, false);
	}

	/**
	 * Call a stored procedure updates bean data
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It can be <code>null</code>.
	 * @throws Exception
	 */
	public static void callUpdateBeanProcedure(Connection con, String schema,
			String pkg, String procName, Object bean,
			HashMap<String, Object> params) throws Exception {
		callProcedure(con, schema, pkg, procName, bean, params, false, false);
	}

	/**
	 * Call a stored procedure that inserts bean data into database
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It <strong>can't<strong> be <code>null</code>.
	 * @return HashMap<String, Object> with the primary keys used in the
	 *         inserted data
	 * @throws Exception
	 */
	public static HashMap<String, Object> callInsertBeanProcedure(
			Connection con, String schema, String pkg, String procName,
			Object bean) throws Exception {
		HashMap<String, Object> hm = callProcedure(con, schema, pkg, procName,
				bean, null, false, false);
		for (String key : hm.keySet()) {
			String property = Utils.nameDbToBean(key);
			Object value = hm.get(key);
			if (property != null && value != null) {
				// Conversion from BigDecimal needed
				if (value instanceof BigDecimal) {
					long lval = ((BigDecimal) value).longValue();
					double dval = ((BigDecimal) value).doubleValue();
					if (lval == dval) {
						value = lval;
					} else {
						value = dval;
					}
					if (TRACE)
						log.debug("bigdecimal: " + lval + " / " + dval + " / "
								+ (lval == dval) + " => " + value);
				}

				PropertyUtils.setProperty(bean, property, value);
			}
		}
		return hm;
	}

	/**
	 * Call a stored procedure that inserts bean data into database
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It <strong>can't<strong> be <code>null</code>.
	 * @return HashMap<String, Object> with the primary keys used in the
	 *         inserted data
	 * @throws Exception
	 */
	public static HashMap<String, Object> callInsertBeanProcedure(
			Connection con, String schema, String pkg, String procName,
			Object bean, HashMap<String, Object> params) throws Exception {
		HashMap<String, Object> HashMap = callProcedure(con, schema, pkg,
				procName, bean, params, false, false);
		for (String key : HashMap.keySet()) {
			String property = Utils.nameDbToBean(key);
			PropertyUtils.setProperty(bean, property, HashMap.get(key));
		}
		return HashMap;
	}

	/**
	 * Call a stored procedure that inserts or updates bean data
	 * 
	 * @param con
	 *            Connection
	 * @param pkg
	 *            Procedure's package or <code>null</code> if it doesn't have
	 *            package.
	 * @param procName
	 *            Procedure's name
	 * @param bean
	 *            Bean object with the data. Properties are auto HashMapped to
	 *            param names. It can be <code>null</code>.
	 * @throws Exception
	 */
	public static void callDeleteBeanProcedure(Connection con, String schema,
			String pkg, String procName, Object bean) throws Exception {
		callProcedure(con, schema, pkg, procName, bean, null, false, false);
	}
}
