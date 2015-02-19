package trame_common;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * 
 * @author KOUNTA
 *
 */

public class SessionBean implements ISession , Serializable{

	private int id;
	
	public SessionBean(int id) throws RemoteException {
		super();
		this.id = id;
	}
	private static final long serialVersionUID = 1L;

	public int getID() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Session [id=" + id + "]";
	}
}
