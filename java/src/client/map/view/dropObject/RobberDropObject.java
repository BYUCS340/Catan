package client.map.view.dropObject;

import java.awt.geom.Point2D;

import client.map.IMapController;
import shared.definitions.CatanColor;
import shared.model.map.*;
import shared.model.map.objects.Hex;

public class RobberDropObject extends DropObject 
{
	private Hex dropLocation;
	
	public RobberDropObject(IMapController controller, CatanColor color) 
	{
		super(controller, color);
	}
	
	public Hex GetDropLocation()
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
			isAllowed = controller.CanPlaceRobber(closestHex);
			
			if (isAllowed)
				dropLocation = model.GetHex(closestHex);
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
		controller.PlaceRobber(dropLocation.getPoint());
	}

}
