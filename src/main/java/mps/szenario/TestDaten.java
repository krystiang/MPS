package mps.szenario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mps.core.fertigung.Arbeitsplan;
import mps.core.fertigung.Bauteil;
import mps.core.fertigung.Stueckliste;
import mps.core.fertigung.StuecklistenPosition;
import mps.core.fertigung.Vorgang;
import mps.core.fertigung.VorgangArtTyp;
import mps.core.fertigung.dao.ArbeitsplanManager;
import mps.core.fertigung.dao.BauteilManager;
import mps.core.fertigung.dao.StuecklisteManager;
import mps.core.fertigung.dao.StuecklistenPositionManager;
import mps.core.fertigung.dao.VorgangManager;

public class TestDaten {

	
	public static void main(String[] args) {
//----------------------Test Datenbankbefuellung------------------------	
	szenario();	
	}

	


	static void szenario(){
		
		
		//Vorgaenge
		Vorgang v1= Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 10, 15, 20);
		Vorgang v2= Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 30, 30, 30);
		Vorgang v3= Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 35, 40, 50);
		Vorgang v4= Vorgang.erstelleVorgang(VorgangArtTyp.BEREITSTELLUNG, 1, 2, 3);
		Vorgang v5= Vorgang.erstelleVorgang(VorgangArtTyp.BEREITSTELLUNG, 4, 5, 6);
		
		List<Vorgang> vlist1= new ArrayList<Vorgang>();
		vlist1.add(v1);
		vlist1.add(v2);
		vlist1.add(v3);
		List<Vorgang> vlist2= new ArrayList<Vorgang>();
		vlist2.add(v4);
		vlist2.add(v5);
		
		//Arbeitsplaene
		Arbeitsplan a1 = Arbeitsplan.erstelleArbeitsplan(vlist1);
		Arbeitsplan a2 = Arbeitsplan.erstelleArbeitsplan(vlist2);
		
		//Bauteile
		Bauteil b1 = Bauteil.erstelleBauteil("Maehdrescher",null,null);
		Bauteil b2 = Bauteil.erstelleBauteil("Motor",null,null);
		Bauteil b3 = Bauteil.erstelleBauteil("Reifen",null,null);
		Bauteil b4 = Bauteil.erstelleBauteil("Schrauben",null,null);
		
		
		//Stuecklistenpositionen
		StuecklistenPosition sp1 = StuecklistenPosition.erstelleStuecklistenPosition(1,b2);
		StuecklistenPosition sp2 = StuecklistenPosition.erstelleStuecklistenPosition(4,b3);
		StuecklistenPosition sp3 = StuecklistenPosition.erstelleStuecklistenPosition(20,b4);
		
		Set<StuecklistenPosition> splist1= new HashSet<StuecklistenPosition>();
		splist1.add(sp1);
		splist1.add(sp2);

		Set<StuecklistenPosition> splist2= new HashSet<StuecklistenPosition>();
		splist2.add(sp3);
		
		//Stuecklisten
		Stueckliste s1 = Stueckliste.erstelleStueckliste("heute","morgen",splist1);
		Stueckliste s2 = Stueckliste.erstelleStueckliste("heute","morgen",splist2);
		
		
		VorgangManager.saveVorgang(v1);
		VorgangManager.saveVorgang(v2);
		VorgangManager.saveVorgang(v3);
		VorgangManager.saveVorgang(v4);
		VorgangManager.saveVorgang(v5);
		
		ArbeitsplanManager.saveArbeitsplan(a1);
		ArbeitsplanManager.saveArbeitsplan(a2);
		
		BauteilManager.saveBauteil(b1);
		BauteilManager.saveBauteil(b2);
		BauteilManager.saveBauteil(b3);
		BauteilManager.saveBauteil(b4);
		
		StuecklistenPositionManager.saveStuecklistenPosition(sp1);
		StuecklistenPositionManager.saveStuecklistenPosition(sp2);
		StuecklistenPositionManager.saveStuecklistenPosition(sp3);
		
		StuecklisteManager.saveStueckliste(s1);
		StuecklisteManager.saveStueckliste(s2);
		
		//Arbeitsplaene und Stuecklisten fuer Bauteile setzen
		b1.setStueckliste(s1);
		b2.setStueckliste(s2);
		b1.setArbeitsplan(a1);
		b2.setArbeitsplan(a2);
		
		BauteilManager.updateBauteil(b1);
		BauteilManager.updateBauteil(b2);
		
		
	}
}
