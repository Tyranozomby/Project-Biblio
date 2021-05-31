package control;

import modele.Emprunt;
import modele.Etudiant;
import util.DataBase;
import vue.bibli.BibliPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller for BibliPanel
 *
 * @see BibliPanel
 */
public class BibliController implements ActionListener {

    private final BibliPanel bibliPanel;
    public final DataBase DB;


    public BibliController(BibliPanel bibliPanel, DataBase DB) {

        this.bibliPanel = bibliPanel;
        this.DB = DB;

        this.bibliPanel.addListener(this);

        bibliPanel.setBookList(DB.researchCorresponding(bibliPanel.getAuteur(), bibliPanel.getTitre()));

        //TODO remplir constructeur
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Etudiant etu;
        Emprunt emp;
        int id;
        switch (e.getActionCommand()) {
            // RETOUR
            case "Retour-Recherche":
                bibliPanel.setEmpAllList(DB.getEmprunts(bibliPanel.getRetourNom(), bibliPanel.getRetourPrenom(), bibliPanel.getRetourTitre(), bibliPanel.getRetourAuteur()));
                break;
            case "Retour-Rendu":
                emp = bibliPanel.getRetourSelectedEmp();
                if (emp != null) {
                    DB.retourEmprunt(emp);
                    bibliPanel.setEmpAllList(DB.getEmprunts(bibliPanel.getRetourNom(), bibliPanel.getRetourPrenom(), bibliPanel.getRetourTitre(), bibliPanel.getRetourAuteur()));
                }
                break;
            case "Retour-Relance":
                emp = bibliPanel.getRetourSelectedEmp();
                if (emp != null) {
                    etu = emp.getEtudiant();
                    if (etu.getId() != 0) {
                        bibliPanel.sendMailRelance(etu);
                    }
                }
                break;

            // INFO ÉTUDIANTS
            case "InfoEtu-Choisir":
                id = bibliPanel.getJComboBoxID();
                if (id > 0) {
                    etu = DB.getStudent(id);
                    bibliPanel.setZoneFill(etu);
                } else {
                    bibliPanel.setZoneFill(null);
                }

                break;
            case "InfoEtu-Sauvegarder":
                id = bibliPanel.getJComboBoxID();
                if (id > 0) {
                    DB.setStudent(bibliPanel.getInfoEtudiant(id));
                }
                break;

            case "InfoEtu-Nouveau":
                id = bibliPanel.getJComboBoxID();
                if (id == 0) {
                    DB.createStudent(bibliPanel.getInfoEtudiant(id));
                }
                System.out.println("InfoEtu-Nouveau");
                break;
            case "InfoEtu-Supprimer":
                System.out.println("InfoEtu-Supprimer");
                break;
        }
    }

    public KeyAdapter enterListener(Object obj) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(obj, 0, "Retour-Recherche"));
                }
            }
        };
    }
}
