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
    	
    	for(File f : dir.listFiles())
    	{
    		//check to see if this file is a command
    		if(f.getName().contains(FilenameUtils.commandPrefix))
    		{
    			retList.add(FilePersistenceUtils.getBlob(f.getPath()));
    		}
    	}
    	return retList;
    }

    /**
     * Adds a command to the DAO
     *
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public void AddCommand(int gameID, String blob) {
    	String commandsDir = FilenameUtils.getFullCommandsDir(gameID);
    	File theDir = new File(commandsDir);
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	int num = this.GetCommandCountFor(gameID) + 1;
    	
    	//Format: data/game1/commands/Command21.catan
    	File theFile = new File(commandsDir + File.separator + FilenameUtils.commandPrefix + num + FilenameUtils.fileSuffix);
    	FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @return false if the commands have already been cleared
     */
    @Override
    public void DeleteCommandFor(int gameID) {
    	String commandsDirPath = FilenameUtils.getFullCommandsDir(gameID);
    	File commandsDir = new File(commandsDirPath);
    	
    	//make sure the commands dir actually exists
    	if(!commandsDir.exists()) return;
    	File dir = new File(commandsDirPath);
    	FilePersistenceUtils.deleteFolder(dir);
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
    	int numCommands = 0;
    	for(File f : dir.listFiles())
    	{
    		if(f.getName().contains(FilenameUtils.commandPrefix))
    		{
    			numCommands++;
    		}
    	}
        return numCommands;
    }

    private String pathToFileSystem = "";
}
