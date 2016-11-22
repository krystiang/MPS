package mps.core.auftragsUndAngebotsVerwaltung;

import mps.core.auftragsUndAngebotsVerwaltung.dao.AngebotManager;
import mps.core.auftragsUndAngebotsVerwaltung.dao.AuftragManager;


//TODO The whole class
public final class AuftragService implements IAuftraege {
	private static final AuftragService INSTANCE = new AuftragService();
	
	private AuftragService() {}
	
	static AuftragService getInstance() {
		return INSTANCE;
	}

	/** gibt eine referenz auf einen auftrag zurück,
	 * diese referenz hat den typ E(xtern)Auftrag, damit
	 * nicht implementierungsdetails aus der komponente raussickern 
	 */
	public EAuftrag getAuftrag(Long auftragNr) {
		return AuftragManager.loadAuftrag(auftragNr);
	}
	
	@Override
	public Auftrag auftragErstellen(String beauftragtAm,Long angebotNr) {
		Angebot angebot = AngebotManager.loadAngebot(angebotNr);
		Auftrag auftrag = AuftragManager.saveAuftrag(false, beauftragtAm, angebot);
		
		return auftrag;
	}
	 
	 

}
