package vue.connect;

import control.ConnectionController;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Insets;

/**
 * Panel showed when connection to database can't be established
 */
public class ErrorPanel extends JPanel {

    private final JButton retry = new JButton("Réessayer");
    private final JButton quit = new JButton("Quitter");

    public ErrorPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 20, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        JLabel text = new JLabel("Connexion échouée");
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        add(text, c);

        //Panel boutons

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new GridLayout(1, 2, 20, 0));
        buttons.add(retry);
        buttons.add(quit);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        add(buttons, c);
    }

    /**
     * @param controller controller which will execute commands when ActionPerformed triggered
     * @see ConnectionController
     */
    public void addListener(ConnectionController controller) {
        retry.setActionCommand("Retry");
        quit.setActionCommand("Quit");

        retry.addActionListener(controller);
        quit.addActionListener(controller);
    }
}
