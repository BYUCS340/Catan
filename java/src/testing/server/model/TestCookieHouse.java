package testing.server.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.cookie.CookieHouse;
import server.cookie.ServerCookie;
import server.model.GameException;

public class TestCookieHouse {

	CookieHouse ch;
	@Before
	public void setUp() throws Exception {
		ch = new CookieHouse();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//Bake a cookie
		ServerCookie sc = ch.bakeCookie(5);
		assertEquals(sc.getPlayerID(),5);
		assertFalse(sc.isExpired());
	
		try 
		{
			ServerCookie sc2 = ch.checkCookie(sc.getCookieText());
			assertEquals(sc2.getPlayerID(),5);
			assertEquals(sc,sc2);
		} 
		catch (GameException e) 
		{
			fail("Cookie should have been found");
		}
		
		
		
	}

}
