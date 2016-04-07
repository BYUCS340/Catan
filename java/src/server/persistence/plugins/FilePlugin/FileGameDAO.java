package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public void AddGame(int gameID, String blob) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	File theDir = new File(gameDir);
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	File theFile = new File(gameDir + File.separator + FilenameUtils.gameFilename);
    	FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public void UpdateGame(int gameID, String blob) {
    	String gameDir = FilenameUtils.getFullGameDir(gameID);
    	
    	File theFile = new File(gameDir + File.separator + FilenameUtils.gameFilename);
    	FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * @return a map of Game ID to blobs
     */
    @Override
    public List<String> GetAllGames() {
    	List<String> gameMap = new ArrayList<String>();
        File rootDir = new File(FilenameUtils.dataDir);
        for(File f : rootDir.listFiles())
        {
        	if(f.isDirectory() && f.getName().contains(FilenameUtils.gameDir))
        	{
        		String gameBlobFile = f.getPath() + File.separator + FilenameUtils.gameFilename;
        		String blob = FilePersistenceUtils.getBlob(gameBlobFile);
        		gameMap.add(blob);
        	}
        }
        return gameMap;
    }

    private String pathToFileSystem = "";

    
}
