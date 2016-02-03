package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.map.MapController;
import client.networking.ServerProxy;
import client.networking.ServerProxyException;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.model.map.Coordinate;
import shared.model.chat.ChatBox;
import shared.networking.transport.NetGameModel;

/**
 * The game manager class acts as a facade between the player/game objects and the ServerProxy/UI
 * @author matthewcarlson, garrettegan
 *
 */
public class GameManager
{
	public  MapController mapController; //this is exposed for easier access
	
	protected GameState gameState;
	protected Bank gameBank;
	protected List<Player> players;
	protected VictoryPointManager victoryPointManager;
	protected ChatBox waterCooler;
	protected GameActionLog log;
	public IMapController map;
	protected int version;
	private int[] playerColors;
	private int playerCanMoveRobber;
	
	
	/**
	 * Constructor for the game manager
	 * @post all players
	 */
	public GameManager()
	{
		//version is by default -1 before it is connected to a server
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
			throw new ModelException();
		}
		Player newPlayer = new Player(name, newIndex, color, isHuman);
		players.add(newPlayer);
		
		playerColors[color.ordinal()] = newIndex;
		return newIndex;
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
	
	public CatanColor getPlayerColorByIndex(int playerID)
	{
		return players.get(playerID).color;
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
	 */
	public int RollDice()
	{
		
		gameState.startBuildPhase();
		int diceRoll = 4;
		//check if we can move the robber
		if (diceRoll == 7 )
		{
			this.playerCanMoveRobber = this.CurrentPlayersTurn();
		}
		log.logAction(this.CurrentPlayersTurn(), "rolled a "+diceRoll);
		//Call map to update the get the transacations
		
		return diceRoll; // chosen by fair dice roll
						// guaranteed to be random
	}
	
	
	//--------------------------------------------------------------------------
	//Player Turn Game methods
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildRoad(int playerID,Coordinate start, Coordinate end) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildRoad(playerID, start,end))
			throw new ModelException();
		GetPlayer(playerID).playerBank.buildRoad();
		CatanColor color = this.getPlayerColorByIndex(playerID);
		map.placeRoad(start,end, color);
		victoryPointManager.playerBuiltRoad(playerID);
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildSettlement(int playerID,Coordinate location) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildSettlement(playerID, location))
			throw new ModelException();
		GetPlayer(playerID).playerBank.buildSettlement();
		CatanColor color = this.getPlayerColorByIndex(playerID);
		map.placeSettlement(location, color);
		victoryPointManager.playerBuiltSettlement(playerID);
		
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildCity(int playerID,Coordinate location) throws ModelException
	{
		//check to see if player has resources
		if (!this.CanBuildSettlement(playerID, location))
			throw new ModelException();
		GetPlayer(playerID).playerBank.buildRoad();
		CatanColor color = this.getPlayerColorByIndex(playerID);
		map.placeCity(location,color);
		victoryPointManager.playerBuiltCity(playerID);
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID 0 to 3
	 * @throws ModelException if the player doesn't have the resources
	 */
	public DevCardType BuyDevCard(int playerID) throws ModelException
	{
		if (!this.CanBuyDevCard(playerID)) throw new ModelException();
		Bank playerBank = GetPlayer(playerID).playerBank;
		playerBank.buyDevCard();
		DevCardType devcard = gameBank.getDevCard();
		playerBank.giveDevCard(devcard);
		victoryPointManager.playerGotDevCard(playerID, devcard);
		return devcard;
	}
	
	/**
	 * Gets the trade ratio for a given resource for a player
	 * @param playerID
	 * @return never more than 4 or less than 2
	 */
	public int getTradeRatio(int playerID, ResourceType type)
	{
		//Check the Map to see if they're connected to a port
		//map.getTradeRatio?
		//Otherwise return the default trade in value
		return 4;
	}
	
	/**
	 * Places the robber
	 * @param playerID
	 * @param location
	 * @throws ModelException 
	 */
	public void placeRobber(int playerID, Coordinate location) throws ModelException
	{
		if (!this.CanPlaceRobber(playerID)) throw new ModelException();
		map.placeRobber(location);
		//mark that the robber has been moved
		this.playerCanMoveRobber = -1;
	}
	
	//--------------------------------------------------------------------------
	//CanDo methods
	
	/**
	 * Checks to see if it's a player's turn
	 * @param playerID 0 to 3
	 * @return if possible
	 */
	public boolean CanPlayerPlay(int playerID)
	{
		//If we aren't in the building phase and this player isn't their turn
		if (CurrentState() != GameRound.PLAYING || this.gameState.activePlayerIndex != playerID)
			return false;
		else
			return false;
	}
	/**
	 * See if a player can discard cards
	 * @param playerID 0 to 3
	 * @param type the type of the resource
	 * @param amount number to discard
	 * @todo: how do we implement this?
	 * @return
	 */
	public boolean CanDiscardCards(int playerID,ResourceType type, int amount)
	{
		
		return false;
	}
	
	/**
	 * Checks to see if a player can roll their number
	 * @param playerID
	 * @return
	 */
	public boolean CanRollNumber(int playerID)
	{
		if (this.CurrentPlayersTurn() != playerID)
			return false;
		if (CurrentState() == GameRound.ROLLING)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if a player can build a road at a location
	 * @param playerID 0-3
	 * @param location the edge's location
	 * @param end 
	 * @return true if possible
	 */
	public boolean CanBuildRoad(int playerID,Coordinate start, Coordinate end)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		try 
		{
			if (!GetPlayer(playerID).playerBank.canBuildRoad())
				return false;
			//check map
			if (!map.canPlaceRoad(start, end))
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
	 * @param playerID
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildSettlement(int playerID, Coordinate location)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		try 
		{
			//check if they have the resources needed
			if (!GetPlayer(playerID).playerBank.canBuildRoad())
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
	 * @param playerID 0 to 3
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildCity (int playerID, Coordinate location)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		try 
		{
			if (!GetPlayer(playerID).playerBank.canBuildCity())
				return false;
			//ask the map
			if (!map.canPlaceCity(location,this.getPlayerColorByIndex(playerID)))
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
	
	public boolean CanOfferTrade (int playerID)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		try 
		{
			GetPlayer(playerID).playerBank.canBuildCity();
		}
		catch (ModelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean CanMaritimeTrade (int playerID)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		try 
		{
			GetPlayer(playerID).playerBank.canBuildCity();
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
	 * 
	 * @param playerID
	 * @return
	 */
	public boolean CanFinishTurn (int playerID)
	{
		if (this.CurrentPlayersTurn() != playerID) return false;
		if (this.CurrentState() == GameRound.PLAYING) return true;
		return false;
	}
	
	/**
	 * Checks to see if a player can buy a dev card
	 * @param playerID 0 to 3
	 * @return true if possible, always false if not the players turn
	 */
	public boolean CanBuyDevCard (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		//This is a total message chain but eh
		try 
		{
			return this.GetPlayer(playerID).playerBank.canBuyDevCard();
		} 
		catch (ModelException e) 
		{
			// if the player isn't found
			return false;
		}
	}
	
	/**
	 * Checks if a player has at least one dev card of the type
	 * @param playerID
	 * @param type
	 * @return
	 */
	private boolean PlayerHasADevCard(int playerID, DevCardType type)
	{
		//This is a total message chain but eh
		try 
		{
			int count = this.GetPlayer(playerID).playerBank.getDevCardCount(type);
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
	 * @param playerID
	 * @return
	 */
	public boolean CanUseYearOfPlenty (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.YEAR_OF_PLENTY);
	}
	
	/**
	 * Checks if a player can use the road builder?
	 * @todo figure out what this function does
	 * @param playerID
	 * @return
	 */
	public boolean CanUseRoadBuilder (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.ROAD_BUILD);
	}
	
	/**
	 * Checks if a player can play a solider card
	 * @param playerID
	 * @return
	 */
	public boolean CanUseSoldier (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.SOLDIER);

	}
	
	/**
	 * Checks to see if a player can play a monopoly card
	 * @param playerID
	 * @return
	 */
	public boolean CanUseMonopoly (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.MONOPOLY);
		
	}
	
	/**
	 * Check to see if they have the momument card
	 * @param playerID
	 * @return
	 */
	public boolean CanUseMonument (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.MONUMENT);

	}
	
	/**
	 * Checks to see if a player can place the robber
	 * @param playerID
	 * @return
	 */
	public boolean CanPlaceRobber (int playerID)
	{
		return this.playerCanMoveRobber == playerID;
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
	 * @param playerId 0-3
	 * @param message
	 */
	public void PlayerChat(int playerId, String message)
	{
		waterCooler.put(message, playerId);
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
	 */
	public void FinishTurn()
	{
		gameState.activePlayerIndex++;
		if (gameState.activePlayerIndex > 3)
			gameState.activePlayerIndex = 0;
		gameState.state = GameRound.ROLLING;
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
	 * @param playerId 0 to 3
	 * @return
	 */
	private Player GetPlayer(int playerId) throws ModelException
	{
		if (playerId >= 0 && playerId < 4)
		{
			return this.players.get(playerId);
		}
		else
		{
			throw new ModelException();
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
}
