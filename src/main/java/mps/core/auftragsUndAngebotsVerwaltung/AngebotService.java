package mps.core.auftragsUndAngebotsVerwaltung;

import mps.core.auftragsUndAngebotsVerwaltung.dao.AngebotManager;
import mps.core.auftragsUndAngebotsVerwaltung.dao.KundeManager;

//Enum singleton pattern
public final class AngebotService implements IAngebote{
	
	private static final AngebotService INSTANCE = new AngebotService();
	
	private AngebotService() {}
	
	static AngebotService getInstance() {
		return INSTANCE;
	}
	
	//SystemOperation
	public Angebot angebotErstellen(Long kundeNr,String gueltigAb, String gueltigBis, Integer preis , Long bauteilNr) {
		Kunde kunde = KundeManager.loadKunde(kundeNr);
		return AngebotManager.saveAngebot(kunde,gueltigAb, gueltigBis, preis, bauteilNr);
	}
	
	public EAngebot getAngebot(Long nr){
		return AngebotManager.loadAngebot(nr);
	}

}
