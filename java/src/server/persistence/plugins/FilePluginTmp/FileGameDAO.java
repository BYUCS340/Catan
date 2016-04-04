package server.persistence.plugins.FilePluginTmp;

import server.persistence.IGameDAO;

import java.util.Map;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class FileGameDAO implements IGameDAO {
    /**
     * Save path to persistant file system
     * @param pathToFileSystem
     */
    public FileGameDAO(String pathToFileSystem){
        this.pathToFileSystem = pathToFileSystem;
    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean AddGame(int gameID, String blob) {
        return false;
    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean UpdateGame(int gameID, String blob) {
        return false;
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public boolean DeleteGame(int gameID) {
        return false;
    }

    /**
     * Deletes all games
     *
     * @return
     */
    @Override
    public boolean DeleteAllGames() {
        return false;
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

}