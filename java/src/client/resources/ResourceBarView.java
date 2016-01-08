package client.resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import shared.definitions.ResourceType;
import client.base.*;
import client.discard.DiscardView;
import client.utils.FontUtils;
import client.utils.ImageUtils;

	
/*
 * These are the types of ResourceBarElement:
 * 		WOOD
 * 		BRICK
 * 		SHEEP
 * 		WHEAT
 * 		ORE
 * 
 * 		ROAD
 * 		SETTLEMENT
 * 		CITY
 * 		BUY_CARD
 * 
 * 		PLAY_CARD
 * 
 * 		SOLDIERS
 */


/**
 * Implementation for the resource bar view
 */
@SuppressWarnings({"serial", "unused"})
public class ResourceBarView extends PanelView implements IResourceBarView
{
	private final boolean TESTING = false;
	
	private final String RESOURCE_IMAGE_PATH = "images" + File.separator +
											   "resources" + File.separator;
	private final String BUILDING_IMAGE_PATH = "images" + File.separator +
											   "building" + File.separator;
	
//	private final String WOOD = ResourceBarElement.WOOD.toString();
//	private final String BRICK = ResourceBarElement.BRICK.toString();
//	private final String SHEEP = ResourceBarElement.SHEEP.toString();
//	private final String WHEAT = ResourceBarElement.WHEAT.toString();
//	private final String ORE = ResourceBarElement.ORE.toString();
//	
//	private final String ROAD = ResourceBarElement.ROAD.toString();
//	private final String SETTLEMENT = ResourceBarElement.SETTLEMENT.toString();
//	private final String CITY = ResourceBarElement.CITY.toString();
//	private final String BUY_CARD = ResourceBarElement.BUY_CARD.toString();
//	private final String PLAY_CARD = ResourceBarElement.PLAY_CARD.toString();
//	private final String SOLDIERS = ResourceBarElement.SOLDIERS.toString();
	
	private JPanel resourcePanel;
//	private JPanel buildingPanel;
//	private JPanel devCardPanel;
//	private JPanel armyPanel;
	
	private JLabel label;
	private JButton testButton;
	
	private BufferedImage brickImage;
	private BufferedImage oreImage;
	private BufferedImage sheepImage;
	private BufferedImage wheatImage;
	private BufferedImage woodImage;
	
	private BufferedImage roadImage;
	private BufferedImage settlementImage;
	private BufferedImage cityImage;
	private BufferedImage buyDevCardImage;
	private BufferedImage playDevCardImage;
	private BufferedImage soldierImage;
	
	private Map<ResourceBarElement, ResourceElement> resources;
	private List<ResourceBarElement> resourceElementList;
	
	public ResourceBarView()
	{
		
		// TEMPORARY
		this.add(new JLabel("Resource Bar View"));
		
		this.addMouseListener(mouseAdapter);
		this.addKeyListener(keyAdapter);
		
		this.initialize();
	}
	
	private void initialize()
	{
		this.loadImages();
		this.initializeResources();
		this.initializeView();
	}
	
