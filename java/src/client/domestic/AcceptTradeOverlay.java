package client.domestic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shared.definitions.*;
import client.base.*;
import client.utils.ImageUtils;


/**
 * Implementation of the "accept trade" overlay, which is used to let the user
 * accept or reject a trade
 */
@SuppressWarnings("serial")
public class AcceptTradeOverlay extends OverlayView implements IAcceptTradeOverlay {
	
	private final int LABEL_TEXT_SIZE = 40;
	private final int BUTTON_TEXT_SIZE = 18;
	private final int GIVE_AND_GET_NUMERAL_TEXT_SIZE = 28;
	private final int OTHER_TEXT_SIZE = 18;
	
	private final String HEAVY_FONT = "Arial Black";
	private final String NORMAL_FONT = "Arial";

	private JLabel label;
	private JButton acceptButton;
	private JButton rejectButton;
	private JPanel buttonPanel;
	private JPanel contentPanel;
	
	private JLabel offerer_component;
	private JPanel offering_component;
	private JLabel request_component;
	private JPanel requesting_component;
	
	public AcceptTradeOverlay()
	{
		this.initialize();
	}
	
	private void initialize()
	{
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		this.setBorder(new EmptyBorder(20,20,20,20));

		//Window Title
		label = new JLabel("Accept Trade Offer?");
		Font labelFont = new Font(HEAVY_FONT, Font.PLAIN, LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		GridBagConstraints label_gbc = new GridBagConstraints();
		label_gbc.gridx = 0;
		label_gbc.gridy = 0;
		label_gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(label, label_gbc);
		
		//Begin Content Panel
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255,255,255,255));
		contentPanel.setLayout(new GridBagLayout());
		
		//In Exchange For... (other pictures and lines are taken care of in other methods in this class
		Font f = new Font(NORMAL_FONT,Font.PLAIN,OTHER_TEXT_SIZE);
		request_component = new JLabel("in exchange for");
		request_component.setHorizontalAlignment(JLabel.CENTER);
		request_component.setFont(f);
		GridBagConstraints request_component_gbc = new GridBagConstraints();
		request_component_gbc.gridx = 0;
		request_component_gbc.gridy = 2;
		contentPanel.add(request_component,request_component_gbc);
		
		GridBagConstraints content_panel_gbc = new GridBagConstraints();
		content_panel_gbc.gridx = 0;
		content_panel_gbc.gridy = 1;
		content_panel_gbc.weighty = 100;
		this.add(contentPanel, content_panel_gbc);
		//End Content Panel
		
		//Begin Button Panel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255,255,255,255));
		buttonPanel.setLayout(new GridBagLayout());
		
		Font buttonFont = new Font(HEAVY_FONT,Font.PLAIN, BUTTON_TEXT_SIZE);
		
		//Accept Button
		acceptButton = new JButton("Accept");
		acceptButton.addActionListener(actionListener);
		acceptButton.setFont(buttonFont);		
		GridBagConstraints accept_gbc = new GridBagConstraints();
		accept_gbc.gridx = 0;
		accept_gbc.gridy = 0;
		accept_gbc.fill = GridBagConstraints.HORIZONTAL;
		accept_gbc.weightx = 50;
		buttonPanel.add(acceptButton, accept_gbc);
		
		//Reject Button
		rejectButton = new JButton("No Thanks!");
		rejectButton.addActionListener(actionListener);
		rejectButton.setFont(buttonFont);	
		GridBagConstraints reject_gbc = new GridBagConstraints();
		reject_gbc.gridx = 1;
		reject_gbc.gridy = 0;
		reject_gbc.fill = GridBagConstraints.HORIZONTAL;
		reject_gbc.weightx = 50;
		buttonPanel.add(rejectButton, reject_gbc);	
		
