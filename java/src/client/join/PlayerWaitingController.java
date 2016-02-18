package client.join;



import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientGame;
import client.networking.ServerProxyException;
import shared.definitions.AIType;
import shared.definitions.ModelNotification;
import shared.model.ModelException;
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
		boolean currOpen = getView().isModalShowing();
		
		if (currOpen) getView().closeModal();
		getView().setPlayers(players);
		if (currOpen) getView().showModal();
		
		if (players.length == 4)
		{
			//Start the game
			ClientGame.getGame().StartGame();
			getView().closeModal();
		}
	}

	@Override
	public void addAI() {
		String ai = getView().getSelectedAI();
		AIType aiType = AIType.fromString(ai);
		//System.out.println(aiType);
		//System.out.println(ai);
		if (aiType == null)
			return;
		try {
			ClientGame.getCurrentProxy().addAI(aiType);
			ClientGame.getGame().RefreshFromServer();
		} 
		catch (ServerProxyException e) 
		{
			//System.err.println(e.toString());
			//System.err.println(e.getMessage());
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return;
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void alert() {
		// TODO Auto-generated method stub
		System.out.println("Refresh player waiting");
		refreshPlayersWaiting();
		
	}

}

