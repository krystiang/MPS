package mps.core.versand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lieferung {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr;
	
	//externe referenzen
	private Long auftragNr;
	
	private Lieferung(Long auftragNr) {
		this.setAuftrag(auftragNr);
	}
	
	public static Lieferung create(Long auftragNr) {
		return new Lieferung(auftragNr);
	}
	
	public Long getNr() {
		return nr;
	}

	public void setNr(Long nr) {
		this.nr = nr;
	}

	public Long getAuftragNr() {
		return auftragNr;
	}

	public void setAuftrag(Long auftrag) {
		this.auftragNr = auftrag;
	}
	
	

}
