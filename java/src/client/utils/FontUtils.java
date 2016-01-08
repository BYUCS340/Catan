package client.utils;

import java.awt.Font;
import javax.swing.JComponent;

public class FontUtils
{
	public static void setFont(JComponent comp, int size) {
		
		Font font = comp.getFont();
		font = font.deriveFont(font.getStyle(), size);
		comp.setFont(font);
	}
}

