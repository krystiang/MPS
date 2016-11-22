package mps.core.auftragsUndAngebotsVerwaltung;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
@Entity
public class Angebot implements EAngebot,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Attribute */ 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr; 
	private String gueltigAb;
	private String gueltigBis;
	private Integer preis;
	
	/** Referenzen */
	//Komponenten-intern
	@OneToOne
	private Auftrag auftrag;
	@OneToOne
	private Kunde kunde;
	//Komponenten-extern
	private Long bauteilNr;
	//referenz zu Kunde fehlt erstmal absichtlich
	
	/** Factory Method */
	public static Angebot create(Kunde kunde, String gueltigAb,String gueltigBis,Integer preis) {
		Angebot instanceToCreate = new Angebot();
		instanceToCreate.setKunde(kunde);
		instanceToCreate.setGueltigAb(gueltigAb);
		instanceToCreate.setGueltigBis(gueltigBis);
		instanceToCreate.setPreis(preis);
		
		return instanceToCreate;
	}
	/**
	 * @return the nr
	 */
	public Long getNr() {
		return nr;
	}
	/**
	 * @param nr the nr to set
	 */
	@SuppressWarnings("unused")
	private void setNr(Long nr) {
		this.nr = nr;
	}
	/**
	 * @return the gueltigAb
	 */
	public String getGueltigAb() {
		return gueltigAb;
	}
	/**
	 * @param gueltigAb the gueltigAb to set
	 */
	public void setGueltigAb(String gueltigAb) {
		this.gueltigAb = gueltigAb;
	}
	/**
	 * @return the gueltigBis
	 */
	public String getGueltigBis() {
		return gueltigBis;
	}
	/**
	 * @param gueltigBis the gueltigBis to set
	 */
	public void setGueltigBis(String gueltigBis) {
		this.gueltigBis = gueltigBis;
	}
	/**
	 * @return the preis
	 */
	public Integer getPreis() {
		return preis;
	}
	/**
	 * @param preis the preis to set
	 */
	public void setPreis(Integer preis) {
		this.preis = preis;
	}
	
	/**
	 * @return the client
	 */
	public Kunde getKunde() {
		return kunde;
	}
	/**
	 * @param the client to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	
	/**
	 * @return the bauteilNr
	 */
	public Long getBauteilNr() {
		return bauteilNr;
	}
	/**
	 * @param bauteilNr the bauteilNr to set
	 */
	public void setBauteilNr(Long bauteilNr) {
		this.bauteilNr = bauteilNr;
	}
	
	/** Implement equals using business key equality, 
	 * see http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch04.html#persistent-classes-equalshashcode
	 * for a discussion of the concept */
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		else if (!(o instanceof Angebot )) return false;
		else { //Safe cast possible
				Angebot that = (Angebot)o;
				return this.getGueltigAb().equals(that.getGueltigAb()) && 
					   this.getGueltigBis().equals(that.getGueltigBis()) && 
					   this.getPreis().equals(that.getPreis());
			}
	}
	
	public Auftrag getAuftrag() {
		return auftrag;
	}
	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}
	
	

}
