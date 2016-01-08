package client.roll;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;

import javax.swing.*;

import client.base.*;
import client.utils.ImageUtils;


/**
 * Implementation for the roll result view, which displays the result of a roll
 */
@SuppressWarnings({"serial", "unused"})
public class RollResultView extends OverlayView implements IRollResultView {

	private final int TITLE_TEXT_SIZE = 40;
	private final int LABEL_TEXT_SIZE = 28;
	private final int BUTTON_TEXT_SIZE = 28;
	private final int BORDER_WIDTH = 10;

	private JLabel titleLabel;
	private JButton okayButton;
	//private JPanel buttonPanel;
	private JPanel centerPanel;
	private JLabel rollLabel;
	private ImageIcon picture;
	private JLabel pictureLabel;

	public RollResultView() {
		
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		//add the title label to the panel
		titleLabel = new JLabel("Roll Results");
		Font titleLabelFont = titleLabel.getFont();
		titleLabelFont = titleLabelFont.deriveFont(titleLabelFont.getStyle(), TITLE_TEXT_SIZE);
		titleLabel.setFont(titleLabelFont);
		this.add(titleLabel, BorderLayout.NORTH);
		
		//add the button to the panel
		okayButton = new JButton("Okay");
		okayButton.addActionListener(actionListener);
		Font okayButtonFont = okayButton.getFont();
		okayButtonFont = okayButtonFont.deriveFont(okayButtonFont.getStyle(), BUTTON_TEXT_SIZE);
		okayButton.setFont(okayButtonFont);
		this.add(okayButton, BorderLayout.SOUTH);
		
		//create the rollLabel
		rollLabel = new JLabel("ERROR: YOU FORGOT TO SET THE ROLL VALUE BEFORE DISPLAYING THIS WINDOW... NAUGHTY, NAUGHTY");
		Font rollLabelFont = rollLabel.getFont();
		rollLabelFont = rollLabelFont.deriveFont(rollLabelFont.getStyle(), LABEL_TEXT_SIZE);
		rollLabel.setFont(rollLabelFont);
		rollLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rollLabel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		
		//create the picture
		picture = new ImageIcon(ImageUtils.loadImage("images/resources/resources.png").getScaledInstance(250, 250, Image.SCALE_SMOOTH));
		pictureLabel = new JLabel();
		pictureLabel.setIcon(picture);
		
		//create the center label
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(pictureLabel, BorderLayout.NORTH);
		centerPanel.add(Box.createRigidArea(new Dimension(25,25)));
		centerPanel.add(rollLabel, BorderLayout.SOUTH);
		this.add(centerPanel,BorderLayout.CENTER);
		
		//add some spacing
		this.add(Box.createRigidArea(new Dimension(50,50)),BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(50,50)),BorderLayout.WEST);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == okayButton) {
				
				closeModal();
			}
		}	
	};
	
	@Override
	public IRollController getController() {
		
		return (IRollController)super.getController();
	}

	@Override
	public void setRollValue(int value) {
		String rollText = String.format("You rolled a %d.", value);
		rollLabel.setText(rollText);
	}

}


