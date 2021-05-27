package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import control.StudentController;
import modele.Etudiant;
import modele.TableModeleLiv;
import modele.TableModeleRes;
import util.DataBase;

import javax.swing.*;
import java.awt.*;

public class BibliPanel extends JPanel {

    private Etudiant student;
    private BibliController controller;

    private final JLabel title = new JLabel();

    private final JButton searchButton = new JButton("Rechercher");
    private final JButton reserveButton = new JButton("Réserver");
    private final JButton cancelResButton = new JButton("Annuler la réservation");

    private final JTextField titreField = new JTextField();
    private final JTextField auteurField = new JTextField();

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final TableModeleRes modeleRes = new TableModeleRes();
    private JTable tableLiv;
    private JTable tableRes;

    private final JLabel infoLiv = new JLabel();
    private final JLabel infoRes = new JLabel();

    public BibliPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        //Panel Titre

        title.setText("Bonjour bernadette Woula");
        JPanel panelTitre = panelTitre();

        //Content Panel

        JPanel content = new JPanel();
        CardLayout layout = new CardLayout();
        content.setLayout(layout);
        content.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        JPanel infoEtudiant = makeInfoEtudiant(c);
        JPanel suprRes = makeSuprRes(c);
        JPanel ResEtudiant = makeResEtudiant(c);
        JPanel CMProfilEtudant = makeCMProfilEtudant(c);
        JPanel monProfil = ongletProfil(c);

        //Ajout onglets

        JTabbedPane onglets = new JTabbedPane();
        onglets.add("Info Étudiant", infoEtudiant);
        onglets.add("Suppression de réservations", suprRes);
        onglets.add("Réservations pour Étudiant", ResEtudiant);
        onglets.add("Création/Modification profil Étudiant", CMProfilEtudant);
        onglets.add("Mon profil", monProfil);
        onglets.setFocusable(false);

        content.add(onglets, BorderLayout.CENTER);

        add(panelTitre, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);



        //TODO Faire un affichage

    }

    public void addListener(BibliController controller) {
        //TODO Remplir avec les objets nécessitant un actionListener. Ne pas oublier setActionCommand("mot-clé")
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

    private JPanel ongletProfil(GridBagConstraints c) {
        JPanel monProfil = new JPanel();
        monProfil.setOpaque(false);
        monProfil.setLayout(new GridBagLayout());

        return monProfil;
    }
    private JPanel makeInfoEtudiant(GridBagConstraints c){
        JPanel o1 = new JPanel();
        return o1;
    }
    private JPanel makeSuprRes(GridBagConstraints c){
        JPanel o2 = new JPanel();
        return o2;
    }
    private JPanel makeResEtudiant(GridBagConstraints c){
        //Onglet nouvelles réservations

        JPanel newRes = new JPanel();
        newRes.setOpaque(false);
        newRes.setLayout(new GridBagLayout());

        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        titreField.setFont(Constantes.FIELD_FONT);
        titreField.setBorder(Constantes.BORDER);
        newRes.add(titreField, c);

        c.gridx = 1;
        c.gridy = 1;
        auteurField.setFont(Constantes.FIELD_FONT);
        auteurField.setBorder(Constantes.BORDER);
        newRes.add(auteurField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelTitre = new JLabel("Titre :");
        labelTitre.setLabelFor(titreField);
        labelTitre.setDisplayedMnemonic('t');
        newRes.add(labelTitre, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelAuteur = new JLabel("Auteur :");
        labelAuteur.setLabelFor(auteurField);
        labelAuteur.setDisplayedMnemonic('a');
        newRes.add(labelAuteur, c);

        //**********Début boutons**********
        JPanel panelBoutons = new JPanel();
        panelBoutons.setOpaque(false);
        panelBoutons.setLayout(new GridLayout(1, 2, 20, 2));

        searchButton.setFocusPainted(false);
        reserveButton.setFocusPainted(false);

        panelBoutons.add(searchButton);
        panelBoutons.add(reserveButton);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        newRes.add(panelBoutons, c);
        //**********Fin boutons**********

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;

        newRes.add(infoLiv, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);

        tableLiv = new JTable(modeleLiv);
        tableLiv.setFocusable(false);
        tableLiv.getColumnModel().getColumn(0).setPreferredWidth(Constantes.AUTEUR_SIZE);  // Column size
        tableLiv.getColumnModel().getColumn(1).setPreferredWidth(Constantes.TITRE_SIZE);   //

        JScrollPane scrollPane = new JScrollPane(tableLiv);
        scrollPane.setPreferredSize(new Dimension(400, 318));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);

        newRes.add(scrollPane, c);
        return newRes;
    }
    private JPanel makeCMProfilEtudant(GridBagConstraints c){
        JPanel o3 = new JPanel();
        return o3;
    }



    public void setDB(DataBase DB) {
        controller = new BibliController(this, DB); // !!! Contrôleur !!!
    }
}
