package Kamil215691.ZPO.LAB12.Zad2;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

class Operations {
    private EntityManagerFactory factory;
    private String[] jobs = new String[]{"Librarian","Plumber","Teacher","Footballer","Ski-jumper",
            "Manager","Accountant","Pilot","Taxi-driver","Cook","Surgeon","Programmer","Policeman",
            "Baker","Geodesist","Writer","Musician","Waiter","Detective","Painter","Hairdresser","Mathematician"};
    public void setUp() throws Exception {
        Map<String, String> properties = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide password for root");
        String password = scanner.nextLine();
        scanner.close();
        properties.put("javax.persistence.jdbc.password", password);
        factory = Persistence.createEntityManagerFactory("people", properties);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin(); //nowa transakcja
        Query q = em.createQuery("select m from Person m"); //lista rekordów
        boolean createNewEntries = (q.getResultList().size() == 0);
        if (createNewEntries) {// No, so lets create new entries
            Family family = new Family();
            List<Job> sboj = new ArrayList<>();
            Random random = new Random();
            for (String job : jobs){
                int val = random.nextInt(8) + 1;
                Job boj = new Job();
                boj.setJobDescr(job);
                boj.setSalary(val*200*54);
                em.persist(boj);
                sboj.add(boj);
            }
            family.setDescription("Family for the Knopfs");
            em.persist(family);
            for (int i = 0; i < 40; i++) {
                Person person = new Person();
                person.setFirstName("Jim_" + i);
                person.setLastName("Knopf_" + i);
                System.out.println("Size of jobs list: "+sboj.size());
                Collections.shuffle(sboj);
                Queue<Job> jobs = new LinkedList<>(sboj);
                int val = random.nextInt(5);
                for(int j = 0; j < val; ++j){
                    Job toAdd = jobs.poll();
                    person.getJobList().add(toAdd);
                    em.persist(toAdd);
                    em.persist(person);
                }
                person.setFamily(family);
                em.persist(person);
                family.getMembers().add(person);
                em.persist(person);
                em.persist(family);
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    public void checkAvailablePeople() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select m from Person m");
        System.out.println(q.getResultList().size());
        em.close();
    }

    public void checkFamily() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select f from Family f");
        System.out.println(q.getResultList().size());
        System.out.println(((Family)q.getSingleResult()).getMembers().size());
        em.close();
    }

    public void deletePerson(String firstName, String lastName) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em
                .createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName");
        q.setParameter("firstName", firstName);
        q.setParameter("lastName", lastName);
        Person user = (Person) q.getSingleResult();
        em.remove(user);
        em.getTransaction().commit();
        em.close();
    }

    public void printJobListAndSalariesOfSpecifiedFamilyMember(int familyID, String name, String lastName){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Family p WHERE p.id = :id");
        q.setParameter("id", familyID);
        try {
            Family family = (Family) q.getSingleResult();
            List<Person> persons = family.getMembers();
            Person p = persons.stream().filter(person -> person.getFirstName().equals(name) && person.getLastName().equals(lastName)).findFirst().orElseGet(Person::new);
            System.out.println(p);
            if (p.getFamily().getId() != familyID || p.getJobList().size() < 1) {
                System.out.println("Provided member not found or provided member doesn't have any job");
            } else {
                p.getJobList().stream().forEach(job -> System.out.println("Job: " + job.getJobDescr() + " | Salary = " + job.getSalary()));
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }

    public void printTotalSalaryForEachFamilyMember(int familyID){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Family p WHERE p.id = :id");
        q.setParameter("id", familyID);
        try {
            Family family = (Family) q.getSingleResult();
            List<Person> persons = family.getMembers();
            if (persons.size() < 1) {
                System.out.println("Provided family has no members");
            }
            else{
                persons.stream().forEach(person -> System.out.println("Sum of salaries for " + person.getFirstName() + " " + person.getLastName() + " equals: " + person.getJobList().stream().map(Job::getSalary).reduce(0.0, Double::sum)));
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }

    public void printAverageSalaryForWholeFamily(int familyID){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Family p WHERE p.id = :id");
        q.setParameter("id", familyID);
        try {
            Family family = (Family) q.getSingleResult();
            List<Person> persons = family.getMembers();
            if (persons.size() < 1) {
                System.out.println("Provided family has no members");
            }
            else{
                System.out.println("Average salary in provided family equals: "+persons.stream().mapToDouble(person -> person.getJobList().stream().map(Job::getSalary).reduce(0.0, Double::sum)).average().orElse(0.0));
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }

    public void insertPerson(String name, String lastName, int familyID, List<String> jobs){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Family p WHERE p.id = :id");
        q.setParameter("id", familyID);
        try {
            Family family = (Family) q.getSingleResult();
            List<Person> persons = family.getMembers();
            if (persons.stream().anyMatch(person -> person.getLastName().equals(lastName) && person.getFirstName().equals(name))) {
                System.out.println("Provided family has already a member with provided name and last name");
            }
            else{
                Person person = new Person();
                person.setFirstName(name);
                person.setLastName(lastName);
                person.setFamily(family);

                q = em.createQuery("SELECT p FROM Job p");
                List<Job> jobList = q.getResultList();
                person.setJobList(jobList.stream().filter(job -> jobs.stream().anyMatch(job1 -> job1.equals(job.getJobDescr()))).collect(Collectors.toList()));
                family.getMembers().add(person);
                em.persist(family);
                em.persist(person);

                em.getTransaction().commit();
                System.out.println("Successfully inserted record");
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }

    public void insertFamily(String description){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Family p WHERE p.description= :id");
        q.setParameter("id", description);
        try {
            if(q.getResultList().size() > 0){
                System.out.println("Provided family already exists");
            }
            else{
                Family family = new Family();
                family.setDescription(description);
                em.persist(family);
                em.getTransaction().commit();
                System.out.println("Successfully inserted record");
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }

    public void insertJob(String jobDesc, Double salary){
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT j FROM Job j WHERE j.jobDescr = :id");
        q.setParameter("id", jobDesc);
        try {
            if(q.getResultList().size() > 0){
                System.out.println("Provided job already exists");
            }
            else {
                Job job = new Job();
                job.setJobDescr(jobDesc);
                job.setSalary(salary);
                em.persist(job);
                em.getTransaction().commit();
                System.out.println("Successfully inserted record");
            }
        }
        catch(NoResultException | NonUniqueResultException e){
            System.out.println("Can't find provided family, or more than one family has the same ID!");
        }
        em.close();
    }
}

public class Main{
    public static void main(String...args) throws Exception{
        Operations operations = new Operations();
        operations.setUp();
        operations.printAverageSalaryForWholeFamily(1);
        operations.printJobListAndSalariesOfSpecifiedFamilyMember(1, "Jim_20", "Knopf_20");
        operations.printTotalSalaryForEachFamilyMember(1);
        operations.insertJob("Lawyer", 158990.0);
        operations.insertFamily("This is the Borgia family");
        operations.insertPerson("Rodrigo", "Borgia", 2, Arrays.asList("Lawyer", "Manager", "Detective"));
        operations.printJobListAndSalariesOfSpecifiedFamilyMember(2, "Rodrigo", "Borgia");
        operations.printTotalSalaryForEachFamilyMember(2);
    }
}