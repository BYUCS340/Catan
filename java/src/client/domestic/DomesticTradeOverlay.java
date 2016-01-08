package client.domestic;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import shared.definitions.*;
import client.base.*;
import client.data.*;
import client.utils.FontUtils;


/**
 * Implementation of the domestic trade overlay, which allows the user to propose
 * a domestic trade
 */
@SuppressWarnings("serial")
public class DomesticTradeOverlay extends OverlayView implements IDomesticTradeOverlay {

	private final int LABEL_TEXT_SIZE = 40;
	private final int BUTTON_TEXT_SIZE = 28;
	private final int BORDER_WIDTH = 10;
	private final int RESOURCE_IMAGE_WIDTH = 120;

	private JLabel label;
	private JButton tradeButton;
	private JButton cancelButton;
	private JPanel playerSelectionPanel;
	private ArrayList<JPanel> resourceSelectionPanels;
	private ArrayList<JToggleButton> playerButtons;
	private ArrayList<JPanel> upDownPanels;
	private Map<ResourceType, JPanel> upDownPanelByResourceType;
	private PlayerInfo[] players;
	private ButtonGroup toggleButtonGroup;
	
	private Map<ResourceType, JLabel> resourceCounts;
	private Map<ResourceType, ArrayList<JButton>> resourceButtonsMap;

	public DomesticTradeOverlay() {
		
		this.buildView();
	}

	private void buildView() {
		this.resourceSelectionPanels = new ArrayList<JPanel>();
		this.resourceCounts = new HashMap<ResourceType, JLabel>();
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		label = new JLabel("Domestic Trade Overlay");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);

		this.add(label, BorderLayout.NORTH);
		this.add(this.setupResourceTradePanel(), BorderLayout.CENTER);
		this.add(this.setupUserSelectPanel(), BorderLayout.SOUTH);		
	}

	private JPanel setupResourceTradePanel() {
		// setup resource tiles
		this.resourceButtonsMap = new HashMap<ResourceType, ArrayList<JButton>>();
		this.upDownPanels = new ArrayList<JPanel>();
		this.upDownPanelByResourceType = new HashMap<ResourceType, JPanel>();
		JPanel brickTile = this.setupResourceTile("images/resources/brick.png", ResourceType.BRICK);
		JPanel oreTile = this.setupResourceTile("images/resources/ore.png", ResourceType.ORE);
		JPanel sheepTile = this.setupResourceTile("images/resources/sheep.png", ResourceType.SHEEP);
		JPanel wheatTile = this.setupResourceTile("images/resources/wheat.png", ResourceType.WHEAT);
		JPanel woodTile = this.setupResourceTile("images/resources/wood.png", ResourceType.WOOD);
		
		JPanel resourceTilesPanel = new JPanel();
		resourceTilesPanel.setLayout(new BoxLayout(resourceTilesPanel, BoxLayout.X_AXIS));
		resourceTilesPanel.add(woodTile);
		resourceTilesPanel.add(brickTile);
		resourceTilesPanel.add(sheepTile);
		resourceTilesPanel.add(wheatTile);
		resourceTilesPanel.add(oreTile);
		
		return resourceTilesPanel;
	}

	private JPanel setupUserSelectPanel() {
		
		// setup trade button
		this.tradeButton = new JButton("set the trade you want to make");
		Font buttonFont = this.tradeButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
		this.tradeButton.setFont(buttonFont);
		this.tradeButton.setEnabled(false);
		this.tradeButton.setAlignmentX(CENTER_ALIGNMENT);
		this.tradeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				getController().sendTradeOffer();
			}
		});
				
		this.cancelButton = new JButton("Cancel");
		FontUtils.setFont(this.cancelButton, BUTTON_TEXT_SIZE);
		this.cancelButton.setAlignmentX(CENTER_ALIGNMENT);
		this.cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				getController().cancelTrade();
