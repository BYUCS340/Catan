package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.model.map.Coordinate;
import shared.model.map.Transaction;
import shared.model.chat.ChatBox;

/**
 * The game manager class acts as a facade between the player/game objects and the ServerProxy/UI
 * @author matthewcarlson, garrettegan
 *
 */
public class GameManager implements ModelSubject
{
	//public  MapController mapController; 
	protected int gameID;
	public String gameTitle;
	protected GameState gameState;
	protected Bank gameBank;
	protected List<Player> players;
	protected VictoryPointManager victoryPointManager;
	protected ChatBox waterCooler;
	protected GameActionLog log;
	public IMapController map; //this is exposed for easier access
	protected int version;
	private int[] playerColors;
	private int playerCanMoveRobber;
	private NotificationCenter notifyCenter;
	
	
	/**
	 * Constructor for the game manager
	 * @post all players
	 */
	public GameManager(){
		this("Default",0);
	}
	
	/**
	 * Creates a game manager with the specified id
	 * @param name the title of the game
	 * @param id
	 */
	public GameManager(String name,int id)
	{
		//version is by default -1 before it is connected to a server
		version = -1;
		this.gameID = id;
		this.gameTitle = name;
		waterCooler = new ChatBox();
		log = new GameActionLog();
		players = new ArrayList<>();
		gameBank = new Bank();
		gameState = new GameState();
		//mapController = new MapController();
		victoryPointManager = new VictoryPointManager();
		notifyCenter = new NotificationCenter();
		playerColors = new int[10];
		//fill the array with -1 by default
		Arrays.fill(playerColors,-1);
		playerCanMoveRobber = -1;
		gameBank.resetToBankDefaults();
	}
	
	/**
	 * Resets a game to default state
	 */
	public void reset()
	{
		Arrays.fill(playerColors,-1);
		version = -1;
		waterCooler = new ChatBox();
		log = new GameActionLog();
		players = new ArrayList<>();
		gameBank = new Bank();
		gameState = new GameState();
		//mapController = new MapController();
		victoryPointManager = new VictoryPointManager();
		playerColors = new int[10];
		//fill the array with -1 by default
		Arrays.fill(playerColors,-1);
		playerCanMoveRobber = -1;
		gameBank.resetToBankDefaults();
	}
	
	
	//========================================================================================
	//Notification Center

	/**
	 * Starts listening to all changes 
	 */
	public boolean startListening(ModelObserver listener)
	{
		notifyCenter.add(listener);
		return true;
	}
	/**
	 * starts listening for a specific type of change
	 */
	public boolean startListening(ModelObserver listener, ModelNotification type)
	{
		notifyCenter.add(listener,type);
		return true;
	}
	
	/**
	 * Stops listening on a model observer
	 */
	public boolean stopListening(ModelObserver listener)
	{
		notifyCenter.remove(listener);
		return true;
	}
	
	
	
	/**
	 * Adds a player to the game
	 * @param name
	 * @return the player index number 
	 * @throws ModelException if there are too many players already in the game or if that color has been used
	 */
	public int AddPlayer(String name, CatanColor color, boolean isHuman) throws ModelException
	{
		//check if that color has already been used
		if (playerColors[color.ordinal()] != -1)
			throw new ModelException();
		
		int newIndex = players.size();
		if (newIndex > 3)
		{
			throw new ModelException("Too many players already to add another");
		}
		Player newPlayer = new Player(name, newIndex, color, isHuman);
		players.add(newPlayer);
		
		this.notifyCenter.notify(ModelNotification.PLAYERS);
		
		playerColors[color.ordinal()] = newIndex;
		return newIndex;
	}
	
	/**
	 * Sets the players 
	 * @param players
	 */
	public void SetPlayers(List<Player> players)
	{
		Arrays.fill(playerColors,-1);
		this.players.clear();
		for (int i=0; i< players.size(); i++)
		{
			Player p = players.get(i);
			System.out.println(p);
			this.players.add(p);
			playerColors[p.color.ordinal()] = p.playerIndex();
		}
		this.notifyCenter.notify(ModelNotification.PLAYERS);
		
	}
	
	/**
	 * Gets the current number of players
	 * @return
	 */
	public int getNumberPlayers()
	{
		return players.size();
	}
	
