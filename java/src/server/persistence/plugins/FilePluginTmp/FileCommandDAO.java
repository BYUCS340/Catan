package server.persistence.plugins.FilePluginTmp;

import server.persistence.ICommandDAO;

import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
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
        return 0;
    }

    private String pathToFileSystem = "";
}
