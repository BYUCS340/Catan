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

/**
 * Handles the placement of cities on the map.
 * @author Jonathan Sadler
 *
 */
public class CityDropObject extends DropObject
{
	private Coordinate vertex = null;
	
	/**
	 * Creates a city drop object.
	 * @param controller The controller associated with the object.
	 * @param color The color of the object.
	 */
	public CityDropObject(IMapController controller, CatanColor color)
	{
		super(controller, color);
	}
	
	/**
	 * Gets the vertex associated with the object.
	 * @return The vertex of the object.
	 * @throws MapException Thrown if the vertex isn't valid.
	 */
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
		controller.CancelMove();
		controller.PlaceCity(vertex);
	}
}
