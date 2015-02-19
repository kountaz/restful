package trame_common.trame_client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.ws.rs.core.MediaType;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.jaxrs.client.WebClient;
import trame_common.Credential;
import trame_common.ISession;
import com.google.gson.Gson;

/**
 * 
 * @author KOUNTA
 *
 */

public class RequestorRest {

	private Gson gson = new Gson();
	private WebClient client;

	public RequestorRest() {
		client = WebClient.create("http://127.0.0.1:47000");
	}

	public void authentificate(Credential cred) {
		
		client.path("/authentification");
		try {
			String response;
			response = client.accept(MediaType.APPLICATION_JSON).post(gson.toJson(cred), String.class);
			
			ISession stub = (ISession) unserializeFrom(Base64.decodeBase64(response));
			
			System.out.println("Session content : "+response);
			System.out.println("Session N° : "+stub.getID());
			
			
		} catch (Exception e) {
			System.out.println("Invalid connection retry...");
		}
	}
	public static Object unserializeFrom (byte [] stream) {
		
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(stream);
		ObjectInputStream objectOuputStream = null;
		try {
			objectOuputStream = new ObjectInputStream(byteInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object obj = null;
		try {
			obj = objectOuputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
}
