package server.persistence.plugins.FilePlugin;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class FilePlugin implements IPersistenceProvider {
    /**
     * Initialize filesystem in plugins/filePlugin
     */
    public FilePlugin()
    {
        super();
    }

	@Override
	public void Clear() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartTransaction() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void EndTransaction(boolean commit) 
	{
		// TODO Auto-generated method stub
		
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