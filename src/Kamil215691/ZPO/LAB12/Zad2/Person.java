package Kamil215691.ZPO.LAB12.Zad2;
import java.util.*;
import javax.persistence.*;
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String firstName;
    private String lastName;
    private Family family;
    private String nonsenseField = "";
    private List<Job> jobList = new ArrayList<>();
    public int getId() { return id; }
    public void setId(int Id) { this.id = Id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // Leave the standard column name of the table
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName;
    }
    @ManyToOne
    public Family getFamily() { return family; }
    public void setFamily(Family family) { this.family = family; }
    @Transient
    public String getNonsenseField() { return nonsenseField; }
    public void setNonsenseField(String nonsenseField) {
        this.nonsenseField = nonsenseField;
    }
    @OneToMany
    public List<Job> getJobList() {
        return this.jobList;
    }
    public void setJobList(List<Job> nickName) {
        this.jobList = nickName;
    }
}
