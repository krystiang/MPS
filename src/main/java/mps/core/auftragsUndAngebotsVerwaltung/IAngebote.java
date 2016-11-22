package mps.core.auftragsUndAngebotsVerwaltung;

public interface IAngebote {


	EAngebot angebotErstellen(Long kundeNr,String gueltigAb, String gueltigBis, Integer preis , Long bauteilNr);
	
	EAngebot getAngebot(Long nr);
	
	static IAngebote getAngebotService() {
		return AngebotService.getInstance();
	}

}

