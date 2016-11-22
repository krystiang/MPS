package mps.core.fertigung.dao;

import org.hibernate.HibernateException;

import mps.core.fertigung.Bauteil;
import mps.core.fertigung.dao.HibernateUtil;

public class BauteilManager {

    private static BauteilDAO bauteilDAO = new BauteilDAO();
	
	public static void saveBauteil(Bauteil v){
        try {
            HibernateUtil.beginTransaction();
            bauteilDAO.save(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Bauteil konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	public static void updateBauteil(Bauteil v){
        try {
        	HibernateUtil.beginTransaction();
            bauteilDAO.merge(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Bauteil konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	public static Bauteil loadBauteil(Long id){
        try {
        	HibernateUtil.beginTransaction();
            Bauteil res = bauteilDAO.findByID(Bauteil.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Bauteil konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	public static Bauteil loadBauteilforFertigungsplan(Long id){
        try {
            Bauteil res = bauteilDAO.findByID(Bauteil.class, id);
            return res;
        } catch (HibernateException ex) {
            System.out.println("Bauteil konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
	
}
