package vue.connect;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.Font;

/**
 * Loading panel used when waiting for connection to database
 */
public class LoadingPanel extends JPanel {

    public LoadingPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        JLabel truc = new JLabel("Loading...");
        truc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        add(truc);

        add(new JLabel(new ImageIcon("files/chargement.gif")));
    }
}
