package vue.etudiant;

import constantes.Constantes;
import control.StudentController;
import modele.*;
import util.DataBase;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class StudentPanel extends JPanel {

    private Etudiant student;
    private StudentController controller;

    private final JLabel title = new JLabel();

    private final JLabel profilNom = new JLabel();
    private final JLabel profilPrenom = new JLabel();
    private final JLabel profilMail = new JLabel();
    private final JLabel profilMdp = new JLabel();

    private final JButton searchButton = new JButton("Rechercher");
    private final JButton reserveButton = new JButton("Réserver");
    private final JButton cancelResButton = new JButton("Annuler la réservation");
    private final JButton newMdpButton = new JButton("Changer le mot de passe");

    private final JTextField titreField = new JTextField();
    private final JTextField auteurField = new JTextField();
    private final JPasswordField changeMdp = new JPasswordField();
    private final JPasswordField confirmMdp = new JPasswordField();

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final TableModeleRes modeleRes = new TableModeleRes();
    private final TableModeleEmp modeleEmp = new TableModeleEmp();
    private final JTable tableLiv = new JTable(modeleLiv);
    private final JTable tableRes = new JTable(modeleRes);
    private final JTable tableEmp = new JTable(modeleEmp);

    private final JLabel infoLiv = new JLabel();
    private final JLabel infoRes = new JLabel();
    private final JLabel infoEmp = new JLabel();

    public StudentPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        //Panel Titre

        JPanel panelTitre = panelTitre();

        //Content Panel

        JPanel content = new JPanel();
        CardLayout layout = new CardLayout();
        content.setLayout(layout);
        content.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        //Ajout onglets

        JTabbedPane onglets = new JTabbedPane();
        onglets.add("Nouvelle Réservation", ongletNewRes(c));
        onglets.add("Mes Réservations", ongletMesRes(c));
        onglets.add("Profil", ongletProfil(c));

        onglets.setFocusable(false);
        onglets.setMnemonicAt(0, KeyEvent.VK_N);
        onglets.setMnemonicAt(1, KeyEvent.VK_M);
        onglets.setMnemonicAt(2, KeyEvent.VK_P);

        content.add(onglets, BorderLayout.CENTER);

        add(panelTitre, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

    }

    /**
     * Panel titre (logique)
     *
     * @return JPanel
     */
    private JPanel panelTitre() {
        JPanel panelTitre = new JPanel();

        panelTitre.setOpaque(false);
        panelTitre.setLayout(new GridLayout(2, 1));

        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitle = new JLabel("Étudiant");
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        subtitle.setHorizontalAlignment(JLabel.CENTER);

        panelTitre.add(title);
        panelTitre.add(subtitle);
        return panelTitre;
    }

    /**
     * Onglet : Profil de l'étudiant
     *
     * @param c GridBagConstraints
     * @return JPanel
     */
    private JPanel ongletProfil(GridBagConstraints c) {
        JPanel monProfil = new JPanel();
        monProfil.setOpaque(false);
        monProfil.setLayout(new GridBagLayout());

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        JLabel label = new JLabel("Mon Profil");
        label.setFont(Constantes.TITLE_FONT);
        monProfil.add(label, c);

        c.gridx = 0;
        c.gridy = 1;

        JPanel newMdp = new JPanel(new GridLayout(2, 2, 10, 10));
        newMdp.setOpaque(false);
        newMdp.add(profilPrenom);
        newMdp.add(profilNom);
        newMdp.add(profilMail);
        newMdp.add(profilMdp);
        monProfil.add(newMdp, c);

        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 300;
        c.ipady = 7;
        c.anchor = GridBagConstraints.EAST;
        changeMdp.setFont(Constantes.FIELD_FONT);
        changeMdp.setBorder(Constantes.BORDER);
        monProfil.add(changeMdp, c);

        c.gridx = 0;
        c.gridy = 3;
        confirmMdp.setFont(Constantes.FIELD_FONT);
        confirmMdp.setBorder(Constantes.BORDER);
        monProfil.add(confirmMdp, c);

        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 25;
        c.ipady = 10;
        c.anchor = GridBagConstraints.WEST;
        JLabel label1 = new JLabel("Nouveau mot de passe");
        label1.setLabelFor(changeMdp);
        label1.setDisplayedMnemonic('o');
        monProfil.add(label1, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel label2 = new JLabel("Confirmer le mot de passe");
        label2.setLabelFor(confirmMdp);
        label2.setDisplayedMnemonic('c');
        monProfil.add(label2, c);

        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 0;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        newMdpButton.setFocusPainted(false);
        monProfil.add(newMdpButton, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.EAST;
        infoEmp.setText("Changez votre mot de passe ici");
        monProfil.add(infoEmp, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        JLabel vosEmprunts = new JLabel("Vos emprunts");
        vosEmprunts.setFont(Constantes.SUBTITLE_FONT);
        monProfil.add(vosEmprunts, c);

        c.gridx = 0;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;

        tableEmp.setFocusable(false);
        tableEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmp.getColumnModel().getColumn(0).setPreferredWidth(400);  // Column size
        tableEmp.getColumnModel().getColumn(1).setPreferredWidth(200);  //
        tableEmp.getColumnModel().getColumn(2).setPreferredWidth(100);  //
        tableEmp.setRowHeight(15);

        JScrollPane scroll = new JScrollPane(tableEmp);
        scroll.getViewport().setBackground(Constantes.WHITE);
        scroll.setPreferredSize(new Dimension(600, 97));
        scroll.setBorder(Constantes.BORDER);
        monProfil.add(scroll, c);

        return monProfil;
    }

    /**
     * Onglet : Réservations de l'étudiant
     *
     * @param c GridBagConstraints
     * @return JPanel
     */
    private JPanel ongletMesRes(GridBagConstraints c) {
        JPanel mesRes = new JPanel();
        mesRes.setOpaque(false);
        mesRes.setLayout(new GridBagLayout());

        c.insets = new Insets(8, 8, 50, 8);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label = new JLabel("Mes réservations");
        label.setFont(Constantes.TITLE_FONT);
        mesRes.add(label, c);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        infoRes.setText("Sélectionnez la réservation à annuler");
        infoRes.setHorizontalAlignment(JLabel.RIGHT);
        mesRes.add(infoRes, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;

        tableRes.setFocusable(false);
        tableRes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableRes.getColumnModel().getColumn(0).setPreferredWidth(300);  // Column size
        tableRes.getColumnModel().getColumn(1).setPreferredWidth(130);  //
        tableRes.getColumnModel().getColumn(2).setPreferredWidth(70);   //
        tableRes.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(tableRes);
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setPreferredSize(new Dimension(500, 87));
        scrollPane.setBorder(Constantes.BORDER);
        mesRes.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        cancelResButton.setFocusPainted(false);
        mesRes.add(cancelResButton, c);

        return mesRes;
    }

    /**
     * Onglet : Ajouter des réservations
     *
     * @param c GridBagConstraints
     * @return JPanel créé
     */
    private JPanel ongletNewRes(GridBagConstraints c) {
        //Onglet nouvelles réservations

        JPanel newRes = new JPanel(new GridBagLayout());
        newRes.setOpaque(false);

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

        tableLiv.setFocusable(false);
        tableLiv.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLiv.getColumnModel().getColumn(0).setPreferredWidth(Constantes.AUTEUR_SIZE);  // Column size
        tableLiv.getColumnModel().getColumn(1).setPreferredWidth(Constantes.TITRE_SIZE);   //

        JScrollPane scrollPane = new JScrollPane(tableLiv);
        scrollPane.setPreferredSize(new Dimension(400, 318));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);

        newRes.add(scrollPane, c);
        return newRes;
    }

    /**
     * Set the connected student and modify welcome message
     *
     * @param student Student that connected
     */
    public void setStudent(Etudiant student) {
        this.student = student;
        controller.updateStudentRes();
        title.setText("Bonjour " + student.getPrenom() + " " + student.getNom());

        controller.updateStudentEmp();
        profilNom.setText("Nom: " + student.getNom());
        profilPrenom.setText("Prénom: " + student.getPrenom());
        profilMail.setText("Email: " + student.getEmail());
        profilMdp.setText("Mot de passe: " + student.getMdp());

        setInfoMessageLiv(Constantes.BASIC_MESSAGE);
    }

    /**
     * Set the Database
     *
     * @param DB DataBase to set
     * @see DataBase
     */
    public void setDB(DataBase DB) {
        controller = new StudentController(this, DB); // !!! Contrôleur !!!
    }

    /**
     * Set new list of books in JTable model
     *
     * @param liste of books
     * @see TableModeleLiv
     */
    public void setBookList(ArrayList<Livre> liste) {
        modeleLiv.setListeLivres(liste);
    }

    public void setResList(Reservation[] res) {
        modeleRes.setListeRes(res);
    }

    public void setEmpList(Emprunt[] emp) {
        modeleEmp.setListeEmp(emp);
    }

    /**
     * @param i int corresponding to message wanted
     */
    public void setInfoMessageLiv(int i) {
        if (i == Constantes.NO_SELECTION) { // No book selected
            infoLiv.setText("Aucun livre sélectionné");
            infoLiv.setForeground(Constantes.ERROR);
        } else if (i == Constantes.BASIC_MESSAGE) {
            infoLiv.setText((Constantes.MAX_RES - student.getNbRes()) + " réservations possibles");
            infoLiv.setForeground(Constantes.BLACK);
        } else if (i == Constantes.MAX_RES_REACHED) {
            infoLiv.setText("Nombre maximal de réservation atteint (" + Constantes.MAX_RES + ")");
            infoLiv.setForeground(Constantes.ERROR);
        } else if (i == Constantes.ALREADY_RESERVED) {
            infoLiv.setText("Vous avez déjà réservé ce livre");
            infoLiv.setForeground(Constantes.ERROR);
        } else if (i == Constantes.SUCCESS) {
            infoLiv.setText("Réservation effectuée, plus que " + (Constantes.MAX_RES - student.getNbRes()) + " possibles");
            infoLiv.setForeground(Constantes.VALID);
        }
    }

    public void setInfoMessageRes(int i) {
        if (i == Constantes.NO_SELECTION) { // No book selected
            infoRes.setText("Aucun livre sélectionné");
            infoRes.setForeground(Constantes.ERROR);
        } else if (i == Constantes.SUCCESS) {
            infoRes.setText("La réservation a bien été annulée");
            infoRes.setForeground(Constantes.VALID);
        }
    }

    public void setInfoMessageEmp(int i) {
        if (i == Constantes.SUCCESS) {
            infoEmp.setText("Votre mot de passe a bien été changé");
            infoEmp.setForeground(Constantes.VALID);
        } else if (i == Constantes.MDP_DIFF) {
            infoEmp.setText("Mots de passe différents");
            infoEmp.setForeground(Constantes.ERROR);
        } else if (i == Constantes.MDP_INV) {
            infoEmp.setText("Mot de passe invalide");
            infoEmp.setForeground(Constantes.ERROR);
        }
    }

    public Etudiant getStudent() {
        return student;
    }

    public String getTitre() { return titreField.getText(); }

    public String getAuteur() { return auteurField.getText(); }

    /**
     * Method to get which book has been selected
     *
     * @return Selected book
     */
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

    public String[] getNewMdp() {
        String[] tab = new String[2];
        tab[0] = String.valueOf(changeMdp.getPassword());
        tab[1] = String.valueOf(confirmMdp.getPassword());
        return tab;
    }

    public void addListener(StudentController controller) {
        searchButton.setActionCommand("Rechercher");
        searchButton.addActionListener(controller);

        reserveButton.setActionCommand("Réserver");
        reserveButton.addActionListener(controller);

        cancelResButton.setActionCommand("Supprimer");
        cancelResButton.addActionListener(controller);

        newMdpButton.setActionCommand("Changer mdp");
        newMdpButton.addActionListener(controller);

        titreField.addKeyListener(controller.enterListener(titreField));
        auteurField.addKeyListener(controller.enterListener(auteurField));
    }

    public void setFocus() {
        titreField.requestFocusInWindow();
    }

    public void resetMdpChange() {
        changeMdp.requestFocusInWindow();
        confirmMdp.setText("");
    }

    public void setNewMdp(String mdp) {
        changeMdp.setText("");
        confirmMdp.setText("");
        student.setMdp(mdp);
        profilMdp.setText("Mot de passe: " + mdp);
    }
}