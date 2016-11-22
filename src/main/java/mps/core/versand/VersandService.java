package mps.core.versand;

import transportDienstAdapterREST.RESTConnector;
import mps.core.auftragsUndAngebotsVerwaltung.EAngebot;
import mps.core.auftragsUndAngebotsVerwaltung.EAuftrag;
import mps.core.auftragsUndAngebotsVerwaltung.EKunde;
import mps.core.auftragsUndAngebotsVerwaltung.IAuftraege;
import mps.core.fertigung.EBauteil;
import mps.core.fertigung.IFertigung;
import mps.core.versand.dao.LieferungManager;
import mps.core.versand.dao.TransportauftragManager;


public class VersandService implements ILieferungen {
	
	private static final VersandService INSTANCE = new VersandService(); 
	
	public static VersandService getInstance() {
		return INSTANCE;
	}
	
	//make the constructor explicitly private
	private VersandService() {}

	public long starteAuslieferung(Long auftragNr) {
		//Lieferung objekt erstellen
		Lieferung lieferung = Lieferung.create(auftragNr);
		
		//Lieferung objekt speichern
		LieferungManager.saveLieferung(lieferung);
		
		//Transportauftrag übermitteln 
		//und im system eintragen
		Transportauftrag transportauftrag = this.erstelleTransportAuftrag(lieferung);
		
		TransportauftragManager.saveTransportauftrag(transportauftrag);
		
		
		return transportauftrag.getTrackingCode();
		
	}

	public Transportauftrag erstelleTransportAuftrag(Lieferung lieferung) {
		
		IAuftraege auftragService = IAuftraege.getAuftragService();
		
		//auftrag und dazugehöriges angebot holen
		EAuftrag auftrag = auftragService.getAuftrag(lieferung.getAuftragNr());
		EAngebot angebot = auftrag.getAngebot();
		
		EKunde k = angebot.getKunde();
		
		EBauteil b = IFertigung.getFertigungService().getBauteil(angebot.getBauteilNr());
		
		
		
		RESTConnector rc = new RESTConnector();
		
		long trackingCode = rc.submitTransportRequest(k, b);
		
		Transportauftrag ta = Transportauftrag.create("13.6.2014",false,"UPPS",trackingCode,lieferung);
		
		return ta;
		
	}
	
	

}
