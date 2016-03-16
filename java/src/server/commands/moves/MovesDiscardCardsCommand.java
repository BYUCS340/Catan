package server.commands.moves;

import java.util.List;

import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles discarding cards.
 * @author Jonathan Sadler
 *
 */
public class MovesDiscardCardsCommand extends MovesCommand 
{
	private List<Integer> toDiscard;
	
	/**
	 * Creates a command that handles discarding.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param toDiscard The quantity of cards to discard (brick, ore, sheep, wheat, wood).
	 */
	public MovesDiscardCardsCommand(NetworkCookie cookie, int playerIndex, List<Integer> toDiscard)
	{
		super(cookie, playerIndex);
		this.toDiscard = toDiscard;
	}

	@Override
	public boolean Execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unexecute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}
