package pl.swislowski.kamil.java.swing.em;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class FileManager {
    private static final String EMPLOYEES_TXT = "employees.txt";

    public static Vector<Employee> loadFile() {
        System.out.println("Wczytuję plik...");

        Vector<Employee> employees = null;
        try {
            List<String> strings = Files.readAllLines(Paths.get(EMPLOYEES_TXT));
            if (strings.size() > 0) {
                employees = new Vector<>();
                for (String string : strings) {

                    String[] split = string.split(" ");
                    if (split.length == 5) {
                        System.out.println(Arrays.toString(split));
                        Employee employee = new Employee();
                        employee.setName(split[0]);
                        employee.setSurname(split[1]);
                        employee.setPosition(EmployeePosition.valueOf(split[2]));
                        employee.setSeniority(Integer.valueOf(split[3]));
                        employee.setSalary(Integer.valueOf(split[4]));
                        employees.add(employee);
                    } else {
                        employees = null;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            employees = null;
        }
        return employees;
    }

    public static void saveFile(Vector<Employee> employees) {
        System.out.println("Zapisuję plik...");
        List<String> strings = employees.stream()
                .map(employee -> employee.getName() +
                        " " + employee.getSurname() +
                        " " + employee.getPosition() +
                        " " + employee.getSeniority() +
                        " " + employee.getSalary())
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(EMPLOYEES_TXT), strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
