package client.map.view.dropObject;

import java.awt.geom.Point2D;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.Hex;

/**
 * Handles the placement of the robber on the map.
 * @author Jonathan Sadler
 *
 */
public class RobberDropObject extends DropObject 
{
	private Coordinate hexPoint;
	
	/**
	 * Creates a RobberDropObject.
	 * @param controller The controller associated with the object.
	 * @param color The color of the object.
	 */
	public RobberDropObject(IMapController controller, CatanColor color) 
	{
		super(controller, color);
	}
	
	/**
	 * Gets the hex that is currently associated with the object.
	 * @return The associated hex.
	 * @throws MapException Thrown if issues arrise getting the hex.
	 */
	public Hex GetDropLocation() throws MapException
	{
		return controller.GetModel().GetHex(hexPoint);
	}
	
	@Override
	public boolean IsValid()
	{
		if (hexPoint == null)
			return false;
		
		return controller.GetModel().ContainsHex(hexPoint);
	}
	
	@Override
	public boolean IsAllowed()
	{
		if (!IsValid())
			return false;
		
		return controller.CanPlaceRobber(hexPoint);
	}

	@Override
	public void Handle(Point2D point)
	{
		hexPoint = GetClosestHexCoordinate(point);
	}

	@Override
	public void Click()
	{
		controller.CancelMove();
		controller.PlaceRobber(hexPoint);
	}
}
