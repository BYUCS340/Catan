package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilePersistenceUtils
{
	public static boolean makeDirs(File theDir)
    {
    	boolean created = true;
    	if(!theDir.exists()) created = theDir.mkdirs();
    	return created;
    }
    
    public static boolean writeFile(File theFile, String blob)
    {
    	try
    	{
    		FileWriter fw = new FileWriter(theFile, false);
    		fw.write(blob);
    		fw.close();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    	
    	return true;
    }
    
    public static void deleteFolder(File folder)
    {
    	File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
    
    public static String getBlob(String cp)
    {
    	//read all bytes and encode them into a string
    	Charset encoding = Charset.defaultCharset();
    	byte[] encoded = null;
		try
		{
			encoded = Files.readAllBytes(Paths.get(cp));
			return new String(encoded, encoding);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
    }
}
