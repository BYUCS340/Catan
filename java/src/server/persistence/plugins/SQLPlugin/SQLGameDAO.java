package server.persistence.plugins.SQLPlugin;

import server.persistence.IGameDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
    public boolean AddGame(int gameID, String blob)
    {
    	try
    	{
			Statement stmt = connection.createStatement();
			
			String sql = "INSERT INTO GAMES (ID, BLOB) " +
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
     * @param blob
     * @return
     */
    @Override
    public boolean UpdateGame(int gameID, String blob)
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "UPDATE GAMES set BLOB = " + blob + " where ID=" + gameID + ";";
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
    public boolean DeleteGame(int gameID)
    {
    	try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from GAMES where ID=" + gameID + ";";
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
     * Deletes all games
     *
     * @return
     */
    @Override
    public boolean DeleteAllGames()
    {
        try
    	{
    		Statement stmt = connection.createStatement();
    	    String sql = "DELETE from GAMES;";
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
    public String GetCheckpoint(int gameID)
    {
    	try
    	{
    		String gameBlob = null;
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from GAMES where ID=" + gameID + ";");
            while (rs.next())
            {
               gameBlob = rs.getString("BLOB");
            }
            rs.close();
            stmt.close();
            return gameBlob;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	return null;
        }
    }

    /**
     * @return a map of Game ID to blobs
     */
    @Override
    public Map<Integer, String> GetAllGames()
    {
    	try
    	{
    		Map<Integer, String> games = new HashMap<Integer, String>();
    		
    		Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from GAMES;");
            while (rs.next())
            {
               int gameID = rs.getInt("ID");
               String  gameBlob = rs.getString("USERNAME");
               games.put(gameID, gameBlob);
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
