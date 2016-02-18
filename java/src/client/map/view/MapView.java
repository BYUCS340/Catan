package client.map.view;

import java.awt.*;

import client.base.*;
import client.map.IMapController;
import shared.definitions.*;

/**
 * Implementation for the map view
 */
@SuppressWarnings("serial")
public class MapView extends PanelView implements IMapView
{
	private MapComponent map;
	
	public MapView()
	{
		this.setLayout(new BorderLayout());
		
		map = new MapComponent();
		
		this.add(map, BorderLayout.CENTER);
	}
	
	@Override
	public IMapController getController()
	{
		return (IMapController)super.getController();
	}
	
	@Override
	public void setController(IController controller)
	{
		super.setController(controller);
		
		map.setController(controller);
	}
	
	@Override
	public void startDrop(PieceType pieceType, CatanColor pieceColor,
						  boolean isCancelAllowed)
	{
		assert(false);
	}
	
	@Override
	public void RefreshView()
	{
		map.repaint();
	}
}

