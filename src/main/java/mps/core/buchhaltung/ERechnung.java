package mps.core.buchhaltung;


public interface ERechnung {
	Long getNr(); 
	
	int getBetrag();
	
	int getUebrigerBetrag();
	
	boolean getIstBezahlt();
	
	Long getAuftragNr();
	
	void setBetrag(int betrag);
	
	void setUebrigerBetrag(int uebrigerBetrag);
	
	void setIstBezahlt(boolean bool);
	
	void setAuftragNr(Long auftragNr);
	
}
