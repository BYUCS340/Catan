package server.persistence.plugins.FilePlugin;

import java.io.File;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemented by Parker Ridd on 4/6/2016
 */
public class FilePlugin implements IPersistenceProvider {
    /**
     * Initialize filesystem in plugins/filePlugin
     */
    public FilePlugin()
    {
        super();
        
        //create the data directory if it doesn't exist
        File dataDir = new File(FilenameUtils.dataDir);
        if(!dataDir.exists()) FilePersistenceUtils.makeDirs(dataDir);
    }

	@Override
	public void Clear() 
	{
		File dataDir = new File(FilenameUtils.dataDir);
        if(dataDir.exists()) FilePersistenceUtils.deleteFolder(dataDir);
		System.out.println("FILE PLUGIN CLEARED");
		
	}

	@Override
	public void StartTransaction() 
	{
		FileTransactionManager.startTransaction();
		
	}

	@Override
	public void EndTransaction(boolean commit) 
	{
		FileTransactionManager.endTransaction(commit);
		
	}

	@Override
	public IUserDAO GetUserDAO() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGameDAO GetGameDAO() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommandDAO GetCommandDAO() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
