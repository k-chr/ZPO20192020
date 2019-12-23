package Kamil215691.ZPO.LAB7.Zad3;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Stream;

public class RandomFileValidator {
    private String[] queueOfFiles;

    public RandomFileValidator(String...args){
        if(args == null || args.length < 1) throw new IllegalArgumentException("Too small count of arguments");
        queueOfFiles = args;
    }
    public void runQueue(){
        Stream.of(queueOfFiles).forEach(RandomFileValidator::validate);
    }

    private static void validate(String filename){
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        int len = 0;
        Pair<Integer,Integer> pair = new Pair<>();
        try {
            File file = new File(RandomFileValidator.class.getResource(filename).toURI());
            try(DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))){
                int i = 0;
                while(true){
                    int valToPut = dataInputStream.readInt();
                    if(set.size() >= 1001){
                        set.remove(set.iterator().next());
                    }
                    boolean var = set.add(valToPut);
                    if(!var){
                        pair.second = i;
                        len = set.size() - findInSet(set,valToPut );
                        pair.first =  i - len;
                        break;
                    }
                    ++i;
                }
                if(len <= 1000){
                    System.out.println("Bad sequence. Two indices: " + pair.toString() + ", length = " + len);
                }
                else{
                    System.out.println("Good sequence");
                }
            }
        }catch (EOFException e){
            System.out.println("Good sequence");
        }
        catch (Exception io){
            io.printStackTrace();
        }
    }
    private static Integer findInSet(LinkedHashSet<Integer> set, Integer integer) {
        int i = -1;
        int j = 0;
        for(Integer d: set){
            if(d.equals(integer)){
                i = j;
                break;
            }
            ++j;
        }

        return i;
    }


}
class Pair<K,V>{
    public K first;
    public V second;

    public Pair(){

    }

    public String toString(){
        return first.toString() + ' ' + second.toString();
    }
}
