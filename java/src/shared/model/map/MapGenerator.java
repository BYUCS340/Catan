package shared.model.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Robber;
import shared.model.map.objects.Vertex;

public class MapGenerator {

	public static void BeginnerMap(MapModel model)
	{
		BeginnerLandSetup(model);
		PlaceWater(model);
		PlacePorts(model);
		PlacePips(model);
	}
	
	private void RandomSetup()
	{
		//Todo Setup
	}
	
	private static void BeginnerLandSetup(MapModel model)
	{
		try
		{
			model.SetHex(HexType.ORE, new Coordinate(1, -2));
			model.SetHex(HexType.SHEEP, new Coordinate(1, 0));
			model.SetHex(HexType.WOOD, new Coordinate(1, 2));
			
			model.SetHex(HexType.WHEAT, new Coordinate(2, -3));
			model.SetHex(HexType.BRICK, new Coordinate(2, -1));
			model.SetHex(HexType.SHEEP, new Coordinate(2, 1));
			model.SetHex(HexType.BRICK, new Coordinate(2, 3));
			
			model.SetHex(HexType.WHEAT, new Coordinate(3, -4));
			model.SetHex(HexType.WOOD, new Coordinate(3, -2));
			model.SetHex(HexType.DESERT, new Coordinate(3, 0));
			model.SetHex(HexType.WOOD, new Coordinate(3, 2));
			model.SetHex(HexType.ORE, new Coordinate(3, 4));
			
			model.SetHex(HexType.WOOD, new Coordinate(4, -3));
			model.SetHex(HexType.ORE, new Coordinate(4, -1));
			model.SetHex(HexType.WHEAT, new Coordinate(4, 1));
			model.SetHex(HexType.SHEEP, new Coordinate(4, 3));
			
			model.SetHex(HexType.BRICK, new Coordinate(5, -2));
			model.SetHex(HexType.WHEAT, new Coordinate(5, 0));
			model.SetHex(HexType.SHEEP, new Coordinate(5, 2));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. Otherwise, we just suck.
		}
		
		try {
			model.SetRobber(model.GetHex(new Coordinate(3,0)));
		}
		catch (MapException e) {
			e.printStackTrace();
			//This shouldn't happen either.
		}
	}
	
	private static void PlaceWater(MapModel model)
	{
		try
		{
			model.SetHex(HexType.WATER, new Coordinate(0, -1));
			model.SetHex(HexType.WATER, new Coordinate(0, -3));
			model.SetHex(HexType.WATER, new Coordinate(1, -4));
			model.SetHex(HexType.WATER, new Coordinate(2, -5));
			model.SetHex(HexType.WATER, new Coordinate(3, -6));
			model.SetHex(HexType.WATER, new Coordinate(4, -5));
			model.SetHex(HexType.WATER, new Coordinate(5, -4));
			model.SetHex(HexType.WATER, new Coordinate(6, -3));
			model.SetHex(HexType.WATER, new Coordinate(6, -1));
			model.SetHex(HexType.WATER, new Coordinate(6, 1));
			model.SetHex(HexType.WATER, new Coordinate(6, 3));
			model.SetHex(HexType.WATER, new Coordinate(5, 4));
			model.SetHex(HexType.WATER, new Coordinate(4, 5));
			model.SetHex(HexType.WATER, new Coordinate(3, 6));
			model.SetHex(HexType.WATER, new Coordinate(2, 5));
			model.SetHex(HexType.WATER, new Coordinate(1, 4));
			model.SetHex(HexType.WATER, new Coordinate(0, 3));
			model.SetHex(HexType.WATER, new Coordinate(0, 1));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. If it does, then you suck.
		}
	}
	
