package server.persistence.plugins.SQLPlugin;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLPlugin implements IPersistenceProvider
{
    /**
     * Initialize sqlite db in plugins/sqlPlugin
     */
    public SQLPlugin()
    {
        super();
    }

	@Override
	public void Clear() 
	{
		System.out.println("Cleared SQL");
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
