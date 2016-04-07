package testing.server.persistence.plugins.FilePlugin;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.persistence.plugins.FilePlugin.FilePersistenceUtils;

public class FilePersistenceUtilsTest
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
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		
		System.out.print("Testing creating a folder...");
		String newFolderPath = "test";
		File newDir = new File(newFolderPath);
		assertTrue(FilePersistenceUtils.makeDirs(newDir));
		assertTrue(newDir.exists());
		System.out.println("Success!");
		
		System.out.print("Testing creating a file...");
		String path = "test" + File.separator + "testFile.txt";
		File newFile = new File(path);
		String blob = "testing!\nhello";
		FilePersistenceUtils.writeFile(newFile, blob);
		assertTrue(newFile.exists());
		System.out.println("Success!");
		
		System.out.print("Testing reading blob from file...");
		String readBlob = FilePersistenceUtils.getBlob(path);
		assertTrue(readBlob.equals(blob));
		System.out.println("Success!");
		
		System.out.print("Testing folder deletion...");
		FilePersistenceUtils.deleteFolder(newDir);
		assertTrue(!newFile.exists());
		assertTrue(!newDir.exists());
		System.out.println("Success!");
		
	}

}
