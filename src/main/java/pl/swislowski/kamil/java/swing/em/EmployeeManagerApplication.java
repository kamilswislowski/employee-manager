package pl.swislowski.kamil.java.swing.em;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.util.Vector;

public class EmployeeManagerApplication extends JFrame {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 500;
    private JPanel jPanel;
    private TableRowSorter<EmployeeTableModel> tableRowSorter;
    private JTable jTable;
    private EmployeeTableModel employeeTableModel = new EmployeeTableModel();


    public static void main(String[] args) {
        new EmployeeManagerApplication();
    }

    public EmployeeManagerApplication() {
        initialize();
    }

    public void initialize() {
        jPanel = new JPanel(new BorderLayout());
        createTable();
        createControlPane();
        add(jPanel);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createTable() {
        employeeTableModel.setTableModelData(initializeEmployeeTable());

        jTable = new JTable(employeeTableModel);
        jTable.setFillsViewportHeight(true);
        jTable.setAutoCreateColumnsFromModel(true);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableRowSorter = new TableRowSorter<>(employeeTableModel);
        jTable.setRowSorter(tableRowSorter);

        JScrollPane jScrollPane = new JScrollPane(jTable);
        jPanel.add(jScrollPane, BorderLayout.CENTER);
    }

    private void createControlPane() {
        JPanel jControlPanel = new JPanel(new BorderLayout());

        jControlPanel.add(createFilterPanel(), BorderLayout.PAGE_START);
        jControlPanel.add(createActionPanel(), BorderLayout.CENTER);
        jControlPanel.add(createFormPanel(), BorderLayout.PAGE_END);
        jPanel.add(jControlPanel, BorderLayout.PAGE_END);
    }

    private JPanel createFilterPanel() {
        JPanel jFilterPanel = new JPanel();

        jFilterPanel.add(new JLabel("Imi??"));
        JTextField jNameTextField = new JTextField(10);
        jFilterPanel.add(jNameTextField);

        jFilterPanel.add(new JLabel("Nazwisko"));
        JTextField jSurnameTextField = new JTextField(10);
        jFilterPanel.add(jSurnameTextField);

        jFilterPanel.add(new JLabel("Stanowisko"));
        JTextField jPositionTextField = new JTextField(10);
        jFilterPanel.add(jPositionTextField);

        jFilterPanel.add(new JLabel("Sta??"));
        JTextField jSeniorityTextField = new JTextField(10);
        jFilterPanel.add(jSeniorityTextField);

        jFilterPanel.add(new JLabel("Pensja"));
        JTextField jSalaryTextField = new JTextField(10);
        jFilterPanel.add(jSalaryTextField);

        JButton jFilterButton = new JButton("Filtruj");
        jFilterButton.addActionListener(e -> {
            System.out.println("Filtrowanie");
            String name = jNameTextField.getText();
            String surname = jSurnameTextField.getText();
            String position = jPositionTextField.getText();
            String seniority = jSeniorityTextField.getText();
            String salary = jSalaryTextField.getText();
            if (name != null && name.length() > 0) {
                tableRowSorter.setRowFilter(RowFilter.regexFilter(name));
            }
            if (surname != null && surname.length() > 0) {
                tableRowSorter.setRowFilter(RowFilter.regexFilter(surname));
            }
            if (position != null && position.length() > 0) {
                tableRowSorter.setRowFilter(RowFilter.regexFilter(position));
            }
            if (seniority != null && seniority.length() > 0) {
                try {
                    int number = Integer.parseInt(seniority);
                    tableRowSorter.setRowFilter(
                            RowFilter.numberFilter(
                                    RowFilter.ComparisonType.EQUAL, number));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(this,
                            "Prosz?? wpisa?? warto???? liczbow?? dla sta??u.",
                            "Ostrze??enie",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            if (salary != null && salary.length() > 0) {
                try {
                    int number = Integer.parseInt(salary);
                    tableRowSorter.setRowFilter(
                            RowFilter.numberFilter(
                                    RowFilter.ComparisonType.AFTER, number));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(this,
                            "Prosz?? wpisa?? warto???? liczbow?? dla pensji.",
                            "Ostrze??enie",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            System.out.println(name);
        });
        jFilterPanel.add(jFilterButton);

        JButton jFilterRemoveButton = new JButton("Usu?? filtr");
        jFilterPanel.add(jFilterRemoveButton);
        jFilterRemoveButton.addActionListener(e -> {
            System.out.println("Usuwanie filtru");
            tableRowSorter.setRowFilter(null);
            jNameTextField.setText("");
            jSurnameTextField.setText("");
            jPositionTextField.setText("");
            jSeniorityTextField.setText("");
            jSalaryTextField.setText("");
        });
        return jFilterPanel;
    }

    private JPanel createActionPanel() {
        JPanel jActionPanel = new JPanel();
        JButton jReadFileButton = new JButton("Odczytaj z pliku");
        jReadFileButton.addActionListener(e -> {
            System.out.println("Odczytaj");
            Vector<Employee> employees = FileManager.loadFile();
            if (employees != null) {
                System.out.println(employees);
                employeeTableModel.setTableModelData(employees);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Plik ma niepoprawny format.",
                        "Ostrze??enie",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        JButton jSaveFileButton = new JButton("Zapisz do pliku");
        jSaveFileButton.addActionListener(e -> {
            System.out.println("Zapisz");
            FileManager.saveFile(employeeTableModel.getTableModelData());
        });
        JButton jDeleteEmployeeButton = new JButton("Usu??");
        jDeleteEmployeeButton.addActionListener(e -> {
            System.out.println("Usuni??to pracownika");
            int selectedRow = jTable.getSelectedRow();
            System.out.println(selectedRow);

            employeeTableModel.removeRow(selectedRow);
        });
        jActionPanel.add(jDeleteEmployeeButton);
        jActionPanel.add(jReadFileButton);
        jActionPanel.add(jSaveFileButton);

        return jActionPanel;
    }

    private JPanel createFormPanel() {
        JPanel jFormPanel = new JPanel();
        jFormPanel.add(new JLabel("Imi??"));
        JTextField jNameTextField = new JTextField(10);
        jFormPanel.add(jNameTextField);
        jFormPanel.add(new JLabel("Nazwisko"));
        JTextField jSurnameTextField = new JTextField(10);
        jFormPanel.add(jSurnameTextField);
        jFormPanel.add(new JLabel("Stanowisko"));
        JComboBox<EmployeePosition> employeePositionJComboBox = new JComboBox<>(EmployeePosition.values());
        jFormPanel.add(employeePositionJComboBox);
        jFormPanel.add(new JLabel("Sta??"));
        JTextField jSeniorityTextField = new JTextField(10);
        jFormPanel.add(jSeniorityTextField);
        jFormPanel.add(new JLabel("Pensja"));
        JTextField jSalaryTextField = new JTextField(10);
        jFormPanel.add(jSalaryTextField);

        JButton jAddEmployeeButton = new JButton("Dodaj/Zapisz");
        jAddEmployeeButton.addActionListener(e -> {
            String name = jNameTextField.getText();
            String surname = jSurnameTextField.getText();
            EmployeePosition selectedItem = (EmployeePosition) employeePositionJComboBox.getSelectedItem();
            String seniority = jSeniorityTextField.getText();
            String salary = jSalaryTextField.getText();

            try {
                int salaryInt = Integer.parseInt(salary);
                int seniorityInt = Integer.parseInt(seniority);
                if (!name.equals("") && !surname.equals("") && !seniority.equals("")) {
                    Employee employee = new Employee(name, surname, selectedItem, seniorityInt, salaryInt);
                    employeeTableModel.addRow(employee);
                    jNameTextField.setText("");
                    jSurnameTextField.setText("");
                    jSeniorityTextField.setText("");
                    jSalaryTextField.setText("");
                    System.out.println("Dodano pracownika : " + employee);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Prosz?? wpisa?? dane do p??l tekstowych dla : imienia i nazwiska.",
                            "Ostrze??enie",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this,
                        "Prosz?? wpisa?? warto???? liczbow?? dla : sta??u i pensji.",
                        "Ostrze??enie",
                        JOptionPane.WARNING_MESSAGE);
            }

        });
        jFormPanel.add(jAddEmployeeButton);

        JButton jEditEmployeeButton = new JButton("Edytuj");
        jEditEmployeeButton.addActionListener(e -> {
            int selectedRow = jTable.getSelectedRow();
            if (selectedRow != -1) {
                Employee selectedEmployee = employeeTableModel.getRow(selectedRow);
                System.out.println(selectedEmployee);
                jNameTextField.setText(selectedEmployee.getName());
                jSurnameTextField.setText(selectedEmployee.getSurname());
                employeePositionJComboBox.setSelectedItem(selectedEmployee.getPosition());
                jSeniorityTextField.setText(String.valueOf(selectedEmployee.getSeniority()));
                jSalaryTextField.setText(String.valueOf(selectedEmployee.getSalary()));
            } else {
                JOptionPane.showMessageDialog(this,
                        "Prosz?? zaznaczy?? wiersz do edycji.",
                        "Ostrze??enie",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        jFormPanel.add(jEditEmployeeButton);

        return jFormPanel;
    }

    private Vector<Employee> initializeEmployeeTable() {
        Vector<Employee> employees = new Vector<>();
        Employee employeeOne = new Employee();
        employeeOne.setName("Jakub");
        employeeOne.setSurname("Stasiak");
        employeeOne.setPosition(EmployeePosition.MANAGER);
        employeeOne.setSeniority(2);
        employeeOne.setSalary(5000);
        Employee employeeTwo = new Employee();
        employeeTwo.setName("Adolf");
        employeeTwo.setSurname("Okuratny");
        employeeTwo.setPosition(EmployeePosition.SECRETARY);
        employeeTwo.setSeniority(3);
        employeeTwo.setSalary(1500);
        Employee employeeThree = new Employee();
        employeeThree.setName("Arnold");
        employeeThree.setSurname("Kuratny");
        employeeThree.setPosition(EmployeePosition.SECRETARY);
        employeeThree.setSeniority(3);
        employeeThree.setSalary(3000);
        employees.add(employeeOne);
        employees.add(employeeTwo);
        employees.add(employeeThree);
        return employees;
    }
}
