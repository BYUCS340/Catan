package server.commands.moves;

import java.util.List;

import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;

/**
 * Command that handles discarding cards.
 * @author Jonathan Sadler
 *
 */
public class MovesDiscardCardsCommand extends MovesCommand 
{
	private static final long serialVersionUID = -165151639575457077L;
	
	transient private ServerGameManager sgm;
	private List<Integer> toDiscard;
	
	/**
	 * Creates a command that handles discarding.
	 * @param cookie The player ID/game ID
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
		try
		{
			sgm = GameArcade.games().GetGame(gameID);
			return sgm.ServerDiscardCards(this.playerIndex, this.toDiscard);
		}
		catch (GameException e)
		{ //game not found
			e.printStackTrace();
		}
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
		if (sgm != null)
			return SerializationUtils.serialize(sgm.ServerGetSerializableModel());
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}
