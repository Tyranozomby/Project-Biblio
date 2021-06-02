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

    public void createBook(String author, String title) {
        try {
            stmt.executeQuery("INSERT INTO LIVRE ( AUTEUR, TITRE) VALUES ('" + author + "','" + title + "')");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void supprBook(String author, String title) {
        try {
            int id = 0;
            try {
                ResultSet rSet = stmt.executeQuery("SELECT ID_LIV FROM LIVRE WHERE auteur = '" + author + "' AND titre='" + title + "'");
                while (rSet.next()) {
                    id = rSet.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la recherche");
                e.printStackTrace();
            }

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

    public ArrayList<Livre> researchCorresponding(String auteur, String titre) {
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
            ResultSet rSet = stmt.executeQuery("SELECT DATE_RES, DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE FROM RESERVATION, LIVRE WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and id_et = " + student.getId() + " ORDER BY DATE_FIN_RES, TITRE, AUTEUR");
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
            ResultSet rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX FROM LIVRE, EXEMPLAIRE, EMPRUNT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = " + student.getId() + " ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            while (rSet.next()) {
                Livre liv = new Livre(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
                String fin = fmt.format(rSet.getDate(4));
                int id = rSet.getInt(5);

                listeEmp[i] = new Emprunt(liv, fin, student, id);
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

    public ArrayList<Emprunt> getEmprunts() {
        ArrayList<Emprunt> listeEmp = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        try {
            ResultSet rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
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

    public ArrayList<Emprunt> getEmprunts(String nom, String prenom, String titre, String auteur) {
        ArrayList<Emprunt> listeEmp = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");

        try {
            ResultSet rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER('%" + nom + "%') and UPPER(PRENOM) like UPPER('%" + prenom + "%') and UPPER(AUTEUR) like UPPER('%" + auteur + "%') and UPPER(TITRE) like UPPER('%" + titre + "%') ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
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
