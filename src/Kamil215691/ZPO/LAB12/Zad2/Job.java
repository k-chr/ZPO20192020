package Kamil215691.ZPO.LAB12.Zad2;
import javax.persistence.*;
@Entity
public class Job{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private double salary;
    private String jobDescr;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getJobDescr() { return jobDescr; }
    public void setJobDescr(String jobDescr){this.jobDescr = jobDescr;}
}