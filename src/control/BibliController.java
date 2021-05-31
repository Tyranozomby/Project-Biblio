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

            case "bouton-Choice":
                int ID = this.bibliPanel.getJComboBoxID();
                if (ID > 0) {
                    Etudiant etudiant = this.DB.getStudent(ID);
                    this.bibliPanel.setZoneFill(etudiant);
                    System.out.println(etudiant.toString());
                } else {
                    this.bibliPanel.setZoneFill(null);
                }

                break;
            case "bouton-Sauvegarder":
                int ID2 = this.bibliPanel.getJComboBoxID();
                if (ID2 > 0) {
                    Etudiant etud = this.bibliPanel.getInfoEtudiant(ID2);
                    this.DB.setStudent(etud);
                }
                break;

            case "bouton-Nouveau":
                int ID3 = this.bibliPanel.getJComboBoxID();
                if (ID3 == 0) {
                    Etudiant etud = this.bibliPanel.getInfoEtudiant(ID3);
                    this.DB.createStudent(etud);
                }
                System.out.println("bouton-Nouveau");
                break;
            case "bouton-Supprimer":
                System.out.println("bouton-Supprimer");
                break;


        }
    }
    public KeyAdapter enterListener(Object obj){
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
