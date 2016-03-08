package shared.model.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.model.map.handlers.HexHandler;
import shared.model.map.model.MapModel;

/**
 * Sets up the map.
 * @author Jonathan Sadler
 *
 */
public class MapGenerator
{
	/**
	 * Creates a beginner map. Essentially, setups up the same map every time.
	 * Kind of boring, yet useful.
	 * @param model The model to initialize.
	 */
	public static void BeginnerMap(MapModel model)
	{
		BeginnerLandSetup(model);
		PlaceWater(model);
		PlacePorts(model);
		PlacePips(model);
	}
	
	/**
	 * Initializes the water pieces on the map.
	 * @param model The model to initialize.
	 */
	public static void WaterSetup(MapModel model)
	{
		PlaceWater(model);
	}
	
	private static void BeginnerLandSetup(MapModel model)
	{
		try
		{
			model.PlaceHex(HexType.ORE, new Coordinate(1, -2));
			model.PlaceHex(HexType.SHEEP, new Coordinate(1, 0));
			model.PlaceHex(HexType.WOOD, new Coordinate(1, 2));
			
			model.PlaceHex(HexType.WHEAT, new Coordinate(2, -3));
			model.PlaceHex(HexType.BRICK, new Coordinate(2, -1));
			model.PlaceHex(HexType.SHEEP, new Coordinate(2, 1));
			model.PlaceHex(HexType.BRICK, new Coordinate(2, 3));
			
			model.PlaceHex(HexType.WHEAT, new Coordinate(3, -4));
			model.PlaceHex(HexType.WOOD, new Coordinate(3, -2));
			model.PlaceHex(HexType.DESERT, new Coordinate(3, 0));
			model.PlaceHex(HexType.WOOD, new Coordinate(3, 2));
			model.PlaceHex(HexType.ORE, new Coordinate(3, 4));
			
			model.PlaceHex(HexType.WOOD, new Coordinate(4, -3));
			model.PlaceHex(HexType.ORE, new Coordinate(4, -1));
			model.PlaceHex(HexType.WHEAT, new Coordinate(4, 1));
			model.PlaceHex(HexType.SHEEP, new Coordinate(4, 3));
			
			model.PlaceHex(HexType.BRICK, new Coordinate(5, -2));
			model.PlaceHex(HexType.WHEAT, new Coordinate(5, 0));
			model.PlaceHex(HexType.SHEEP, new Coordinate(5, 2));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. Otherwise, we just suck.
		}
		
		try {
			model.PlaceRobber(new Coordinate(3,0));
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
			model.PlaceHex(HexType.WATER, new Coordinate(0, -1));
			model.PlaceHex(HexType.WATER, new Coordinate(0, -3));
			model.PlaceHex(HexType.WATER, new Coordinate(1, -4));
			model.PlaceHex(HexType.WATER, new Coordinate(2, -5));
			model.PlaceHex(HexType.WATER, new Coordinate(3, -6));
			model.PlaceHex(HexType.WATER, new Coordinate(4, -5));
			model.PlaceHex(HexType.WATER, new Coordinate(5, -4));
			model.PlaceHex(HexType.WATER, new Coordinate(6, -3));
			model.PlaceHex(HexType.WATER, new Coordinate(6, -1));
			model.PlaceHex(HexType.WATER, new Coordinate(6, 1));
			model.PlaceHex(HexType.WATER, new Coordinate(6, 3));
			model.PlaceHex(HexType.WATER, new Coordinate(5, 4));
			model.PlaceHex(HexType.WATER, new Coordinate(4, 5));
			model.PlaceHex(HexType.WATER, new Coordinate(3, 6));
			model.PlaceHex(HexType.WATER, new Coordinate(2, 5));
			model.PlaceHex(HexType.WATER, new Coordinate(1, 4));
			model.PlaceHex(HexType.WATER, new Coordinate(0, 3));
			model.PlaceHex(HexType.WATER, new Coordinate(0, 1));
		}
		catch (MapException e)
		{
			e.printStackTrace();
			//This shouldn't happen. If it does, then you suck.
		}
	}
	
	private static void PlacePorts(MapModel model)
	{
		Coordinate hex;
		Coordinate edgeStart;
		Coordinate edgeEnd;
		
		try
		{
			hex = new Coordinate(0,3);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			model.PlacePort(PortType.THREE, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(2,5);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			model.PlacePort(PortType.WHEAT, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(4,5);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			model.PlacePort(PortType.ORE, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(6,3);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			model.PlacePort(PortType.THREE, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(6,-1);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			model.PlacePort(PortType.SHEEP, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(5,-4);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			model.PlacePort(PortType.THREE, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(3,-6);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			model.PlacePort(PortType.THREE, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(1,-4);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			model.PlacePort(PortType.BRICK, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(0,-1);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			model.PlacePort(PortType.WOOD, hex, edgeStart, edgeEnd);
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
		
	}

	private static void PlacePips(MapModel model)
	{
		List<Integer> pipList = GetPipList();
		List<Coordinate> hexList = GetHexList(model);
		
		Iterator<Integer> pipIterator = pipList.iterator();
		Iterator<Coordinate> hexIterator = hexList.iterator();
		
		while (pipIterator.hasNext())
		{
			int pip = pipIterator.next();
		
			Coordinate hex = hexIterator.next();
			if (!model.CanPlacePip(hex))
				hex = hexIterator.next();
			
			try
			{
				model.PlacePip(pip, hex);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
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

	private static List<Coordinate> GetHexList(MapModel model)
	{
		List<Coordinate> hexList = new ArrayList<Coordinate>(19);
		
		hexList.add(new Coordinate(1, -2));
		hexList.add(new Coordinate(2, -3));
		hexList.add(new Coordinate(3, -4));
		hexList.add(new Coordinate(4, -3));
		hexList.add(new Coordinate(5, -2));
		hexList.add(new Coordinate(5, 0));
		hexList.add(new Coordinate(5, 2));
		hexList.add(new Coordinate(4, 3));
		hexList.add(new Coordinate(3, 4));
		hexList.add(new Coordinate(2, 3));
		hexList.add(new Coordinate(1, 2));
		hexList.add(new Coordinate(1, 0));
		hexList.add(new Coordinate(2, -1));
		hexList.add(new Coordinate(3, -2));
		hexList.add(new Coordinate(4, -1));
		hexList.add(new Coordinate(4, 1));
		hexList.add(new Coordinate(3, 2));
		hexList.add(new Coordinate(2, 1));
		hexList.add(new Coordinate(3, 0));
		
		return hexList;
	}
}
