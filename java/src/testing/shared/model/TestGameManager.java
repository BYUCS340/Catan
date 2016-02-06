package testing.shared.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.map.MapController;
import client.map.view.MapView;
import shared.definitions.CatanColor;
import shared.definitions.GameRound;
import shared.model.GameManager;
import shared.model.ModelException;
import shared.model.Player;
import shared.model.map.MapModel;

public class TestGameManager {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayerTurns(){
		GameManager gm = new GameManager();
		try {
			int index = gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			assertEquals(index,0);
			index = gm.AddPlayer("Matt2", CatanColor.RED, false);
			assertEquals(index,1);
			index = gm.AddPlayer("Matt3", CatanColor.GREEN, false);
			assertEquals(index,2);
			index = gm.AddPlayer("Matt4", CatanColor.YELLOW, false);
			assertEquals(index,3);
			
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		MapController controller = new MapController(new MapView(), null, new MapModel());
		gm.map = controller;
		
		gm.StartGame();
		
		try{
			assertEquals(GameRound.FIRSTROUND,gm.CurrentState());
			assertEquals(gm.CurrentPlayersTurn(),0);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),1);
			gm.FinishTurn();
			assertEquals(GameRound.FIRSTROUND,gm.CurrentState());
			assertEquals(gm.CurrentPlayersTurn(),2);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),3);
			assertEquals(GameRound.FIRSTROUND,gm.CurrentState());
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),0);
			assertEquals(GameRound.SECONDROUND,gm.CurrentState());
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),1);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),2);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),3);
			assertEquals(GameRound.SECONDROUND,gm.CurrentState());
			gm.FinishTurn();
			assertEquals(GameRound.ROLLING,gm.CurrentState());
			assertFalse(gm.CanFinishTurn());
		}
		catch (Exception e){
			fail("No exceptions here!");
		}
		gm.payDayForDayz();
		try{
			assertFalse(gm.CanBuyDevCard(gm.CurrentPlayersTurn()));
			int roll = gm.RollDice();
			assertEquals(GameRound.PLAYING,gm.CurrentState());
		}
		catch (Exception e){
			fail("exceptions trying to roll the dice!");
		}
		
		try{
			int roll = gm.RollDice();
			fail("We should have got an exceptions rolling the dice!");
		}
		catch (Exception e){
			
		}
		
		try{
			
		}
		catch (Exception e){
			
		}
		
		
		
		
	}
	@Test
	public void testPlayerAdding()
	{
		GameManager gm = new GameManager();
		try {
			int index = gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			assertEquals(index,0);
			index = gm.AddPlayer("Matt2", CatanColor.RED, false);
			assertEquals(index,1);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		
		//make sure we can't add the same color twice
		try{
			gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			fail("We can't add the same color twice");
		} catch (ModelException e) {
			//Good good
		}
		
		try{
			int index = gm.AddPlayer("Matt3", CatanColor.GREEN, false);
			assertEquals(index,2);
			index = gm.AddPlayer("Matt4", CatanColor.YELLOW, false);
			assertEquals(index,3);
			
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		
		try{
			int index = gm.AddPlayer("Matt5", CatanColor.WHITE, false);
			fail("Can't add more players");
		}
		catch (ModelException e) {
			
			
		}
	}

}
