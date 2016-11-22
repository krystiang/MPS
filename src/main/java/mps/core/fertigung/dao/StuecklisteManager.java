package mps.core.fertigung.dao;

import org.hibernate.HibernateException;
import mps.core.fertigung.Stueckliste;
import mps.core.fertigung.dao.HibernateUtil;

public class StuecklisteManager {

    private static StuecklisteDAO stuecklisteDAO = new StuecklisteDAO();
	
	public static void saveStueckliste(Stueckliste v){
        try {
            HibernateUtil.beginTransaction();
            stuecklisteDAO.save(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Stueckliste konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	
	public static Stueckliste loadStueckliste(Long id){
        try {
            HibernateUtil.beginTransaction();
            Stueckliste res = stuecklisteDAO.findByID(Stueckliste.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Stueckliste konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
