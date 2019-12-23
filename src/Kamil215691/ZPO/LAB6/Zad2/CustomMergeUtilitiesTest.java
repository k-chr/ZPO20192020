package Kamil215691.ZPO.LAB6.Zad2;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomMergeUtilitiesTest {

    private Map<String, Integer> counts;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        counts = new HashMap<>();
        counts.put("word", 1);
    }

    @org.junit.jupiter.api.Test
    void mergeUsingContains() {
        long time = System.nanoTime();
        CustomMergeUtilities.mergeUsingContains(counts, "word", 1, Integer::sum);
        CustomMergeUtilities.mergeUsingContains(counts, "new", 1, Integer::sum);
        time = System.nanoTime() - time;
        double seconds = time/(1000000000.0);
        System.out.println(String.format("mergeUsingContains: %f",seconds));
        assertEquals(2, counts.get("word"));
        assertEquals(1, counts.get("new"));
    }

    @org.junit.jupiter.api.Test
    void mergeUsingGetAndNullCheck() {
        long time = System.nanoTime();
        CustomMergeUtilities.mergeUsingGetAndNullCheck(counts, "word", 1, Integer::sum);
        CustomMergeUtilities.mergeUsingGetAndNullCheck(counts, "new", 1, Integer::sum);
        time = System.nanoTime() - time;
        double seconds = time/(1000000000.0);
        System.out.println(String.format("mergeUsingGetAndNullCheck: %f",seconds));
        assertEquals(2, counts.get("word"));
        assertEquals(1, counts.get("new"));
    }

    @org.junit.jupiter.api.Test
    void mergeUsingGetOrDefault() {
        long time = System.nanoTime();
        CustomMergeUtilities.mergeUsingGetOrDefault(counts, "word", 1, Integer::sum);
        CustomMergeUtilities.mergeUsingGetOrDefault(counts, "new", 1, Integer::sum);
        time = System.nanoTime() - time;
        double seconds = time/(1000000000.0);
        System.out.println(String.format("mergeUsingGetOrDefault: %f",seconds));
        assertEquals(2, counts.get("word"));
        assertEquals(1, counts.get("new"));
    }

    @org.junit.jupiter.api.Test
    void mergeUsingPutIfAbsent() {
        long time = System.nanoTime();
        CustomMergeUtilities.mergeUsingPutIfAbsent(counts, "word", 1, Integer::sum);
        CustomMergeUtilities.mergeUsingPutIfAbsent(counts, "new", 1, Integer::sum);
        time = System.nanoTime() - time;
        double seconds = time/(1000000000.0);
        System.out.println(String.format("mergeUsingPutIfAbsent: %f",seconds));
        assertEquals(2, counts.get("word"));
        assertEquals(1, counts.get("new"));
    }
}