package Kamil215691.ZPO.LAB1.Zad1;

public class Employee {

    private String name;
    private String lastName;
    private String id;
    private int age;

    public Employee(){}

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ")
                .append(this.name)
                .append("\nLast name: ")
                .append(this.lastName)
                .append("\nAge: ")
                .append(this.age)
                .append("\nEmployee ID: ")
                .append(this.id);
        return stringBuilder.toString();
    }
}
