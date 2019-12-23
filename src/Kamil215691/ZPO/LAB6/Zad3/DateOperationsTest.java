package Kamil215691.ZPO.LAB6.Zad3;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DateOperationsTest {

    @Test
    void computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber() {
        int val = DateOperations.computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(LocalTime.of(0, 0), LocalTime.of(12 ,0), 3);
        assertEquals(16, val);
    }

    @Test
    void nullInputForTestedMethod(){
        assertThrows(UnsupportedOperationException.class, ()->DateOperations.computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(null, null, 12));
    }

    @Test
    void invalidTime(){
        assertThrows(UnsupportedOperationException.class, ()->DateOperations.computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(LocalTime.of(12 ,0), LocalTime.of(0, 0), 12));
    }

    @Test
    void invalidSum(){
        assertThrows(UnsupportedOperationException.class, ()->DateOperations.computeWhenSumOfHoursAndMinutesDigitsAreEqualToProvidedNumber(LocalTime.of(0 ,0), LocalTime.of(0, 12), -12));
    }
}