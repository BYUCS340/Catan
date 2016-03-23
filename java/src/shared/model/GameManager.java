package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import shared.data.DataTranslator;
import shared.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.model.map.*;
import shared.model.map.model.MapGenerator;
import shared.model.map.model.MapModel;
import shared.model.chat.ChatBox;

/**
 * The game manager class acts as a facade between the player/game objects and the ServerProxy/UI
 * @author matthewcarlson, garrettegan
 *
 */
public class GameManager implements ModelSubject
{
	protected int gameID;
	protected String gameTitle;
	protected GameState gameState;
	protected Bank gameBank;
	protected List<Player> players;
	protected VictoryPointManager victoryPointManager;
	protected ChatBox waterCooler;
	protected GameActionLog log;
	protected MapModel map;
	protected int version;
	private int[] playerColors;
	private int playerCanMoveRobber;
	protected NotificationCenter notifyCenter;
	
	
	/**
	 * Constructor for the game manager
	 * @post all players
	 */
	public GameManager()
	{
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
		victoryPointManager = new VictoryPointManager();
		notifyCenter = new NotificationCenter();
		playerColors = new int[10];
		//fill the array with -1 by default
		Arrays.fill(playerColors,-1);
		playerCanMoveRobber = -1;
		gameBank.resetToBankDefaults();
		
		//Create map and fill with default data.
		map = MapGenerator.BeginnerMap();
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
		map = MapGenerator.BeginnerMap();
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
	 * Adds a players to the game
	 * @param name The name of the player
	 * @param color The piece color
	 * @param isHuman True if human, else AI
	 * @param playerID The id of the player
	 * @return the player index
	 * @throws ModelException Thrown if the player can't be added
	 */
	public int AddPlayer(String name, CatanColor color, boolean isHuman, int playerID) throws ModelException
	{
		//check if that color has already been used
		if (playerColors[color.ordinal()] != -1)
			throw new ModelException();
		
		int newIndex = players.size();
		if (newIndex > 3 || newIndex < 0)
		{
			throw new ModelException("Too many players already to add another");
		}
		Player newPlayer = new Player(name, newIndex, color, isHuman, playerID);
		players.add(newPlayer);
		
		playerColors[color.ordinal()] = newIndex;
		return newIndex;
	}
	
	/**
	 * Adds a player to the game
	 * @param name
	 * @return the player index number 
	 * @throws ModelException if there are too many players already in the game or if that color has been used
	 */
	public int AddPlayer(String name, CatanColor color, boolean isHuman) throws ModelException
	{
		return this.AddPlayer(name, color, isHuman, -1);
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
			if (p == null){
				System.err.println("Player at "+i+" is null");
				continue;
			}
			//System.out.println(p);
			this.players.add(p);
			playerColors[p.color.ordinal()] = p.playerIndex();
		}
		
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
	 *
	 * @return
	 */
	public PlayerInfo[] allCurrentPlayers()
	{
		PlayerInfo[] allplayers = new PlayerInfo[this.players.size()];
		for (int i=0; i< this.players.size(); i++)
		{
			allplayers[i] = DataTranslator.convertPlayerInfo(players.get(i));
		}

		return allplayers;
	}

	/**
	 *
	 * @param playerIndex
	 */
	public String getPlayerNameByIndex(int playerIndex)
	{
		if(playerIndex > 3 || playerIndex < 0)
			return null;

		return players.get(playerIndex).name;
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
		if (playerIndex > players.size() || playerIndex < 0)
		{
			System.out.println("UNKNOWN INDEX"+playerIndex);
			return null;
		}
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
	 * Gets the number of pieces for the current player
	 * @param playerIndex
	 * @param type
	 * @return
	 */
	public int playerPieceCount(int playerIndex,PieceType type)
	{
		try
		{
			return this.players.get(playerIndex).playerBank.getPieceCount(type);
		} 
		catch (ModelException e)
		{
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
		Random randomGen = new Random();
		int diceRoll = randomGen.nextInt(5) + randomGen.nextInt(5) + 2;
		DiceRoll(diceRoll);
		return diceRoll;
	}
	
	/**
	 * Handles the roll of a die
	 * @param roll
	 * @throws ModelException 
	 */
	protected void DiceRoll(int diceRoll) throws ModelException{
		
		//check if we can move the robber
		if (diceRoll < 2 || diceRoll > 12 ) 
			throw new ModelException("Bad Dice Value");
		
		if (diceRoll == 7 )
		{
			this.playerCanMoveRobber = this.CurrentPlayersTurn();
			if (!gameState.startRobbing()) throw new ModelException("Unable to stop rolling after 7");
		}
		else
		{
			if (!gameState.stopRolling()) throw new ModelException("Unable to stop rolling after a non 7");
		}
		log.logAction(this.CurrentPlayersTurn(), "rolled a "+diceRoll);
		
		//Call map to update the get the transactions
		Iterator<Transaction> transList = map.GetTransactions(diceRoll);
		//Go through each transaction
		while (transList.hasNext())
		{
			Transaction trans = transList.next();
			//Get the player ID
			int playerIndex = this.getPlayerIndexByColor(trans.getColor());
			try 
			{
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
			} 
			catch (ModelException e) 
			{
				System.err.println("No player with the color specified by transaction found");
				System.err.println(trans.getColor());
			}
		}
		
	}
	
	
	//--------------------------------------------------------------------------
	//Player Turn Game methods
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildRoad(int playerIndex, Coordinate start, Coordinate end, boolean free) throws ModelException
	{
		try
		{
			if (!free)
			{
				//check to see if player has resources
				if (!this.CanBuildRoad(playerIndex, start,end))
					throw new ModelException();
				GetPlayer(playerIndex).playerBank.buildRoad();
			}
			CatanColor color = this.getPlayerColorByIndex(playerIndex);
			map.PlaceRoad(start, end, color);
			victoryPointManager.playerBuiltRoad(playerIndex);
		}
		catch (MapException e)
		{
			throw new ModelException("Can't place road.", e);
		}
	}
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildSettlement(int playerIndex, Coordinate location, boolean free) throws ModelException
	{
		try
		{
			if (!free)
			{
				//check to see if player has resources
				if (!this.CanBuildSettlement(playerIndex, location))
					throw new ModelException();
				GetPlayer(playerIndex).playerBank.buildSettlement();
			}
			CatanColor color = this.getPlayerColorByIndex(playerIndex);
			map.PlaceSettlement(location, color);
			victoryPointManager.playerBuiltSettlement(playerIndex);
		}
		catch (MapException e)
		{
			throw new ModelException("Can't place settlement.", e);
		}
	}
	
	/**
	 * Attempts to build a road
	 * @param playerIndex
	 * @throws ModelException if the player doesn't have the resources
	 * @throws MapException if the Location doesn't exist
	 */
	public void BuildCity(int playerIndex,Coordinate location) throws ModelException
	{
		try
		{
			//check to see if player has resources
			if (!this.CanBuildCity(playerIndex, location))
				throw new ModelException("Cannot place city");
			
			GetPlayer(playerIndex).playerBank.buildRoad();
			CatanColor color = this.getPlayerColorByIndex(playerIndex);
			map.PlaceCity(location,color);
			victoryPointManager.playerBuiltCity(playerIndex);
		}
		catch (MapException e)
		{
			throw new ModelException("Can't place city.", e);
		}
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
		CatanColor color = this.getPlayerColorByIndex(playerIndex);
		
		Iterator<PortType> iter = map.GetPorts(color);
		
		int lowestRatio = 4;
		
		while (iter.hasNext())
		{
			PortType port = iter.next();
			if (port == PortType.THREE && lowestRatio > 3) 
				lowestRatio = 3;
			else if (PortType.MatchedResource(port, type))
				lowestRatio = 2;
		}
		
		//Otherwise return the default trade in value
		return lowestRatio;
	}
	
	/**
	 * Places the robber
	 * @param playerIndex
	 * @param location
	 * @throws ModelException 
	 */
	public void placeRobber(int playerIndex) throws ModelException
	{
		if (!this.CanPlaceRobber(playerIndex))
			throw new ModelException("Player can't place robber right now");
		//mark that the robber has been moved
		this.playerCanMoveRobber = -1;
		
		if (!gameState.stopRobbing())
			throw new ModelException("Can't stop robbing.");
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
			//System.out.println("Cannot play since the current player is "+this.gameState.activePlayerIndex);
			return false;
		}
		else if (this.gameState.state != GameRound.FIRSTROUND && this.gameState.state != GameRound.SECONDROUND && this.gameState.state != GameRound.PLAYING)
		{
			//System.out.println("Player cannot play because we are in the "+this.gameState.state);
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
		return this.CanBuildRoad(playerIndex);
	}
	
	/**
	 * Just checks if a player can build a road
	 * @param playerIndex
	 * @return
	 */
	public boolean CanBuildRoad(int playerIndex)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			if (!GetPlayer(playerIndex).playerBank.canBuildRoad())
				return false;
			//Map has already been checked by map
		}
		catch (ModelException e)
		{
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
		return this.CanBuildSettlement(playerIndex);
	}
	
	/**
	 * Checks if a player can build the settlement
	 * @param playerIndex
	 * @return
	 */
	public boolean CanBuildSettlement(int playerIndex)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			Player player = GetPlayer(playerIndex);
			
			//check if they have the resources needed
			if (!player.playerBank.canBuildSettlement())
				return false;
			
			//Map has been/will be checked by map
		}
		catch (ModelException e)
		{
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
		//ask the map
		if (!map.CanPlaceCity(location,this.getPlayerColorByIndex(playerIndex)))
			return false;
		return this.CanBuildCity(playerIndex);
	}
	
	/**
	 * Checks if a player can build a city
	 * @param playerIndex
	 * @return
	 */
	public boolean CanBuildCity (int playerIndex)
	{
		if (!CanPlayerPlay(playerIndex))
			return false;
		try 
		{
			if (!GetPlayer(playerIndex).playerBank.canBuildCity())
				return false;
			
		}
		catch (ModelException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param playerIndex
	 * @return
	 */
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
			try
			{
				players.get(i).playerBank.giveResource(ResourceType.BRICK, 5);
				players.get(i).playerBank.giveResource(ResourceType.ORE, 5);
				players.get(i).playerBank.giveResource(ResourceType.SHEEP, 5);
				players.get(i).playerBank.giveResource(ResourceType.WOOD, 5);
				players.get(i).playerBank.giveResource(ResourceType.WHEAT, 5);
				players.get(i).playerBank.givePiece(PieceType.ROAD,2);
				players.get(i).playerBank.givePiece(PieceType.SETTLEMENT,2);
			} 
			catch (ModelException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Can do method for determining whether a player can maritime trade
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
	 * @return true if we aren't waiting for players
	 */
	public boolean canChat(int playerIndex)
	{
		if (gameState.state == GameRound.WAITING) 
			return false;
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
		if (!this.CanPlayerPlay(playerIndex) || !this.PlayerHasADevCard(playerIndex, DevCardType.ROAD_BUILD))
			return false;
		
		return this.playerPieceCount(playerIndex, PieceType.ROAD) >= 2;
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
		notifyCenter.notify(ModelNotification.CHAT);
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
	 * Returns the game's title
	 * @return
	 */
	public String GetGameTitle()
	{
		return this.gameTitle;
	}
	
	/**
	 * Returns the ID of the game
	 * @return
	 */
	public int GetGameID()
	{
		return this.gameID;
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

	/**
	 * Returns the number of the requested type of resource held by the bank
	 * @param resourceType
	 * @return
	 */
	public int getBankResourceCount(ResourceType resourceType){
		return gameBank.getResourceCount(resourceType);
	}
	
//	public void initializeBankTempTesting(){
//		try {
//			gameBank.giveResource(ResourceType.BRICK, 60);
//			gameBank.giveResource(ResourceType.SHEEP, 60);
//			gameBank.giveResource(ResourceType.WHEAT, 60);
//			gameBank.giveResource(ResourceType.ORE, 60);
//			gameBank.giveResource(ResourceType.WOOD, 60);
//
//		} catch (ModelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ResourceType type = ResourceType.BRICK;
//		System.out.println("The bank has" + gameBank.getResourceCount(type) + " of " + type);
//		type = ResourceType.SHEEP;
//		System.out.println("The bank has" + gameBank.getResourceCount(type) + " of " + type);
//		 type = ResourceType.WHEAT;
//		System.out.println("The bank has" + gameBank.getResourceCount(type) + " of " + type);
//		 type = ResourceType.ORE;
//		System.out.println("The bank has" + gameBank.getResourceCount(type) + " of " + type);
//		 type = ResourceType.WOOD;
//		System.out.println("The bank has" + gameBank.getResourceCount(type) + " of " + type);
//		
//
//	}

	
	
}
