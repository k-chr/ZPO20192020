package Kamil215691.ZPO.LAB6.Zad4;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class BisectionAlgorithmForFindingRootOfEquation {
    private final static double EPSILON = 0.1;
    private final static double STEP = 0.000001;
    private double a,b,c,s1,s2;
    private boolean isParsedEquation = false;

    private ArrayList<Double> coords;
    private ArrayList<Integer> powers;

    public BisectionAlgorithmForFindingRootOfEquation(double a, double b, double c, double s1, double s2) throws UnsupportedOperationException{
        this.a = a;
        this.b = b;
        this.c = c;
        this.s1 = s1;
        this.s2 = s2;
        if(s1 > s2) throw new UnsupportedOperationException("Invalid arguments");
    }

    private BisectionAlgorithmForFindingRootOfEquation(ArrayList<Double> tempCoords, ArrayList<Integer> tempPowers, double s1, double s2) {
        coords = new ArrayList<>();
        coords.addAll(tempCoords);
        powers = new ArrayList<>();
        powers.addAll(tempPowers);
        this.s1 = s1;
        this.s2 = s2;
        isParsedEquation = true;
    }

    public static BisectionAlgorithmForFindingRootOfEquation of(String toParse, double s1, double s2){

        if(toParse == null || toParse.equals("") || Pattern.matches("(.*\\^[-+].*)|(.*[^0-9+^x\\-].*)|(.*[\\-+][\\-+].*) | (.*\\^[0-9.].*)", toParse)) throw new UnsupportedOperationException("Invalid polynomial equation");
        if(s1 > s2) throw new UnsupportedOperationException("Invalid arguments");

        String[] groups = toParse.split("([+])|(?=-)");
        ArrayList <Double> tempCoords = new ArrayList<>();
        ArrayList <Integer> tempPowers = new ArrayList<>();

        for(String group : groups){
            if(group.contains("x")) {
                String[] subGroups = group.split("\\^");
                tempPowers.add(subGroups.length > 1 ? Integer.valueOf(subGroups[1]) : Integer.valueOf(1));
                String temp = subGroups[0].replace("x", "");
                tempCoords.add(temp.equals("") || temp.equals("+") ? 1 : (temp.equals("-") ? -1 : Double.parseDouble(temp)));
            }
            else{
                tempCoords.add(Double.parseDouble(group));
                tempPowers.add(0);
            }
        }

        return new BisectionAlgorithmForFindingRootOfEquation(tempCoords, tempPowers, s1, s2);
    }

    public BigDecimal find(){

        if(isParsedEquation) throw new UnsupportedOperationException("Cannot perform parsed equation using this method, try use findParsed()");

        ArrayList<BigDecimal> horizontalCoords = Lists.newArrayList();

        for(BigDecimal start = BigDecimal.valueOf(s1); start.compareTo(BigDecimal.valueOf(s2)) <= 0; start = start.add(BigDecimal.valueOf(STEP)) ){
            horizontalCoords.add(start);
        }

        int idx = Collections.binarySearch(Lists.transform(horizontalCoords, bigDecimal -> {
            BigDecimal decimal = bigDecimal.pow(2).multiply(BigDecimal.valueOf(a));
            decimal = decimal.add(bigDecimal.multiply(BigDecimal.valueOf(b)));
            decimal = decimal.add(BigDecimal.valueOf(c));
            return new BigDecimal(String.valueOf(decimal));
        }), new BigDecimal(0.0), (BigDecimal bigDecimal, BigDecimal bigDecimal1) -> {
            BigDecimal subtraction = bigDecimal.subtract(bigDecimal1);
            return subtraction.abs().compareTo(BigDecimal.valueOf(EPSILON)) <= 0 ? 0 : -bigDecimal.compareTo(bigDecimal1);
        });
        System.out.println(idx);
        return idx >= 0 ? horizontalCoords.get(idx) : null;
    }

    public Double findParsed(){
        if(!isParsedEquation) throw new UnsupportedOperationException("Cannot perform not parsed equation using this method, try use find()");
        ArrayList<BigDecimal> horizontalCoords = Lists.newArrayList();

        for(BigDecimal start = BigDecimal.valueOf(s1); start.compareTo(BigDecimal.valueOf(s2)) <= 0; start = start.add(BigDecimal.valueOf(STEP)) ){
            horizontalCoords.add(start);
        }

        int idx = Collections.binarySearch(Lists.transform(horizontalCoords, bigDecimal -> {
            BigDecimal decimal = BigDecimal.ZERO;

            for(int i = 0; i < coords.size(); ++i){
                BigDecimal decimal1 = bigDecimal.pow(powers.get(i));
                decimal1 = decimal1.multiply(BigDecimal.valueOf(coords.get(i)));
                decimal = decimal.add(decimal1);
            }

            return new BigDecimal(String.valueOf(decimal));
        }), new BigDecimal(0.0), (BigDecimal bigDecimal, BigDecimal bigDecimal1) -> {
            BigDecimal subtraction = bigDecimal.subtract(bigDecimal1);
            return subtraction.abs().compareTo(BigDecimal.valueOf(EPSILON)) <= 0 ? 0 : (Collections.max(powers) % 2 == 0 ? -bigDecimal.compareTo(bigDecimal1) : bigDecimal.compareTo(bigDecimal1));
        });
        return idx >= 0 ? horizontalCoords.get(idx).doubleValue() : null;

    }
}
