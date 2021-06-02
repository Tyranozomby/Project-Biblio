package modele;

public class Reservation {

    private final Livre liv;
    private final String date_fin;
    private final Etudiant student;

    public Reservation(Livre livre, Etudiant stu, String fin) {
        liv = livre;
        student = stu;
        date_fin = fin;
    }

    public Reservation() {
        liv = new Livre();
        student = null;
        date_fin = "";
    }

    public String toString() {
        return "Livre: " + liv + " | Étudiant: " + student + " | Fin: " + date_fin;
    }

    public Livre getLivre() {
        return liv;
    }

    public String getFin() {
        return date_fin;
    }

    public Etudiant getEtudiant() {
        return student;
    }
}
