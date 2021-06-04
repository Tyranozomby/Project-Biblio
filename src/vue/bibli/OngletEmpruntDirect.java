package vue.bibli;

import constantes.Constantes;
import control.BibliController;
import modele.Etudiant;
import modele.Livre;

import javax.swing.*;
import java.awt.*;

public class OngletEmpruntDirect extends JPanel {

    private final JComboBox<Livre> comboLivres = new JComboBox<>();
    private final JComboBox<Etudiant> comboEtu = new JComboBox<>();
    private final JButton empAjout = new JButton("Ajouter");

    public OngletEmpruntDirect() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 100, 8);

        c.gridy = 0;
        c.gridwidth = 2;
        JLabel label = new JLabel("Emprunt Direct", JLabel.CENTER);
        label.setFont(Constantes.TITLE_FONT);
        add(label, c);

        c.insets = new Insets(8, 8, 8, 8);

        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 100;
        c.ipady = 7;
        comboEtu.setFont(Constantes.FIELD_FONT);
        comboEtu.setBackground(Constantes.WHITE);
        add(comboEtu, c);

        c.gridx = 1;
        c.gridy = 1;
        comboLivres.setFont(Constantes.FIELD_FONT);
        comboLivres.setBackground(Constantes.WHITE);
        add(comboLivres, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        JLabel empInfoTxt = new JLabel("", JLabel.CENTER);
        empInfoTxt.setText("Choisissez un étudiant et un livre");
        add(empInfoTxt, c);

        c.gridy = 3;
        c.gridwidth = 2;
        empAjout.setFocusPainted(false);
        add(empAjout, c);
    }

    public Etudiant getSelectedEtu() {
        return (Etudiant) comboEtu.getSelectedItem();
    }

    public Livre getSelectedLiv() {
        return (Livre) comboLivres.getSelectedItem();
    }

    public JComboBox<Livre> getComboLivres() {
        return comboLivres;
    }

    public JComboBox<Etudiant> getComboEtu() {
        return comboEtu;
    }

    public void addListener(BibliController controller) {
        empAjout.setActionCommand("Emprunt-Ajout");
        empAjout.addActionListener(controller);
    }

}
