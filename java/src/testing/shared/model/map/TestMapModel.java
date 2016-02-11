//package testing.shared.model.map;
//
//import static org.junit.Assert.*;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map.Entry;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import shared.definitions.CatanColor;
//import shared.definitions.HexType;
//import shared.definitions.PieceType;
//import shared.definitions.PortType;
//import shared.model.map.Coordinate;
//import shared.model.map.MapException;
//import shared.model.map.MapGenerator;
//import shared.model.map.MapModel;
//import shared.model.map.objects.Edge;
//import shared.model.map.objects.Hex;
//import shared.model.map.objects.Vertex;
//
//public class TestMapModel
//{
//	private MapModel model;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception
//	{
//	
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception
//	{
//	
//	}
//
//	@Before
//	public void setUp() throws Exception
//	{
//		model = new MapModel();
//	}
//
//	@After
//	public void tearDown() throws Exception
//	{
//		model = null;
//	}
//
//	@Test
//	public void testContainsEdge_Valid()
//	{
//		Coordinate p1 = new Coordinate(2,0);
//		Coordinate p2 = new Coordinate(3,0);
//		
//		assertTrue(model.ContainsEdge(p1, p2));
//		assertTrue(model.ContainsEdge(p2, p1));
//	}
//	
//	@Test
//	public void testContainsEdge_Invalid()
//	{
//		Coordinate p1 = new Coordinate(1,0);
//		Coordinate p2 = new Coordinate(2,0);
//		
//		assertFalse(model.ContainsEdge(p1, p2));
//		assertFalse(model.ContainsEdge(p2, p1));
//	}
//
//	@Test
//	public void testContainsHex_Uninitialized()
//	{
//		Coordinate point = new Coordinate(1,0);
//		
//		assertFalse(model.ContainsHex(point));
//	}
//	
//	@Test
//	public void testContainsHex_Initialized()
//	{
//		MapGenerator.BeginnerMap(model);
//		Coordinate point = new Coordinate(1, 0);
//		
//		assertTrue(model.ContainsHex(point));
//	}
//	
//	@Test
//	public void testContainsHex_InvalidHex()
//	{
//		MapGenerator.BeginnerMap(model);
//		Coordinate point = new Coordinate(1, 1);
//		
//		assertFalse(model.ContainsHex(point));
//	}
//
//	@Test
//	public void testContainsVertex_Valid()
//	{
//		Coordinate point = new Coordinate(1, 0);
//		
//		assertTrue(model.ContainsVertex(point));
//	}
//	
//	@Test
//	public void testContainsVertex_Invalid()
//	{
//		Coordinate point = new Coordinate(0, 0);
//		
//		assertFalse(model.ContainsVertex(point));
//	}
//
//	@Test
//	public void testGetEdge_Valid() throws MapException
//	{
//		Coordinate p1 = new Coordinate(2,0);
//		Coordinate p2 = new Coordinate(3,0);
//		
//		Edge edge = model.GetEdge(p1, p2);
//		assertNotNull(edge);
//		assertTrue(edge.getClass() == Edge.class);
//		
//		edge = model.GetEdge(p2, p1);
//		assertNotNull(edge);
//		assertTrue(edge.getClass() == Edge.class);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testGetEdge_Invalid() throws MapException
//	{
//		Coordinate p1 = new Coordinate(1,0);
//		Coordinate p2 = new Coordinate(2,0);
//		
//		model.GetEdge(p1, p2);
//		
//		fail("Code should not have been reached");
//	}
//
//	@Test
//	public void testGetEdges_Center() throws MapException
//	{
//		Coordinate point = new Coordinate(2,0);
//		Vertex vertex = model.GetVertex(point);
//		
//		Iterator<Edge> edges = model.GetEdges(vertex);
//		int count = 0;
//		while (edges.hasNext())
//		{
//			Edge edge = edges.next();
//			
//			assertNotNull(edge);
//			assertTrue(edge.getClass() == Edge.class);
//			
//			count++;
//		}
//		assertTrue(count == 3);
//	}
//	
//	@Test
//	public void testGetEdges_Edge() throws MapException
//	{
//		Coordinate point = new Coordinate(1,0);
//		Vertex vertex = model.GetVertex(point);
//		
//		Iterator<Edge> edges = model.GetEdges(vertex);
//		int count = 0;
//		while (edges.hasNext())
//		{
//			Edge edge = edges.next();
//			
//			assertNotNull(edge);
//			assertTrue(edge.getClass() == Edge.class);
//			
//			count++;
//		}
//		assertTrue(count == 2);
//	}
//
//	@Test
//	public void testGetAllEdges()
//	{
//		Iterator<Edge> edges = model.GetAllEdges();
//		int count = 0;
//		while (edges.hasNext())
//		{
//			Edge edge = edges.next();
//			
//			assertNotNull(edge);
//			assertTrue(edge.getClass() == Edge.class);
//			
//			count++;
//		}
//		assertTrue(count == 72);
//	}
//
//	@Test(expected=MapException.class)
//	public void testGetHexCoordinate_Uninitialized() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		
//		model.GetHex(point);
//	}
//	
//	@Test
//	public void testGetHexCoordinate_Initialized() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		MapGenerator.BeginnerMap(model);
//		
//		Hex hex = model.GetHex(point);
//		
//		assertNotNull(hex);
//		assertTrue(hex.getClass() == Hex.class);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testGetHexCoordinate_InvalidHex() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 1);
//		MapGenerator.BeginnerMap(model);
//		
//		model.GetHex(point);
//	}
//
//	@Test(expected=MapException.class)
//	public void testGetHexInt_Uninitialized() throws MapException
//	{
//		int role = 2;
//		
//		model.GetHex(role);
//	}
//	
//	@Test
//	public void testGetHexInt_Initialized() throws MapException
//	{
//		int role = 2;
//		MapGenerator.BeginnerMap(model);
//		
//		Iterator<Hex> hexes = model.GetHex(role);
//		assertNotNull(hexes);
//		
//		int count = 0;
//		while (hexes.hasNext())
//		{
//			Hex hex = hexes.next();
//			
//			assertNotNull(hex);
//			count++;
//		}
//		
//		assertTrue(count > 0);
//	}
//
//	@Test
//	public void testGetAllHexes_Uninitialized()
//	{
//		Iterator<Hex> hexes = model.GetAllHexes();
//		
//		assertNotNull(hexes);
//		if (hexes.hasNext())
//			fail("Hexes should be empty");
//	}
//	
//	@Test
//	public void testGetAllHexes_Initialized()
//	{
//		MapGenerator.BeginnerMap(model);
//		Iterator<Hex> hexes = model.GetAllHexes();
//		
//		assertNotNull(hexes);
//		
//		int count = 0;
//		while (hexes.hasNext())
//		{
//			Hex hex = hexes.next();
//			
//			assertNotNull(hex);
//			count++;
//		}
//		
//		assertTrue(count == 37);
//	}
//
//	@Test
//	public void testGetPips_Uninitialized()
//	{
//		Iterator<Entry<Integer, List<Hex>>> pips = model.GetPips();
//		
//		assertNotNull(pips);
//		
//		if (pips.hasNext())
//			fail("No pips should exist");
//	}
//	
//	@Test
//	public void testGetPips_Initialized()
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		Iterator<Entry<Integer, List<Hex>>> pips = model.GetPips();
//		
//		assertNotNull(pips);
//		
//		int count = 0;
//		while (pips.hasNext())
//		{
//			Entry<Integer, List<Hex>> pip = pips.next();
//			
//			assertNotNull(pip);
//			assertTrue(pip.getValue().size() > 0);
//			count++;
//		}
//		
//		assertTrue(count == 10);
//	}
//
//	@Test
//	public void testGetVertex_Valid() throws MapException
//	{
//		Coordinate point = new Coordinate(1,0);
//		
//		Vertex vertex = model.GetVertex(point);
//		
//		assertNotNull(vertex);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testGetVertex_Invalid() throws MapException
//	{
//		Coordinate point = new Coordinate(0, 0);
//		
//		model.GetVertex(point);
//	}
//
//	@Test
//	public void testGetVerticiesVertex_Center() throws MapException
//	{
//		Coordinate point = new Coordinate(2, 0);
//		Vertex vertex = model.GetVertex(point);
//		
//		Iterator<Vertex> verticies = model.GetVerticies(vertex);
//		
//		int count = 0;
//		while (verticies.hasNext())
//		{
//			Vertex vertexFromList = verticies.next();
//			
//			assertNotNull(vertexFromList);
//			
//			count++;
//		}
//		
//		assertTrue(count == 3);
//	}
//	
//	@Test
//	public void testGetVerticiesVertex_Edge() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		Vertex vertex = model.GetVertex(point);
//		
//		Iterator<Vertex> verticies = model.GetVerticies(vertex);
//		
//		int count = 0;
//		while (verticies.hasNext())
//		{
//			Vertex vertexFromList = verticies.next();
//			
//			assertNotNull(vertexFromList);
//			
//			count++;
//		}
//		
//		assertTrue(count == 2);
//	}
//	
//	@Test
//	public void testGetVerticiesHex() throws MapException
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		Coordinate point = new Coordinate(1, 0);
//		Hex hex = model.GetHex(point);
//		
//		Iterator<Vertex> verticies = model.GetVerticies(hex);
//		
//		int count = 0;
//		while (verticies.hasNext())
//		{
//			Vertex vertexFromList = verticies.next();
//			
//			assertNotNull(vertexFromList);
//			
//			count++;
//		}
//		
//		assertTrue(count == 6);
//	}
//
//	@Test
//	public void testGetAllVerticies()
//	{
//		Iterator<Vertex> verticies = model.GetAllVerticies();
//		
//		int count = 0;
//		while (verticies.hasNext())
//		{
//			Vertex vertex = verticies.next();
//			
//			assertNotNull(vertex);
//			
//			count++;
//		}
//		
//		assertTrue(count == 54);
//	}
//
//	@Test
//	public void testGetOccupiedVerticies() throws MapException
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		Coordinate point = new Coordinate(1, 0);
//		Hex hex = model.GetHex(point);
//		
//		Iterator<Vertex> vertices = model.GetOccupiedVerticies(hex);
//		if (vertices.hasNext())
//			fail("Vertices returned when no vertices should be present");
//		
//		model.SetCity(point, CatanColor.GREEN);
//		vertices = model.GetOccupiedVerticies(hex);
//		int count = 0;
//		while (vertices.hasNext())
//		{
//			Vertex vertex = vertices.next();
//			
//			assertNotNull(vertex);
//			assertTrue(vertex.getColor() == CatanColor.GREEN);
//			count++;
//		}
//		
//		assertTrue(count == 1);
//	}
//
//	@Test
//	public void testGetAllPorts_Uninitialized()
//	{
//		Iterator<Entry<Edge, Hex>> ports = model.GetAllPorts();
//		
//		if (ports.hasNext())
//			fail("Ports contained values when it shouldn't");
//	}
//	
//	@Test
//	public void testGetAllPorts_Initialized()
//	{
//		MapGenerator.BeginnerMap(model);
//		Iterator<Entry<Edge, Hex>> ports = model.GetAllPorts();
//		
//		assertTrue(ports.hasNext());
//	}
//
//	@Test(expected=MapException.class)
//	public void testGetRobberPlacement_Uninitialized() throws MapException
//	{
//		model.GetRobberPlacement();
//	}
//	
//	@Test
//	public void testGetRobberPlacement_Initialized() throws MapException
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		Hex hex = model.GetRobberPlacement();
//		
//		assertNotNull(hex);
//	}
//
//	@Test
//	public void testLongestRoadExists_NoExist()
//	{
//		assertFalse(model.LongestRoadExists());
//	}
//	
//	@Test
//	public void testLongestRoadExists_Exists() throws MapException
//	{
//		Coordinate p1 = new Coordinate(3, 0);
//		Coordinate p2 = new Coordinate(3, 1);
//		Coordinate p3 = new Coordinate(3, 2);
//		Coordinate p4 = new Coordinate(3, 3);
//		
//		model.SetSettlement(p1, CatanColor.GREEN);
//		model.SetRoad(p1, p2, CatanColor.GREEN);
//		model.SetRoad(p2, p3, CatanColor.GREEN);
//		model.SetRoad(p3, p4, CatanColor.GREEN);
//		
//		assertTrue(model.LongestRoadExists());
//	}
//
//	@Test(expected=MapException.class)
//	public void testGetLongestRoadColor_NoExist() throws MapException
//	{
//		model.GetLongestRoadColor();
//	}
//	
//	@Test
//	public void testGetLongestRoadColor_Exist() throws MapException
//	{
//		Coordinate p1 = new Coordinate(3, 0);
//		Coordinate p2 = new Coordinate(3, 1);
//		Coordinate p3 = new Coordinate(3, 2);
//		Coordinate p4 = new Coordinate(3, 3);
//		
//		model.SetSettlement(p1, CatanColor.GREEN);
//		model.SetRoad(p1, p2, CatanColor.GREEN);
//		model.SetRoad(p2, p3, CatanColor.GREEN);
//		model.SetRoad(p3, p4, CatanColor.GREEN);
//		
//		assertTrue(model.GetLongestRoadColor() == CatanColor.GREEN);
//	}
//
//	@Test
//	public void testSetRoad_Valid() throws MapException
//	{
//		Coordinate p1 = new Coordinate(1, 0);
//		Coordinate p2 = new Coordinate(1, 1);
//		
//		model.SetSettlement(p1, CatanColor.GREEN);
//		model.SetRoad(p1, p2, CatanColor.GREEN);
//		Edge edge = model.GetEdge(p1, p2);
//		
//		assertTrue(edge.getColor() == CatanColor.GREEN);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetRoad_Invalid() throws MapException
//	{
//		Coordinate p1 = new Coordinate(1, 0);
//		Coordinate p2 = new Coordinate(2, 0);
//		
//		model.SetRoad(p1, p2, CatanColor.GREEN);
//	}
//
//	@Test
//	public void testSetHex_Valid() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		model.SetHex(HexType.DESERT, point);
//		
//		Hex hex = model.GetHex(point);
//		assertNotNull(hex);
//		assertTrue(hex.getType() == HexType.DESERT);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetHex_Overwrite() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		
//		model.SetHex(HexType.DESERT, point);
//		model.SetHex(HexType.BRICK, point);
//		
//		fail("Hex overwritten when it shouldn't be");
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetHex_Overflow() throws MapException
//	{
//		for (int i = 0; i < 40; i++)
//		{
//			Coordinate point = new Coordinate(i, 0);
//			
//			model.SetHex(HexType.DESERT, point);
//		}
//		
//		fail("Hex allowed too many hexes");
//	}
//
//	@Test
//	public void testSetPip() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		model.SetHex(HexType.BRICK, point);
//		Hex hex = model.GetHex(point);
//		
//		model.SetPip(2, hex);
//		Iterator<Entry<Integer, List<Hex>>> pips = model.GetPips();
//		
//		Entry<Integer,List<Hex>> entry = pips.next();
//		
//		assertTrue(entry.getKey() == 2);
//		assertTrue(entry.getValue().get(0).equals(hex));
//	}
//
//	@Test
//	public void testSetSettlement_Valid() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		model.SetSettlement(point, CatanColor.GREEN);
//		
//		Vertex vertex = model.GetVertex(point);
//		assertTrue(vertex.getType() == PieceType.SETTLEMENT);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetSettlement_Invalid() throws MapException
//	{
//		Coordinate point = new Coordinate(0, 0);
//		model.SetSettlement(point, CatanColor.GREEN);
//	}
//
//	@Test
//	public void testSetCity_Valid() throws MapException
//	{
//		Coordinate point = new Coordinate(1, 0);
//		model.SetSettlement(point, CatanColor.GREEN);
//		model.SetCity(point, CatanColor.GREEN);
//		
//		Vertex vertex = model.GetVertex(point);
//		assertTrue(vertex.getType() == PieceType.CITY);
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetCity_Invalid() throws MapException
//	{
//		Coordinate point = new Coordinate(0, 0);
//		model.SetCity(point, CatanColor.GREEN);
//	}
//
//	@Test
//	public void testSetPort_Valid() throws MapException
//	{
//		assertFalse(model.GetAllPorts().hasNext());
//		
//		Coordinate point = new Coordinate(0, 1);
//		model.SetHex(HexType.WATER, point);
//		
//		Coordinate p1 = new Coordinate(1, 0);
//		Coordinate p2 = new Coordinate(1, 1);
//		Edge edge = model.GetEdge(p1, p2);
//		
//		Hex hex = model.GetHex(point);
//		
//		model.SetPort(PortType.BRICK, edge, hex);
//		
//		assertTrue(model.GetAllPorts().hasNext());
//	}
//	
//	@Test(expected=MapException.class)
//	public void testSetPort_Invalid() throws MapException
//	{
//		Coordinate point = new Coordinate(0, 1);
//		model.SetHex(HexType.DESERT, point);
//		
//		Coordinate p1 = new Coordinate(1, 0);
//		Coordinate p2 = new Coordinate(1, 1);
//		Edge edge = model.GetEdge(p1, p2);
//		
//		Hex hex = model.GetHex(point);
//		
//		model.SetPort(PortType.BRICK, edge, hex);
//	}
//	
//	@Test
//	public void testIsRobberInitialized_False()
//	{
//		assertFalse(model.IsRobberInitialized());
//	}
//	
//	@Test
//	public void testIsRobberInitialized_True()
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		assertTrue(model.IsRobberInitialized());
//	}
//
//	@Test
//	public void testSetRobber() throws MapException
//	{
//		MapGenerator.BeginnerMap(model);
//		
//		Coordinate point = new Coordinate(1, 0);
//		Hex hex = model.GetHex(point);
//		
//		model.SetRobber(hex);
//		
//		assertTrue(model.GetRobberPlacement().getPoint().equals(point));
//	}
//}
