package testing.server.persistence.plugins.SQLPlugin;

import org.junit.*;
import server.model.ServerPlayer;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;
import server.persistence.plugins.SQLPlugin.SQLPlugin;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLPluginTest
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
	public void testClearDatabase() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();



		//  insert into db
		IGameDAO gameDao = plugin.GetGameDAO();
		IUserDAO userDao = plugin.GetUserDAO();
		ICommandDAO commandDao = plugin.GetCommandDAO();

		plugin.StartTransaction();
		gameDao.AddGame(1, "game1");
		userDao.AddUser(1, "user1", "passIThink");
		commandDao.AddCommand(1, "command1");
		plugin.EndTransaction(true);

		//  check that insert worked
		List<String> games = gameDao.GetAllGames();
		assertTrue(games.size() > 0);
		List<ServerPlayer> users = userDao.GetAllUsers();
		assertTrue(users.size() > 0);
		List<String> commands = commandDao.GetCommands();
		assertTrue(commands.size() > 0);


		plugin.Clear();

		//  check that games cleared
		List<String> games_cleared = gameDao.GetAllGames();
		assertEquals(games_cleared.size(),0);
		List<ServerPlayer> users_cleared = userDao.GetAllUsers();
		assertEquals(users_cleared.size(),0);
		List<String> commands_cleared = commandDao.GetCommands();
		assertEquals(commands_cleared.size(),0);

	}
	
	@Test
	public void testGetDAOs() throws Exception
	{
		SQLPlugin testTmpPlugin =  new SQLPlugin();
		ICommandDAO commandDAO = testTmpPlugin.GetCommandDAO();
		IGameDAO gameDAO = testTmpPlugin.GetGameDAO();
		IUserDAO userDAO = testTmpPlugin.GetUserDAO();
	}

}
