package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.Coordinate;
import shared.model.map.IMapModel;
import shared.model.map.MapException;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class CityDropObject extends DropObject
{
	private Vertex dropLocation = null;
	
	public CityDropObject(IMapController controller, CatanColor color)
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
			
			isAllowed = controller.CanPlaceCity(p1, color);
			
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
		controller.PlaceCity(dropLocation.getPoint());
	}

}
