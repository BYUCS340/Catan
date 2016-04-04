package server.persistence.plugins.SQLPluginTmp;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;

import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLUserDAO implements IUserDAO {
    /**
     *  Set mysql db connection
     *
     * @param mysqlDb
     */
    public SQLUserDAO(String mysqlDb){
        this.mysqlDb = mysqlDb;
    }

    /**
     * Adds a user to the DAO
     *
     * @param id
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean AddUser(String id, String username, String password) {
        return false;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public ServerPlayer GetUser(String username) {
        return null;
    }

    /**
     * @param playerID
     * @return
     */
    @Override
    public ServerPlayer GetUser(int playerID) {
        return null;
    }

    /**
     * Gets all the players in the DAO
     *
     * @return
     */
    @Override
    public List<ServerPlayer> GetAllUsers() {
        return null;
    }

    /**
     * Deletes all users on server
     *
     * @return
     */
    @Override
    public boolean DeleteAllUsers() {
        return false;
    }

    String mysqlDb;
}
