package client.maritime;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import client.base.*;


/**
 * Implementation for the maritime trade view, which displays the "Maritime Trade" button
 */
@SuppressWarnings("serial")
public class MaritimeTradeView extends PanelView implements IMaritimeTradeView {

	private JButton button;
	
	public MaritimeTradeView() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Font font = new JButton().getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);

		button = new JButton("Maritime Trade");
		button.setFont(newFont);
		button.addActionListener(buttonListener);
		
		this.add(button);
	}

	@Override
	public IMaritimeTradeController getController() {
		return (IMaritimeTradeController)super.getController();
	}

	@Override
	public void enableMaritimeTrade(boolean value) {
		
		button.setEnabled(value);
	}
	
	private ActionListener buttonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			getController().startTrade();
		}
	};
	
}


