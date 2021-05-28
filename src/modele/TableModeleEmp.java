package modele;

import constantes.Constantes;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class TableModeleEmp implements TableModel {


    private Emprunt[] listeEmp = new Emprunt[Constantes.MAX_RES];
    private static final String[] header = {"Titre", "Auteur", "Fin emprunt"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();


    @Override
    public int getRowCount() {
        return listeEmp.length;
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
        Emprunt emp = listeEmp[rowIndex];
        if (columnIndex == 0) {
            return emp.getLivre().getTitre();
        } else if (columnIndex == 1) {
            return emp.getLivre().getAuteur();
        } else if (columnIndex == 2) {
            return emp.getFin();
        }
        return null;
    }

    public Emprunt getValueAt(int row) {
        return listeEmp[row];
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

    public void setListeEmp(Emprunt[] list) {
        listeEmp = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}