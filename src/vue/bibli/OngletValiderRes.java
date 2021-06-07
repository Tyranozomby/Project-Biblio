package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Reservation;
import modele.TableModeleResAll;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletValiderRes extends JPanel {

    private final JLabel infoMessage = new JLabel("", JLabel.RIGHT);

    private final JTextField nom = new JTextField();
    private final JTextField prenom = new JTextField();
    private final JTextField titre = new JTextField();
    private final JTextField auteur = new JTextField();

    private final JButton rechercher = new JButton("Rechercher");
    private final JButton valider = new JButton("Valider la réservation");

    private final TableModeleResAll modele = new TableModeleResAll();
    private final JTable table = new JTable(modele);

    public OngletValiderRes() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        setInfoMessage(Constantes.BASIC_MESSAGE);
        add(infoMessage, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(15);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);  //
        table.getColumnModel().getColumn(1).setPreferredWidth(250);  // Column size
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  //
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  //

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);
        add(scrollPane, c);

        c.insets = new Insets(8, 220, 8, 220);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel fields = new JPanel(new GridLayout(4, 2, 10, 10));
        fields.setOpaque(false);

        nom.setPreferredSize(Constantes.FIELD_SIZE);
        prenom.setPreferredSize(Constantes.FIELD_SIZE);
        titre.setPreferredSize(Constantes.FIELD_SIZE);
        auteur.setPreferredSize(Constantes.FIELD_SIZE);

        JLabel nomEtu = new JLabel("Nom élève:");
        nomEtu.setLabelFor(nom);
        nomEtu.setDisplayedMnemonic('n');
        fields.add(nomEtu);

        nom.setFont(Constantes.FIELD_FONT);
        nom.setBorder(Constantes.BORDER);
        fields.add(nom);

        JLabel prenomEtu = new JLabel("Prénom élève:");
        prenomEtu.setLabelFor(prenom);
        prenomEtu.setDisplayedMnemonic('p');
        fields.add(prenomEtu);

        prenom.setFont(Constantes.FIELD_FONT);
        prenom.setBorder(Constantes.BORDER);
        fields.add(prenom);

        JLabel labelTitre = new JLabel("Titre:");
        labelTitre.setLabelFor(titre);
        labelTitre.setDisplayedMnemonic('t');
        fields.add(labelTitre);

        titre.setFont(Constantes.FIELD_FONT);
        titre.setBorder(Constantes.BORDER);
        fields.add(titre);

        JLabel labelAuteur = new JLabel("Auteur:");
        labelAuteur.setLabelFor(auteur);
        labelAuteur.setDisplayedMnemonic('a');
        fields.add(labelAuteur);

        auteur.setFont(Constantes.FIELD_FONT);
        auteur.setBorder(Constantes.BORDER);
        fields.add(auteur);
        add(fields, c);

        c.gridy = 3;
        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 10));
        buttons.setOpaque(false);
        rechercher.setFocusPainted(false);    //
        valider.setFocusPainted(false);      // FOCUS PAINTED FALSE
        buttons.add(rechercher);  //
        buttons.add(valider);    // ADD

        add(buttons, c);
    }

    public String getNom() {
        return nom.getText();
    }

    public String getPrenom() {
        return prenom.getText();
    }

    public String getTitre() {
        return titre.getText();
    }

    public String getAuteur() {
        return auteur.getText();
    }

    public Reservation getSelectedRes() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return modele.getValueAt(row);
    }

    public TableModeleResAll getModel() {
        return modele;
    }

    public void setList(ArrayList<Reservation> res) {
        modele.setListeRes(res);
    }

    public void setInfoMessage(int i) {
        if (i == Constantes.BASIC_MESSAGE) {
            infoMessage.setText("Vous pouvez ici valider les réservations en attente");
            infoMessage.setForeground(Constantes.BLACK);
        } else if (i == Constantes.NO_SELECTION) {
            infoMessage.setText("Veuillez sélectionner une réservation");
            infoMessage.setForeground(Constantes.RED);
        } else if (i == Constantes.SUCCESS) {
            infoMessage.setText("La réservation a bien été validée");
            infoMessage.setForeground(Constantes.GREEN);
        } else if (i == Constantes.ERROR) {
            infoMessage.setText("Ce livre n'a plus d'exemplaire, l'élève a déjà " + Constantes.MAX_EMP + " emprunts ou il a déjà ce livre d'emprunté");
            infoMessage.setForeground(Constantes.RED);
        }
    }

    public void addListener(BibliController controller) {
        nom.addKeyListener(controller.enterListener(nom, "Res-Recherche"));
        prenom.addKeyListener(controller.enterListener(prenom, "Res-Recherche"));
        titre.addKeyListener(controller.enterListener(titre, "Res-Recherche"));
        auteur.addKeyListener(controller.enterListener(auteur, "Res-Recherche"));

        rechercher.setActionCommand("Res-Recherche");
        rechercher.addActionListener(controller);
        valider.setActionCommand("Res-Valider");
        valider.addActionListener(controller);
    }
}
