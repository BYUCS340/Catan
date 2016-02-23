package testing.shared.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.GameRound;
import shared.model.GameState;

public class TestGameState {
	GameState gm;
	@Before
	public void setUp() throws Exception {
		gm = new GameState();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() 
	{
		assertTrue(gm.startGame());
		assertEquals(gm.state, GameRound.FIRSTROUND);
		
		//TODO: so much
		
		//This will change so leaving stubbed out for now
	}

}
