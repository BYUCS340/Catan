package testing.server.persistence.plugins.FilePlugin;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;
import server.persistence.plugins.FilePlugin.FilePersistenceUtils;
import server.persistence.plugins.FilePlugin.FileUserDAO;
import server.persistence.plugins.FilePlugin.FilenameUtils;

public class FileUserDAOTest
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
	public void testAddUser() throws Exception
	{
		IUserDAO userDAO = new FileUserDAO();
		userDAO.AddUser(1, "johnny", "pills123");
		File userFile = new File(FilenameUtils.getFullUserPath(1));
		assertTrue(userFile.exists());
		String blob = FilePersistenceUtils.getBlob(userFile.getPath());
		assertTrue(blob.equals("johnny,pills123"));
	} 
	
	@Test
	public void testGetUsers() throws Exception
	{
		IUserDAO userDAO = new FileUserDAO();
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
	}
	
	

}
