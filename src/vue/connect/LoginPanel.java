package vue.connect;

import constantes.Constantes;
import control.ConnectionController;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;


/**
 * Connection/login panel
 */
public class LoginPanel extends JPanel {


    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton connexionButton = new JButton("Connexion");
    private final JLabel errorText = new JLabel("Identifiant ou mot de passe invalide");

    public LoginPanel() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        GridBagConstraints c = new GridBagConstraints(); //GribBagConstraints for the whole panel

        //Top panel with Title and Subtitle

        JPanel panelNorth = new JPanel();
        panelNorth.setOpaque(false);
        panelNorth.setLayout(new GridBagLayout());

        c.insets = new Insets(50, 8, 8, 8);

        JLabel title = new JLabel("BiblioRent");
        title.setFont(Constantes.MAIN_TITLE_FONT);

        JLabel subtitle = new JLabel("Login");
        subtitle.setFont(Constantes.TITLE_FONT);

        c.gridy = 0;
        panelNorth.add(title, c);
        c.insets = new Insets(0, 8, 8, 8);

        c.gridy = 1;
        panelNorth.add(subtitle, c);

        //Middle panel with login elements

        JPanel panelCenter = new JPanel();
        panelCenter.setOpaque(false);
        panelCenter.setLayout(new GridBagLayout());

        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 200;
        c.ipady = 7;
        emailField.setFont(Constantes.FIELD_FONT);
        emailField.setBorder(Constantes.BORDER);
        panelCenter.add(emailField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 25;
        c.ipady = 10;
        JLabel email = new JLabel("Email :");
        email.setLabelFor(emailField);
        email.setDisplayedMnemonic('e');
        panelCenter.add(email, c);

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 7;
        passwordField.setBorder(Constantes.BORDER);
        passwordField.setFont(Constantes.FIELD_FONT);
        panelCenter.add(passwordField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipady = 10;
        JLabel password = new JLabel("Password :");
        password.setLabelFor(passwordField);
        password.setDisplayedMnemonic('p');
        panelCenter.add(password, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelCenter.add(connexionButton, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;
        c.insets = new Insets(-8, 8, 0, 8);
        errorText.setForeground(Constantes.BG_COLOR);
        panelCenter.add(errorText, c);

        //Add of panels

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void setFocus() {
        emailField.requestFocusInWindow();
    }

    public void showErrorText() {
        errorText.setForeground(Color.red);
    }

    public void reset() {
        passwordField.setText("");
        passwordField.requestFocusInWindow();
    }

    public void addListener(ConnectionController controller) {
        connexionButton.setActionCommand("Connexion");
        connexionButton.addActionListener(controller);

        emailField.addKeyListener(controller.enterListener(emailField));
        passwordField.addKeyListener(controller.enterListener(passwordField));
    }
}
