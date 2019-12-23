package Kamil215691.ZPO.LAB4.Zad3;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    public static List<String> formattedNumbers(List<Double> nums, int group, char separator, int nDigits, boolean padding){

        List<String> out = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(separator);
        symbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);

        if(nums != null && nums.stream().allMatch(Objects::nonNull)) {
            if (padding) {
                decimalFormat.applyPattern(preparePatternWithPadding(group, nDigits));
                decimalFormat.setMinimumFractionDigits(nDigits);
                int max = 0;
                for (Double d : nums) {
                    String tmp = decimalFormat.format(d);
                    out.add(tmp);
                    if(tmp.length() > max){
                        max = tmp.length();
                    }
                }
                for(int i = 0; i < out.size(); ++i){
                    int len = out.get(i).length();
                    if(len < max){
                        StringBuilder s = new StringBuilder("");
                        for(int j = 0; j < max - len; ++j) {
                            s.append(" ");
                        }
                        out.set(i, s.toString() + out.get(i));
                    }
                }
            }
            else{
                decimalFormat.applyPattern(preparePatternWithPadding(group, 0));
                int max = 0;
                List<String> fractionalParts = new ArrayList<>();
                for (Double d : nums) {
                    String s = d.toString().split("\\.")[1];
                    if(s.length() > nDigits){
                        String format = "%." + String.format("%d", nDigits) + "f";
                        String tmp = String.format(format,Double.valueOf("0." + s));
                        s = tmp.split(",")[1];
                    }
                    fractionalParts.add(s);
                    String tmp = decimalFormat.format(d);
                    out.add(tmp);
                    if(tmp.length() > max){
                        max = tmp.length();
                    }
                }

                for(int i = 0; i < out.size(); ++i){
                    int len = out.get(i).length();
                    StringBuilder s = new StringBuilder("");
                    if(len < max){
                        for(int j = 0; j < max - len; ++j) {
                            s.append(" ");
                        }
                    }
                    Double d = nums.get(i);
                    out.set(i, s.toString() + out.get(i) + ((d == Math.rint(d)) ? "" : ("." + fractionalParts.get(i))));
                }
            }
        }
        return out;
    }

    public static void main(String[] args) {
    }

    private static String preparePatternWithPadding(int group, int nDigits){

        StringBuilder str = new StringBuilder();

        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < group; ++j){
                str.append('#');
            }
            if(i == 0){
                str.append(',');
            }
        }

        if(nDigits > 0) {
            str.append('.');

            for (int k = 0; k < nDigits; ++k) {
                str.append('#');
            }
        }
        return str.toString();
    }
}
