package modele;

public class Emprunt {

    private final Livre liv;
    private final String fin;
    private final int ex;

    public Emprunt(Livre book, String date_fin, int id) {
        liv = book;
        fin = date_fin;
        ex = id;
    }

    public Emprunt() {
        liv = new Livre();
        fin = "";
        ex = 0;
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
}
