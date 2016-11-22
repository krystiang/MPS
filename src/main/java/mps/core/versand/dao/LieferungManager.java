/**
 * 
 */
package mps.core.versand.dao;

import org.hibernate.HibernateException;

import mps.core.versand.Lieferung;

/**
 * @author Christian
 *
 */
public class LieferungManager {
	
	private static LieferungDAO lieferungDAO = new LieferungDAO();
	
	public static void saveLieferung(Lieferung l) {
		try {
			HibernateUtil.beginTransaction();
			lieferungDAO.save(l);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Lieferung konnte nicht gespeichert werden");
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
		}
		
	}

}
