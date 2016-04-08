/**
 * 
 */
package testing.server.persistence.plugins.FilePlugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.persistence.IGameDAO;
import server.persistence.plugins.FilePlugin.FileGameDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FilenameUtils;

/**
 * @author Parker Ridd
 *
 */
public class FileGameDAOTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		File dataFolder = new File(FilenameUtils.dataDir);
		if(dataFolder.exists())
		{
			FilePersistenceUtils.deleteFolder(dataFolder);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
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
	public void testAddGame() throws Exception
	{
		IGameDAO gameDAO = new FileGameDAO();
		String gameStr = "HI I'M A FAKE GAME ACTING LIKE I'M A REAL GAME";
		int gameID = 476;
		gameDAO.AddGame(gameID, gameStr);
		
		File gameDir = new File(FilenameUtils.getFullGameDir(gameID));
		assertTrue(gameDir.exists());
		
		File gameFile = new File(FilenameUtils.getFullGameDir(gameID) + File.separator + FilenameUtils.gameFilename);
		assertTrue(gameFile.exists());
		
		String blob = FilePersistenceUtils.getBlob(gameFile.getPath());
		assertTrue(blob.equals(gameStr));
	}
	
	@Test
	public void testUpdateGame() throws Exception
	{
		IGameDAO gameDAO = new FileGameDAO();
		String gameStr = "HI I'M A FAKE GAME ACTING LIKE I'M A REAL GAME";
		int gameID = 476;
		gameDAO.AddGame(gameID, gameStr);
		
		String updatedString = "THE FAKE GAME HAS BEEN UPDATED NOW!!! :D";
		gameDAO.UpdateGame(476, updatedString);
		
		File gameDir = new File(FilenameUtils.getFullGameDir(gameID));
		assertTrue(gameDir.exists());
		
		File gameFile = new File(FilenameUtils.getFullGameDir(gameID) + File.separator + FilenameUtils.gameFilename);
		assertTrue(gameFile.exists());
		
		String blob = FilePersistenceUtils.getBlob(gameFile.getPath());
		assertTrue(blob.equals(updatedString));
	}
	
	@Test
	public void testGetAllGames() throws Exception
	{
		IGameDAO gameDAO = new FileGameDAO();
		String gameStr1 = "HI";
		int gameID1 = 476;
		gameDAO.AddGame(gameID1, gameStr1);
		
		String gameStr2 = "I'M";
		int gameID2 = 892;
		gameDAO.AddGame(gameID2, gameStr2);
		
		String gameStr3 = "HAPPY";
		int gameID3 = 1;
		gameDAO.AddGame(gameID3, gameStr3);
		
		List<String> gameList = gameDAO.GetAllGames();
		
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
	}
	


}
