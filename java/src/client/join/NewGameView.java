package client.join;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import client.base.*;
import javax.swing.border.Border;

/**
 * Implementation for the new game view, which lets the user enter parameters
 * for a new game
 */
@SuppressWarnings("serial")
public class NewGameView extends OverlayView implements INewGameView
{

    private final int LABEL_TEXT_SIZE = 40;
    private final int BUTTON_TEXT_SIZE = 28;
    private final int BORDER_WIDTH = 10;

    private JLabel lblNewGameSettings = null;
    private JButton createButton = null;
    private JButton cancelButton = null;
    private JPanel buttonPanel = null;
    
    private JTextField txtTitle = null;
    private JCheckBox chkRandNumbers = null;
    private JCheckBox chkRandHexes = null;
    private JCheckBox chkRandPorts = null;

    public NewGameView()
    {

        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));

        lblNewGameSettings = new JLabel("New Game Settings");
        Font labelFont = lblNewGameSettings.getFont();
        labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
        lblNewGameSettings.setFont(labelFont);
        this.add(lblNewGameSettings, BorderLayout.PAGE_START);

        this.add(initInternalComponents(), BorderLayout.CENTER);
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionListener);
        Font buttonFont = cancelButton.getFont();
        buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
        cancelButton.setFont(buttonFont);

        createButton = new JButton("Create Game");
        createButton.addActionListener(actionListener);
        createButton.setFont(buttonFont);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);        
        this.add(buttonPanel, BorderLayout.SOUTH);                
    }
    
    private JComponent initInternalComponents()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        JLabel lblTitle = new JLabel("Title:");
        txtTitle = new JTextField();
        chkRandNumbers = new JCheckBox("Randomly place Numbers");
        chkRandHexes = new JCheckBox("Randomly place Hexes");
        chkRandPorts = new JCheckBox("Use Random ports");
        
        
        mainPanel.add(lblTitle);
        mainPanel.add(txtTitle);
        mainPanel.add(chkRandNumbers);
        mainPanel.add(chkRandHexes);
        mainPanel.add(chkRandPorts);   
        
        mainPanel.setBorder(createBufferBorder());
        
        return mainPanel;        
    }
    
    private Border createBufferBorder()
    {
        final int BUFFER_SPACE = 7;
        Border innerBuffer = BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE);
        Border outerBuffer = BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE);
        Border etching = BorderFactory.createEtchedBorder();

        Border outerCompound = BorderFactory.createCompoundBorder(outerBuffer, etching);
        Border wholeCompound = BorderFactory.createCompoundBorder(outerCompound, innerBuffer);

        return wholeCompound;
    }

    private ActionListener actionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if (e.getSource() == createButton)
            {

                getController().createNewGame();
            }
            else if (e.getSource() == cancelButton)
            {

                getController().cancelCreateNewGame();
            }
        }
    };

    @Override
    public IJoinGameController getController()
    {

        return (IJoinGameController) super.getController();
    }

    @Override
    public void setTitle(String value)
    {
        this.txtTitle.setText(value);
    }

    @Override
    public String getTitle()
    {
        return txtTitle.getText();
    }

    @Override
    public void setRandomlyPlaceNumbers(boolean value)
    {
        chkRandNumbers.setSelected(value);
    }

    @Override
    public boolean getRandomlyPlaceNumbers()
    {
        return chkRandNumbers.isSelected();
    }

    @Override
    public void setRandomlyPlaceHexes(boolean value)
    {
        chkRandHexes.setSelected(value);
    }

    @Override
    public boolean getRandomlyPlaceHexes()
    {
        return chkRandHexes.isSelected();
    }

    @Override
    public void setUseRandomPorts(boolean value)
    {
        chkRandPorts.setSelected(value);
    }

    @Override
    public boolean getUseRandomPorts()
    {
        return chkRandPorts.isSelected();
    }

}

