package server.commands.game;

import server.commands.CookieCommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command object that gets game models from the server.
 * @author Jonathan Sadler
 *
 */
public class GameModelCommand extends CookieCommand
{
	private static final long serialVersionUID = 7071448787665345669L;
	
	private int version;
	private ServerGameManager sgm;
	
	/**
	 * Gets a game model from the server.
	 * @param version The version number as had by the client.
	 */
	public GameModelCommand(NetworkCookie cookie, int version) 
	{
		super(cookie);
		this.version = version;
	}

	@Override
	public boolean Execute() 
	{
		try 
		{
			sgm = GameArcade.games().GetGame(gameID);
			return true;
		}
		catch (GameException e) 
		{ //game not found
			e.printStackTrace();
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
		if (sgm != null) 
		{
			if(sgm.GetVersion() != version)
				return SerializationUtils.serialize(sgm.ServerGetSerializableModel());
			else
				//Logger.getLogger("CatanServer").log(Level.INFO, "No new model detected. Current version: " + sgm.GetVersion() + " client version: " + version);
				return "No new model";
		}
	
		return "No Model";
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}
