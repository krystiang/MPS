package mps.szenario;

import mps.core.auftragsUndAngebotsVerwaltung.EAngebot;
import mps.core.auftragsUndAngebotsVerwaltung.EKunde;
import mps.redundant.server.Fassade;

public class TestDatenOfClient {
	
	public static void main(String[] args) {

		Fassade mps = new Fassade();
		EKunde k = mps.createKunde("SAP", "Holunderstrasse 25");
		EKunde k2 = mps.createKunde("Siemens", "Petersallee 10");
		
		
		EAngebot an = mps.createAngebot(k,"10.05.2014", "17.05.2014", 50, new Long(1));
		EAngebot an2 = mps.createAngebot(k2,"10.05.2014", "17.05.2014", 50, new Long(1));
		EAngebot an3 = mps.createAngebot(k2,"10.05.2014", "17.05.2014", 50, new Long(2));

		mps.createAngebot(k2,"10.05.2014", "17.05.2014", 50, new Long(3));
		
		mps.createAuftrag(false,"17.05.2014", an);
		
		mps.createAuftrag(false,"17.05.2014", an2);
		mps.createAuftrag(false,"17.05.2014", an3);
	
	}

}
