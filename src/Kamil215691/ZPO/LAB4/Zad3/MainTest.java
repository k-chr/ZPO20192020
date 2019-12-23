package Kamil215691.ZPO.LAB4.Zad3;

import java.util.List;

import static Kamil215691.ZPO.LAB4.Zad3.Main.formattedNumbers;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void testFormattedNumbers() {
        List<Double> li = List.of((double)-5100, 43.257, (double)200000, 2000000.5);
        List<String> fn = formattedNumbers(li, 2, ',', 2, true);
        assertEquals(4, fn.size());
        assertEquals("    -51,00.00", fn.get(0));
        assertEquals("        43.26", fn.get(1));
        assertEquals("  20,00,00.00", fn.get(2));
        assertEquals("2,00,00,00.50", fn.get(3));

        List<String> fn2 = formattedNumbers(li, 3, '|', 2,
                false);
        assertEquals(4, fn2.size());
        assertEquals("   -5|100", fn2.get(0));
        assertEquals("       43.26", fn2.get(1));
        assertEquals("  200|000", fn2.get(2));
        assertEquals("2|000|000.5", fn2.get(3));
    }
}