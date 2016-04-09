package server.persistence.plugins.SQLPlugin;

import server.Log;
import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLCommandDAO implements ICommandDAO
{
	Connection connection;
	
    /**
     *  Setup mysql db connection
     */
    public SQLCommandDAO(Connection c)
    {
    	connection = c;
    }

    /**
     * Get a List of all Commands in COMMANDS table
     *
     * @param gameID
     * @return List of Command as String
     * @throws PersistenceException
     */
    @Override
    public List<String> GetCommands() throws PersistenceException
    {
    	try
    	{
    		List<String> commands = new ArrayList<String>();
    		PreparedStatement pStmt = null;
    		
    		String sql = "SELECT * from COMMANDS";
			pStmt = connection.prepareStatement(sql);
    		
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
               String  commandBlob = rs.getString("BLOB");
               commands.add(commandBlob);
            }
            rs.close();
            pStmt.close();
            return commands;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	throw new PersistenceException("GetCommands SQLException", e);
        }
    }

    /**
     * Adds a command to the COMMANDS table
     *
     * @param gameID
     * @param blob
     * @throws PersistenceException
     */
    @Override
    public void AddCommand(int gameID, String blob) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
    		String sql = "INSERT INTO COMMANDS (ID, BLOB) VALUES (?, ?)";
			pStmt = connection.prepareStatement(sql);
			
			pStmt.setInt(1, gameID);
			pStmt.setString(2, blob);
			
			if (pStmt.executeUpdate() == 1)
			{
				pStmt.close();
			}
			else
			{
				pStmt.close();
				throw new PersistenceException("AddCommand update failed");
			}
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("AddCommand SQLException", e);
		}
    }

    /**
     * Deletes a Command from COMMANDS table
     * 
     * @param gameID
     * @throws PersistenceException
     */
    @Override
    public void DeleteCommands(int gameID) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
    		String sql = "DELETE from COMMANDS where ID=?";
			pStmt = connection.prepareStatement(sql);
			
			pStmt.setInt(1, gameID);
			
			//this returns the number of rows deleted
			pStmt.executeUpdate();
			pStmt.close();
			
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("DeleteCommands SQLException");
		}
    }

    /**
     * Gets the number of Commands in COMMANDS table for a specified Game
     *
     * @param gameID
     * @return Number of Commands
     * @throws PersistenceException
     */
    @Override
    public int GetCommandCount(int gameID) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
    		String sql = "SELECT COUNT(*) total from COMMANDS where ID=?";
			pStmt = connection.prepareStatement(sql);
			
			pStmt.setInt(1, gameID);
    		
            ResultSet rs = pStmt.executeQuery();
            int commandBlobCount = 0;
            while (rs.next())
            {
            	commandBlobCount += rs.getInt("total");
            }
            rs.close();
            pStmt.close();
            //Log.GetLog().finest("# of commands for game "+gameID+" is "+commandBlobCount);
            return commandBlobCount;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	throw new PersistenceException("GetCommandCount SQLException", e);
        }
    }

    String mysqlDb;
}