	private void loadImages()
	{
		brickImage = ImageUtils.loadImage(RESOURCE_IMAGE_PATH + "brick.png");
		oreImage = ImageUtils.loadImage(RESOURCE_IMAGE_PATH + "ore.png");
		sheepImage = ImageUtils.loadImage(RESOURCE_IMAGE_PATH + "sheep.png");
		wheatImage = ImageUtils.loadImage(RESOURCE_IMAGE_PATH + "wheat.png");
		woodImage = ImageUtils.loadImage(RESOURCE_IMAGE_PATH + "wood.png");
		roadImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "road.png");
		settlementImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "settlement.png");
		cityImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "city.png");
		buyDevCardImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "card.jpg");
		playDevCardImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "development_card.jpg");
		soldierImage = ImageUtils.loadImage(BUILDING_IMAGE_PATH + "soldier.jpg");
	}
	
	private void initializeResources()
	{
		resources = new HashMap<>();
		resourceElementList = new ArrayList<>();
		
		// These resources are not clickable
		ResourceElement brick = new ResourceElement(ResourceBarElement.BRICK, false);
		ResourceElement ore = new ResourceElement(ResourceBarElement.ORE, false);
		ResourceElement sheep = new ResourceElement(ResourceBarElement.SHEEP, false);
		ResourceElement wheat = new ResourceElement(ResourceBarElement.WHEAT, false);
		ResourceElement wood = new ResourceElement(ResourceBarElement.WOOD, false);
		
		brick.setElementImage(brickImage);
		ore.setElementImage(oreImage);
		sheep.setElementImage(sheepImage);
		wheat.setElementImage(wheatImage);
		wood.setElementImage(woodImage);
		
		// These resources are clickable
		ResourceElement road = new ResourceElement(ResourceBarElement.ROAD, true);
		ResourceElement settlement = new ResourceElement(ResourceBarElement.SETTLEMENT, true);
		ResourceElement city = new ResourceElement(ResourceBarElement.CITY, true);
		ResourceElement buyDevCard = new ResourceElement(ResourceBarElement.BUY_CARD, true);
		
		road.setElementImage(roadImage);
		road.setEnabled(true);
		settlement.setElementImage(settlementImage);
		settlement.setEnabled(true);
		city.setElementImage(cityImage);
		city.setEnabled(true);
		buyDevCard.setElementImage(buyDevCardImage);
		buyDevCard.setElementCount(-1);
		buyDevCard.setEnabled(true);
		
		// This resource is clickable
		ResourceElement playDevCard = new ResourceElement(ResourceBarElement.PLAY_CARD, true);
		playDevCard.setElementImage(playDevCardImage);
		playDevCard.setElementCount(-1);
		playDevCard.setEnabled(true);
		
		// This resource are not clickable
		ResourceElement soldier = new ResourceElement(ResourceBarElement.SOLDIERS, false);
		soldier.setElementImage(soldierImage);
		
		
		// This is to determine the order of the Elements in the
		// ResourceBar.
		resourceElementList.add(ResourceBarElement.WOOD);
		resourceElementList.add(ResourceBarElement.BRICK);
		resourceElementList.add(ResourceBarElement.SHEEP);
		resourceElementList.add(ResourceBarElement.WHEAT);
		resourceElementList.add(ResourceBarElement.ORE);
		
		resourceElementList.add(ResourceBarElement.ROAD);
		resourceElementList.add(ResourceBarElement.SETTLEMENT);
		resourceElementList.add(ResourceBarElement.CITY);
		resourceElementList.add(ResourceBarElement.BUY_CARD);
		
		resourceElementList.add(ResourceBarElement.PLAY_CARD);
		
		resourceElementList.add(ResourceBarElement.SOLDIERS);
		
		
		resources.put(ResourceBarElement.WOOD, wood);
		resources.put(ResourceBarElement.BRICK, brick);
		resources.put(ResourceBarElement.SHEEP, sheep);
		resources.put(ResourceBarElement.WHEAT, wheat);
		resources.put(ResourceBarElement.ORE, ore);
		
		resources.put(ResourceBarElement.ROAD, road);
		resources.put(ResourceBarElement.SETTLEMENT, settlement);
		resources.put(ResourceBarElement.CITY, city);
		resources.put(ResourceBarElement.BUY_CARD, buyDevCard);
		
		resources.put(ResourceBarElement.PLAY_CARD, playDevCard);
		
		resources.put(ResourceBarElement.SOLDIERS, soldier);
	}
	
	private void initializeView()
	{
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		
		
		resourcePanel = new JPanel();
		resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
		resourcePanel.setBackground(Color.WHITE);
		for(ResourceBarElement type : resourceElementList)
		{
			resourcePanel.add(resources.get(type).asJComponent());
		}
		
		this.add(resourcePanel, BorderLayout.CENTER);
		
		
		JPanel discardButtonPanel = new JPanel();
		discardButtonPanel.setBackground(Color.WHITE);
		
		if(TESTING)
		{
    		testButton = new JButton("Enable");
    		testButton.addActionListener(actionListener);
    		discardButtonPanel.add(testButton);
		}
		this.setBackground(Color.WHITE);
		this.add(discardButtonPanel, BorderLayout.SOUTH);
		
	}
	
