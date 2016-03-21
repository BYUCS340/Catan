package testing.shared.model.map;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.model.map.*;
import shared.model.map.model.MapGenerator;
import shared.model.map.model.MapModel;
import shared.model.map.objects.*;

public class TestMapModel
{
	private MapModel model;

	@Before
	public void setUp() throws Exception
	{
		model = MapGenerator.BeginnerMap();
	}

	@After
	public void tearDown() throws Exception
	{
		model = null;
	}

	/**
	 * By running the map generator, the robber should get initialized.
	 */
	@Test
	public void testIsRobberInitialized_True()
	{
		assertTrue(model.IsRobberInitialized());
	}

	/**
	 * Edges are initialized automatically. The edge passed in shouldn't exist
	 * as it passes across the face of a hex. The method should return the same
	 * results regardless of the order the points are passed in.
	 */
	@Test
	public void testContainsEdge_False()
	{
		Coordinate p1 = new Coordinate(1,0);
		Coordinate p2 = new Coordinate(2,0);
		
		assertFalse(model.ContainsEdge(p1, p2));
		assertFalse(model.ContainsEdge(p2, p1));
	}
	
	/**
	 * Tests for an existing edge. The method should work regardless of the order
	 * the parameters are passed in.
	 */
	@Test
	public void testContainsEdge_True()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(3,0);
		
