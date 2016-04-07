package server.persistence.plugins.SQLPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLPlugin implements IPersistenceProvider
{
	private Connection connection;
	
    /**
     * Initialize sqlite db in plugins/sqlPlugin
     */
    public SQLPlugin()
    {
        //setup and store connection
        connection = null;
        try
        {
          Class.forName("org.sqlite.JDBC");
          connection = DriverManager.getConnection("jdbc:sqlite:savedata"+File.separator+"sqlite"+File.separator+"db.db");
          connection.setAutoCommit(false);
        }
        catch ( Exception e )
        {
          System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

	@Override
	public void Clear() 
	{

	}

	@Override
	public void StartTransaction()
	{
		//do nothing...?
		
	}

	@Override
	public void EndTransaction(boolean commit) 
	{
		try
		{
			if (commit)
			{
				
				connection.commit();
				System.out.println("Committed");
				
			}
			else
			{
				connection.rollback();
			}
			connection.close();
			System.out.println("Closed");
		}
		catch (SQLException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public IUserDAO GetUserDAO() 
	{
		return new SQLUserDAO(connection);
	}

	@Override
	public IGameDAO GetGameDAO() 
	{
		return new SQLGameDAO(connection);
	}

	@Override
	public ICommandDAO GetCommandDAO() 
	{
		return new SQLCommandDAO(connection);
	}
}
