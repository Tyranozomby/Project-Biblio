package modele;

import util.DataBase;

import java.util.*;


public class TabEtudiant {

    private ArrayList<Etudiant> tabEtudiant;
    private int nbEtudiant;


    public TabEtudiant() {
        tabEtudiant = new ArrayList<>();
        nbEtudiant = 0;

    }

    public TabEtudiant(ArrayList<Etudiant> tabE) {
        tabEtudiant = tabE;
        nbEtudiant = tabEtudiant.size();

    }

    public void addEtudiant(Etudiant student) {
        tabEtudiant.add(student);
        nbEtudiant = nbEtudiant + 1;
    }

    public void setNbRes(int ID, DataBase DB) {
        Iterator<Etudiant> iterateur = tabEtudiant.iterator();
        Etudiant student;
        while (iterateur.hasNext()) {
            student = iterateur.next();
            if (student.getId() == ID) {
                student.setNbRes(DB.getNumberRes(student));
            }
        }
    }

    public String[] listIDString() {
        Iterator<Etudiant> iterateur = tabEtudiant.iterator();
        Etudiant student;
        String[] listID = new String[nbEtudiant];
        int ID;
        while (iterateur.hasNext()) {
            student = iterateur.next();
            ID = student.getId();
            for (int i = 0; i < (nbEtudiant); i++) {
                if (listID[i] == null) {
                    listID[i] = Integer.toString(ID);
                }
            }
        }
        return listID;
    }

    public ArrayList<Etudiant> getList() {
        return tabEtudiant;
    }

    public Etudiant[] getTab() {
        Etudiant[] tab = new Etudiant[tabEtudiant.size()];
        for (int i = 0; i < tabEtudiant.size(); i++) {
            tab[i] = tabEtudiant.get(i);
        }
        return tab ;
    }

    public String[] getListString() {

        String[] tab = new String[tabEtudiant.size()];
        for (int i = 0; i < tabEtudiant.size(); i++) {
            tab[i] = tabEtudiant.get(i).toString();
        }
        return tab;
    }
}
