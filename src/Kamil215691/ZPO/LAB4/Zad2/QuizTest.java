package Kamil215691.ZPO.LAB4.Zad2;

import Kamil215691.ZPO.LAB4.Zad2.TestGenerator.Word;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.Random.class)
class QuizTest {
    private Test test;
    @org.junit.jupiter.api.BeforeEach
    void setup(){
        test = new Test();
        test.getQuestions().add(new Word("wybuch", new String[]{"bang", "blast", "explosion", "burst", "detonation"}));
        test.getQuestions().add(new Word("masakra", new String[]{"bloodbath","massacre" ,"butchery", "carnage"}));
        test.getQuestions().add(new Word("unicestwienie", new String[]{"annihilation","destruction" ,"decreation", "dilapidation", "obliteration"}));
        test.getQuestions().add(new Word("chaos", new String[]{"disorder","disarray" ,"mayhem", "havoc", "chaos"}));
        test.getQuestions().add(new Word("bunt", new String[]{"rebellion","revolt" ,"riot", "mutiny"}));
    }

    @org.junit.jupiter.api.Test
    void correctAnswersTest() {
        double score = 0;
        ArrayList<Word> questions = test.getQuestions();
        String[] answers = new String[]{"bang", "butchery", "dilapidation", "mayhem", "rebellion"};

        for(int i = 0; i < questions.size(); ++i) {
            Word question = questions.get(i);
            score += test.getOneQuestionScore(question, answers[i]);
        }

        Assertions.assertEquals(5.0, score);
    }

    @org.junit.jupiter.api.Test
    void incorrectAnswersTest() {
        double score = 0;
        ArrayList<Word> questions = test.getQuestions();
        String[] answers = new String[]{"brag", "batch", "dedication", "mayer", "revelling"};

        for(int i = 0; i < questions.size(); ++i) {
            Word question = questions.get(i);
            score += test.getOneQuestionScore(question, answers[i]);
        }

        Assertions.assertEquals(0.0, score);
    }

    @org.junit.jupiter.api.Test
    void misTypedAnswersTest() {
        double score = 0;
        ArrayList<Word> questions = test.getQuestions();
        String[] answers = new String[]{"bng", "butchry", "dilapidaton", "mayhlem", "rebelion"};

        for(int i = 0; i < questions.size(); ++i) {
            Word question = questions.get(i);
            score += test.getOneQuestionScore(question, answers[i]);
        }

        Assertions.assertEquals(2.5, score);
    }
}