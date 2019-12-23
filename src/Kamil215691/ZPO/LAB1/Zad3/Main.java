package Kamil215691.ZPO.LAB1.Zad3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static String PESEL = "";
    private static LocalDate date = null;
    private static String sex = "";
    public static void main(String[] args) {

        System.out.println("Provide PESEL");
        Scanner scanner = new Scanner(System.in);
        PESEL = scanner.next();
        scanner.close();
        try{
            validatePESEL();
            System.out.println("Provided PESEL is valid");
            System.out.println("Date of birth: " + date.toString());
            System.out.println("Sex: " + sex);
        }
        catch (Exception e){
            System.out.println("Provided PESEL is not valid: \n\t" + e.getMessage());
        }
    }

    private static void validatePESEL() throws Exception{
        Map<Integer, String> mapOfYearPrefixes = Map.of(0,"19",
                1,"19",
                2,"20",
                3,"20",
                4,"21",
                5,"21",
                6,"22",
                7,"22",
                8,"18",
                9,"18");
        if(Pattern.matches("[^0-9]+.*", PESEL)){
            throw new Exception("PESEL can contain only digits");
        }
        if(PESEL.length() != 11){
            throw new Exception("PESEL always contains 11 digits");
        }
        String chk = checksum();
        if(chk.lastIndexOf(PESEL.charAt(PESEL.length() - 1)) != chk.length() - 1){
            throw new Exception("PESEL control digit doesn't match to computed checksum");
        }
        String month = PESEL.substring(2,4);
        String year = PESEL.substring(0,2);
        String day = PESEL.substring(4,6);
        sex = (PESEL.charAt(9) - '0') % 2 == 0 ? "Female" : "Male";
        year = mapOfYearPrefixes.get(month.charAt(0) - '0') + year;
        month = ((Integer)((month.charAt(0) - '0')%2)).toString() + month.charAt(1);
        Integer y = Integer.parseInt(year, 10);
        if(month.equals("02") && (
                ((Integer.parseInt(day, 10) > 29) && (
                        (((y % 4) == 0) &&
                        ((y % 100) != 0)) ||
                        ((y % 400) == 0)
                )) ||
                ((Integer.parseInt(day, 10) > 28) && (
                        ((y % 4) != 0) ||
                        (((y % 100) == 0) &&
                        ((y % 400) != 0))
                ))
        )){
            throw new Exception("Cannot parse properly provided date");
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date = LocalDate.from(format.parse(day + "-" + month + "-" + year));
    }
    //9×a + 7×b + 3×c + 1×d + 9×e + 7×f + 3×g + 1×h + 9×i + 7×j checksum
    private static String checksum(){
        Integer chcksm = 0;
        int[] multipliers = {9,7,3,1,9,7,3,1,9,7};
        for(int i = 0; i < multipliers.length; ++i){
            chcksm += multipliers[i] * (PESEL.charAt(i) - '0');
        }
        return chcksm.toString();
    }
}