	private static void PlacePorts(MapModel model)
	{
		Hex hex;
		Edge edge;
		
		try
		{
			hex = model.GetHex(new Coordinate(0,3));
			edge = model.GetEdge(hex.getPoint().GetEast(), hex.getPoint().GetSouthEast());
			model.SetPort(PortType.THREE, edge, hex);
			
			hex = model.GetHex(new Coordinate(2,5));
			edge = model.GetEdge(hex.getPoint().GetSouth(), hex.getPoint().GetSouthEast());
			model.SetPort(PortType.WHEAT, edge, hex);
			
			hex = model.GetHex(new Coordinate(4,5));
			edge = model.GetEdge(hex.getPoint().GetSouth(), hex.getPoint().GetSouthEast());
			model.SetPort(PortType.ORE, edge, hex);
			
			hex = model.GetHex(new Coordinate(6,3));
			edge = model.GetEdge(hex.getPoint().GetSouth(), hex.getPoint());
			model.SetPort(PortType.THREE, edge, hex);
			
			hex = model.GetHex(new Coordinate(6,-1));
			edge = model.GetEdge(hex.getPoint().GetNorth(), hex.getPoint());
			model.SetPort(PortType.SHEEP, edge, hex);
			
			hex = model.GetHex(new Coordinate(5,-4));
			edge = model.GetEdge(hex.getPoint().GetNorth(), hex.getPoint());
			model.SetPort(PortType.THREE, edge, hex);
			
			hex = model.GetHex(new Coordinate(3,-6));
			edge = model.GetEdge(hex.getPoint().GetNorth(), hex.getPoint().GetNorthEast());
			model.SetPort(PortType.THREE, edge, hex);
			
			hex = model.GetHex(new Coordinate(1,-4));
			edge = model.GetEdge(hex.getPoint().GetEast(), hex.getPoint().GetNorthEast());
			model.SetPort(PortType.BRICK, edge, hex);
			
			hex = model.GetHex(new Coordinate(0,-1));
			edge = model.GetEdge(hex.getPoint().GetEast(), hex.getPoint().GetNorthEast());
			model.SetPort(PortType.WOOD, edge, hex);
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
		
	}

	private static void PlacePips(MapModel model)
	{
		List<Integer> pipList = GetPipList();
		List<Hex> hexList = GetHexList(model);
		
		Iterator<Integer> pipIterator = pipList.iterator();
		Iterator<Hex> hexIterator = hexList.iterator();
		
		while (pipIterator.hasNext())
		{
			int pip = pipIterator.next();
			
			Hex hex = hexIterator.next();
			if (hex.getType() == HexType.DESERT)
				hex = hexIterator.next();
			
			model.SetPip(pip, hex);
		}
	}
	
	private static List<Integer> GetPipList()
	{
		List<Integer> pipList = new ArrayList<Integer>(18);
		
		pipList.add(5);
		pipList.add(2);
		pipList.add(6);
		pipList.add(3);
		pipList.add(8);
		pipList.add(10);
		pipList.add(9);
		pipList.add(12);
		pipList.add(11);
		pipList.add(4);
		pipList.add(8);
		pipList.add(10);
		pipList.add(9);
		pipList.add(4);
		pipList.add(5);
		pipList.add(6);
		pipList.add(3);
		pipList.add(11);
		
		return pipList;
	}

	private static List<Hex> GetHexList(MapModel model)
	{
		List<Hex> hexList = new ArrayList<Hex>(19);
		
		try
		{
			hexList.add(model.GetHex(new Coordinate(1, -2)));
			hexList.add(model.GetHex(new Coordinate(2, -3)));
			hexList.add(model.GetHex(new Coordinate(3, -4)));
			hexList.add(model.GetHex(new Coordinate(4, -3)));
			hexList.add(model.GetHex(new Coordinate(5, -2)));
			hexList.add(model.GetHex(new Coordinate(5, 0)));
			hexList.add(model.GetHex(new Coordinate(5, 2)));
			hexList.add(model.GetHex(new Coordinate(4, 3)));
			hexList.add(model.GetHex(new Coordinate(3, 4)));
			hexList.add(model.GetHex(new Coordinate(2, 3)));
			hexList.add(model.GetHex(new Coordinate(1, 2)));
			hexList.add(model.GetHex(new Coordinate(1, 0)));
			hexList.add(model.GetHex(new Coordinate(2, -1)));
			hexList.add(model.GetHex(new Coordinate(3, -2)));
			hexList.add(model.GetHex(new Coordinate(4, -1)));
			hexList.add(model.GetHex(new Coordinate(4, 1)));
			hexList.add(model.GetHex(new Coordinate(3, 2)));
			hexList.add(model.GetHex(new Coordinate(2, 1)));
			hexList.add(model.GetHex(new Coordinate(3, 0)));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//These are all standard coordinates. The only reason these wouldn't
			//exist is if you haven't created the map yet.
		}
		
		return hexList;
	}
}
