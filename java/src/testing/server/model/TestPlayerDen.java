/**
 * 
 */
package testing.server.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.model.GameException;
import server.model.PlayerDen;
import server.model.ServerPlayer;

/**
 * @author matthewcarlson
 *
 */
public class TestPlayerDen {

	PlayerDen pd;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		pd = new PlayerDen();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void test() 
	{
		String username = "mattface";
		String password = "themyththelegend";
		
		String username2 = "dankey";
		String password2 =" kang";
		
		//Check the login
		int userID = pd.CheckLogin(username, password);
		assertEquals(userID,-1); //we should get not logged in
		
		//Register the player
		try 
		{
			userID = pd.RegisterPlayer(username, password);
			assertNotEquals(userID, -1);
		}
		catch (GameException e) 
		{
			fail("This user wasn't already registered");
		}
		
		//Register another player
		int userID2 = -1;
		try 
		{
			userID2 = pd.RegisterPlayer(username2, password2);
			
		}
		catch (GameException e) 
		{
			fail("This user 2 wasn't already registered");
		}
		
		//Try and reregister player 1
		try 
		{
			pd.RegisterPlayer(username, password);
			fail("We shouldn't be able to register the player twice");
		}
		catch (GameException e) 
		{
		}
		
		assertNotEquals(userID, userID2);
		
		//Repeat the check login for user 1
		int userIDr = pd.CheckLogin(username, password);
		assertEquals(userID,userIDr);
		
		//Repeat the check login for user 2
		int userID2r = pd.CheckLogin(username2, password2);
		assertEquals(userID2,userID2r);
		
		ServerPlayer sp1;
		try 
		{
			sp1 = pd.GetPlayerID(userID);
			assertEquals(sp1.GetName(),username);
			assertEquals(sp1.GetID(),userID);
		} 
		catch (GameException e1) 
		{
			// TODO Auto-generated catch block
			fail("Player 1 not found");
			e1.printStackTrace();
		}
		
		
		ServerPlayer sp2;
		try 
		{
			sp2 = pd.GetPlayerID(userID2);
			assertEquals(sp2.GetName(),username2);
			assertEquals(sp2.GetID(),userID2);
		} 
		catch (GameException e) 
		{
			// TODO Auto-generated catch block
			fail("Player 2 at ID"+userID2+" not found"+e.getMessage());
		}
		
		
		
		
	}

}
