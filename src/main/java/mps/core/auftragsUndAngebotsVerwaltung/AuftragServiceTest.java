package mps.core.auftragsUndAngebotsVerwaltung;

import static org.junit.Assert.*;

import org.junit.Test;

public class AuftragServiceTest {
	
	//Technical Tests
	@Test
	public void testGetInstance() {
		AuftragService serviceA = AuftragService.getInstance();
		AuftragService serviceB = AuftragService.getInstance();
		assertTrue("There is only one AuftragService",serviceA == serviceB);
	}

	@Test
	public void testEquals() {
		AuftragService serviceA = AuftragService.getInstance();
		AuftragService serviceB = AuftragService.getInstance();
		assertEquals("Only reference equality needed because the class is a singleton",serviceA,serviceB);
	}
	
	
	@Test
	public void testAuftragErstellen1() {
		AngebotService as = AngebotService.getInstance();
		Angebot angebot = as.angebotErstellen(null, "17.5", "18.5", 1, 20L);
		
		AuftragService auftragService = AuftragService.getInstance();
		Auftrag auftragMitAngebot = auftragService.auftragErstellen("16.5",angebot.getNr());
		
		//Check if the association has been made correctly 
		assertEquals(angebot,auftragMitAngebot.getAngebot());
	}

}
