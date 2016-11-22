package mps.core.auftragsUndAngebotsVerwaltung;

import mps.core.auftragsUndAngebotsVerwaltung.dao.KundeManager;

//Enum singleton pattern
public final class KundeService implements IKunden{
	
	private static final KundeService INSTANCE = new KundeService();
	
	private KundeService() {}
	
	static KundeService getInstance() {
		return INSTANCE;
	}
	
	//SystemOperation
	public Kunde kundeErstellen(String name,String adresse) {
		return KundeManager.saveKunde(name, adresse);
	}

	public EKunde getKunde(Long nr) {
		return KundeManager.loadKunde(nr);
	}
	
	

}