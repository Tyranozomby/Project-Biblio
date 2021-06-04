package vue.etudiant;

import control.StudentController;
import modele.*;
import util.DataBase;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StudentPanel extends JPanel {

    private DataBase DB;
    private final JTabbedPane onglets;

    private final JLabel title = new JLabel();


    public StudentPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        //Panel Titre
        JPanel panelTitre = panelTitre();

        //Content Panel
        JPanel content = new JPanel();
        CardLayout layout = new CardLayout();
        content.setLayout(layout);
        content.setOpaque(false);

        //Ajout onglets
        onglets = new JTabbedPane();
        content.add(onglets, BorderLayout.CENTER);

        add(panelTitre, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

    }

    /**
     * Panel titre (logique)
     *
     * @return JPanel
     */
    private JPanel panelTitre() {
        JPanel panelTitre = new JPanel();

        panelTitre.setOpaque(false);
        panelTitre.setLayout(new GridLayout(2, 1));

        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitle = new JLabel("Étudiant");
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        subtitle.setHorizontalAlignment(JLabel.CENTER);

        panelTitre.add(title);
        panelTitre.add(subtitle);
        return panelTitre;
    }


    /**
     * Set the connected student and modify welcome message
     *
     * @param student Student that connected
     */
    public void setStudent(Etudiant student) {
        OngletNewRes newRes = new OngletNewRes(student);
        OngletMesRes mesRes = new OngletMesRes();
        OngletProfil profil = new OngletProfil(student);

        title.setText("Bonjour " + student.getPrenom() + " " + student.getNom());
        onglets.add("Nouvelle Réservation", newRes);
        onglets.add("Mes Réservations", mesRes);
        onglets.add("Profil", profil);

        onglets.setFocusable(false);
        onglets.setMnemonicAt(0, KeyEvent.VK_N);
        onglets.setMnemonicAt(1, KeyEvent.VK_M);
        onglets.setMnemonicAt(2, KeyEvent.VK_P);

        new StudentController(newRes, mesRes, profil, student, DB); // !!! Contrôleur !!!
    }

    /**
     * Set the Database
     *
     * @param DB DataBase to set
     * @see DataBase
     */
    public void setDB(DataBase DB) {
        this.DB = DB;
    }

}