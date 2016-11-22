package mps.core.auftragsUndAngebotsVerwaltung;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Kunde implements EKunde,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Attribute */
	@XmlTransient
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr; 
	
	private String name;
	private String adresse;
	

	public Long getNr() {
		// TODO Auto-generated method stub
		return nr;
	}
	
	@SuppressWarnings("unused")
	private void setNr(Long nr) {
		this.nr = nr; 
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name of Client
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param address of client
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	
	
	@Override 
	/** use business-key equality for implementing equals */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Kunde)) return false;
		Kunde that = (Kunde)o;
		return this.getName().equals(that.getName()) && 
			   this.getAdresse().equals(that.getAdresse());
	}

	public static Kunde create(String name, String adresse) {
		Kunde kunde = new Kunde();
		kunde.setName(name);
		kunde.setAdresse(adresse);
		
		return kunde;
	}

}
