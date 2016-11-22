package mps.core.auftragsUndAngebotsVerwaltung;

/**EAuftrag - Externer Auftrag
 * Verkapselung von Auftrag-objekten*/
public interface EAuftrag {
	Long getNr(); 
	
	boolean getIstAbgeschlossen();
	
	String getBeauftragtAm();
	
	void setIstAbgeschlossen(boolean istAbgeschlossen);
	
	void setBeauftragtAm(String beauftragtAm);
	
	EAngebot getAngebot();
	
}
