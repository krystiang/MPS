package mps.core.fertigung.dao;

import org.hibernate.HibernateException;
import mps.core.fertigung.Vorgang;
import mps.core.fertigung.dao.HibernateUtil;

public class VorgangManager {

    private static VorgangDAO vorgangDAO = new VorgangDAO();
	
	public static void saveVorgang(Vorgang v){
        try {
            HibernateUtil.beginTransaction();
            vorgangDAO.save(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Vorgang konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	public static Vorgang loadVorgang(Long id){
        try {
            HibernateUtil.beginTransaction();
            Vorgang res = vorgangDAO.findByID(Vorgang.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Vorgang konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
