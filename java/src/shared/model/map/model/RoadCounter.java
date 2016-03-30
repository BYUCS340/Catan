package shared.model.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.model.map.MapException;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Vertex;

public class RoadCounter 
{
	private static final int longestRoadRequiredLength = 5;
	private IMapModel model;
	
	public RoadCounter(IMapModel model)
	{
		this.model = model;
	}
	
	public CatanColor Count()
	{
		try
		{
			RoadComparer lengths = new RoadComparer();
			
			Iterator<Vertex> vList = model.GetVertices();
			while (vList.hasNext())
			{
				Vertex vertex = vList.next();
				
				List<Road> roads = BeginCountRoad(vertex);
				
				lengths.AddRoads(roads);
			}
			
			CatanColor currentLongest = null;
			if (model.LongestRoadExists())
				currentLongest = model.GetLongestRoadColor();
			
			return lengths.GetLongestRoad(currentLongest);
		}
		catch (MapException e)
		{
			//This shouldn't occur.
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Road> BeginCountRoad(Vertex start) throws MapException
	{
		Set<Edge> counted = new HashSet<Edge>();
		List<Road> roads = new ArrayList<Road>(3);
		
		Iterator<Vertex> endPoints = model.GetVertices(start);
		
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			
			if (!edge.doesRoadExists())
				continue;
			
			counted.add(edge);
			Road road = CountRoad(end, counted, edge.getColor()).IncrementLength();
			counted.remove(edge);
			
			roads.add(road);
		}
		
		return roads;
	}
	
	private Road CountRoad(Vertex start, Set<Edge> counted, CatanColor color) throws MapException
	{
		if (start.getType() != PieceType.NONE && start.getColor() != color)
			return new Road(color);
		
		Iterator<Vertex> endPoints = model.GetVertices(start);
		
		List<Road> roads = new ArrayList<Road>(3);
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			
			if (counted.contains(edge) || !edge.doesRoadExists() || edge.getColor() != color)
				continue;
			
			counted.add(edge);	
			Road road = CountRoad(end, counted, edge.getColor()).IncrementLength();
			counted.remove(edge);
			
			roads.add(road);
		}
		
		if (roads.size() > 0)
			return GetLongestRoad(roads);
		else
			return new Road(color);
	}
	
	private Road GetLongestRoad(List<Road> roads)
	{
		Road longestRoad = new Road(null);
		
		for (Road road : roads)
		{
			if (road.GetLength() > longestRoad.GetLength())
				longestRoad = road;
		}
		
		return longestRoad;
	}
	
	private class Road
	{
		private int length;
		private CatanColor color;
		
		public Road(CatanColor color)
		{
			this.length = 0;
			this.color = color;
		}
		
		public Road IncrementLength()
		{
			length++;
			return this;
		}
		
		public int GetLength()
		{
			return length;
		}
		
		public CatanColor GetColor()
		{
			return color;
		}
	}
	
	private class RoadComparer
	{
		private Map<CatanColor, Road> lengths;
		
		public RoadComparer()
		{
			lengths = new HashMap<CatanColor, Road>();
		}
		
		public void AddRoad(Road road)
		{
			if (road == null)
				return;
			
			CatanColor color = road.GetColor();
			if (lengths.containsKey(color))
			{
				if (road.GetLength() > lengths.get(color).GetLength())
				{
					lengths.remove(color);
					lengths.put(color, road);
				}
			}
			else
			{
				lengths.put(color, road);
			}
		}
		
		public void AddRoads(List<Road> roads)
		{
			for (Road road : roads)
				AddRoad(road);
		}
		
		public CatanColor GetLongestRoad(CatanColor currentLongest)
		{
			Road longestRoad = new Road(null);
			
			for(Map.Entry<CatanColor, Road> roads : lengths.entrySet())
			{
				Road road = roads.getValue();
				if (road.GetLength() > longestRoad.GetLength())
					longestRoad = road;
				else if (road.GetLength() == longestRoad.GetLength() && road.GetColor() == currentLongest)
					longestRoad = road;
			}
			
			if (longestRoad.GetLength() >= longestRoadRequiredLength)
				return longestRoad.GetColor();
			else
				return null;
		}
	}
}
