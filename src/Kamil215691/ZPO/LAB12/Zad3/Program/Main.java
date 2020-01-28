package Kamil215691.ZPO.LAB12.Zad3.Program;

public class Main {
    public static void main(String...args) throws Exception{
        Operations operations = new Operations();
        operations.setup();
        operations.printStudentStatistics("John", "Baker");
        operations.printStudentStatistics("John", "Sanchez");
        operations.printStudentStatistics("John", "Iraschko");
        operations.printStudentStatistics("John", "Homme");
        operations.printStudentStatistics("John", "Stones");
        operations.printAverageOfGradesForClasses();
        operations.printCountOfStudents();
        operations.deleteClasses("Human-Computer Communication");
        operations.printCountOfStudents();
    }
}
