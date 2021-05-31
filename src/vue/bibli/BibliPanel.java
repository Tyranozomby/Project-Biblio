package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import control.StudentController;
import modele.*;
import util.DataBase;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class BibliPanel<Database> extends JPanel {


    private Etudiant student;
    private BibliController controller;

    private final JLabel title = new JLabel();

    private final JLabel profilNom = new JLabel();
    private final JLabel profilPrenom = new JLabel();
    private final JLabel profilMail = new JLabel();
    private final JLabel profilMdp = new JLabel();

    private final JButton searchButton = new JButton("Rechercher");
    private final JButton reserveButton = new JButton("Réserver");
    private final JButton cancelResButton = new JButton("Annuler la réservation");

    private final JButton boutonChoice = new JButton("Choisir");
    private final JButton boutonSauvegarder = new JButton("Sauvergarder");
    private final JButton boutonSupprimer = new JButton("Supprimer");
    private final JButton boutonNouveau = new JButton("Nouveau");

    private final JTextField zoneEmail = new JTextField();
    private final JTextField zoneNom = new JTextField();
    private final JTextField zonePrenom = new JTextField();
    private final JTextField zoneMdp = new JTextField();

    private final JComboBox<String> comboBox = new JComboBox<>();

    private final JTextField titreField = new JTextField();
    private final JTextField auteurField = new JTextField();
    private final JTextField idEtudiantField = new JTextField();

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final TableModeleRes modeleRes = new TableModeleRes();
    private final TableModeleEmp modeleEmp = new TableModeleEmp();
    private final JTable tableLiv = new JTable(modeleLiv);
    private final JTable tableRes = new JTable(modeleRes);
    private final JTable tableEmp = new JTable(modeleEmp);

    private final JLabel infoLiv = new JLabel();
    private final JLabel infoRes = new JLabel();
    private final JLabel infoEmp = new JLabel();


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
        JPanel supprRes = makeSuprRes(c);
        JPanel ResEtudiant = makeResEtudiant(c);

        //Ajout onglets

        JTabbedPane onglets = new JTabbedPane();
        onglets.add("Info Étudiant", infoEtudiant);
        onglets.add("Emprunt", supprRes); //Pouvoir voir les exemplaires, les livres non rendus/rendu /Valider les emprunts
        onglets.add("Reserver et ajouter un livre", ResEtudiant);
        onglets.setFocusable(false);

        content.add(onglets, BorderLayout.CENTER);

        add(panelTitre, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

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

    private JPanel makeInfoEtudiant(GridBagConstraints c) {
        JPanel makeInfoEtudiant = new JPanel();

        makeInfoEtudiant.setOpaque(false);
        makeInfoEtudiant.setLayout(new GridBagLayout());

        c.insets = new Insets(8, 8, 8, 8);

        // ligne 1


        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        comboBox.setFont(Constantes.FIELD_FONT);
        comboBox.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(comboBox, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        boutonChoice.setFont(Constantes.FIELD_FONT);
        boutonChoice.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(boutonChoice, c);


        // ligne 2

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelnom = new JLabel("Nom de l'étudiant :");
        labelnom.setLabelFor(titreField);
        labelnom.setDisplayedMnemonic('t');
        makeInfoEtudiant.add(labelnom, c);

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        zoneNom.setFont(Constantes.FIELD_FONT);
        zoneNom.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(zoneNom, c);


        // ligne 3

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelprenom = new JLabel("Prénom de l'étudiant :");
        labelnom.setLabelFor(titreField);
        labelnom.setDisplayedMnemonic('t');
        makeInfoEtudiant.add(labelprenom, c);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        zonePrenom.setFont(Constantes.FIELD_FONT);
        zonePrenom.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(zonePrenom, c);


        // ligne 3

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelEmail = new JLabel("Email de l'étudiant :");
        labelEmail.setLabelFor(titreField);
        labelEmail.setDisplayedMnemonic('t');
        makeInfoEtudiant.add(labelEmail, c);

        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        zoneEmail.setFont(Constantes.FIELD_FONT);
        zoneEmail.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(zoneEmail, c);


        // ligne 4

        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelMdp = new JLabel("Mot De Passe de l'étudiant :");
        labelMdp.setLabelFor(titreField);
        labelMdp.setDisplayedMnemonic('t');
        makeInfoEtudiant.add(labelMdp, c);

        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        zoneMdp.setFont(Constantes.FIELD_FONT);
        zoneMdp.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(zoneMdp, c);

        //Début bouton
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        boutonSauvegarder.setFont(Constantes.FIELD_FONT);
        boutonSauvegarder.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(boutonSauvegarder, c);

        //6
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        boutonSupprimer.setFont(Constantes.FIELD_FONT);
        boutonSupprimer.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(boutonSupprimer, c);

        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        boutonNouveau.setFont(Constantes.FIELD_FONT);
        boutonNouveau.setBorder(Constantes.BORDER);
        makeInfoEtudiant.add(boutonNouveau, c);

        return makeInfoEtudiant;
    }

    private JPanel makeResEtudiant(GridBagConstraints c) {
        //Onglet nouvelles réservations

        JPanel newRes = new JPanel();
        newRes.setOpaque(false);
        newRes.setLayout(new GridBagLayout());
        c.insets = new Insets(10, 10, 10, 10);

        //
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        idEtudiantField.setFont(Constantes.FIELD_FONT);
        idEtudiantField.setBorder(Constantes.BORDER);
        newRes.add(idEtudiantField, c);
        //

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        titreField.setFont(Constantes.FIELD_FONT);
        titreField.setBorder(Constantes.BORDER);
        newRes.add(titreField, c);

        c.gridx = 1;
        c.gridy = 2;
        auteurField.setFont(Constantes.FIELD_FONT);
        auteurField.setBorder(Constantes.BORDER);
        newRes.add(auteurField, c);

        //
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelIDEtudiant = new JLabel("ID Etudiant :");
        labelIDEtudiant.setLabelFor(titreField);
        labelIDEtudiant.setDisplayedMnemonic('t');
        newRes.add(labelIDEtudiant, c);
        //

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelTitre = new JLabel("Titre :");
        labelTitre.setLabelFor(titreField);
        labelTitre.setDisplayedMnemonic('t');
        newRes.add(labelTitre, c);

        c.gridx = 0;
        c.gridy = 2;
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
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        newRes.add(panelBoutons, c);
        //**********Fin boutons**********

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;

        newRes.add(infoLiv, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);

        tableLiv.setFocusable(false);
        tableLiv.getColumnModel().getColumn(0).setPreferredWidth(Constantes.AUTEUR_SIZE);  // Column size
        tableLiv.getColumnModel().getColumn(1).setPreferredWidth(Constantes.TITRE_SIZE);   //

        JScrollPane scrollPane = new JScrollPane(tableLiv);
        scrollPane.setPreferredSize(new Dimension(400, 218));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);

        newRes.add(scrollPane, c);
        return newRes;
    }

    public JPanel makeSuprRes(GridBagConstraints c) {
        return null;
    }


    public void setDB(DataBase DB) {
        controller = new BibliController(this, DB); // !!! Contrôleur !!!
        ArrayList<Etudiant> truc = DB.setTabStudent();
        comboBox.removeAllItems();
        System.out.println(truc);
        for (Etudiant etu : truc) {
            System.out.println(etu);
            comboBox.addItem(etu.toString());
        }
    }

    public void setBookList(ArrayList<Livre> liste) {
        modeleLiv.setListeLivres(liste);
    }

    public Etudiant getStudent() {
        return student;
    }


    public String getTitre() {
        return titreField.getText();
    }

    public String getAuteur() {
        return auteurField.getText();
    }

    public String getIdEtudiant() {
        return idEtudiantField.getText();
    }

    /* *
     * Method to get which book has been selected
     *
     * @return Selected book
     */

    /*
    public Livre getSelectedBook() {
        int row = tableLiv.getSelectedRow();
        if (row == -1) { // No book selected
            return null;
        }
        setInfoMessageLiv(Constantes.BASIC_MESSAGE);
        return modeleLiv.getValueAt(row);
    }

    /**
     * Method to get which reservation has been selected
     *
     * @return Selected reservation
     */

    public Reservation getSelectedRes() {
        int row = tableRes.getSelectedRow();
        if (row == -1 || modeleRes.getValueAt(row).getLivre().getId() == 0) { // No book selected
            return null;
        }
        return modeleRes.getValueAt(row);
    }

    public void setFocus() {
        titreField.requestFocusInWindow();
    }


    public void setResList(Reservation[] res) {
        modeleRes.setListeRes(res);
    }

    public void setEmpList(Emprunt[] emp) {
        modeleEmp.setListeEmp(emp);
    }


    public void addListener(BibliController controller) {
        //TODO Remplir avec les objets nécessitant un actionListener. Ne pas oublier setActionCommand("mot-clé")
    }
}
