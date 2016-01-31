package testing.client.networking;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.networking.RealServerProxy;
import shared.definitions.CatanColor;
import shared.networking.UserCookie;
import shared.networking.transport.NetGame;

public class RealServerProxyTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		//TODO put data on server
	}

	@After
	public void tearDown() throws Exception
	{
		//TODO wipe server
	}

	@Test
	public void testRegisterUser() throws Exception
	{
		RealServerProxy testProxy = new RealServerProxy();
		boolean couldRegister = testProxy.registerUser("reguser", "imatest");
		
		//double check that the user could be registered
		assertTrue(couldRegister);
		
		//make sure the same user can't register twice
		couldRegister = testProxy.registerUser("reguser", "imatest");
		assertTrue(!couldRegister);
		
	}
	
	@Test
	public void testLoginUser() throws Exception
	{
		RealServerProxy testProxy = new RealServerProxy();
		boolean couldRegister = testProxy.registerUser("qwer", "rewq");
		
		assertTrue(couldRegister);
		boolean couldLogin = testProxy.loginUser("qwer", "rewq");
		assertTrue(couldLogin);
		
		UserCookie uCookie = testProxy.getUserCookie();
		
		//make sure the cookie was set correctly
		String user = uCookie.getUsername();
		assertTrue(user.equalsIgnoreCase("qwer"));
		
		String password = uCookie.getPassword();
		assertTrue(password.equalsIgnoreCase("rewq"));
		
		testProxy.clearCookies();
		
		//make sure bad login attempts are rejected without an exception
		couldLogin = testProxy.loginUser("randomness", "MoreRand");
		assertTrue(!couldLogin);
		
	}
	
	@Test
	public void testListGames() throws Exception
	{
		RealServerProxy testProxy = new RealServerProxy();
		
		//get result from server
		List<NetGame> gList = testProxy.listGames();
		NetGame testGameRes = gList.get(0);
		
		//test the game at index 0
		assertTrue(testGameRes.getId() == 0);
		assertTrue(testGameRes.getTitle().equals("Default Game"));
		assertTrue(testGameRes.getNetPlayers().get(0).getName().equals("Sam"));
		assertTrue(testGameRes.getNetPlayers().get(0).getColor() == CatanColor.ORANGE);
		assertTrue(testGameRes.getNetPlayers().get(0).getPlayerID() == 0);
		assertTrue(testGameRes.getNetPlayers().get(1).getName().equals("Brooke"));
		assertTrue(testGameRes.getNetPlayers().get(1).getColor() == CatanColor.BLUE);
		assertTrue(testGameRes.getNetPlayers().get(1).getPlayerID() == 1);
		assertTrue(testGameRes.getNetPlayers().get(2).getName().equals("Pete"));
		assertTrue(testGameRes.getNetPlayers().get(2).getColor() == CatanColor.RED);
		assertTrue(testGameRes.getNetPlayers().get(2).getPlayerID() == 10);
		assertTrue(testGameRes.getNetPlayers().get(3).getName().equals("Mark"));
		assertTrue(testGameRes.getNetPlayers().get(3).getColor() == CatanColor.GREEN);
		assertTrue(testGameRes.getNetPlayers().get(3).getPlayerID() == 11);
		
		//TODO: Make this test more complete and not reliant on the default instance
		//of the server
		
	}

}
