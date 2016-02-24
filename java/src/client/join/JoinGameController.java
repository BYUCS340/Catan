package client.join;

import shared.definitions.CatanColor;
import shared.networking.transport.NetGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import client.base.*;
import client.data.*;
import client.misc.*;
import client.model.ClientGame;
import client.networking.ServerProxyException;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, ActionListener
{
	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private Timer timer;


	/**
	 * JoinGameController constructor
	 *
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView,
								ISelectColorView selectColorView, IMessageView messageView)
	{
		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);

		timer = new Timer(3500, this);

	}


	public IJoinGameView getJoinGameView()
	{
		return (IJoinGameView)super.getView();
	}

	/**
	 * Returns the action to be executed when the user joins a game
	 *
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction()
	{
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 *
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value)
	{
		joinAction = value;
	}

	public INewGameView getNewGameView()
	{
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView)
	{
		this.newGameView = newGameView;
	}

	public ISelectColorView getSelectColorView()
	{
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView)
	{
		this.selectColorView = selectColorView;
	}

	public IMessageView getMessageView()
	{
		return messageView;
	}
	public void setMessageView(IMessageView messageView)
	{
		this.messageView = messageView;
	}

	@Override
	public void start()
	{
		refreshGameList();
		getJoinGameView().showModal();
		timer.start();
	}

	/**
	 * Refreshes the view's game list from the proxy
	 */
	private int gameCount = 0;
	private int joinedPlayerCount = 0;
	private void refreshGameList()
	{
		try
		{
			List<NetGame> allGames = ClientGame.getCurrentProxy().listGames();

			//Get the number of players currently joined to a game
			int currentPlayerCount = 0;
			Iterator<NetGame> gameIter = allGames.iterator();
			while (gameIter.hasNext())
				currentPlayerCount += gameIter.next().getNetPlayers().size();
			//If there is no need to update, don't
			if (allGames.size() <= gameCount && joinedPlayerCount != currentPlayerCount) return;

			//Get the games
			GameInfo[] games = new GameInfo[allGames.size()];
			for (int i=0; i< allGames.size(); i++)
			{
				games[i] = ClientDataTranslator.convertGame(allGames.get(i));
			}
			//Create the current player
			PlayerInfo localPlayer = new PlayerInfo();
			localPlayer.setName(ClientGame.getCurrentProxy().getUserName());
			localPlayer.setId(ClientGame.getCurrentProxy().getUserId());
			getJoinGameView().setGames(games, localPlayer);
			//update the game count and player count
			gameCount = games.length;
			joinedPlayerCount = currentPlayerCount;
		}
		catch (ServerProxyException e)
		{
			System.err.println("UNABLE TO GET GAME LIST " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void startCreateNewGame()
	{
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame()
	{
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame()
	{
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
		this.refreshGameList();
		getNewGameView().closeModal();

		//Open the games list
		getJoinGameView().showModal();
	}

	private GameInfo lastGameSelected = null;
	@Override
	public void startJoinGame(GameInfo game)
	{
		getJoinGameView().closeModal();
		lastGameSelected = game;
		List<PlayerInfo> players = game.getPlayers();
		CatanColor mycolor = null;
		for (int i=0;i<players.size(); i++)
		{
			PlayerInfo play = players.get(i);
			getSelectColorView().setColorEnabled(play.getColor(), false);
			if (play.getId() == ClientGame.getCurrentProxy().getUserId())
			{
				mycolor = play.getColor();
			}
		}

		//If we are rejoining a game
		getSelectColorView().showModal();
		if (mycolor != null)
		{

			joinGame(mycolor);
		}
	}

	@Override
	public void cancelJoinGame()
	{
		lastGameSelected = null;
		getSelectColorView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color)
	{
		if (lastGameSelected == null) return;

		if (!ClientGame.getGame().joinGame(lastGameSelected, color))
		{
			getMessageView().setMessage("Unable to join game: #"+lastGameSelected);
			getMessageView().showModal();
			return;
		}
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();

		timer.stop();
		timer.setRepeats(false);
		System.out.println("joining game "+lastGameSelected);

		joinAction.execute();
	}

	/**
	 * This is the handler to respond to the timer by refreshing the game list
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean showing = this.getJoinGameView().isModalShowing() && !this.getNewGameView().isModalShowing() && !this.getSelectColorView().isModalShowing();
		if (showing)
			this.getJoinGameView().closeModal();

		this.refreshGameList();
		if (showing)
			this.getJoinGameView().showModal();

	}
}
