package server.persistence.plugins.SQLPlugin;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;
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
public class SQLUserDAO implements IUserDAO
{
	public Connection connection;
	
    /**
     *  Setup mysql db connection
     */
    public SQLUserDAO(Connection c)
    {
    	connection = c;
    }

    /**
     * Adds a user to the USERS table
     *
     * @param userID
     * @param username
     * @param password
     * @throws PersistenceException 
     */
    @Override
    public void AddUser(int userID, String username, String password) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
			String sql = "INSERT INTO USERS (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)";
			pStmt = connection.prepareStatement(sql);
			
			pStmt.setInt(1, userID);
			pStmt.setString(2, username);
			pStmt.setString(3, password);
			
			if (pStmt.executeUpdate() == 1)
			{
				pStmt.close();
			}
			else
			{
				pStmt.close();
				throw new PersistenceException("AddUser update failed");
			}
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("AddUser SQLException", e);
		}
    }

    /**
     * Gets all the players in the USERS table
     *
     * @return List of ServerPlayer
     * @throws PersistenceException
     */
    @Override
    public List<ServerPlayer> GetAllUsers() throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
    		List<ServerPlayer> players = new ArrayList<ServerPlayer>();
    		
    		String sql = "SELECT * from USERS";
			pStmt = connection.prepareStatement(sql);
			
			ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
               int userID = rs.getInt("ID");
               String  userName = rs.getString("USERNAME");
               String  password = rs.getString("PASSWORD");
               ServerPlayer user = new ServerPlayer(userName, password, userID);
               players.add(user);
            }
            rs.close();
            pStmt.close();
            return players;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	throw new PersistenceException("GetAllUsers SQLException", e);
        }
    }

    String mysqlDb;
}
