package testing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the testing folder and finds all the test files.
 * @author Jonathan Sadler
 *
 */
public class UnitTests {
	
	private static final String path = "java" + File.separator + "build" + 
			File.separator + "testing";  

	public static void main(String[] args)
	{
		org.junit.runner.JUnitCore.main(Parse());
	}
	
	private static String[] Parse()
	{
		List<String> paths = new ArrayList<String>();
		Parse(path, paths);
		
		String[] temp = (String[]) paths.toArray(new String[0]); 
		return temp;
	}
	
	private static void Parse(String path, List<String> collectedPaths)
	{
		File file = new File(path);
		
		if (file.getName().equals("UnitTests.class"))
			return;
		
		if (file.isDirectory())
		{
			for (String subFile : file.list())
				Parse(path + File.separator + subFile, collectedPaths);
		}
		else
		{
			path = path.replace(File.separatorChar, '.');
			path = path.substring(11, path.length() - 6);
			collectedPaths.add(path);
		}
	}
	
// This is old code. I left it in case we need to switch back to it.
// It requires adding test cases manually.
	
//	private static String[] ClientTests()
//	{
//		String[] tests = new String[]
//		{
//			"networking.RealServerProxyTest"
//		};
//		
//		AddPrefix("testing.client", tests);
//		
//		return tests;
//	}
//	
//	private static String[] SharedTests()
//	{
//		String[] tests = new String[]
//		{
//				"networking.JSONDeserializerTest"
//		};
//		
//		AddPrefix("testing.shared", tests);
//		
//		return tests;
//	}
//	
//	private static void AddPrefix(String prefix, String[] array)
//	{
//		for (int i = 0; i < array.length; i++)
//		{
//			array[i] = prefix + "." + array[i];
//		}
//	}
}
