package client.join;



import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientGame;
import client.networking.ServerProxyException;
import shared.definitions.AIType;
import shared.definitions.ModelNotification;
import shared.model.ModelObserver;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController,ModelObserver {

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
		
		
		ClientGame.getGame().startListening(this,ModelNotification.PLAYERS);
		refreshPlayersWaiting();
		
		getView().showModal();
	}
	
	/**
	 * 
	 */
	private void refreshPlayersWaiting()
	{
		PlayerInfo[] players = ClientGame.getGame().allCurrentPlayers();
		
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

	@Override
	public void alert() {
		// TODO Auto-generated method stub
		System.out.println("Refresh player waiting");
		refreshPlayersWaiting();
		
	}

}

