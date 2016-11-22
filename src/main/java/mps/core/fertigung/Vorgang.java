package mps.core.fertigung;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vorgang {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr;
	private Long ruestzeit;
	private Long maschinenzeit;
	private Long personenzeit;
	@Enumerated(EnumType.STRING)
	private VorgangArtTyp vorgangArtTyp;

	public static Vorgang erstelleVorgang(VorgangArtTyp typ, long ruestzeit, long maschinenzeit, long personenzeit){
		Vorgang v = new Vorgang();
		v.setVorgangArtTyp(typ);
		v.setRuestzeit(ruestzeit);
		v.setMaschinenzeit(maschinenzeit);
		v.setPersonenzeit(personenzeit);
		return v;
	}
	
	public Long getRuestzeit() {
		return ruestzeit;
	}

	public void setRuestzeit(Long ruestzeit) {
		this.ruestzeit = ruestzeit;
	}

	public Long getMaschinenzeit() {
		return maschinenzeit;
	}

	public void setMaschinenzeit(Long maschinenzeit) {
		this.maschinenzeit = maschinenzeit;
	}

	public Long getPersonenzeit() {
		return personenzeit;
	}

	public void setPersonenzeit(Long personenzeit) {
		this.personenzeit = personenzeit;
	}

	public VorgangArtTyp getVorgangArtTyp() {
		return vorgangArtTyp;
	}

	public void setVorgangArtTyp(VorgangArtTyp vorgangArtTyp) {
		this.vorgangArtTyp = vorgangArtTyp;
	}
	
	public String toString(){
		return "Vorgang Nr: "+nr+" Typ: "+vorgangArtTyp+" Ruestzeit: "+ruestzeit+" Maschinenzeit: "+maschinenzeit+" Personenzeit: "+personenzeit+"\n";
	}
	
	public boolean equals(Object o){
	    boolean result = false;
	    if (o instanceof Vorgang) {
	        Vorgang that = (Vorgang) o;
	        result = (this.getRuestzeit() == that.getRuestzeit()
	        		&& this.getMaschinenzeit() == that.getMaschinenzeit()
	        		&& this.getPersonenzeit() == that.getPersonenzeit());
	    }
	    return result;
	}

}