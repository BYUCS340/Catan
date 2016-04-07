package server.persistence.plugins.SQLPlugin;

import server.persistence.IGameDAO;
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
     * Adds a Game to the GAMES table
     * 
     * @param gameID
     * @param blob
     * @throws PersistenceException
     */
    @Override
    public void AddGame(int gameID, String blob) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
			
			String sql = "INSERT INTO GAMES (ID, BLOB) VALUES (?, ?)";
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
				throw new PersistenceException("AddGame update failed");
			}
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("AddGame SQLException", e);
		}
    }

    /**
     * Updates a Game in the GAMES table
     * 
     * @param gameID
     * @param blob
     * @throws PersistenceException
     */
    @Override
    public void UpdateGame(int gameID, String blob) throws PersistenceException
    {
    	try
    	{
    		PreparedStatement pStmt = null;
    		
    	    String sql = "UPDATE GAMES set BLOB=? where ID=?";
    	    pStmt = connection.prepareStatement(sql);
			
    	    pStmt.setString(1, blob);
			pStmt.setInt(2, gameID);
			
    	    if (pStmt.executeUpdate() == 1)
			{
				pStmt.close();
			}
			else
			{
				pStmt.close();
				throw new PersistenceException("UpdateGame update failed");
			}
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			throw new PersistenceException("UpdateGame SQLException", e);
		}
    }

    /**
     * Get a List of all Games in GAMES table
     * 
     * @return List of Game as String
     * @throws PersistenceException
     */
    @Override
    public List<String> GetAllGames() throws PersistenceException
    {
    	try
    	{
    		List<String> games = new ArrayList<String>();
    		PreparedStatement pStmt = null;
    		
    		String sql = "SELECT * from GAMES";
    		pStmt = connection.prepareStatement(sql);
    		
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
               String  gameBlob = rs.getString("BLOB");
               games.add(gameBlob);
            }
            rs.close();
            pStmt.close();
            return games;
    	}
        catch (SQLException e)
        {
        	e.printStackTrace();
        	throw new PersistenceException("GetAllGames SQLException", e);
        }
    }

    String mysqlDb;
}
