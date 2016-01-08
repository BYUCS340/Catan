package client.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.base.*;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation for the login view, which lets the user create a new account
 * and login
 */
@SuppressWarnings({"serial", "unused"})
public class LoginView extends OverlayView implements ILoginView
{

    private final int LABEL_TEXT_SIZE = 40;
    private final float SMALL_LABEL_TEXT_SIZE = 16.0F;
    private final float BIG_LABEL_TEXT_SIZE = LABEL_TEXT_SIZE * 1.5F;
    private final int BUTTON_TEXT_SIZE = 28;
    private final int BORDER_WIDTH = 10;

    private SignInPanel signInPanel = null;
    private RegisterPanel registerPanel = null;

    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.add(new LoginView());

        jf.getContentPane().add(mainPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
    }

    public LoginView()
    {

        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
//
//        label = new JLabel("Login View");
//        Font labelFont = label.getFont();
//        labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
//        label.setFont(labelFont);
//        this.add(label, BorderLayout.CENTER);
//
//        signInButton = new JButton("Sign in");
//        signInButton.addActionListener(actionListener);
//        Font buttonFont = signInButton.getFont();
//        buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
//        signInButton.setFont(buttonFont);
//
//        buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
//        buttonPanel.add(signInButton);
//        this.add(buttonPanel, BorderLayout.SOUTH);

        initComponents();
    }

    private void initComponents()
    {
        JComponent left = initLeftComponents();
        JComponent middle = initMiddleComponents();
        JComponent right = initRightComponents();

        this.setLayout(new GridLayout(1, 3));

        this.add(left);
        this.add(middle);
        this.add(right);
    }

