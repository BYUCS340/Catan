package testing.server.persistence.plugins.FilePlugin;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FilePlugin;
import server.persistence.plugins.FilePlugin.FilenameUtils;

public class FilePluginTest
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
	public void testClearDatabase() throws Exception
	{
		File dataFile = new File(FilenameUtils.dataDir);
		FilePersistenceUtils.makeDirs(new File(FilenameUtils.dataDir));
		File testFile = new File(FilenameUtils.dataDir + File.separator + "test.txt");
		FilePersistenceUtils.writeFile(testFile, "THIS IS A TEST");
		IPersistenceProvider fplugin = new FilePlugin();
		fplugin.Clear();
		
		assertTrue(dataFile.exists());
		assertTrue(!testFile.exists());
		assertTrue(dataFile.listFiles().length == 0);	
	}
	
	@Test
	public void testGetDAOs() throws Exception
	{
		IPersistenceProvider fplugin = new FilePlugin();
		ICommandDAO commandDAO = fplugin.GetCommandDAO();
		IGameDAO gameDAO = fplugin.GetGameDAO();
		IUserDAO userDAO = fplugin.GetUserDAO();
	}

}
