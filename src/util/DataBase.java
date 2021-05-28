package util;

import constantes.Constantes;
import modele.Emprunt;
import modele.Etudiant;
import modele.Livre;

import modele.Reservation;
import oracle.jdbc.driver.OracleDriver;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Class used to connect to Database and regroup all SQL request needed for the application
 */
public class DataBase {

    private final Statement stmt;

    /**
     * Try to connect to database
     *
     * @throws SQLException if connection can't be established
     */
    public DataBase() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        Connection connexion;

        try {
            //At IUT
            connexion = DriverManager.getConnection("jdbc:oracle:thin:@madere:1521:info", "erogeaux", "azerty");
        } catch (SQLException e) {
            //With PUTTY
            connexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:info", "erogeaux", "azerty");
        }

        stmt = connexion.createStatement();
    }

    /**
     * Test if given logins are valid
     *
     * @param email    to log in
     * @param password of the email
     * @return corresponding student if logins are valid else, returns null
     * @see Etudiant
     */
    public Etudiant requestLoginStudent(String email, String password) {
        try {
            ResultSet rSet = stmt.executeQuery("SELECT ID_ET, PRENOM, NOM FROM ETUDIANT WHERE EMAIL='" + email + "' and MDP='" + password + "'");
            if (rSet.next()) {
                Etudiant etu = new Etudiant(Integer.parseInt(rSet.getString(1)), rSet.getString(2), rSet.getString(3), email, password);
                etu.setNbRes(getNumberRes(etu));
                return etu;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNumberRes(Etudiant student) {
        int nb = 0;
        try {
            ResultSet rSet = stmt.executeQuery("SELECT count(*) FROM RESERVATION WHERE ID_ET = " + student.getId());
            if (rSet.next()) {
                nb = Integer.parseInt(rSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nb;
    }

    /* LIVRES */

    public ArrayList<Livre> researchCorresponding(String auteur, String titre) {
        ArrayList<Livre> liste = new ArrayList<>();
        try {
            ResultSet rSet = stmt.executeQuery("SELECT ID_LIV, AUTEUR,TITRE FROM LIVRE WHERE UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%')");
            while (rSet.next()) {
                liste.add(new Livre(Integer.parseInt(rSet.getString(1)), rSet.getString(2), rSet.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
            return null;
        }
        return liste;
    }

    public boolean canReserveBook(Etudiant student, Livre book) {
        ResultSet rSet;
        try {
            rSet = stmt.executeQuery("SELECT * FROM RESERVATION WHERE id_et = " + student.getId() + " and id_liv = " + book.getId());
            return !rSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* RESERVATIONS */

    public void addReservation(Livre selectedBook, Etudiant student) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        fmt.setCalendar(cal);
        String date_deb = fmt.format(cal.getTime());
        cal.add(GregorianCalendar.DAY_OF_MONTH, 15);
        String date_fin = fmt.format(cal.getTime());

        try {
            stmt.executeQuery("INSERT INTO RESERVATION (date_res, date_fin_res, id_liv, id_et) VALUES ('" + date_deb + "','" + date_fin + "','" + selectedBook.getId() + "','" + student.getId() + "')");
            student.setNbRes(getNumberRes(student));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reservation[] getReservations(Etudiant student) {
        Reservation[] listeRes = new Reservation[Constantes.MAX_RES];
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        int i = 0;
        try {
            ResultSet rSet = stmt.executeQuery("SELECT DATE_RES, DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE FROM RESERVATION, LIVRE WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and id_et = " + student.getId());
            while (rSet.next()) {
                String deb = fmt.format(rSet.getDate(1));
                String fin = fmt.format(rSet.getDate(2));
                Livre liv = new Livre(Integer.parseInt(rSet.getString(3)), rSet.getString(4), rSet.getString(5));

                listeRes[i] = new Reservation(liv, deb, fin);
                i++;
            }
            for (; i < Constantes.MAX_RES; i++) {
                listeRes[i] = new Reservation();
            }
            return listeRes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteReservation(Reservation res, Etudiant stu) {
        try {
            stmt.executeQuery("DELETE FROM RESERVATION WHERE ID_LIV = " + res.getLivre().getId() + " and ID_ET = " + stu.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* EMPRUNTS */

    public Emprunt[] getEmprunts(Etudiant student) {
        Emprunt[] listeEmp = new Emprunt[Constantes.MAX_BOOK];
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        int i = 0;
        try {
            ResultSet rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX FROM LIVRE, EXEMPLAIRE, EMPRUNT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = " + student.getId());
            while (rSet.next()) {
                Livre liv = new Livre(Integer.parseInt(rSet.getString(1)), rSet.getString(2), rSet.getString(3));
                String fin = fmt.format(rSet.getDate(4));
                int id = rSet.getInt(5);

                listeEmp[i] = new Emprunt(liv, fin, id);
                i++;
            }
            for (; i < Constantes.MAX_BOOK; i++) {
                listeEmp[i] = new Emprunt();
            }
            return listeEmp;
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public String newPassword(Etudiant student, String mdp) {
        try {
            stmt.executeQuery("UPDATE ETUDIANT SET MDP = '" + mdp + "' WHERE ID_ET =" + student.getId());
            return mdp;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du mot de passe");
            e.printStackTrace();
        }
        return null;
    }
}
