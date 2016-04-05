package server.persistence.plugins.FilePlugin;

import java.io.File;
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
        return false;
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
    	int numCommands = dir.listFiles().length;
        return numCommands;
    }

    private String pathToFileSystem = "";
}
