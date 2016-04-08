package server.ai.characters.personalities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;
import server.commands.moves.MovesCommand;
import server.model.GameArcade;
import server.model.GameException;
import server.model.ServerGameManager;
import server.persistence.PersistenceException;
import server.persistence.PersistenceFacade;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.model.Bank;
import shared.model.GameModel;
import shared.model.OfferedTrade;
import shared.model.Player;
import shared.model.map.Coordinate;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Vertex;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;
import shared.networking.parameter.PAcceptTrade;
import shared.networking.parameter.PBuildCity;
import shared.networking.parameter.PBuildRoad;
import shared.networking.parameter.PBuildSettlement;
import shared.networking.parameter.PDiscardCards;
import shared.networking.parameter.PGetModel;
import shared.networking.parameter.PRobPlayer;
import shared.networking.parameter.PRollDice;
import shared.networking.parameter.PSendChat;

/**
 * Personality objects allow AIs to share aspects of their playing style with
 * other AIs. This is the root personality object which allows better functionality.
 * @author Jonathan Sadler
 *
 */
public abstract class Personality 
{
	protected String username;
	protected int id;
	
	/**
	 * Base personality constructor.
	 * @param username The username of the player.
	 */
	public Personality(String username) 
	{ 
		this.username = username;
	}
	
	/**
	 * The ID associated with the AI.
	 * @param id
	 */
	public void SetID(int id)
	{
		this.id = id;
	}
	
