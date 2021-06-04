package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Etudiant;

import javax.swing.*;
import java.awt.*;

public class OngletGestionEtudiants extends JPanel {

    private final JTextField email = new JTextField();
    private final JTextField nom = new JTextField();
    private final JTextField prenom = new JTextField();
    private final JTextField mdp = new JTextField();

    private final JComboBox<Etudiant> comboBox = new JComboBox<>();
    private final JLabel labelID = new JLabel("", JLabel.CENTER);

    private final JButton sauvegarder = new JButton("Sauvegarder");
    private final JButton supprimer = new JButton("Supprimer");


    public OngletGestionEtudiants() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 8, 8);

        // ligne 1

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        c.ipady = 7;
        comboBox.setFont(Constantes.FIELD_FONT);
        comboBox.setBackground(Constantes.WHITE);
        add(comboBox, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 50;
        c.ipady = 10;
        add(labelID, c);

        // LABELS
        c.gridx = 0;
        c.gridy = 1;
        JLabel labelNom = new JLabel("Nom de l'étudiant :");
        labelNom.setLabelFor(nom);
        labelNom.setDisplayedMnemonic('n');
        add(labelNom, c);

        c.gridy = 2;
        JLabel labelPrenom = new JLabel("Prénom de l'étudiant :");
        labelPrenom.setLabelFor(prenom);
        labelPrenom.setDisplayedMnemonic('p');
        add(labelPrenom, c);

        c.gridy = 3;
        JLabel labelEmail = new JLabel("Email de l'étudiant :");
        labelEmail.setLabelFor(email);
        labelEmail.setDisplayedMnemonic('e');
        add(labelEmail, c);

        c.gridy = 4;
        JLabel labelMdp = new JLabel("Mot De Passe de l'étudiant :");
        labelMdp.setLabelFor(mdp);
        labelMdp.setDisplayedMnemonic('m');
        add(labelMdp, c);

        // FIELDS
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 100;
        c.ipady = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        nom.setFont(Constantes.FIELD_FONT);
        nom.setBorder(Constantes.BORDER);
        add(nom, c);

        c.gridy = 2;
        prenom.setFont(Constantes.FIELD_FONT);
        prenom.setBorder(Constantes.BORDER);
        add(prenom, c);

        c.gridy = 3;
        email.setFont(Constantes.FIELD_FONT);
        email.setBorder(Constantes.BORDER);
        add(email, c);

        c.gridy = 4;
        mdp.setFont(Constantes.FIELD_FONT);
        mdp.setBorder(Constantes.BORDER);
        add(mdp, c);

        /* Début bouton */
        JPanel boutons = new JPanel(new GridLayout(2, 2, 10, 10));
        boutons.setOpaque(false);

        sauvegarder.setFocusPainted(false);
        supprimer.setFocusPainted(false);

        boutons.add(sauvegarder, c);
        boutons.add(supprimer, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        add(boutons, c);
        /* Fin boutons */
    }

    public int getStudentID() {
        Etudiant etu = (Etudiant) comboBox.getSelectedItem();
        if (etu == null) return -1;
        return etu.getId();
    }

    public Etudiant getInfoEtudiant(int ID) {
        return new Etudiant(ID, prenom.getText(), nom.getText(), email.getText(), mdp.getText());
    }

    public void fillInfoEtudiant(Etudiant etudiant) {
        if (etudiant == null) { //si null mettre a vide
            email.setText("");
            nom.setText("");
            prenom.setText("");
            mdp.setText("");

        } else { // mettre info étudiant
            email.setText(etudiant.getEmail());
            nom.setText(etudiant.getNom());
            prenom.setText(etudiant.getPrenom());
            mdp.setText(etudiant.getMdp());
        }
    }

    public void setLabelID(String ID) {
        labelID.setText("ID :        " + ID);
    }

    public JComboBox<Etudiant> getComboBox() {
        return comboBox;
    }

    public void addListener(BibliController controller) {
        // INFO ÉTUDIANTS
        comboBox.setActionCommand("InfoEtu-Choisir");
        comboBox.addActionListener(controller);
        sauvegarder.setActionCommand("InfoEtu-Sauvegarder");
        sauvegarder.addActionListener(controller);
        supprimer.setActionCommand("InfoEtu-Supprimer");
        supprimer.addActionListener(controller);
    }

}
