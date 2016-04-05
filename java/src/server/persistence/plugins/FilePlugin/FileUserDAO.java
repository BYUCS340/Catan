package server.persistence.plugins.FilePlugin;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;

import java.io.File;
import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class FileUserDAO implements IUserDAO {
    /**
     * initialize path to persistant local file system
     */
    public FileUserDAO(){

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
    	String userDir = FilenameUtils.userDirFull;
    	File theDir = new File(userDir);
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	
    	File theFile = new File(userDir + File.separator + id);
    	String blob = username + "," + password;
    	return FilePersistenceUtils.writeFile(theFile, blob);
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

    private String pathToFileSystem = "";
}
