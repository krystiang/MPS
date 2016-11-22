package mps.core.fertigung;


/** Komponente fuer welche diese Schnittstelle entworfen wurde: AuftragsUndAngebotsVerwaltung, Fassade
 * Dieses Interface wird von der Klasse FertigungsService implementiert/realisiert.
 * @author Christian, Nick, Krystian
 *
 */
public interface IFertigung {
	/**Systemoperation*/
	
	/** Erstellt fuer eine gegebene auftrags-ID einen Fertigungsplan
	 *  und gibt die ID des entsprechenden Fertigungsauftrags zurueck.
	 *  Der Fertigungsplan ist Textdatei, in der die einzelnen Bauteile
	 *  und dazugehörigen Arbeitsplaene samt Vorgaengen stukturiert 
	 *  dargestellt sind, womit die Fertigung ihre Fertigungsauftraege 
	 *  durchfuehren koennen 
	 * @param auftragNr ID des auftrags 
	 * @return ID des Fertigungsplans/Fertigungsauftrags
	 * Nicht spezifiziert: Format der Textdatei 
	 * Alternativ wird hier nur eine textuelle Form der im Datenmodell 
	 * der Komponente "Fertigung" hinterlegte Daten ausgegeben.  
	 */
	Long fertigungsPlanErstellen(Long auftragNr, Long bauteilNr);
	
	/**
	 * Holt eine read-only sicht auf ein bauteil 
	 * von einer ID ausgehend 
	 * @param bauteilNr
	 * @return das read-only bauteil
	 */
	EBauteil getBauteil(Long bauteilNr);
	
	static IFertigung getFertigungService() {
		return FertigungService.getInstance();
	}
}