		assertTrue(model.ContainsEdge(p1, p2));
		assertTrue(model.ContainsEdge(p2, p1));
	}

	/**
	 * Vertices are initialized automatically. They only exist if they border a hex
	 * that isn't water.
	 */
	@Test
	public void testContainsVertex_False()
	{
		Coordinate point = new Coordinate(0, 0);
		
		assertFalse(model.ContainsVertex(point));
	}
	
	/**
	 * Tests a valid vertex.
	 */
	@Test
	public void testContainsVertex_True()
	{
		Coordinate point = new Coordinate(1, 0);
		
		assertTrue(model.ContainsVertex(point));
	}
	
	/**
	 * Map generator initializes the hexes. Passing in a valid coordinate
	 * should return true.
	 */
	@Test
	public void testHexExists_Initialized()
	{
		Coordinate point = new Coordinate(1, 0);
		
		assertTrue(model.ContainsHex(point));
	}
	
	/**
	 * This checks a hex that shouldn't exist after initialization.
	 * Ensures that the method still returns false even with hexes in
	 * the system.
	 */
	@Test
	public void testContainsHex_InvalidHex()
	{
		Coordinate point = new Coordinate(1, 1);
		
		assertFalse(model.ContainsHex(point));
	}

	/**
	 * No end points are satisfied. The road cannot be built.
	 * The method should return the same results regardless of point order.
	 */
	@Test
	public void testCanPlaceRoad_InvalidEdge()
	{
		Coordinate p1 = new Coordinate(2, 0);
		Coordinate p2 = new Coordinate(3, 0);
		
		assertFalse(model.CanPlaceRoad(p1, p2, CatanColor.GREEN));
		assertFalse(model.CanPlaceRoad(p2, p1, CatanColor.GREEN));
	}
	
	/**
	 * Attempts to place roads on already existed roads. This shouldn't
	 * be allowed. The method should return the same results regardless of point
	 * order.
	 * @throws MapException This shouldn't occur.
	 */
	@Test
	public void testCanPlaceRoad_RoadExists() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.GREEN);
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		assertFalse(model.CanPlaceRoad(p1, p2, CatanColor.GREEN));
		assertFalse(model.CanPlaceRoad(p2, p1, CatanColor.GREEN));
		assertFalse(model.CanPlaceRoad(p1, p2, CatanColor.BLUE));
		assertFalse(model.CanPlaceRoad(p2, p1, CatanColor.BLUE));
	}
	
	/**
	 * Village is used to refer to cities or settlements.
	 * This tests that roads can be placed when one of the ends are
	 * satisfied. The method should return the same result regardless of 
	 * point order.
	 * @throws MapException Shouldn't occur.
	 */
	@Test 
	public void testCanPlaceRoad_VillageSatisfied() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		//Ensure village satisfies end condition
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.GREEN);
		
		model.SetupPhase(false);
		assertTrue(model.CanPlaceRoad(p1, p2, CatanColor.GREEN));
		assertTrue(model.CanPlaceRoad(p2, p1, CatanColor.GREEN));
		
		//Ensure city satisfies end condition
		model.PlaceCity(p1, CatanColor.GREEN);
		assertTrue(model.CanPlaceRoad(p1, p2, CatanColor.GREEN));
		assertTrue(model.CanPlaceRoad(p2, p1, CatanColor.GREEN));
	}
	
	/**
	 * Roads can be added to other roads of like color. The method return
	 * the same value regardless of point order.
	 * @throws MapException Shouldn't occur
	 */
	@Test
	public void testCanPlaceRoad_RoadSatisfied() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		Coordinate p3 = new Coordinate(2,2);
		
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.GREEN);
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		
		model.SetupPhase(false);
		assertTrue(model.CanPlaceRoad(p2, p3, CatanColor.GREEN));
		assertTrue(model.CanPlaceRoad(p3, p2, CatanColor.GREEN));
	}

	/**
	 * This village can't be placed as a village or city is already in the location.
	 * @throws MapException This shouldn't occur
	 */
	@Test
	public void testCanPlaceSettlement_VillageExists() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		
		//Test with settlement
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceSettlement(p1, CatanColor.BLUE));
		
		//Test with city
		model.PlaceCity(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceSettlement(p1, CatanColor.BLUE));
	}
	
	/**
	 * A settlement can't be placed when too close to other
	 * villages.
	 * @throws MapException Shouldn't occur
	 */
	@Test
	public void testCanPlaceSettlement_ExistingNeighbor() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		//Test with settlement
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceSettlement(p2, CatanColor.BLUE));
		
		//Test with city
		model.PlaceCity(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceSettlement(p2, CatanColor.BLUE));
	}
	
	/**
	 * Attempts to use a vertex that doesn't exist. Shouldn't allow placement.
	 */
	@Test
	public void testCanPlaceSettlement_InvalidVertex() 
	{
		Coordinate p1 = new Coordinate(0,0);
		
		assertFalse(model.CanPlaceSettlement(p1, CatanColor.BLUE));
	}
	
	/**
	 * This settlement can be placed as the location is valid an no other pieces
	 * are on the board to restrict it.
	 */
	@Test
	public void testCanPlaceSettlementInitial_True()
	{
		Coordinate p1 = new Coordinate(2,0);
		
		model.SetupPhase(true);
		assertTrue(model.CanPlaceSettlement(p1, CatanColor.BLUE));
	}
	
	/**
	 * A city cannot be placed on another city regardless of color.
	 * A city cannot be placed on a settlement of another color.
	 * @throws MapException Shouldn't occur
	 */
	@Test
	public void testCanPlaceCity_CityExists() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceCity(p1, CatanColor.GREEN));
		
		model.SetupPhase(false);
		model.PlaceCity(p1, CatanColor.BLUE);
		assertFalse(model.CanPlaceCity(p1, CatanColor.BLUE));
	}
	
	/**
	 * A city requires a settlement to be placed.
	 */
	@Test
	public void testCanPlaceCity_NotEstablished()
	{
		Coordinate p2 = new Coordinate(2,1);
		
		assertFalse(model.CanPlaceCity(p2, CatanColor.GREEN));
	}
	
	/**
	 * Attempt to place city on a coordinate that doesn't exist.
	 */
	@Test
	public void testCanPlaceCity_Invalid() 
	{
		Coordinate p1 = new Coordinate(0,0);
		
		assertFalse(model.CanPlaceCity(p1, CatanColor.GREEN));
	}

	/**
	 * A city can be placed on a settlement of like color.
	 * @throws MapException Shouldn't occur
	 */
	@Test
	public void testCanPlaceCity_True() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.GREEN);
		
		model.SetupPhase(false);
		assertTrue(model.CanPlaceCity(p1, CatanColor.GREEN));
	}
	
	/**
	 * The robber cannot be placed on water.
	 */
	@Test
	public void testCanPlaceRobber_False()
	{
		Coordinate point = new Coordinate(0, 1);
		assertFalse(model.CanPlaceRobber(point));
	}
	
	/**
	 * Hexes need to be generated, which is done in the MapGenerator.
	 * The hex (0,0) doesn't exist.
	 */
	@Test
	public void testCanPlaceRobber_Invalid()
	{
		Coordinate point = new Coordinate(0, 0);
		assertFalse(model.CanPlaceRobber(point));
	}
	
	/**
	 * Test valid placement of robber.
	 */
	@Test
	public void testCanPlaceRobber_Valid()
	{
		Coordinate point = new Coordinate(1,0);
		assertTrue(model.CanPlaceRobber(point));
	}

	/**
	 * A pip cannot be placed on water.
	 */
	@Test
	public void testCanPlacePip_False()
	{
		Coordinate point = new Coordinate(0, 1);
		assertFalse(model.CanPlacePip(point));
	}
	
	/**
	 * Pips can't be placed until the hexes have been initialized.
	 */
	public void testCanPlacePip_Uninitialized()
	{
		Coordinate point = new Coordinate(1, 0);
		assertFalse(model.CanPlacePip(point));
	}
	
	/**
	 * Attempt to place pip on a hex that doesn't exist.
	 */
	@Test
	public void testCanPlacePip_Invalid()
	{
		Coordinate point = new Coordinate(0, 0);
		assertFalse(model.CanPlacePip(point));
	}
	
	/**
	 * Should be allowed to place pip.
	 */
	@Test
	public void testCanPlacePip_True()
	{
		Coordinate point = new Coordinate(1, 0);
		assertTrue(model.CanPlacePip(point));
	}
	
	/**
	 * Can't use set hex to overwrite existing hexes.
	 * @throws MapException Thrown due to attempt to overwrite.
	 */
	@Test(expected=MapException.class)
	public void testPlaceHex_Overwrite() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		
		model.PlaceHex(HexType.DESERT, point);
		model.PlaceHex(HexType.BRICK, point);
		
		fail("Hex overwritten when it shouldn't be");
	}
	
	/**
	 * The map can only store a certain amount of tiles for a valid board.
	 * @throws MapException Thrown because too many tiles are added.
	 */
	@Test(expected=MapException.class)
	public void testPlaceHex_Overflow() throws MapException
	{
		for (int i = 0; i < 40; i++)
		{
			Coordinate point = new Coordinate(i, 0);
			
			model.PlaceHex(HexType.DESERT, point);
		}
		
		fail("Hex allowed too many hexes");
	}
	
	/**
	 * Successfully adds hex.
	 * @throws MapException Shouldn't occur.
	 */
	public void testPlaceHex_Valid() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		model.PlaceHex(HexType.DESERT, point);
		
		Hex hex = model.GetHex(point);
		assertNotNull(hex);
		assertTrue(hex.getType() == HexType.DESERT);
	}

	/**
	 * Attempt to add road across hex.
	 * @throws MapException Thrown as the road doesn't exist.
	 */
	@Test(expected=MapException.class)
	public void testPlaceRoad_InvalidEdge() throws MapException
	{
		Coordinate p1 = new Coordinate(1, 0);
		Coordinate p2 = new Coordinate(2, 0);
		
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
	}
	
	/**
	 * Can't place a road on an existing road.
	 * @throws MapException Should be thrown at second road placement attempt.
	 */
	@Test(expected=MapException.class)
	public void testPlaceRoad_RoadExists() throws MapException
	{
		Coordinate p1 = new Coordinate(1, 0);
		Coordinate p2 = new Coordinate(1, 1);
		
		model.PlaceSettlement(p1, CatanColor.GREEN);
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		
		fail("This code should not be reached");
	}
	
	/**
	 * Attempts to add road when the end condition isn't satisfied.
	 * @throws MapException Thrown when road is placed.
	 */
	@Test(expected=MapException.class)
	public void testPlaceRoad_EndNotSatisified() throws MapException
	{
		Coordinate p1 = new Coordinate(1, 0);
		Coordinate p2 = new Coordinate(1, 1);
		
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		
		fail("This code should not be reached");
	}
	
	/**
	 * Satisfies all conditions to make road.
	 * @throws MapException
	 */
	@Test
	public void testPlaceRoad_Valid() throws MapException
	{
		Coordinate p1 = new Coordinate(1, 0);
		Coordinate p2 = new Coordinate(1, 1);
		
		Edge edge = model.GetEdge(p1, p2);
		assertFalse(edge.doesRoadExists());
		
		model.SetupPhase(true);
		model.PlaceSettlement(p1, CatanColor.GREEN);
		model.PlaceRoad(p1, p2, CatanColor.GREEN);
		edge = model.GetEdge(p1, p2);
		
		assertTrue(edge.doesRoadExists());
		assertTrue(edge.getColor() == CatanColor.GREEN);
	}
	
	/**
	 * Can't place a village on an already occupied vertex.
	 * @throws MapException Thrown when trying to replace village.
	 */
	@Test(expected=MapException.class)
	public void testPlaceSettlement_VillageExists() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		
		//Test with settlement
		model.PlaceSettlement(p1, CatanColor.BLUE);
		model.PlaceSettlement(p1, CatanColor.BLUE);
		
		fail("Code should not have been reached");
	}
	
	/**
	 * Settlements can't have neighbors. An exception should be thrown.
	 * @throws MapException Thrown when attempt is made to add neighbor.
	 */
	@Test(expected=MapException.class)
	public void testPlaceSettlement_ExistingNeighbor() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		model.PlaceSettlement(p1, CatanColor.BLUE);
		model.PlaceSettlement(p2, CatanColor.BLUE);
		
		fail("Code should not have been reached");
	}

	/**
	 * Exception should be thrown for a vertex that doesn't exist.
	 * @throws MapException Thrown when attempting to place on invalid vertex.
	 */
	@Test(expected=MapException.class)
	public void testPlaceSettlement_InvalidVertex() throws MapException
	{
		Coordinate point = new Coordinate(0, 0);
		model.PlaceSettlement(point, CatanColor.GREEN);
	}
	
	@Test
	public void testPlaceSettlement_Valid() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		model.SetupPhase(true);
		model.PlaceSettlement(point, CatanColor.GREEN);
		
		Vertex vertex = model.GetVertex(point);
		assertTrue(vertex.getType() == PieceType.SETTLEMENT);
	}
	
	/**
	 * Attempt to place a city on a non-existent vertex.
	 * @throws MapException Thrown in attempt to place city.
	 */
	@Test(expected=MapException.class)
	public void testPlaceCity_Invalid() throws MapException
	{
		Coordinate point = new Coordinate(0, 0);
		
		model.PlaceCity(point, CatanColor.GREEN);
		
		fail("Reached code that should not have been reached");
	}
	
	/**
	 * Attempt to place a city on a vertex that doesn't contain a settlement.
	 * @throws MapException Thrown when placing city.
	 */
	@Test(expected=MapException.class)
	public void testPlaceCity_NotEstablished() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		
		model.PlaceCity(point, CatanColor.GREEN);
		
		fail("Reached code that should not have been reached");
	}
	
	/**
	 * Attempt to steal a settlement.
	 * @throws MapException Thrown when placing city.
	 */
	@Test(expected=MapException.class)
	public void testPlaceCity_WrongColor() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		
		model.PlaceSettlement(point, CatanColor.GREEN);
		model.PlaceCity(point, CatanColor.BLUE);
		
		fail("Reached code that should not have been reached");
	}
	
	/**
	 * Valid placement of a city on a matching settlement.
	 * @throws MapException Shouldn't be thrown.
	 */
	@Test
	public void testPlaceCity_Valid() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		model.SetupPhase(true);
		model.PlaceSettlement(point, CatanColor.GREEN);
		model.PlaceCity(point, CatanColor.GREEN);
		
		Vertex vertex = model.GetVertex(point);
		assertTrue(vertex.getType() == PieceType.CITY);
	}
	
	/**
	 * Attempts to place port on something other than water.
	 * @throws MapException Thrown when placing port.
	 */
	@Test(expected=MapException.class)
	public void testPlacePort_DryPort() throws MapException
	{
		Coordinate mainPoint = new Coordinate(3, 0);
		Coordinate helpPoint = new Coordinate(3, 1);
		
		model.PlacePort(PortType.BRICK, mainPoint, mainPoint, helpPoint);
		
		fail("Reached code that should not have been reached");
	}
	
	/**
	 * Uses invalid verticies for the edge.
	 * @throws MapException Thrown when placing port.
	 */
	@Test(expected=MapException.class)
	public void testPlacePort_InvalidVertices() throws MapException
	{
		Coordinate mainPoint = new Coordinate(0, 1);
		Coordinate helpPoint = new Coordinate(0, 2);
		
		model.PlacePort(PortType.BRICK, mainPoint, mainPoint, helpPoint);
		
		fail("Reached code that should not have been reached");
	}
	
	@Test
	public void testSetPort_Valid() throws MapException
	{
		Coordinate mainPoint = new Coordinate(0, 1);
		Coordinate p1 = new Coordinate(1, 1);
		Coordinate p2 = new Coordinate(1, 2);
		
		model.PlacePort(PortType.BRICK, mainPoint, p1, p2);
	}

	/**
	 * Attempts to place robber in water, which is not valid.
	 * @throws MapException Thrown upon placing robber.
	 */
	@Test(expected=MapException.class)
	public void testPlaceRobber_Fail() throws MapException
	{
		Coordinate point = new Coordinate(0, 1);
		
		model.PlaceRobber(point);
		
		fail("Reached code that should not have been reached");
	}
	/**
	 * Successfully call of placing robber.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testPlaceRobber() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		
		model.PlaceRobber(point);
		
		assertTrue(model.GetRobberLocation().getPoint().equals(point));
	}
	
	/**
	 * Thrown when trying to access a invalid hex coordinate.
	 * @throws MapException Thrown accessing hex.
	 */
	@Test(expected=MapException.class)
	public void testGetHex_InvalidHex() throws MapException
	{
		Coordinate point = new Coordinate(1, 1);
		
		model.GetHex(point);
	}
	
	/**
	 * Valid attempt to get hex.
	 * @throws MapException
	 */
	@Test
	public void testGetHex_Valid() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		
		Hex hex = model.GetHex(point);
		
		assertNotNull(hex);
		assertTrue(hex.getClass() == Hex.class);
	}

	/**
	 * When uninitialized, a null iterator should be returned.
	 */
	public void testGetHexes_Uninitialized()
	{
		Iterator<Hex> hexes = model.GetHexes();
		
		assertNotNull(hexes);
		if (hexes.hasNext())
			fail("Hexes should be empty");
	}
	
	/**
	 * When fully inititalized, 37 hexes should exist.
	 */
	@Test
	public void testGetHexes_Initialized()
	{
		Iterator<Hex> hexes = model.GetHexes();
		
		assertNotNull(hexes);
		
		int count = 0;
		while (hexes.hasNext())
		{
			Hex hex = hexes.next();
			
			assertNotNull(hex);
			count++;
		}
		
		assertTrue(count == 37);
	}
	
	/**
	 * Attempt to get edge that doesn't exist.
	 * @throws MapException Thrown when getting edge.
	 */
	@Test(expected=MapException.class)
	public void testGetEdge_Invalid() throws MapException
	{
		Coordinate p1 = new Coordinate(1,0);
		Coordinate p2 = new Coordinate(2,0);
		
		model.GetEdge(p1, p2);
		
		fail("Code should not have been reached");
	}
	
	/**
	 * Valid attempt to get edge. Edge should be able to be accessed
	 * regardless of the point order.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testGetEdge_Valid() throws MapException
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(3,0);
		
		Edge edge = model.GetEdge(p1, p2);
		assertNotNull(edge);
		
		edge = model.GetEdge(p2, p1);
		assertNotNull(edge);
	}

	/**
	 * Should return all the edges in the map. Edges get created
	 * on creation, so they should always exist.
	 */
	@Test
	public void testGetEdges()
	{
		Iterator<Edge> edges = model.GetEdges();
		int count = 0;
		while (edges.hasNext())
		{
			Edge edge = edges.next();
			
			assertNotNull(edge);
			assertTrue(edge.getClass() == Edge.class);
			
			count++;
		}
		assertTrue(count == 72);
	}
	
	/**
	 * Attempt to access a vertex that doesn't exist.
	 * @throws MapException Thrown when accessing invalid vertex.
	 */
	@Test(expected=MapException.class)
	public void testGetVertex_Invalid() throws MapException
	{
		Coordinate point = new Coordinate(0, 0);
		
		model.GetVertex(point);
		
		fail("Reached code that should not have been reached");
	}
	
	/**
	 * Should be able to access vertex.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testGetVertex_Valid() throws MapException
	{
		Coordinate point = new Coordinate(1,0);
		
		Vertex vertex = model.GetVertex(point);
		
		assertNotNull(vertex);
	}

	/**
	 * Should get a list of all vertices. Vertices are initialized on
	 * creation, so they should always exist.
	 */
	@Test
	public void testGetVertices()
	{
		Iterator<Vertex> verticies = model.GetVertices();
		
		int count = 0;
		while (verticies.hasNext())
		{
			Vertex vertex = verticies.next();
			
			assertNotNull(vertex);
			
			count++;
		}
		
		assertTrue(count == 54);
	}

	/**
	 * Gets the vertices around an interior hex. There should be 6.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testGetVerticiesHex_Interior() throws MapException
	{
		Coordinate point = new Coordinate(1, 0);
		Hex hex = model.GetHex(point);
		
		Iterator<Vertex> verticies = model.GetVertices(hex);
		
		int count = 0;
		while (verticies.hasNext())
		{
			Vertex vertexFromList = verticies.next();
			
			assertNotNull(vertexFromList);
			
			count++;
		}
		
		assertTrue(count == 6);
	}
	
	/**
	 * Gets the vertices around an exterior hex. There should be 3.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testGetVerticiesHex_Exterior() throws MapException
	{
		Coordinate point = new Coordinate(0, 1);
		Hex hex = model.GetHex(point);
		
		Iterator<Vertex> verticies = model.GetVertices(hex);
		
		int count = 0;
		while (verticies.hasNext())
		{
			Vertex vertexFromList = verticies.next();
			
			assertNotNull(vertexFromList);
			
			count++;
		}
		
		assertTrue(count == 3);
	}

	/**
	 * Gets the neighboring vertices. There should be 3.
	 * @throws MapException Shouldn't occur.
	 */
	@Test
	public void testGetVerticesVertex_Center() throws MapException
	{
		Coordinate point = new Coordinate(2, 0);
		Vertex vertex = model.GetVertex(point);
		
		Iterator<Vertex> vertices = model.GetVertices(vertex);
		
		int count = 0;
		while (vertices.hasNext())
		{
			Vertex vertexFromList = vertices.next();
			
			assertNotNull(vertexFromList);
			
			count++;
		}
		
		assertTrue(count == 3);
	}

	/**
	 * Tests to make sure we get all the ports.
	 */
	@Test
	public void testGetPorts()
	{
		Iterator<Entry<Edge, Hex>> ports = model.GetPorts();
		
		int count = 0;
		while(ports.hasNext())
		{
			Entry<Edge, Hex> port = ports.next();
			assertNotNull(port);
			count++;
		}
		
		assert(count > 0);
	}

	/**
	 * Test to make sure we get all the pips.
	 */
	@Test
	public void testGetPips()
	{
		Iterator<Entry<Integer, List<Hex>>> pips = model.GetPips();
		
		int count = 0;
		while (pips.hasNext())
		{
			Entry<Integer, List<Hex>> pip = pips.next();
			assertNotNull(pip);
			count++;
		}
		
		assert(count > 0);
	}
}
