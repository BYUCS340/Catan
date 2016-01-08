package client.devcards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.base.*;

/**
 * "Buy dev card" view implementation
 */

@SuppressWarnings("serial")
public class BuyDevCardView extends OverlayView implements IBuyDevCardView {
	
	private final int LABEL_TEXT_SIZE = 25;
	private final int BUTTON_TEXT_SIZE = 20;
	private final int BORDER_WIDTH = 10;

	private JLabel label;
	private JButton acceptButton;
	private JButton rejectButton;
    private JLabel imageLabel;
	private JPanel buttonPanel;

	public BuyDevCardView() {

		this.setOpaque(true);
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));

		label = new JLabel("Really buy a development card?");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		this.add(label, BorderLayout.NORTH);

        try {
            BufferedImage devCardImg = ImageIO.read(new File("images/building/card.jpg"));
            imageLabel = new JLabel(new ImageIcon(devCardImg));
            this.setBackground(Color.WHITE);
            this.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException ex) {
            // Handle Exception Here
        }

		acceptButton = new JButton("Buy Card");
		acceptButton.addActionListener(actionListener);
		Font buttonFont = acceptButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
		acceptButton.setFont(buttonFont);
		
		rejectButton = new JButton("No Thanks!");
		rejectButton.addActionListener(actionListener);
		rejectButton.setFont(buttonFont);	

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));		
		buttonPanel.add(acceptButton);
		buttonPanel.add(rejectButton);	
		buttonPanel.setBackground(Color.WHITE);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public IDevCardController getController() {
		return (IDevCardController)super.getController();
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == acceptButton) {
				getController().buyCard();
			}
			else if (e.getSource() == rejectButton) {
				getController().cancelBuyCard();
			}			
		}	
	};

}


