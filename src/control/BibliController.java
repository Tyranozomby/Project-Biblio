package control;

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
        switch (e.getActionCommand()) {
            case "Retour-Recherche":
                bibliPanel.setEmpAllList(DB.getEmprunts(bibliPanel.getRetourNom(), bibliPanel.getRetourPrenom(), bibliPanel.getRetourTitre(), bibliPanel.getRetourAuteur()));
                break;
            case "Retour-Rendu":
                DB.retourEmprunt(bibliPanel.getRetourSelectedEmp());
                bibliPanel.setEmpAllList(DB.getEmprunts(bibliPanel.getRetourNom(), bibliPanel.getRetourPrenom(), bibliPanel.getRetourTitre(), bibliPanel.getRetourAuteur()));
                break;
            case "Retour-Relance":
                Etudiant etu = bibliPanel.getRetourSelectedEmp().getEtudiant();
                if (etu.getId() != 0) {
                    bibliPanel.sendMailRelance(etu);
                }
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
