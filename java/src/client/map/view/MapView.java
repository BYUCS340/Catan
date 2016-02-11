package client.map.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

import client.base.*;
import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.Edge;
import shared.model.map.objects.Hex;
import shared.model.map.objects.Vertex;

/**
 * Implementation for the map view
 */
@SuppressWarnings("serial")
public class MapView extends PanelView implements IMapView
{
	
	private MapComponent map;
	private MapOverlay overlay;
	
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
		
		overlay = new MapOverlay(map);
		overlay.setController(overlayController);
		overlay.startDrop(pieceType, pieceColor, isCancelAllowed);
		overlay.showModal();
	}
	
	@Override
	public void RefreshView() {
		map.repaint();
	}
	
	private IMapController overlayController = new IMapController() {
		
		@Override
		public IView getView()
		{
			assert false;
			return null;
		}

		@Override
		public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color)
		{
			return getController().CanPlaceRoad(p1, p2, color);
		}

		@Override
		public boolean CanPlaceSettlement(Coordinate point)
		{
			return getController().CanPlaceSettlement(point);
		}

		@Override
		public boolean CanPlaceCity(Coordinate point, CatanColor color)
		{
			return getController().CanPlaceCity(point, color);
		}

		@Override
		public boolean CanPlaceRobber(Coordinate point) 
		{
			return getController().CanPlaceRobber(point);
		}

		@Override
		public Iterator<Hex> GetHexes()
		{
			return getController().GetHexes();
		}

		@Override
		public Iterator<Edge> GetEdges()
		{
			return getController().GetEdges();
		}

		@Override
		public Iterator<Vertex> GetVertices()
		{
			return getController().GetVertices();
		}

		@Override
		public Iterator<Entry<Edge, Hex>> GetPorts()
		{
			return getController().GetPorts();
		}

		@Override
		public Iterator<Entry<Integer, List<Hex>>> GetPips()
		{
			return getController().GetPips();
		}

		@Override
		public Hex GetRobberPlacement() throws MapException
		{
			return getController().GetRobberPlacement();
		}

		@Override
		public void StartMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void CancelMove() {
			// TODO Auto-generated method stub
			
		}
	};
	
	private static class MapOverlay extends OverlayView
	{
		
		private final int LABEL_TEXT_SIZE = 40;
		private final int BUTTON_TEXT_SIZE = 28;
		private final int BORDER_WIDTH = 10;
		
		private MapComponent mainMap;
		private JLabel label;
		private MapComponent map;
		private JButton cancelButton;
		
		public MapOverlay(MapComponent mainMap)
		{
			
			super();
			
			this.mainMap = mainMap;
		}
		
		@Override
		public IMapController getController()
		{
			return (IMapController)super.getController();
		}
		
		public void startDrop(PieceType pieceType, CatanColor pieceColor,
							  boolean isCancelAllowed)
		{
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.setBorder(BorderFactory.createLineBorder(Color.black,
														  BORDER_WIDTH));
			
			label = new JLabel(getLabelText(pieceType), JLabel.CENTER);
			label.setOpaque(true);
			label.setBackground(Color.white);
			Font labelFont = label.getFont();
			labelFont = labelFont.deriveFont(labelFont.getStyle(),
											 LABEL_TEXT_SIZE);
			label.setFont(labelFont);
			
			map = mainMap.copy();
			map.setController(getController());
			
			int prefWidth = (int)(mainMap.getScale() * mainMap.getPreferredSize()
															  .getWidth());
			int prefHeight = (int)(mainMap.getScale() * mainMap.getPreferredSize()
															   .getHeight());
			Dimension prefSize = new Dimension(prefWidth, prefHeight);
			map.setPreferredSize(prefSize);
			
			this.add(label, BorderLayout.NORTH);
			this.add(map, BorderLayout.CENTER);
			
			if(isCancelAllowed)
			{
				
				cancelButton = new JButton("Cancel");
				Font buttonFont = cancelButton.getFont();
				buttonFont = buttonFont.deriveFont(buttonFont.getStyle(),
												   BUTTON_TEXT_SIZE);
				cancelButton.setFont(buttonFont);
				cancelButton.addActionListener(cancelButtonListener);
				this.add(cancelButton, BorderLayout.SOUTH);
			}
			
			map.startDrop(pieceType, pieceColor);
		}
		
		private ActionListener cancelButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				getController().CancelMove();
			}
		};
		
		private String getLabelText(PieceType pieceType)
		{
			switch (pieceType)
			{
				case ROAD:
					return "Place a Road!";
				case SETTLEMENT:
					return "Place a Settlement!";
				case CITY:
					return "Place a City!";
				case ROBBER:
					return "Move the Robber!";
				default:
					assert false;
					return "";
			}
		}
	}

	@Override
	public void SetModel(MapModel model) {
		// TODO Auto-generated method stub
		
	}
}

