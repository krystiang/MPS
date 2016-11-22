package mps.core.fertigung;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Arbeitsplan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr;
	@OneToMany(fetch=FetchType.EAGER)
	private List<Vorgang> vorgangListe = new ArrayList<Vorgang>();

	public static Arbeitsplan erstelleArbeitsplan(List<Vorgang> vorgaenge){
		Arbeitsplan a = new Arbeitsplan();
		a.setVorgangListe(vorgaenge);
		return a;
	}
	
	public List<Vorgang> getVorgangListe() {
		return vorgangListe;
	}

	public void setVorgangListe(List<Vorgang> vorgangListe) {
		this.vorgangListe = vorgangListe;
	}

	public Long getNr() {
		return nr;
	}
	
	public String toString(){
		StringBuffer vl = new StringBuffer();
		for(Vorgang v:vorgangListe) vl.append(v.toString());
		return "Arbeitsplan Nr: "+nr+"\n"+ vl;
	}
	
	public boolean equals(Object o){
	    boolean result = false;
	    if (o instanceof Arbeitsplan) {
	        Arbeitsplan that = (Arbeitsplan) o;
	        result = this.getVorgangListe().size() == that.getVorgangListe().size() 
	        		&& this.getVorgangListe().containsAll(that.getVorgangListe());
	    }
	    return result;
	}

}