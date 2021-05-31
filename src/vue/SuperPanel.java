package vue;

import control.BibliController;
import control.StudentController;
import util.DataBase;
import modele.Etudiant;
import control.ConnectionController;
import vue.bibli.BibliPanel;
import vue.connect.ErrorPanel;
import vue.connect.LoadingPanel;
import vue.connect.LoginPanel;
import vue.etudiant.StudentPanel;

import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Cursor;

import java.sql.SQLException;

/**
 * Main panel
 */
public class SuperPanel extends JPanel {

    private final LoginPanel loginPanel = new LoginPanel();
    private final StudentPanel studentPanel = new StudentPanel();
    private final BibliPanel bibliPanel = new BibliPanel();

    private final ConnectionController coControl;

    private final CardLayout layout = new CardLayout();

    public SuperPanel() {
        setLayout(layout);
        setOpaque(false);

        LoadingPanel panelLoad = new LoadingPanel();
        ErrorPanel errorPanel = new ErrorPanel();

        this.add(panelLoad, "panelLoad");
        this.add(loginPanel, "panelCo");
        this.add(studentPanel, "panelEtu");
        this.add(bibliPanel, "panelBibli");
        this.add(errorPanel, "panelError");

        coControl = new ConnectionController(this, loginPanel, errorPanel);

    }

    /**
     * Set visible panel to StudentPanel and add its controller
     *
     * @param student is the student of the connection
     * @see StudentPanel
     * @see StudentController
     */
    public void setStudentPanel(Etudiant student) {
        studentPanel.setStudent(student);
        layout.show(this, "panelEtu");
        studentPanel.setFocus();
    }

    /**
     * Set visible panel to panelBibli and add its controller
     *
     * @see BibliPanel
     * @see BibliController
     */
    public void setBibliPanel() {
        layout.show(this, "panelBibli");
    }

    /**
     * Show loading panel while trying to connect to database
     * Show LoginPanel if connection successful. Else show ErrorPanel
     */
    public void connectAndStart() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        layout.show(this, "panelLoad");
        Thread connectionThread = new Thread(() -> {
            DataBase DB;
            try {
                DB = new DataBase();
            } catch (SQLException e) {
                e.printStackTrace();
                DB = null;
            }
            finishConnection(DB);
        });
        connectionThread.start();
    }

    /**
     * Second part of connectAndStart
     *
     * @param DB DataBase gotten from connection attempt
     * @see #connectAndStart()
     */
    private void finishConnection(DataBase DB) {
        if (DB != null) {
            coControl.setDB(DB);
            studentPanel.setDB(DB);
            bibliPanel.setDB(DB);
            layout.show(this, "panelCo");
        } else {
            layout.show(this, "panelError");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        setFocus(); //Set focus to email JTextField
    }

    /**
     * Set focus to email JTextField in LoginPanel
     */
    public void setFocus() {
        loginPanel.setFocus();
    }

    public Insets getInsets() {
        return new Insets(10, 25, 20, 25);
    }

}
