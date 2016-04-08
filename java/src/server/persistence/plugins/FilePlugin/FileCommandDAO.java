package server.persistence.plugins.FilePlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

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
    public List<String> GetCommands() throws PersistenceException
    {
    	List<String> retList = new ArrayList<String>();
    	
    	File dataDir = new File(FilenameUtils.dataDir);
    	for(File gdfile : dataDir.listFiles())
    	{
    		if(gdfile.getName().contains(FilenameUtils.gameDir))
			{
    			int gameID = FilenameUtils.getGameIDFromDirString(gdfile.getName());
    			String commandPath = FilenameUtils.getFullCommandsDir(gameID);
    			File dir = new File(commandPath);
    			if(!dir.exists()) continue;
    	    	
    			int commandCount = this.GetCommandCount(gameID);
    			
    			//iterate through all of the commands that should exist in this folder
    			for(int i = 0; i < commandCount; i++)
				{
    				//Get file name of the specified command
    				String fileName = commandPath + File.separator + FilenameUtils.commandPrefix + i + FilenameUtils.fileSuffix;
    				File tempFile = new File(fileName);
    				if(!tempFile.exists())
    				{
    					throw new PersistenceException("Path " + fileName + " should have existed but didn't");
    				}
    				
    	    		retList.add(FilePersistenceUtils.getBlob(fileName));
				}	
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
    public void AddCommand(int gameID, String blob) 
    {
    	String commandsDir = FilenameUtils.getFullCommandsDir(gameID);
    	File theDir = new File(commandsDir);
    	FilePersistenceUtils.makeDirs(theDir);
    	
    	int num = this.GetCommandCount(gameID);
    	
    	//Format: data/game1/commands/Command21.catan
    	File theFile = new File(commandsDir + File.separator + FilenameUtils.commandPrefix + num + FilenameUtils.fileSuffix);
    	FilePersistenceUtils.writeFile(theFile, blob);
    }

    /**
     * @param gameID
     * @return false if the commands have already been cleared
     */
    @Override
    public void DeleteCommands(int gameID) 
    {
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
    public int GetCommandCount(int gameID) 
    {
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
