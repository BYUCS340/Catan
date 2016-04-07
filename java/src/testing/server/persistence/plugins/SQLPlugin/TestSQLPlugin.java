package testing.server.persistence.plugins.SQLPlugin;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.model.RealServerGameManager;
import server.model.ServerGameManager;
import server.model.ServerPlayer;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.SQLPlugin.*;
import shared.networking.SerializationUtils;

public class TestSQLPlugin
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
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();
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
			dao.AddGame(1, "Bloby");
			plugin.EndTransaction(true);
			List<String> games = dao.GetAllGames();
			assertEquals(games.size(),1);
			
			assertEquals(games.get(0),"Bloby");
			
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
		
		
	}
	
	@Test
	public void testSQLUserDAO()
	{
		SQLPlugin plugin =  new SQLPlugin();
		IUserDAO dao = null;
		try 
		{
			dao = plugin.GetUserDAO();
			
		}
		catch (PersistenceException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Unable to create User DAO");
		}
		try 
		{
			assertEquals(dao.GetAllUsers().size(),0);
			dao.AddUser(1, "Matthew", "Tesitng");
			plugin.EndTransaction(true);
			List<ServerPlayer> players = dao.GetAllUsers();
			assertEquals(players.size(),1);
			ServerPlayer player = players.get(1);
			assertEquals(player.GetID(), 1);
			assertEquals(player.GetName(), "Matthew");
			
			dao.AddUser(3, "Matthew2", "Tesitng");
			plugin.EndTransaction(true);
			assertEquals(dao.GetAllUsers().size(),2);
			
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
		
	}
	
	@Test
	public void testSQLCommandsDAO()
	{
		SQLPlugin plugin =  new SQLPlugin();
		ICommandDAO dao = null;
		try 
		{
			dao = plugin.GetCommandDAO();
			
		} 
		catch (PersistenceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail("Unable to create Command DAO");
		}
		
		try 
		{
			assertEquals(dao.GetCommandCount(1),0);
			dao.AddCommand(1, "blob");
			plugin.EndTransaction(true);
			assertEquals(dao.GetCommandCount(1),1);
			assertEquals(dao.GetCommandCount(2),0);
			
			dao.DeleteCommands(1);
			plugin.EndTransaction(true);
			assertEquals(dao.GetCommandCount(1),0);
			
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
		
	}
	
	@Test
	public void testSQLPlugin()
	{
		
		
	}
	

}
