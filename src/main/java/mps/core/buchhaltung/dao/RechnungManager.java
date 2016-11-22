package mps.core.buchhaltung.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import mps.core.fertigung.dao.HibernateUtil;
import mps.core.buchhaltung.Rechnung;

public class RechnungManager {

    private static RechnungDAO rechnungDAO = new RechnungDAO();
	
	public static Long saveRechnung(int betrag, Long auftragNr) {
		
		try{
		//Create Transient object
		Rechnung r = Rechnung.create(betrag, auftragNr);
		
		//Begin Persistence Context
		//HibernateUtil.beginTransaction();
		
		
		//Persist the object
		Long rechnungNr = rechnungDAO.save(r);
		
		//Commit the changes and end the persistence context (implicitly)
		//HibernateUtil.commitTransaction();
		
		return rechnungNr;
    } catch (HibernateException ex) {
        System.out.println("Rechnung konnte nicht gespeichert werden");
        HibernateUtil.rollbackTransaction();
    }
		return null;
	}
	
	public static Rechnung loadRechnung(Long id){
        try {
            HibernateUtil.beginTransaction();
            Rechnung res = rechnungDAO.findByID(Rechnung.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Rechnung konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	public static void zahlungsEingang(int zahlung, Long rechnungsnummer) {
		Session session = HibernateUtil.beginTransaction();
		Rechnung result = (Rechnung) session.get(Rechnung.class, rechnungsnummer);
		result.zahlungsEingang(zahlung);
		session.merge(result); //TODO: bitte zeile inspizieren
		HibernateUtil.commitTransaction();
		
	}
	
}
