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
	SQLPlugin plugin;
	
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
		plugin =  new SQLPlugin();
		plugin.Clear();
	}

	@After
	public void tearDown() throws Exception
	{
		plugin.Clear();
	}

	/*
	@Test
	public void testSQLGameDAO()
	{
		IGameDAO dao = plugin.GetGameDAO();
		try 
		{
			assertEquals(dao.GetAllGames().size(),0);
			dao.AddGame(1, "Bloby");
			List<String> games = dao.GetAllGames();
			assertEquals(games.size(),1);
			
			assertEquals(games.get(0),"Bloby");
			
			dao.UpdateGame(1, "blob");
			
			games = dao.GetAllGames();
			assertEquals(games.size(),1);
			assertEquals(games.get(0),"blob");
			
				
		} 
		catch (PersistenceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("We probably shouldn't have failed to");
		}
		
		
	}
	
	@Test
	public void testSQLUserDAO()
	{
		IUserDAO dao = plugin.GetUserDAO();
		try 
		{
			assertEquals(dao.GetAllUsers().size(),0);
			dao.AddUser(1, "Matthew", "Tesitng");
			List<ServerPlayer> players = dao.GetAllUsers();
			assertEquals(players.size(),1);
			ServerPlayer player = players.get(1);
			assertEquals(player.GetID(), 1);
			assertEquals(player.GetName(), "Matthew");
			
			dao.AddUser(3, "Matthew2", "Tesitng");
			assertEquals(dao.GetAllUsers().size(),2);
			
		} 
		catch (PersistenceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("We probably shouldn't have failed to");
		}
		
	}
	
	@Test
	public void testSQLCommandsDAO()
	{
		ICommandDAO dao = plugin.GetCommandDAO();
		
		try 
		{
			assertEquals(dao.GetCommandCount(1),0);
			dao.AddCommand(1, "blob");
			assertEquals(dao.GetCommandCount(1),1);
			assertEquals(dao.GetCommandCount(2),0);
			
			dao.DeleteCommands(1);
			assertEquals(dao.GetCommandCount(1),0);
			
		} 
		catch (PersistenceException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("We probably shouldn't have failed to");
		}
		
	}
	*/
	@Test
	public void testSQLPlugin()
	{
		
		
	}
	

}
