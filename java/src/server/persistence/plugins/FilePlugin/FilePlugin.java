package server.persistence.plugins.FilePlugin;

import java.io.File;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

/**
 * Created by Tunadude09 on 4/4/2016.
 * Implemented by Parker Ridd on 4/6/2016
 */
public class FilePlugin implements IPersistenceProvider {
	private IUserDAO userDAO = null;
	private IGameDAO gameDAO = null;
	private ICommandDAO commandDAO = null;
	
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
        FilePersistenceUtils.makeDirs(dataDir);
		System.out.println("FILE PLUGIN CLEARED");
		
	}

	@Override
	public void StartTransaction() throws PersistenceException
	{
		boolean successful = FileTransactionManager.startTransaction();
		if(!successful)
		{
			throw new PersistenceException("The transaction could not be started! Resuld false");
		}
	}

	@Override
	public void EndTransaction(boolean commit) throws PersistenceException
	{
		boolean successful = FileTransactionManager.endTransaction(commit);
		if(!successful)
		{
			throw new PersistenceException("The transaction couldn't be committed! Result false");
		}
	}

	@Override
	public IUserDAO GetUserDAO() 
	{
		if(userDAO == null)
		{
			userDAO = new FileTransactionalUserDAO(new FileUserDAO());
		}
		return userDAO;
	}

	@Override
	public IGameDAO GetGameDAO() 
	{
		if(gameDAO == null)
		{
			gameDAO = new FileTransactionalGameDAO(new FileGameDAO());
		}
		return gameDAO;
	}

	@Override
	public ICommandDAO GetCommandDAO() 
	{
		if(commandDAO == null)
		{
			commandDAO = new FileTransactionalCommandDAO(new FileCommandDAO());
		}
		return commandDAO;
	}
}
