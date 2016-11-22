package mps.szenario;

import mps.core.auftragsUndAngebotsVerwaltung.EAngebot;
import mps.core.auftragsUndAngebotsVerwaltung.EKunde;
import mps.redundant.Config;
import mps.redundant.dispatcher.IDispatcher;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
	public static IDispatcher getDispatcher(String dispatcherHost,
			int dispatcherPort) throws RemoteException, NotBoundException {
		Registry dispatcherRegistry = LocateRegistry.getRegistry(
				dispatcherHost, dispatcherPort);
		IDispatcher remoteDispatcher = (IDispatcher) dispatcherRegistry
				.lookup(Config.DISPATCHER_NAME);
		return remoteDispatcher;
	}

	public static void main(String[] args) throws NotBoundException, IOException {
		if (args.length == 2) {
			IDispatcher remoteDispatcher = getDispatcher(args[0],
					Integer.parseInt(args[1]));

			EKunde k = remoteDispatcher.getRemoteServerInstance().createKunde("SAP", "Holunderstrasse 25");
			EKunde k2 = remoteDispatcher.getRemoteServerInstance().createKunde("Siemens", "Petersallee 10");
			
			EAngebot a = remoteDispatcher.getRemoteServerInstance()
					.createAngebot(k,"10.05.2014", "17.05.2014", 50, new Long(1));
			EAngebot a2 = remoteDispatcher.getRemoteServerInstance()
					.createAngebot(k2,"10.05.2014", "17.05.2014", 50, new Long(1));
			EAngebot a3 = remoteDispatcher.getRemoteServerInstance()
					.createAngebot(k2,"10.05.2014", "17.05.2014", 50, new Long(2));

			remoteDispatcher.getRemoteServerInstance().createAngebot(k2,
					"10.05.2014", "17.05.2014", 50, new Long(3));
			remoteDispatcher.getRemoteServerInstance().createAuftrag(false,
					"17.05.2014", a);
			remoteDispatcher.getRemoteServerInstance().createAuftrag(false,
					"17.05.2014", a2);
			remoteDispatcher.getRemoteServerInstance().createAuftrag(false,
					"17.05.2014", a3);
			
			System.out.println("Press any key to send a transport request to UPPS...");
			System.in.read();
			
			long auftragNr = 3;
			
			long transportID = remoteDispatcher.getRemoteServerInstance().starteAuslieferung(auftragNr);
			
			System.out.println("Auftrag " +auftragNr + " mit transportID " + transportID + " versendet.");
		
		} else
			System.err
					.println("please specify the ip adress and port of the server on which the dispatcher is running");

	}
}