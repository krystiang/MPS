package mps.core.buchhaltung;


public interface IRechnungen {
	static IRechnungen getRechnungService() {
		return RechnungService.getInstance();
	}

	Long rechnungErstellen(int betrag, Long auftragNr);
	
	void zahlungsEingang(int zahlung, Long rechnungsnummer);
}
