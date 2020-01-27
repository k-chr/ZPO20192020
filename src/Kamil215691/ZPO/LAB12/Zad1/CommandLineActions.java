package Kamil215691.ZPO.LAB12.Zad1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineActions {
    public boolean stopProperty = false;
    private Scanner scanner;
    private ModelConnector connector;

    public CommandLineActions(){
        scanner = new Scanner(System.in);
        connector = new ModelConnector();
    }

    public void startListen(){
        System.out.println("Provide password for user: \'root\' to connect to MySQL Database\n");
        String passwd = scanner.nextLine();
        stopProperty = !connector.connectToDB(passwd);
        if(stopProperty){
            System.out.println("Cannot connect to database, I\'m going to close program!");
        }
        while (!stopProperty){
            System.out.println("HELLO USER WHAT DO YOU WANT TO DO?:\n\t1. Insert new records? Type \"IR\" and press enter\n\t2. Sort records by some criteria? Type \"SR\" and press enter\n\t3. Print average salary in specified country? Type \"AS\" and press enter\n\t4. Quit program? Just press enter!");
            String line  = scanner.nextLine();
            switch (line){
                case "":{
                    stopProperty = true;
                    break;
                }
                case "IR":{
                    insertRecords();
                    break;
                }
                case "SR":{
                    sortRecords();
                    break;
                }
                case "AS":{
                    printAverageSalary();
                    break;
                }
            }
        }
        System.out.println("It\'s the end of the world as we know it");
        scanner.nextLine();
        connector.cleanUp();
        scanner.close();
    }

    private void insertRecords(){
        boolean stop = false;
        while(!stop){
            System.out.println("HELLO USER WHAT DO YOU WANT TO DO?:\n\t 1. Type record? Type \"next\" and press enter\n\t 2. Exit insertRecords method? Just press enter");
            String line = scanner.nextLine();
            switch(line){
                case "":{
                    stop = true;
                    break;
                }
                case "next":{
                    getNextRecordToInsert();
                }
            }
        }
    }
    private void getNextRecordToInsert(){
        System.out.println("Provide Data: Name, LastName, Country, Salary");
        String data = scanner.nextLine();
        String[] splitted = data.split(" ");
        if(splitted.length != 4){
            System.out.println("Incorrect data");
            return;
        }
        String name = splitted[0];
        String lastName = splitted[1];
        String country = splitted[2];
        String salary = splitted[3];
        if(salary.matches(".*[^0-9]+.*") || name.matches(".*[^a-zA-Z]+.*") || lastName.matches(".*[^a-zA-Z]+.*") || country.matches(".*[^a-zA-Z]+.*")){
            System.out.println("Can't insert data, provided values are incorrect");
        }
        else{
            if(connector.insertRecord(name,lastName,Integer.valueOf(salary), country)){
                System.out.println("Successfully added new data");
            }
            else {
                System.out.println("Provided data already exists in database");
            }
        }
    }

    private void sortRecords(){
        System.out.println("Which column?\n\t1. Name - type 1\n\t2. Last name - type 2\n\t3. Country - type 3\n\t4. Salary - type 4");
        Map<String, String> words = new HashMap<>();
        words.put("1", "Name");
        words.put("2", "LastName");
        words.put("3", "Country");
        words.put("4", "Salary");
        String line = scanner.nextLine();
        switch(line){
            case "1": case "2": case"3": case"4":{
                connector.sortRecords(words.get(line));
                break;
            }
            default:{
                System.out.println("Invalid operation");
                break;
            }
        }
    }

    private void printAverageSalary(){
        System.out.println("Provide name of country to get the value of average salary");
        String country = scanner.nextLine();
        connector.getAverageSalary(country);
    }
}
