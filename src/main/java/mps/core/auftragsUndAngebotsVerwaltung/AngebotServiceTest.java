package mps.core.auftragsUndAngebotsVerwaltung;

import static org.junit.Assert.*;
import org.hibernate.Session;
import org.junit.Test;
import mps.core.auftragsUndAngebotsVerwaltung.HibernateUtil;

public class AngebotServiceTest {
	
	//Technical Tests
		@Test
		public void testGetInstance() {
			AngebotService serviceA = AngebotService.getInstance();
			AngebotService serviceB = AngebotService.getInstance();
			assertTrue("There is only one AuftragService",serviceA == serviceB);
		}

		@Test
		public void testEquals() {
			AngebotService serviceA = AngebotService.getInstance();
			AngebotService serviceB = AngebotService.getInstance();
			assertEquals("Only reference equality needed because the class is a singleton",serviceA,serviceB);
		}
		
	//Domain-specific tests 
	@Test
	public void testAngebotErstellen() {
		AngebotService as = AngebotService.getInstance();
		Angebot a1 = as.angebotErstellen(1L, "Gestern", "Morgen", 1147, 1L);
		long a1Nr = a1.getNr();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		Angebot persistentAngebot = (Angebot) s.get(Angebot.class, a1Nr);
		
		s.getTransaction().commit();
		
		assertEquals(a1, persistentAngebot);
	}
	

}
