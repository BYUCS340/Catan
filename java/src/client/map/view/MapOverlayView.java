package client.map.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import client.base.*;
import client.map.IMapController;
import client.map.view.dropObject.DropObject;
import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.*;

@SuppressWarnings("serial")
public class MapOverlayView extends OverlayView
{
	private final int LABEL_TEXT_SIZE = 40;
	private final int BUTTON_TEXT_SIZE = 28;
	private final int BORDER_WIDTH = 10;
	
	private JLabel label;
	private MapComponent map;
	private JButton cancelButton;
	
	public MapOverlayView()
	{
		super();
		
		map = new MapComponent();
		
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black,
													  BORDER_WIDTH));
		label = new JLabel("", JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.white);
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(),
										 LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		
		int prefWidth = (int)(map.getScale() * map.getPreferredSize()
														  .getWidth());
		int prefHeight = (int)(map.getScale() * map.getPreferredSize()
														   .getHeight());
		Dimension prefSize = new Dimension(prefWidth, prefHeight);
		map.setPreferredSize(prefSize);
		
		cancelButton = new JButton("Cancel");
		Font buttonFont = cancelButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(),
										   BUTTON_TEXT_SIZE);
		cancelButton.setFont(buttonFont);
		cancelButton.addActionListener(cancelButtonListener);
		
		this.add(label, BorderLayout.NORTH);
		this.add(map, BorderLayout.CENTER);
		this.add(cancelButton, BorderLayout.SOUTH);
	}

	@Override
	public void setController(IController controller)
	{
		super.setController(controller);
		
		map.setController(new OverlayController());
	}
	
	@Override
	public IMapController getController()
	{
		return (IMapController)super.getController();
	}
	
	public void startDrop(PieceType pieceType, CatanColor pieceColor,
			  boolean isCancelAllowed)
	{
		label.setText(getLabelText(pieceType));
		
		cancelButton.setVisible(isCancelAllowed);
		
		getController().StartMove(pieceType, pieceColor, true);
		
		showModal();
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
	
	private class OverlayController implements IMapController
	{
		@Override
		public IView getView()
		{
			return getView();
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
		public boolean IsRobberInitialized()
		{
			return getController().IsRobberInitialized();
		}

		@Override
		public DropObject GetDropObject()
		{
			return getController().GetDropObject();
		}

		@Override
		public void PlaceRoad(Coordinate p1, Coordinate p2)
		{
			closeModal();
			getController().PlaceRoad(p1, p2);
		}

		@Override
		public void PlaceSettlement(Coordinate point)
		{
			closeModal();
			getController().PlaceSettlement(point);
		}

		@Override
		public void PlaceCity(Coordinate point)
		{
			closeModal();
			getController().PlaceCity(point);
		}

		@Override
		public void PlaceRobber(Coordinate point)
		{
			closeModal();
			getController().PlaceRobber(point);
		}

		@Override
		public void StartMove(PieceType pieceType, CatanColor color, boolean allowDisconnected)
		{
			assert(false);
		}

		@Override
		public void CancelMove()
		{
			closeModal();
			getController().CancelMove();
		}

		@Override
		public void MouseMove(Point2D worldPoint)
		{
			getController().MouseMove(worldPoint);
		}

		@Override
		public void MouseClick()
		{
			getController().MouseClick();
		}

		@Override
		public IMapModel GetModel()
		{
			return getController().GetModel();
		}
	}

	private ActionListener cancelButtonListener = new ActionListener()
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			closeModal();
			getController().CancelMove();
		}
	};
}
