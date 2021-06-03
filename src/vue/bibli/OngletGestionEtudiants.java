package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Etudiant;

import javax.swing.*;
import java.awt.*;

public class OngletGestionEtudiants extends JPanel {

    private final JTextField infoEmail = new JTextField();
    private final JTextField infoNom = new JTextField();
    private final JTextField infoPrenom = new JTextField();
    private final JTextField infoMdp = new JTextField();

    private final JComboBox<Etudiant> infoComboBox = new JComboBox<>();
    private final JLabel labelID = new JLabel("", JLabel.CENTER);

    private final JButton infoSauvegarder = new JButton("Sauvegarder");
    private final JButton infoSupprimer = new JButton("Supprimer");


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
        infoComboBox.setFont(Constantes.FIELD_FONT);
        infoComboBox.setBackground(Constantes.WHITE);
        add(infoComboBox, c);

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
        labelNom.setLabelFor(infoNom);
        labelNom.setDisplayedMnemonic('n');
        add(labelNom, c);

        c.gridy = 2;
        JLabel labelPrenom = new JLabel("Prénom de l'étudiant :");
        labelPrenom.setLabelFor(infoPrenom);
        labelPrenom.setDisplayedMnemonic('p');
        add(labelPrenom, c);

        c.gridy = 3;
        JLabel labelEmail = new JLabel("Email de l'étudiant :");
        labelEmail.setLabelFor(infoEmail);
        labelEmail.setDisplayedMnemonic('e');
        add(labelEmail, c);

        c.gridy = 4;
        JLabel labelMdp = new JLabel("Mot De Passe de l'étudiant :");
        labelMdp.setLabelFor(infoMdp);
        labelMdp.setDisplayedMnemonic('m');
        add(labelMdp, c);

        // FIELDS
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 100;
        c.ipady = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        infoNom.setFont(Constantes.FIELD_FONT);
        infoNom.setBorder(Constantes.BORDER);
        add(infoNom, c);

        c.gridy = 2;
        infoPrenom.setFont(Constantes.FIELD_FONT);
        infoPrenom.setBorder(Constantes.BORDER);
        add(infoPrenom, c);

        c.gridy = 3;
        infoEmail.setFont(Constantes.FIELD_FONT);
        infoEmail.setBorder(Constantes.BORDER);
        add(infoEmail, c);

        c.gridy = 4;
        infoMdp.setFont(Constantes.FIELD_FONT);
        infoMdp.setBorder(Constantes.BORDER);
        add(infoMdp, c);

        /* Début bouton */
        JPanel boutons = new JPanel(new GridLayout(2, 2, 10, 10));
        boutons.setOpaque(false);

        infoSauvegarder.setFocusPainted(false);
        infoSupprimer.setFocusPainted(false);

        boutons.add(infoSauvegarder, c);
        boutons.add(infoSupprimer, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        add(boutons, c);
        /* Fin boutons */
    }

    public int getStudentID() {
        Etudiant etu = (Etudiant) infoComboBox.getSelectedItem();
        if (etu == null) return -1;
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

    public void setLabelID(String ID) {
        labelID.setText("ID :        " + ID);
    }

    public JComboBox<Etudiant> getComboBox() {
        return infoComboBox;
    }

    public void addListener(BibliController controller) {
        // INFO ÉTUDIANTS
        infoComboBox.setActionCommand("InfoEtu-Choisir");
        infoComboBox.addActionListener(controller);
        infoSauvegarder.setActionCommand("InfoEtu-Sauvegarder");
        infoSauvegarder.addActionListener(controller);
        infoSupprimer.setActionCommand("InfoEtu-Supprimer");
        infoSupprimer.addActionListener(controller);
    }

}
