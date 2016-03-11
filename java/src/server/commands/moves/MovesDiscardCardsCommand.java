package server.commands.moves;

import java.util.List;

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
	public MovesDiscardCardsCommand(int playerID, int playerIndex, List<Integer> toDiscard)
	{
		super(playerID, playerIndex);
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
	public String Response() {
		// TODO Auto-generated method stub
		return null;
	}

}
