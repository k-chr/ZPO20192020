package Kamil215691.ZPO.LAB4.Zad2;

import Kamil215691.ZPO.LAB4.Zad2.TestGenerator.Word;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.io.FileReader;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class Test {

    public ArrayList<Word> getQuestions() {
        return questions;
    }

    private ArrayList<Word> questions = new ArrayList<>();

    public void randomizeTest() throws Exception {
        Gson gson = new GsonBuilder().create();
        Word[] arr;
        String jsonName = "PolEngTest.json";
        try(JsonReader jsonReader = new JsonReader(new FileReader(jsonName))){
            arr = gson.fromJson(jsonReader, Word[].class);
        }

        Random random = new Random();
        if(arr != null) {
            while (questions.size() != 5) {
                int idx = random.nextInt(Integer.MAX_VALUE) % 10;
                if ((arr.length != 10)) {
                    throw new IllegalArgumentException();
                }
                Word w = arr[idx];
                if(!questions.contains(w)){
                    questions.add(w);
                }
            }
        }

    }

    public void runTest() throws Exception{

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Hello");
        nameDialog.setContentText("Provide your name and last name");
        Optional<String> resultName = nameDialog.showAndWait();
        String file_name = resultName.orElse("None").replace(" ", "_");
        file_name += ".json";
        ArrayList<Word> answers = new ArrayList<>();
        long time = System.nanoTime();
        final double[] score = {0};

        for(int i = 0; i < questions.size(); ++i){
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Question No. " + i);
            Word question = questions.get(i);
            textInputDialog.setHeaderText("Provide english translation of: \n\t" + question.getBase());
            Optional<String> result = textInputDialog.showAndWait();
            result.ifPresent(s ->{ score[0] += getOneQuestionScore(question, s);
            answers.add(new Word(question.getBase(), new String[]{s}));});
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz score");
        double emit = (System.nanoTime() - time)/1_000_000_000.0;
        System.out.printf("%.2f", emit);
        alert.setContentText("You received: " + String.format("%.1f/%.1f\n\nElapsed time: %.2f seconds", score[0], (float) questions.size(), emit) );
        alert.showAndWait();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(Writer writer = new FileWriter(file_name)){
            gson.toJson(answers, writer);
        }
    }

    public double getOneQuestionScore(Word question, String answer){
        return Arrays.stream(question.getTranslations()).anyMatch(s1 -> s1.equals(answer.toLowerCase())) ?  1.0 : (Arrays.stream(question.getTranslations()).anyMatch(s2 -> Lev(s2, answer.toLowerCase()) == 1) ? 0.5 : 0.0);
    }

    private double Lev(String s1, String s2){
        double [][] arr = new double[s1.length() + 1][s2.length() + 1];
        for(int i = 0; i < s1.length() + 1; ++i){
            arr[i][0] = i;
        }
        for(int j = 0; j < s2.length() + 1; ++j){
            arr[0][j] = j;
        }
        for(int i = 1; i < s1.length() + 1; ++i){
            for(int j = 1; j < s2.length() + 1; ++j){
                char first = s1.charAt(i - 1);
                char second = s2.charAt(j - 1);
                double cost = first == second ? 0 : 1;
                arr[i][j] = Math.min(arr[i - 1][j - 1] + cost, Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1));
            }
        }
        return arr[s1.length()][s2.length()];
    }
}
