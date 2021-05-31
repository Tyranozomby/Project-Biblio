package modele;

public class Emprunt {

    private final Livre liv;
    private final String fin;
    private final int ex;
    private final Etudiant etu;

    public Emprunt(Livre book, String date_fin, Etudiant etudiant, int id) {
        liv = book;
        fin = date_fin;
        ex = id;
        etu = etudiant;
    }

    public Emprunt() {
        liv = new Livre();
        fin = "";
        ex = 0;
        etu = null;
    }

    public Livre getLivre() {
        return liv;
    }

    public String getFin() {
        return fin;
    }

    public int getId() {
        return ex;
    }

    public Etudiant getEtudiant() {
        return etu;
    }

    @Override
    public String toString() {
        assert etu != null;
        return liv.toString() + " " + fin + " " + etu;
    }
}
