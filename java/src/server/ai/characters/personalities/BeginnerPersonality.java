package server.ai.characters.personalities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.map.Coordinate;
import shared.model.map.objects.Edge;
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
		GameModel model = GetModel(gameID);
		
		if (model.gameState.IsSetup())
			Setup(model);
		else
			Play(model);
		
		FinishTurn(gameID);
	}
	
	@Override
	public void Discard(int gameID) 
	{
		GameModel model = GetModel(gameID);
		
		int count = model.gameBank.getResourceCount();
		if (count <= 7)
			return;
		
		int toRemove = (int)Math.floor(count / 2.0);
		int selected = 0;
		
		List<Integer> currentResources = new ArrayList<Integer>();
		List<Integer> selectedResources = new ArrayList<Integer>();
		
		for (ResourceType resource : ResourceType.values())
			currentResources.add(model.gameBank.getResourceCount(resource));
		
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
		if (model.gameBank.canBuildCity())
			AttemptBuildCity(model);
		if (model.gameBank.canBuildSettlement())
			AttemptBuildSettlement(model);
		if (model.gameBank.canBuildRoad())
			AttemptBuildRoad(model);
		if (model.gameBank.canBuyDevCard())
			AttemptBuyDevCard(model);
	}
	
	private void AttemptBuildCity(GameModel model)
	{
		Iterator<Vertex> settlements = GetSettlements(model).iterator();
		
		while (settlements.hasNext() && model.gameBank.canBuildCity())
		{
			Vertex vertex = settlements.next();
			
			model = BuildCity(model.gameID, vertex.getPoint());
		}
	}
	
	private void AttemptBuildSettlement(GameModel model)
	{
		final boolean free = false;
		
		Iterator<Vertex> available = GetAvailableVertices(model).iterator();
		
		while (available.hasNext() && model.gameBank.canBuildSettlement())
		{
			Vertex vertex = available.next();
			
			model = BuildSettlement(model.gameID, vertex.getPoint(), free);
		}
	}
	
	private void AttemptBuildRoad(GameModel model)
	{
		final boolean free = false;
		
		Iterator<Edge> available = GetAvailableEdges(model).iterator();
		
		while (available.hasNext() && model.gameBank.canBuildRoad())
		{
			Edge edge = available.next();
			
			model = BuildRoad(model.gameID, edge.getStart(), edge.getEnd(), free);
		}
	}
	
	private void AttemptBuyDevCard(GameModel model)
	{
		while (model.gameBank.canBuyDevCard())
			BuyDevCard(model.gameID);
	}
}
