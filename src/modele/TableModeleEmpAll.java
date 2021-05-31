package modele;

import constantes.Constantes;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableModeleEmpAll implements TableModel {

    private ArrayList<Emprunt> listeEmp = new ArrayList<>();
    private static final String[] header = {"Titre", "Auteur", "Étudiant", "Fin emprunt"};

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();


    @Override
    public int getRowCount() {
        return listeEmp.size();
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
        Emprunt emp = listeEmp.get(rowIndex);
        if (columnIndex == 0) {
            return emp.getLivre().getTitre();
        } else if (columnIndex == 1) {
            return emp.getLivre().getAuteur();
        } else if (columnIndex == 2) {
            return emp.getEtudiant().getNom() + " " + emp.getEtudiant().getPrenom();
        } else if (columnIndex == 3) {
            try {
                if (new Date().after(new SimpleDateFormat("dd MMM yyyy").parse(emp.getFin()))) {
                    return emp.getFin() + " ⚠";
                } else {
                    return emp.getFin();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Emprunt getValueAt(int row) {
        return listeEmp.get(row);
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

    public void setListeEmp(ArrayList<Emprunt> list) {
        listeEmp = list;
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this));
        }
    }
}