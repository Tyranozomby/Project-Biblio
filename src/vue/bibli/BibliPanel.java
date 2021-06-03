package vue.bibli;

import control.BibliController;
import util.DataBase;

import javax.swing.*;
import java.awt.*;

public class BibliPanel extends JPanel {

    private final JLabel title = new JLabel();

    private final OngletGestionEtudiants infoEtudiant = new OngletGestionEtudiants();
    private final OngletGestionLivres gestionLivres = new OngletGestionLivres();
    private final OngletValiderRes validerRes = new OngletValiderRes();
    private final OngletEmpruntDirect empruntDirect = new OngletEmpruntDirect();
    private final OngletRetourEmprunt retourEmprunts = new OngletRetourEmprunt();


    public BibliPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        //Panel Titre
        title.setText("Bonjour Bibliothécaire");
        JPanel panelTitre = panelTitre();

        //Content Panel
        JPanel content = new JPanel();
        CardLayout layout = new CardLayout();
        content.setLayout(layout);
        content.setOpaque(false);

        //Ajout onglets
        JTabbedPane onglets = new JTabbedPane();
        onglets.add("Gestion Étudiants", infoEtudiant);
        onglets.add("Gestion Livres", gestionLivres);
        onglets.add("Valider Réservation", validerRes);
        onglets.add("Nouvel Emprunt", empruntDirect);
        onglets.add("Retour Emprunts", retourEmprunts);
        onglets.setFocusable(false);

        content.add(onglets, BorderLayout.CENTER);

        add(panelTitre, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

    }

    private JPanel panelTitre() {
        JPanel panelTitre = new JPanel();

        panelTitre.setOpaque(false);
        panelTitre.setLayout(new GridLayout(2, 1));

        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitle = new JLabel("Biblio");
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        subtitle.setHorizontalAlignment(JLabel.CENTER);

        panelTitre.add(title);
        panelTitre.add(subtitle);
        return panelTitre;
    }


    public void setDB(DataBase DB) {
        new BibliController(infoEtudiant, gestionLivres, validerRes, empruntDirect, retourEmprunts, DB); // !!! Contrôleur !!!
    }
}