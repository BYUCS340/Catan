package testing.server.persistence.plugins.SQLPlugin;

import org.junit.*;
import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.FileCommandDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FilenameUtils;
import server.persistence.plugins.SQLPlugin.SQLCommandDAO;
import server.persistence.plugins.SQLPlugin.SQLPlugin;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SQLCommandDAOTest
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
	public void testSQLCommandDAOGenerally()
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

			plugin.StartTransaction();
			commDao.AddCommand(2, "blob");
			plugin.EndTransaction(true);

			assertEquals(commDao.GetCommandCount(1),0);
			assertEquals(commDao.GetCommandCount(2),1);
			List<String> commands = commDao.GetCommands();
			assertEquals(commands.size(),1);
			assertEquals(commands.get(0), "blob");

			plugin.StartTransaction();
			commDao.DeleteCommands(1);
			plugin.EndTransaction(true);

			commands = commDao.GetCommands();
			assertEquals(commands.size(),1);



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
	public void testAddCommand() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();

		ICommandDAO commandDao = plugin.GetCommandDAO();
		
		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		int gameID = 4;

		plugin.StartTransaction();
		commandDao.AddCommand(gameID, cmd1);
		plugin.EndTransaction(true);

		List<String> commands = commandDao.GetCommands();
		assertTrue(commands.size() == 1);








		plugin.Close();

	}






	@Test
	public void testGetCommandCount() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		plugin.Clear();

		ICommandDAO commandDao = plugin.GetCommandDAO();

		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		String cmd2 = "TWO IS BETTER THAN ONE";
		String cmd3 = "THREE IS A CROWD";
		int gameID = 4;


		plugin.StartTransaction();

		commandDao.AddCommand(gameID, cmd1);
		commandDao.AddCommand(gameID, cmd2);
		commandDao.AddCommand(gameID, cmd3);
		plugin.EndTransaction(true);

		int numCommands = commandDao.GetCommandCount(gameID);

		assertTrue(numCommands == 3);
		plugin.Close();

	}

	@Test
	public void testDeleteCommands() throws Exception
	{
		SQLPlugin plugin =  new SQLPlugin();
		ICommandDAO commandDao = plugin.GetCommandDAO();

		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		String cmd2 = "TWO IS BETTER THAN ONE";
		String cmd3 = "THREE IS A CROWD";
		String cmd4 = "WHY AM I STILL CODING AT 12:11 AM?";
		int gameID = 4;
		int gameID2 = 8;



		plugin.StartTransaction();

		commandDao.AddCommand(gameID, cmd1);
		commandDao.AddCommand(gameID, cmd2);
		commandDao.AddCommand(gameID, cmd3);
		commandDao.AddCommand(gameID2, cmd4);
		plugin.EndTransaction(true);


		plugin.StartTransaction();

		commandDao.DeleteCommands(gameID);
		plugin.EndTransaction(true);

		assertTrue(commandDao.GetCommandCount(gameID) == 0);
		assertTrue(commandDao.GetCommandCount(gameID2) == 1);

		plugin.Close();

	}

}
