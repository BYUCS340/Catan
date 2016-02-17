package client.join;



import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientGame;
import client.networking.ServerProxyException;
import shared.definitions.AIType;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		
	}

	@Override
	public IPlayerWaitingView getView() {
		
		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
		//Set the AI choices
		
		//Get the ai types 
		AIType[] aiTypes = AIType.values();
		String ais[] = new String[aiTypes.length];
		for (int i=0;i<aiTypes.length; i++)
			ais[i] = aiTypes[i].toString();
		getView().setAIChoices(ais);
		
		refreshPlayersWaiting();
		
		getView().showModal();
	}
	
	/**
	 * 
	 */
	private void refreshPlayersWaiting()
	{
		PlayerInfo[] players = new PlayerInfo[1];
		
		getView().setPlayers(players);
	}

	@Override
	public void addAI() {
		String ai = getView().getSelectedAI();
		AIType aiType = AIType.fromString(ai);
		try {
			ClientGame.getCurrentProxy().addAI(aiType);
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		// TEMPORARY
		getView().closeModal();
	}

}

