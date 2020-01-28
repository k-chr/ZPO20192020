package Kamil215691.ZPO.LAB12.Zad3.Model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "students_classes")
public class ClassesWithGrades {

    @EmbeddedId
    private ClassesWithGradesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentID")
    private StudentModel student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("classesID")
    private Classes classes;

    private Integer grade;
    private ClassesWithGrades() {}

    public ClassesWithGrades(
            StudentModel postId,
            Classes tagId) {
        this.student = postId;
        this.classes = tagId;
        this.id = new ClassesWithGradesId(this.student.getId(), this.classes.getId());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClassesWithGrades that = (ClassesWithGrades) o;
        return Objects.equals(student, that.student) &&
                Objects.equals(classes, that.classes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, classes);
    }

    public Integer getGrade() {
        return grade;
    }

    public String getSubjectName(){
        return classes.getSubjectName();
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
