package client.join;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import client.base.Controller;
import client.base.IAction;
import client.misc.IMessageView;
import client.model.ClientGame;
import client.networking.ServerProxyException;
import shared.data.DataTranslator;
import shared.data.GameInfo;
import shared.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.networking.transport.NetGame;


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
	GameInfo[] games;


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
	private void refreshGameList()
	{
		try
		{
			//get the games
			List<GameInfo> allGames = ClientGame.getCurrentProxy().listGames();
			games = allGames.toArray(new GameInfo[allGames.size()]);

			//Create the current player
			PlayerInfo localPlayer = new PlayerInfo();
			localPlayer.setName(ClientGame.getCurrentProxy().getUserName());
			localPlayer.setId(ClientGame.getCurrentProxy().getUserId());
			getJoinGameView().setGames(games, localPlayer);
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

	private boolean createdGame = false;
	@Override
	public void createNewGame()
	{
		String name = getNewGameView().getTitle();
		if (name.equals(""))
		{
			getMessageView().setMessage("Unable to create game with blank title");
			getMessageView().showModal();
			return;
		}
		boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomPorts = getNewGameView().getUseRandomPorts();
		GameInfo newgameinfo = ClientGame.getGame().createGame(randomTiles, randomNumbers, randomPorts, name); 
		if (newgameinfo == null)
		{
			getMessageView().setMessage("Unable to create game: "+name);
			getMessageView().showModal();
			return;
		}
		
		//this.refreshGameList();
		getNewGameView().closeModal();
		
		createdGame = true;
		startJoinGame(newgameinfo);

		//Open the games list
		//getJoinGameView().showModal();
	}

	private GameInfo lastGameSelected = null;
	@Override
	public void startJoinGame(GameInfo game)
	{
		if (getJoinGameView().isModalShowing())
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
		
		//attach as listener
		
	}

	@Override
	public void cancelJoinGame()
	{
		lastGameSelected = null;
		createdGame = false;
		getSelectColorView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color)
	{
		if (lastGameSelected == null) 
			return;
		
		if (!ClientGame.getGame().joinGame(lastGameSelected, color))
		{
			getMessageView().setMessage("Unable to join game: #"+lastGameSelected);
			getMessageView().showModal();
			return;
		}
		getSelectColorView().closeModal();
		
		if (getJoinGameView().isModalShowing())
			getJoinGameView().closeModal();
		
		if (createdGame){
			getJoinGameView().showModal();
			createdGame = false;
			return;
		}
		createdGame = false;

		timer.stop();
		timer.setRepeats(false);
		System.out.println("joining game " + lastGameSelected);

		joinAction.execute();
	}

	/**
	 * This is the handler to respond to the timer by refreshing the game list
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		boolean jGameShowing = this.getJoinGameView().isModalShowing() && !this.getNewGameView().isModalShowing() && !this.getSelectColorView().isModalShowing();
		if (jGameShowing)
			this.getJoinGameView().closeModal();

		this.refreshGameList();
		if (jGameShowing)
			this.getJoinGameView().showModal();

		
		//refresh the colors panel to avoid race conditions where 2 players get the same color
		boolean colorsShowing = this.getSelectColorView().isModalShowing();
		if(colorsShowing)
		{
			this.getSelectColorView().closeModal();
		}
		
		if(lastGameSelected != null)
		{
			for(GameInfo g: games)
			{
				if(g.getId() == lastGameSelected.getId())
				{
					List<PlayerInfo> players = g.getPlayers();
					for (int i=0;i< players.size(); i++)
					{
						PlayerInfo play = players.get(i);
						getSelectColorView().setColorEnabled(play.getColor(), false);
						
						lastGameSelected = g;
					}
				}
			}
		}
		if(colorsShowing)
		{
			this.getSelectColorView().showModal();
		}
		
	}
}
