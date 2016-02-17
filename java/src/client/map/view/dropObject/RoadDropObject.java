package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class RoadDropObject extends DropObject
{
	private Edge dropLocation = null;
	
	public RoadDropObject(IMapController controller, CatanColor color)
	{
		super(controller, color);
	}
	
	public Edge GetDropLocation()
	{
		return dropLocation;
	}

	@Override
	public void Handle(Point2D point)
	{
		IMapModel model = controller.GetModel();
		
		Coordinate closestHex = GetClosestHexCoordinate(point);
		if (!model.HexExists(closestHex))
			return;
		
		try
		{
			Hex hex = model.GetHex(closestHex);
			
			Map<Double, Vertex> possibleEnds = GetSortedVerticies(point, hex);
			
			Iterator<Vertex> sortedVerticies = possibleEnds.values().iterator();
			Vertex v1 = sortedVerticies.next();
			Vertex v2 = sortedVerticies.next();
			Coordinate p1 = v1.getPoint();
			Coordinate p2 = v2.getPoint();
			
			isAllowed = controller.CanPlaceRoad(p1, p2, color);
			
			if (isAllowed)
				dropLocation = model.GetEdge(p1, p2);
		}
		catch (MapException e)
		{
			isAllowed = false;
			e.printStackTrace();
		}
	}

	@Override
	public void Click()
	{
		Coordinate p1 = dropLocation.getStart();
		Coordinate p2 = dropLocation.getEnd();
		controller.PlaceRoad(p1, p2);
	}
}
