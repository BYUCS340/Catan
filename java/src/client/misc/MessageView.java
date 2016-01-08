package client.misc;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.base.*;


/**
 * Implementation for the message view, which is used to display messages to the user
 */
@SuppressWarnings("serial")
public class MessageView extends OverlayView implements IMessageView {

	private final int LABEL_TEXT_SIZE = 20;
	private final int BUTTON_TEXT_SIZE = 20;
	private final int BORDER_WIDTH = 10;

	private JLabel label;
	private JButton closeButton;
	private JPanel buttonPanel;
	private JLabel message;
	
	

	public MessageView() {
		
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		label = new JLabel("Message View");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		this.add(label, BorderLayout.NORTH);
		
		
		
		message = new JLabel("Message Body");
		this.add(message, BorderLayout.CENTER);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(actionListener);
		Font buttonFont = closeButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
		closeButton.setFont(buttonFont);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));		
		buttonPanel.add(closeButton);		
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == closeButton) {
				closeModal();
			}
		}	
	};

	@Override
	public void setTitle(String title) {
		label.setText(title);
	}

	@Override
	public void setMessage(String message) {
		this.message.setText(String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 500, message));
	}
	
}


