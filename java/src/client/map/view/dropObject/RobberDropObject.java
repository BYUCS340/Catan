package client.map.view.dropObject;

import java.awt.geom.Point2D;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.Hex;

public class RobberDropObject extends DropObject 
{
	private Coordinate hexPoint;
	
	public RobberDropObject(IMapController controller, CatanColor color) 
	{
		super(controller, color);
	}
	
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
