package control;

import constantes.Constantes;
import modele.Etudiant;
import modele.Livre;
import modele.Reservation;
import util.DataBase;
import vue.etudiant.StudentPanel;

import java.awt.event.*;

/**
 * Controller for StudentPanel
 *
 * @see StudentPanel
 */
public class StudentController implements ActionListener {

    private final StudentPanel panelPrincipal;
    private final DataBase DB;


    public StudentController(StudentPanel studentPanel, DataBase DB) {

        this.panelPrincipal = studentPanel;
        this.DB = DB;

        panelPrincipal.setNewBookList(DB.researchCorresponding(panelPrincipal.getAuteur(), panelPrincipal.getTitre()));

        this.panelPrincipal.addListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Livre book = panelPrincipal.getSelectedBook();
        Etudiant stu = panelPrincipal.getStudent();
        Reservation res = panelPrincipal.getSelectedRes();
        switch (e.getActionCommand()) {
            case "Rechercher":
                panelPrincipal.setNewBookList(DB.researchCorresponding(panelPrincipal.getAuteur(), panelPrincipal.getTitre()));
                panelPrincipal.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                break;
            case "Réserver":
                if (book != null) {
                    if (DB.getNumberRes(stu) < Constantes.MAX_RES) {
                        if (DB.canReserveBook(stu, book)) {
                            DB.addReservation(book, panelPrincipal.getStudent());
                            panelPrincipal.setInfoMessageLiv(Constantes.SUCCESS);
                            updateStudentRes();
                        } else {
                            panelPrincipal.setInfoMessageLiv(Constantes.ALREADY_RESERVED);
                        }
                    } else {
                        panelPrincipal.setInfoMessageLiv(Constantes.MAX_RES_REACHED);
                    }
                } else {
                    panelPrincipal.setInfoMessageLiv(Constantes.NO_SELECTION);
                }
                break;
            case "Supprimer":
                if (res != null) {
                    DB.deleteReservation(res, stu);
                    updateStudentRes();
                    stu.setNbRes(DB.getNumberRes(stu));
                    panelPrincipal.setInfoMessageRes(Constantes.SUCCESS);
                    panelPrincipal.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                } else {
                    panelPrincipal.setInfoMessageRes(Constantes.NO_SELECTION);
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
        panelPrincipal.setNewResList(DB.getReservations(panelPrincipal.getStudent()));
    }
}
