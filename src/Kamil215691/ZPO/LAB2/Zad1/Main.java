package Kamil215691.ZPO.LAB2.Zad1;

import java.util.AbstractMap;
import java.util.Map;

public class Main {

    public static double LevQWERTY(String s1, String s2){
        double out = 0;
        Map<Character, Pair> keyboardRows = Map.ofEntries(
                new AbstractMap.SimpleEntry<>('q', new Pair('\0', 'w')),
                new AbstractMap.SimpleEntry<>('w', new Pair('q', 'e')),
                new AbstractMap.SimpleEntry<>('e', new Pair('w', 'r')),
                new AbstractMap.SimpleEntry<>('r', new Pair('e', 't')),
                new AbstractMap.SimpleEntry<>('t', new Pair('r', 'y')),
                new AbstractMap.SimpleEntry<>('y', new Pair('t', 'u')),
                new AbstractMap.SimpleEntry<>('u', new Pair('y', 'i')),
                new AbstractMap.SimpleEntry<>('i', new Pair('u', 'o')),
                new AbstractMap.SimpleEntry<>('o', new Pair('i', 'p')),
                new AbstractMap.SimpleEntry<>('p', new Pair('o', '\0')),
                new AbstractMap.SimpleEntry<>('a', new Pair('\0', 's')),
                new AbstractMap.SimpleEntry<>('s', new Pair('a', 'd')),
                new AbstractMap.SimpleEntry<>('d', new Pair('s', 'f')),
                new AbstractMap.SimpleEntry<>('f',new Pair('d', 'g')),
                new AbstractMap.SimpleEntry<>('g', new Pair('f', 'h')),
                new AbstractMap.SimpleEntry<>('h', new Pair('g', 'j')),
                new AbstractMap.SimpleEntry<>('j', new Pair('h', 'k')),
                new AbstractMap.SimpleEntry<>('k', new Pair('j', 'l')),
                new AbstractMap.SimpleEntry<>('l', new Pair('k', '\0')),
                new AbstractMap.SimpleEntry<>('z', new Pair('\0', 'x')),
                new AbstractMap.SimpleEntry<>('x', new Pair('z', 'c')),
                new AbstractMap.SimpleEntry<>('c', new Pair('x', 'v')),
                new AbstractMap.SimpleEntry<>('v', new Pair('c', 'b')),
                new AbstractMap.SimpleEntry<>('b', new Pair('v', 'n')),
                new AbstractMap.SimpleEntry<>('n', new Pair('b', 'm')),
                new AbstractMap.SimpleEntry<>('m', new Pair('n', '\0'))
        );
        double [][] arr = new double[s1.length() + 1][s2.length() + 1];
        for(int i = 0; i < s1.length() + 1; ++i){
            arr[i][0] = i;
        }
        for(int j = 0; j < s2.length() + 1; ++j){
            arr[0][j] = j;
        }
        for(int i = 1; i < s1.length() + 1; ++i){
            for(int j = 1; j < s2.length() + 1; ++j){
                double cost = 0;
                char first = s1.charAt(i - 1);
                char second = s2.charAt(j - 1);
                Pair pair2 = keyboardRows.get(Character.toLowerCase(second));
                cost = first == second ? 0 : ((first == pair2.getLeftNeighbour() || first == pair2.getRightNeighbour() ) ? 0.5 : 1);
                arr[i][j] = Math.min(arr[i - 1][j - 1] + cost, Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1));
            }
        }
        out = arr[s1.length()][s2.length()];
        return out;
    }

    public static void main(String[] args) {

    }

}
class Pair{
    private char leftNeighbour;
    private char rightNeighbour;
    public Pair(char x, char y){
        leftNeighbour = x;
        rightNeighbour = y;
    }

    public char getLeftNeighbour() {
        return leftNeighbour;
    }

    public char getRightNeighbour() {
        return rightNeighbour;
    }
}