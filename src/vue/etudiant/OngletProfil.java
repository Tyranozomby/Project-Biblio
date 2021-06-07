package vue.etudiant;

import constantes.Constantes;
import control.StudentController;
import modele.Emprunt;
import modele.Etudiant;
import modele.TableModeleEmp;

import javax.swing.*;
import java.awt.*;

public class OngletProfil extends JPanel {

    private final Etudiant student;

    private final JButton newMdpButton = new JButton("Changer le mot de passe");
    private final JPasswordField changeMdp = new JPasswordField();
    private final JPasswordField confirmMdp = new JPasswordField();

    private final TableModeleEmp modeleEmp = new TableModeleEmp();

    private final JLabel profilMdp = new JLabel();
    private final JLabel infoEmp = new JLabel();

    public OngletProfil(Etudiant student) {
        this.student = student;

        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        JLabel label = new JLabel("Mon Profil", JLabel.CENTER);
        label.setFont(Constantes.TITLE_FONT);
        add(label, c);

        c.gridx = 0;
        c.gridy = 1;

        JPanel newMdp = new JPanel(new GridLayout(2, 2, 10, 10));
        newMdp.setOpaque(false);
        JLabel profilPrenom = new JLabel();
        profilPrenom.setText("Prénom: " + student.getPrenom());
        JLabel profilNom = new JLabel();
        profilNom.setText("Nom: " + student.getNom());
        JLabel profilMail = new JLabel();
        profilMail.setText("Email: " + student.getEmail());
        profilMdp.setText("Mot de passe: " + student.getMdp());
        newMdp.add(profilPrenom);
        newMdp.add(profilNom);
        newMdp.add(profilMail);
        newMdp.add(profilMdp);
        add(newMdp, c);

        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 300;
        c.ipady = 7;
        c.anchor = GridBagConstraints.EAST;
        changeMdp.setFont(Constantes.FIELD_FONT);
        changeMdp.setBorder(Constantes.BORDER);
        add(changeMdp, c);

        c.gridx = 0;
        c.gridy = 3;
        confirmMdp.setFont(Constantes.FIELD_FONT);
        confirmMdp.setBorder(Constantes.BORDER);
        add(confirmMdp, c);

        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 25;
        c.ipady = 10;
        c.anchor = GridBagConstraints.WEST;
        JLabel label1 = new JLabel("Nouveau mot de passe");
        label1.setLabelFor(changeMdp);
        label1.setDisplayedMnemonic('o');
        add(label1, c);

        c.gridx = 0;
        c.gridy = 3;
        JLabel label2 = new JLabel("Confirmer le mot de passe");
        label2.setLabelFor(confirmMdp);
        label2.setDisplayedMnemonic('c');
        add(label2, c);

        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 0;
        c.ipady = 0;
        c.anchor = GridBagConstraints.CENTER;
        newMdpButton.setFocusPainted(false);
        add(newMdpButton, c);

        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.EAST;
        infoEmp.setText("Changez votre mot de passe ici");
        add(infoEmp, c);

        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        JLabel vosEmprunts = new JLabel("Vos emprunts");
        vosEmprunts.setFont(Constantes.SUBTITLE_FONT);
        add(vosEmprunts, c);

        c.gridx = 0;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;

        JTable tableEmp = new JTable(modeleEmp);
        tableEmp.setFocusable(false);
        tableEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmp.getColumnModel().getColumn(0).setPreferredWidth(400);  // Column size
        tableEmp.getColumnModel().getColumn(1).setPreferredWidth(200);  //
        tableEmp.getColumnModel().getColumn(2).setPreferredWidth(100);  //
        tableEmp.setRowHeight(15);

        JScrollPane scroll = new JScrollPane(tableEmp);
        scroll.getViewport().setBackground(Constantes.WHITE);
        scroll.setPreferredSize(new Dimension(600, 97));
        scroll.setBorder(Constantes.BORDER);
        add(scroll, c);
    }

    public void setEmpList(Emprunt[] emp) {
        modeleEmp.setListeEmp(emp);
    }

    public void setInfoMessageEmp(int i) {
        if (i == Constantes.SUCCESS) {
            infoEmp.setText("Votre mot de passe a bien été changé");
            infoEmp.setForeground(Constantes.GREEN);
        } else if (i == Constantes.MDP_DIFF) {
            infoEmp.setText("Mots de passe différents");
            infoEmp.setForeground(Constantes.RED);
        } else if (i == Constantes.MDP_INV) {
            infoEmp.setText("Mot de passe invalide");
            infoEmp.setForeground(Constantes.RED);
        }
    }

    public void resetMdpChange() {
        changeMdp.requestFocusInWindow();
        confirmMdp.setText("");
    }

    public void setNewMdp(String mdp) {
        changeMdp.setText("");
        confirmMdp.setText("");
        student.setMdp(mdp);
        profilMdp.setText("Mot de passe: " + mdp);
    }

    public String[] getNewMdp() {
        String[] tab = new String[2];
        tab[0] = String.valueOf(changeMdp.getPassword());
        tab[1] = String.valueOf(confirmMdp.getPassword());
        return tab;
    }

    public void addListener(StudentController controller) {
        newMdpButton.setActionCommand("Changer mdp");
        newMdpButton.addActionListener(controller);
    }
}
