package Kamil215691.ZPO.LAB1.Zad2;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Integer number =  0b1101_1000;

    public static void main(String[] args) {

        System.out.println("Provide base to convert number: " + number);
        Scanner scanner = new Scanner(System.in);
        String base = scanner.next();
        BaseConverter baseConverter = new BaseConverter(base);
        try {
            System.out.println(baseConverter.convert());
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }
    static class BaseConverter{
        private String base;

        BaseConverter(String base){
            this.base = base;
        }

        public String convert() throws NoSuchElementException{
            switch(base){
                case "dziesięć": {
                    return convertTo10();
                }
                case "trzy" : {
                    return convertTo3();
                }
                case "szesnaście" : {
                    return convertTo16();
                }
                default:{
                    throw new NoSuchElementException("This base is not recognized by a program!");
                }
            }
        }
        private String convertTo3(){
            Integer integer = Integer.parseInt(number.toString(), 10);
            return integer.toString(integer, 3);
        }
        private String convertTo16(){
            Integer integer = Integer.parseInt(number.toString(), 10);
            return integer.toString(integer, 16);
        }
        private String convertTo10(){
            return number.toString();
        }

    }
}
