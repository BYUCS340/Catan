package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.model.IMapModel;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class RoadDropObject extends DropObject
{
	private Coordinate p1 = null;
	private Coordinate p2 = null;
	
	public RoadDropObject(IMapController controller, CatanColor color)
	{
		super(controller, color);
	}
	
	public Edge GetDropLocation() throws MapException
	{
		return controller.GetModel().GetEdge(p1, p2);
	}
	
	@Override
	public boolean IsValid()
	{
		if (p1 == null || p2 == null)
			return false;
		
		return controller.GetModel().ContainsEdge(p1, p2);
	}
	
	@Override
	public boolean IsAllowed()
	{
		if (!IsValid())
			return false;
		
		return controller.CanPlaceRoad(p1, p2, color);
	}

	@Override
	public void Handle(Point2D point)
	{
		IMapModel model = controller.GetModel();
		
		Coordinate closestHex = GetClosestHexCoordinate(point);
		if (!model.ContainsHex(closestHex))
			return;
		
		try
		{
			Hex hex = model.GetHex(closestHex);
			
			Map<Double, Vertex> possibleEnds = GetSortedVerticies(point, hex);
			
			Iterator<Vertex> sortedVerticies = possibleEnds.values().iterator();
			Vertex v1 = sortedVerticies.next();
			Vertex v2 = sortedVerticies.next();
			
			p1 = v1.getPoint();
			p2 = v2.getPoint();
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void Click()
	{
		controller.PlaceRoad(p1, p2);
	}
}
