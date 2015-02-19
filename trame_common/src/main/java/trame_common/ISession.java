package trame_common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISession extends Remote {

	public int getID() throws RemoteException;
}
