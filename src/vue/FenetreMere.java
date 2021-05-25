package vue;

import constantes.Constantes;

import javax.swing.JFrame;

/**
 * @author Eliott, Stéphane & Alipio
 * Main window
 */
public class FenetreMere extends JFrame {

    /**
     * @param title Title of the window
     */
    public FenetreMere(String title) {
        super(title);

        SuperPanel contentPane = new SuperPanel();
        this.setContentPane(contentPane);

        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);   //Center JFrame in the middle of the screen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Constantes.BG_COLOR);
        this.setVisible(true);

        contentPane.connectAndStart();  //Try to connect to database and start or show error panel

    }

    public static void main(String[] args) {
        new FenetreMere("BiblioRent");
    }
}
