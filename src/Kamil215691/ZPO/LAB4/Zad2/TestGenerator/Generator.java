package Kamil215691.ZPO.LAB4.Zad2.TestGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Generator {

    private static final String jsonName = "PolEngTest.json";

    public static void main(String[] args) throws Exception{
        generateTest();
    }

    private static void generateTest() throws Exception {

        ArrayList<Word> list = new ArrayList<>();
        list.add(new Word("wybuch", new String[]{"bang", "blast", "explosion", "burst", "detonation"}));
        list.add(new Word("droga", new String[]{"lane", "route", "road", "way", "path"}));
        list.add(new Word("magazyn", new String[]{"storage","storehouse" ,"warehouse", "depository"}));
        list.add(new Word("przeciętny", new String[]{"ordinary","average" ,"unremarkable", "common"}));
        list.add(new Word("zysk", new String[]{"benefit","gain" ,"profit", "return"}));
        list.add(new Word("chaos", new String[]{"disorder","disarray" ,"mayhem", "havoc", "chaos"}));
        list.add(new Word("cel", new String[]{"aim","objective" ,"goal", "purpose"}));
        list.add(new Word("bunt", new String[]{"rebellion","revolt" ,"riot", "mutiny"}));
        list.add(new Word("unicestwienie", new String[]{"annihilation","destruction" ,"decreation", "dilapidation", "obliteration"}));
        list.add(new Word("masakra", new String[]{"bloodbath","massacre" ,"butchery", "carnage"}));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Writer writer = new FileWriter(jsonName)){
            gson.toJson(list, writer);
        }
    }


}
