package Kamil215691.ZPO.LAB8.Zad1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MaxSearchAlgorithms {

    private int[] input;

    public MaxSearchAlgorithms(@NotNull int []  input){
        if(input.length <= 0) throw new UnsupportedOperationException("There's nothing to search");
        this.input = input;
    }

    ArrayList<Integer> scanFromRightToLeft(){
        ArrayList<Integer> maximums = new ArrayList<>();
        int max = input[input.length-1];
        maximums.add(max);
        for(int i = input.length-1; i >= 0; --i){
            if(input[i] > max) {
                max = input[i];
                maximums.add(max);
            }
        }
        return maximums;
    }
    ArrayList<Integer> scanFromLeftToRight(){

        ArrayList<Integer> maximums = new ArrayList<>();
        int max = input[0];
        maximums.add(max);

        for (int value : input) {
            if (value > max) {
                max = value;
                maximums.add(max);
            }
        }

        return maximums;
    }
    ArrayList<Integer> scanFromEvenToOdd(){

        ArrayList<Integer> maximums = new ArrayList<>();
        boolean evenFinished = false;
        int max = input[0];
        maximums.add(max);

        for(int i = 0;  ; i += 2){
            if(!evenFinished && i >= input.length){
                i = 1;
                evenFinished = true;
            }

            if(i >= input.length){
                break;
            }
            if (input[i] > max) {
                max = input[i];
                maximums.add(max);
            }
        }

        return maximums;
    }
}
