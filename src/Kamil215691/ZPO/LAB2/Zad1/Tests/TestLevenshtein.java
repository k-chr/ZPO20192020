package Kamil215691.ZPO.LAB2.Zad1.Tests;

import Kamil215691.ZPO.LAB2.Zad1.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class TestLevenshtein {

    @Test
    public void matchingWordsTest(){
        Assertions.assertEquals(0, Main.LevQWERTY("Kamil", "Kamil"));
    }

    @Test
    public void subWords(){
        Assertions.assertEquals(2, Main.LevQWERTY("fotel", "fotelik"));
    }

    @Test
    public void computeLength(){
        Assertions.assertEquals(Main.LevQWERTY("", "rododendron"), ("rododendron").length());
    }

    @Test
    public void mismatchingWords(){
        Assertions.assertEquals(13.5, Main.LevQWERTY("qwerty", "ajsemtibitiiiooo"));
    }

    @Test
    public void mistakenWords(){
        Assertions.assertEquals(3, Main.LevQWERTY( "wqreyt", "qwerty"));
    }
}
