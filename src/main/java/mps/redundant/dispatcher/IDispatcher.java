package mps.redundant.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import mps.redundant.server.IMpsServer;

public interface IDispatcher extends Remote {
	public IMpsServer getRemoteServerInstance() throws RemoteException;

}