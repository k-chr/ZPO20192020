package Kamil215691.ZPO.LAB1.Zad4;

import javax.swing.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception{
        String answer = JOptionPane.showInputDialog("Provide mathematical formula \"a op b op c\", \nwhere a,b,c are integers less or equal 99 and greater or equal -99 and op is operator from set {+,*,-}");
        if(Pattern.matches("((0)||((-)?[1-9][0-9]?)) [-*+] ((0)||((-)?[1-9][0-9]?)) [-*+] ((0)||((-)?[1-9][0-9]?))", answer)){
            JOptionPane.showMessageDialog(null, parseAnswer(answer));
        }
        else{
            throw new Exception("Provided string: " + answer + " is not valid mathematical formula for three parameters");
        }
    }
    private static String parseAnswer(String answer){
        Map<String, String> dicc = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("0", "zero"),
                new AbstractMap.SimpleEntry<>("1", "jeden"),
                new AbstractMap.SimpleEntry<>("2", "dwa"),
                new AbstractMap.SimpleEntry<>("3", "trzy"),
                new AbstractMap.SimpleEntry<>("4", "cztery"),
                new AbstractMap.SimpleEntry<>("5", "pięć"),
                new AbstractMap.SimpleEntry<>("6", "sześć"),
                new AbstractMap.SimpleEntry<>("7", "siedem"),
                new AbstractMap.SimpleEntry<>("8", "osiem"),
                new AbstractMap.SimpleEntry<>("9", "dziewięć"),
                new AbstractMap.SimpleEntry<>("10", "dziesięć"),
                new AbstractMap.SimpleEntry<>("11", "jedenaście"),
                new AbstractMap.SimpleEntry<>("12", "dwanaście"),
                new AbstractMap.SimpleEntry<>("13", "trzynaście"),
                new AbstractMap.SimpleEntry<>("14", "czternaście"),
                new AbstractMap.SimpleEntry<>("15", "piętnaście"),
                new AbstractMap.SimpleEntry<>("16", "szesnaście"),
                new AbstractMap.SimpleEntry<>("17", "siedemnaście"),
                new AbstractMap.SimpleEntry<>("18", "osiemnaście"),
                new AbstractMap.SimpleEntry<>("19", "dziewiętnaście"),
                new AbstractMap.SimpleEntry<>("20", "dwadzieścia"),
                new AbstractMap.SimpleEntry<>("30", "trzydzieści"),
                new AbstractMap.SimpleEntry<>("40", "czterdzieści"),
                new AbstractMap.SimpleEntry<>("50", "pięćdziesiąt"),
                new AbstractMap.SimpleEntry<>("60", "sześćdziesiąt"),
                new AbstractMap.SimpleEntry<>("70", "siedemdziesiąt"),
                new AbstractMap.SimpleEntry<>("80","osiemdziesiąt"),
                new AbstractMap.SimpleEntry<>("90","dziewięćdziesiąt"),
                new AbstractMap.SimpleEntry<>("+", "plus"),
                new AbstractMap.SimpleEntry<>("-", "minus"),
                new AbstractMap.SimpleEntry<>("*", "razy")
        );
        StringBuilder out = new StringBuilder();
        String[] params = answer.split(" ");
        for(String param : params){
            if(param.length() > 1 && param.charAt(0) == '-'){
                out.append(dicc.get("" + param.charAt(0)) + " ");
                param = param.substring(1);
            }
            out.append(((param.length() < 2) || (param.compareTo("21") < 0)) ? dicc.get(param) : (dicc.get((param.charAt(0) + "0")) + " " + dicc.get("" + param.charAt(1))));
            out.append(" ");
        }
        return out.toString();
    }
}
