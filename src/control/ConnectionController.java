package control;


import util.DataBase;

import modele.Etudiant;

import vue.connect.ErrorPanel;
import vue.connect.LoginPanel;
import vue.SuperPanel;

import constantes.Constantes;

import java.awt.event.*;

/**
 * Controller of all connection-related panels
 *
 * @see LoginPanel
 * @see ErrorPanel
 */
public class ConnectionController implements ActionListener {

    private DataBase DB;
    private final SuperPanel superPanel;
    private final LoginPanel loginPanel;


    public ConnectionController(SuperPanel superPanel, LoginPanel panelLogin, ErrorPanel errorPanel) {

        this.superPanel = superPanel;
        this.loginPanel = panelLogin;

        errorPanel.addListener(this);
        this.loginPanel.addListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connexion":
                boolean valid = checkConnexion(loginPanel.getEmail(), loginPanel.getPassword());

                if (!valid) {
                    loginPanel.reset();
                    loginPanel.showErrorText();
                }
                break;
            case "Retry":
                restartConnection();
                break;
            case "Quit":
                System.exit(0);

        }
    }

    /**
     * Try to login with given infos
     *
     * @param email    login email
     * @param password login password
     * @return boolean for valid login or not
     */
    private boolean checkConnexion(String email, String password) {
        if (email.equals(Constantes.BIBLI_MAIL) && password.equals(Constantes.BIBLI_MDP)) {
            superPanel.setBibliPanel(); //Si login bibliothécaire
        } else {
            Etudiant student = DB.requestLoginStudent(email, password);
            if (student != null) {
                superPanel.setStudentPanel(student);
                return true;
            }
        }
        return false;
    }

    public KeyAdapter enterListener(Object obj) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(obj, 0, "Connexion"));
                }
            }
        };
    }

    public void setDB(DataBase db) {
        this.DB = db;
    }

    private void restartConnection() {
        superPanel.connectAndStart();
    }
}