//				reset();// TODO, this may not be needed here
			}
		});
		
		// setup user select
		JPanel userSelectPanel = new JPanel();
		userSelectPanel.setLayout(new BoxLayout(userSelectPanel, BoxLayout.Y_AXIS));
		userSelectPanel.setAlignmentX(CENTER_ALIGNMENT);
		userSelectPanel.add(this.setupUsersList());
		userSelectPanel.add(this.tradeButton);
		userSelectPanel.add(this.cancelButton);
		
		return userSelectPanel;
	}

	private JPanel setupUsersList() {
		this.playerButtons = new ArrayList<JToggleButton>();
		this.toggleButtonGroup = new ButtonGroup();
		
		JToggleButton noneToggle = new JToggleButton("None");
		noneToggle.setSelected(true);
		noneToggle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				getController().setPlayerToTradeWith(-1);
			}
		});
		noneToggle.setActionCommand("None");
		noneToggle.putClientProperty( "JButton.buttonType", "segmented" );
		noneToggle.putClientProperty( "JButton.segmentPosition", "first" );
        toggleButtonGroup.add(noneToggle);

		this.playerSelectionPanel = new JPanel();
		this.playerSelectionPanel.setLayout(new BoxLayout(this.playerSelectionPanel, BoxLayout.X_AXIS));
		this.playerSelectionPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.playerSelectionPanel.add(noneToggle);
		
		return this.playerSelectionPanel;
	}

	private PlayerInfo getPlayerByName(String name) {
		for (PlayerInfo pi : this.players) {
			if(pi.getName().equals(name))
				return pi;
		}
		return null;
	}
	
	private JPanel setupResourceTile(String imageFilePath, final ResourceType resourceType) {
		try {
			BufferedImage image = ImageIO.read(new File(imageFilePath));
			image = this.getScaledImage(image, RESOURCE_IMAGE_WIDTH, RESOURCE_IMAGE_WIDTH);
			JLabel imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setAlignmentX(CENTER_ALIGNMENT);
			imageLabel.setAlignmentY(TOP_ALIGNMENT);
			
			JPanel upDownButtonsPanel = this.setupUpDownButtonsPanel(resourceType);
			this.upDownPanelByResourceType.put(resourceType, upDownButtonsPanel);
//			upDownButtonsPanel.setVisible(false);
			
			ButtonGroup toggleButtonGroup = new ButtonGroup();
			
			JToggleButton sendToggle = new JToggleButton("send");
			sendToggle.setActionCommand("send");
			sendToggle.putClientProperty( "JButton.buttonType", "segmented" );
	        sendToggle.putClientProperty( "JButton.segmentPosition", "first" );
	        sendToggle.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					upDownPanelByResourceType.get(resourceType).setVisible(true);
					getController().setResourceToSend(resourceType);
				}
	        	
	        });
	        toggleButtonGroup.add(sendToggle);
	        
	        
	        JToggleButton noneToggle = new JToggleButton("none");
	        noneToggle.setSelected(true);
	        noneToggle.setActionCommand("none");
	        noneToggle.putClientProperty( "JButton.buttonType", "segmented" );
	        noneToggle.putClientProperty( "JButton.segmentPosition", "middle" );
	        noneToggle.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					upDownPanelByResourceType.get(resourceType).setVisible(false);
					resourceCounts.get(resourceType).setText("0");
					getController().unsetResource(resourceType);
				}
	        	
	        });
	        toggleButtonGroup.add(noneToggle);
	        
	        JToggleButton recieveToggle = new JToggleButton("recieve");
	        recieveToggle.setActionCommand("recieve");
	        recieveToggle.putClientProperty( "JButton.buttonType", "segmented" );
	        recieveToggle.putClientProperty( "JButton.segmentPosition", "last" );
	        recieveToggle.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					upDownPanelByResourceType.get(resourceType).setVisible(true);
					getController().setResourceToReceive(resourceType);
				}
	        	
	        });
	        toggleButtonGroup.add(recieveToggle);
			

	        
			JPanel segmentedButtonPanel = new JPanel();
			segmentedButtonPanel.setAlignmentX(CENTER_ALIGNMENT);
			segmentedButtonPanel.setLayout(new BoxLayout(segmentedButtonPanel, BoxLayout.X_AXIS));
			segmentedButtonPanel.add(sendToggle);
			segmentedButtonPanel.add(noneToggle);
			segmentedButtonPanel.add(recieveToggle);
			
			JPanel resourceSelectionPanel = new JPanel();
			resourceSelectionPanel.setAlignmentX(CENTER_ALIGNMENT);
			resourceSelectionPanel.setLayout(new BoxLayout(resourceSelectionPanel, BoxLayout.Y_AXIS));
			resourceSelectionPanel.add(segmentedButtonPanel);
			resourceSelectionPanel.add(upDownButtonsPanel);
			resourceSelectionPanel.setAlignmentY(TOP_ALIGNMENT);
			this.resourceSelectionPanels.add(resourceSelectionPanel);
			
			this.upDownPanels.add(upDownButtonsPanel);
			
			JPanel tile = new JPanel();
			tile.setLayout(new BoxLayout(tile, BoxLayout.Y_AXIS));
			tile.add(imageLabel);
			tile.add(resourceSelectionPanel);
			tile.setAlignmentY(TOP_ALIGNMENT);
			tile.setPreferredSize(new Dimension((int) (RESOURCE_IMAGE_WIDTH * 1.6), (int) (RESOURCE_IMAGE_WIDTH * 1.7)));
			
			
			return tile;
		} catch (IOException e) {
			throw new RuntimeException("Failed to setup resource tile for image path: " + imageFilePath + ", error: " + e.getLocalizedMessage());
		}
	}
	
	private JPanel setupUpDownButtonsPanel(final ResourceType resourceType) {
		this.resourceButtonsMap.put(resourceType, new ArrayList<JButton>());
		JPanel upDownButtonsPanel = new JPanel();
		upDownButtonsPanel.setLayout(new BoxLayout(upDownButtonsPanel, BoxLayout.X_AXIS));
		
		try {
			Integer upDownButtonWidth = 20;
			JLabel resourceCountLabel = new JLabel("0");
			this.resourceCounts.put(resourceType, resourceCountLabel);
			
			BufferedImage upImage = ImageIO.read(new File("images/misc/up.png"));
			upImage = this.getScaledImage(upImage, upDownButtonWidth, upDownButtonWidth);
			JButton upButton = new JButton(new ImageIcon(upImage));
			upButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					Integer currentAmount = Integer.parseInt(resourceCounts.get(resourceType).getText());
					getController().increaseResourceAmount(resourceType);
					currentAmount++;
					resourceCounts.get(resourceType).setText("" + currentAmount);
				}
			});
			this.resourceButtonsMap.get(resourceType).add(upButton);
			
			BufferedImage downImage = ImageIO.read(new File("images/misc/down.png"));
			downImage = this.getScaledImage(downImage, upDownButtonWidth, upDownButtonWidth);
			JButton downButton = new JButton(new ImageIcon(downImage));
			downButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					Integer currentAmount = Integer.parseInt(resourceCounts.get(resourceType).getText());
					if(currentAmount > 0){
						getController().decreaseResourceAmount(resourceType);
						currentAmount--;
						resourceCounts.get(resourceType).setText("" + currentAmount);
					}
				}
			});
			this.resourceButtonsMap.get(resourceType).add(downButton);
			
			upDownButtonsPanel.add(upButton);
			upDownButtonsPanel.add(resourceCountLabel);
			upDownButtonsPanel.add(downButton);
			
		} catch (IOException e) {
			throw new RuntimeException("error possibly with image path, error: " + e.getLocalizedMessage());
		}
		
		return upDownButtonsPanel;
	}

	/**
	* Resizes an image using a Graphics2D object backed by a BufferedImage.
	* @param srcImg - source image to scale
	* @param w - desired width
	* @param h - desired height
	* @return - the new resized image
	*/
	private BufferedImage getScaledImage(BufferedImage src, int w, int h){
	    int finalw = w;
	    int finalh = h;
	    double factor = 1.0d;
	    if(src.getWidth() > src.getHeight()){
	        factor = ((double)src.getHeight()/(double)src.getWidth());
	        finalh = (int)(finalw * factor);                
	    }else{
	        factor = ((double)src.getWidth()/(double)src.getHeight());
	        finalw = (int)(finalh * factor);
	    }   

	    BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(src, 0, 0, finalw, finalh, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	private ActionListener playerSelectActionListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton button = (JToggleButton)e.getSource();
			PlayerInfo pi = getPlayerByName(button.getText());
			getController().setPlayerToTradeWith(pi.getPlayerIndex());
		}

	};
	
	
	@Override
	public IDomesticTradeController getController() {
		return (IDomesticTradeController)super.getController();
	}

	@Override
	public void reset() {
		this.removeAll();
		this.buildView();
		this.setPlayers(this.players);
	}

	@Override
	public void setPlayers(PlayerInfo[] value) {
		
		for (int i = 0; i < value.length; i++) {
			
			JToggleButton toggle = new JToggleButton(value[i].getName());
			toggle.addActionListener(playerSelectActionListener);
			toggle.setActionCommand(value[i].getName());
			toggle.putClientProperty( "JButton.buttonType", "segmented" );
			if(i == value.length - 1)
				toggle.putClientProperty( "JButton.segmentPosition", "last" );
			else
				toggle.putClientProperty( "JButton.segmentPosition", "middle" );
			toggle.setForeground(value[i].getColor().getJavaColor());
	        toggleButtonGroup.add(toggle);
	        this.playerSelectionPanel.add(toggle);
	        this.playerButtons.add(toggle);
		}
		this.players = value;
		for (JPanel panel : this.upDownPanels) {
			panel.setVisible(false);
		}
	}

	
