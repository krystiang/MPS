package mps.redundant.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import mps.core.auftragsUndAngebotsVerwaltung.EAngebot;
import mps.core.auftragsUndAngebotsVerwaltung.EKunde;

public interface IMpsServer extends Remote {
	boolean isDeaktiviert() throws RemoteException;

	void setisDeaktiviert(boolean b) throws RemoteException;

	String getName() throws RemoteException;

	void createAuftrag(boolean b, String string, EAngebot a)
			throws RemoteException;

	EAngebot createAngebot(EKunde kunde, String string, String string2, int i, Long long1)
			throws RemoteException;
	EAngebot getAngebot(long nr)throws RemoteException;
	
	EKunde createKunde(String name,String adresse)
			throws RemoteException;
	EKunde getKunde(Long nr)
			throws RemoteException;
	void zahlungsEingang(int zahlung, Long rechnungsnummer)throws RemoteException;
	
	long starteAuslieferung(Long auftragNr) throws RemoteException;
}

