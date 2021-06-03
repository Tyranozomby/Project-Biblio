package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Reservation;
import modele.TableModeleResAll;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletValiderRes extends JPanel {

    private final JTextField resNom = new JTextField();
    private final JTextField resPrenom = new JTextField();
    private final JTextField resTitre = new JTextField();
    private final JTextField resAuteur = new JTextField();

    private final JButton resRecherche = new JButton("Rechercher");
    private final JButton resValider = new JButton("Valider la réservation");

    private final TableModeleResAll modeleResAll = new TableModeleResAll();
    private final JTable tableResAll = new JTable(modeleResAll);

    public OngletValiderRes() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 0;
        c.gridy = 1;
        tableResAll.setFocusable(false);
        tableResAll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableResAll.setRowHeight(15);
        tableResAll.getColumnModel().getColumn(0).setPreferredWidth(300);  //
        tableResAll.getColumnModel().getColumn(1).setPreferredWidth(250);  // Column size
        tableResAll.getColumnModel().getColumn(2).setPreferredWidth(150);  //
        tableResAll.getColumnModel().getColumn(3).setPreferredWidth(100);  //

        JScrollPane scrollPane = new JScrollPane(tableResAll);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);
        add(scrollPane, c);

        c.insets = new Insets(8, 250, 8, 250);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel fields = new JPanel(new GridLayout(4, 2, 10, 10));
        fields.setOpaque(false);

        JLabel nomEtu = new JLabel("Nom élève:");
        nomEtu.setLabelFor(resNom);
        nomEtu.setDisplayedMnemonic('n');
        fields.add(nomEtu);

        resNom.setFont(Constantes.FIELD_FONT);
        resNom.setBorder(Constantes.BORDER);
        fields.add(resNom);

        JLabel prenomEtu = new JLabel("Prénom élève:");
        prenomEtu.setLabelFor(resPrenom);
        prenomEtu.setDisplayedMnemonic('p');
        fields.add(prenomEtu);

        resPrenom.setFont(Constantes.FIELD_FONT);
        resPrenom.setBorder(Constantes.BORDER);
        fields.add(resPrenom);

        JLabel titre = new JLabel("Titre:");
        titre.setLabelFor(resTitre);
        titre.setDisplayedMnemonic('t');
        fields.add(titre);

        resTitre.setFont(Constantes.FIELD_FONT);
        resTitre.setBorder(Constantes.BORDER);
        fields.add(resTitre);

        JLabel auteur = new JLabel("Auteur:");
        auteur.setLabelFor(resAuteur);
        auteur.setDisplayedMnemonic('a');
        fields.add(auteur);

        resAuteur.setFont(Constantes.FIELD_FONT);
        resAuteur.setBorder(Constantes.BORDER);
        fields.add(resAuteur);
        add(fields, c);

        c.gridy = 2;
        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 10));
        buttons.setOpaque(false);
        resRecherche.setFocusPainted(false);    //
        resValider.setFocusPainted(false);      // FOCUS PAINTED FALSE
        buttons.add(resRecherche);  //
        buttons.add(resValider);    // ADD

        add(buttons, c);
    }

    public String getResNom() {
        return resNom.getText();
    }

    public String getResPrenom() {
        return resPrenom.getText();
    }

    public String getResTitre() {
        return resTitre.getText();
    }

    public String getResAuteur() {
        return resAuteur.getText();
    }

    public Reservation getSelectedRes() {
        int row = tableResAll.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return modeleResAll.getValueAt(row);
    }

    public TableModeleResAll getModel() {
        return modeleResAll;
    }

    public void setResAllList(ArrayList<Reservation> res) {
        modeleResAll.setListeRes(res);
    }

    public void addListener(BibliController controller) {
        resNom.addKeyListener(controller.enterListener(resNom, "Res-Recherche"));
        resPrenom.addKeyListener(controller.enterListener(resPrenom, "Res-Recherche"));
        resTitre.addKeyListener(controller.enterListener(resTitre, "Res-Recherche"));
        resAuteur.addKeyListener(controller.enterListener(resAuteur, "Res-Recherche"));

        resRecherche.setActionCommand("Res-Recherche");
        resRecherche.addActionListener(controller);
        resValider.setActionCommand("Res-Valider");
        resValider.addActionListener(controller);
    }
}