//	private void initializeResourcePanel()
//	{
//		resourcePanel = new JPanel();
//	}
//	
//	private void initializeBuildingPanel()
//	{
//		buildingPanel = new JPanel();
//	}
//	
//	private void initializeDevCardPanel()
//	{
//		devCardPanel = new JPanel();
//	}
//	
//	private void initializeArmyPanel()
//	{
//		armyPanel = new JPanel();
//	}
	
	private ActionListener actionListener = new ActionListener() {
		private boolean enabled = false;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == testButton)
			{
				enabled = !enabled;
				if(enabled)
					testButton.setText("Disable");
				else
					testButton.setText("Enable");
			} else {
//				System.out.println(e.getSource().getClass().getName());
				switch(ResourceBarElement.valueOf(e.getActionCommand()))
				{
					case ROAD:
						getController().buildRoad();
						break;
					case SETTLEMENT:
						getController().buildSettlement();
						break;
					case CITY:
						getController().buildCity();
						break;
					case BUY_CARD:
						getController().buyCard();
						break;
					case PLAY_CARD:
						getController().playCard();
						break;
					default:
						System.out.println(e.getActionCommand());
						break;
				}
			}
		}
	};
	
	@Override
	public IResourceBarController getController()
	{
		return (IResourceBarController)super.getController();
	}
	
	@Override
	public void setElementEnabled(ResourceBarElement element, boolean enabled)
	{   
		resources.get(element).setEnabled(enabled);
	}
	
	@Override
	public void setElementAmount(ResourceBarElement element, int amount)
	{   
		resources.get(element).setElementCount(amount);
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e)
		{
			ResourceBarView.this.requestFocus();
		}
	};
	
	private KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyTyped(KeyEvent e)
		{
			switch (e.getKeyChar())
			{
				case '1':
					getController().buildRoad();
					break;
				case '2':
					getController().buildSettlement();
					break;
				case '3':
					getController().buildCity();
					break;
				case '4':
					getController().buyCard();
					break;
				case '5':
					getController().playCard();
					break;
			}
		}
	};
	
	private class ResourceElement
	{
		private static final int PADDING = 5;
		private static final int IMAGE_SIZE = 50;
		private static final int LABEL_FONT_SIZE = 30;
		
		private static final float SCALE = 0.8f;
		private static final float R_SCALE = SCALE;
		private static final float G_SCALE = SCALE;
		private static final float B_SCALE = SCALE;
		private static final float A_SCALE = 0.4f;
		
		private static final float R_OFFSET = 0.0f;
		private static final float G_OFFSET = 0.0f;
		private static final float B_OFFSET = 0.0f;
		private static final float A_OFFSET = 0.0f;
		
		private boolean _clickable;
		private boolean _enabled;
		private ResourceBarElement _type;
		private BufferedImage _elementImage;
		private BufferedImage _disabledImage;
		private int _elementCount;
		
		private JPanel _resourceElementPanel;
		private JButton _elementImageButton;
		private JLabel _elementCountLabel;
		
		public ResourceElement()
		{
			this(ResourceBarElement.ROAD);
		}

		public ResourceElement(ResourceBarElement type)
		{
			this(type, false, 0);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable)
		{
			this(type, clickable, 0);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       int elementCount)
		{
			this(type, false, elementCount);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable,
		                       int elementCount)
		{
			this(type, clickable, false, elementCount);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable,
		                       BufferedImage elementImage)
		{
			this(type, clickable, elementImage, 0);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable,
		                       BufferedImage elementImage,
		                       int elementCount)
		{
			this(type, clickable, false, elementImage, elementCount);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable,
		                       boolean enabled,
		                       int elementCount)
		{
			this.initialize();
			this.setElementType(type);
			this.setClickable(clickable);
			this.setEnabled(enabled);
			this.setElementImage(new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));
			this.setElementCount(elementCount);
		}
		
		public ResourceElement(ResourceBarElement type,
		                       boolean clickable,
		                       boolean enabled,
		                       BufferedImage elementImage,
		                       int elementCount)
		{
			this.initialize();
			this.setElementType(type);
			this.setClickable(clickable);
			this.setEnabled(enabled);
			this.setElementImage(elementImage);
			this.setElementCount(elementCount);
		}
		
		private void initialize()
		{
			_resourceElementPanel = new JPanel();
			_resourceElementPanel.setLayout(new BoxLayout(_resourceElementPanel,
			                                              BoxLayout.X_AXIS));
			_elementImageButton = new JButton();
			_elementCountLabel = new JLabel();
			FontUtils.setFont(_elementCountLabel, LABEL_FONT_SIZE);
		}
		
		public void setElementType(ResourceBarElement type)
		{
			this._type = type;
			this.update();
		}
		
		public ResourceBarElement getElementType()
		{
			return this._type;
		}
		
		public void setClickable(boolean clickable)
		{
			this._clickable = clickable;
			this.update();
		}
		
		public boolean isClickable()
		{
			return this._clickable;
		}
		
		public void setEnabled(boolean enabled)
		{
			this._enabled = enabled;
			this.update();
		}
		
		public boolean isEnabled()
		{
			return this._enabled;
		}
		
		public void setElementImage(BufferedImage elementImage)
		{
			BufferedImage image;
			
			image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			double heightScale = (double)IMAGE_SIZE / 
								 (double)(elementImage.getHeight(null));
			double widthScale = (double)IMAGE_SIZE / 
								(double)(elementImage.getWidth(null));
			g.scale(widthScale, heightScale);
			g.drawImage(elementImage, 0, 0, null);
			
			this._elementImage = image;

			float[] scaleFactors = {R_SCALE, G_SCALE, B_SCALE, A_SCALE};
			float[] offsetFactors = {R_OFFSET, G_OFFSET, B_OFFSET, A_OFFSET};
			
			this._disabledImage = new RescaleOp(scaleFactors,
			                                   offsetFactors,
			                                   null).filter(image, null);
			this.update();
		}
		
		public void setElementCount(int elementCount)
		{
			this._elementCount = elementCount;
			this.update();
		}
		
		public int getElementCount()
		{
			return this._elementCount;
		}
		
		public void update()
		{
			_elementImageButton.setEnabled(this.isEnabled());
			if(_elementCount >= 0)
				_elementCountLabel.setText(""+_elementCount);
			_resourceElementPanel.repaint();
		}
		
		public JComponent asJComponent()
		{
			this.initialize();
			
			JPanel elementPanel = new JPanel(new GridBagLayout());
			elementPanel.setBackground(Color.WHITE);
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.25;
			c.weighty = 0.25;
			
			ImageIcon enabledIcon = new ImageIcon(_elementImage);
			_elementImageButton.setIcon(enabledIcon);
			
			if(this.isClickable())
			{
				ImageIcon disabledIcon = new ImageIcon(_disabledImage);
				_elementImageButton.setDisabledIcon(disabledIcon);
			} else {

				_elementImageButton.setDisabledIcon(enabledIcon);
			}

			_elementImageButton.setEnabled(this.isEnabled());
			_elementImageButton.setOpaque(false);
			_elementImageButton.setMargin(new Insets(0, 0, 0, 0));
			_elementImageButton.setContentAreaFilled(false);
			_elementImageButton.setBorderPainted(false);
			_elementImageButton.setActionCommand(this.getElementType().toString());
			_elementImageButton.addActionListener(actionListener);
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
//			c.insets = new Insets(0, 0, 0, 0);
			elementPanel.add(_elementImageButton, c);
			
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
//			c.insets = new Insets(0, 0, 0, 0);
    			
			if(_elementCount >= 0) {
    			_elementCountLabel = new JLabel(""+_elementCount, JLabel.LEFT);
    			FontUtils.setFont(_elementCountLabel, LABEL_FONT_SIZE);
    			elementPanel.add(_elementCountLabel, c);
			} else {
				JLabel label = new JLabel();
				FontUtils.setFont(label, LABEL_FONT_SIZE);
    			elementPanel.add(label, c);
			}
			
			_resourceElementPanel.setBackground(Color.WHITE);
			_resourceElementPanel.add(elementPanel);
			
			return _resourceElementPanel;
		}
	}
}

