package pl.swislowski.kamil.java.swing.em;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class EmployeeTableModel extends AbstractTableModel {
    public static final String[] COLUMN_NAMES = {"imię", "nazwisko", "stanowisko", "staż pracy", "pensja"};
    private Vector<Employee> employees;

    public void setTableModelData(Vector<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    public Vector<Employee> getTableModelData() {
        return employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0: {
                return employees.get(rowIndex).getName();
            }
            case 1: {
                return employees.get(rowIndex).getSurname();
            }
            case 2: {
                return employees.get(rowIndex).getPosition();
            }
            case 3: {
                return employees.get(rowIndex).getSeniority();
            }
            case 4: {
                return employees.get(rowIndex).getSalary();
            }
            default: {
                return "";
            }
        }
    }

    public void removeRow(int row) {
        employees.remove(row);
        fireTableDataChanged();
    }

    public void addRow(Employee employee) {
        employees.add(employee);
        fireTableDataChanged();
    }

    public Employee getRow(int row) {
        return employees.get(row);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}

