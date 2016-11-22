package mps.core.fertigung;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StuecklistenPosition {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long nr;
	@ManyToOne
	private Bauteil bauteil;
	private Long menge;

	public static StuecklistenPosition erstelleStuecklistenPosition(long menge, Bauteil bauteil){
		StuecklistenPosition sp = new StuecklistenPosition();
		sp.setMenge(menge);
		sp.setBauteil(bauteil);
		return sp;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	public Long getMenge() {
		return menge;
	}

	public void setMenge(Long menge) {
		this.menge = menge;
	}
	
	public String toString(){
		return "Menge: "+menge+" - "+bauteil.toString()+"\n";
	}
	
	@Override
	public boolean equals(Object o){
	    boolean result = false;
	    if (o instanceof StuecklistenPosition) {
	    	StuecklistenPosition that = (StuecklistenPosition) o;
	        result = (this.getMenge() == that.getMenge());
	    }
	    return result;
	}
	
}