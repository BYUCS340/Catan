package server.commands;

/**
 * Builder class that is used for commands that require cookie data.
 * @author Jonathan Sadler
 *
 */
public abstract class CookieBuilder implements ICommandBuilder 
{
	protected int playerID;
	
	/**
	 * Data to be set from the cookie.
	 * @param playerID The player ID.
	 */
	public final void SetPlayerData(int playerID)
	{
		this.playerID = playerID;
	}
}
