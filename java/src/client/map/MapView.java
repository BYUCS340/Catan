package client.map;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import client.base.*;
import client.data.*;
import shared.definitions.*;
import shared.locations.*;

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
	public void addHex(HexLocation hexLoc, HexType hexType)
	{
		map.addHex(hexLoc, hexType);
	}
	
	@Override
	public void addNumber(HexLocation hexLoc, int num)
	{
		map.addNumber(hexLoc, num);
	}
	
	@Override
	public void addPort(EdgeLocation edgeLoc, PortType portType)
	{
		map.placePort(edgeLoc, portType);
	}
	
	@Override
	public void placeRoad(EdgeLocation edgeLoc, CatanColor color)
	{
		map.placeRoad(edgeLoc, color);
	}
	
	@Override
	public void placeSettlement(VertexLocation vertLoc, CatanColor color)
	{
		map.placeSettlement(vertLoc, color);
	}
	
	@Override
	public void placeCity(VertexLocation vertLoc, CatanColor color)
	{
		map.placeCity(vertLoc, color);
	}
	
	@Override
	public void placeRobber(HexLocation hexLoc)
	{
		map.placeRobber(hexLoc);
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
	
	private IMapController overlayController = new IMapController() {
		
		@Override
		public IView getView()
		{
			assert false;
			return null;
		}
		
		@Override
		public boolean canPlaceRoad(EdgeLocation edgeLoc)
		{
			return getController().canPlaceRoad(edgeLoc);
		}
		
		@Override
		public boolean canPlaceSettlement(VertexLocation vertLoc)
		{
			return getController().canPlaceSettlement(vertLoc);
		}
		
		@Override
		public boolean canPlaceCity(VertexLocation vertLoc)
		{
			return getController().canPlaceCity(vertLoc);
		}
		
		@Override
		public boolean canPlaceRobber(HexLocation hexLoc)
		{
			return getController().canPlaceRobber(hexLoc);
		}
		
		@Override
		public void placeRoad(EdgeLocation edgeLoc)
		{
			
			closeModal();
			getController().placeRoad(edgeLoc);
		}
		
		@Override
		public void placeSettlement(VertexLocation vertLoc)
		{
			
			closeModal();
			getController().placeSettlement(vertLoc);
		}
		
		@Override
		public void placeCity(VertexLocation vertLoc)
		{
			
			closeModal();
			getController().placeCity(vertLoc);
		}
		
		@Override
		public void placeRobber(HexLocation hexLoc)
		{
			
			closeModal();
			getController().placeRobber(hexLoc);
		}
		
		@Override
		public void startMove(PieceType pieceType, boolean isFree,
							  boolean allowDisconnected)
		{
			assert false;
		}
		
		@Override
		public void cancelMove()
		{
			
			closeModal();
			getController().cancelMove();
		}
		
		@Override
		public void playSoldierCard()
		{
			assert false;
		}
		
		@Override
		public void playRoadBuildingCard()
		{
			assert false;
		}
		
		@Override
		public void robPlayer(RobPlayerInfo victim)
		{
			assert false;
		}
		
		private void closeModal()
		{
			overlay.cancelDrop();
			overlay.closeModal();
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
				getController().cancelMove();
			}
		};
		
		public void cancelDrop()
		{
			
			map.cancelDrop();
		}
		
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
	
}

