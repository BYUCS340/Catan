package testing.client.map;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.map.MapController;
import client.map.view.MapView;
import shared.definitions.CatanColor;
import shared.model.map.Coordinate;
import shared.model.map.MapGenerator;
import shared.model.map.MapModel;

public class MapControllerTest
{	
	private static MapView view;
	
	private MapModel model;
	private MapController controller;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		view = new MapView();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		view = null;
	}

	@Before
	public void setUp() throws Exception
	{
		model = new MapModel();
		controller = new MapController(view, null, model);
	}

	@After
	public void tearDown() throws Exception 
	{
		controller = null;
	}

	@Test 
	public void testCanPlaceRoad_VillageSatisfied()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		controller.placeSettlement(p1, CatanColor.GREEN);
		assertTrue(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
		
		controller.placeCity(p1, CatanColor.GREEN);
		assertTrue(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceRoad_RoadSatisfied()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		Coordinate p3 = new Coordinate(2,2);
		
		controller.placeSettlement(p1, CatanColor.GREEN);
		controller.placeRoad(p1, p2, CatanColor.GREEN);
		
		assertTrue(controller.canPlaceRoad(p2, p3, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceRoad_EndsNotSatisfied()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		Coordinate p3 = new Coordinate(2,2);
		
		assertFalse(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
		
		controller.placeSettlement(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
		
		controller.placeRoad(p1, p2, CatanColor.BLUE);
		assertFalse(controller.canPlaceRoad(p2, p3, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceRoad_RoadAlreadyPlaced()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		controller.placeSettlement(p1, CatanColor.GREEN);
		controller.placeRoad(p1, p2, CatanColor.GREEN);
		assertFalse(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
		assertFalse(controller.canPlaceRoad(p1, p2, CatanColor.BLUE));
	}
	
	@Test
	public void testCanPlaceRoad_NonexistingRoad()
	{
		Coordinate p1 = new Coordinate(3,0);
		Coordinate p2 = new Coordinate(4,0);
		
		assertFalse(controller.canPlaceRoad(p1, p2, CatanColor.GREEN));
	}

	@Test
	public void testCanPlaceSettlement_Valid()
	{
		Coordinate p1 = new Coordinate(2,0);
		
		assertTrue(controller.canPlaceSettlement(p1));
	}
	
	@Test
	public void testCanPlaceSettlement_AlreadyExisting()
	{
		Coordinate p1 = new Coordinate(2,0);
		
		controller.placeSettlement(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceSettlement(p1));
		
		controller.placeCity(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceSettlement(p1));
	}
	
	@Test
	public void testCanPlaceSettlement_ExistingNeighbor()
	{
		Coordinate p1 = new Coordinate(2,0);
		Coordinate p2 = new Coordinate(2,1);
		
		controller.placeSettlement(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceSettlement(p2));
		
		controller.placeCity(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceSettlement(p2));
	}
	
	@Test
	public void testCanPlaceSettlement_Nonexisting() 
	{
		Coordinate p1 = new Coordinate(0,0);
		
		assertFalse(controller.canPlaceSettlement(p1));
	}
	
	@Test
	public void testCanPlaceCity_Valid()
	{
		Coordinate p1 = new Coordinate(2,0);
		
		controller.placeSettlement(p1, CatanColor.GREEN);
		assertTrue(controller.canPlaceCity(p1, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceCity_AlreadyExisting()
	{
		Coordinate p1 = new Coordinate(2,0);
		
		controller.placeSettlement(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceCity(p1, CatanColor.GREEN));
		
		controller.placeCity(p1, CatanColor.BLUE);
		assertFalse(controller.canPlaceCity(p1, CatanColor.BLUE));
	}
	
	@Test
	public void testCanPlaceCity_NonSettlement()
	{
		Coordinate p2 = new Coordinate(2,1);
		
		assertFalse(controller.canPlaceCity(p2, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceCity_Nonexisting() 
	{
		Coordinate p1 = new Coordinate(0,0);
		
		assertFalse(controller.canPlaceCity(p1, CatanColor.GREEN));
	}
	
	@Test
	public void testCanPlaceRobber_Valid()
	{
		MapGenerator.BeginnerMap(model);
		
		Coordinate point = new Coordinate(1,0);
		assertTrue(controller.canPlaceRobber(point));
	}
	
	@Test
	public void testCanPlaceRobber_Invalid()
	{
		MapGenerator.BeginnerMap(model);
		
		Coordinate point = new Coordinate(0, 0);
		assertFalse(controller.canPlaceRobber(point));
	}
	
	@Test
	public void testCanPlaceRobber_Water()
	{
		MapGenerator.BeginnerMap(model);
		
		Coordinate point = new Coordinate(0, 1);
		assertFalse(controller.canPlaceRobber(point));
	}
}
