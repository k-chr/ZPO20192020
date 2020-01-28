package Kamil215691.ZPO.LAB12.Zad3.Model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClassesWithGradesId implements Serializable {
    @Column(name = "student_id")
    private Integer studentID;

    @Column(name = "classes_id")
    private Integer classesID;
    private ClassesWithGradesId() {}

    public ClassesWithGradesId(
            Integer studentID,
            Integer tagId) {
        this.studentID = studentID;
        this.classesID = tagId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClassesWithGradesId that = (ClassesWithGradesId) o;
        return Objects.equals(studentID, that.studentID) &&
                Objects.equals(classesID, that.classesID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID, classesID);
    }
}
