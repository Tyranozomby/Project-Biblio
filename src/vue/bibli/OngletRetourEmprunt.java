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

    private final JTextField retourNom = new JTextField();
    private final JTextField retourPrenom = new JTextField();
    private final JTextField retourTitre = new JTextField();
    private final JTextField retourAuteur = new JTextField();
    private final JCheckBox onlyLate = new JCheckBox("Ne montrer que les retards");

    private final JButton retourRecherche = new JButton("Rechercher");
    private final JButton retourRendu = new JButton("Livre bien rendu");
    private final JButton retourRelance = new JButton("Relancer l'élève");

    private final TableModeleEmpAll modeleEmpAll = new TableModeleEmpAll();
    private final JTable tableEmpAll = new JTable(modeleEmpAll);

    public OngletRetourEmprunt() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // TABLEAU
        c.gridy = 1;
        c.gridwidth = 1;
        tableEmpAll.setFocusable(false);
        tableEmpAll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmpAll.getColumnModel().getColumn(0).setPreferredWidth(300);  //
        tableEmpAll.getColumnModel().getColumn(1).setPreferredWidth(250);  // Column size
        tableEmpAll.getColumnModel().getColumn(2).setPreferredWidth(150);  //
        tableEmpAll.getColumnModel().getColumn(3).setPreferredWidth(100);  //
        tableEmpAll.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(tableEmpAll);
        scrollPane.setPreferredSize(new Dimension(800, 218));
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setBorder(Constantes.BORDER);
        add(scrollPane, c);

        // TEXT FIELDS
        c.insets = new Insets(8, 250, 30, 250);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel fields = new JPanel(new GridLayout(5, 2, 10, 10));
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

        onlyLate.setOpaque(false);
        onlyLate.setFocusPainted(false);
        fields.add(onlyLate);

        add(fields, c);

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

        add(boutons, c);
    }


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

    public boolean isCheckboxSelected() {
        return onlyLate.isSelected();
    }

    public Emprunt getRetourSelectedEmp() {
        int row = tableEmpAll.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return modeleEmpAll.getValueAt(row);
    }

    public TableModeleEmpAll getModel() {
        return modeleEmpAll;
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

    public void addListener(BibliController controller) {
        retourNom.addKeyListener(controller.enterListener(retourNom, "Retour-Recherche"));
        retourPrenom.addKeyListener(controller.enterListener(retourPrenom, "Retour-Recherche"));
        retourTitre.addKeyListener(controller.enterListener(retourTitre, "Retour-Recherche"));
        retourAuteur.addKeyListener(controller.enterListener(retourAuteur, "Retour-Recherche"));

        onlyLate.setActionCommand("Retour-Recherche");
        onlyLate.addActionListener(controller);
        retourRecherche.setActionCommand("Retour-Recherche");
        retourRecherche.addActionListener(controller);
        retourRendu.setActionCommand("Retour-Rendu");
        retourRendu.addActionListener(controller);
        retourRelance.setActionCommand("Retour-Relance");
        retourRelance.addActionListener(controller);
    }

}
