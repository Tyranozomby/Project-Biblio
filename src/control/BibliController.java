package control;

import modele.Emprunt;
import modele.Etudiant;
import modele.Livre;
import modele.Reservation;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Reservation res;
        Etudiant etu;
        Emprunt emp;
        Livre book;
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
                    bibliPanel.setlabelID(Integer.toString(id));
                } else {
                    bibliPanel.setZoneFill(null);
                    bibliPanel.setlabelID("Aucun");
                }
                break;

            case "InfoEtu-Sauvegarder":
                id = bibliPanel.getJComboBoxID();
                etu = bibliPanel.getInfoEtudiant(id);
                if (etu.getNom().equals("") || etu.getPrenom().equals("") || etu.getEmail().equals("") || etu.getMdp().equals("")) {
                    break;
                }
                if (id > 0) {
                    DB.setStudent(bibliPanel.getInfoEtudiant(id));
                } else if (id == 0) {
                    DB.createStudent(etu);
                }
                bibliPanel.updateJCombobox(DB);
                break;

            case "InfoEtu-Supprimer":
                id = bibliPanel.getJComboBoxID();
                if (id != 0) {
                    DB.deleteStudent(id);
                    bibliPanel.updateJCombobox(DB);
                }
                break;

            // GESTION LIVRE
            case "Livre-Search":
                bibliPanel.setBookList(DB.researchCorresponding(bibliPanel.getLivTitre(), bibliPanel.getLivAuteur()));
                break;
            case "Livre-Ajout":
                if (!DB.canAddBook(bibliPanel.getLivTitre(), bibliPanel.getLivAuteur())) {
                    break;
                }
                DB.createBook(bibliPanel.getLivTitre(), bibliPanel.getLivAuteur());
                bibliPanel.setBookList(DB.researchCorresponding("", ""));
                break;
            case "Livre-Suppression":
                book = bibliPanel.getSelectedBook();
                if (book != null) {
                    DB.supprBook(book);
                }
                bibliPanel.setBookList(DB.researchCorresponding(bibliPanel.getLivTitre(), bibliPanel.getLivAuteur()));
                break;

            // VALIDER RES
            case "Res-Recherche":
                bibliPanel.setResAllList(DB.getReservations(bibliPanel.getResNom(), bibliPanel.getResPrenom(), bibliPanel.getResTitre(), bibliPanel.getResAuteur()));
                break;
            case "Res-Valider":
                res = bibliPanel.getSelectedRes();
                if (res != null) {
                    id = DB.exemplaireLibrePour(res.getLivre());
                    if (id != 0) {
                        DB.validerRes(res, id);
                        bibliPanel.setResAllList(DB.getReservations(bibliPanel.getResNom(), bibliPanel.getResPrenom(), bibliPanel.getResTitre(), bibliPanel.getResAuteur()));
                        bibliPanel.setEmpAllList(DB.getEmprunts(bibliPanel.getRetourNom(), bibliPanel.getRetourPrenom(), bibliPanel.getRetourTitre(), bibliPanel.getRetourAuteur()));
                    }
                }
                break;
        }
    }

    public KeyAdapter enterListener(Object obj, String command) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(obj, 0, command));
                }
            }
        };
    }
}
