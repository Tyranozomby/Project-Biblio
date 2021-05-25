package modele;

/**
 * Object representing a student
 */
public class Etudiant {

    private final int id;
    private final String nom;
    private final String prenom;
    private int nbRes;

    public Etudiant(int id, String firstName, String lastName) {
        this.id = id;
        this.nom = lastName;
        this.prenom = firstName;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    public int getNbRes() {
        return nbRes;
    }

    public void setNbRes(int nb) {
        nbRes = nb;
    }
}
