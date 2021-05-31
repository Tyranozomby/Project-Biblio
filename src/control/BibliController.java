package control;

import constantes.Constantes;
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
public class BibliController { //implements ActionListener { //

    private final BibliPanel bibliPanel;
    public final DataBase DB;


    public BibliController(BibliPanel bibliPanel, DataBase DB) {

        this.bibliPanel = bibliPanel;
        this.DB = DB;

        this.bibliPanel.addListener(this);

        bibliPanel.setBookList(DB.researchCorresponding(bibliPanel.getAuteur(), bibliPanel.getTitre()));

        //TODO remplir constructeur
    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        Livre book = BibliPanel.getSelectedBook();
        Etudiant stu = BibliPanel.getStudent();
        Reservation res = BibliPanel.getSelectedRes();
        switch (e.getActionCommand()) {
            case "Rechercher":
                BibliPanel.setBookList(DB.researchCorresponding(BibliPanel.getAuteur(), BibliPanel.getTitre()));
                BibliPanel.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                break;
            case "Réserver":
                if (book != null) {
                    if (DB.getNumberRes(stu) < Constantes.MAX_RES) {
                        if (DB.canReserveBook(stu, book)) {
                            DB.addReservation(book, BibliPanel.getStudent());
                            BibliPanel.setInfoMessageLiv(Constantes.SUCCESS);
                            updateStudentRes();
                        } else {
                            BibliPanel.setInfoMessageLiv(Constantes.ALREADY_RESERVED);
                        }
                    } else {
                        BibliPanel.setInfoMessageLiv(Constantes.MAX_RES_REACHED);
                    }
                } else {
                    BibliPanel.setInfoMessageLiv(Constantes.NO_SELECTION);
                }
                break;
            case "Supprimer":
                if (res != null) {
                    DB.deleteReservation(res, stu);
                    updateStudentRes();
                    stu.setNbRes(DB.getNumberRes(stu));
                    BibliPanel.setInfoMessageRes(Constantes.SUCCESS);
                    BibliPanel.setInfoMessageLiv(Constantes.BASIC_MESSAGE);
                } else {
                    BibliPanel.setInfoMessageRes(Constantes.NO_SELECTION);
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
    } */

    public void updateStudentRes() {
        bibliPanel.setResList(DB.getReservations(bibliPanel.getStudent()));
    }

    public void updateStudentEmp() {
        bibliPanel.setEmpList(DB.getEmprunts(bibliPanel.getStudent()));
    }
}
