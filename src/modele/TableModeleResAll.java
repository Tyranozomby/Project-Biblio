package modele;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableModeleResAll implements TableModel {

    private ArrayList<Reservation> listeRes = new ArrayList<>();
    private static final String[] header = {"Titre", "Auteur", "Étudiant", "Fin emprunt"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();

    @Override
    public int getRowCount() {
        return listeRes.size();
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
        Reservation res = listeRes.get(rowIndex);
        if (columnIndex == 0) {
            return res.getLivre().getTitre();
        } else if (columnIndex == 1) {
            return res.getLivre().getAuteur();
        } else if (columnIndex == 2) {
            return res.getEtudiant().getNom() + " " + res.getEtudiant().getPrenom();
        } else if (columnIndex == 3) {
            try {
                if (new Date().after(new SimpleDateFormat("dd MMM yyyy").parse(res.getFin()))) {
                    return res.getFin() + " /!\\";
                } else {
                    return res.getFin();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Reservation getValueAt(int row) {
        return listeRes.get(row);
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

    public void setListeRes(ArrayList<Reservation> list) {
        listeRes = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}
