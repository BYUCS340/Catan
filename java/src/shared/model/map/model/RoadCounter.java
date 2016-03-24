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
			Set<Edge> counted = new HashSet<Edge>();
			RoadComparer lengths = new RoadComparer();
			
			Iterator<Vertex> vList = model.GetVertices();
			while (vList.hasNext())
			{
				Vertex vertex = vList.next();
				
				Road road = BeginCountIntersection(vertex, counted);
				
				lengths.AddRoad(road);
			}
			
			vList = model.GetVertices();
			while (vList.hasNext())
			{
				Vertex vertex = vList.next();
				
				List<Road> roads = BeginCountRoad(vertex, counted);
				
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
	
	private Road BeginCountIntersection(Vertex start, Set<Edge> counted) throws MapException
	{
		CatanColor color = null;
		
		//Verify the vertex is an intersection.
		int count = 0;
		Iterator<Vertex> endPoints = model.GetVertices(start);
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			count++;
			
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			if (!edge.doesRoadExists())
				return null;
			
			if (color == null)
				color = edge.getColor();
			else if (color != edge.getColor())
				return null;
		}
		
		if (count != 3)
			return null;
		
		//Count the road as it is an intersection.
		Set<Edge> path = new HashSet<Edge>();
		return CountIntersection(start, path, counted, color);
	}
	
	private Road CountIntersection(Vertex start, Set<Edge> path, Set<Edge> counted, CatanColor color) throws MapException
	{
		if (start.getType() != PieceType.NONE && start.getColor() != color)
			return new Road(color);
		
		Iterator<Vertex> endPoints = model.GetVertices(start);
		
		List<Road> roads = new ArrayList<Road>();
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			
			//If the edge has already been counted on the current path, continue.
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			if (!edge.doesRoadExists() || edge.getColor() != color || path.contains(edge))
				continue;
			
			counted.add(edge);
			path.add(edge);
			Road road = CountIntersection(end, path, counted, color).IncrementLength();
			roads.add(road);
			path.remove(edge);
		}
		
		if (roads.size() > 0)
			return GetLongestRoad(roads);
		else
			return new Road(color);
	}
	
	private List<Road> BeginCountRoad(Vertex start, Set<Edge> counted) throws MapException
	{
		List<Road> roads = new ArrayList<Road>();
		
		Iterator<Vertex> endPoints = model.GetVertices(start);
		
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			if (counted.contains(edge) || !edge.doesRoadExists())
				continue;
			
			counted.add(edge);
			
			Road road = CountRoad(end, counted, edge.getColor()).IncrementLength();
			roads.add(road);
		}
		
		return MergeRoads(roads);
	}
	
	private Road CountRoad(Vertex start, Set<Edge> counted, CatanColor color) throws MapException
	{
		if (start.getType() != PieceType.NONE && start.getColor() != color)
			return new Road(color);
		
		Iterator<Vertex> endPoints = model.GetVertices(start);
		
		while (endPoints.hasNext())
		{
			Vertex end = endPoints.next();
			
			Edge edge = model.GetEdge(start.getPoint(), end.getPoint());
			
			if (counted.contains(edge) || !edge.doesRoadExists() || edge.getColor() != color)
				continue;
			
			counted.add(edge);
			
			Road road = CountRoad(end, counted, edge.getColor()).IncrementLength();
			
			return road;
		}
		
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
	
	private List<Road> MergeRoads(Road a, Road b)
	{
		List<Road> result = new ArrayList<Road>();
		
		if (a.GetColor() == b.GetColor())
		{
			int length = a.GetLength() + b.GetLength();
			CatanColor color = a.GetColor();
			
			result.add(new Road(color, length));
		}
		else
		{
			result.add(a);
			result.add(b);
		}
		
		return result;
	}
	
	private List<Road> MergeRoads(Road a, Road b, Road c)
	{
		List<Road> result;
		
		if (a.GetColor() == b.GetColor())
		{
			result = MergeRoads(a, b);
			result.add(c);
		}
		else if (a.GetColor() == c.GetColor())
		{
			result = MergeRoads(a, c);
			result.add(b);
		}
		else if (b.GetColor() == c.GetColor())
		{
			result = MergeRoads(b, c);
			result.add(a);
		}
		else
		{
			result = new ArrayList<Road>();
			result.add(a);
			result.add(b);
			result.add(c);
		}
		
		return result;
	}
	
	private List<Road> MergeRoads(List<Road> roads)
	{
		if (roads.size() == 2)
			return MergeRoads(roads.get(0), roads.get(1));
		else if (roads.size() == 3)
			return MergeRoads(roads.get(0), roads.get(1), roads.get(2));
		else
			return roads;
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
		
		public Road(CatanColor color, int length)
		{
			this.length = length;
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
