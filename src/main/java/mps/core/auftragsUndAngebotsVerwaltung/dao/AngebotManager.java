package mps.core.auftragsUndAngebotsVerwaltung.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import mps.core.auftragsUndAngebotsVerwaltung.Angebot;
import mps.core.auftragsUndAngebotsVerwaltung.Kunde;
import mps.core.fertigung.dao.HibernateUtil;

public class AngebotManager {

    private static AngebotDAO angebotDAO = new AngebotDAO();
	
	public static Angebot saveAngebot(Kunde kunde, String gueltigAb,String gueltigBis, Integer preis , Long bauteilId){
        try {
    		//Create Transient object
    		Angebot a = Angebot.create(kunde, gueltigAb,gueltigBis,preis);
    		
    		//Begin Persistence Context
    		Session session = HibernateUtil.beginTransaction();
    		
    		//set references
    		a.setBauteilNr(bauteilId);
    		
    		//Persist the object
    		session.save(a);
    		
    		//Commit the changes and end the persistence context (implicitly)
    		HibernateUtil.commitTransaction();
    		
    		return a;
        } catch (HibernateException ex) {
            System.out.println("Angebot konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	
	public static Angebot loadAngebot(Long id){
        try {
            HibernateUtil.beginTransaction();
            Angebot res = angebotDAO.findByID(Angebot.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Angebot konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
