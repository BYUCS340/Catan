package client.map.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.map.model.handlers.*;
import client.map.model.objects.Hex;
import shared.definitions.HexType;

public class MapModel {
	
	Map<Integer, List<Hex>> values;
	
	HexHandler hexes;
	EdgeHandler edges;
	VertexHandler verticies;
	
	public MapModel()
	{
		this(Method.beginner);
	}
	
	public MapModel(Method method)
	{
		values = new HashMap<Integer, List<Hex>>();
		
		hexes = new HexHandler();
		edges = new EdgeHandler();
		verticies = new VertexHandler();
		
		if (method == Method.random)
			RandomSetup();
		else
			BeginnerSetup();
		
		PlaceWater();
		PlacePips();
	}
	
	private void RandomSetup()
	{
		//Todo Setup
	}
	
	private void BeginnerSetup()
	{
		hexes.AddHex(new Hex(HexType.ORE, 1, -2));
		hexes.AddHex(new Hex(HexType.SHEEP, 1, 0));
		hexes.AddHex(new Hex(HexType.WOOD, 1, 2));
		
		hexes.AddHex(new Hex(HexType.WHEAT, 2, -3));
		hexes.AddHex(new Hex(HexType.BRICK, 2, -1));
		hexes.AddHex(new Hex(HexType.SHEEP, 2, 1));
		hexes.AddHex(new Hex(HexType.BRICK, 2, 3));
		
		hexes.AddHex(new Hex(HexType.WHEAT, 3, -4));
		hexes.AddHex(new Hex(HexType.WOOD, 3, -2));
		hexes.AddHex(new Hex(HexType.DESERT, 3, 0));
		hexes.AddHex(new Hex(HexType.WOOD, 3, 2));
		hexes.AddHex(new Hex(HexType.ORE, 3, 4));
		
		hexes.AddHex(new Hex(HexType.WOOD, 4, -3));
		hexes.AddHex(new Hex(HexType.ORE, 4, -1));
		hexes.AddHex(new Hex(HexType.WHEAT, 4, 1));
		hexes.AddHex(new Hex(HexType.SHEEP, 4, 3));
		
		hexes.AddHex(new Hex(HexType.BRICK, 5, -2));
		hexes.AddHex(new Hex(HexType.WHEAT, 5, 0));
		hexes.AddHex(new Hex(HexType.SHEEP, 5, 2));
	}
	
	private void PlaceWater()
	{
		hexes.AddHex(new Hex(HexType.WATER, 0, -1));
		hexes.AddHex(new Hex(HexType.WATER, 0, -3));
		hexes.AddHex(new Hex(HexType.WATER, 1, -4));
		hexes.AddHex(new Hex(HexType.WATER, 2, -5));
		hexes.AddHex(new Hex(HexType.WATER, 3, -6));
		hexes.AddHex(new Hex(HexType.WATER, 4, -5));
		hexes.AddHex(new Hex(HexType.WATER, 5, -4));
		hexes.AddHex(new Hex(HexType.WATER, 6, -3));
		hexes.AddHex(new Hex(HexType.WATER, 6, -1));
		hexes.AddHex(new Hex(HexType.WATER, 6, 1));
		hexes.AddHex(new Hex(HexType.WATER, 6, 3));
		hexes.AddHex(new Hex(HexType.WATER, 5, 4));
		hexes.AddHex(new Hex(HexType.WATER, 4, 5));
		hexes.AddHex(new Hex(HexType.WATER, 3, 6));
		hexes.AddHex(new Hex(HexType.WATER, 2, 5));
		hexes.AddHex(new Hex(HexType.WATER, 1, 4));
		hexes.AddHex(new Hex(HexType.WATER, 0, 3));
		hexes.AddHex(new Hex(HexType.WATER, 0, 1));
	}

	
	private void PlacePips()
	{
		List<Integer> pipList = GetPipList();
		List<Hex> hexList = GetHexList();
		
		Iterator<Integer> pipIterator = pipList.iterator();
		Iterator<Hex> hexIterator = hexList.iterator();
		
		while (pipIterator.hasNext())
		{
			int pip = pipIterator.next();
			
			Hex hex = hexIterator.next();
			if (hex.getType() == HexType.DESERT)
				hex = hexIterator.next();
			
			AddPip(pip, hex);
		}
	}
	
	private List<Integer> GetPipList()
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

	private List<Hex> GetHexList()
	{
		List<Hex> hexList = new ArrayList<Hex>(19);
		
		hexList.add(hexes.GetHex(1, -2));
		hexList.add(hexes.GetHex(2, -3));
		hexList.add(hexes.GetHex(3, -4));
		hexList.add(hexes.GetHex(4, -3));
		hexList.add(hexes.GetHex(5, -2));
		hexList.add(hexes.GetHex(5, 0));
		hexList.add(hexes.GetHex(5, 2));
		hexList.add(hexes.GetHex(4, 3));
		hexList.add(hexes.GetHex(3, 4));
		hexList.add(hexes.GetHex(2, 3));
		hexList.add(hexes.GetHex(1, 2));
		hexList.add(hexes.GetHex(1, 0));
		hexList.add(hexes.GetHex(2, -1));
		hexList.add(hexes.GetHex(3, -2));
		hexList.add(hexes.GetHex(4, -1));
		hexList.add(hexes.GetHex(4, 1));
		hexList.add(hexes.GetHex(3, 2));
		hexList.add(hexes.GetHex(2, 1));
		hexList.add(hexes.GetHex(3, 0));
		
		return hexList;
	}
	
	private void AddPip(int value, Hex hex)
	{
		if (values.containsKey(value))
		{
			values.get(value).add(hex);
		}
		else
		{
			List<Hex> tempList = new ArrayList<Hex>();
			tempList.add(hex);
			values.put(value, tempList);
		}
	}
	
	public enum Method
	{
		beginner, random
	}
}
