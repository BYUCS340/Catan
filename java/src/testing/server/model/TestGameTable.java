package testing.server.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.cookie.ServerCookie;
import server.model.GameException;
import server.model.GameTable;
import server.model.ServerGameManager;
import server.model.ServerPlayer;

public class TestGameTable 
{
	GameTable gt;
	@Before
	public void setUp() throws Exception 
	{
		gt = new GameTable();
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void test() 
	{
		String username = "amIhuman";
		String password = "orAmIdancer";
		ServerCookie sc = null;
		ServerPlayer sp = null;
		int playerID = -1;
		
		String username2 = "mrBrightside";
		String password2 = "feelingfine";
		ServerCookie sc2 = null;
		ServerPlayer sp2 = null;
		int playerID2 = -1;
		
		//try to login
		try 
		{
			sc = gt.Login(username, password);
			fail("We shouldn't have been able to login");
		}
		catch (GameException e){}
		
		//Register the player
		try{
			sc = gt.RegisterPlayer(username, password);
			playerID = sc.getPlayerID();
		}
		catch (GameException e){
			fail("We should be able to register the player");
		}
		
		//Re-register the player
		try{
			gt.RegisterPlayer(username, password);
			fail("We shouldn't be able to register the player again");
		}
		catch (GameException e){}
		
		//Get the current player
		try 
		{
			sp = gt.CookieCheck(sc.getCookieText());
			assertEquals(sp.GetName(), username);
			assertEquals(sp.GetID(), playerID);
		}
		catch (GameException e) 
		{
			e.printStackTrace();
			fail("Cookie check failed!");
		}
		
		//PLAYER 2
		
		//try to login
		try 
		{
			sc2 = gt.Login(username2, password2);
			fail("We shouldn't have been able to login");
		}
		catch (GameException e){}
		
		
		//Register the player
		try{
			sc2 = gt.RegisterPlayer(username2, password2);
			playerID2 = sc2.getPlayerID();
		}
		catch (GameException e){
			fail("We should be able to register the player");
		}
		
		//Re-register the player
		try{
			gt.RegisterPlayer(username2, password2);
			fail("We shouldn't be able to register the player again");
		}
		catch (GameException e){}
		
		//Get the current player
		try 
		{
			sp2 = gt.CookieCheck(sc2.getCookieText());
			assertEquals(sp2.GetName(), username2);
			assertEquals(sp2.GetID(), playerID2);
		}
		catch (GameException e) 
		{
			e.printStackTrace();
			fail("Cookie check failed!");
		}

		
		
		
		//Get the current player
		try 
		{
			sp = gt.CookieCheck(sc.getCookieText());
			assertEquals(sp.GetName(), username);
			assertEquals(sp.GetID(), playerID);
		}
		catch (GameException e) 
		{
			e.printStackTrace();
			fail("Cookie check failed!");
		}
		
		
		//Check to make sure the games run properly
		assertEquals(gt.GetNumberGames(),0);
		String gameName = "BrawlRoyal";
		int gameID = gt.CreateGame(gameName, false, false, false);
		assertEquals(gt.GetNumberGames(),1);
		ServerGameManager sgm = null;
		try 
		{
			sgm = gt.GetGame(gameID);
			assertEquals(gameName,sgm.GetGameTitle());
		}
		catch (GameException e) 
		{
			//e.printStackTrace();
			fail("We should have found the game");
		}
		
		
	}

}
