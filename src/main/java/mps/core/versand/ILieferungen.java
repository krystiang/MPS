package mps.core.versand;


public interface ILieferungen {
	static ILieferungen getVersandService() {
		return VersandService.getInstance();
	}

	long starteAuslieferung(Long auftragNr);
	
	Transportauftrag erstelleTransportAuftrag(Lieferung lieferung);
}
