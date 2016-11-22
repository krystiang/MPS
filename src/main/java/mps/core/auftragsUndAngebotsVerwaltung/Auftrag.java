package mps.core.auftragsUndAngebotsVerwaltung;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Auftrag implements Serializable, EAuftrag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7479698477845443890L;
	/** Attribute */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr; 
	private Boolean istAbgeschlossen;
	private String beauftragtAm;
	
	/** Referenzen */
	@OneToOne(fetch=FetchType.EAGER)
	private Angebot angebot;
	private Long fertigungsauftragNr;
	private Long rechnungNr; 

	@Override
	public Long getNr() {
		// TODO Auto-generated method stub
		return nr;
	}
	
	@SuppressWarnings("unused")
	private void setNr(Long nr) {
		this.nr = nr; 
	}

	@Override
	public boolean getIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	@Override
	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	@Override
	public String getBeauftragtAm() {
		return beauftragtAm;
	}

	@Override
	public void setBeauftragtAm(String beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

	/**
	 * @return the ID of the Fertigungsauftrag
	 */
	public Long getFertigungsauftragNr() {
		return fertigungsauftragNr;
	}

	/**
	 * @param fertigungsAuftrag the ID of the Fertigungsauftrag to set
	 */
	public void setFertigungsauftragNr(Long fertigungsauftragNr) {
		this.fertigungsauftragNr = fertigungsauftragNr;
	}

	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}
	
	public Long getRechnungNr(){
		return rechnungNr;
	}
	
	public void setRechnungNr(Long rechnungNr) {
		this.rechnungNr = rechnungNr;
		
	}
	
	@Override 
	/** use business-key equality for implementing equals */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Auftrag)) return false;
		Auftrag that = (Auftrag)o;
		return this.getIstAbgeschlossen() == that.getIstAbgeschlossen() && 
			   this.getBeauftragtAm().equals(that.getBeauftragtAm());
	}

	public static Auftrag create(boolean istAbgeschlossen, String beauftragtAm) {
		Auftrag auftrag = new Auftrag();
		auftrag.setIstAbgeschlossen(istAbgeschlossen);
		auftrag.setBeauftragtAm(beauftragtAm);
		
		return auftrag;
	}



}
