package mps.core.fertigung;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;


@Entity
public class Bauteil implements EBauteil {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr;
	private String name;
	@XmlTransient
	@OneToOne
	private Arbeitsplan arbeitsplan;
	@OneToOne
	private Stueckliste stueckliste;
	@OneToMany
	private Set<Fertigungsauftrag> fertigungsauftragListe = new HashSet<Fertigungsauftrag>();
	
	public static Bauteil erstelleBauteil(String name, Stueckliste stueckliste, Arbeitsplan arbeitsplan){
		Bauteil b = new Bauteil();
		b.setName(name);
		b.setStueckliste(stueckliste);
		b.setArbeitsplan(arbeitsplan);
		return b;
		}

	public Long getNr() {
		return nr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Arbeitsplan getArbeitsplan() {
		return arbeitsplan;
	}

	public void setArbeitsplan(Arbeitsplan arbeitsplan) {
		this.arbeitsplan = arbeitsplan;
	}

	public Stueckliste getStueckliste() {
		return stueckliste;
	}

	public void setStueckliste(Stueckliste stueckliste) {
		this.stueckliste = stueckliste;
	}

	public Set<Fertigungsauftrag> getFertigungsauftragListe() {
		return fertigungsauftragListe;
	}

	public void setFertigungsauftragListe(
			Set<Fertigungsauftrag> fertigungsauftragListe) {
		this.fertigungsauftragListe = fertigungsauftragListe;
	}
	
	public String toString(){
		if(stueckliste!=null && arbeitsplan!=null)
		return "Bauteil Nr: "+nr+" Name: "+ name+"\n"+"["+stueckliste.toString()+"\n"+arbeitsplan.toString()+"]";
		return "Bauteil Nr: "+nr+" Name: "+ name;
	}
	

	@Override
	public boolean equals(Object o){
	    boolean result = false;
	    if (o instanceof Bauteil) {
	        Bauteil that = (Bauteil) o;
	        result = (this.getName().equals(that.getName()));
	    }
	    return result;
	}
	
}