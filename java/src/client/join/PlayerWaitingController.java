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
public class PlayerWaitingController extends Controller implements IPlayerWaitingController,ModelObserver
{
	public PlayerWaitingController(IPlayerWaitingView view) 
	{
		super(view);
	}

	@Override
	public IPlayerWaitingView getView() 
	{	
		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() 
	{
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
	 * Refreshes the players waiting list
	 */
	private void refreshPlayersWaiting()
	{
		//Get the players
		PlayerInfo[] players = ClientGame.getGame().allCurrentPlayers();
		//Check if the modal is currently open
		boolean currOpen = getView().isModalShowing();
		
		//If it's open we need to close it before we update it
		//I don't know why but it doesn't work if we don't 
		//It's what the TA did when he took this class
		if (currOpen) 
			getView().closeModal();
		getView().setPlayers(players);
		if (currOpen) 
			getView().showModal();
		
		//If we have all the players needed to start the game
		if (players.length == 4)
		{
			//Start the game
			getView().closeModal();
			ClientGame.getGame().StartGame();
			
		}
	}

	@Override
	public void addAI() {
		String ai = getView().getSelectedAI();
		AIType aiType = AIType.fromString(ai);
		
		if (aiType == null)
			return;
		
		try 
		{
			ClientGame.getCurrentProxy().addAI(aiType);
			ClientGame.getGame().RefreshFromServer();
		} 
		catch (ServerProxyException e) 
		{
			return;
		} 
		catch (ModelException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void alert() 
	{
		System.out.println("Refresh player waiting");
		refreshPlayersWaiting();	
	}
}

