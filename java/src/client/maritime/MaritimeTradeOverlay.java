package client.maritime;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import shared.definitions.*;
import client.base.*;
import client.utils.ImageUtils;

//TODO Craig: I think one thing that could help layout is to simply add .preferedSize() to the createGet/GivePane
// Also, I'm not sure if you have moved the folder for the images, but that is just in the starting variables.  :D
/**
 * Implementation for the maritime trade overlay, which lets the user make a maritime trade
 */
@SuppressWarnings("serial")
public class MaritimeTradeOverlay extends OverlayView implements IMaritimeTradeOverlay {

	private final int LABEL_TEXT_SIZE = 40;
	private final int BUTTON_TEXT_SIZE = 28;
	private final int BORDER_WIDTH = 10;

	private JLabel label; //Where is this used? //It's for the name
	private JPanel mainPane;

	//Image variables
	BufferedImage reloadImg, woodImg, brickImg,	sheepImg, wheatImg, oreImg;
	String resourceImageFolder;
	
	//give variables
	private JButton givereload;
	private JLabel giveAmount;
	private JButton givewood, givebrick, givesheep, givewheat, giveore;
	ResourceType[] giveAvailables;
	
	//get variables

	private JButton getreload;
	private JLabel getAmount;
	private JButton getwood, getbrick, getsheep, getwheat, getore;
	ResourceType[] getAvailables;
	
	private ActionListener actionListener;
	private ActionListener giveActionListener;
	private ActionListener getActionListener;
	
	//This was given (some of it was anyway)
	private JButton tradeButton;
	private JButton cancelButton;
	private JPanel buttonPanel;
	

