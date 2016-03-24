package testing.shared.model;
import static org.junit.Assert.*;

import org.junit.Test;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.model.GameManager;
import shared.model.ModelException;

public class TestGameManager 
{
	@Test
	public void testPlayerTurns()
	{
		GameManager gm = new GameManager();
		try 
		{
			int index = gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			assertEquals(index,0);
			index = gm.AddPlayer("Matt2", CatanColor.RED, false);
			assertEquals(index,1);
			index = gm.AddPlayer("Matt3", CatanColor.GREEN, false);
			assertEquals(index,2);
			index = gm.AddPlayer("Matt4", CatanColor.YELLOW, false);
			assertEquals(index,3);
			
		} 
		catch (ModelException e) 
		{
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		
		gm.StartGame();
		
		//Get through the first two rounds for each player
		try
		{
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
			assertEquals(gm.CurrentPlayersTurn(),3);
			assertEquals(GameRound.SECONDROUND,gm.CurrentState());
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),2);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),1);
			gm.FinishTurn();
			assertEquals(gm.CurrentPlayersTurn(),0);
			assertEquals(GameRound.SECONDROUND,gm.CurrentState());
			gm.FinishTurn();
			assertEquals(GameRound.ROLLING,gm.CurrentState());
			assertFalse(gm.CanFinishTurn());
		}
		catch (Exception e)
		{
			fail("No exceptions here!");
		}
		
		//Give everyone 5 of every resource
		gm.payDayForDayz();
		try
		{
			assertFalse(gm.CanBuyDevCard(gm.CurrentPlayersTurn()));
			int roll = gm.RollDice();
			//If we roll a 7 then we're robbing
			if (roll ==7)
			{
				//assertEquals(GameRound.ROBBING,gm.CurrentState());
				gm.placeRobber(gm.CurrentPlayersTurn());
			}
			
			assertEquals(GameRound.PLAYING,gm.CurrentState());
		}
		catch (Exception e)
		{
			System.err.println(e);
			fail("exceptions trying to roll the dice!");
		}
		
		//Make sure we can't roll the dice again
		try
		{
			gm.RollDice();
			fail("We should have got an exceptions rolling the dice!");
		}
		catch (Exception e){
			
		}
		
		//Try buying a dev card
		try{
			assertTrue(gm.CanBuyDevCard(gm.CurrentPlayersTurn()));
			DevCardType card = gm.BuyDevCard(gm.CurrentPlayersTurn());
			System.out.print("Bought card: ");
			System.out.println(card.toString());
		}
		catch (Exception e){
			e.printStackTrace();
			fail("We failed in buying a dev card");
			
		}
		
		//Go to the next player's turn
		try{
			assertEquals(gm.CurrentPlayersTurn(),0);
			gm.FinishTurn();
			assertEquals(GameRound.ROLLING,gm.CurrentState());
			assertEquals(gm.CurrentPlayersTurn(),1);
			int roll = gm.RollDice();
			if (roll ==7)
			{
				assertEquals(GameRound.ROBBING,gm.CurrentState());
				gm.placeRobber(gm.CurrentPlayersTurn());
			}
			assertEquals(GameRound.PLAYING,gm.CurrentState());
			
		}
		catch (Exception e){
			fail("Shouldn't have failed on second player turn");
		}
		
		
		
	}
	@Test
	public void testPlayerAdding()
	{
		GameManager gm = new GameManager();
		try 
		{
			int index = gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			assertEquals(index,0);
			index = gm.AddPlayer("Matt2", CatanColor.RED, false);
			assertEquals(index,1);
		} 
		catch (ModelException e) 
		{
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		
		//make sure we can't add the same color twice
		try
		{
			gm.AddPlayer("Matt1", CatanColor.BLUE, false);
			fail("We can't add the same color twice");
		} 
		catch (ModelException e) 
		{
			//Good good
		}
		
		try
		{
			int index = gm.AddPlayer("Matt3", CatanColor.GREEN, false);
			assertEquals(index,2);
			index = gm.AddPlayer("Matt4", CatanColor.YELLOW, false);
			assertEquals(index,3);
		} 
		catch (ModelException e)
		{
			fail("Player wasn't able to add");
			e.printStackTrace();
		}
		
		try
		{
			gm.AddPlayer("Matt5", CatanColor.WHITE, false);
			fail("Can't add more players");
		}
		catch (ModelException e) { }
	}

}