//	Alternative way to deal with the enabling/disabling of the
//	player buttons.
//	
//	@Override
//	public void setPlayerSelectionEnabled(boolean enable) {
//		for(JToggleButton tButton : playerButtons) {
//			tButton.setEnabled(enable);
//		}
//	}
	
	
	@Override
	public void setPlayerSelectionEnabled(boolean enable) {
		this.playerSelectionPanel.setVisible(enable);
	}

	@Override
	public void setResourceAmount(ResourceType resource, String amount) {
		this.resourceCounts.get(resource).setText(amount);
	}

	@Override
	public void setResourceAmountChangeEnabled(ResourceType resource, boolean canIncrease, boolean canDecrease) {
		// up button is index 0, down is index 1
		this.resourceButtonsMap.get(resource).get(0).setEnabled(canIncrease);
		this.resourceButtonsMap.get(resource).get(1).setEnabled(canDecrease);
	}

	@Override
	public void setResourceSelectionEnabled(boolean enable) {
		for (JPanel panel : this.resourceSelectionPanels) {
			panel.setVisible(enable);
		}
	}

	@Override
	public void setStateMessage(String message) {
		this.tradeButton.setText(message);
	}

	@Override
	public void setTradeEnabled(boolean enable) {
		this.tradeButton.setEnabled(enable);
	}

	@Override
	public void setCancelEnabled(boolean enabled) {
		this.cancelButton.setEnabled(enabled);
	}

}


