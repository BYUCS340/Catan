package testing.server.persistence.plugins.SQLPlugin;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.model.ServerPlayer;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.SQLPlugin.*;

public class TestSQLPlugin
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
		//plugin.Clear();
	}

	
	@Test
	public void testSQLGameDAO()
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
	public void testSQLUserDAO()
	{
		SQLPlugin plugin =  new SQLPlugin();
		IUserDAO userDao = null;
		try 
		{
			userDao = plugin.GetUserDAO();
			
		}
		catch (PersistenceException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Unable to create User DAO");
		}
		try 
		{
			plugin.StartTransaction();
			assertEquals(userDao.GetAllUsers().size(),0);
			userDao.AddUser(1, "Matthew", "Tesitng");
			plugin.EndTransaction(true);
			
			List<ServerPlayer> players = userDao.GetAllUsers();
			assertEquals(players.size(),1);
			ServerPlayer player = players.get(0);
			assertEquals(player.GetID(), 1);
			assertEquals(player.GetName(), "Matthew");
			
			plugin.StartTransaction();
			userDao.AddUser(3, "Matthew2", "Tesitng");
			plugin.EndTransaction(true);
			
			assertEquals(userDao.GetAllUsers().size(),2);
			
		} 
		catch (PersistenceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try 
			{
				plugin.EndTransaction(false);
			}
			catch (PersistenceException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fail("We probably shouldn't have failed to");
		}
		plugin.Close();
	}
	@Test
	public void testSQLCommandDAO()
	{
		SQLPlugin plugin =  new SQLPlugin();
		ICommandDAO commDao = null;
		try 
		{
			commDao = plugin.GetCommandDAO();
			
		} 
		catch (PersistenceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Unable to create Command DAO");
		}
		
		try 
		{
			plugin.StartTransaction();
			assertEquals(commDao.GetCommandCount(1),0);
			commDao.AddCommand(1, "blob");
			plugin.EndTransaction(true);
			
			assertEquals(commDao.GetCommandCount(1),1);
			assertEquals(commDao.GetCommandCount(2),0);
			
			plugin.StartTransaction();
			commDao.DeleteCommands(1);
			plugin.EndTransaction(true);
			
			assertEquals(commDao.GetCommandCount(1),0);
			
		} 
		catch (PersistenceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try 
			{
				plugin.EndTransaction(false);
			}
			catch (PersistenceException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fail("We probably shouldn't have failed to");
		}
		plugin.Close();
		
	}
		

}
