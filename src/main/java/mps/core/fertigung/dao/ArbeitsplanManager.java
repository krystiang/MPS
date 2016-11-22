package mps.core.fertigung.dao;

import org.hibernate.HibernateException;

import mps.core.fertigung.dao.HibernateUtil;
import mps.core.fertigung.Arbeitsplan;

public class ArbeitsplanManager {

    private static ArbeitsplanDAO arbeitsplanDAO = new ArbeitsplanDAO();
	
	public static void saveArbeitsplan(Arbeitsplan v){
        try {
            HibernateUtil.beginTransaction();
            arbeitsplanDAO.save(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Arbeitsplan konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	public static Arbeitsplan loadArbeitsplan(Long id){
        try {
            HibernateUtil.beginTransaction();
            Arbeitsplan res = arbeitsplanDAO.findByID(Arbeitsplan.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("Arbeitsplan konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
