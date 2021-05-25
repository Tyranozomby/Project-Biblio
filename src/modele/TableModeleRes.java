package modele;

import constantes.Constantes;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class TableModeleRes implements TableModel {

    private Reservation[] listeRes = new Reservation[Constantes.MAX_RES];
    private static final String[] header = {"Titre", "Auteur", "Fin réservation"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();


    @Override
    public int getRowCount() {
        return listeRes.length;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return header[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation res = listeRes[rowIndex];
        if (columnIndex == 0) {
            return res.getLivre().getTitre();
        } else if (columnIndex == 1) {
            return res.getLivre().getAuteur();
        } else if (columnIndex == 2) {
            return res.getFin();
        }

        return null;
    }

    public Reservation getValueAt(int row) {
        return listeRes[row];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void setListeRes(Reservation[] list) {
        listeRes = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}
