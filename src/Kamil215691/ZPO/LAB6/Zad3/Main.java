package Kamil215691.ZPO.LAB6.Zad3;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args){
        System.out.println(String.format("World War II endured: %d days", DateOperations.daysOfWorldWarII()));
        LocalDate var = DateOperations.theDayAndMonthFromOffset(LocalDate.of(2016,1,1), 67);
        System.out.println(String.format("The 68th day of 2016 was in %02d.%02d", var.getMonthValue(), var.getDayOfMonth()));
        System.out.println(String.format("Times when sum of digits of hours and minutes of time between 11:45 - 22:30 equals 15: %d", DateOperations.computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(LocalTime.of(11, 45), LocalTime.of(22, 30), 15)));
        System.out.println(String.format("How many times I've lived 29th of February since my date of birth: %d", DateOperations.howManyTimesProvidedDayAppearedBetweenProvidedDates(LocalDate.of(2004,3,14),LocalDate.now(), 29, 2)));
    }
}
