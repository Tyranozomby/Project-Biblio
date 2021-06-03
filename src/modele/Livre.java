package modele;

public class Livre {

    private final int id;
    private final String titre;
    private final String auteur;

    public Livre(int id, String auteur, String titre) {
        this.id = id;
        this.auteur = auteur;
        this.titre = titre;
    }

    public Livre() {
        this.id = 0;
        this.auteur = "";
        this.titre = "";
    }

    public String toString() {
        return titre + " → " + auteur;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }
}
