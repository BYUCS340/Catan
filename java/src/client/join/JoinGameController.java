package client.join;

import shared.definitions.CatanColor;
import shared.networking.transport.NetGame;

import java.util.List;

import client.base.*;
import client.data.*;
import client.misc.*;
import client.model.ClientGame;
import client.networking.ServerProxyException;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {
		try 
		{
			List<NetGame> allGames = ClientGame.getCurrentProxy().listGames();
			GameInfo[] games = new GameInfo[allGames.size()];
			for (int i=0; i< allGames.size(); i++)
			{
				games[i] = ClientDataTranslator.convert(allGames.get(i));
			}
			PlayerInfo localPlayer = new PlayerInfo();
			getJoinGameView().setGames(games, localPlayer);
		} 
		catch (ServerProxyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		String name = getNewGameView().getTitle();
		boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		if (!ClientGame.getGame().createGame(randomTiles, randomNumbers, randomPorts, name))
		{
			getMessageView().setMessage("Unable to create game: "+name);
			getMessageView().showModal();
			return;
		}
		
		getNewGameView().closeModal();
	}
	private int lastGameID = 0;
	@Override
	public void startJoinGame(GameInfo game) {
		lastGameID = game.getId();
		
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		lastGameID = 0;
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		if (lastGameID == 0) return;
		
		if (!ClientGame.getGame().joinGame(lastGameID, color))
		{
			return;
		}
		
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}

}

