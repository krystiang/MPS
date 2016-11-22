package mps.core.auftragsUndAngebotsVerwaltung;

public interface EAngebot {
	Long getNr(); 
	
	String getGueltigAb();
	
	String getGueltigBis();
	
	Integer getPreis();
	
	Long getBauteilNr();
	
	EKunde getKunde();
	
	void setGueltigAb(String ab);
	
	void setGueltigBis(String bis);
	
	void setPreis(Integer preis);
	
	void setBauteilNr(Long nr);
}