	/**
	 * Gets the player index by color
	 * @param color
	 * @return -1 if not found
	 */
	public int getPlayerIndexByColor(CatanColor color)
	{
		return playerColors[color.ordinal()];
	}
	
	public CatanColor getPlayerColorByIndex(int playerIndex)
	{
		return players.get(playerIndex).color;
	}
	
	
	/**
	 * Gets the number of resources for the player
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	public int playerResourceCount(int playerIndex, ResourceType type)
	{
		return this.players.get(playerIndex).playerBank.getResourceCount(type);
	}
	
	/**
	 * Gets the number of devCards for the current player
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	public int playerDevCardCount(int playerIndex,DevCardType type)
	{
		return this.players.get(playerIndex).playerBank.getDevCardCount(type);
	}
	/**
	 * Gets the total number of dev cards for a player
	 * @param playerIndex
	 * @return
	 */
	public int playerDevCardCount(int playerIndex)
	{
		return this.players.get(playerIndex).playerBank.getDevCardCount();
	}
	
	/**
	 * Gets the number of devCards for the current player
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	public int playerPieceCount(int playerIndex,PieceType type)
	{
		try {
			return this.players.get(playerIndex).playerBank.getPieceCount(type);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * The number of players in a game
	 * @return
	 */
	public int NumberActivePlayers()
	{
		return players.size();
	}
	
	
	
