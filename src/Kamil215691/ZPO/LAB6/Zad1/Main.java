package Kamil215691.ZPO.LAB6.Zad1;

public class Main {

    public static void main(String [] args){
        StreamOperations streamOperations = new StreamOperations("dane.txt");
        try {
            System.out.println(streamOperations.getSumOfSalariesOfEmployeesFromPolandHavingTheHighestSalaries());
            System.out.println(streamOperations.getSumOfSalariesOfEmployeesFromPolandHavingTheLowestSalaries());
            streamOperations.getCountOfEmployeesFromCountries();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
