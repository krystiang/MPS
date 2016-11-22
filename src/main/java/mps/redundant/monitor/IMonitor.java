package mps.redundant.monitor;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote {
    public void alive(String serverName) throws RemoteException, NotBoundException;

	public void getMpsServerRegistry(String serverName, String host, int port)
			throws RemoteException;
}