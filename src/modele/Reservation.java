package modele;

public class Reservation {

    private final Livre liv;
    private final String date_deb;
    private final String date_fin;

    public Reservation(Livre livre, String deb, String fin) {
        liv = livre;
        date_deb = deb;
        date_fin = fin;
    }

    public Reservation() {
        liv = new Livre();
        date_deb = "";
        date_fin = "";
    }

    public String toString() {
        return "Livre: " + liv + " | Début: " + date_deb + " | Fin: " + date_fin;
    }

    public Livre getLivre() {
        return liv;
    }

    public String getFin() {
        return date_fin;
    }
}
