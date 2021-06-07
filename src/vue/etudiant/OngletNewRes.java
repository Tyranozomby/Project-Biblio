package vue.etudiant;

import constantes.Constantes;
import control.StudentController;
import modele.Etudiant;
import modele.Livre;
import modele.TableModeleLiv;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletNewRes extends JPanel {

    private final JLabel infoMessage = new JLabel();
    private final JTextField titreField = new JTextField();
    private final JTextField auteurField = new JTextField();
    private final JButton searchButton = new JButton("Rechercher");
    private final JButton reserveButton = new JButton("Réserver");

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final JTable tableLiv = new JTable(modeleLiv);

    private final Etudiant student;

    public OngletNewRes(Etudiant student) {
        this.student = student;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setOpaque(false);

        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        titreField.setFont(Constantes.FIELD_FONT);
        titreField.setBorder(Constantes.BORDER);
        add(titreField, c);

        c.gridx = 1;
        c.gridy = 1;
        auteurField.setFont(Constantes.FIELD_FONT);
        auteurField.setBorder(Constantes.BORDER);
        add(auteurField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelTitre = new JLabel("Titre :");
        labelTitre.setLabelFor(titreField);
        labelTitre.setDisplayedMnemonic('t');
        add(labelTitre, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelAuteur = new JLabel("Auteur :");
        labelAuteur.setLabelFor(auteurField);
        labelAuteur.setDisplayedMnemonic('a');
        add(labelAuteur, c);

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
        add(panelBoutons, c);
        //**********Fin boutons**********

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.NONE;
        setInfoMessage(Constantes.BASIC_MESSAGE);
        add(infoMessage, c);

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

        add(scrollPane, c);
    }


    public String getTitre() {
        return titreField.getText();
    }

    public String getAuteur() {
        return auteurField.getText();
    }

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
        setInfoMessage(Constantes.BASIC_MESSAGE);
        return modeleLiv.getValueAt(row);
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

    /**
     * @param i int corresponding to message wanted
     */
    public void setInfoMessage(int i) {
        if (i == Constantes.NO_SELECTION) { // No book selected
            infoMessage.setText("Aucun livre sélectionné");
            infoMessage.setForeground(Constantes.RED);
        } else if (i == Constantes.BASIC_MESSAGE) {
            infoMessage.setText((Constantes.MAX_RES - student.getNbRes()) + " réservations possibles");
            infoMessage.setForeground(Constantes.BLACK);
        } else if (i == Constantes.MAX_RES_REACHED) {
            infoMessage.setText("Nombre maximal de réservation atteint (" + Constantes.MAX_RES + ")");
            infoMessage.setForeground(Constantes.RED);
        } else if (i == Constantes.ALREADY_RESERVED) {
            infoMessage.setText("Vous avez déjà réservé ce livre");
            infoMessage.setForeground(Constantes.RED);
        } else if (i == Constantes.SUCCESS) {
            infoMessage.setText("Réservation effectuée, plus que " + (Constantes.MAX_RES - student.getNbRes()) + " possibles");
            infoMessage.setForeground(Constantes.GREEN);
        }
    }

    public void addListener(StudentController controller) {
        titreField.addKeyListener(controller.enterListener(titreField));
        auteurField.addKeyListener(controller.enterListener(auteurField));

        searchButton.setActionCommand("Rechercher");
        searchButton.addActionListener(controller);
        reserveButton.setActionCommand("Réserver");
        reserveButton.addActionListener(controller);
    }
}
