package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.*;
import util.DataBase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BibliPanel extends JPanel {

    private final JLabel title = new JLabel();

    private final JButton searchButton = new JButton("Rechercher");
    private final JButton reserveButton = new JButton("Réserver");
    private final JButton cancelResButton = new JButton("Annuler la réservation");
    private final JButton ajoutLivre = new JButton("Ajouter un livre");
    private final JButton suprLivre = new JButton("Supprimer un livre");

    private final JButton infoChoisir = new JButton("Choisir");
    private final JButton infoSauvegarder = new JButton("Sauvegarder");
    private final JButton infoSupprimer = new JButton("Supprimer");
    private final JButton infoNouveau = new JButton("Nouveau");

    private final JTextField infoEmail = new JTextField();
    private final JTextField infoNom = new JTextField();
    private final JTextField infoPrenom = new JTextField();
    private final JTextField infoMdp = new JTextField();

    //Retour Livres
    private final JTextField retourNom = new JTextField();
    private final JTextField retourPrenom = new JTextField();
    private final JTextField retourTitre = new JTextField();
    private final JTextField retourAuteur = new JTextField();
    private final JButton retourRecherche = new JButton("Rechercher");
    private final JButton retourRendu = new JButton("Livre bien rendu");
    private final JButton retourRelance = new JButton("Relancer l'élève");

    private final JComboBox<Etudiant> infoComboBox = new JComboBox<>();

    private final JTextField titreField = new JTextField();
    private final JTextField auteurField = new JTextField();
    private final JTextField idEtudiantField = new JTextField();

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final TableModeleRes modeleRes = new TableModeleRes();
    private final TableModeleEmpAll modeleEmpAll = new TableModeleEmpAll();
    private final JTable tableLiv = new JTable(modeleLiv);
    private final JTable tableRes = new JTable(modeleRes);
    private final JTable tableEmpAll = new JTable(modeleEmpAll);

    private final JLabel infoTxtLiv = new JLabel();


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

        //Ajout onglets

        JTabbedPane onglets = new JTabbedPane();
        onglets.add("Info Étudiant", infoEtudiant(c));
        onglets.add("Emprunt", supprRes(c)); //Pouvoir voir les exemplaires, les livres non rendus/rendu /Valider les emprunts
        onglets.add("Réserver et ajouter un livre", resEtudiant(c));
        onglets.add("Retours livres", retourLivres(c));
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

    /**
     * Method used to get panel for books' return and remind late students
     *
     * @param c GridBagConstraints object to use for the panel
     * @return a JPanel
     */
    private JPanel retourLivres(GridBagConstraints c) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        // TABLEAU
        c.gridy = 1;
        c.gridwidth = 1;
        tableEmpAll.setFocusable(false);
        tableEmpAll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmpAll.getColumnModel().getColumn(0).setPreferredWidth(300);  //
        tableEmpAll.getColumnModel().getColumn(1).setPreferredWidth(250);  // Column size
        tableEmpAll.getColumnModel().getColumn(2).setPreferredWidth(150);  //
        tableEmpAll.getColumnModel().getColumn(3).setPreferredWidth(100);  //

        JScrollPane scrollPane = new JScrollPane(tableEmpAll);
        scrollPane.setPreferredSize(new Dimension(800, 218));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);
        panel.add(scrollPane, c);

        // TEXT FIELDS
        c.insets = new Insets(8, 250, 30, 250);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel fields = new JPanel(new GridLayout(4, 2, 10, 10));
        fields.setOpaque(false);

        JLabel nomEtu = new JLabel("Nom élève:");
        nomEtu.setLabelFor(retourNom);
        nomEtu.setDisplayedMnemonic('n');
        fields.add(nomEtu);

        retourNom.setFont(Constantes.FIELD_FONT);
        retourNom.setBorder(Constantes.BORDER);
        fields.add(retourNom);

        JLabel prenomEtu = new JLabel("Prénom élève:");
        prenomEtu.setLabelFor(retourPrenom);
        prenomEtu.setDisplayedMnemonic('p');
        fields.add(prenomEtu);

        retourPrenom.setFont(Constantes.FIELD_FONT);
        retourPrenom.setBorder(Constantes.BORDER);
        fields.add(retourPrenom);

        JLabel titre = new JLabel("Titre:");
        titre.setLabelFor(retourTitre);
        titre.setDisplayedMnemonic('t');
        fields.add(titre);

        retourTitre.setFont(Constantes.FIELD_FONT);
        retourTitre.setBorder(Constantes.BORDER);
        fields.add(retourTitre);

        JLabel auteur = new JLabel("Auteur:");
        auteur.setLabelFor(retourAuteur);
        auteur.setDisplayedMnemonic('a');
        fields.add(auteur);

        retourAuteur.setFont(Constantes.FIELD_FONT);
        retourAuteur.setBorder(Constantes.BORDER);
        fields.add(retourAuteur);
        panel.add(fields, c);

        c.insets = new Insets(30, 100, 8, 100);

        // BOUTONS
        c.gridy = 2;
        c.gridwidth = 1;
        JPanel boutons = new JPanel(new GridLayout(1, 3, 10, 10));
        boutons.setOpaque(false);
        retourRecherche.setFocusPainted(false); //
        retourRendu.setFocusPainted(false);     // FOCUS PAINTED FALSE
        retourRelance.setFocusPainted(false);   //
        boutons.add(retourRecherche);   //
        boutons.add(retourRendu);       // ADD
        boutons.add(retourRelance);     //

        panel.add(boutons, c);

        return panel;
    }

    private JPanel infoEtudiant(GridBagConstraints c) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        c.insets = new Insets(8, 8, 8, 8);

        // ligne 1

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        infoComboBox.setFont(Constantes.FIELD_FONT);
        infoComboBox.setBackground(Constantes.WHITE);
        panel.add(infoComboBox, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.CENTER;
        infoChoisir.setFont(Constantes.FIELD_FONT);
        infoChoisir.setBorder(Constantes.BORDER);
        infoChoisir.setFocusPainted(false);
        panel.add(infoChoisir, c);

        // LABELS
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelNom = new JLabel("Nom de l'étudiant :");
        labelNom.setLabelFor(infoNom);
        labelNom.setDisplayedMnemonic('n');
        panel.add(labelNom, c);

        c.gridy = 2;
        JLabel labelPrenom = new JLabel("Prénom de l'étudiant :");
        labelPrenom.setLabelFor(infoPrenom);
        labelPrenom.setDisplayedMnemonic('p');
        panel.add(labelPrenom, c);

        c.gridy = 3;
        JLabel labelEmail = new JLabel("Email de l'étudiant :");
        labelEmail.setLabelFor(infoEmail);
        labelEmail.setDisplayedMnemonic('e');
        panel.add(labelEmail, c);

        c.gridy = 4;
        JLabel labelMdp = new JLabel("Mot De Passe de l'étudiant :");
        labelMdp.setLabelFor(infoMdp);
        labelMdp.setDisplayedMnemonic('m');
        panel.add(labelMdp, c);

        // FIELDS
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 100;
        c.ipady = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        infoNom.setFont(Constantes.FIELD_FONT);
        infoNom.setBorder(Constantes.BORDER);
        panel.add(infoNom, c);

        c.gridy = 2;
        infoPrenom.setFont(Constantes.FIELD_FONT);
        infoPrenom.setBorder(Constantes.BORDER);
        panel.add(infoPrenom, c);

        c.gridy = 3;
        infoEmail.setFont(Constantes.FIELD_FONT);
        infoEmail.setBorder(Constantes.BORDER);
        panel.add(infoEmail, c);

        c.gridy = 4;
        infoMdp.setFont(Constantes.FIELD_FONT);
        infoMdp.setBorder(Constantes.BORDER);
        panel.add(infoMdp, c);

        /* Début bouton */
        JPanel boutons = new JPanel(new GridLayout(2, 2, 10, 10));
        boutons.setOpaque(false);

        infoSauvegarder.setFont(Constantes.FIELD_FONT);
        infoNouveau.setFont(Constantes.FIELD_FONT);
        infoSupprimer.setFont(Constantes.FIELD_FONT);

        infoSauvegarder.setBorder(Constantes.BORDER);
        infoNouveau.setBorder(Constantes.BORDER);
        infoSupprimer.setBorder(Constantes.BORDER);

        infoSauvegarder.setFocusPainted(false);
        infoNouveau.setFocusPainted(false);
        infoSupprimer.setFocusPainted(false);

        boutons.add(infoSauvegarder, c);
        boutons.add(infoNouveau, c);
        boutons.add(infoSupprimer, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        panel.add(boutons, c);
        /* Fin boutons */

        return panel;
    }

    private JPanel resEtudiant(GridBagConstraints c) {
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
        panelBoutons.add(ajoutLivre);
        panelBoutons.add(suprLivre);

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

        newRes.add(infoTxtLiv, c);

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

    public JPanel supprRes(GridBagConstraints c) {
        return new JPanel();
    }


    public void setDB(DataBase DB) {
        new BibliController(this, DB); // !!! Contrôleur !!!
        infoComboBox.removeAllItems();

        // Pour créer les nouveaux étudiants
        infoComboBox.addItem(new Etudiant(0, "Étudiant", "Créer", "", ""));

        for (Etudiant etu : DB.setTabStudent()) {
            infoComboBox.addItem(etu);
        }
        modeleEmpAll.setListeEmp(DB.getEmprunts());
    }

    public void setBookList(ArrayList<Livre> liste) {
        modeleLiv.setListeLivres(liste);
    }


    // RETOUR
    public String getRetourNom() {
        return retourNom.getText();
    }

    public String getRetourPrenom() {
        return retourPrenom.getText();
    }

    public String getRetourTitre() {
        return retourTitre.getText();
    }

    public String getRetourAuteur() {
        return retourAuteur.getText();
    }

    public Emprunt getRetourSelectedEmp() {
        int row = tableEmpAll.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return modeleEmpAll.getValueAt(row);
    }

    public void setEmpAllList(ArrayList<Emprunt> emp) {
        modeleEmpAll.setListeEmp(emp);
    }

    public void sendMailRelance(Etudiant student) {
        String mail = student.getEmail();
        if (!mail.equals("")) {
            JOptionPane.showMessageDialog(this, "Un email a bien été envoyé à l'adresse: " + student.getEmail() + "\nEn vrai non mais faites comme si");
        }
    }


    // INFO ÉTUDIANTS
    public int getJComboBoxID() {
        Etudiant etu = (Etudiant) infoComboBox.getSelectedItem();
        assert etu != null;
        return etu.getId();
    }

    public Etudiant getInfoEtudiant(int ID) {
        return new Etudiant(ID, infoPrenom.getText(), infoNom.getText(), infoEmail.getText(), infoMdp.getText());
    }

    public void setZoneFill(Etudiant etudiant) {
        if (etudiant == null) { //si null mettre a vide
            infoEmail.setText("");
            infoNom.setText("");
            infoPrenom.setText("");
            infoMdp.setText("");

        } else { // mettre info étudiant
            infoEmail.setText(etudiant.getEmail());
            infoNom.setText(etudiant.getNom());
            infoPrenom.setText(etudiant.getPrenom());
            infoMdp.setText(etudiant.getMdp());
        }
    }

    // RÉSERVATIONS
    public String getTitre() {
        return titreField.getText();
    }

    public String getAuteur() {
        return auteurField.getText();
    }


    public void addListener(BibliController controller) {
        // RETOUR LIVRES
        retourNom.addKeyListener(controller.enterListener(retourNom));
        retourPrenom.addKeyListener(controller.enterListener(retourPrenom));
        retourTitre.addKeyListener(controller.enterListener(retourTitre));
        retourAuteur.addKeyListener(controller.enterListener(retourAuteur));

        retourRecherche.setActionCommand("Retour-Recherche");
        retourRecherche.addActionListener(controller);
        retourRendu.setActionCommand("Retour-Rendu");
        retourRendu.addActionListener(controller);
        retourRelance.setActionCommand("Retour-Relance");
        retourRelance.addActionListener(controller);

        // INFO ÉTUDIANTS
        infoChoisir.setActionCommand("InfoEtu-Choisir");
        infoChoisir.addActionListener(controller);
        infoSauvegarder.setActionCommand("InfoEtu-Sauvegarder");
        infoSauvegarder.addActionListener(controller);
        infoNouveau.setActionCommand("InfoEtu-Nouveau");
        infoNouveau.addActionListener(controller);
        infoSupprimer.setActionCommand("InfoEtu-Supprimer");
        infoSupprimer.addActionListener(controller);

        //TODO Remplir avec les objets nécessitant un actionListener. Ne pas oublier setActionCommand("mot-clé")
    }

}
