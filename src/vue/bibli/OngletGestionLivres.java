package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Livre;
import modele.TableModeleLiv;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletGestionLivres extends JPanel {

    private final JLabel infoMessage = new JLabel("", JLabel.RIGHT);

    private final JTextField titre = new JTextField();
    private final JTextField auteur = new JTextField();

    private final JButton rechercher = new JButton("Rechercher");
    private final JButton ajouter = new JButton("Ajouter un livre");
    private final JButton supprimer = new JButton("Supprimer un livre");

    private final TableModeleLiv modele = new TableModeleLiv();
    private final JTable table = new JTable(modele);

    public OngletGestionLivres() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        titre.setFont(Constantes.FIELD_FONT);
        titre.setBorder(Constantes.BORDER);
        add(titre, c);

        c.gridx = 1;
        c.gridy = 1;
        auteur.setFont(Constantes.FIELD_FONT);
        auteur.setBorder(Constantes.BORDER);
        add(auteur, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelTitre = new JLabel("Titre :");
        labelTitre.setLabelFor(titre);
        labelTitre.setDisplayedMnemonic('t');
        add(labelTitre, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelAuteur = new JLabel("Auteur :");
        labelAuteur.setLabelFor(auteur);
        labelAuteur.setDisplayedMnemonic('a');
        add(labelAuteur, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.EAST;
        setInfoMessage(Constantes.BASIC_MESSAGE);
        add(infoMessage, c);


        //**********Début boutons**********
        JPanel panelBoutons = new JPanel(new GridLayout(1, 2, 20, 2));
        panelBoutons.setOpaque(false);

        rechercher.setFocusPainted(false);
        ajouter.setFocusPainted(false);
        supprimer.setFocusPainted(false);

        panelBoutons.add(rechercher);
        panelBoutons.add(ajouter);
        panelBoutons.add(supprimer);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(panelBoutons, c);
        //**********Fin boutons**********

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);

        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(Constantes.AUTEUR_SIZE);  // Column size
        table.getColumnModel().getColumn(1).setPreferredWidth(Constantes.TITRE_SIZE);   //
        table.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 222));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);

        add(scrollPane, c);
    }

    public String getTitre() {
        return titre.getText();
    }

    public String getAuteur() {
        return auteur.getText();
    }

    public Livre getSelectedBook() {
        int row = table.getSelectedRow();
        if (row == -1) { // No book selected
            return null;
        }
        return modele.getValueAt(row);
    }

    public TableModeleLiv getModel() {
        return modele;
    }

    public void setList(ArrayList<Livre> liste) {
        modele.setListeLivres(liste);
    }

    public void setInfoMessage(int i) {
        if (i == Constantes.BASIC_MESSAGE) {
            infoMessage.setText("Onglet de gestion des livres");
            infoMessage.setForeground(Constantes.BLACK);
        } else if (i == Constantes.SUCCESS) {
            infoMessage.setText("Votre action a bien été exécutée");
            infoMessage.setForeground(Constantes.GREEN);
        } else if (i == Constantes.NO_SELECTION) {
            infoMessage.setText("Veuillez sélectionner le livre que vous souhaitez supprimer");
            infoMessage.setForeground(Constantes.RED);
        } else if (i == Constantes.ERROR) {
            infoMessage.setText("Tous les champs ne sont pas remplis ou ce livre existe déjà");
            infoMessage.setForeground(Constantes.RED);
        }
    }

    public void addListener(BibliController controller) {

        titre.addKeyListener(controller.enterListener(titre, "Livre-Search"));
        auteur.addKeyListener(controller.enterListener(auteur, "Livre-Search"));

        rechercher.setActionCommand("Livre-Search");
        rechercher.addActionListener(controller);
        ajouter.setActionCommand("Livre-Ajout");
        ajouter.addActionListener(controller);
        supprimer.setActionCommand("Livre-Suppression");
        supprimer.addActionListener(controller);
    }
}
