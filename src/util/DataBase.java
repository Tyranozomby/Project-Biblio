package util;

import constantes.Constantes;
import modele.*;

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

    public Etudiant getStudent(int ID) {
        try {
            ResultSet rSet = stmt.executeQuery("SELECT * FROM ETUDIANT WHERE ID_ET=" + ID);
            if (rSet.next()) {
                return new Etudiant(Integer.parseInt(rSet.getString(1)), rSet.getString(3), rSet.getString(2), rSet.getString(5), rSet.getString(4));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setStudent(Etudiant student) {
        try {
            stmt.executeQuery("UPDATE ETUDIANT SET NOM = '" + student.getNom() + "' WHERE ID_ET =" + student.getId());
            stmt.executeQuery("UPDATE ETUDIANT SET PRENOM = '" + student.getPrenom() + "' WHERE ID_ET =" + student.getId());
            stmt.executeQuery("UPDATE ETUDIANT SET MDP = '" + student.getMdp() + "' WHERE ID_ET =" + student.getId());
            stmt.executeQuery("UPDATE ETUDIANT SET EMAIL = '" + student.getEmail() + "' WHERE ID_ET =" + student.getId());

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void createStudent(Etudiant student) {
        try {
            stmt.executeQuery("INSERT INTO ETUDIANT (NOM, PRENOM, MDP, EMAIL) VALUES ('" + student.getNom() + "','" + student.getPrenom() + "','" + student.getMdp() + "','" + student.getEmail() + "')");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try {
            stmt.executeQuery("DELETE from RESERVATION WHERE ID_ET = " + id);
            stmt.executeQuery("DELETE from EMPRUNT WHERE ID_ET = " + id);
            stmt.executeQuery("DELETE from ETUDIANT WHERE ID_ET= " + id);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la Suppression");
            e.printStackTrace();
        }

    }


    public ArrayList<Etudiant> setTabStudent() {
        ArrayList<Etudiant> listEtu = new ArrayList<>();
        try {
            ResultSet rSet = stmt.executeQuery("SELECT * FROM ETUDIANT ORDER BY NOM, PRENOM, ID_ET");
            while (rSet.next()) {
                Etudiant etu = new Etudiant(Integer.parseInt(rSet.getString(1)), rSet.getString(3), rSet.getString(2), rSet.getString(5), rSet.getString(4));
                listEtu.add(etu);
            }
            return listEtu;
        } catch (SQLException e) {
            e.printStackTrace();
            return listEtu;
        }
    }


    /* LIVRES */

    public void createBook(String title, String author) {
        try {
            stmt.executeQuery("INSERT INTO LIVRE ( AUTEUR, TITRE) VALUES ('" + author + "','" + title + "')");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void supprBook(Livre liv) {
        try {
            int id = liv.getId();
            if (id != 0) {
                stmt.executeQuery("DELETE from RESERVATION WHERE ID_LIV = " + id);
                stmt.executeQuery("DELETE from EXEMPLAIRE WHERE ID_LIV = " + id);
                stmt.executeQuery("DELETE from LIVRE WHERE ID_LIV = " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression");
            e.printStackTrace();
        }
    }

    public ArrayList<Livre> researchCorresponding(String titre, String auteur) {
        ArrayList<Livre> liste = new ArrayList<>();
        try {
            ResultSet rSet = stmt.executeQuery("SELECT ID_LIV, AUTEUR,TITRE FROM LIVRE WHERE UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%') ORDER BY TITRE, AUTEUR");
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

    public boolean canAddBook(String titre, String auteur) {
        ResultSet rSet;
        if (titre.equals("") || auteur.equals("")) {
            return false;
        }
        try {
            rSet = stmt.executeQuery("SELECT * FROM LIVRE WHERE UPPER(TITRE) = UPPER('" + titre + "') and UPPER(AUTEUR) = UPPER('" + auteur + "')");
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
        cal.add(GregorianCalendar.DAY_OF_MONTH, Constantes.LENGTH_RES);
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
            ResultSet rSet = stmt.executeQuery("SELECT DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE FROM RESERVATION, LIVRE WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and id_et = " + student.getId() + " ORDER BY DATE_FIN_RES, TITRE, AUTEUR");
            while (rSet.next()) {
                String fin = fmt.format(rSet.getDate(1));
                Livre liv = new Livre(Integer.parseInt(rSet.getString(2)), rSet.getString(3), rSet.getString(4));

                listeRes[i] = new Reservation(liv, student, fin);
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

    public ArrayList<Reservation> getReservations() {
        ArrayList<Reservation> listeRes = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        try {
            ResultSet rSet = stmt.executeQuery("SELECT DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM RESERVATION, LIVRE, ETUDIANT WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and RESERVATION.ID_ET = ETUDIANT.ID_ET ORDER BY DATE_FIN_RES, TITRE, AUTEUR, NOM, PRENOM");
            while (rSet.next()) {
                String fin = fmt.format(rSet.getDate(1));
                Livre liv = new Livre(rSet.getInt(2), rSet.getString(3), rSet.getString(4));
                Etudiant student = new Etudiant(rSet.getInt(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9));

                listeRes.add(new Reservation(liv, student, fin));
            }
            return listeRes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Reservation> getReservations(String nom, String prenom, String titre, String auteur) {
        ArrayList<Reservation> listeRes = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        try {
            ResultSet rSet = stmt.executeQuery("SELECT DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM RESERVATION, LIVRE, ETUDIANT WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and RESERVATION.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER('%" + nom + "%') and UPPER(PRENOM) like UPPER('%" + prenom + "%') and UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%') ORDER BY DATE_FIN_RES, TITRE, AUTEUR, NOM, PRENOM ");
            while (rSet.next()) {
                Livre liv = new Livre(rSet.getInt(2), rSet.getString(3), rSet.getString(4));
                String fin = fmt.format(rSet.getDate(1));
                Etudiant student = new Etudiant(rSet.getInt(5), rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9));

                listeRes.add(new Reservation(liv, student, fin));
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

    public boolean canValidReservation(Reservation res) {
        try {
            ResultSet rSet = stmt.executeQuery("SELECT RESERVATION.ID_LIV FROM RESERVATION, EXEMPLAIRE, EMPRUNT WHERE RESERVATION.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = " + res.getEtudiant().getId());

            if (rSet.next()) {
                return false;
            }
            rSet = stmt.executeQuery("SELECT count(*) FROM EMPRUNT WHERE ID_ET = " + res.getEtudiant().getId());
            return rSet.next() && rSet.getInt(1) < Constantes.MAX_EMP;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void validerRes(Reservation res, int id) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        fmt.setCalendar(cal);
        String deb = fmt.format(cal.getTime());
        cal.add(GregorianCalendar.DAY_OF_MONTH, Constantes.LENGTH_RES);
        String fin = fmt.format(cal.getTime());
        try {
            stmt.executeQuery("INSERT INTO EMPRUNT(DATE_EMP, DATE_RETOUR, ID_EX, ID_ET) values ('" + deb + "', '" + fin + "', " + id + ", " + res.getEtudiant().getId() + ")");
            stmt.executeQuery("DELETE FROM RESERVATION WHERE ID_LIV = " + res.getLivre().getId() + " and ID_ET = " + res.getEtudiant().getId());
        } catch (SQLException e) {
            System.out.println("La réservation n'a pas pu être validée");
            e.printStackTrace();
        }
    }

    /* EXEMPLAIRES */

    public int exemplaireLibrePour(Livre liv) {
        try {
            ResultSet rSet = stmt.executeQuery("SELECT MIN(ID_EX) FROM EXEMPLAIRE WHERE ID_EX not in (SELECT ID_EX FROM EMPRUNT) and id_liv = " + liv.getId());
            if (rSet.next()) {
                return rSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Livre invalide");
            e.printStackTrace();
        }
        return 0;
    }

    /* EMPRUNTS */

    public Emprunt[] getEmprunts(Etudiant student) {
        Emprunt[] listeEmp = new Emprunt[Constantes.MAX_EMP];
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        int i = 0;
        try {
            ResultSet rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX FROM LIVRE, EXEMPLAIRE, EMPRUNT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = " + student.getId() + " ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            while (rSet.next()) {
                Livre liv = new Livre(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
                String fin = fmt.format(rSet.getDate(4));
                int id = rSet.getInt(5);

                listeEmp[i] = new Emprunt(liv, fin, student, id);
                i++;
            }
            for (; i < Constantes.MAX_EMP; i++) {
                listeEmp[i] = new Emprunt();
            }
            return listeEmp;
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Emprunt> getEmprunts(boolean onlyLate) {
        ArrayList<Emprunt> listeEmp = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        ResultSet rSet;
        try {
            if (!onlyLate) {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            } else {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and EMPRUNT.DATE_RETOUR < SYSDATE ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");

            }
            while (rSet.next()) {
                Livre liv = new Livre(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
                String fin = fmt.format(rSet.getDate(4));
                int id = rSet.getInt(5);
                Etudiant student = new Etudiant(rSet.getInt(6), rSet.getString(7), rSet.getString(8), rSet.getString(9), rSet.getString(10));

                listeEmp.add(new Emprunt(liv, fin, student, id));
            }
            return listeEmp;
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Emprunt> getEmprunts(String nom, String prenom, String titre, String auteur, boolean selected) {
        ArrayList<Emprunt> listeEmp = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        ResultSet rSet;
        try {
            if (!selected) {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER('%" + nom + "%') and UPPER(PRENOM) like UPPER('%" + prenom + "%') and UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%') ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            } else {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and EMPRUNT.DATE_RETOUR < SYSDATE and UPPER(NOM) like UPPER('%" + nom + "%') and UPPER(PRENOM) like UPPER('%" + prenom + "%') and UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%') ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");

            }
            while (rSet.next()) {
                Livre liv = new Livre(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
                int id = rSet.getInt(5);
                String fin = fmt.format(rSet.getDate(4));
                Etudiant student = new Etudiant(rSet.getInt(6), rSet.getString(7), rSet.getString(8), rSet.getString(9), rSet.getString(10));

                listeEmp.add(new Emprunt(liv, fin, student, id));
            }
            return listeEmp;
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public void retourEmprunt(Emprunt emp) {
        try {
            stmt.executeQuery("DELETE EMPRUNT WHERE ID_EX = " + emp.getId());
        } catch (SQLException e) {
            System.out.println("Problème lors du retour");
            e.printStackTrace();
        }
    }

    public boolean canAddEmprunt(Etudiant etu, Livre liv) {
        try {
            int i = exemplaireLibrePour(liv);
            if (i == 0) {
                return false;
            }
            ResultSet rSet = stmt.executeQuery("SELECT * FROM EMPRUNT WHERE ID_EX in (SELECT ID_EX FROM EXEMPLAIRE WHERE ID_LIV = " + liv.getId() + ") and ID_ET = " + etu.getId());
            if (rSet.next()) {
                return false;
            }
            rSet = stmt.executeQuery("SELECT count(*) FROM EMPRUNT WHERE ID_ET = " + etu.getId());
            return rSet.next() && rSet.getInt(1) < Constantes.MAX_EMP;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addEmprunt(Etudiant etu, int id) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        fmt.setCalendar(cal);
        String deb = fmt.format(cal.getTime());
        cal.add(GregorianCalendar.DAY_OF_MONTH, Constantes.LENGTH_RES);
        String fin = fmt.format(cal.getTime());
        try {
            stmt.executeQuery("INSERT INTO EMPRUNT VALUES ('" + deb + "', '" + fin + "', " + id + ", " + etu.getId() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // OTHER
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
