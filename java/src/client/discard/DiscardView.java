package client.discard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import shared.definitions.ResourceType;
import client.base.OverlayView;
import client.custom.cwt.RoundedButton;
import client.utils.ImageUtils;
import client.utils.FontUtils;

/*
 * IDiscardController Overview
 * 
 * 	 This method is called when the user increases the amount of the specified
 * 	 	resource.
 * 	 @param resource The resource that was increased 
 * 	void increaseAmount(ResourceType resource);
 * 
 * 	 This method is called when the user decreases the amount of the specified
 * 	 	resource.
 * 	 @param resource The resource that was decreased
 * 	void decreaseAmount(ResourceType resource);
 * 
 * 	 This method is called when the user clicks the discard button.
 *  void discard();
 */

/**
 * Discard view implementation
 */
@SuppressWarnings({"serial", "unused"})
public class DiscardView extends OverlayView implements IDiscardView
{
	private final boolean TESTING = false;
	
	private final int LABEL_TEXT_SIZE = 20;
	private final int BUTTON_TEXT_SIZE = 14;
	private final int BORDER_WIDTH = 7;
	
	private final String RESOURCE_IMAGE_PATH = "images" + File.separator +
											   "resources" + File.separator;
	private final String MISC_IMAGE_PATH = "images" + File.separator +
										   "misc" + File.separator;
	
	private JLabel label;
	private JButton discardButton;
	private RoundedButton testButton;
	private JPanel resourcePanel;
	
	private BufferedImage brickImage;
	private BufferedImage oreImage;
	private BufferedImage sheepImage;
	private BufferedImage wheatImage;
	private BufferedImage woodImage;
	private BufferedImage upImage;
	private BufferedImage downImage;
	
	private Map<ResourceType, Resource> resources;
	private List<ResourceType> resourceList;
	
	public DiscardView()
	{
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
		upImage = ImageUtils.loadImage(MISC_IMAGE_PATH + "up.png");
		downImage = ImageUtils.loadImage(MISC_IMAGE_PATH + "down.png");
	}
	
	private void initializeImages() { this.loadImages(); }
	
	private void initializeResources()
	{
		resources = new HashMap<>();
		resourceList = new ArrayList<>();
		
		Resource brick = new Resource(ResourceType.BRICK);
		brick.setResourceImage(brickImage);
		brick.setUpArrowImage(upImage);
		brick.setDownArrowImage(downImage);
		
		Resource ore = new Resource(ResourceType.ORE);
		ore.setResourceImage(oreImage);
		ore.setUpArrowImage(upImage);
		ore.setDownArrowImage(downImage);
		
		Resource sheep = new Resource(ResourceType.SHEEP);
		sheep.setResourceImage(sheepImage);
		sheep.setUpArrowImage(upImage);
		sheep.setDownArrowImage(downImage);
		
		Resource wheat = new Resource(ResourceType.WHEAT);
		wheat.setResourceImage(wheatImage);
		wheat.setUpArrowImage(upImage);
		wheat.setDownArrowImage(downImage);
		
		Resource wood = new Resource(ResourceType.WOOD);
		wood.setResourceImage(woodImage);
		wood.setUpArrowImage(upImage);
		wood.setDownArrowImage(downImage);
		
		resourceList.add(ResourceType.WOOD);
		resourceList.add(ResourceType.BRICK);
		resourceList.add(ResourceType.SHEEP);
		resourceList.add(ResourceType.WHEAT);
		resourceList.add(ResourceType.ORE);
		
		resources.put(ResourceType.BRICK, brick);
		resources.put(ResourceType.ORE, ore);
		resources.put(ResourceType.SHEEP, sheep);
		resources.put(ResourceType.WHEAT, wheat);
		resources.put(ResourceType.WOOD, wood);
	}

