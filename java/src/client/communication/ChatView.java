package client.communication;

import client.base.PanelView;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Chat view implementation
 */
@SuppressWarnings("serial")
public class ChatView extends PanelView implements IChatView {
    
    private LogComponent chatPanel;
    private JScrollPane chatScrollPane;
    private JPanel inputPanel;
    private JButton sendChatBtn;
    private PlaceholderTextField chatTextInput;

    /**
     * Creates a new chat view component.
     */
    public ChatView() {
        // Create the components
        chatPanel = new LogComponent();
        chatScrollPane = new JScrollPane(chatPanel);
        inputPanel = new JPanel();
        sendChatBtn = new JButton("Send");
        chatTextInput = new PlaceholderTextField();
        chatTextInput.setPlaceholder("Send a message!");
        chatTextInput.setPreferredSize(new Dimension(260, 20));
        
        // Register the listeners
        EventListener listener = new EventListener();
        sendChatBtn.addActionListener(listener);
        chatTextInput.addKeyListener(listener);
        
        // Create the layout and add the components
        inputPanel.setLayout(new GridBagLayout());
        
        // Send Chat Button
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(sendChatBtn, constraints);

        // Chat Input Field
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.9;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(5, 0, 5, 0);
        inputPanel.add(chatTextInput, constraints);
        
        setLayout(new BorderLayout());
        add(chatScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.PAGE_END);
        
	setBackground(Color.white);
    }

    @Override
    public IChatController getController() {
        return (IChatController) super.getController();
    }

    @Override
    public void setEntries(final List<LogEntry> entries) {
        chatPanel.setEntries(entries);
    }
    
    /**
     * Calls the send message function on the registered IChatController.
     */
    private void sendMessage() {
        String message = chatTextInput.getText();
        if (!message.isEmpty()) {
            getController().sendMessage(message);
            
            // Clear the text area so we are ready for the next message
            chatTextInput.setText("");
        }
    }
    
    /**
     * Handles events that occur on the chat view.
     */
    private class EventListener extends KeyAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            // Check if the enter key was pressed
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sendMessage();
            }
        }
    }
	
}


