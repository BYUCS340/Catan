package client.domestic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.base.*;

/**
 * Implementation of the domestic trade view, which contains the
 * "Domestic Trade" button
 */
@SuppressWarnings("serial")
public class DomesticTradeView extends PanelView implements IDomesticTradeView
{
	
	private JButton button;
	
	public DomesticTradeView()
	{
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Font font = new JButton().getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);
		
		button = new JButton("Domestic Trade");
		button.setFont(newFont);
		button.addActionListener(buttonListener);
		
		this.add(button);
	}
	
	@Override
	public IDomesticTradeController getController()
	{
		return (IDomesticTradeController)super.getController();
	}
	
	@Override
	public void enableDomesticTrade(boolean value)
	{
		
		button.setEnabled(value);
	}
	
	private ActionListener buttonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			getController().startTrade();
		}
	};
	
}

