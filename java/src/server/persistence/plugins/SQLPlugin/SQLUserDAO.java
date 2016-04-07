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
	public Connection connection;
    
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
    public void AddUser(int userID, String username, String password)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO USERS (ID, USERNAME, PASSWORD) " +
		            "VALUES (" + userID + ", '"+ username + "', '"+ password + "');";

		    stmt.executeUpdate(sql);

		    stmt.close();
		    //if we commit here it works but that defeats the purpose of the DAO plugin facade
		    //connection.commit();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
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
            ResultSet rs = stmt.executeQuery("SELECT * from USERS;");
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
        	e.printStackTrace();
        	return null;
        }
    }
    
    String mysqlDb;
}
