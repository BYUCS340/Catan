package client.map.view;

import java.awt.*;

import client.base.*;
import client.map.IMapController;
import client.map.MapObserver;

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
		getController().AddMapObserver(mapObserver);
	}
	
	@Override
	public void RefreshView()
	{
		map.repaint();
	}

	private MapObserver mapObserver = new MapObserver()
	{
		@Override
		public void StartDrag(boolean cancelAllowed)
		{
			RefreshView();
		}

		@Override
		public void EndDrag()
		{
			RefreshView();
		}
	};
}

