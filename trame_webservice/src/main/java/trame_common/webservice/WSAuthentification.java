package trame_common.webservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.jetty.util.security.Credential;

import trame_common.ISession;
import trame_common.SessionBean;

import com.google.gson.Gson;

/**
 * 
 * @author KOUNTA
 *
 */
public class WSAuthentification {

	private static WSAuthentification instance;
	private Gson gson;
	private Map credentials;
	private Map sessions;
	private int currentIdAuth;

	private WSAuthentification() {
		gson = new Gson();
		credentials = new HashMap<String, Credential>();
		sessions = new HashMap<String, ISession>();
		currentIdAuth = 0;
	}

	public static WSAuthentification getInstance() {
		if (instance == null)
			instance = new WSAuthentification();
		return instance;
	}

	@POST
	@Path("/authentification")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Authenticate(String credentialFromClient) throws IOException {

		trame_common.Credential credential = gson.fromJson(credentialFromClient,trame_common.Credential.class);
		
		String login = credential.getLogin();
		System.out.println("Login client : " + login);
		

		if (((trame_common.Credential) credentials.get(login)).getLogin().equals(login)
				&& ((trame_common.Credential) credentials.get(login)).getPassword().equals(credential.getPassword())) {
			
			ResponseBuilder rb = Response.status(200);Response.ok();
			
			if	(sessions.containsKey(credential.getLogin()))	{
				
				System.out.println("The client "+credential.getLogin()+" retry to connect but he still online");
				System.out.println("Session n°:" + ((ISession)sessions.get(credential.getLogin())).getID());
				System.out.println("Authentification is success !");

				String encode = new String(Base64.encodeBase64(serializeFrom(sessions.get(credential.getLogin()))));
				Gson g = new Gson();
				String toSend = g.toJson(encode);
				System.out.println("Chain return to client with login "+""+credential.getLogin()+" : "+toSend);
				
				System.out.println();
				System.out.println();
				rb.entity(toSend);
				return rb.build();
			}
			else	{
				
					currentIdAuth++;
					try {
						ISession session = new SessionBean(currentIdAuth);
						Remote session_stub = UnicastRemoteObject.exportObject(session, (47000+currentIdAuth));
						
						sessions.put(credential.getLogin(), session_stub);
						
						System.out.println("Session n°:" + session.getID());
						System.out.println("Authentification is success !");
		
						String encode = new String(Base64.encodeBase64(serializeFrom(session_stub)));
						
						Gson g = new Gson();
					
						
						String toSend = g.toJson(encode);
						System.out.println("Chain return to client with login "+""+credential.getLogin()+" : "+toSend);
						
						System.out.println();
						System.out.println();
						
						rb.entity(toSend);
						return rb.build();
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
					
					}

			}

			
		}
		return null;
	}

	public void putCredential(trame_common.Credential cred) {

		credentials.put(cred.getLogin(), cred);
	}

	public static byte[] serializeFrom(Object bjct) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			if (bjct instanceof Serializable) {
				oos.writeObject(bjct);
			} else {
				throw new NotSerializableException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bos.toByteArray();
	}

}
