package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import server.persistence.ICommandDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemente by Parker Ridd on 4/5/2016
 */
public class FileCommandDAO implements ICommandDAO {
    /**
     * Initialize path to persistant local file system
     */
    public FileCommandDAO(){

    }

    /**
     * gets command blobs for a game ID
     *
     * @param gameID
     * @return
     */
    @Override
    public List<String> GetCommandsFor(int gameID) {
    	List<String> retList = new ArrayList<String>();
    	
    	String commandsDir = FilenameUtils.getFullCommandsDir(gameID);
    	File dir = new File(commandsDir);
    	if(!dir.exists()) return retList;
    	
    	//TODO complete
    	return null;
    }

    /**
     * Adds a command to the DAO
     *
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean AddCommand(int gameID, String blob) {
        return false;
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public boolean DeleteCommandFor(int gameID) {
    	String commandsDir = FilenameUtils.getFullCommandsDir(gameID);
    	File dir = new File(commandsDir);
    	FilePersistenceUtils.deleteFolder(dir);
    	return true;
    	
    }

    /**
     * Deletes all commands for all games
     *
     * @return
     */
    @Override
    public boolean DeleteAllCommands() {
        return false;
    }

    /**
     * Gets the total number of commands for
     *
     * @param gameID
     * @return
     */
    @Override
    public int GetCommandCountFor(int gameID) {
    	String commandsDir = FilenameUtils.getFullCommandsDir(gameID);
    	File dir = new File(commandsDir);
    	if(!dir.exists()) return 0;
    	int numCommands = dir.listFiles().length;
        return numCommands;
    }

    private String pathToFileSystem = "";
}
