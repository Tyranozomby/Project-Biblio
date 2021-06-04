package control;

import constantes.Constantes;
import modele.Etudiant;
import modele.Livre;
import modele.Reservation;
import util.DataBase;
import vue.etudiant.OngletMesRes;
import vue.etudiant.OngletNewRes;
import vue.etudiant.OngletProfil;
import vue.etudiant.StudentPanel;

import java.awt.event.*;

/**
 * Controller for StudentPanel
 *
 * @see StudentPanel
 */
public class StudentController implements ActionListener {

    private final OngletNewRes newRes;
    private final OngletMesRes mesRes;
    private final OngletProfil profil;
    private final Etudiant student;
    private final DataBase DB;

    public StudentController(OngletNewRes newRes, OngletMesRes mesRes, OngletProfil profil, Etudiant student, DataBase DB) {
        this.newRes = newRes;
        this.mesRes = mesRes;
        this.profil = profil;
        this.student = student;
        this.DB = DB;

        newRes.setBookList(DB.researchCorresponding(newRes.getTitre(), newRes.getAuteur()));
        updateStudentRes();
        updateStudentEmp();

        this.newRes.addListener(this);
        this.mesRes.addListener(this);
        this.profil.addListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Livre liv = newRes.getSelectedBook();
        Reservation res = mesRes.getSelectedRes();
        switch (e.getActionCommand()) {
            case "Rechercher":
                newRes.setBookList(DB.researchCorresponding(newRes.getTitre(), newRes.getAuteur()));
                newRes.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                break;
            case "Réserver":
                if (liv != null) {
                    if (DB.getNumberRes(student) < Constantes.MAX_RES) {
                        if (DB.canReserveBook(student, liv)) {
                            DB.addReservation(liv, student);
                            newRes.setInfoMessageLiv(Constantes.SUCCESS);
                            updateStudentRes();
                        } else {
                            newRes.setInfoMessageLiv(Constantes.ALREADY_RESERVED);
                        }
                    } else {
                        newRes.setInfoMessageLiv(Constantes.MAX_RES_REACHED);
                    }
                } else {
                    newRes.setInfoMessageLiv(Constantes.NO_SELECTION);
                }
                break;
            case "Supprimer":
                if (res != null) {
                    DB.deleteReservation(res, student);
                    updateStudentRes();
                    student.setNbRes(DB.getNumberRes(student));
                    mesRes.setInfoMessageRes(Constantes.SUCCESS);
                    newRes.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                } else {
                    mesRes.setInfoMessageRes(Constantes.NO_SELECTION);
                }
                break;
            case "Changer mdp":
                String[] newMdp = profil.getNewMdp();
                String mdp1 = newMdp[0];
                String mdp2 = newMdp[1];
                if (mdp1.equals(mdp2) && !mdp1.equals("")) {
                    String mdp = DB.newPassword(student, mdp1);
                    if (mdp != null) {
                        profil.setNewMdp(mdp);
                        profil.setInfoMessageEmp(Constantes.SUCCESS);
                    } else {
                        profil.setInfoMessageEmp(Constantes.MDP_INV);
                        profil.resetMdpChange();
                    }
                } else {
                    profil.setInfoMessageEmp(Constantes.MDP_DIFF);
                    profil.resetMdpChange();
                }
                break;
        }
    }

    public KeyAdapter enterListener(Object obj) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(obj, 0, "Rechercher"));
                }
            }
        };
    }

    public void updateStudentRes() {
        mesRes.setResList(DB.getReservations(student));
    }

    public void updateStudentEmp() {
        profil.setEmpList(DB.getEmprunts(student));
    }
}