		GridBagConstraints button_panel_gbc = new GridBagConstraints();
		button_panel_gbc.gridx = 0;
		button_panel_gbc.gridy = 2;
		button_panel_gbc.fill = GridBagConstraints.HORIZONTAL;
		button_panel_gbc.weightx = 100;
		this.add(buttonPanel,button_panel_gbc);
		//End Button Panel
	}

	@Override
	public IDomesticTradeController getController() {
		return (IDomesticTradeController)super.getController();
	}

	@Override
	public void addGetResource(ResourceType resource, int amount) {
		//Instantiate component on the first addition
		if(offering_component == null){
			offering_component = new JPanel();
			offering_component.setBackground(new Color(255,255,255,255));
			offering_component.setLayout(new GridBagLayout());
			GridBagConstraints offering_component_gbc = new GridBagConstraints();
			offering_component_gbc.gridx = 0;
			offering_component_gbc.gridy = 1;
			contentPanel.add(offering_component,offering_component_gbc);
		}
		//The Image
		Image i = ImageUtils.loadImage("images/resources/"+resource+".png");
		ImageIcon ii = new ImageIcon(i.getScaledInstance(50, 50, 0));

		//The Number (and image)
		JLabel get_combo = new JLabel(Integer.toString(amount));
		get_combo.setFont(new Font(HEAVY_FONT,Font.PLAIN,GIVE_AND_GET_NUMERAL_TEXT_SIZE));
		get_combo.setBorder(new EmptyBorder(10,10,10,10));
		get_combo.setIcon(ii);
		
		//Add it to the window
		offering_component.add(get_combo);
	}

	@Override
	public void addGiveResource(ResourceType resource, int amount) {
		//Instantiate component on the first addition
		if(requesting_component == null){
			requesting_component = new JPanel();
			requesting_component.setBackground(new Color(255,255,255,255));
			requesting_component.setLayout(new GridBagLayout());
			GridBagConstraints requesting_component_gbc = new GridBagConstraints();
			requesting_component_gbc.gridx = 0;
			requesting_component_gbc.gridy = 3;
			contentPanel.add(requesting_component,requesting_component_gbc);
		}
		//The Image
		Image i = ImageUtils.loadImage("images/resources/"+resource.toString().toLowerCase()+".png");
		ImageIcon ii = new ImageIcon(i.getScaledInstance(50, 50, 0));
		
		//The Number (and image)
		JLabel give_combo = new JLabel(Integer.toString(amount));
		give_combo.setFont(new Font(HEAVY_FONT,Font.PLAIN,GIVE_AND_GET_NUMERAL_TEXT_SIZE));
		give_combo.setBorder(new EmptyBorder(10,10,10,10));
		give_combo.setIcon(ii);
		
		//Add it to the window
		requesting_component.add(give_combo);
	}

	@Override
	public void setAcceptEnabled(boolean enable) {
		if(enable){
			acceptButton.setText("Accept");
		}else{
			acceptButton.setText("Can't Accept");
		}
		acceptButton.setEnabled(enable);
	}

	@Override
	public void setPlayerName(String name) {
		//Instantiate component 
		if(this.offerer_component == null){
			this.offerer_component = new JLabel();
		}
		Font f = new Font(NORMAL_FONT,Font.PLAIN,OTHER_TEXT_SIZE);
		offerer_component.setHorizontalAlignment(JLabel.CENTER);
		offerer_component.setText(name + " offered to give you");
		offerer_component.setFont(f);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		contentPanel.add(offerer_component,gbc);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    int x = 0;
	    int y = 0;
	    int w = getWidth();
	    int h = getHeight();
	    int arc = 30;

	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setColor(new Color(255, 255, 255, 255));
	    g2.fillRoundRect(x, y, w, h, arc, arc);

	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.GRAY);
	    g2.drawRoundRect(x, y, w, h, arc, arc); 

	    g2.dispose();
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == acceptButton) {
				getController().acceptTrade(true);
			}
			else if (e.getSource() == rejectButton) {
				getController().acceptTrade(false);
			}			
		}	
	};

	@Override
	public void reset()
	{
		offerer_component = null;
		offering_component = null;
		request_component = null;
		requesting_component = null;
		this.removeAll();
		this.initialize();
	}
	
}


