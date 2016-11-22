package mps.core.versand;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersandServiceTest {

	@Test
	public void testStarteAusliefererung() {
		VersandService vs = VersandService.getInstance();
		
		long transportauftrag1Nr = vs.starteAuslieferung(1L);
		long transportauftrag2Nr = vs.starteAuslieferung(2L);
		
		assertTrue(transportauftrag1Nr < transportauftrag2Nr);
		
	}

}
