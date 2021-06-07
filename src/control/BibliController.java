package control;

import constantes.Constantes;
import modele.Emprunt;
import modele.Etudiant;
import modele.Livre;
import modele.Reservation;
import util.DataBase;
import vue.bibli.*;

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

    private final OngletGestionEtudiants gestionEtudiants;
    private final OngletGestionLivres gestionLivres;
    private final OngletValiderRes validerRes;
    private final OngletEmpruntDirect empruntDirect;
    private final OngletRetourEmprunt retourEmprunt;
    private final DataBase DB;


    public BibliController(OngletGestionEtudiants gestionEtudiants, OngletGestionLivres gestionLivres, OngletValiderRes validerRes, OngletEmpruntDirect empruntDirect, OngletRetourEmprunt retourEmprunt, DataBase DB) {

        this.gestionEtudiants = gestionEtudiants;
        this.gestionLivres = gestionLivres;
        this.validerRes = validerRes;
        this.empruntDirect = empruntDirect;
        this.retourEmprunt = retourEmprunt;
        this.DB = DB;

        this.gestionEtudiants.addListener(this);
        this.gestionLivres.addListener(this);
        this.validerRes.addListener(this);
        this.empruntDirect.addListener(this);
        this.retourEmprunt.addListener(this);

        updateAll(DB);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Reservation res;
        Etudiant etu;
        Emprunt emp;
        Livre liv;
        int id;
        switch (e.getActionCommand()) {

            // GESTION ÉTUDIANTS
            case "InfoEtu-Choisir":
                id = gestionEtudiants.getStudentID();

                if (id > 0) {
                    etu = DB.getStudent(id);
                    gestionEtudiants.fillInfoEtudiant(etu);
                    gestionEtudiants.setLabelID(Integer.toString(id));
                    gestionEtudiants.toggleSuppr(true);
                } else {
                    gestionEtudiants.fillInfoEtudiant(null);
                    gestionEtudiants.setLabelID("Aucun");
                    gestionEtudiants.toggleSuppr(false);
                }
                break;
            case "InfoEtu-Sauvegarder":
                id = gestionEtudiants.getStudentID();
                etu = gestionEtudiants.getInfoEtudiant(id);
                if (etu.getNom().equals("") || etu.getPrenom().equals("") || etu.getEmail().equals("") || etu.getMdp().equals("")) {
                    gestionEtudiants.setInfoMessage(Constantes.ERROR);
                    break;
                }
                if (id > 0) {
                    DB.setStudent(gestionEtudiants.getInfoEtudiant(id));
                } else if (id == 0) {
                    DB.createStudent(etu);
                }
                gestionEtudiants.setInfoMessage(Constantes.SUCCESS);
                updateAll(DB);
                break;
            case "InfoEtu-Supprimer":
                DB.deleteStudent(gestionEtudiants.getStudentID());
                gestionEtudiants.setInfoMessage(Constantes.SUCCESS);
                updateAll(DB);
                break;

            // GESTION LIVRES
            case "Livre-Search":
                gestionLivres.setList(DB.researchCorresponding(gestionLivres.getTitre(), gestionLivres.getAuteur()));
                break;
            case "Livre-Ajout":
                if (!DB.canAddBook(gestionLivres.getTitre(), gestionLivres.getAuteur())) {
                    gestionLivres.setInfoMessage(Constantes.ERROR);
                    break;
                }
                DB.createBook(gestionLivres.getTitre(), gestionLivres.getAuteur());
                gestionLivres.setList(DB.researchCorresponding(gestionLivres.getTitre(), gestionLivres.getAuteur()));
                gestionLivres.setInfoMessage(Constantes.SUCCESS);
                break;
            case "Livre-Suppression":
                liv = gestionLivres.getSelectedBook();
                if (liv == null) {
                    gestionLivres.setInfoMessage(Constantes.NO_SELECTION);
                    break;
                }
                DB.supprBook(liv);
                gestionLivres.setList(DB.researchCorresponding(gestionLivres.getTitre(), gestionLivres.getAuteur()));
                gestionLivres.setInfoMessage(Constantes.SUCCESS);
                break;

            // VALIDER RÉSERVATION
            case "Res-Recherche":
                validerRes.setList(DB.getReservations(validerRes.getNom(), validerRes.getPrenom(), validerRes.getTitre(), validerRes.getAuteur()));
                break;
            case "Res-Valider":
                res = validerRes.getSelectedRes();
                if (res != null) {
                    if (DB.canValidReservation(res)) {
                        id = DB.exemplaireLibrePour(res.getLivre());
                        if (id != 0) {
                            DB.validerRes(res, id);
                            validerRes.setInfoMessage(Constantes.SUCCESS);
                            updateAll(DB);
                        } else {
                            validerRes.setInfoMessage(Constantes.ERROR);
                        }
                    } else {
                        validerRes.setInfoMessage(Constantes.ERROR);
                    }
                } else {
                    validerRes.setInfoMessage(Constantes.NO_SELECTION);
                }
                break;

            // EMPRUNT DIRECT
            case "Emprunt-Ajout":
                etu = empruntDirect.getSelectedEtu();
                liv = empruntDirect.getSelectedLiv();
                if (DB.canAddEmprunt(etu, liv)) {
                    id = DB.exemplaireLibrePour(liv);
                    if (id != 0) {
                        DB.addEmprunt(etu, id);
                        empruntDirect.setInfoMessage(Constantes.SUCCESS);
                        updateAll(DB);
                    } else {
                        empruntDirect.setInfoMessage(Constantes.ERROR);
                    }
                } else {
                    empruntDirect.setInfoMessage(Constantes.ERROR);
                }
                break;

            // RETOUR EMPRUNT
            case "Retour-Recherche":
                retourEmprunt.setList(DB.getEmprunts(retourEmprunt.getNom(), retourEmprunt.getPrenom(), retourEmprunt.getTitre(), retourEmprunt.getAuteur(), retourEmprunt.isCheckboxSelected()));
                break;
            case "Retour-Rendu":
                emp = retourEmprunt.getRetourSelectedEmp();
                if (emp != null) {
                    DB.retourEmprunt(emp);
                    retourEmprunt.setList(DB.getEmprunts(retourEmprunt.getNom(), retourEmprunt.getPrenom(), retourEmprunt.getTitre(), retourEmprunt.getAuteur(), retourEmprunt.isCheckboxSelected()));
                    retourEmprunt.setInfoMessage(Constantes.SUCCESS);
                } else {
                    retourEmprunt.setInfoMessage(Constantes.NO_SELECTION);
                }
                break;
            case "Retour-Relance":
                emp = retourEmprunt.getRetourSelectedEmp();
                if (emp != null) {
                    retourEmprunt.sendMailRelance(emp.getEtudiant());
                } else {
                    retourEmprunt.setInfoMessage(Constantes.NO_SELECTION);
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

    public void updateAll(DataBase DB) {
        gestionEtudiants.getComboBox().removeAllItems();
        empruntDirect.getComboEtu().removeAllItems();
        empruntDirect.getComboLivres().removeAllItems();

        gestionEtudiants.getComboBox().addItem(new Etudiant(0, "Étudiant", "Créer", "", ""));

        for (Etudiant etu : DB.setTabStudent()) {
            gestionEtudiants.getComboBox().addItem(etu);
            empruntDirect.getComboEtu().addItem(etu);
        }
        for (Livre liv : DB.researchCorresponding("", "")) {
            empruntDirect.getComboLivres().addItem(liv);
        }
        retourEmprunt.getModel().setListeEmp(DB.getEmprunts(retourEmprunt.isCheckboxSelected()));
        validerRes.getModel().setListeRes(DB.getReservations());
        gestionLivres.getModel().setListeLivres(DB.researchCorresponding("", ""));
        gestionEtudiants.getComboBox().setSelectedIndex(0);
    }
}
