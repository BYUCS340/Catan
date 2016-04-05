package server.persistence.plugins.SQLPlugin;

import server.persistence.ICommandDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     * gets command blobs for a game ID
     *
     * @param gameID
     * @return
     */
    @Override
    public List<String> GetCommandsFor(int gameID)
    {
    	try
    	{
    		List<String> commands = new ArrayList<String>();
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COMMANDS where ID=" + gameID + ";");
            while (rs.next())
            {
               String  commandBlob = rs.getString("BLOB");
               commands.add(commandBlob);
            }
            rs.close();
            stmt.close();
            return commands;
    	}
        catch (SQLException e)
        {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return null;
        }
    }

    /**
     * Adds a command to the DAO
     *
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public boolean AddCommand(int gameID, String blob)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO COMMANDS (ID, BLOB) " +
		            "VALUES (gameID, blob);";
		    stmt.executeUpdate(sql);

		    stmt.close();
		    return true;
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			return false;
		}
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public boolean DeleteCommandFor(int gameID)
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from COMMANDS where ID=" + gameID + ";";
    	    stmt.executeUpdate(sql);
    	    
    	    stmt.close();
		    return true;
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			return false;
		}
    }

    /**
     * Deletes all commands for all games
     *
     * @return
     */
    @Override
    public boolean DeleteAllCommands()
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from COMMANDS;";
    	    stmt.executeUpdate(sql);
    	    
    	    stmt.close();
		    return true;
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			return false;
		}
    }

    /**
     * Gets the total number of commands for
     *
     * @param gameID
     * @return
     */
    @Override
    public int GetCommandCountFor(int gameID)
    {
    	return GetCommandsFor(gameID).size();
    }

    String mysqlDb;
}
