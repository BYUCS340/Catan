package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import server.persistence.IGameDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemented by Parker Ridd on 4/4/2016
 */
public class FileGameDAO implements IGameDAO {
    /**
     * Initialize path to persistant local file system
     */
    public FileGameDAO(){

    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean AddGame(int gameID, String blob) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	File theDir = new File(gameDir);
    	makeDirs(theDir);
    	
    	
    	File theFile = new File(gameDir + File.separator + FilenameUtils.gameFilename);
    	return writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean UpdateGame(int gameID, String blob) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	
    	File theFile = new File(gameDir + File.separator + FilenameUtils.gameFilename);
    	return writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public boolean DeleteGame(int gameID) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	File theDir = new File(gameDir);
    	
    	deleteFolder(theDir);
    	
    	return true;
    }

    /**
     * Deletes all games
     *
     * @return
     */
    @Override
    public boolean DeleteAllGames() {
        String gamesRootDir = FilenameUtils.dataDir;
        File rootDir = new File(gamesRootDir);
        for(File f : rootDir.listFiles())
        {
        	if(f.isDirectory() && f.getName().contains("Game"))
        	{
        		deleteFolder(f);
        	}
        }
        
        return true;
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public String GetCheckpoint(int gameID) {
        return null;
    }

    /**
     * @return a map of Game ID to blobs
     */
    @Override
    public Map<Integer, String> GetAllGames() {
        return null;
    }

    private String pathToFileSystem = "";

    private static boolean makeDirs(File theDir)
    {
    	boolean created = true;
    	if(!theDir.exists()) created = theDir.mkdirs();
    	return created;
    }
    
    private static boolean writeFile(File theFile, String blob)
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
    
    private static void deleteFolder(File folder)
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
}
