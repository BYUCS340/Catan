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
    public List<String> GetCommands()
    {
    	try
    	{
    		List<String> commands = new ArrayList<String>();
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from COMMANDS");
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
        	e.printStackTrace();
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
    public void AddCommand(int gameID, String blob)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO COMMANDS (ID, BLOB) " +
		            "VALUES (" + gameID + ", '" + blob + "');";
		    stmt.executeUpdate(sql);

		    stmt.close();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    }

    /**
     * @param gameID
     * @return
     */
    @Override
    public void DeleteCommands(int gameID)
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from COMMANDS where ID=" + gameID + ";";
    	    stmt.executeUpdate(sql);
    	    
    	    stmt.close();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
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
    	return 0;
    }

    String mysqlDb;
}
