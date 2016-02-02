package testing.shared.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.model.VictoryPointManager;

public class TestVictoryPoint {
	VictoryPointManager vpm;
	@Before
	public void setUp() throws Exception {
		vpm = new VictoryPointManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVictoryPointManager() {
		assertFalse(vpm.anyWinner());
		vpm.playerBuiltSettlement(0);
		assertEquals(vpm.getVictoryPoints(0),1);
		vpm.playerBuiltSettlement(1);
		assertEquals(vpm.getVictoryPoints(0),1);
	}
	
	@Test
	public void testVictoryPointManagerWinner() {
		vpm = new VictoryPointManager();
		
	}

}
