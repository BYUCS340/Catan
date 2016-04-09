package testing.server.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.model.GameException;
import server.model.GameTable;

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
		
		String username2 = "mrBrightside";
		String password2 = "feelingfine";
		
		//try to login
		try 
		{
			gt.Login(username, password);
			fail("We shouldn't have been able to login");
		}
		catch (GameException e){}
		
		//Register the player
		try
		{
			gt.RegisterPlayer(username, password);
		}
		catch (GameException e)
		{
			fail("We should be able to register the player");
		}
		
		//Re-register the player
		try
		{
			gt.RegisterPlayer(username, password);
			fail("We shouldn't be able to register the player again");
		}
		catch (GameException e){}
		
		//PLAYER 2
		
		//try to login
		try 
		{
			gt.Login(username2, password2);
			fail("We shouldn't have been able to login");
		}
		catch (GameException e){}
		
		
		//Register the player
		try
		{
			gt.RegisterPlayer(username2, password2);
		}
		catch (GameException e){
			fail("We should be able to register the player");
		}
		
		//Re-register the player
		try
		{
			gt.RegisterPlayer(username2, password2);
			fail("We shouldn't be able to register the player again");
		}
		catch (GameException e){}
	}

}
