package Kamil215691.ZPO.LAB5.Zad3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class LearningProcess {

    private volatile LinkedHashSet<Word> words = new LinkedHashSet<>();
    private ArrayList<String> dictionaryEnglish = new ArrayList<>();

    private long days;
    private long terminalDay;

    public LearningProcess(long days, long dayOfAlzheimer) throws Exception{

        this.days = days;
        terminalDay = dayOfAlzheimer;
        readDictionary();

    }

    private void readDictionary() throws Exception {

        File file = new File(getClass().getResource("1500.txt").toURI());

        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))){

            String line = fileReader.readLine();

            while(line != null){
                dictionaryEnglish.add(line);
                line = fileReader.readLine();
            }
        }

    }

    public void startLearning(){

        for (int i = 0; i < days; ++i){
            DayOfLearning dayOfLearning = new DayOfLearning(i + 1);
            System.out.println(dayOfLearning);
        }

    }
    class DayOfLearning{

        Random random = new Random();
        Integer day;
        ArrayList<String> newWords = new ArrayList<>();
        ArrayList<String> forgottenWords = new ArrayList<>();

        public DayOfLearning(int day){

            this.day = day;
            Word str1 = null, str2 = null;
            ArrayList<Word> wordsToForget;
            updateDays();
            wordsToForget = words.stream().filter(w -> w.getCurrDay() >= terminalDay).collect(Collectors.toCollection(ArrayList::new));

            if(wordsToForget.size() >= 2){

                str1 = wordsToForget.get(random.nextInt(wordsToForget.size()));
                str2 = new Word(str1);

                while(str1.getData().equals(str2.getData())){
                    str2 = wordsToForget.get(random.nextInt(wordsToForget.size()));
                }

                if(random.nextBoolean()){
                    words.remove(str1);
                    forgottenWords.add(str1.getData());
                }

                if(random.nextBoolean()){
                    words.remove(str2);
                    forgottenWords.add(str2.getData());
                }

            }

            int idx = random.nextInt(dictionaryEnglish.size());

            while(!words.add(new Word(dictionaryEnglish.get(idx), 1)));
            newWords.add(dictionaryEnglish.get(idx));

            idx = random.nextInt(dictionaryEnglish.size());

            while(!words.add( new Word(dictionaryEnglish.get(idx), 1)));
            newWords.add(dictionaryEnglish.get(idx));
        }

        public String toString(){
            return "Day " + day + "\n" + "New words: " + String.join(" ", newWords) + "\nForgotten words: " + String.join(" ", forgottenWords) + "\nAlready known words: [" + words.stream().map(Word::getData).collect(Collectors.joining(" ")) + "]";
        }
    }

    private void updateDays() {
        words.forEach(word -> word.setCurrDay(word.getCurrDay() + 1));
    }
}
