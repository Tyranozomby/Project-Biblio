package vue.bibli;

import control.BibliController;
import util.DataBase;

import javax.swing.*;

public class BibliPanel extends JPanel {

    private BibliController controller;

    public BibliPanel() {
        setOpaque(false);

        JLabel test = new JLabel("Biblio Boy");
        add(test);

        //TODO Faire un affichage

    }

    public void addListener(BibliController controller) {
        //TODO Remplir avec les objets nécessitant un actionListener. Ne pas oublier setActionCommand("mot-clé")
    }

    public void setDB(DataBase DB) {
        controller = new BibliController(this, DB); // !!! Contrôleur !!!
    }
}
