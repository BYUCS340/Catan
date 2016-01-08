package client.communication;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

/**
 * JTextField that supports adding a placeholder text
 */
public class PlaceholderTextField extends JTextField {
    
	private static final long serialVersionUID = 1L;
	private String placeholder = "";
    
    public String getPlaceholder() {
        return placeholder;
    }
    
    public void setPlaceholder(String placeholder) {
        if (placeholder == null) {
            this.placeholder = "";
        } else {
            this.placeholder = placeholder;
        }
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // If we don't have a placeholder or there is text continue
        if (placeholder.isEmpty() || !getText().isEmpty()) {
            return;
        }

        // Render the placeholder text
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setColor(getDisabledTextColor());
        g2d.drawString(
            placeholder,
            getInsets().left,
            g2d.getFontMetrics().getMaxAscent() + getInsets().top
        );
    }
    
}

