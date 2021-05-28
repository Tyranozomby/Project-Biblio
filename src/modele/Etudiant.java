package modele;

/**
 * Object representing a student
 */
public class Etudiant {

    private final int id;
    private final String nom;
    private final String prenom;
    private final String email;
    private String mdp;
    private int nbRes;

    public Etudiant(int id, String firstName, String lastName, String mail, String pass) {
        this.id = id;
        this.nom = lastName;
        this.prenom = firstName;
        this.email = mail;
        this.mdp = pass;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public int getId() {
        return id;
    }

    public int getNbRes() {
        return nbRes;
    }


    public void setMdp(String newMdp) {
        mdp = newMdp;
    }

    public void setNbRes(int nb) {
        nbRes = nb;
    }
}
