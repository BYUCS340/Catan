package client.devcards;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shared.definitions.*;
import client.base.*;
import client.utils.*;

import java.util.*;

/**
 * "Play dev card" view implementation
 */
@SuppressWarnings("serial")
public class PlayDevCardView extends OverlayView implements IPlayDevCardView {
	
	private final int LABEL_TEXT_SIZE = 40;
	private final int BUTTON_TEXT_SIZE = 20;
	private final int BORDER_WIDTH = 10;
	
	private final String DEFAULT_USE_BUTTON_LABEL = "select a development card to use";

	private JLabel title;
	private DevelopmentCardChooser devCards;
	private ResourceCardChooser resCard1;
	private ResourceCardChooser resCard2;

	// Action buttons
	private JButton useButton;
	private JButton cancelButton;

	public PlayDevCardView() {
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		this.setLayout(new BorderLayout());

		// Title Panel (immutable)
		JPanel titlePanel = new JPanel(new BorderLayout());
		title = new JLabel("Development Cards");
		FontUtils.setFont(title, LABEL_TEXT_SIZE);
		titlePanel.add(title, BorderLayout.WEST);
		this.add(titlePanel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// Separator
		mainPanel.add(new JSeparator());

		// Blank space
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Development Card Chooser
		devCards = new DevelopmentCardChooser();
		devCards.setListener(btnGrpPnlListener);
		mainPanel.add(devCards);

		// Blank space
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel resPanel = new JPanel();
		resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.Y_AXIS));

		// Resource Card 1
		resCard1 = new ResourceCardChooser();
		resCard1.setEnabled(false);
		resCard1.setListener(btnGrpPnlListener);
		mainPanel.add(resCard1);

		// Blank space
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Resource Card 2
		resCard2 = new ResourceCardChooser();
		resCard2.setEnabled(false);
		resCard2.setListener(btnGrpPnlListener);
		resPanel.add(resCard2);

		mainPanel.add(resPanel);

		// Blank space
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Use Dev Card Button
		JPanel usePanel = new JPanel();
		useButton = new JButton(DEFAULT_USE_BUTTON_LABEL);
		useButton.setEnabled(false);
		FontUtils.setFont(useButton, BUTTON_TEXT_SIZE);
		useButton.addActionListener(actionListener);
		usePanel.add(useButton);
		mainPanel.add(usePanel);

		// Blank space
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Cancel Button
		JPanel cancelPanel = new JPanel();
		cancelButton = new JButton("Cancel");
		FontUtils.setFont(cancelButton, BUTTON_TEXT_SIZE);
		cancelButton.addActionListener(actionListener);
		cancelPanel.add(cancelButton);
		mainPanel.add(cancelPanel);
		
		this.add(mainPanel, BorderLayout.CENTER);
	}

	@Override
	public IDevCardController getController() {
		return (IDevCardController)super.getController();
	}

	@Override
	public void showModal() {
		reset();
		super.showModal();
	}

	@Override
	public void reset() {
		useButton.setText(DEFAULT_USE_BUTTON_LABEL);
		useButton.setEnabled(false);
		resCard1.setEnabled(false);
		resCard2.setEnabled(false);
		devCards.clearSelection();
		resCard1.clearSelection();
		resCard2.clearSelection();
	}

	@Override
	public void setCardEnabled(DevCardType cardType, boolean enabled) {
		devCards.setCardEnabled(cardType, enabled);
	}

	@Override
	public void setCardAmount(DevCardType cardType, int amount) {
		devCards.setCardAmount(cardType, amount);
	}

	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == cancelButton) {
				getController().cancelPlayCard();
			} 
			else if (e.getSource() == useButton) {
				if (devCards.getSelectedDevCard() == DevCardType.MONOPOLY) {					
					closeModal();
					getController().playMonopolyCard(resCard1.getSelectedResourceCard());
				}
				else if (devCards.getSelectedDevCard() == DevCardType.YEAR_OF_PLENTY) {
					closeModal();
					getController().playYearOfPlentyCard(resCard1.getSelectedResourceCard(),
														resCard2.getSelectedResourceCard());
				}
			}
		}	
	};
	
	private ButtonGroupPanelListener btnGrpPnlListener = new ButtonGroupPanelListener() {

		@Override
		public void selectedButtonChanged(ButtonGroupPanel source) {
			
			DevCardType selectedDevCard = devCards.getSelectedDevCard();
			
			if (selectedDevCard == null) {
				useButton.setText(DEFAULT_USE_BUTTON_LABEL);
				useButton.setEnabled(false);
				resCard1.setEnabled(false);
				resCard2.setEnabled(false);
			}
			else if (selectedDevCard == DevCardType.MONUMENT) {
				useButton.setText(DEFAULT_USE_BUTTON_LABEL);
				useButton.setEnabled(false);
				resCard1.setEnabled(false);
				resCard2.setEnabled(false);
				
				closeModal();
				getController().playMonumentCard();
			}
			else if (selectedDevCard == DevCardType.ROAD_BUILD) {
				useButton.setText(DEFAULT_USE_BUTTON_LABEL);
				useButton.setEnabled(false);
				resCard1.setEnabled(false);
				resCard2.setEnabled(false);
				
				closeModal();
				getController().playRoadBuildCard();
			}
			else if (selectedDevCard == DevCardType.SOLDIER) {
				useButton.setText(DEFAULT_USE_BUTTON_LABEL);
				useButton.setEnabled(false);
				resCard1.setEnabled(false);
				resCard2.setEnabled(false);
				
				closeModal();
				getController().playSoldierCard();
			}
			else if (selectedDevCard == DevCardType.MONOPOLY) {
				useButton.setText("use monopoly");
				useButton.setEnabled(resCard1.getSelectedResourceCard() != null);
				resCard1.setEnabled(true);
				resCard2.setEnabled(false);
			}
			else if (selectedDevCard == DevCardType.YEAR_OF_PLENTY) {
				useButton.setText("use year of plenty");
				useButton.setEnabled(resCard1.getSelectedResourceCard() != null &&
										resCard2.getSelectedResourceCard() != null);
				resCard1.setEnabled(true);
				resCard2.setEnabled(true);
			}
		}		
	};
}


