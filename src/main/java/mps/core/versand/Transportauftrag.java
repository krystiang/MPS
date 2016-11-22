package mps.core.versand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Transportauftrag {
	


	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr; 
	
	private String ausgangsdatum;
	
	private boolean lieferungErfolgt;
	
	private String transportdienstleister;
	
	private Long trackingCode;
	
	//referenzen
	//nur intern
	@OneToOne
	private Lieferung lieferung;
	
	private Transportauftrag(String ausgangsdatum, boolean lieferungErfolgt, 
			String transportdienstleister, Long trackingCode, Lieferung lieferung) {
		this.ausgangsdatum = ausgangsdatum;
		this.lieferungErfolgt = lieferungErfolgt;
		this.transportdienstleister = transportdienstleister;
		this.trackingCode = trackingCode;
		this.lieferung = lieferung;
	}
	
	public Long getNr() {
		return nr;
	}

	public void setNr(Long nr) {
		this.nr = nr;
	}

	public String getAusgangsdatum() {
		return ausgangsdatum;
	}

	public void setAusgangsdatum(String ausgangsdatum) {
		this.ausgangsdatum = ausgangsdatum;
	}

	public boolean isLieferungErfolgt() {
		return lieferungErfolgt;
	}

	public void setLieferungErfolgt(boolean lieferungErfolgt) {
		this.lieferungErfolgt = lieferungErfolgt;
	}

	public String getTransportdienstleister() {
		return transportdienstleister;
	}

	public void setTransportdienstleister(String transportdienstleister) {
		this.transportdienstleister = transportdienstleister;
	}

	public Long getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(Long trackingCode) {
		this.trackingCode = trackingCode;
	}
	
	public Lieferung getLieferung() {
		return lieferung;
	}

	public void setLieferung(Lieferung lieferung) {
		this.lieferung = lieferung;
	}

	public static Transportauftrag create(String ausgangsdatum, boolean lieferungErfolgt,
			String transportdienstleister, long trackingCode, Lieferung lieferung) {
		return new Transportauftrag(ausgangsdatum, lieferungErfolgt, transportdienstleister, trackingCode, lieferung);
	}
	

}
