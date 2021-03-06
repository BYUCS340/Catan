package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemented by Parker Ridd on 4/5/2016
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
    public void AddUser(int id, String username, String password) {
    	String userDir = FilenameUtils.userDirFull;
    	File theDir = new File(userDir);
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	
    	File theFile = new File(FilenameUtils.getFullUserPath(id));
    	String blob = username + "," + password;
    	FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * Gets all the players in the DAO
     *
     * @return
     */
    @Override
    public List<ServerPlayer> GetAllUsers() {
    	String userDirPath = FilenameUtils.userDirFull;
        File userDir = new File(userDirPath);
        
        List<ServerPlayer> retList = new ArrayList<ServerPlayer>()	;
        if(!userDir.exists()) return retList;
        
        for(File f: userDir.listFiles())
        {
        	if(Character.isDigit(f.getName().charAt(0)))
        	{
        		String blob = FilePersistenceUtils.getBlob(f.getPath());
            	List<String> splitList = Arrays.asList(blob.split(","));
        		retList.add(new ServerPlayer(splitList.get(0), splitList.get(1), 
        				FilenameUtils.getUserIDFromString(f.getPath())));
        	}
        }
        
        return retList;
    }

    private String pathToFileSystem = "";
}