	protected GameModel GetModel(int game)
	{
		StringBuilder param = new StringBuilder("GAME/MODEL");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PGetModel model = new PGetModel(0);
		String object = SerializationUtils.serialize(model);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	/**
	 * Rolls the dice with a random number (2-12)
	 * @param game the game ID
	 * @return the model of the game
	 */
	protected GameModel RollDice(int game)
	{
		Random randomGen = new Random();
		int diceRoll = randomGen.nextInt(5) + randomGen.nextInt(5) + 2;
		return RollDice(game, diceRoll);
	}
	
	/**
	 * Rolls the dice at a specific number
	 * @param game the game ID
	 * @param roll
	 * @return the new model of the game
	 */
	protected GameModel RollDice(int game, int roll)
	{
		StringBuilder param = new StringBuilder("MOVES/ROLLNUMBER");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PRollDice myroll = new PRollDice(roll);
		String object = SerializationUtils.serialize(myroll);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel Rob(int game, int victimIndex, Coordinate hex)
	{
		StringBuilder param = new StringBuilder("MOVES/ROBPLAYER");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PRobPlayer rob = new PRobPlayer(victimIndex, hex);
		String object = SerializationUtils.serialize(rob);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel BuildCity(int game, Coordinate point)
	{
		StringBuilder param = new StringBuilder("MOVES/BUILDCITY");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PBuildCity city = new PBuildCity(point);
		String object = SerializationUtils.serialize(city);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel BuildRoad(int game, Coordinate start, Coordinate end, boolean free)
	{
		StringBuilder param = new StringBuilder("MOVES/BUILDROAD");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PBuildRoad road = new PBuildRoad(start, end, free);
		String object = SerializationUtils.serialize(road);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel BuildSettlement(int game, Coordinate point, boolean free)
	{
		StringBuilder param = new StringBuilder("MOVES/BUILDSETTLEMENT");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PBuildSettlement settlement = new PBuildSettlement(point, free);
		String object = SerializationUtils.serialize(settlement);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel BuyDevCard(int game)
	{
		StringBuilder param = new StringBuilder("MOVES/BUYDEVCARD");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		return CommandExecutor(param, cookie, GameModel.class);
	}
	
	/**
	 * Sends a chat 
	 * @param game
	 * @param message
	 * @return the game model
	 */
	protected GameModel SendChat(int game, String message)
	{
		StringBuilder param = new StringBuilder("MOVES/SENDCHAT");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PSendChat bottle = new PSendChat(message);
		String object = SerializationUtils.serialize(bottle);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel Discard(int game, List<Integer> resourceList)
	{
		StringBuilder param = new StringBuilder("MOVES/DISCARDCARDS");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PDiscardCards discard = new PDiscardCards(resourceList);
		String object = SerializationUtils.serialize(discard);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected GameModel AcceptTrade(int game, boolean willAccept)
	{
		StringBuilder param = new StringBuilder("MOVES/ACCEPTTRADE");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		PAcceptTrade accept = new PAcceptTrade(willAccept);
		String object = SerializationUtils.serialize(accept);
		
		return CommandExecutor(param, cookie, object, GameModel.class);
	}
	
	protected void FinishTurn(int game)
	{
		StringBuilder param = new StringBuilder("MOVES/FINISHTURN");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		CommandExecutor(param, cookie);
	}
	
	protected Player GetAIPlayer(GameModel model)
	{
		for (int i = 0; i < model.players.size(); i++)
		{
			if (model.players.get(i).playerID() == id)
				return model.players.get(i);
		}
		
		assert false;
		return null;
	}
	
	protected CatanColor GetColor(GameModel model)
	{
		return GetAIPlayer(model).color;
	}
	
	protected Bank GetBank(GameModel model)
	{
		return GetAIPlayer(model).playerBank;
	}
	
	protected List<Vertex> GetAvailableVertices(GameModel model)
	{
		CatanColor color = GetColor(model);
		
		List<Vertex> available = new ArrayList<Vertex>();
		Iterator<Vertex> vertices = model.mapModel.GetVertices();
		while (vertices.hasNext())
		{
			Vertex vertex = vertices.next();
			if (model.mapModel.CanPlaceSettlement(vertex.getPoint(), color))
				available.add(vertex);
		}
		
		return available;
	}
	
	protected List<Edge> GetAvailableEdges(GameModel model)
	{
		CatanColor color = GetColor(model);
		
		List<Edge> available = new ArrayList<Edge>();
		Iterator<Edge> edges = model.mapModel.GetEdges();
		while (edges.hasNext())
		{
			Edge edge = edges.next();
			if (model.mapModel.CanPlaceRoad(edge.getStart(), edge.getEnd(), color))
				available.add(edge);
		}
		
		return available;
	}
	
	protected List<Vertex> GetSettlements(GameModel model)
	{
		CatanColor color = GetColor(model);
		
		List<Vertex> settlements = new ArrayList<Vertex>();
		Iterator<Vertex> vertices = model.mapModel.GetVertices();
		while (vertices.hasNext())
		{
			Vertex vertex = vertices.next();
			
			if (vertex.getType() == PieceType.SETTLEMENT && vertex.getColor() == color)
				settlements.add(vertex);
		}
		
		return settlements;
	}
	
	protected int GetIndexByColor(GameModel model, CatanColor color)
	{
		for (Player player : model.players)
		{
			if (player.color == color)
				return player.playerIndex();
		}
		
		return -1;
	}
	
	private NetworkCookie GetCookie(String username, int id, int game)
	{
		NetworkCookie cookie = new NetworkCookie(username, null, id);
		cookie.setGameID(game);
		return cookie;
	}
	
	private String CommandExecutor(StringBuilder param, NetworkCookie cookie)
	{
		return CommandExecutor(param, cookie, "");
	}
	
	private String CommandExecutor(StringBuilder param, NetworkCookie cookie, String object)
	{
		try
		{
			ICommand command = CommandFactory.GetCommandFactory().GetCommand(param, cookie, object);
			if (command.Execute())
			{
				HandlePersistence(command);
				return command.GetResponse();
			}
			return null;
		}
		catch (InvalidFactoryParameterException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private <T extends Serializable> T CommandExecutor(StringBuilder param, NetworkCookie cookie, java.lang.Class<T> objClass)
	{
		return CommandExecutor(param, cookie, null, objClass);
	}
	
	private <T extends Serializable> T CommandExecutor(StringBuilder param, NetworkCookie cookie, String object, java.lang.Class<T> objClass)
	{
		String response = CommandExecutor(param, cookie, object);
		
		if (response == null) return null;
		
		return SerializationUtils.deserialize(response, objClass);
	}
	
	private void HandlePersistence(ICommand command)
	{
		try
		{
			if (MovesCommand.class.isAssignableFrom(command.getClass()))
			{
				PersistenceFacade facade = PersistenceFacade.GetPersistence();
				
				MovesCommand move = (MovesCommand)command;
				int gameID = move.GetGameID();
				
				if (!facade.AddCommand(gameID, command))
				{
					ServerGameManager sgm = GameArcade.games().GetGame(gameID);
					facade.UpdateGame(sgm);
				}
			}
		}
		catch (PersistenceException | GameException e)
		{
			e.printStackTrace();
		}
	}
	
	public abstract void TakeTurn(int gameID);
	
	public abstract void Discard(int gameID);
	
	public abstract void ChatReceived(int gameID, String message);
	
	public abstract void ReceivedOffer(int gameID, OfferedTrade trade);
	
	protected abstract void Setup(GameModel model);
	
	protected abstract void Play(GameModel model);

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Personality))
			return false;
		Personality other = (Personality) obj;
		if (id != other.id)
			return false;
		if (username == null) 
		{
			if (other.username != null)
				return false;
		} 
		else if (!username.equals(other.username))
			return false;
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Personality [" + (username != null ? "username=" + username + ", " : "") + "id=" + id + "]";
	}

	
}
