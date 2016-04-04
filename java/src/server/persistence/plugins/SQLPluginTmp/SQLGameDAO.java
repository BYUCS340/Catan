package server.persistence.plugins.SQLPluginTmp;

import server.persistence.IGameDAO;

import java.util.Map;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLGameDAO implements IGameDAO {
    /**
     *  Set mysql db connection
     * @param mysqlDb
     */
    public SQLGameDAO(String mysqlDb){
        this.mysqlDb = mysqlDb;
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

    String mysqlDb;
}
