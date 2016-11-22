package mps.core.fertigung;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mps.core.fertigung.dao.ArbeitsplanManager;
import mps.core.fertigung.dao.BauteilManager;
import mps.core.fertigung.dao.HibernateUtil;
import mps.core.fertigung.dao.StuecklisteManager;
import mps.core.fertigung.dao.StuecklistenPositionManager;
import mps.core.fertigung.dao.VorgangManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JUnitFertigung {

	Vorgang v1 = new Vorgang();
	Vorgang v2 = new Vorgang();
	Vorgang v3 = new Vorgang();
	Vorgang v4 = new Vorgang();
	Vorgang v5 = new Vorgang();

	Arbeitsplan a1 = new Arbeitsplan();
	Arbeitsplan a2 = new Arbeitsplan();

	Bauteil maehdrescher = new Bauteil();
	Bauteil motor = new Bauteil();
	Bauteil reifen = new Bauteil();
	Bauteil schrauben = new Bauteil();

	StuecklistenPosition sp1 = new StuecklistenPosition();
	StuecklistenPosition sp2 = new StuecklistenPosition();
	StuecklistenPosition sp3 = new StuecklistenPosition();

	Stueckliste s1 = new Stueckliste();
	Stueckliste s2 = new Stueckliste();

	@Before
	public void setup() {
		// Vorgaenge
		v1 = Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 10, 15, 20);
		v2 = Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 30, 30, 30);
		v3 = Vorgang.erstelleVorgang(VorgangArtTyp.MONTAGE, 35, 40, 50);
		v4 = Vorgang.erstelleVorgang(VorgangArtTyp.BEREITSTELLUNG, 1, 2, 3);
		v5 = Vorgang.erstelleVorgang(VorgangArtTyp.BEREITSTELLUNG, 4, 5, 6);

		List<Vorgang> vlist1 = new ArrayList<Vorgang>();
		vlist1.add(v1);
		vlist1.add(v2);
		vlist1.add(v3);
		List<Vorgang> vlist2 = new ArrayList<Vorgang>();
		vlist2.add(v4);
		vlist2.add(v5);

		// Arbeitsplaene
		a1 = Arbeitsplan.erstelleArbeitsplan(vlist1);
		a2 = Arbeitsplan.erstelleArbeitsplan(vlist2);

		// Bauteile
		maehdrescher = Bauteil.erstelleBauteil("Maehdrescher", null, null);
		motor = Bauteil.erstelleBauteil("Motor", null, null);
		reifen = Bauteil.erstelleBauteil("Reifen", null, null);
		schrauben = Bauteil.erstelleBauteil("Schrauben", null, null);

		// Stuecklistenpositionen
		sp1 = StuecklistenPosition.erstelleStuecklistenPosition(1, motor);
		sp2 = StuecklistenPosition.erstelleStuecklistenPosition(4, reifen);
		sp3 = StuecklistenPosition.erstelleStuecklistenPosition(20, schrauben);

		Set<StuecklistenPosition> splist1 = new HashSet<StuecklistenPosition>();
		splist1.add(sp1);
		splist1.add(sp2);

		Set<StuecklistenPosition> splist2 = new HashSet<StuecklistenPosition>();
		splist2.add(sp3);

		// Stuecklisten
		s1 = Stueckliste.erstelleStueckliste("heute", "morgen", splist1);
		s2 = Stueckliste.erstelleStueckliste("heute", "morgen", splist2);

		VorgangManager.saveVorgang(v1);
		VorgangManager.saveVorgang(v2);
		VorgangManager.saveVorgang(v3);
		VorgangManager.saveVorgang(v4);
		VorgangManager.saveVorgang(v5);

		ArbeitsplanManager.saveArbeitsplan(a1);
		ArbeitsplanManager.saveArbeitsplan(a2);

		BauteilManager.saveBauteil(maehdrescher);
		BauteilManager.saveBauteil(motor);
		BauteilManager.saveBauteil(reifen);
		BauteilManager.saveBauteil(schrauben);

		StuecklistenPositionManager.saveStuecklistenPosition(sp1);
		StuecklistenPositionManager.saveStuecklistenPosition(sp2);
		StuecklistenPositionManager.saveStuecklistenPosition(sp3);

		StuecklisteManager.saveStueckliste(s1);
		StuecklisteManager.saveStueckliste(s2);

		// Arbeitsplaene und Stuecklisten fuer Bauteile setzen
		maehdrescher.setStueckliste(s1);
		motor.setStueckliste(s2);
		maehdrescher.setArbeitsplan(a1);
		motor.setArbeitsplan(a2);

		BauteilManager.updateBauteil(maehdrescher);
		BauteilManager.updateBauteil(motor);

	}

	@Test
	public void testEquals() {

		assertEquals(VorgangManager.loadVorgang(new Long(1)), v1);
		assertEquals(VorgangManager.loadVorgang(new Long(2)), v2);
		assertEquals(VorgangManager.loadVorgang(new Long(3)), v3);
		assertEquals(VorgangManager.loadVorgang(new Long(4)), v4);
		assertEquals(VorgangManager.loadVorgang(new Long(5)), v5);

		assertEquals(ArbeitsplanManager.loadArbeitsplan(new Long(1)), a1);
		assertEquals(ArbeitsplanManager.loadArbeitsplan(new Long(2)), a2);

		assertEquals(BauteilManager.loadBauteil(new Long(1)), maehdrescher);
		assertEquals(BauteilManager.loadBauteil(new Long(2)), motor);
		assertEquals(BauteilManager.loadBauteil(new Long(3)), reifen);
		assertEquals(BauteilManager.loadBauteil(new Long(4)), schrauben);

		assertEquals(
				StuecklistenPositionManager
						.loadStuecklistenPosition(new Long(1)),
				sp1);
		assertEquals(
				StuecklistenPositionManager
						.loadStuecklistenPosition(new Long(2)),
				sp2);
		assertEquals(
				StuecklistenPositionManager
						.loadStuecklistenPosition(new Long(3)),
				sp3);

		assertEquals(StuecklisteManager.loadStueckliste(new Long(1)), s1);
		assertEquals(StuecklisteManager.loadStueckliste(new Long(2)), s2);
	}

	 @After
	 public void setdown(){
	HibernateUtil.beginTransaction();
	HibernateUtil.getSession().createSQLQuery("drop database mps").executeUpdate();
	HibernateUtil.getSession().createSQLQuery("create database mps").executeUpdate();
	HibernateUtil.commitTransaction();
	 }
}