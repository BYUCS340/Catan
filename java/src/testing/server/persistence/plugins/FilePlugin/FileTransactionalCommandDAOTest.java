package testing.server.persistence.plugins.FilePlugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.persistence.ICommandDAO;
import server.persistence.plugins.FilePlugin.FileCommandDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FileTransactionManager;
import server.persistence.plugins.FilePlugin.FileTransactionalCommandDAO;
import server.persistence.plugins.FilePlugin.FilenameUtils;

public class FileTransactionalCommandDAOTest
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
		File dataFolder = new File(FilenameUtils.dataDir);
		if(dataFolder.exists())
		{
			FilePersistenceUtils.deleteFolder(dataFolder);
		}
	}

	@After
	public void tearDown() throws Exception
	{
		File dataFolder = new File(FilenameUtils.dataDir);
		if(dataFolder.exists())
		{
			FilePersistenceUtils.deleteFolder(dataFolder);
		}
	}

	@Test
	public void testAddCommand() throws Exception
	{
		ICommandDAO commandDAO = new FileTransactionalCommandDAO(new FileCommandDAO());
		
		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		int gameID = 4;
		
		FileTransactionManager.startTransaction();
		commandDAO.AddCommand(gameID, cmd1);
		FileTransactionManager.endTransaction(true);
		
		String commandPath = FilenameUtils.getFullCommandsDir(gameID) + File.separator +
				FilenameUtils.commandPrefix + 0 + FilenameUtils.fileSuffix;
		File cmdFile = new File(commandPath);
		
		assertTrue(cmdFile.exists());
		String inBlob = FilePersistenceUtils.getBlob(commandPath);
		assertTrue(inBlob.equals(cmd1));
	}
	
	@Test
	public void testGetCommandCount() throws Exception
	{
		ICommandDAO commandDAO = new FileTransactionalCommandDAO(new FileCommandDAO());
		
		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		String cmd2 = "TWO IS BETTER THAN ONE";
		String cmd3 = "THREE IS A CROWD";
		int gameID = 4;
		
		FileTransactionManager.startTransaction();
		commandDAO.AddCommand(gameID, cmd1);
		commandDAO.AddCommand(gameID, cmd2);
		commandDAO.AddCommand(gameID, cmd3);
		FileTransactionManager.endTransaction(true);
		
		int numCommands = commandDAO.GetCommandCount(gameID);
		
		assertTrue(numCommands == 3);
	}
	
	@Test
	public void testGetCommands() throws Exception
	{
		ICommandDAO commandDAO = new FileTransactionalCommandDAO(new FileCommandDAO());
		
		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		String cmd2 = "TWO IS BETTER THAN ONE";
		String cmd3 = "THREE IS A CROWD";
		String cmd4 = "WHY AM I STILL CODING AT 12:11 AM?";
		int gameID = 4;
		int gameID2 = 8;
		
		FileTransactionManager.startTransaction();
		commandDAO.AddCommand(gameID, cmd1);
		commandDAO.AddCommand(gameID, cmd2);
		commandDAO.AddCommand(gameID, cmd3);
		commandDAO.AddCommand(gameID2, cmd4);
		FileTransactionManager.endTransaction(true);
		
		List<String> commands = commandDAO.GetCommands();
		assertTrue(commands.size() == 4);
		
		boolean foundFirstSet = false;
		boolean foundSecondSet = false;
		
		for(int i = 0; i < commands.size(); i++)
		{
			if(commands.get(i).equals(cmd1) && commands.get(i+1).equals(cmd2) && commands.get(i+2).equals(cmd3))
				foundFirstSet = true;
			if(commands.get(i).equals(cmd4))
				foundSecondSet = true;
		}
		
		assertTrue(foundFirstSet);
		assertTrue(foundSecondSet);
	}
	
	@Test
	public void testDeleteCommands() throws Exception
	{
		ICommandDAO commandDAO = new FileTransactionalCommandDAO(new FileCommandDAO());
		
		String cmd1 = "I'M A COMMAND BUT I'M SO LONELY";
		String cmd2 = "TWO IS BETTER THAN ONE";
		String cmd3 = "THREE IS A CROWD";
		String cmd4 = "WHY AM I STILL CODING AT 12:11 AM?";
		int gameID = 4;
		int gameID2 = 8;
		
		FileTransactionManager.startTransaction();
		commandDAO.AddCommand(gameID, cmd1);
		commandDAO.AddCommand(gameID, cmd2);
		commandDAO.AddCommand(gameID, cmd3);
		commandDAO.AddCommand(gameID2, cmd4);
		FileTransactionManager.endTransaction(true);
		
		String deletedCommandsString = FilenameUtils.getFullCommandsDir(gameID);
		File deletedCommandsDir = new File(deletedCommandsString);
		assertTrue(deletedCommandsDir.exists());
		
		FileTransactionManager.startTransaction();
		commandDAO.DeleteCommands(gameID);
		FileTransactionManager.endTransaction(true);
		
		assertTrue(commandDAO.GetCommandCount(gameID) == 0);
		assertTrue(commandDAO.GetCommandCount(gameID2) == 1);
		
		
		assertTrue(!deletedCommandsDir.exists());
	}

}
