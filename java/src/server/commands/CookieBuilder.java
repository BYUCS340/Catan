package server.commands;

import server.model.GameArcade;
import server.model.GameException;
import shared.networking.cookie.NetworkCookie;

/**
 * Builder class that is used for commands that require cookie data.
 * @author Jonathan Sadler
 *
 */
public abstract class CookieBuilder implements ICommandBuilder 
{
	protected NetworkCookie cookie;
	protected int playerIndex;
	
	/**
	 * Data to be set from the cookie.
	 * @param playerID The player ID.
	 * @throws GameException Thrown when there is an issue getting the player index
	 */
	public final void SetCookie(NetworkCookie cookie) throws GameException
	{
		this.cookie = cookie;
		
		if (cookie.getGameID() != -1)
			playerIndex = GameArcade.games().GetPlayerIndex(cookie.getPlayerID(), cookie.getGameID());
	}
}
