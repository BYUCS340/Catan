package testing.shared.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Translate;
import shared.model.map.Coordinate;
import shared.model.map.model.MapGenerator;
import shared.model.map.model.MapModel;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class TestTranslate
{
	private static MapModel model;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		model = MapGenerator.BeginnerMap();		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		model = null;
	}

	@Test
	public void testGetEdgeLocation() 
	{
		Iterator<Edge> edges = model.GetEdges();
		while (edges.hasNext())
		{
			Edge edge = edges.next();
			Coordinate p1 = edge.getStart();
			Coordinate p2 = edge.getEnd();
			
			assertNotNull(Translate.GetEdgeLocation(p1, p2));
			assertNotNull(Translate.GetEdgeLocation(p2, p1));
		}
	}

	@Test
	public void testGetVertexLocation()
	{
		Iterator<Vertex> vertices = model.GetVertices();
		while (vertices.hasNext())
		{
			Vertex vertex = vertices.next();
			Coordinate point = vertex.getPoint();
			
			assertNotNull(Translate.GetVertexLocation(point));
		}
	}

	@Test
	public void testGetHexLocation() 
	{
		Iterator<Hex> hexes = model.GetHexes();
		while (hexes.hasNext())
		{
			Hex hex = hexes.next();
			Coordinate point = hex.getPoint();
			
			assertNotNull(Translate.GetHexLocation(point));
		}
	}

}
