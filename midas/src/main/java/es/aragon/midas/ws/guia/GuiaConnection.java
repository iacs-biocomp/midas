package es.aragon.midas.ws.guia;

import es.aragon.midas.config.AppProperties;
import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import es.aragon.midas.logging.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;

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
 * Encapsula una conexiï¿½n a GUIA
 * 
 * @author Carlos
 */
public class GuiaConnection {

	private Logger log = new Logger();

	/**
	 * EnvÃ­a una peticiÃ³n a GUIA, y devuelve la respuesta
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
	 *            Token generado por guia desde la aplicación de origen.
	 * @param appSrc
	 *            Nombre de la aplicación de origen.
	 * @param appDst
	 *            Nombre de la aplicación destino.
	 * @param url
	 *            URL de GUIA para realizar la validación del token.
	 * @return Obtiene un XML con el resultado de la validación de GUIA. Si es
	 *         correcta tendrá los datos del usuario.
	 */
	public String validateToken(String token, String appSrc, String appDst,
			String url) {
		HttpClient client = new HttpClient();

		String returnValue = null;
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] data = { new NameValuePair("s", "validateToken"),
				new NameValuePair("appSrc", appSrc),
				new NameValuePair("appDst", appDst),
				new NameValuePair("token", token) };
		method.setRequestBody(data);

		try {
			Protocol easyhttps = new Protocol("https",
					new HttpsSSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", easyhttps);
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
			log.error("Fatal protocol violation: " + e.getMessage()
					+ " conectando a " + url);
		} catch (IOException e) {
			returnValue = "KO1";
			log.error("Fatal transport error: " + e.getMessage()
					+ " conectando a " + url);
		} catch (IllegalArgumentException e) {
			returnValue = "KO3";
			log.error("URL mal construida: " + url);
		} catch (Throwable e) {
			returnValue = "KO1";
			log.error("Error: " + e.getMessage() + " conectando a " + url);
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return returnValue;
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
			log.error("Fatal protocol violation: " + e.getMessage()
					+ " conectando a " + url);
		} catch (IOException e) {
			returnValue = "KO1";
			log.error("Fatal transport error: " + e.getMessage()
					+ " conectando a " + url);
		} catch (IllegalArgumentException e) {
			returnValue = "KO3";
			log.error("URL mal construida: " + url);
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

}
