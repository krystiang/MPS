package mps.redundant.client;

import mps.core.auftragsUndAngebotsVerwaltung.EAngebot;
import mps.core.auftragsUndAngebotsVerwaltung.EKunde;
import mps.redundant.Config;
import mps.redundant.dispatcher.IDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static IDispatcher getDispatcher(String dispatcherHost,
			int dispatcherPort) throws RemoteException, NotBoundException {
		Registry dispatcherRegistry = LocateRegistry.getRegistry(
				dispatcherHost, dispatcherPort);
		IDispatcher remoteDispatcher = (IDispatcher) dispatcherRegistry
				.lookup(Config.DISPATCHER_NAME);
		return remoteDispatcher;
	}

	public static void main(String[] args) throws RemoteException,
			NotBoundException {
		if (args.length == 2) {
			IDispatcher remoteDispatcher = getDispatcher(args[0],
					Integer.parseInt(args[1]));
			
			

		      //  open up standard input
		      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		      try {
		    	  while(true){
		    		  System.out.println("kunde <name> <adresse>");
		    		  System.out.println("angebot <kundenNr> <erstellungsdatum> <ablaufsdatum> <preis> <bauteilNr>");
		    		  System.out.println("auftrag <abgeschlossen?> <erstellungsdatum> <angebotNr>");
		    		  System.out.println("lieferung <auftragNr>");
		    		  System.out.println("Enter your command: ");
		         String input = br.readLine();
		         if(input.trim().matches("kunde#.*#.*")){
		        	String[] inputPieces = input.split("#");
		        	remoteDispatcher.getRemoteServerInstance().createKunde(inputPieces[1],inputPieces[2]);
		         }
		         else if(input.trim().matches("angebot#\\d*#.*#.*#\\d*#\\d*")){
		        	String[] inputPieces = input.split("#");
		        	EKunde kunde = remoteDispatcher.getRemoteServerInstance().getKunde(Long.parseLong(inputPieces[1]));
		        	remoteDispatcher.getRemoteServerInstance().createAngebot(kunde, inputPieces[2], inputPieces[3], Integer.parseInt(inputPieces[4]), Long.parseLong(inputPieces[5]));
		         }
		         else if(input.trim().matches("auftrag#.*#.*#\\d*")){
		        	String[] inputPieces = input.split("#");
		        	EAngebot angebot= remoteDispatcher.getRemoteServerInstance().getAngebot(Long.parseLong(inputPieces[3]));
		        	remoteDispatcher.getRemoteServerInstance().createAuftrag(Boolean.getBoolean(inputPieces[1]), inputPieces[2], angebot);
		         }
		         else if(input.trim().matches("lieferung#\\d*")){
		        	 String[] inputPieces = input.split("#");
		        	 long transportID =  remoteDispatcher.getRemoteServerInstance().starteAuslieferung(Long.parseLong(inputPieces[1]));
		        	 System.out.println("Auftrag " + inputPieces[1] + " mit transportID " + transportID + " versendet.");
		         }
		         else{
		        	 System.out.println("Wrong command syntax!");
		         }
		    	  }
		      } catch (IOException ioe) {
		         System.out.println("IO error trying to read your command!");
		         System.exit(1);
		      }

		} else
			System.err
					.println("please specify the ip adress and port of the server on which the dispatcher is running");

	}
}
