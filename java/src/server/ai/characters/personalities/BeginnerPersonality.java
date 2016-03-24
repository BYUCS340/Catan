package server.ai.characters.personalities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import server.Log;
import shared.definitions.CatanColor;
import shared.definitions.GameRound;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.GameModel;
import shared.model.map.Coordinate;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class BeginnerPersonality extends Personality 
{
	public BeginnerPersonality(String username) 
	{
		super(username);
	}

	@Override
	public void TakeTurn(int gameID)
	{
		try
		{
			GameModel model = GetModel(gameID);
			
			if (model.gameState.IsSetup())
				Setup(model);
			else
			{
				if (model.gameState.state == GameRound.ROLLING)
					model = this.RollDice(gameID);
				
				//If we roll a 7
				if (model.gameState.state == GameRound.DISCARDING)
				{
					//Discard if necessary.
					Discard(gameID);
					
					do
					{
						Thread.sleep(3000);
						model = GetModel(gameID);
					} while (model.gameState.state == GameRound.DISCARDING);
				}
				
				//Check if we are robbing
				if (model.gameState.state == GameRound.ROBBING)
					Rob(model);
				else
					Play(model);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			Log.GetLog().throwing("BeginnerPersonality", "TakeTurn", e);
		}
		
		FinishTurn(gameID);
	}
	
	@Override
	public void Discard(int gameID) 
	{
		GameModel model = GetModel(gameID);
		
		int count = GetBank(model).getResourceCount();
		if (count <= 7)
			return;
		
		int toRemove = (int)Math.floor(count / 2.0);
		int selected = 0;
		
		List<Integer> currentResources = new ArrayList<Integer>();
		List<Integer> selectedResources = new ArrayList<Integer>();
		
		for (ResourceType resource : ResourceType.values())
			currentResources.add(GetBank(model).getResourceCount(resource));
		
		int index = 0;
		while (selected < toRemove)
		{
			if (currentResources.get(index) > 0)
			{
				currentResources.set(index, currentResources.get(index) - 1);
				selectedResources.set(index, selectedResources.get(index) + 1);
			}
			
			index++;
			if (index >= currentResources.size())
				index = 0;
		}
		
		Discard(model.gameID, selectedResources);
	}

	@Override
	public void ChatReceived(int gameID, String message) 
	{
		return;
	}

	@Override
	protected void Setup(GameModel model) 
	{
		final boolean free = true;
		
		List<Vertex> available = GetAvailableVertices(model);
		
		Random random = new Random();
		int vertex = random.nextInt(available.size());
		
		int gameID = model.gameID;
		Vertex selectedVertex = available.get(vertex);
		Coordinate mainVertex = selectedVertex.getPoint();
		model = BuildSettlement(gameID, mainVertex, free);
		
		List<Vertex> availableRoadEnd = new ArrayList<Vertex>();
		Iterator<Vertex> roads = model.mapModel.GetVertices(selectedVertex);
		while (roads.hasNext())
			availableRoadEnd.add(roads.next());
		
		int road = random.nextInt(availableRoadEnd.size());
		
		Coordinate endVertex = availableRoadEnd.get(road).getPoint();
		model = BuildRoad(gameID, mainVertex, endVertex, free);
	}
	
	

	@Override
	protected void Play(GameModel model) 
	{
		
		Bank bank = GetBank(model);
		
		if (bank.canBuildCity())
			AttemptBuildCity(model);
		if (bank.canBuildSettlement())
			AttemptBuildSettlement(model);
		if (bank.canBuildRoad())
			AttemptBuildRoad(model);
		if (bank.canBuyDevCard())
			AttemptBuyDevCard(model);
	}
	
	private void AttemptBuildCity(GameModel model)
	{
		Iterator<Vertex> settlements = GetSettlements(model).iterator();
		
		while (settlements.hasNext() && model != null && GetBank(model).canBuildCity())
		{
			Vertex vertex = settlements.next();
			
			model = BuildCity(model.gameID, vertex.getPoint());
		}
	}
	
	private void AttemptBuildSettlement(GameModel model)
	{
		final boolean free = false;
		
		Iterator<Vertex> available = GetAvailableVertices(model).iterator();
		
		while (available.hasNext() && model != null && GetBank(model).canBuildSettlement())
		{
			Vertex vertex = available.next();
			
			model = BuildSettlement(model.gameID, vertex.getPoint(), free);
		}
	}
	
	private void AttemptBuildRoad(GameModel model)
	{
		final boolean free = false;
		
		Iterator<Edge> available = GetAvailableEdges(model).iterator();
		
		while (available.hasNext() && model != null && GetBank(model).canBuildRoad())
		{
			Edge edge = available.next();
			
			model = BuildRoad(model.gameID, edge.getStart(), edge.getEnd(), free);
		}
	}
	
	private void AttemptBuyDevCard(GameModel model)
	{
		while (model != null && GetBank(model).canBuyDevCard())
			model = BuyDevCard(model.gameID);
	}
	
	private void Rob(GameModel model)
	{
		Hex current = model.mapModel.GetRobberLocation();
		
		Iterator<Hex> hexes = model.mapModel.GetHexes();
		
		List<Hex> options = new ArrayList<Hex>();
		while (hexes.hasNext())
		{
			Hex hex = hexes.next();
			
			if (hex.getType() == HexType.WATER || hex.equals(current))
				continue;
			
			options.add(hex);
		}
		
		Random random = new Random();
		int selected = random.nextInt(options.size());
		
		Hex robber = options.get(selected);
		
		CatanColor aiColor = GetColor(model);
		Set<Integer> robOptions = new HashSet<Integer>();
		Iterator<CatanColor> colors = model.mapModel.GetOccupiedVertices(robber.getPoint());
		while (colors.hasNext())
		{
			CatanColor color = colors.next();
			
			if (color == aiColor)
				continue;
			
			int index = GetIndexByColor(model, color);
			
			robOptions.add(index);
		}
		
		int toRob = -1;
		if (robOptions.size() > 0)
		{
			selected = random.nextInt(robOptions.size());
			Integer[] pickToRob = robOptions.toArray(new Integer[0]);
			
			toRob = pickToRob[selected];
		}
		
		Rob(model.gameID, toRob, robber.getPoint());
	}
}
