package vue.etudiant;

import constantes.Constantes;
import control.StudentController;
import modele.Reservation;
import modele.TableModeleRes;

import javax.swing.*;
import java.awt.*;

public class OngletMesRes extends JPanel {

    private final TableModeleRes modeleRes = new TableModeleRes();
    private final JTable tableRes = new JTable(modeleRes);
    private final JLabel infoRes = new JLabel();
    private final JButton cancelResButton = new JButton("Annuler la réservation");

    public OngletMesRes() {

        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(8, 8, 50, 8);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label = new JLabel("Mes réservations", JLabel.CENTER);
        label.setFont(Constantes.TITLE_FONT);
        add(label, c);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        infoRes.setText("Sélectionnez la réservation à annuler");
        infoRes.setHorizontalAlignment(JLabel.RIGHT);
        add(infoRes, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;

        tableRes.setFocusable(false);
        tableRes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableRes.getColumnModel().getColumn(0).setPreferredWidth(300);  // Column size
        tableRes.getColumnModel().getColumn(1).setPreferredWidth(130);  //
        tableRes.getColumnModel().getColumn(2).setPreferredWidth(70);   //
        tableRes.setRowHeight(15);

        JScrollPane scrollPane = new JScrollPane(tableRes);
        scrollPane.getViewport().setBackground(Constantes.WHITE);
        scrollPane.setPreferredSize(new Dimension(500, 94));
        scrollPane.setBorder(Constantes.BORDER);
        add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        cancelResButton.setFocusPainted(false);
        add(cancelResButton, c);
    }

    public void setResList(Reservation[] res) {
        modeleRes.setListeRes(res);
    }

    public void setInfoMessageRes(int i) {
        if (i == Constantes.NO_SELECTION) { // No book selected
            infoRes.setText("Aucun livre sélectionné");
            infoRes.setForeground(Constantes.RED);
        } else if (i == Constantes.SUCCESS) {
            infoRes.setText("La réservation a bien été annulée");
            infoRes.setForeground(Constantes.GREEN);
        }
    }

    /**
     * Method to get which reservation has been selected
     *
     * @return Selected reservation
     */
    public Reservation getSelectedRes() {
        int row = tableRes.getSelectedRow();
        if (row == -1 || modeleRes.getValueAt(row).getLivre().getId() == 0) { // No book selected
            return null;
        }
        return modeleRes.getValueAt(row);
    }

    public void addListener(StudentController controller) {
        cancelResButton.setActionCommand("Supprimer");
        cancelResButton.addActionListener(controller);
    }

}
