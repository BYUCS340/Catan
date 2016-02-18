package client.map.view.dropObject;

import java.awt.geom.Point2D;

public class NoDrop extends DropObject 
{
	public NoDrop()
	{
		super(null, null);
	}

	@Override
	public boolean IsValid()
	{
		return false;
	}
	
	@Override
	public boolean IsAllowed()
	{
		return false;
	}
	
	@Override
	public void Handle(Point2D point)
	{
		return;
	}

	@Override
	public void Click() {
		return;
	}
}
