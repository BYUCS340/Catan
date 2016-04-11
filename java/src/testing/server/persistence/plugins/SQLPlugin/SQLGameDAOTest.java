/**
 * 
 */
package testing.server.persistence.plugins.SQLPlugin;

import org.junit.*;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.FileGameDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FilenameUtils;
import server.persistence.plugins.SQLPlugin.SQLPlugin;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Parker Ridd
 *
 */
public class SQLGameDAOTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{


	}

	@After
	public void tearDown() throws Exception
	{
//		plugin.Clear();
	}

	@Test
	public void testSQLGameDAOGenerally()
	{
		SQLPlugin plugin =  new SQLPlugin();
		IGameDAO dao = null;
		try
		{
			dao = plugin.GetGameDAO();

		}
		catch (PersistenceException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Unable to create Game DAO");
		}
		try
		{
			assertEquals(dao.GetAllGames().size(),0);
			plugin.StartTransaction();
			dao.AddGame(1, "Bloby");
			plugin.EndTransaction(true);
			List<String> games = dao.GetAllGames();
			assertEquals(games.size(),1);

			assertEquals(games.get(0),"Bloby");

			plugin.StartTransaction();
			dao.UpdateGame(1, "blob");
			plugin.EndTransaction(true);

			games = dao.GetAllGames();
			assertEquals(games.size(),1);
			assertEquals(games.get(0),"blob");


		}
		catch (PersistenceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{
				plugin.EndTransaction(false);
			}
			catch (PersistenceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fail("We probably shouldn't have failed to");
		}
		plugin.Close();
	}

	@Test
	public void testAddGame() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();

		plugin.StartTransaction();
		IGameDAO gameDao = plugin.GetGameDAO();

		String gameStr = "HI I'M A FAKE GAME ACTING LIKE I'M A REAL GAME";
		int gameID = 476;
		gameDao.AddGame(gameID, gameStr);
		plugin.EndTransaction(true);

		List<String> games = gameDao.GetAllGames();
		assertEquals(games.size(),1);
		assertEquals(games.get(0), gameStr);
		plugin.Close();

	}
	
	@Test
	public void testUpdateGame() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();

		IGameDAO gameDao = plugin.GetGameDAO();

		String gameStr = "HI I'M A FAKE GAME ACTING LIKE I'M A REAL GAME";
		int gameID = 476;
		gameDao.AddGame(gameID, gameStr);
		
		String updatedString = "THE FAKE GAME HAS BEEN UPDATED NOW!!! :D";
		gameDao.UpdateGame(476, updatedString);

		List<String> games = gameDao.GetAllGames();
		assertEquals(games.size(),1);
		assertTrue(!games.get(0).equals(gameStr));
		assertEquals(games.get(0), updatedString);
		plugin.Close();

	}
	
	@Test
	public void testGetAllGames() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();
		IGameDAO gameDao = plugin.GetGameDAO();

		String gameStr1 = "HI";
		int gameID1 = 476;
		gameDao.AddGame(gameID1, gameStr1);
		
		String gameStr2 = "I'M";
		int gameID2 = 892;
		gameDao.AddGame(gameID2, gameStr2);
		
		String gameStr3 = "HAPPY";
		int gameID3 = 1;
		gameDao.AddGame(gameID3, gameStr3);
		
		List<String> gameList = gameDao.GetAllGames();
		
		boolean foundGame1 = false;
		boolean foundGame2 = false;
		boolean foundGame3 = false;
		
		for(String s : gameList)
		{
			if(s.equals(gameStr1))
			{
				foundGame1 = true;
			}
			else if(s.equals(gameStr2))
			{
				foundGame2 = true;
			}
			else if(s.equals(gameStr3))
			{
				foundGame3 = true;
			}
		}
		
		assertTrue(foundGame1);
		assertTrue(foundGame2);
		assertTrue(foundGame3);
		plugin.Close();

	}
	


}
