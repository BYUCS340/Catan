package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.model.IMapModel;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

public class CityDropObject extends DropObject
{
	private Coordinate vertex = null;
	
	public CityDropObject(IMapController controller, CatanColor color)
	{
		super(controller, color);
	}
	
	public Vertex GetDropLocation() throws MapException
	{
		return controller.GetModel().GetVertex(vertex);
	}
	
	@Override
	public boolean IsValid()
	{
		if (vertex == null)
			return false;
		
		return controller.GetModel().ContainsVertex(vertex);
	}

	@Override
	public boolean IsAllowed()
	{
		if (!IsValid())
			return false;
		
		return controller.CanPlaceCity(vertex, color);
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
			
			vertex = v1.getPoint();
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void Click()
	{
		controller.PlaceCity(vertex);
	}
}
