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
		
		vpm.setPlayerToHaveLongestRoad(0);
		assertEquals(vpm.getVictoryPoints(0),3);
		vpm.checkPlayerArmySize(0, 5);
		assertEquals(vpm.getVictoryPoints(0),5);
		
		vpm.playerBuiltCity(0);
		assertEquals(vpm.getVictoryPoints(0),6);
		
		vpm.playerBuiltCity(0);
		assertEquals(vpm.getVictoryPoints(0),7);
		
		vpm.playerBuiltCity(0);
		assertEquals(vpm.getVictoryPoints(0),8);
		
		vpm.playerBuiltCity(0);
		assertEquals(vpm.getVictoryPoints(0),9);
		
		assertFalse(vpm.anyWinner());
		vpm.playerBuiltCity(0);
		assertEquals(vpm.getVictoryPoints(0),10);
		assertTrue(vpm.anyWinner());
		assertEquals(vpm.winner(),0);
		
		vpm.checkPlayerArmySize(2, 4);
		assertEquals(vpm.getVictoryPoints(0),10);
		vpm.checkPlayerArmySize(2, 10);
		assertEquals(vpm.getVictoryPoints(0),8);
		assertEquals(vpm.getVictoryPoints(2), 2);
		assertFalse(vpm.anyWinner());
		
		vpm.setPlayerToHaveLongestRoad(2);
		assertEquals(vpm.getVictoryPoints(0),6);
		assertEquals(vpm.getVictoryPoints(2), 4);
		assertFalse(vpm.anyWinner());
		
	}
	
	@Test
	public void testVictoryPointManagerWinner() {
		vpm = new VictoryPointManager();
		vpm.setPlayerToHaveLongestRoad(0);
		vpm.checkPlayerArmySize(0, 5);
	}

}
