package shared.model.map.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.handlers.HexHandler;

/**
 * Sets up the map.
 * @author Jonathan Sadler
 *
 */
public class MapGenerator
{
	/**
	 * Creates a map model.
	 * @return A map model
	 * @deprecated Use {@link #GenerateMap(boolean, boolean, boolean)} instead.
	 */
	@Deprecated
	public static MapModel CreateMapModel()
	{
		return new MapModel();
	}
	
	/**
	 * Creates a beginner map. Essentially, setups up the same map every time.
	 * Kind of boring, yet useful.
	 * @param model The model to initialize.
	 */
	public static MapModel BeginnerMap()
	{
		MapModel model = new MapModel();
		
		BeginnerLandSetup(model);
		PlaceWater(model);
		PlacePorts(model);
		PlacePips(model);
		
		return model;
	}
	
	/**
	 * Generates a new map model.
	 * @param randomTiles True to have random tile placement.
	 * @param randomNumbers True to have random number placement.
	 * @param randomPorts True to have random port placement.
	 * @return
	 */
	public static MapModel GenerateMap(boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{	
		MapModel model = new MapModel();
		
		PlaceWater(model);
		
		if (randomTiles)
			RandomLandSetup(model);
		else
			BeginnerLandSetup(model);
		
		if (randomNumbers)
			PlaceRandomPips(model);
		else
			PlacePips(model);
		
		if (randomPorts)
			PlaceRandomPorts(model);
		else
			PlacePorts(model);
		
		return model;
	}
	
	/**
	 * Initializes the water pieces on the map.
	 * @param model The model to initialize.
	 * @deprecated
	 */
	@Deprecated
	public static void WaterSetup(MapModel model)
	{
		PlaceWater(model);
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
	
	private static void RandomLandSetup(MapModel model)
	{
		try
		{
			Random random = new Random();
			List<Coordinate> coordinates = GetHexCoordinates();
			List<HexType> hexes = GetHexTypeList();
			
			for (HexType hex : hexes)
			{
				int coordinate = random.nextInt(coordinates.size());
				
				model.PlaceHex(hex, coordinates.get(coordinate));
				
				if(hex == HexType.DESERT)
				{
					model.PlaceRobber(coordinates.get(coordinate));
				}
				coordinates.remove(coordinate);
			}
		}
		catch(MapException e)
		{
			e.printStackTrace();
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

	private static void PlaceRandomPorts(MapModel model)
	{
		Coordinate hex;
		Coordinate edgeStart;
		Coordinate edgeEnd;
		PortType type;
		
		List<PortType> ports = GetPortTypes();
		
		try
		{
			hex = new Coordinate(0,3);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(2,5);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(4,5);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetBottomRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(6,3);
			edgeStart = HexHandler.GetBottomLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(6,-1);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(5,-4);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetLeft(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(3,-6);
			edgeStart = HexHandler.GetTopLeft(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(1,-4);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
			
			hex = new Coordinate(0,-1);
			edgeStart = HexHandler.GetRight(hex);
			edgeEnd = HexHandler.GetTopRight(hex);
			type = GetRandomPort(ports);
			model.PlacePort(type, hex, edgeStart, edgeEnd);
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void PlacePips(MapModel model)
	{
		List<Integer> pipList = GetPipList();
		List<Coordinate> hexList = GetHexCoordinates();
		
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
	
	private static void PlaceRandomPips(MapModel model)
	{
		Random random = new Random();
		List<Integer> pipList = GetPipList();
		List<Coordinate> coordinates = GetHexCoordinates();
		
		for (Coordinate coordinate : coordinates)
		{
			if (!model.CanPlacePip(coordinate))
				continue;
			
			int pip = random.nextInt(pipList.size());
			
			try
			{
				model.PlacePip(pipList.get(pip), coordinate);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
			
			pipList.remove(pip);
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

	private static List<HexType> GetHexTypeList()
	{
		List<HexType> hexTypes = new ArrayList<HexType>(19);
		
		hexTypes.add(HexType.BRICK);
		hexTypes.add(HexType.BRICK);
		hexTypes.add(HexType.BRICK);
		hexTypes.add(HexType.ORE);
		hexTypes.add(HexType.ORE);
		hexTypes.add(HexType.ORE);
		hexTypes.add(HexType.SHEEP);
		hexTypes.add(HexType.SHEEP);
		hexTypes.add(HexType.SHEEP);
		hexTypes.add(HexType.SHEEP);
		hexTypes.add(HexType.WHEAT);
		hexTypes.add(HexType.WHEAT);
		hexTypes.add(HexType.WHEAT);
		hexTypes.add(HexType.WHEAT);
		hexTypes.add(HexType.WOOD);
		hexTypes.add(HexType.WOOD);
		hexTypes.add(HexType.WOOD);
		hexTypes.add(HexType.WOOD);
		hexTypes.add(HexType.DESERT);
		
		return hexTypes;
	}
	
	private static List<Coordinate> GetHexCoordinates()
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

	private static List<PortType> GetPortTypes()
	{
		List<PortType> ports = new ArrayList<PortType>();
		
		ports.add(PortType.WOOD);
		ports.add(PortType.SHEEP);
		ports.add(PortType.THREE);
		ports.add(PortType.ORE);
		ports.add(PortType.WHEAT);
		ports.add(PortType.THREE);
		ports.add(PortType.THREE);
		ports.add(PortType.BRICK);
		ports.add(PortType.THREE);
		
		return ports;
	}
	
	private static PortType GetRandomPort(List<PortType> ports)
	{
		Random random = new Random();
		int port = random.nextInt(ports.size());
		
		PortType toReturn = ports.get(port);
		ports.remove(toReturn);
		
		return toReturn;
	}
}
