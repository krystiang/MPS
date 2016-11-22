package mps.core.fertigung.dao;

import org.hibernate.HibernateException;
import mps.core.fertigung.StuecklistenPosition;
import mps.core.fertigung.dao.HibernateUtil;

public class StuecklistenPositionManager {

    private static StuecklistenPositionDAO stuecklistenPositionDAO = new StuecklistenPositionDAO();
	
	public static void saveStuecklistenPosition(StuecklistenPosition v){
        try {
            HibernateUtil.beginTransaction();
            stuecklistenPositionDAO.save(v);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("StuecklistenPosition konnte nicht gespeichert werden");
            HibernateUtil.rollbackTransaction();
        }
	}
	
	public static StuecklistenPosition loadStuecklistenPosition(Long id){
        try {
            HibernateUtil.beginTransaction();
            StuecklistenPosition res = stuecklistenPositionDAO.findByID(StuecklistenPosition.class, id);
            HibernateUtil.commitTransaction();
            return res;
        } catch (HibernateException ex) {
            System.out.println("StuecklistenPosition konnte nicht geladen werden");
            HibernateUtil.rollbackTransaction();
        }
        return null;
	}
	
}
