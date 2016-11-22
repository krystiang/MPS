package mps.core.versand.dao;

import mps.core.versand.Transportauftrag;

import org.hibernate.HibernateException;

public class TransportauftragManager {
	
	private static TransportauftragDAO transportauftragDAO =
			new TransportauftragDAO();
	
	public static void saveTransportauftrag(Transportauftrag t) {
		try {
			HibernateUtil.beginTransaction();
			transportauftragDAO.save(t);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Transportauftrag konnte nicht gespeichert werden");
			ex.printStackTrace();
			HibernateUtil.rollbackTransaction();
		}
	}

}
