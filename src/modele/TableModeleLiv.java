package modele;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class TableModeleLiv implements TableModel {

    private ArrayList<Livre> listeLivres = new ArrayList<>();
    private static final String[] header = {"Titre", "Auteur"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();


    @Override
    public int getRowCount() {
        return listeLivres.size();
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
        Livre livre = listeLivres.get(rowIndex);
        if (columnIndex == 0) {
            return livre.getTitre();
        } else if (columnIndex == 1) {
            return livre.getAuteur();
        }
        return null;
    }

    public Livre getValueAt(int row) {
        return listeLivres.get(row);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //NUL !!!
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void setListeLivres(ArrayList<Livre> list) {
        listeLivres = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }

}
