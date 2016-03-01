package client.map.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import client.base.*;
import client.map.*;
import client.map.view.dropObject.*;

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
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		label = new JLabel("", JLabel.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.white);
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		
		cancelButton = new JButton("Cancel");
		Font buttonFont = cancelButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
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
		
		map.setController(controller);
		getController().AddMapObserver(mapObserver);
	}
	
	@Override
	public IMapController getController()
	{
		return (IMapController)super.getController();
	}
	
	private String getLabelText(DropObject pieceType)
	{
		if (pieceType.getClass() == RoadDropObject.class)
			return "Place a Road!";
		else if (pieceType.getClass() == SettlementDropObject.class)
			return "Place a Settlement!";
		else if (pieceType.getClass() == CityDropObject.class)
			return "Place a City!";
		else if (pieceType.getClass() == RobberDropObject.class)
			return "Move the Robber!";
		else
			return "";
	}
	
	private void Repaint()
	{
		if (isModalShowing())
			map.repaint();
	}
	
	private MapObserver mapObserver = new MapObserver()
	{
		@Override
		public void StartDrag(boolean isCancelAllowed)
		{
			DropObject dropObject = getController().GetDropObject();
			
			if (dropObject.getClass() == NoDrop.class)
			{
				EndDrag();
				return;
			}
			
			label.setText(getLabelText(getController().GetDropObject()));
			cancelButton.setVisible(isCancelAllowed);
			
			map.temp = true;
			
			if (!isModalShowing())
				showModal();
			Repaint();
		}

		@Override
		public void EndDrag()
		{
			if (isModalShowing())
				closeModal();
		}
	};

	private ActionListener cancelButtonListener = new ActionListener()
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			getController().CancelMove();
		}
	};
}