	/**
	 * Returns the current version
	 * @return
	 */
	public int GetVersion()
	{
		return this.version;
	}
	/**
	 * @see <a href="https://imgs.xkcd.com/comics/random_number.png">Sauce</a>
	 * @post all player's banks will be added resources
	 * @return the number rolled
	 * @throws ModelException if we aren't in the rolling phase
	 */
	public int RollDice() throws ModelException
	{
		if (gameState.state != GameRound.ROLLING) 
			throw new ModelException("Game isn't in rolling state");
		
		//Correctly rolls the dice
		int diceRoll = (int) ((Math.random() * 5) + (Math.random() * 5) + 2);
		
		//check if we can move the robber
		if (diceRoll == 7 )
		{
			this.playerCanMoveRobber = this.CurrentPlayersTurn();
			gameState.startRobbing();
		}
		else
		{
			gameState.stopRolling();
		}
		log.logAction(this.CurrentPlayersTurn(), "rolled a "+diceRoll);
		
		//Call map to update the get the transacations
		Iterator<Transaction> transList = map.GetVillages(diceRoll);
		//Go through each trasaction
		while (transList.hasNext())
		{
			Transaction trans = transList.next();
			//Get the player ID
			int playerIndex = this.getPlayerIndexByColor(trans.getColor());
			try {
				//Get the player
				Player player = this.GetPlayer(playerIndex);
				//The piece type
				PieceType harvester = trans.getPieceType();
				int amount = 0;
				//Get the correct amount for the piece type
				if (harvester == PieceType.CITY) amount = 2;
				else if (harvester == PieceType.SETTLEMENT) amount = 1;
				//Get the resource
				ResourceType resource = ResourceType.fromHex(trans.getHexType());
				//Give it to them
				//TODO get the resources from the game bank and give them to the player
				player.playerBank.giveResource(resource, amount);
				//player.playerBank
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				System.err.println("No player with the color specified by transaction found");
				System.err.println(trans.getColor());
				//e.printStackTrace();
			}
			
		}
		return diceRoll; // chosen by fair dice roll
						// guaranteed to be random
	}
	
	
	//--------------------------------------------------------------------------
	//Player Turn Game methods
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildRoad(int playerIndex, Coordinate start, Coordinate end) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildRoad(playerIndex, start, end))
			throw new ModelException("Player can't build road right now");
		GetPlayer(playerIndex).playerBank.buildRoad();
		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		map.placeRoad(start, end, color);
		victoryPointManager.playerBuiltRoad(playerIndex);
	}
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildSettlement(int playerIndex, Coordinate location) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildSettlement(playerIndex, location))
			throw new ModelException("Player can't build settlement right now");
		GetPlayer(playerIndex).playerBank.buildSettlement();
		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		map.placeSettlement(location, color);
		victoryPointManager.playerBuiltSettlement(playerIndex);
		
	}
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildCity(int playerIndex,Coordinate location) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildSettlement(playerIndex, location))
			throw new ModelException("Player can't build city right now");
		GetPlayer(playerIndex).playerBank.buildRoad();
		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		map.placeCity(location,color);
		victoryPointManager.playerBuiltCity(playerIndex);
	}
	
	/**
	 * Attempts to buy a dev card
	 * @param playerIndex 0 to 3
	 * @throws ModelException if the player doesn't have the resources
	 * @returns the dev card type bought (hopefully somewhat random)
	 */
	public DevCardType BuyDevCard(int playerIndex) throws ModelException
	{
		if (!this.CanBuyDevCard(playerIndex)) throw new ModelException("Player can't buy dev card");
		Bank playerBank = GetPlayer(playerIndex).playerBank;
		playerBank.buyDevCard();
		DevCardType devcard = gameBank.getDevCard();
		playerBank.giveDevCard(devcard);
		victoryPointManager.playerGotDevCard(playerIndex, devcard);
		return devcard;
	}
	
	/**
	 * Plays a dev card
	 * @param playerIndex
	 * @param type
	 * @throws ModelException 
	 */
	public void playDevCard(int playerIndex, DevCardType type) throws ModelException
	{
		if (players.get(playerIndex).playerBank.getDevCardCount(type) < 1) throw new ModelException("Player doesn't have a dev card of that type to play");
		if (type == DevCardType.SOLDIER)
		{
			this.playerCanMoveRobber = playerIndex;
			
		}
		players.get(playerIndex).playerBank.getDevCard(type);
	}
	
	/**
	 * Gets the trade ratio for a given resource for a player
	 * @param playerIndex
	 * @return never more than 4 or less than 2
	 */
	public int getTradeRatio(int playerIndex, ResourceType type)
	{
		//Check the Map to see if they're connected to a port
		//map.getTradeRatio?
		//Otherwise return the default trade in value
		return 4;
	}
	
	/**
	 * Places the robber
	 * @param playerIndex
	 * @param location
	 * @throws ModelException 
	 */
	public void placeRobber(int playerIndex, Coordinate location) throws ModelException
	{
		if (!this.CanPlaceRobber(playerIndex)) throw new ModelException("Player can't place robber right now");
		map.placeRobber(location);
		//mark that the robber has been moved
		this.playerCanMoveRobber = -1;
	}
	
	//--------------------------------------------------------------------------
	//CanDo methods
	
	/**
	 * Checks to see if it's a player's turn
	 * @param playerIndex 0 to 3
	 * @return if possible
	 */
	public boolean CanPlayerPlay(int playerIndex)
	{
		//If we aren't in the building phase and this player isn't their turn
		if (this.gameState.activePlayerIndex != playerIndex)
		{
			return false;
		}
		else if (this.gameState.state != GameRound.FIRSTROUND && this.gameState.state != GameRound.SECONDROUND && this.gameState.state != GameRound.PLAYING)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * See if a player can discard cards
	 * @param playerIndex 0 to 3
	 * @param type the type of the resource
	 * @param amount number to discard
	 * @todo: how do we implement this?
	 * @return
	 */
	public boolean CanDiscardCards(int playerIndex,ResourceType type, int amount)
	{
		
		return false;
	}
	
	/**
	 * Checks to see if a player can roll their number
	 * @param playerIndex
	 * @return
	 */
	public boolean CanRollNumber(int playerIndex)
	{
		if (this.CurrentPlayersTurn() != playerIndex)
			return false;
		if (CurrentState() == GameRound.ROLLING)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if a player can build a road at a location
	 * @param playerIndex 0-3
	 * @param location the edge's location
	 * @param end 
	 * @return true if possible
	 */
	public boolean CanBuildRoad(int playerIndex,Coordinate start, Coordinate end)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			if (!GetPlayer(playerIndex).playerBank.canBuildRoad())
				return false;
			//check map
			CatanColor color = getPlayerColorByIndex(playerIndex);
			if (!map.canPlaceRoad(start, end, color))
				return false;
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * checks to see if a player can build a settlement
	 * @param playerIndex
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildSettlement(int playerIndex, Coordinate location)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			//check if they have the resources needed
			if (!GetPlayer(playerIndex).playerBank.canBuildRoad())
				return false;
			//ask the map
			if (!map.canPlaceSettlement(location))
				return false;
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	/**
	 * Checks to see if a player can build a city 
	 * @param playerIndex 0 to 3
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildCity (int playerIndex, Coordinate location)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			if (!GetPlayer(playerIndex).playerBank.canBuildCity())
				return false;
			//ask the map
			if (!map.canPlaceCity(location,this.getPlayerColorByIndex(playerIndex)))
				return false;
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean CanOfferTrade (int playerIndex)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			GetPlayer(playerIndex).playerBank.canBuildCity();
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Can do method for whether a player can accept an article
	 * @todo need to figure out what this does
	 * @param playerIndex
	 * @return
	 */
	public boolean canAcceptTrade(int playerIndex)
	{
		return false;
	}
	
	/**
	 * Testing function
	 */
	public void payDayForDayz()
	{
		//TODO remove
		for (int i=0;i < this.NumberActivePlayers(); i++)
		{
			try {
				players.get(i).playerBank.giveResource(ResourceType.BRICK, 5);
				players.get(i).playerBank.giveResource(ResourceType.ORE, 5);
				players.get(i).playerBank.giveResource(ResourceType.SHEEP, 5);
				players.get(i).playerBank.giveResource(ResourceType.WOOD, 5);
				players.get(i).playerBank.giveResource(ResourceType.WHEAT, 5);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Can do method for determinign wheter a player can martitime trade
	 * @param playerIndex
	 * @return
	 */
	public boolean CanMaritimeTrade (int playerIndex)
	{
		return !CanPlayerPlay(playerIndex);
	}
	
	/**
	 * Checks whether a player can finish their turn
	 * @param playerIndex
	 * @return
	 */
	public boolean CanFinishTurn (int playerIndex)
	{
		//TODO check to make sure they've built their settlements during the first round
		if (this.CurrentPlayersTurn() != playerIndex) return false;
		if (this.CurrentState() == GameRound.PLAYING || this.CurrentState() == GameRound.FIRSTROUND || this.CurrentState() == GameRound.SECONDROUND) return true;
		return false;
	}
	
	/**
	 * Checks whether the current player can finish their turn
	 * @return true or false
	 */
	public boolean CanFinishTurn ()
	{
		return this.CanFinishTurn(gameState.activePlayerIndex);
	}
	
	/**
	 * Checks to see if a player can buy a dev card
	 * @param playerIndex 0 to 3
	 * @return true if possible, always false if not the players turn
	 */
	public boolean CanBuyDevCard (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		//This is a total message chain but eh
		try 
		{
			return this.GetPlayer(playerIndex).playerBank.canBuyDevCard();
		} 
		catch (ModelException e) 
		{
			// if the player isn't found
			return false;
		}
	}
	
	/**
	 * Check if player can chat
	 * @param playerIndex
	 * @return always true?
	 */
	public boolean canChat(int playerIndex)
	{
		return true;
	}
	
	/**
	 * Checks if a player has at least one dev card of the type
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	private boolean PlayerHasADevCard(int playerIndex, DevCardType type)
	{
		//This is a total message chain but eh
		try 
		{
			int count = this.GetPlayer(playerIndex).playerBank.getDevCardCount(type);
			if (count > 0)
				return true;
			else
				return false;
		} 
		catch (ModelException e) 
		{
			// if the player isn't found
			return false;
		}
	}
	
	/**
	 * Checks to see if the player can use a year of plenty picture
	 * @param playerIndex
	 * @return
	 */
	public boolean CanUseYearOfPlenty (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		return this.PlayerHasADevCard(playerIndex, DevCardType.YEAR_OF_PLENTY);
	}
	
	/**
	 * Checks if a player can use the road builder?
	 * @todo figure out what this function does
	 * @param playerIndex
	 * @return
	 */
	public boolean CanUseRoadBuilder (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		return this.PlayerHasADevCard(playerIndex, DevCardType.ROAD_BUILD);
	}
	
	/**
	 * Checks if a player can play a solider card
	 * @param playerIndex
	 * @return
	 */
	public boolean CanUseSoldier (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		return this.PlayerHasADevCard(playerIndex, DevCardType.SOLDIER);

	}
	
	/**
	 * Checks to see if a player can play a monopoly card
	 * @param playerIndex
	 * @return
	 */
	public boolean CanUseMonopoly (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		return this.PlayerHasADevCard(playerIndex, DevCardType.MONOPOLY);
		
	}
	
	/**
	 * Check to see if they have the momument card
	 * @param playerIndex
	 * @return
	 */
	public boolean CanUseMonument (int playerIndex)
	{
		if (!this.CanPlayerPlay(playerIndex)) 
			return false;
		return this.PlayerHasADevCard(playerIndex, DevCardType.MONUMENT);

	}
	
	/**
	 * Determines whenter player can play card
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	public boolean CanPlayDevCard(int playerIndex, DevCardType type)
	{
		switch (type)
		{
		case MONOPOLY:  return this.CanUseMonopoly(playerIndex);
		case MONUMENT:  return this.CanUseMonument(playerIndex);
		case ROAD_BUILD:return this.CanUseRoadBuilder(playerIndex);
		case SOLDIER:   return this.CanUseSoldier(playerIndex);
		case YEAR_OF_PLENTY: return this.CanUseYearOfPlenty(playerIndex);
		default:
			return false;
			
		}
	}
	
	/**
	 * Checks to see if a player can place the robber
	 * @param playerIndex
	 * @return
	 */
	public boolean CanPlaceRobber (int playerIndex)
	{
		return this.CurrentState() == GameRound.ROBBING || this.playerCanMoveRobber == playerIndex;
	}
	
	
	//--------------------------------------------------------------------------
	//Chat methods
	/**
	 * When the player chats currently playing 
	 * @param message
	 */
	public void CurrentPlayerChat(String message)
	{
		this.PlayerChat(gameState.activePlayerIndex, message);
	}
	
	/**
	 * Chats for the specified player
	 * @param playerIndex 0-3
	 * @param message
	 */
	public void PlayerChat(int playerIndex, String message)
	{
		waterCooler.put(message, playerIndex);
	}
	
	//--------------------------------------------------------------------------
	//Game methods
	
	/**
	 * Starts the game
	 */
	public void StartGame()
	{
		gameState.startGame();
	}
	
	/**
	 * Ends the current player's turn
	 * @throws ModelException 
	 */
	public void FinishTurn() throws ModelException
	{
		if (!this.CanFinishTurn() || !gameState.nextTurn()) throw new ModelException("Player can't finish turn right now");
	}
	
	/**
	 * Gets the player index of the current player
	 * @return 0 to 3
	 */
	public int CurrentPlayersTurn()
	{
		return gameState.activePlayerIndex;
	}
	
	/**
	 * Gets the specified player
	 * @param playerIndex 0 to 3
	 * @return
	 */
	private Player GetPlayer(int playerIndex) throws ModelException
	{
		if (playerIndex >= 0 && playerIndex < 4 && playerIndex < players.size())
		{
			return this.players.get(playerIndex);
		}
		else
		{
			throw new ModelException("Invalid player ID");
		}
	}
	
	/**
	 * Gets the current player
	 * @return the player
	 */
	private Player GetCurrentPlayer() throws ModelException
	{
		return this.GetPlayer(gameState.activePlayerIndex);
	}
	
	/**
	 * Returns the current round of the game
	 * @return
	 */
	public GameRound CurrentState()
	{
		return gameState.state;
	}
	
	/**
	 * Check whether the game has started
	 * @return
	 */
	public boolean hasGameStarted()
	{
		return gameState.state != GameRound.WAITING;
	}
	
    /**
	 * Returns the log 
	 * @return the log
	 */
	public GameActionLog getGameActionLog()
	{
		return log;

	}
	
	/**
	 * Returns the chat
	 * @return the chat
	 */
	public ChatBox getChat()
	{
		return waterCooler;
	}
	
	/**
	 * Returns the VictoryPointManager
	 * @return the VictoryPointManager
	 */
	public VictoryPointManager getVictoryPointManager()
	{
		return victoryPointManager;
	}

	
}
