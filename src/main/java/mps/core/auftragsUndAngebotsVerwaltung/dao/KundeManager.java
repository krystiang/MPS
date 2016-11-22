package mps.core.auftragsUndAngebotsVerwaltung.dao;

import org.hibernate.HibernateException;

import mps.core.auftragsUndAngebotsVerwaltung.Kunde;
import mps.core.fertigung.dao.HibernateUtil;

public class KundeManager {

    private static KundeDAO kundeDAO = new KundeDAO();
	
	public static Kunde saveKunde(String name, String adresse){
        try {
        	//Create Transient object
    		Kunde a = Kunde.create(name,adresse);
    		
    		//Begin Persistence Context
    		HibernateUtil.beginTransaction();
    		
    		//Persist the object
    		kundeDAO.save(a);
    		
    		//Commit the changes and end the persistence context (implicitly)
    		HibernateUtil.commitTransaction();
    		
    		return a;
    		
        } catch (HibernateException ex) {
            System.out.println("Kunde konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	public static Kunde loadKunde(Long id){
        try {
            HibernateUtil.beginTransaction();
            Kunde res = kundeDAO.findByID(Kunde.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Kunde konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
