package Kamil215691.ZPO.LAB6.Zad3;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateOperations {

    public static long daysOfWorldWarII(){
        LocalDate dayOfBeginning = LocalDate.of(1939, 9, 1);
        LocalDate dayOfEnd = LocalDate.of(1945, 5 ,8);
        return ChronoUnit.DAYS.between(dayOfBeginning, dayOfEnd);
    }

    public static LocalDate theDayAndMonthFromOffset(LocalDate baseDate, int days){
        return baseDate.plusDays(days);
    }

    public static int computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(LocalTime hoursFrom, LocalTime hoursTo, int model){
        if(model < 0 || hoursFrom == null || hoursTo == null || hoursFrom.compareTo(hoursTo) > 0) throw new UnsupportedOperationException("Provided invalid data into method");
        int count = 0;
        for(;hoursFrom.compareTo(hoursTo) <= 0;){
            int sum = hoursFrom.getHour()%10 + hoursFrom.getHour()/10 + hoursFrom.getMinute()/10 + hoursFrom.getMinute()%10;
            if(sum == model){
                ++count;
            }
            hoursFrom = hoursFrom.plusMinutes(1);
        }
        return count;
    }

    public static long howManyTimesProvidedDayAppearedBetweenProvidedDates(LocalDate dateFrom, LocalDate dateTo, int day, int month){
        return dateFrom.datesUntil(dateTo).filter(date -> date.getDayOfMonth() == day && date.getMonth().getValue() == month).count();
    }
}
