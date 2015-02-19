package trame_common.webservice;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import trame_common.Credential;
/**
 * 
 * @author KOUNTA
 *
 */
public class LunchServer {

	public static void main(String[] args) {
		
		String BASE_URI;
		BASE_URI = "http://127.0.0.1:47000";
		
		WSAuthentification server = WSAuthentification.getInstance();
		server.putCredential(new Credential("seybany", "passe"));
		server.putCredential(new Credential("isidis", "isidis"));
		
		JAXRSServerFactoryBean srvFctry = new JAXRSServerFactoryBean();
		srvFctry.setServiceBean(server);
		srvFctry.setAddress(BASE_URI);
		srvFctry.create();
	}
}
