package es.aragon.midas.ws.guia;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import es.aragon.midas.config.MidGuia;
import es.aragon.midas.logging.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Encapsula una conexion a GUIA
 * 
 * @author Carlos
 */
public class GuiaConnection {

	private Logger log = new Logger();

	/**
	 * Envía una petición a GUIA, y devuelve la respuesta
	 * 
	 * @param query
	 * @return
	 */
	public String auth(String login, String pass) {
		PostMethod method;
		String url = AppProperties.getParameter(Constants.URL_GUIA_AUTH);
		method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] data = { new NameValuePair(null, "xml"),
				new NameValuePair("s", "authGUIA"),
				new NameValuePair("login", login),
				new NameValuePair("pass", pass) };
		method.setRequestBody(data);

		return evaluateResponse(method, url);
	}

	/**
	 * Valida un token contra GUIA.
	 * 
	 * @param token
	 *            Token generado por guia desde la aplicacion de origen.
	 * @param appSrc
	 *            Nombre de la aplicacion de origen.
	 * @return Obtiene un XML con el resultado de la validacion de GUIA. Si es
	 *         correcta tendra los datos del usuario.
	 */
	public String validateToken(String token, String appSrc) {
		// Consulta los parametros para hacer la consulta a GUIA
		String appDst = AppProperties.getParameter("midas.guia.appName");
		String url = AppProperties.getParameter(Constants.URL_GUIA_AUTH);

		Protocol easyhttps = new Protocol("https",
				new HttpsSSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", easyhttps);
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] data = { new NameValuePair("s", "validateToken"),
				new NameValuePair("appSrc", appSrc),
				new NameValuePair("appDst", appDst),
				new NameValuePair("token", token) };
		method.setRequestBody(data);

		return evaluateResponse(method, url);

	}

	/**
	 * Genera una URL construida para acceder a una aplicacion destino con token de GUIA
	 * 
	 * @param midGuia
	 *            Objeto con los datos de la tabla MID_GUIA para generar el
	 *            token de GUIA
	 * @param nif
	 *            NIF del usuario para generar el token GUIA. Sera el usuario
	 *            con el que se accedera a la aplicacion destino.
	 * @return URL con la que se puede acceder a la aplicacion destino.
	 *         Contendra el token generado por GUIA.
	 */
	public String generateTokenUrl(MidGuia midGuia, String nif) {
		return generateTokenUrl(midGuia, nif, "");
	}

	/**
	 * Genera una URL construida para acceder a una aplicacion destino con token de GUIA
	 * 
	 * @param midGuia
	 *            Objeto con los datos de la tabla MID_GUIA para generar el
	 *            token de GUIA
	 * @param nif
	 *            NIF del usuario para generar el token GUIA. Sera el usuario
	 *            con el que se accedera a la aplicacion destino.
	 * @param customParameters
	 *            parametros variables para añadir a la URL generada
	 * @return URL con la que se puede acceder a la aplicacion destino.
	 *         Contendra el token generado por GUIA.
	 */
	public String generateTokenUrl(MidGuia midGuia, String nif,
			String customParameters) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

		// Obtiene parametros para generar el token de GUIA
		String appSrc = AppProperties.getParameter("midas.guia.appName");
		String url = AppProperties.getParameter(Constants.URL_GUIA_AUTH);
		String secret = midGuia.getSecret();
		String appDst = midGuia.getAppDst();
		String sec = sf.format(new Date());
		String trustString = "";
		try {
			trustString = getMD5Hash(appSrc + " " + appDst + " " + nif + " "
					+ secret + " " + sec);
		} catch (NoSuchAlgorithmException e) {
			log.error("Error al construir el Hash MD5 para GUIA", e);
		}

		// Genera el token en GUIA
		Protocol easyhttps = new Protocol("https",
				new HttpsSSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", easyhttps);
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] data = { new NameValuePair("s", "getToken"),
				new NameValuePair("appSrc", appSrc),
				new NameValuePair("appDst", appDst),
				new NameValuePair("nif", nif), new NameValuePair("sec", sec),
				new NameValuePair("trustString", trustString) };
		method.setRequestBody(data);

		String guiaResponse = evaluateResponse(method, url);

		// Extrae el token de la respuesta de GUIA
		String token = "";
		Pattern pattern = Pattern.compile("<token>(.*?)</token>");
		Matcher matcher = pattern.matcher(guiaResponse);
		while (matcher.find()) {
			token = matcher.group(1);
		}

		// Genera la url de acceso a la aplicacion destino
		String tokenUrl = midGuia.getUrlDst();

		tokenUrl = tokenUrl.replace("${TOKEN}", token);
		tokenUrl = tokenUrl.replace("${APPSRC}", appSrc);
		tokenUrl = tokenUrl.replace("${APPDST}", appDst);
		tokenUrl = tokenUrl.replace("${NIF}", nif);
		tokenUrl = tokenUrl.replace("${CUSTOM}", customParameters);

		return tokenUrl;
	}

	/**
	 * 
	 * @param ticket
	 * @return
	 */
	public String verifyTicket(String ticket) {
		GetMethod method;
		String url = EnvProperties.getProperty(Constants.URL_GUIA_VALIDATE);
		url = url + "?ticket=" + ticket;
		method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		return evaluateResponse(method, url);

	}


	/**
	 * Devuelve información del usuario desde GUIA
	 * @param login
	 * @return objeto InfoUserResponse con la información del usuario
	 */
	public InfoUserResponse infoUser(String login) {
		PostMethod method;
		InfoUserResponse resp;
		String url = AppProperties.getParameter(Constants.URL_GUIA_AUTH);
		method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] data = { new NameValuePair("out", "xml"),
				new NameValuePair("s", "infoUser"),
				new NameValuePair("login", login)
				};
		method.setRequestBody(data);
		String response = evaluateResponse(method, url);
		log.debug("Guia response: " + response);
        if (response != null && !response.startsWith("KO")) {
    		try {
    			JAXBContext jc = JAXBContext.newInstance(InfoUserResponse.class);
    			Unmarshaller unmarshaller = jc.createUnmarshaller();
    			StringReader reader = new StringReader(
    					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
    							+ response);
    			resp = (InfoUserResponse) unmarshaller.unmarshal(reader);
    		} catch (Exception e) {
    			resp = new InfoUserResponse("K11", "Error parseando respuesta de Guia / InfoUser");
    			log.error("Error parseando respuesta de Guia / infoUser", e);
    			log.error("Respuesta de GUIA: " + response);
    		}
        } else {
			resp = new InfoUserResponse("K12", "Error Conectando a GUIA. Respuesta nula");
        }

        return resp;
	}	
	
	
	
	
	/**
	 * Executes call to HTTP method and evaluates result. Is call from auth and
	 * verifyTicket
	 * 
	 * @param method
	 * @param url
	 * @return
	 */
	private String evaluateResponse(HttpMethodBase method, String url) {
		HttpClient client = new HttpClient();
		String returnValue = null;
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);
			returnValue = method.getResponseBodyAsString();

			if (statusCode != HttpStatus.SC_OK) {
				returnValue = "KO2";
				log.warn("Error Status " + statusCode + " conectando a " + url);
			} else {
				log.debug("Envio a GUIA correcto");
			}

		} catch (HttpException e) {
			returnValue = "KO3";
			log.error("Fatal protocol violation conectando a " + url, e);
		} catch (IOException e) {
			returnValue = "KO1";
			log.error("Fatal transport error conectando a " + url, e);
		} catch (IllegalArgumentException e) {
			returnValue = "KO3";
			log.error("URL mal construida " + url, e);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return returnValue;
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public AuthGuiaResponse mapping(String src) {
		ObjectMapper mapper = new ObjectMapper();
		AuthGuiaResponse value = null;
		try {
			value = mapper.readValue(src, AuthGuiaResponse.class);
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(GuiaConnection.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		return value;
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public AuthGuiaResponse xmlMapping(String src) {
		AuthGuiaResponse resp = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(AuthGuiaResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
							+ src);
			resp = (AuthGuiaResponse) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			log.error("Error parseando respuesta de Guia / Auth", e);
			log.error("Respuesta de GUIA: " + src);
		}
		return resp;
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public AuthGuiaTicketResponse xmlMappingTicketAnswer(String src) {
		AuthGuiaTicketResponse resp = null;
		try {
			JAXBContext jc = JAXBContext
					.newInstance(AuthGuiaTicketResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StringReader reader = new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
							+ src);
			resp = (AuthGuiaTicketResponse) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			log.error("Error parseando respuesta de Guia / Ticket", e);
			log.error("Respuesta de GUIA: " + src);
		}
		return resp;
	}

	private String getMD5Hash(String str) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(str.getBytes(), 0, str.length());
		return new BigInteger(1, m.digest()).toString(16);
	}

}
