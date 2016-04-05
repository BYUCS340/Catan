package server.persistence.plugins.SQLPlugin;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLUserDAO implements IUserDAO
{
	Connection connection;
    /**
     *  Setup mysql db connection
     */
    public SQLUserDAO(Connection c)
    {
    	connection = c;
    }

    /**
     * Adds a user to the DAO
     *
     * @param id
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean AddUser(int userID, String username, String password)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO USERS (ID, USERNAME, PASSWORD) " +
		            "VALUES (userID, username, password);";
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
     * @param username
     * @return
     */
    @Override
    public ServerPlayer GetUser(String username)
    {
    	try
    	{
    		ServerPlayer user = null;
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME=" + username + ";");
            while (rs.next())
            {
               int userID = rs.getInt("ID");
               String  userName = rs.getString("USERNAME");
               String  password = rs.getString("PASSWORD");
               user = new ServerPlayer(userName, password, userID);
            }
            rs.close();
            stmt.close();
            return user;
    	}
        catch (SQLException e)
        {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return null;
        }
    }

    /**
     * @param playerID
     * @return
     */
    @Override
    public ServerPlayer GetUser(int playerID)
    {
    	try
    	{
    		ServerPlayer user = null;
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE ID=" + playerID + ";");
            while (rs.next())
            {
               int userID = rs.getInt("ID");
               String  userName = rs.getString("USERNAME");
               String  password = rs.getString("PASSWORD");
               user = new ServerPlayer(userName, password, userID);
            }
            rs.close();
            stmt.close();
            return user;
    	}
        catch (SQLException e)
        {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return null;
        }
    }

    /**
     * Gets all the players in the DAO
     *
     * @return
     */
    @Override
    public List<ServerPlayer> GetAllUsers()
    {
    	try
    	{
    		List<ServerPlayer> players = new ArrayList<ServerPlayer>();
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS;");
            while (rs.next())
            {
               int userID = rs.getInt("ID");
               String  userName = rs.getString("USERNAME");
               String  password = rs.getString("PASSWORD");
               ServerPlayer user = new ServerPlayer(userName, password, userID);
               players.add(user);
            }
            rs.close();
            stmt.close();
            return players;
    	}
        catch (SQLException e)
        {
        	System.err.println(e.getClass().getName() + ": " + e.getMessage());
        	return null;
        }
    }

    /**
     * Deletes all users on server
     *
     * @return
     */
    @Override
    public boolean DeleteAllUsers()
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from USERS;";
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

    String mysqlDb;
}