// Helper interfaces and classes ==============================================

interface IButtonGroup {
	
	public void add(AbstractButton button);
	public void clearSelection();
	public int getButtonCount();
	public Enumeration<AbstractButton> getElements();
	public ButtonModel getSelection();
	public boolean isSelected(ButtonModel model);
	public void remove(AbstractButton button);
	public void setSelected(ButtonModel model, boolean bool);
}


interface ButtonGroupPanelListener {

	public void selectedButtonChanged(ButtonGroupPanel source);
}


@SuppressWarnings("serial")
class ButtonGroupPanel extends JPanel implements IButtonGroup {
	
	private ButtonGroupPanelListener listener;
	
	private ButtonGroup bg;

	ButtonGroupPanel() {
		super();		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
		bg = new ButtonGroup();
	}
	
	public void setListener(ButtonGroupPanelListener listener) {		
		this.listener = listener;
	}
	
	protected void notifySelectedButtonChanged() {	
		if (listener != null) {
			listener.selectedButtonChanged(this);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
				
		Enumeration<AbstractButton> buttons = getElements();
		while (buttons.hasMoreElements()) {	
			buttons.nextElement().setEnabled(enabled);
		}
		
		super.setEnabled(enabled);
	}

	// IButtonGroup Implementations
	public void add(AbstractButton button) {
		button.addActionListener(actionListener);
		bg.add(button);
		super.add(button);
	}

	public void clearSelection() {
		bg.clearSelection();
	}

	public int getButtonCount() {
		return bg.getButtonCount();
	}

	public Enumeration<AbstractButton> getElements() {
		return bg.getElements();
	}

	public ButtonModel getSelection() {
		return bg.getSelection();
	}

	public boolean isSelected(ButtonModel model) {
		return bg.isSelected(model);
	}

	public void remove(AbstractButton button) {
		button.removeActionListener(actionListener);
		bg.remove(button);
	}

	public void setSelected(ButtonModel model, boolean selected) {
		bg.setSelected(model, selected);
	}
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			notifySelectedButtonChanged();
		}	
	};
}


@SuppressWarnings("serial")
class ResourceCardChooser extends ButtonGroupPanel {
	
	private final int BUTTON_TEXT_SIZE = 18;
	
	private JToggleButton none;
	private JToggleButton wood;
	private JToggleButton brick;
	private JToggleButton sheep;
	private JToggleButton wheat;
	private JToggleButton ore;
	
	ResourceCardChooser() {		
		super();

		none = new JToggleButton("none");
		FontUtils.setFont(none, BUTTON_TEXT_SIZE);
		wood = new JToggleButton("wood");
		FontUtils.setFont(wood, BUTTON_TEXT_SIZE);
		brick = new JToggleButton("brick");
		FontUtils.setFont(brick, BUTTON_TEXT_SIZE);
		sheep = new JToggleButton("sheep");
		FontUtils.setFont(sheep, BUTTON_TEXT_SIZE);
		wheat = new JToggleButton("wheat");
		FontUtils.setFont(wheat, BUTTON_TEXT_SIZE);
		ore	= new JToggleButton("ore");
		FontUtils.setFont(ore, BUTTON_TEXT_SIZE);

		this.add(none);
		this.add(wood);
		this.add(brick);
		this.add(sheep);
		this.add(wheat);
		this.add(ore);

		this.setSelected(none.getModel(), true);
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		
		if (isEnabled() != enabled) {
			
			super.setEnabled(enabled);
		
			if (!enabled) {
				clearSelection();
			}
			else {
				clearSelection();
				setSelected(none.getModel(), true);
			}
		}
	}


