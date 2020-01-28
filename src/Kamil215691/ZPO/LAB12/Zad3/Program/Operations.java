package Kamil215691.ZPO.LAB12.Zad3.Program;

import Kamil215691.ZPO.LAB12.Zad3.Model.Classes;
import Kamil215691.ZPO.LAB12.Zad3.Model.ClassesWithGrades;
import Kamil215691.ZPO.LAB12.Zad3.Model.StudentModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Operations {
    private EntityManagerFactory factory;

    private String[] surnames = new String[]{
            "Ward","Smith","Walker","Kasmirski","Snow",
            "Lescott","Blackmore","Homme","Timber","Cook","Gomez","Iraschko","Kowalski",
            "Baker","Wright","Writer","Sparrow","Shutterhand","Sanchez","Pollack","Stones","Deamon"
    };

    private String[] names = new String[]{
            "John", "Abraham", "Paul", "Jeremy", "Joshua", "Mike",
            "Ian", "Bradley", "Katherine", "Anna", "Ursula", "Christopher", "Peter", "Robert", "Nicole",
            "Marie", "Bonaventura", "Giovanni", "Donald", "George", "Jadon", "Alan", "Maya", "Jennifer",
            "Kyle", "Stephen", "Seth", "Alexander", "Andrew", "Denis", "Diana", "Cynthia"
    };

    private String[] subjects = new String[]{
            "Fundamentals of Programming","Mathematical Analysis","Physics","Algorithms and Data Structures",
            "Operating systems", "Embedded Systems", "Advanced Object-Oriented Programming",
            "Network Programming", "Human-Computer Communication", "Signal and Image Processing"
    };

    private Map<String,String> getCredential(){
        Map<String, String> properties = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide password for root");
        String password = scanner.nextLine();
        scanner.close();
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }

    public void setup() throws Exception {
        factory = Persistence.createEntityManagerFactory("students", getCredential());

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("select m from StudentModel m");
        boolean createNewEntries = (q.getResultList().size() == 0);
        if (createNewEntries) {
            List<Classes> classes = new ArrayList<>();
            Random random = new Random();
            for (String subject : subjects) {
                Classes class_ = new Classes();
                class_.setSubjectName(subject);
                classes.add(class_);
            }

            for (String name : names) {
                for (String surname:surnames){
                    StudentModel model = new StudentModel();
                    model.setName(name);
                    model.setLastName(surname);
                    Collections.shuffle(classes);
                    Queue<Classes> queue = new LinkedList<>(classes);
                    int count = random.nextInt(5) + 2;
                    for(int i = 0; i < count; ++i){
                        Classes classToAdd = queue.poll();
                        int grade = random.nextInt(4) + 2;
                        model.addClasses(classToAdd, grade);
                        em.persist(classToAdd);
                    }
                    em.persist(model);
                }
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    public void printStudentStatistics(String name, String surname){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM StudentModel p WHERE p.name = :name and p.lastName = :lastName");
        q.setParameter("name", name);
        q.setParameter("lastName", surname);
        try{
            StudentModel student = (StudentModel)q.getSingleResult();
            System.out.println("Student: " + student.getLastName() + " " + student.getName()+  " |  => statistics:");
            student.getClasses().stream().forEach(classes -> {
                System.out.println("\t"+classes.getSubjectName() + " | Grade = " + classes.getGrade());
            });
        }catch(NoResultException | NonUniqueResultException e){
            System.out.println("Not found");
        }
        em.getTransaction().commit();
        em.close();
    }

    public void printAverageOfGradesForClasses(){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM ClassesWithGrades p");
        try{
            List<ClassesWithGrades> classes = q.getResultList();
            if(classes.size() < 1){
                System.out.println("Wow, such empty");
            }
            else{
                classes.stream().collect(Collectors.groupingBy(ClassesWithGrades::getSubjectName,
                        Collectors.averagingDouble(ClassesWithGrades::getGrade))).
                        forEach((k,v)->System.out.println("Subject: " + k + " | Average Grade: " + v));
            }
        }catch(NoResultException | NonUniqueResultException e){
            System.out.println("Not found");
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteClasses(String id){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT s.classes FROM StudentModel s");
        try {
            for (Object el : q.getResultList()) {
                ClassesWithGrades s = (ClassesWithGrades) el;
                if (s.getSubjectName().equals(id)) {
                    em.remove(s);
                }
            }
            Query q1 = em.createQuery("SELECT s FROM Classes s where s.subjectName = :name");
            q1.setParameter("name", id);
            try {
                Classes c = (Classes)q1.getSingleResult();
                em.remove(c);
            }catch(NoResultException | NonUniqueResultException e){
                System.out.println("Subject Not found");
            }
        }catch(NoResultException | NonUniqueResultException e){
            System.out.println("Not found");
        }
        em.getTransaction().commit();
        em.close();
    }

    public void printCountOfStudents(){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT Count(s) FROM StudentModel s");
        try {
            Long result =(Long) q.getSingleResult();
            System.out.println("Students count = "+result);
        }catch(NoResultException | NonUniqueResultException e){
            System.out.println("Not found");
        }
        em.getTransaction().commit();
        em.close();
    }
}
