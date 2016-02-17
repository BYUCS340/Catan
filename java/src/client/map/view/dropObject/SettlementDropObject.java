package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.*;

public class SettlementDropObject extends DropObject 
{
	private Vertex dropLocation = null;
	
	public SettlementDropObject(IMapController controller, CatanColor color) 
	{
		super(controller, color);
	}
	
	public Vertex GetDropLocation()
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
			Coordinate p1 = v1.getPoint();
			
			isAllowed = controller.CanPlaceSettlement(p1);
			
			if (isAllowed)
				dropLocation = model.GetVertex(p1);
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
		controller.PlaceSettlement(dropLocation.getPoint());
	}

}
