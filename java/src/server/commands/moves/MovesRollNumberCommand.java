package server.commands.moves;


import java.util.logging.Level;

import server.Log;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.model.GameModel;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that deals with the rolling of dice.
 * @author Jonathan Sadler
 *
 */
public class MovesRollNumberCommand extends MovesCommand 
{
	private int roll;
	private GameModel model;
	/**
	 * Creates a command object to handle dice rolls.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param roll The value on the dice.
	 */
	public MovesRollNumberCommand(NetworkCookie cookie, int playerIndex, int roll) 
	{
		super(cookie, playerIndex);
		this.roll = roll;
		
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			ServerGameManager sgm = GameArcade.games().GetGame(gameID);
			if (sgm.ServerRollNumber(playerIndex, roll))
			{
				model = sgm.ServerGetModel();
				return true;
			}
		}
		catch (GameException e) 
		{ //game not found
			e.printStackTrace();
			Log.GetLog().log(Level.SEVERE, "Exception while rolling " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetResponse() 
	{
		if (model != null)
			return SerializationUtils.serialize(model);
		else 
			return "Unable to Roll";
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}
