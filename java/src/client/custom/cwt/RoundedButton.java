package client.custom.cwt;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("unused")
public class RoundedButton extends JButton
{
	private static final long serialVersionUID = 3193769590459118040L;   
	
	public RoundedButton()
	{
		super();
	}
	
	public RoundedButton(String text)
	{
		super(text);
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = g2.getRenderingHints();
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		
		// Make it grey #DDDDDD, and make it round with   
		// 1px black border.  
		// Use an HTML color guide to find a desired color.  
		// Last color is alpha, with max 0xFF to make   
		// completely opaque.  
		   
		// Draw rectangle with rounded corners on top of   
		// button  
		g2.setColor(Color.GRAY);
		g2.fillRoundRect(0,0,getWidth()-2,getHeight()-2,15,15);  
		   
		// I'm just drawing a border  
		//g2d.setColor(getSelectionColor()); 
		this.setBorder(new LineBorder(Color.GRAY, 3, true));
		g2.drawRoundRect(0,0,getWidth(),getHeight(),15,15);  
		   
		// Finding size of text so can position in center.  
		FontRenderContext frc = new FontRenderContext(null, false, false);  
		Rectangle2D r = getFont().getStringBounds(getText(), frc);  
		   
		float xMargin = (float)(getWidth()-r.getWidth())/2;  
		float yMargin = (float)(getHeight()-getFont().getSize())/2;  
		   
		// Draw the text in the center  
		g2.setColor(Color.BLACK);  
		g2.drawString(getText(), xMargin, (float)getFont().getSize() + yMargin);
	}
}

