package Kamil215691.ZPO.LAB4.Zad1;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void sortStrings(Collator collator, String[] words){
        for(int i = 1, j; i < words.length; ++i){
            String s = words[i];
            j = i - 1;
            while(j >= 0 && collator.compare(s, words[j]) < 0){
                words[j + 1] = words[j];
                --j;
            }
            words[j + 1] = s;
        }
    }

    public static void fastSortStrings2(Collator collator, String[] words){
        Arrays.sort(words, (word1, word2)->collator.compare(word1, word2));
    }

    public static void fastSortStrings3(Collator collator, String[] words){
        Arrays.sort(words, collator::compare);
    }
    public static void fastSortStrings(Collator collator, String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }

    public static void main(String[] args) {
	// write your code here
    }
}
