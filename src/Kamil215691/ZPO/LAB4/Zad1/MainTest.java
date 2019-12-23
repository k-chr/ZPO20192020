package Kamil215691.ZPO.LAB4.Zad1;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void sortStringsTest() {
        String[] names = {"Łukasz", "Ścibor", "Stefania", "Darek", "Agnieszka",
                "Zyta", "Órszula", "Świętopełk"};

        String[] expected = {"Agnieszka", "Darek", "Łukasz", "Órszula", "Stefania", "Ścibor", "Świętopełk", "Zyta"};
        String[] namesCopy1 = Arrays.copyOf(names, names.length);
        String[] namesCopy2 = Arrays.copyOf(names, names.length);
        String[] namesCopy3 = Arrays.copyOf(names, names.length);

        Main.sortStrings(Collator.getInstance(new Locale("pl", "PL")), namesCopy1);
        Main.fastSortStrings(Collator.getInstance(new Locale("pl", "PL")), namesCopy2);
        Main.fastSortStrings2(Collator.getInstance(new Locale("pl", "PL")), namesCopy3);

        System.out.println("sortStrings\n\n");
        Arrays.stream(namesCopy1).forEach(System.out::println);
        System.out.println("---------------------------------------");

        System.out.println("fastSortStrings\n\n");
        Arrays.stream(namesCopy2).forEach(System.out::println);
        System.out.println("---------------------------------------");

        System.out.println("fastSortStrings2\n\n");
        Arrays.stream(namesCopy3).forEach(System.out::println);
        System.out.println("---------------------------------------");

        assertArrayEquals(expected, namesCopy2);
        assertArrayEquals(expected, namesCopy1);
        assertArrayEquals(expected, namesCopy3);
    }

    interface Action{
        void sort(Collator collator, String[] data);
    }

    @org.junit.jupiter.api.Test
    void efficiencyTest(){
        Action[] actions = {Main::sortStrings, Main::fastSortStrings, Main::fastSortStrings2, Main::fastSortStrings3};
        String[] actionsNames = {"sortStrings", "fastSortStrings", "fastSortStrings2", "fastSortStrings3"};
        String[] names = {"Łukasz", "Ścibor", "Stefania", "Darek", "Agnieszka",
                "Zyta", "Órszula", "Świętopełk"};


        for(int idx = 0; idx < actions.length; ++idx){
            long time = System.nanoTime();
            Action action = actions[idx];
            for(int i = 0; i < 100000; ++i){
                String[] namesCopy1 = Arrays.copyOf(names, names.length);
                action.sort(Collator.getInstance(new Locale("pl", "PL")), namesCopy1);
            }
            time = System.nanoTime() - time;
            System.out.println( actionsNames[idx]+ "\n\ttime: " + time);
        }

    }

}