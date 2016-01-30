package testing.client.networking;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.networking.RealServerProxy;
import shared.networking.UserCookie;

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

}
