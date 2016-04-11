package testing.server.persistence.plugins.SQLPlugin;

import org.junit.*;
import server.model.ServerPlayer;
import server.persistence.IGameDAO;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FileUserDAO;
import server.persistence.plugins.FilePlugin.FilenameUtils;
import server.persistence.plugins.SQLPlugin.SQLPlugin;
import server.persistence.plugins.SQLPlugin.SQLUserDAO;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SQLUserDAOTest
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
	public void testSQLUserDAOGenerally()
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
	public void testAddUser() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();
		IUserDAO userDAO = plugin.GetUserDAO();


		userDAO.AddUser(1, "johnny", "pills123");
		List<ServerPlayer> users = userDAO.GetAllUsers();
		assertEquals(users.size(),1);
		assertEquals(users.get(0).GetName(), "johnny");
		assertEquals(users.get(0).GetID(), 1);
		assertEquals(users.get(0).GetPassword(), "pills123");



		plugin.Close();

	} 
	
	@Test
	public void testGetUsers() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();
		IUserDAO userDAO = plugin.GetUserDAO();

		userDAO.AddUser(1, "johnny", "pills123");
		userDAO.AddUser(2, "james", "excellence1");
		userDAO.AddUser(3, "joni", "john123");
		userDAO.AddUser(4, "orange", "red567");
		
		List<ServerPlayer> players = userDAO.GetAllUsers();
		assertTrue(players.size() == 4);
		
		boolean user1Found = false;
		boolean user2Found = false;
		boolean user3Found = false;
		boolean user4Found = false;
		
		for(ServerPlayer sp : players)
		{
			if(sp.GetID() == 1 && sp.GetName().equals("johnny") && sp.PasswordMatches("pills123"))
				user1Found = true;
			else if(sp.GetID() == 2 && sp.GetName().equals("james") && sp.PasswordMatches("excellence1"))
				user2Found = true;
			else if(sp.GetID() == 3 && sp.GetName().equals("joni") && sp.PasswordMatches("john123"))
				user3Found = true;
			else if(sp.GetID() == 4 && sp.GetName().equals("orange") && sp.PasswordMatches("red567"))
				user4Found = true;
		}
		
		assertTrue(user1Found);
		assertTrue(user2Found);
		assertTrue(user3Found);
		assertTrue(user4Found);

		plugin.Close();

	}
}
