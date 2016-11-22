package mps.core.auftragsUndAngebotsVerwaltung;

/** Komponente fuer welche diese Schnittstelle entworfen wurde: Fertigung
 * @author Christian
 *
 */
public interface IAuftraege {
	/** Operationen, welche einem benoetigte daten besorgen */
	
	
	//Neue methoden aus aufgabe 3
	
	
	/** gibt eine referenz auf einen auftrag zurück,
	 * diese referenz hat den typ E(xtern)Auftrag, damit
	 * nicht implementierungsdetails aus der komponente raussickern 
	 */
	EAuftrag getAuftrag(Long auftragNr);
	
	static IAuftraege getAuftragService() {
		return AuftragService.getInstance();
	}

	EAuftrag auftragErstellen(String beauftragtAm, Long nr);
}
