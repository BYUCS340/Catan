package server.persistence.plugins.SQLPlugin;

import server.persistence.IGameDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tunadude09 on 4/4/2016.
 */
public class SQLGameDAO implements IGameDAO
{
	Connection connection;
	
    /**
     *  Setup mysql db connection
     */
    public SQLGameDAO(Connection c)
    {
    	connection = c;
    }

    /**
     * @param gameID
     * @param blob
     * @return
     */
    @Override
    public void AddGame(int gameID, String blob)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO GAMES (ID, BLOB) " +
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
     * @param blob
     * @return
     */
    @Override
    public void UpdateGame(int gameID, String blob)
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "UPDATE GAMES set BLOB = " + blob + " where ID=" + gameID + ";";
    	    stmt.executeUpdate(sql);
    	    
    	    stmt.close();
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    }

    /**
     * @return a map of Game ID to blobs
     */
    @Override
    public List<String> GetAllGames()
    {
    	try
    	{
    		List<String> games = new ArrayList<String>();
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from GAMES;");
            while (rs.next())
            {
               String  gameBlob = rs.getString("USERNAME");
               games.add(gameBlob);
            }
            rs.close();
            stmt.close();
            return games;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    String mysqlDb;
}
