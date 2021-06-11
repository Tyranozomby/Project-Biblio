package util;

import constantes.Constantes;
import modele.*;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Class used to connect to Database and regroup all SQL request needed for the application
 */
public class DataBase {

    //private final Statement stmt;
    private final Connection connect;

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
        connect = connexion;
        //stmt = connexion.createStatement();
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
            PreparedStatement stmt = connect.prepareStatement("SELECT ID_ET, PRENOM, NOM FROM ETUDIANT WHERE EMAIL=? and MDP=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rSet = stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("SELECT count(*) FROM RESERVATION WHERE ID_ET = ?");
            stmt.setInt(1, student.getId());
            ResultSet rSet = stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM ETUDIANT WHERE ID_ET = ?");
            stmt.setInt(1, ID);
            ResultSet rSet = stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("UPDATE ETUDIANT SET NOM = ? WHERE ID_ET = ?");
            stmt.setString(1, student.getNom());
            stmt.setInt(2, student.getId());
            stmt.executeQuery();

            stmt = connect.prepareStatement("UPDATE ETUDIANT SET PRENOM = ? WHERE ID_ET = ?");
            stmt.setString(1, student.getPrenom());
            stmt.setInt(2, student.getId());
            stmt.executeQuery();

            stmt = connect.prepareStatement("UPDATE ETUDIANT SET MDP = ? WHERE ID_ET = ?");
            stmt.setString(1, student.getMdp());
            stmt.setInt(2, student.getId());
            stmt.executeQuery();

            stmt = connect.prepareStatement("UPDATE ETUDIANT SET EMAIL = ? WHERE ID_ET = ?");
            stmt.setString(1, student.getEmail());
            stmt.setInt(2, student.getId());
            stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void createStudent(Etudiant student) {
        try {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO ETUDIANT (NOM, PRENOM, MDP, EMAIL) VALUES (?, ?, ?, ?)");
            stmt.setString(1, student.getNom());
            stmt.setString(2, student.getPrenom());
            stmt.setString(3, student.getMdp());
            stmt.setString(4, student.getEmail());
            stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try {
            PreparedStatement stmt = connect.prepareStatement("DELETE from RESERVATION WHERE ID_ET = ?");
            stmt.setInt(1, id);
            stmt.executeQuery();

            stmt = connect.prepareStatement("DELETE from EMPRUNT WHERE ID_ET = ?");
            stmt.setInt(1, id);
            stmt.executeQuery();

            stmt = connect.prepareStatement("DELETE from ETUDIANT WHERE ID_ET = ?");
            stmt.setInt(1, id);
            stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la Suppression");
            e.printStackTrace();
        }

    }


    public ArrayList<Etudiant> setTabStudent() {
        ArrayList<Etudiant> listEtu = new ArrayList<>();
        try {
            Statement stmt = connect.createStatement();
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
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO LIVRE ( AUTEUR, TITRE) VALUES (?, ?)");
            stmt.setString(1, author);
            stmt.setString(2, title);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification");
            e.printStackTrace();
        }
    }

    public void supprBook(Livre liv) {
        try {
            int id = liv.getId();
            if (id != 0) {
                PreparedStatement stmt = connect.prepareStatement("DELETE from RESERVATION WHERE ID_LIV = ?");
                stmt.setInt(1, id);
                stmt.executeQuery();

                stmt = connect.prepareStatement("DELETE from EXEMPLAIRE WHERE ID_LIV = ?");
                stmt.setInt(1, id);
                stmt.executeQuery();

                stmt = connect.prepareStatement("DELETE from LIVRE WHERE ID_LIV = ?");
                stmt.setInt(1, id);
                stmt.executeQuery();

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression");
            e.printStackTrace();
        }
    }

    public ArrayList<Livre> researchCorresponding(String titre, String auteur) {
        ArrayList<Livre> liste = new ArrayList<>();
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT ID_LIV, AUTEUR,TITRE FROM LIVRE WHERE UPPER(AUTEUR) like UPPER(?) and UPPER(TITRE) like UPPER(?) ORDER BY TITRE, AUTEUR");
            stmt.setString(1, '%' + auteur + '%');
            stmt.setString(2, '%' + titre + '%');
            ResultSet rSet = stmt.executeQuery();

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
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM RESERVATION WHERE id_et = ? and id_liv = ?");
            stmt.setInt(1, student.getId());
            stmt.setInt(2, book.getId());
            ResultSet rSet = stmt.executeQuery();
            return !rSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean canAddBook(String titre, String auteur) {
        if (titre.equals("") || auteur.equals("")) {
            return false;
        }
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM LIVRE WHERE UPPER(TITRE) = UPPER(?) and UPPER(AUTEUR) = UPPER(?)");
            stmt.setString(1, titre);
            stmt.setString(2, auteur);
            ResultSet rSet = stmt.executeQuery();
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
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO RESERVATION (date_res, date_fin_res, id_liv, id_et) VALUES (?, ?, ?, ?)");
            stmt.setString(1, date_deb);
            stmt.setString(2, date_fin);
            stmt.setInt(3, selectedBook.getId());
            stmt.setInt(4, student.getId());
            stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("SELECT DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE FROM RESERVATION, LIVRE WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and id_et = ? ORDER BY DATE_FIN_RES, TITRE, AUTEUR");
            stmt.setInt(1, student.getId());
            ResultSet rSet = stmt.executeQuery();
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
            Statement stmt = connect.createStatement();
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
            PreparedStatement stmt = connect.prepareStatement("SELECT DATE_FIN_RES, LIVRE.ID_LIV, AUTEUR, TITRE, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM RESERVATION, LIVRE, ETUDIANT WHERE RESERVATION.ID_LIV = LIVRE.ID_LIV and RESERVATION.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER(?) and UPPER(PRENOM) like UPPER(?) and UPPER(AUTEUR) like UPPER(?) and UPPER(TITRE) like UPPER(?) ORDER BY DATE_FIN_RES, TITRE, AUTEUR, NOM, PRENOM ");
            stmt.setString(1, '%' + nom + '%');
            stmt.setString(2, '%' + prenom + '%');
            stmt.setString(3, '%' + auteur + '%');
            stmt.setString(4, '%' + titre + '%');
            ResultSet rSet = stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("DELETE FROM RESERVATION WHERE ID_LIV = ? and ID_ET = ?");
            stmt.setInt(1, res.getLivre().getId());
            stmt.setInt(2, stu.getId());
            stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean canValidReservation(Reservation res) {
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT RESERVATION.ID_LIV FROM RESERVATION, EXEMPLAIRE, EMPRUNT WHERE RESERVATION.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ?");
            stmt.setInt(1, res.getEtudiant().getId());
            ResultSet rSet = stmt.executeQuery();

            if (rSet.next()) {
                return false;
            }
            stmt = connect.prepareStatement("SELECT count(*) FROM EMPRUNT WHERE ID_ET = ?");
            stmt.setInt(1, res.getEtudiant().getId());
            rSet = stmt.executeQuery();

            return rSet.next() && rSet.getInt(1) < Constantes.MAX_EMP;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void validerRes(Reservation res, int id) {
        String[] dates = getDates();
        String deb = dates[0];
        String fin = dates[1];
        try {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO EMPRUNT(DATE_EMP, DATE_RETOUR, ID_EX, ID_ET) values (?, ?, ?, ?)");
            stmt.setString(1, deb);
            stmt.setString(2, fin);
            stmt.setInt(3, id);
            stmt.setInt(4, res.getEtudiant().getId());
            stmt.executeQuery();

            stmt = connect.prepareStatement("DELETE FROM RESERVATION WHERE ID_LIV = ? and ID_ET = ?");
            stmt.setInt(1, res.getLivre().getId());
            stmt.setInt(2, res.getEtudiant().getId());
            stmt.executeQuery();

        } catch (SQLException e) {
            System.out.println("La réservation n'a pas pu être validée");
            e.printStackTrace();
        }
    }

    /* EXEMPLAIRES */

    public int exemplaireLibrePour(Livre liv) {
        try {
            PreparedStatement stmt = connect.prepareStatement("SELECT MIN(ID_EX) FROM EXEMPLAIRE WHERE ID_EX not in (SELECT ID_EX FROM EMPRUNT) and id_liv = ?");
            stmt.setInt(1, liv.getId());
            ResultSet rSet = stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX FROM LIVRE, EXEMPLAIRE, EMPRUNT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ? ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            stmt.setInt(1, student.getId());
            ResultSet rSet = stmt.executeQuery();

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
        ResultSet rSet;
        try {
            Statement stmt = connect.createStatement();
            if (!onlyLate) {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            } else {
                rSet = stmt.executeQuery("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and EMPRUNT.DATE_RETOUR < SYSDATE ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            }

            return createListEmp(rSet);
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Emprunt> getEmprunts(String nom, String prenom, String titre, String auteur, boolean selected) {
        ResultSet rSet;
        PreparedStatement stmt;
        try {
            if (!selected) {
                stmt = connect.prepareStatement("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and UPPER(NOM) like UPPER(?) and UPPER(PRENOM) like UPPER(?) and UPPER(AUTEUR) like UPPER(?) and UPPER(TITRE) like UPPER(?) ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            } else {
                stmt = connect.prepareStatement("SELECT LIVRE.ID_LIV, AUTEUR, TITRE, DATE_RETOUR, EMPRUNT.ID_EX, ETUDIANT.ID_ET, PRENOM, NOM, EMAIL, MDP FROM LIVRE, EXEMPLAIRE, EMPRUNT, ETUDIANT WHERE LIVRE.ID_LIV = EXEMPLAIRE.ID_LIV and EXEMPLAIRE.ID_EX = EMPRUNT.ID_EX and EMPRUNT.ID_ET = ETUDIANT.ID_ET and EMPRUNT.DATE_RETOUR < SYSDATE and UPPER(NOM) like UPPER(?) and UPPER(PRENOM) like UPPER(?) and UPPER(AUTEUR) like UPPER(?) and UPPER(TITRE) like UPPER(?) ORDER BY DATE_RETOUR, TITRE, AUTEUR, ID_EX");
            }
            stmt.setString(1, '%' + nom + '%');
            stmt.setString(2, '%' + prenom + '%');
            stmt.setString(3, '%' + auteur + '%');
            stmt.setString(4, '%' + titre + '%');
            rSet = stmt.executeQuery();

            return createListEmp(rSet);
        } catch (SQLException e) {
            System.out.println("Problème recherche");
            e.printStackTrace();
        }
        return null;
    }

    public void retourEmprunt(Emprunt emp) {
        try {
            PreparedStatement stmt = connect.prepareStatement("DELETE EMPRUNT WHERE ID_EX = ?");
            stmt.setInt(1, emp.getId());
            stmt.executeQuery();

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
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM EMPRUNT WHERE ID_EX in (SELECT ID_EX FROM EXEMPLAIRE WHERE ID_LIV = ?) and ID_ET = ?");
            stmt.setInt(1, liv.getId());
            stmt.setInt(2, etu.getId());
            ResultSet rSet = stmt.executeQuery();

            if (rSet.next()) {
                return false;
            }
            stmt = connect.prepareStatement("SELECT count(*) FROM EMPRUNT WHERE ID_ET = ?");
            stmt.setInt(1, etu.getId());
            rSet = stmt.executeQuery();
            return rSet.next() && rSet.getInt(1) < Constantes.MAX_EMP;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addEmprunt(Etudiant etu, int id) {
        String[] dates = getDates();
        String deb = dates[0];
        String fin = dates[1];
        try {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO EMPRUNT VALUES (?, ?, ?, ?)");
            stmt.setString(1, deb);
            stmt.setString(2, fin);
            stmt.setInt(3, id);
            stmt.setInt(4, etu.getId());
            stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // OTHER
    public String newPassword(Etudiant student, String mdp) {
        try {
            PreparedStatement stmt = connect.prepareStatement("UPDATE ETUDIANT SET MDP = '" + mdp + "' WHERE ID_ET = ?");
            stmt.setInt(1, student.getId());
            stmt.executeQuery();
            return mdp;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du mot de passe");
            e.printStackTrace();
        }
        return null;
    }

    private String[] getDates() {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        fmt.setCalendar(cal);
        String deb = fmt.format(cal.getTime());
        cal.add(GregorianCalendar.DAY_OF_MONTH, Constantes.LENGTH_RES);
        String fin = fmt.format(cal.getTime());
        return new String[]{deb, fin};
    }

    private ArrayList<Emprunt> createListEmp(ResultSet rSet) throws SQLException {
        ArrayList<Emprunt> listeEmp = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        while (rSet.next()) {
            Livre liv = new Livre(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
            String fin = fmt.format(rSet.getDate(4));
            int id = rSet.getInt(5);
            Etudiant student = new Etudiant(rSet.getInt(6), rSet.getString(7), rSet.getString(8), rSet.getString(9), rSet.getString(10));

            listeEmp.add(new Emprunt(liv, fin, student, id));
        }
        return listeEmp;
    }
}
