package control;

import modele.Emprunt;
import modele.Etudiant;
import util.DataBase;
import vue.bibli.BibliPanel;

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
                bibliPanel.updateJCombobox(DB);
                break;

            case "InfoEtu-Nouveau":
                id = bibliPanel.getJComboBoxID();
                if (id == 0) {
                    DB.createStudent(bibliPanel.getInfoEtudiant(id));
                }
                System.out.println("InfoEtu-Nouveau");
                bibliPanel.updateJCombobox(DB);
                break;

            case "InfoEtu-Supprimer":
                id = bibliPanel.getJComboBoxID();
                System.out.println(id);
                DB.deleteStudent(id);
                System.out.println("InfoEtu-Supprimer");
                bibliPanel.updateJCombobox(DB);
                break;
                //
            case "resEtudiant-search":
                bibliPanel.setBookList(DB.researchCorresponding(bibliPanel.getAuteur(), bibliPanel.getTitre()));
                System.out.println("resEtudiant-search");
                break;

            case "resEtudiant-ajout":
                DB.createBook(bibliPanel.getAuteur(), bibliPanel.getTitre());
                System.out.println("resEtudiant-ajout");
                break;

            case "resEtudiant-suppression":
                DB.supprBook(bibliPanel.getAuteur(), bibliPanel.getTitre());
                System.out.println("resEtudiant-Supr");
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
