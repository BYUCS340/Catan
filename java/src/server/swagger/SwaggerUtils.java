package server.swagger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SwaggerUtils 
{
	private static final Map<String, String> MIMETYPES = MakeMimeTypes();
	
	public static String CleanPath(String path)
	{
		int value = path.length() - 1;
		while (path.charAt(value) > 127)
			value--;
		
		return path.substring(0, value + 1);
	}
	
	public static byte[] ReadFile(String path) throws IOException
	{	
		Path filepath = Paths.get(path);
		
		return Files.readAllBytes(filepath);
	}
	
	public static String GetMimeType(String path)
	{
		String ending = path.substring(path.lastIndexOf('.'), path.length());
		
		if (MIMETYPES.containsKey(ending))
			return MIMETYPES.get(ending);
		else
			return "";
	}
	
	private static Map<String, String> MakeMimeTypes()
	{
		Map<String, String> mimeTypes = new HashMap<String, String>();
		
		mimeTypes.put(".js", "application/javascript");
		mimeTypes.put(".css", "text/css");
		mimeTypes.put(".html", "text/html");
		
		return mimeTypes;
	}
}
