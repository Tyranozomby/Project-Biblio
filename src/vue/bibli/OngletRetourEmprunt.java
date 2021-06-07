package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Emprunt;
import modele.Etudiant;
import modele.TableModeleEmpAll;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OngletRetourEmprunt extends JPanel {

    private final JLabel infoMessage = new JLabel("", JLabel.RIGHT);

    private final JTextField nom = new JTextField();
    private final JTextField prenom = new JTextField();
    private final JTextField titre = new JTextField();
    private final JTextField auteur = new JTextField();
    private final JCheckBox onlyLate = new JCheckBox("Ne montrer que les retards");

    private final JButton rechercher = new JButton("Rechercher");
    private final JButton rendu = new JButton("Livre bien rendu");
    private final JButton relance = new JButton("Relancer l'élève");

    private final TableModeleEmpAll modele = new TableModeleEmpAll();
    private final JTable table = new JTable(modele);

    public OngletRetourEmprunt() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 8, 8);

        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        setInfoMessage(Constantes.BASIC_MESSAGE);
        add(infoMessage, c);

        // TABLEAU
        c.gridy = 2;
        table.setFocusable(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(300);  //
        table.getColumnModel().getColumn(1).setPreferredWidth(250);  // Column size
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  //
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  //
        table.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 218));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);
        add(scrollPane, c);

        // TEXT FIELDS
        c.insets = new Insets(8, 250, 8, 250);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel fields = new JPanel(new GridLayout(5, 2, 10, 10));
        fields.setOpaque(false);

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
        labelTitre.setLabelFor(this.titre);
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

        onlyLate.setOpaque(false);
        onlyLate.setFocusPainted(false);
        fields.add(onlyLate);

        add(fields, c);

        c.insets = new Insets(30, 100, 8, 100);

        // BOUTONS
        c.gridy = 3;
        JPanel boutons = new JPanel(new GridLayout(1, 3, 10, 10));
        boutons.setOpaque(false);
        rechercher.setFocusPainted(false);  //
        rendu.setFocusPainted(false);       // FOCUS PAINTED FALSE
        relance.setFocusPainted(false);     //
        boutons.add(rechercher);    //
        boutons.add(rendu);         // ADD
        boutons.add(relance);       //

        add(boutons, c);
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

    public boolean isCheckboxSelected() {
        return onlyLate.isSelected();
    }

    public Emprunt getRetourSelectedEmp() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return modele.getValueAt(row);
    }

    public TableModeleEmpAll getModel() {
        return modele;
    }

    public void setList(ArrayList<Emprunt> emp) {
        modele.setListeEmp(emp);
    }

    public void sendMailRelance(Etudiant student) {
        String mail = student.getEmail();
        if (!mail.equals("")) {
            JOptionPane.showMessageDialog(this, "Un email a bien été envoyé à l'adresse: " + student.getEmail() + "\nEn vrai non mais faites comme si");
        }
    }

    public void setInfoMessage(int i) {
        if (i == Constantes.BASIC_MESSAGE) {
            infoMessage.setText("Onglet de gestion des emprunts");
            infoMessage.setForeground(Constantes.BLACK);
        } else if (i == Constantes.SUCCESS) {
            infoMessage.setText("Le retour du livre a bien été enregistré");
            infoMessage.setForeground(Constantes.GREEN);
        } else if (i == Constantes.NO_SELECTION) {
            infoMessage.setText("Veuillez choisir un emprunt");
            infoMessage.setForeground(Constantes.RED);
        }
    }

    public void addListener(BibliController controller) {
        nom.addKeyListener(controller.enterListener(nom, "Retour-Recherche"));
        prenom.addKeyListener(controller.enterListener(prenom, "Retour-Recherche"));
        titre.addKeyListener(controller.enterListener(titre, "Retour-Recherche"));
        auteur.addKeyListener(controller.enterListener(auteur, "Retour-Recherche"));

        onlyLate.setActionCommand("Retour-Recherche");
        onlyLate.addActionListener(controller);
        rechercher.setActionCommand("Retour-Recherche");
        rechercher.addActionListener(controller);
        rendu.setActionCommand("Retour-Rendu");
        rendu.addActionListener(controller);
        relance.setActionCommand("Retour-Relance");
        relance.addActionListener(controller);
    }

}
