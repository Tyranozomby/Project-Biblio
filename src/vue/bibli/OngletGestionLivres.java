package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Livre;
import modele.TableModeleLiv;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletGestionLivres extends JPanel {

    private final JTextField livreTitre = new JTextField();
    private final JTextField livreAuteur = new JTextField();

    private final JButton livreSearch = new JButton("Rechercher");
    private final JButton livreAjouter = new JButton("Ajouter un livre");
    private final JButton livreSuppr = new JButton("Supprimer un livre");

    private final TableModeleLiv modeleLiv = new TableModeleLiv();
    private final JTable tableLiv = new JTable(modeleLiv);

    public OngletGestionLivres() {
        //Onglet nouvelles réservations
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
        livreTitre.setFont(Constantes.FIELD_FONT);
        livreTitre.setBorder(Constantes.BORDER);
        add(livreTitre, c);

        c.gridx = 1;
        c.gridy = 1;
        livreAuteur.setFont(Constantes.FIELD_FONT);
        livreAuteur.setBorder(Constantes.BORDER);
        add(livreAuteur, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelTitre = new JLabel("Titre :");
        labelTitre.setLabelFor(livreTitre);
        labelTitre.setDisplayedMnemonic('t');
        add(labelTitre, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        JLabel labelAuteur = new JLabel("Auteur :");
        labelAuteur.setLabelFor(livreAuteur);
        labelAuteur.setDisplayedMnemonic('a');
        add(labelAuteur, c);

        //**********Début boutons**********
        JPanel panelBoutons = new JPanel();
        panelBoutons.setOpaque(false);
        panelBoutons.setLayout(new GridLayout(1, 2, 20, 2));

        livreSearch.setFocusPainted(false);
        livreAjouter.setFocusPainted(false);
        livreSuppr.setFocusPainted(false);

        panelBoutons.add(livreSearch);
        panelBoutons.add(livreAjouter);
        panelBoutons.add(livreSuppr);

        c.gridx = 0;
        c.gridy = 2;
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

        tableLiv.setFocusable(false);
        tableLiv.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLiv.getColumnModel().getColumn(0).setPreferredWidth(Constantes.AUTEUR_SIZE);  // Column size
        tableLiv.getColumnModel().getColumn(1).setPreferredWidth(Constantes.TITRE_SIZE);   //
        tableLiv.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(tableLiv);
        scrollPane.setPreferredSize(new Dimension(400, 222));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);

        add(scrollPane, c);
    }

    public String getLivTitre() {
        return livreTitre.getText();
    }

    public String getLivAuteur() {
        return livreAuteur.getText();
    }

    public Livre getSelectedBook() {
        int row = tableLiv.getSelectedRow();
        if (row == -1) { // No book selected
            return null;
        }
        return modeleLiv.getValueAt(row);
    }

    public TableModeleLiv getModel() {
        return modeleLiv;
    }

    public void setBookList(ArrayList<Livre> liste) {
        modeleLiv.setListeLivres(liste);
    }

    public void addListener(BibliController controller) {

        livreTitre.addKeyListener(controller.enterListener(livreTitre, "Livre-Search"));
        livreAuteur.addKeyListener(controller.enterListener(livreAuteur, "Livre-Search"));

        livreSearch.setActionCommand("Livre-Search");
        livreSearch.addActionListener(controller);
        livreAjouter.setActionCommand("Livre-Ajout");
        livreAjouter.addActionListener(controller);
        livreSuppr.setActionCommand("Livre-Suppression");
        livreSuppr.addActionListener(controller);
    }
}
