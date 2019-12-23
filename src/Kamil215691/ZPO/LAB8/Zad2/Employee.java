package Kamil215691.ZPO.LAB8.Zad2;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;

public class Employee {

    private String name;
    private String lastName;
    private LocalDate dateOfEmployment;
    private int age;
    private boolean isManager = false;
    private long phoneNumber;

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isManager() {
        return isManager;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public int getAge() {
        return age;
    }

    @IgnoreEquals
    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Employee(){}

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
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
                .append("\nEmployee date of employment: ")
                .append(this.dateOfEmployment);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other){
        return (other instanceof Employee) && (this.getAge() == ((Employee) other).getAge()) && this.getName().equals(((Employee) other).getName()) &&
                this.getLastName().equals(((Employee) other).getLastName()) && (this.isManager() == ((Employee) other).isManager()) && this.getDateOfEmployment().equals(((Employee) other).getDateOfEmployment());
    }

    public boolean reflectEquals(Object other) {
        boolean value = false;

        if(other.getClass().equals(this.getClass())){
            value = Arrays.stream(this.getClass().getDeclaredMethods()).filter(method -> method.getName().contains("get") || method.getName().equals("isManager")).filter(method -> Arrays.stream(method.getDeclaredAnnotations()).noneMatch(annotation -> annotation instanceof IgnoreEquals)).allMatch(method -> {
                try {
                    return method.invoke(this).equals(method.invoke(other));
                } catch (IllegalAccessException| InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            });
        }

        return value;
    }
}