	public ResourceType getSelectedResourceCard() {
		
		ButtonModel selection = getSelection();
		
		if (selection == wood.getModel())
			return ResourceType.WOOD;
		else if (selection == brick.getModel())
			return ResourceType.BRICK;
		else if (selection == sheep.getModel())
			return ResourceType.SHEEP;
		else if (selection == wheat.getModel())
			return ResourceType.WHEAT;
		else if (selection == ore.getModel())
			return ResourceType.ORE;
		else
			return null;
	}
}


@SuppressWarnings("serial")
class DevelopmentCardChooser extends ButtonGroupPanel {
	private Map<DevCardType, JToggleButton> devCards;
	private Map<JToggleButton, DevCardType> devCardTypes;

	private JToggleButton soldier;		
	private JToggleButton yearofplenty;		
	private JToggleButton monopoly;
	private JToggleButton roadbuilding;
	private JToggleButton monument;

	DevelopmentCardChooser() {
		super();

		devCards = new HashMap<DevCardType, JToggleButton>();
		devCardTypes = new HashMap<JToggleButton, DevCardType>();

		soldier = createDevCardButton("0", "images/cards/soldier.jpg");		
		yearofplenty = createDevCardButton("0", "images/cards/year-of-plenty.jpg");		
		monopoly = createDevCardButton("0", "images/cards/monopoly.jpg");
		roadbuilding = createDevCardButton("0", "images/cards/road-building.jpg");
		monument = createDevCardButton("0", "images/cards/monument.jpg");

		this.add(DevCardType.SOLDIER, soldier);
		this.add(DevCardType.YEAR_OF_PLENTY, yearofplenty);
		this.add(DevCardType.MONOPOLY, monopoly);
		this.add(DevCardType.ROAD_BUILD, roadbuilding);
		this.add(DevCardType.MONUMENT, monument);
	}
	
	private JToggleButton createDevCardButton(String text, String imageFile) {

		final int BUTTON_TEXT_SIZE = 24;
		
		BufferedImage image = loadDevCardImage(imageFile);
		
		JToggleButton button = new JToggleButton(text, new ImageIcon(image)) {
			
			@Override
			public void paintComponent(Graphics g) {
				
				g.setColor(Color.white);
				g.fillRect(0,  0,  this.getWidth(), this.getHeight());
				
				super.paintComponent(g);
			}
		};
		
		FontUtils.setFont(button, BUTTON_TEXT_SIZE);
		button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		button.setBackground(Color.white);
		button.setContentAreaFilled(false);
		button.setVerticalTextPosition(AbstractButton.BOTTOM);
		button.setHorizontalTextPosition(AbstractButton.CENTER);
		
		button.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg) {
				
				JToggleButton source = (JToggleButton)arg.getSource();
				if (source.isSelected()) {
					source.setBorder(BorderFactory.createLineBorder(Color.black, 3));
				}
				else {
					source.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				}
			}		
		});
		
		return button;
	}
	
	private BufferedImage loadDevCardImage(String file) {		
		BufferedImage image = ImageUtils.loadImage(file);
		return ImageUtils.resizeImage(image, 100, 100);
	}

	void add(DevCardType type, JToggleButton button) {
		devCards.put(type, button);
		devCardTypes.put(button, type);
		this.add(button);
	}

	void setCardEnabled(DevCardType cardType, boolean enabled) {
		JToggleButton button = devCards.get(cardType);
		button.setEnabled(enabled);
	}

	void setCardAmount(DevCardType cardType, int amount) {
		JToggleButton button = devCards.get(cardType);
		button.setText(Integer.toString(amount));
	}
	
	public DevCardType getSelectedDevCard() {
		
		ButtonModel selection = getSelection();
		
		if (selection == soldier.getModel())
			return DevCardType.SOLDIER;
		else if (selection == yearofplenty.getModel())
			return DevCardType.YEAR_OF_PLENTY;
		else if (selection == monopoly.getModel())
			return DevCardType.MONOPOLY;
		else if (selection == roadbuilding.getModel())
			return DevCardType.ROAD_BUILD;
		else if (selection == monument.getModel())
			return DevCardType.MONUMENT;
		else
			return null;		
	}
}



