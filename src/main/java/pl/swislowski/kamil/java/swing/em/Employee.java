package pl.swislowski.kamil.java.swing.em;

public class Employee {
    private String name;
    private String surname;
    private EmployeePosition position;
    private Integer seniority;
    private Integer salary;

    public Employee() {
    }

    public Employee(String name, String surname, EmployeePosition position, Integer seniority, Integer salary) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.seniority = seniority;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public Integer getSeniority() {
        return seniority;
    }

    public void setSeniority(Integer seniority) {
        this.seniority = seniority;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position=" + position +
                ", seniority=" + seniority +
                ", salary=" + salary +
                '}';
    }
}
