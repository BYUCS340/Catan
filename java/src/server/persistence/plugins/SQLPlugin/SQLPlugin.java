package server.persistence.plugins.SQLPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

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

    /**
     * Clears the database, dropping and recreating all tables
     * @throws PersistenceException
     */
	@Override
	public void Clear() throws PersistenceException
	{
		try
    	{
			//Drop Tables
    		PreparedStatement pStmtDropUsers = null;
    		PreparedStatement pStmtDropGames = null;
    		PreparedStatement pStmtDropCommands = null;
    		
    		String sqlDropUsers = "DROP TABLE IF EXISTS USERS";
    		String sqlDropGames = "DROP TABLE IF EXISTS GAMES";
    		String sqlDropCommands = "DROP TABLE IF EXISTS COMMANDS";
    		
    		pStmtDropUsers = connection.prepareStatement(sqlDropUsers);
    		pStmtDropGames = connection.prepareStatement(sqlDropGames);
    		pStmtDropCommands = connection.prepareStatement(sqlDropCommands);
			
			pStmtDropUsers.execute();
			pStmtDropGames.execute();
			pStmtDropCommands.execute();
			
			pStmtDropUsers.close();
			pStmtDropGames.close();
			pStmtDropCommands.close();
			
			//Create Tables
    		PreparedStatement pStmtCreateUsers = null;
    		PreparedStatement pStmtCreateGames = null;
    		PreparedStatement pStmtCreateCommands = null;
    		
    		String sqlCreateUsers = "CREATE TABLE USERS (ID INTEGER not NULL, " +
    					"USERNAME STRING not NULL, PASSWORD STRING not NULL, PRIMARY KEY (ID))";
    		String sqlCreateGames = "CREATE TABLE GAMES (ID INTEGER not NULL, " + 
    					"BLOB STRING not NULL, PRIMARY KEY (ID))";
    		String sqlCreateCommands = "CREATE TABLE COMMANDS (ID INTEGER not NULL, " + 
    					"BLOB STRING not NULL, PRIMARY KEY (ID))";
    		
    		pStmtCreateUsers = connection.prepareStatement(sqlCreateUsers);
    		pStmtCreateGames = connection.prepareStatement(sqlCreateGames);
    		pStmtCreateCommands = connection.prepareStatement(sqlCreateCommands);
			
			pStmtCreateUsers.execute();
			pStmtCreateGames.execute();
			pStmtCreateCommands.execute();
			
			pStmtCreateUsers.close();
			pStmtCreateGames.close();
			pStmtCreateCommands.close();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("Clear SQLException");
		}
	}

	/**
	 * Starts a transaction (but actually does nothing because SQLite handles that)
	 * @throws PersistenceException
	 */
	@Override
	public void StartTransaction() throws PersistenceException
	{
		//do nothing...?
	}

	/**
	 * Ends a transaction, committing if true, rolling-back if false
	 * @param commit
	 * @throws PersistenceException
	 */
	@Override
	public void EndTransaction(boolean commit) throws PersistenceException
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
				System.out.println("Rolled-Back");
			}
			connection.close();
			System.out.println("Closed");
		}
		catch (SQLException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Get a SQLUserDAO
	 * @return SQLUserDAO
	 * @throws PersistenceException
	 */
	@Override
	public IUserDAO GetUserDAO() throws PersistenceException
	{
		return new SQLUserDAO(connection);
	}

	/**
	 * Get a SQLGameDAO
	 * @return SQLGameDAO
	 * @throws PersistenceException
	 */
	@Override
	public IGameDAO GetGameDAO() throws PersistenceException
	{
		return new SQLGameDAO(connection);
	}

	/**
	 * Get a SQLCommandDAO
	 * @return SQLCommandDAO
	 * @throws PersistenceException
	 */
	@Override
	public ICommandDAO GetCommandDAO() throws PersistenceException
	{
		return new SQLCommandDAO(connection);
	}
}
