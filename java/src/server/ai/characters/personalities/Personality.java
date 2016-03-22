package server.ai.characters.personalities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.commands.InvalidFactoryParameterException;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.model.GameModel;
import shared.model.map.Coordinate;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Vertex;
import shared.networking.SerializationUtils;
import shared.networking.cookie.NetworkCookie;
import shared.networking.parameter.PBuildCity;
import shared.networking.parameter.PBuildRoad;
import shared.networking.parameter.PBuildSettlement;

public abstract class Personality 
{
	protected String username;
	protected int id;
	
	public Personality(String username) 
	{ 
		this.username = username;
	}
	
	public void SetID(int id)
	{
		this.id = id;
	}
	
	protected GameModel GetModel(int game)
	{
		StringBuilder param = new StringBuilder("GAME/MODEL");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		return CommandExecutor(param, cookie, null, GameModel.class);
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
	
	protected void FinishTurn(int game)
	{
		StringBuilder param = new StringBuilder("MOVES/FINISHTURN");
		NetworkCookie cookie = GetCookie(username, id, game);
		
		CommandExecutor(param, cookie);
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
	
	protected CatanColor GetColor(GameModel model)
	{
		for (int i = 0; i < model.players.size(); i++)
		{
			if (model.players.get(i).playerID() == id)
				return model.players.get(i).color;
		}
		
		assert false;
		return null;
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
	
	private NetworkCookie GetCookie(String username, int id)
	{
		return GetCookie(username, id, -1);
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
			command.Execute();
			return command.GetResponse();
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
		
		return SerializationUtils.deserialize(response, objClass);
	}
	
	public abstract void TakeTurn(int gameID);
	
	protected abstract void Setup(GameModel model);
	
	protected abstract void Play(GameModel model);
}
