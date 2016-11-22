package mps.core.buchhaltung;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rechnung implements ERechnung,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Attribute */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr; 
	private int betrag;
	private int uebrigerBetrag;
	boolean istBezahlt = false;
	
	/** Referenzen */
	private Long auftragNr;
	
	
	public void zahlungsEingang(int zahlung){
		uebrigerBetrag-=zahlung;
		if(uebrigerBetrag<=0){
			setIstBezahlt(true);
		}
	}

	public Long getNr() {
		// TODO Auto-generated method stub
		return nr;
	}
	
	@SuppressWarnings("unused")
	private void setNr(Long nr) {
		this.nr = nr; 
	}


	/**
	 * @return the amount to pay
	 */
	public int getBetrag() {
		return betrag;
	}

	/**
	 * @param amount to pay
	 */
	public void setBetrag(int betrag) {
		this.betrag = betrag;
	}

	/**
	 * @return the amount left to pay
	 */
	public int getUebrigerBetrag() {
		return uebrigerBetrag;
	}

	/**
	 * @param amount left to pay
	 */
	public void setUebrigerBetrag(int uebrigerBetrag) {
		this.uebrigerBetrag = uebrigerBetrag;
	}
	
	/**
	 * @return boolean ob bezahlt
	 */
	public boolean getIstBezahlt() {
		return istBezahlt;
	}

	/**
	 * @param amount left to pay
	 */
	public void setIstBezahlt(boolean bool) {
		this.istBezahlt = bool;
	}

	/**
	 * @return the order
	 */
	public Long getAuftragNr() {
		return auftragNr;
	}

	/**
	 * @param order
	 */
	public void setAuftragNr(Long auftragNr) {
		this.auftragNr = auftragNr;
	}
	
	
	@Override 
	/** use business-key equality for implementing equals */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Rechnung)) return false;
		Rechnung that = (Rechnung)o;
		return this.getBetrag()==that.getBetrag() && 
			   this.getAuftragNr()==that.getAuftragNr();
	}

	public static Rechnung create(int betrag, Long auftragNr) {
		Rechnung rechnung = new Rechnung();
		rechnung.setBetrag(betrag);
		rechnung.setUebrigerBetrag(betrag);
		rechnung.setAuftragNr(auftragNr);
		return rechnung;
	}

}