	public MaritimeTradeOverlay() {
		initImages();
		
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));

		mainPane = createWholePanel();

		//create the view elsewhere and add it here
		this.add(mainPane, BorderLayout.CENTER);
	}

	private void initImages() {
		resourceImageFolder = "images"+File.separator+"resources"+File.separator;
	
		
		reloadImg = ImageUtils.loadImage("images"+File.separator+"misc"+File.separator+"reload.png");
		
		woodImg = ImageUtils.loadImage(resourceImageFolder+"wood.png");
		brickImg = ImageUtils.loadImage(resourceImageFolder+"brick.png");
		sheepImg = ImageUtils.loadImage(resourceImageFolder+"sheep.png");
		wheatImg = ImageUtils.loadImage(resourceImageFolder+"wheat.png");
		oreImg = ImageUtils.loadImage(resourceImageFolder+"ore.png");
	}
	
	@Override
	public IMaritimeTradeController getController() {
		return (IMaritimeTradeController)super.getController();
	}

	@Override
	public void reset() {
		this.removeAll();
		mainPane = this.createWholePanel();
		this.add(mainPane, BorderLayout.CENTER);
	}
	
	@Override
	public void hideGetOptions() {
		getwood.setVisible(false);
		getbrick.setVisible(false);
		getsheep.setVisible(false);
		getwheat.setVisible(false);
		getore.setVisible(false);
		getreload.setVisible(false);
		getAmount.setVisible(false);
	}

	@Override
	public void hideGiveOptions() {
		givewood.setVisible(false);
		givebrick.setVisible(false);
		givesheep.setVisible(false);
		givewheat.setVisible(false);
		giveore.setVisible(false);
		givereload.setVisible(false);
		giveAmount.setVisible(false);
	}

	@Override
	public void selectGetOption(ResourceType selectedResource, int amount) {
		getAmount.setText(Integer.toString(amount));
		getAmount.setVisible(true);
		
		//Hide them all
		getwood.setVisible(false);
		getbrick.setVisible(false);
		getsheep.setVisible(false);
		getwheat.setVisible(false);
		getore.setVisible(false);

		//Restore the correct resource type, setting it to disabled
		if 		(selectedResource == ResourceType.WOOD)		
			{	getwood.setVisible(true); 	getwood.setEnabled(false);	}

		else if (selectedResource == ResourceType.BRICK)
			{	getbrick.setVisible(true);	getbrick.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.SHEEP)
			{	getsheep.setVisible(true);	getsheep.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.WHEAT)
			{	getwheat.setVisible(true);	getwheat.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.ORE)
			{	getore.setVisible(true);	getore.setEnabled(false);	}

		//displays the undo button
		getreload.setVisible(true);
		getreload.setEnabled(true);
	}

	@Override
	public void selectGiveOption(ResourceType selectedResource, int amount) {
		giveAmount.setText(Integer.toString(amount));
		giveAmount.setVisible(true);
		
		//Hide them all
		givewood.setVisible(false);
		givebrick.setVisible(false);
		givesheep.setVisible(false);
		givewheat.setVisible(false);
		giveore.setVisible(false);

		//Restore the correct resource type, setting it to disabled
		if 		(selectedResource == ResourceType.WOOD)		
			{	givewood.setVisible(true); 	givewood.setEnabled(false);	}

		else if (selectedResource == ResourceType.BRICK)
			{	givebrick.setVisible(true);	givebrick.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.SHEEP)
			{	givesheep.setVisible(true);	givesheep.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.WHEAT)
			{	givewheat.setVisible(true);	givewheat.setEnabled(false);	}
		
		else if (selectedResource == ResourceType.ORE)
			{	giveore.setVisible(true);	giveore.setEnabled(false);	}

		//displays the undo button
		givereload.setVisible(true);
		givereload.setEnabled(true);
	}

	@Override
	public void setStateMessage(String message) {
		tradeButton.setText(message);
	}

	@Override
	public void setTradeEnabled(boolean enable) {
		tradeButton.setEnabled(enable);
	}

	@Override
	public void setCancelEnabled(boolean enabled) {
		cancelButton.setEnabled(enabled);
	}

	@Override
	public void showGetOptions(ResourceType[] enabledResources) {
		getAvailables = enabledResources;
		getreload.setVisible(false);
		getAmount.setVisible(false);
		
		//Show them all
		getwood.setVisible(true);
		getbrick.setVisible(true);
		getsheep.setVisible(true);
		getwheat.setVisible(true);
		getore.setVisible(true);
		
		//Disable them all
		getwood.setEnabled(false);
		getbrick.setEnabled(false);
		getsheep.setEnabled(false);
		getwheat.setEnabled(false);
		getore.setEnabled(false);
		
		//Enable only the ones that are available
		for (ResourceType res : enabledResources) {
			if		(res == ResourceType.WOOD)	{ getwood.setEnabled(true);}
			else if (res == ResourceType.BRICK)	{getbrick.setEnabled(true);} 
			else if (res == ResourceType.SHEEP)	{getsheep.setEnabled(true);} 
			else if (res == ResourceType.WHEAT)	{getwheat.setEnabled(true);} 
			else if (res == ResourceType.ORE)	{  getore.setEnabled(true);} 
		}
	}

	@Override
	public void showGiveOptions(ResourceType[] enabledResources) {
		giveAvailables = enabledResources;
		givereload.setVisible(false);
		giveAmount.setVisible(false);
		
		//Show them all
		givewood.setVisible(true);
		givebrick.setVisible(true);
		givesheep.setVisible(true);
		givewheat.setVisible(true);
		giveore.setVisible(true);
		
		//Disable all
		givewood.setEnabled(false);
		givebrick.setEnabled(false);
		givesheep.setEnabled(false);
		givewheat.setEnabled(false);
		giveore.setEnabled(false);
		
		//Re-enable the available ones
		for (ResourceType res : enabledResources) {
			if		(res == ResourceType.WOOD)	{ givewood.setEnabled(true);}
			else if (res == ResourceType.BRICK)	{givebrick.setEnabled(true);} 
			else if (res == ResourceType.SHEEP)	{givesheep.setEnabled(true);} 
			else if (res == ResourceType.WHEAT)	{givewheat.setEnabled(true);} 
			else if (res == ResourceType.ORE)	{  giveore.setEnabled(true);} 
		}
	}

	private void createListeners() {
		actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == tradeButton) {
					getController().makeTrade();
				}
				else if (e.getSource() == cancelButton) {
					reset();//TODO Should I leave this in?  It just emptys the view and starts a new one. should the controller do that?
					getController().cancelTrade(); 
				}
			}
		};
		giveActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == givereload)	
				{getController().unsetGiveValue();hideGetOptions();}

				else if (e.getSource() == givewood) 
				{getController().setGiveResource(ResourceType.WOOD);}

				else if (e.getSource() == givebrick) 
				{getController().setGiveResource(ResourceType.BRICK);}

				else if (e.getSource() == givesheep) 
				{getController().setGiveResource(ResourceType.SHEEP);}

				else if (e.getSource() == givewheat) 
				{getController().setGiveResource(ResourceType.WHEAT);}

				else if (e.getSource() == giveore) 
				{getController().setGiveResource(ResourceType.ORE);}
			}
		};
		getActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == getreload)	
				{getController().unsetGetValue();}

				else if (e.getSource() == getwood) 
				{getController().setGetResource(ResourceType.WOOD);}

				else if (e.getSource() == getbrick) 
				{getController().setGetResource(ResourceType.BRICK);}

				else if (e.getSource() == getsheep) 
				{getController().setGetResource(ResourceType.SHEEP);}

				else if (e.getSource() == getwheat) 
				{getController().setGetResource(ResourceType.WHEAT);}

				else if (e.getSource() == getore) 
				{getController().setGetResource(ResourceType.ORE);}
			}
		};
	}
	private JPanel createWholePanel() {
		createListeners();
		
		label = new JLabel("Maritime Trade Overlay");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		this.add(label, BorderLayout.NORTH);
		
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		mainPane.setPreferredSize(new Dimension(825, 400));

		mainPane.add(Box.createVerticalStrut(25));
		mainPane.add(createGivePane());
		mainPane.add(Box.createVerticalStrut(5));
		mainPane.add(createGetPane());
		mainPane.add(Box.createVerticalStrut(5));
		mainPane.add(createButtonPane());
		mainPane.add(Box.createVerticalStrut(5));
		
		giveAmount.setFont(labelFont);
		getAmount.setFont(labelFont);
		
		return mainPane;
	}

	private JPanel createGetPane() {
		//System.out.println(resourceImageFolder+"wheat.png");
		//System.out.println(new File(resourceImageFolder).getAbsolutePath());
		//create the buttons
		getreload = new JButton();
		getreload.setPreferredSize(new Dimension(40,40));
		getreload.setIcon(new ImageIcon(resize(reloadImg, 40, 40)));
		getwood = new JButton();
		getwood.setPreferredSize(new Dimension(150,150));
		getwood.setIcon(new ImageIcon(resize(woodImg,150,150)));
		getbrick = new JButton();
		getbrick.setPreferredSize(new Dimension(150,150));
		getbrick.setIcon(new ImageIcon(resize(brickImg,150,150)));
		getsheep = new JButton();
		getsheep.setPreferredSize(new Dimension(150,150));
		getsheep.setIcon(new ImageIcon(resize(sheepImg,150,150)));
		getwheat = new JButton();
		getwheat.setPreferredSize(new Dimension(150,150));
		getwheat.setIcon(new ImageIcon(resize(wheatImg,150,150)));
		getore = new JButton();
		getore.setPreferredSize(new Dimension(150,150));
		getore.setIcon(new ImageIcon(resize(oreImg,150,150)));

		//Remove the boarders from the buttons (this may not work everywhere.
		//It's Dependent on the OS look and feel.
		getwood.setContentAreaFilled(false);
		getbrick.setContentAreaFilled(false);
		getsheep.setContentAreaFilled(false);
		getwheat.setContentAreaFilled(false);
		getore.setContentAreaFilled(false);

		//add action listeners!
		getreload.addActionListener(getActionListener);
		getwood.addActionListener(getActionListener);
		getbrick.addActionListener(getActionListener);
		getsheep.addActionListener(getActionListener);
		getwheat.addActionListener(getActionListener);
		getore.addActionListener(getActionListener);

		getAmount = new JLabel("1");
		getAmount.setVisible(false);
		getreload.setVisible(false);
		
		//Hide it for now
		hideGetOptions();
		
		//Make it all laid out.
		JPanel getPane = new JPanel();
		getPane.setLayout(new BoxLayout(getPane, BoxLayout.X_AXIS));
		getPane.setPreferredSize(new Dimension(875,175));

		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getreload);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getAmount);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getwood);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getbrick);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getsheep);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getwheat);
		getPane.add(Box.createHorizontalStrut(5));
		getPane.add(getore);
		getPane.add(Box.createHorizontalStrut(5));

		return getPane;
	}
	private JPanel createGivePane() {

		givereload = new JButton();
		givereload.setPreferredSize(new Dimension(40,40));
		givereload.setIcon(new ImageIcon(resize(reloadImg, 40, 40)));
		
		givewood = new JButton();
		givewood.setPreferredSize(new Dimension(150,150));
		givewood.setIcon(new ImageIcon(resize(woodImg,150,150)));
		givebrick = new JButton();
		givebrick.setPreferredSize(new Dimension(150,150));
		givebrick.setIcon(new ImageIcon(resize(brickImg,150,150)));
		givesheep = new JButton();
		givesheep.setPreferredSize(new Dimension(150,150));
		givesheep.setIcon(new ImageIcon(resize(sheepImg,150,150)));
		givewheat = new JButton();
		givewheat.setPreferredSize(new Dimension(150,150));
		givewheat.setIcon(new ImageIcon(resize(wheatImg,150,150)));
		giveore = new JButton();
		giveore.setPreferredSize(new Dimension(150,150));
		giveore.setIcon(new ImageIcon(resize(oreImg,150,150)));

		//Remove the boarders from the buttons (this may not work everywhere.
		//It's Dependent on the OS look and feel.
		givewood.setContentAreaFilled(false);
		givebrick.setContentAreaFilled(false);
		givesheep.setContentAreaFilled(false);
		givewheat.setContentAreaFilled(false);
		giveore.setContentAreaFilled(false);

		//Add the action Listeners
		givereload.addActionListener(giveActionListener);
		givewood.addActionListener(giveActionListener);
		givebrick.addActionListener(giveActionListener);
		givesheep.addActionListener(giveActionListener);
		givewheat.addActionListener(giveActionListener);
		giveore.addActionListener(giveActionListener);

		giveAmount = new JLabel("-");
		giveAmount.setVisible(false);
		givereload.setVisible(false);

		//put it all together
		JPanel givePane = new JPanel();
		givePane.setLayout(new BoxLayout(givePane, BoxLayout.X_AXIS));
		givePane.setPreferredSize(new Dimension(875,175));

		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(givereload);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(giveAmount);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(givewood);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(givebrick);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(givesheep);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(givewheat);
		givePane.add(Box.createHorizontalStrut(5));
		givePane.add(giveore);
		givePane.add(Box.createHorizontalStrut(5));

		return givePane;
	}
	private JPanel createButtonPane() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionListener);
		Font buttonFont = cancelButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
		cancelButton.setFont(buttonFont);

		tradeButton = new JButton("Trade!");
		tradeButton.addActionListener(actionListener);
		tradeButton.setFont(buttonFont);

		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(tradeButton);
		buttonPanel.add(cancelButton);		
		this.add(buttonPanel, BorderLayout.SOUTH);
		return buttonPanel;
	}
	
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}
}

