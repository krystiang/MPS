package mps.core.auftragsUndAngebotsVerwaltung;

public interface EKunde {
	Long getNr(); 
	
	String getName();
	
	String getAdresse();
	
	void setName(String name);
	
	void setAdresse(String adresse);
	
}