    private JComponent initLeftComponents()
    {
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));

        //Set an empty border to take up space.
        leftPanel.setBorder(createBufferBorder());

        //Create the title label, with the large font.
        JLabel lblTitle = new JLabel("<html><body>Settlers<br>of Catan</body></html>");
        Font labelFont = lblTitle.getFont();
        labelFont = labelFont.deriveFont(labelFont.getStyle(), BIG_LABEL_TEXT_SIZE);
        lblTitle.setFont(labelFont);

        leftPanel.add(lblTitle);

        return leftPanel;
    }

    private JComponent initMiddleComponents()
    {
        signInPanel = new SignInPanel();
        signInPanel.setBorder(createBufferBorder());
        return signInPanel;
    }

    private JComponent initRightComponents()
    {
        registerPanel = new RegisterPanel();
        registerPanel.setBorder(createBufferBorder());
        return registerPanel;
    }

    private Border createBufferBorder()
    {
        final int BUFFER_SPACE = 15;
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
            getController().signIn();
        }
    };

    @Override
    public ILoginController getController()
    {

        return (ILoginController) super.getController();
    }

    @Override
    public String getLoginUsername()
    {
        return signInPanel.txtUsername.getText();
    }

    @Override
    public String getLoginPassword()
    {
        return signInPanel.txtPassword.getText();
    }

    @Override
    public String getRegisterUsername()
    {
        return registerPanel.txtUsername.getText();
    }

    @Override
    public String getRegisterPassword()
    {
        return registerPanel.txtPassword.getText();
    }

    @Override
    public String getRegisterPasswordRepeat()
    {
        return registerPanel.txtPasswordAgain.getText();
    }

    private class SignInPanel extends JPanel
    {

        private JLabel lblLogin = null;
        private JLabel lblUsername = null;
        private JTextField txtUsername = null;
        private JLabel lblPassword = null;
        private JTextField txtPassword = null;
        private JButton btnSignIn = null;

        public SignInPanel()
        {
            initComponents();
            initLayout();
            initEventListeners();
        }

        private void initComponents()
        {
            lblLogin = new JLabel("Login");
            Font labelFont = lblLogin.getFont();
            labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
            lblLogin.setFont(labelFont);

            final int NUM_TXT_COLS = 16;

            lblUsername = new JLabel("Username");
            txtUsername = new JTextField(NUM_TXT_COLS);
            lblPassword = new JLabel("Password");
            txtPassword = new JPasswordField(NUM_TXT_COLS);

            btnSignIn = new JButton("Sign in");
        }

        private void initLayout()
        {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.add(lblLogin);

            this.add(Box.createVerticalGlue());

            JPanel internalInputBox = new JPanel(new GridLayout(4, 1));
            //Change the font on the user entry labels.
            Font smallTextFont = lblUsername.getFont();
            smallTextFont = smallTextFont.deriveFont(smallTextFont.getStyle(), SMALL_LABEL_TEXT_SIZE);
            lblUsername.setFont(smallTextFont);
            txtUsername.setFont(smallTextFont);
            lblPassword.setFont(smallTextFont);
            txtPassword.setFont(smallTextFont);

            internalInputBox.add(lblUsername);
            internalInputBox.add(txtUsername);
            internalInputBox.add(lblPassword);
            internalInputBox.add(txtPassword);
            this.add(internalInputBox);

            this.add(Box.createVerticalGlue());

            Font btnFont = btnSignIn.getFont();
            btnFont = btnFont.deriveFont(btnFont.getStyle(), BUTTON_TEXT_SIZE);
            btnSignIn.setFont(btnFont);
            this.add(btnSignIn);

        }

        private void initEventListeners()
        {
            btnSignIn.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    getController().signIn();
                }

            });
        }
    }

    private class RegisterPanel extends JPanel
    {

        private JLabel lblRegister = null;
        private JLabel lblUsername = null;
        private JTextField txtUsername = null;
        private JLabel lblPassword = null;
        private JTextField txtPassword = null;
        private JLabel lblPasswordAgain = null;
        private JTextField txtPasswordAgain = null;
        private JButton btnRegister = null;

        public RegisterPanel()
        {
            initComponents();
            initTooltips();
            initLayout();
            initEventListeners();
        }

        private void initComponents()
        {
            lblRegister = new JLabel("Register");
            Font labelFont = lblRegister.getFont();
            labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
            lblRegister.setFont(labelFont);

            final int NUM_TXT_COLS = 16;

            lblUsername = new JLabel("Username");
            txtUsername = new JTextField(NUM_TXT_COLS);
            lblPassword = new JLabel("Password");
            txtPassword = new JPasswordField(NUM_TXT_COLS);
            lblPasswordAgain = new JLabel("Password (Again)");
            txtPasswordAgain = new JPasswordField(NUM_TXT_COLS);

            btnRegister = new JButton("Register");
        }

        private void initTooltips()
        {
            txtUsername.setToolTipText("The username must be between three and seven "
                    + "characters: letters, digits, underscore, or dash.");
            txtPassword.setToolTipText("Please match the requested format.  "
                    + "The password must be five or more characters: "
                    + "letters, digits, underscore, or dash.");
            txtPasswordAgain.setToolTipText("Make sure the two passwords match!");
        }

        private void initLayout()
        {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.add(lblRegister);

            this.add(Box.createVerticalGlue());

            JPanel internalInputBox = new JPanel(new GridLayout(6, 1));
            //Change the font on the user entry labels.
            Font smallTextFont = lblUsername.getFont();
            smallTextFont = smallTextFont.deriveFont(smallTextFont.getStyle(), SMALL_LABEL_TEXT_SIZE);
            lblUsername.setFont(smallTextFont);
            txtUsername.setFont(smallTextFont);
            lblPassword.setFont(smallTextFont);
            txtPassword.setFont(smallTextFont);
            lblPasswordAgain.setFont(smallTextFont);
            txtPasswordAgain.setFont(smallTextFont);

            internalInputBox.add(lblUsername);
            internalInputBox.add(txtUsername);
            internalInputBox.add(lblPassword);
            internalInputBox.add(txtPassword);
            internalInputBox.add(lblPasswordAgain);
            internalInputBox.add(txtPasswordAgain);
            this.add(internalInputBox);

            this.add(Box.createVerticalGlue());

            Font btnFont = btnRegister.getFont();
            btnFont = btnFont.deriveFont(btnFont.getStyle(), BUTTON_TEXT_SIZE);
            btnRegister.setFont(btnFont);

            this.add(btnRegister);

        }

        private void initEventListeners()
        {
            btnRegister.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    getController().register();
                }

            });

            //Code to check if the username length is correct!
            TextFieldValidator usernameValidator = new TextFieldValidator(txtUsername)
            {
                @Override
                public boolean validateContents(String username)
                {
                    final int MIN_UNAME_LENGTH = 3;
                    final int MAX_UNAME_LENGTH = 7;

                    if (username.length() < MIN_UNAME_LENGTH
                            || username.length() > MAX_UNAME_LENGTH)
                    {
                        return false;
                    }
                    else
                    {
                        for (char c : username.toCharArray())
                        {
                            if (!Character.isLetterOrDigit(c)
                                    && c != '_' && c != '-')
                            {
                                return false;
                            }
                        }
                    }

                    return true;
                }

            };

            TextFieldValidator passValidator = new TextFieldValidator(txtPassword)
            {

                @Override
                public boolean validateContents(String input)
                {
                    final int MIN_PASS_LENGTH = 5;

                    if (input.length() < MIN_PASS_LENGTH)
                    {
                        return false;
                    }
                    else
                    {
                        for (char c : input.toCharArray())
                        {
                            if (!Character.isLetterOrDigit(c)
                                    && c != '_' && c != '-')
                            {
                                return false;
                            }
                        }
                    }

                    return true;
                }

            };

            TextFieldValidator passAgainValidator = new TextFieldValidator(txtPasswordAgain)
            {

                @Override
                public boolean validateContents(String input)
                {
                    return input.equals(txtPassword.getText());
                }
                
            };
            
            txtUsername.addFocusListener(usernameValidator);
            txtUsername.getDocument().addDocumentListener(usernameValidator);
            
            txtPassword.addFocusListener(passValidator);
            txtPassword.getDocument().addDocumentListener(passValidator);
            
            txtPasswordAgain.addFocusListener(passAgainValidator);
            txtPasswordAgain.getDocument().addDocumentListener(passAgainValidator);

        }
    }

    private static abstract class TextFieldValidator implements DocumentListener, FocusListener
    {

        public abstract boolean validateContents(String input);

        private JTextField textFieldValidate = null;
        private Border originalBorder = null;
        private Border redBorder = null;

        public TextFieldValidator(JTextField textFieldValidate)
        {
            this.textFieldValidate = textFieldValidate;
            originalBorder = textFieldValidate.getBorder();
            redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        }

        @Override
        public void focusGained(FocusEvent e)
        {
            validateInput();
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            validateInput();
        }

        @Override
        public void insertUpdate(DocumentEvent e)
        {
            validateInput();
        }

        @Override
        public void removeUpdate(DocumentEvent e)
        {
            validateInput();
        }

        @Override
        public void changedUpdate(DocumentEvent e)
        {
            validateInput();
        }

        private void validateInput()
        {
            String contents = textFieldValidate.getText();

            if (validateContents(contents))
            {
                textFieldValidate.setBorder(originalBorder);
            }
            else
            {
                Border errorBorder = BorderFactory.createCompoundBorder(originalBorder, redBorder);
                textFieldValidate.setBorder(errorBorder);
            }
        }
    }

}

