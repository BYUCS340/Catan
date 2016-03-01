package client.catan;

import java.awt.*;
import javax.swing.*;

import client.map.*;
import client.map.IMapController;
import client.map.view.*;

@SuppressWarnings("serial")
public class MidPanel extends JPanel
{

	private TradePanel tradePanel;
	private MapView mapView;
	private MapOverlayView mapOverlayView;
	private MapController mapController;
	private GameStatePanel gameStatePanel;

	public MidPanel()
	{
		this.setLayout(new BorderLayout());

		tradePanel = new TradePanel();

		mapView = new MapView();
		mapOverlayView = new MapOverlayView();
		
		mapController = new MapController(mapView);
		//Give the game the right map controller
		mapView.setController(mapController);
		mapOverlayView.setController(mapController);

		gameStatePanel = new GameStatePanel();

		this.add(tradePanel, BorderLayout.NORTH);
		this.add(mapView, BorderLayout.CENTER);
		this.add(gameStatePanel, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(800, 700));
	}

	public GameStatePanel getGameStatePanel()
	{
		return gameStatePanel;
	}

	public IMapController getMapController()
	{
		return this.mapController;
	}

}
