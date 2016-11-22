package mps.core.auftragsUndAngebotsVerwaltung.dao;

import org.hibernate.HibernateException;

import mps.core.auftragsUndAngebotsVerwaltung.Angebot;
import mps.core.auftragsUndAngebotsVerwaltung.Auftrag;
import mps.core.buchhaltung.IRechnungen;
import mps.core.fertigung.IFertigung;
import mps.core.fertigung.dao.HibernateUtil;

public class AuftragManager {

    private static AuftragDAO auftragDAO = new AuftragDAO();
    private static AngebotDAO angebotDAO = new AngebotDAO();
	
	public static Auftrag saveAuftrag(boolean istAbgeschlossen, String beauftragtAm, Angebot angebot){
        try {
        	// Create transient object
    		Auftrag auftrag = Auftrag.create(istAbgeschlossen, beauftragtAm);

    		HibernateUtil.beginTransaction();

    		// Set references
    		auftrag.setAngebot(angebot);
    		auftrag.setFertigungsauftragNr(null); // currently unknown
    		// Set reference back
    		angebot.setAuftrag(auftrag);

    		// Update the newly created object
    		long aid = auftragDAO.save(auftrag);
    		// Update the already existing object
    		angebotDAO.merge(angebot);

    		auftrag = auftragDAO.findByID(Auftrag.class, aid);

    		Long fertigungsPlanNr = IFertigung.getFertigungService()
    				.fertigungsPlanErstellen(auftrag.getNr(),
    						auftrag.getAngebot().getBauteilNr());

    		Long rechnungNr = IRechnungen.getRechnungService().rechnungErstellen(
    				angebot.getPreis(), auftrag.getNr());

    		auftrag.setFertigungsauftragNr(fertigungsPlanNr);
    		auftrag.setRechnungNr(rechnungNr);

    		auftragDAO.merge(auftrag);

    		HibernateUtil.commitTransaction();

    		return auftrag;
        } catch (HibernateException ex) {
            System.out.println("Auftrag konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	public static Auftrag loadAuftrag(Long id){
        try {
            HibernateUtil.beginTransaction();
            Auftrag res = auftragDAO.findByID(Auftrag.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Auftrag konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