	private void initializeView()
	{
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		label = new JLabel("More than Seven Cards: Discard");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		this.add(label, BorderLayout.NORTH);
		
		resourcePanel = new JPanel();
		resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.X_AXIS));
		for(ResourceType type : resourceList)
		{
			resourcePanel.add(resources.get(type).asJComponent());
		}
		
		this.add(resourcePanel, BorderLayout.CENTER);
		
		discardButton = new JButton("Discard");
		discardButton.setMargin(new Insets(2, 100, 2, 100));
		discardButton.addActionListener(actionListener);
		Font buttonFont = discardButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(),
										   BUTTON_TEXT_SIZE);
		discardButton.setFont(buttonFont);
		
		JPanel discardButtonPanel = new JPanel();
		discardButtonPanel.add(discardButton);
		
		if(TESTING)
		{
    		testButton = new RoundedButton("Enable");
    		testButton.addActionListener(actionListener);
    		discardButtonPanel.add(testButton);
		}
		
		this.add(discardButtonPanel, BorderLayout.SOUTH);
		
	}
	
	private void update()
	{
		for(ResourceType type : resourceList)
			resources.get(type).update();
		resourcePanel.repaint();
	}
	
	@Override
	public IDiscardController getController()
	{
		return (IDiscardController)super.getController();
	}
	
	/**
	 * Used to enable or disable the discard button.
	 * 
	 * @param enabled
	 *            Whether or not the discard button should be enabled
	 */
	@Override
	public void setDiscardButtonEnabled(boolean enabled)
	{
		discardButton.setEnabled(enabled);
		this.update();
	}
	
	/**
	 * Sets the discard amount displayed for the specified resource.
	 * 
	 * @param resource
	 *            The resource for which the discard amount is being set
	 * @param amount
	 *            The new discard amount
	 */
	@Override
	public void setResourceDiscardAmount(ResourceType resource, int amount)
	{
		resources.get(resource).setDiscardAmount(amount);
		this.update();
	}
	
	/**
	 * Sets the maximum amount displayed for the specified resource.
	 * 
	 * @param resource
	 *            The resource for which the maximum amount is being set
	 * @param maxAmount
	 *            The new maximum amount
	 */
	@Override
	public void setResourceMaxAmount(ResourceType resource, int maxAmount)
	{   

		resources.get(resource).setMaxAmount(maxAmount);
		this.update();
	}
	
	/**
	 * Used to specify whether or not the discard amount of the specified
	 * resource can be increased and decreased. (The buttons for increasing or
	 * decreasing the discard amount are only visible when the corresponding
	 * operations are allowed.)
	 * 
	 * @param resource
	 *            The resource for which amount changes are being enabled or
	 *            disabled
	 * @param increase
	 *            Whether or not the amount for the specified resource can be
	 *            increased
	 * @param decrease
	 *            Whether or not the amount for the specified resource can be
	 *            decreased
	 */
	@Override
	public void setResourceAmountChangeEnabled(ResourceType resource,
											   boolean increase,
											   boolean decrease)
	{   
		resources.get(resource).setIncrease(increase);
		resources.get(resource).setDecrease(decrease);
		this.update();
	}
	
	/**
	 * Sets the state message, which indicates how many cards a player has set
	 * to discard, and how many remain to set.
	 * 
	 * @param message
	 *            The new state message (e.g., "0/6")
	 */
	@Override
	public void setStateMessage(String message)
	{   
		discardButton.setText(message);
		this.update();
	}
	
	private ActionListener actionListener = new ActionListener() {
		private boolean enabled = false;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == discardButton)
			{
				getController().discard();
			}
			else if(e.getSource() == testButton)
			{
				enabled = !enabled;
				if(enabled)
					testButton.setText("Disable");
				else
					testButton.setText("Enable");
				DiscardView.this.setDiscardButtonEnabled(enabled);
//				DiscardView.this.update();
			}
		}
	};
	
	private class Resource
	{
		private static final int PADDING = 5;
		private static final int BUTTON_SIZE = 20;
		private static final int LABEL_SIZE = 10;
		private static final int LABEL_FONT_SIZE = 20;
		private static final int IMAGE_SIZE = 100;
		private static final double IMAGE_SCALE = 0.25;
		
		private static final float R_SCALE = 1.0f;
		private static final float G_SCALE = 1.0f;
		private static final float B_SCALE = 1.0f;
		private static final float A_SCALE = 0.0f;
		
		private static final float R_OFFSET = 0.0f;
		private static final float G_OFFSET = 0.0f;
		private static final float B_OFFSET = 0.0f;
		private static final float A_OFFSET = 0.0f;
		
		private ResourceType _type;
		private int _discardAmount;
		private int _maxAmount;
		private boolean _canIncrease;
		private boolean _canDecrease;
		
		private BufferedImage _resourceImage;
		private BufferedImage _upImage;
		private BufferedImage _blankUpImage;
		private BufferedImage _downImage;
		private BufferedImage _blankDownImage;
		
		private JLabel _discardAmountLabel;
		private JLabel _maxAmountLabel;
		
		private JButton _upButton;
		private JButton _downButton;
		
		private JPanel _discardResourcePanel;
		
		public Resource()
		{
			this(ResourceType.BRICK, 0, 0, false, false);
		}

		public Resource(ResourceType type)
		{
			this(type, 0, 0, false, false);
		}
		
		public Resource(ResourceType type,
		                int maxAmount,
		                int discardAmount,
		                boolean canIncrease,
		                boolean canDecrease)
		{
			this._type = type;
			this._maxAmount = maxAmount;
			this._discardAmount = discardAmount;
			this._canIncrease = canIncrease;
			this._canDecrease = canDecrease;
		}

		public ResourceType getType()
		{
			return this._type;
		}

		public void setType(ResourceType type)
		{
			this._type = type;
		}

		public int getDiscardAmount()
		{
			return this._discardAmount;
		}

		public void setDiscardAmount(int discardAmount)
		{
			this._discardAmount = discardAmount;
			this.update();
		}

		public int getMaxAmount()
		{
			return this._maxAmount;
		}

		public void setMaxAmount(int maxAmount)
		{
			this._maxAmount = maxAmount;
			this.update();
		}

		public boolean canIncrease()
		{
			return this._canIncrease;
		}

		public void setIncrease(boolean canIncrease)
		{
			this._canIncrease = canIncrease;
			this.update();
		}

		public boolean canDecrease()
		{
			return this._canDecrease;
		}

		public void setDecrease(boolean canDecrease)
		{
			this._canDecrease = canDecrease;
			this.update();
		}
		
		public void update()
		{
			_upButton.setEnabled(_canIncrease);
			_downButton.setEnabled(_canDecrease);
			_discardAmountLabel.setText(""+_discardAmount);
			_maxAmountLabel.setText(""+_maxAmount);
			_discardResourcePanel.repaint();
		}
		
		public void setResourceImage(BufferedImage resourceImage)
		{
			BufferedImage image;
			
			image = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			double heightScale = (double)IMAGE_SIZE / 
								 (double)(resourceImage.getHeight(null));
			double widthScale = (double)IMAGE_SIZE / 
								(double)(resourceImage.getWidth(null));
			g.scale(widthScale, heightScale);
			g.drawImage(resourceImage, 0, 0, null);
			
			this._resourceImage = image;
		}
		
		public void setUpArrowImage(BufferedImage upImage)
		{
			BufferedImage image;
			
			image = new BufferedImage(BUTTON_SIZE, BUTTON_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			double heightScale = (double)BUTTON_SIZE / 
								 (double)(upImage.getHeight(null));
			double widthScale = (double)BUTTON_SIZE / 
								(double)(upImage.getWidth(null));
			g.scale(widthScale, heightScale);
			g.drawImage(upImage, 0, 0, null);

			float[] scaleFactors = {R_SCALE, G_SCALE, B_SCALE, A_SCALE};
			float[] offsetFactors = {R_OFFSET, G_OFFSET, B_OFFSET, A_OFFSET};
			
			this._blankUpImage = new RescaleOp(scaleFactors,
			                                   offsetFactors,
			                                   null).filter(image, null);
			this._upImage = image;
		}
		
		public void setDownArrowImage(BufferedImage downImage)
		{
			BufferedImage image;
			
			image = new BufferedImage(BUTTON_SIZE, BUTTON_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			double heightScale = (double)BUTTON_SIZE / 
								 (double)(downImage.getHeight(null));
			double widthScale = (double)BUTTON_SIZE / 
								(double)(downImage.getWidth(null));
			g.scale(widthScale, heightScale);
			g.drawImage(downImage, 0, 0, null);

			float[] scaleFactors = {R_SCALE, G_SCALE, B_SCALE, A_SCALE};
			float[] offsetFactors = {R_OFFSET, G_OFFSET, B_OFFSET, A_OFFSET};
			
			this._blankDownImage = new RescaleOp(scaleFactors,
			                                     offsetFactors,
			                                     null).filter(image, null);
			this._downImage = image;
		}
		
		public JComponent asJComponent()
		{
			JPanel resourcePanel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.25;
			c.weighty = 0.25;
			
			// Discard Amount Label Settings
			_maxAmountLabel = new JLabel("Total "+_maxAmount, JLabel.CENTER);
			FontUtils.setFont(_maxAmountLabel, LABEL_FONT_SIZE);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 3;
			c.gridheight = 1;
			resourcePanel.add(_maxAmountLabel, c);
			
			// Resource Image in the Middle of the Panel
			ImageIcon resourceIcon = new ImageIcon(_resourceImage);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 5;
			c.gridheight = 5;
			resourcePanel.add(new JLabel(resourceIcon), c);
			
			c.gridx = 0;
			c.gridy = 6;
			c.gridwidth = 1;
			c.gridheight = 1;
			resourcePanel.add(Box.createRigidArea(new Dimension(BUTTON_SIZE, BUTTON_SIZE)), c);
			
			// Up Button Configuration
			ImageIcon upIcon = new ImageIcon(_upImage);
			_upButton = new JButton(upIcon);
			_upButton.setOpaque(false);
			_upButton.setMargin(new Insets(0, 0, 0, 0));
			_upButton.setContentAreaFilled(false);
			_upButton.setBorderPainted(false);
			_upButton.setDisabledIcon(new ImageIcon(_blankUpImage));
			_upButton.setActionCommand("UP");
			_upButton.addActionListener(_actionListener);
			
			c.gridx = 1;
			c.gridy = 6;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.insets = new Insets(0, 0, 0, 0);
			resourcePanel.add(_upButton, c);
//			if(_maxAmount == 0)
//				_upButton.setEnabled(false);
			
			// Discard Amount Label Configuration
			_discardAmountLabel = new JLabel(""+this._discardAmount, JLabel.CENTER);
			FontUtils.setFont(_discardAmountLabel, LABEL_FONT_SIZE);
			_discardAmountLabel.setSize(25,18);
			c.gridx = 2;
			c.gridy = 6;
			c.gridwidth = 1;
			c.gridheight = 1;
			resourcePanel.add(_discardAmountLabel, c);
			
			// Down Button Configuration
			ImageIcon downIcon = new ImageIcon(_downImage);
			_downButton = new JButton(downIcon);
			_downButton.setOpaque(false);
			_downButton.setMargin(new Insets(0, 0, 0, 0));
			_downButton.setContentAreaFilled(false);
			_downButton.setBorderPainted(false);
			_downButton.setDisabledIcon(new ImageIcon(_blankDownImage));
			_downButton.setActionCommand("DOWN");
			_downButton.addActionListener(_actionListener);
			_downButton.setEnabled(false);
			c.gridx = 3;
			c.gridy = 6;
			c.gridwidth = 1;
			c.gridheight = 1;
			resourcePanel.add(_downButton, c);
			
			c.gridx = 4;
			c.gridy = 6;
			c.gridwidth = 1;
			c.gridheight = 1;
			resourcePanel.add(Box.createRigidArea(new Dimension(BUTTON_SIZE, BUTTON_SIZE)), c);
			
			_discardResourcePanel = new JPanel();
			_discardResourcePanel.setLayout(new BoxLayout(_discardResourcePanel,
			                                              BoxLayout.Y_AXIS));
			_discardResourcePanel.add(resourcePanel);
			
			return _discardResourcePanel;
		}
		
		private ActionListener _actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch(e.getActionCommand())
				{
					case "UP":
						System.out.printf("Increase amount of %s\n",
						                  Resource.this.getType());
						DiscardView.this.getController()
										.increaseAmount(Resource.this.getType());
						break;
					case "DOWN":
						System.out.printf("Decrease amount of %s\n",
						                  Resource.this.getType());
						DiscardView.this.getController()
										.decreaseAmount(Resource.this.getType());
						break;
					default:
						break;
				}
			}
		};
	}
}

