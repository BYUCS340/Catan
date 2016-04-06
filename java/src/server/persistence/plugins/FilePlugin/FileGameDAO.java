package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import server.persistence.IGameDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemented by Parker Ridd on 4/4/2016
 */
public class FileGameDAO implements IGameDAO {
    /**
     * Initialize path to persistent local file system
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
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	
    	File theFile = new File(gameDir + File.separator + FilenameUtils.gameFilename);
    	return FilePersistenceUtils.writeFile(theFile, blob);
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
    	return FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @return false if the game does not exist
     */
    @Override
    public boolean DeleteGame(int gameID) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	File theDir = new File(gameDir);
    	
    	if(!theDir.exists()) return false;
    	
    	FilePersistenceUtils.deleteFolder(theDir);
    	
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
        	if(f.isDirectory() && f.getName().contains(FilenameUtils.commandDir))
        	{
        		FilePersistenceUtils.deleteFolder(f);
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
    	String gameDirStr = FilenameUtils.getFullGameDir(gameID);
    	String cp = gameDirStr + File.separator + FilenameUtils.gameFilename;
    	
    	return FilePersistenceUtils.getBlob(cp);
    }

    /**
     * @return a map of Game ID to blobs
     */
    @Override
    public Map<Integer, String> GetAllGames() {
    	Map<Integer, String> gameMap = new TreeMap<Integer, String>();
        File rootDir = new File(FilenameUtils.dataDir);
        for(File f : rootDir.listFiles())
        {
        	if(f.isDirectory() && f.getName().contains(FilenameUtils.gameDir))
        	{
        		int gameID = FilenameUtils.getGameIDFromDirString(f.getName());
        		String gameBlobFile = f.getPath() + File.separator + FilenameUtils.gameFilename;
        		String blob = FilePersistenceUtils.getBlob(gameBlobFile);
        		gameMap.put(gameID, blob);
        	}
        }
        
        return gameMap;
    }

    private String pathToFileSystem = "";

    
}
