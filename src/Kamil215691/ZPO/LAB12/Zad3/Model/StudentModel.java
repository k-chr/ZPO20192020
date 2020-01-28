package Kamil215691.ZPO.LAB12.Zad3.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name= "StudentModel")
@Table(name = "Students")
public class StudentModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String lastName;

    //like on cascade in definition of foreign key constraint
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private List<ClassesWithGrades> classes = new ArrayList<>();

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ClassesWithGrades> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesWithGrades> classes) {
        this.classes = classes;
    }

    public void addClasses(Classes classToAdd, int grade) {
        ClassesWithGrades classesWithGrades = new ClassesWithGrades(this, classToAdd);
        classesWithGrades.setGrade(grade);
        this.getClasses().add(classesWithGrades);
    }
}
