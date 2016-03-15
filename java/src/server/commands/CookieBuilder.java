package server.commands;

import server.model.GameArcade;
import shared.model.Player;
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
	 */
	public final void SetCookie(NetworkCookie cookie)
	{
		this.cookie = cookie;
		
		if (cookie.getGameID() != -1)
		{
			Player player = GameArcade.games().PlayerInGame(cookie.getPlayerID(), cookie.getGameID());
			playerIndex = player.playerIndex();
		}
	}
}
