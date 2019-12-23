package Kamil215691.ZPO.LAB7.Zad2;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class RandomGaussianFileGenerator {
    private int N;
    private double stdDev;
    private double average;

    public RandomGaussianFileGenerator(String[] args) throws IllegalArgumentException {
        if(args.length != 3) throw new IllegalArgumentException("Passed too small count of arguments");
        N = Integer.parseInt(args[0]);
        stdDev = Double.parseDouble(args[2]);
        average = Double.parseDouble(args[1]);
        if(N < 0 || stdDev < 0) throw new IllegalArgumentException("The standard deviation is negative or count of numbers to generate is negative");
    }

    public void generateAndDumpIntoBinary(String fileName) throws IOException {
        ArrayList<Double> numbers = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < N; ++i){
            numbers.add(random.nextGaussian()*stdDev + average);
        }
        try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(fileName)))){
            for(Double number : numbers){
                dataOutputStream.writeDouble(number);
            }
        }
    }

    public void readFromBinaryAndDumpToText(String fileName) throws Exception{
        ArrayList<Double> numbers = new ArrayList<>();
        byte[] bytes;
        try(DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(fileName)))){
            bytes = dataInputStream.readAllBytes();
        }
        for(int i = 0; i < bytes.length;){
            byte[] doubleFromBytes = new byte[8];
            for(int j = 0 ;j < 8 && i < bytes.length; ++j){
                doubleFromBytes[j] = bytes[i++];
            }
            numbers.add(toDouble(doubleFromBytes));
        }
        System.out.print(numbers.size());
        //TODO dump
        fileName = fileName.replace(".bin", ".txt");
        try(FileWriter fileWriter = new FileWriter(new File(fileName))){
            for(Double d : numbers){
                NumberFormat format = NumberFormat.getNumberInstance(new Locale("pl", "PL"));
                fileWriter.write(format.format(d)+'\n');
            }
        }
    }

    public void showData(String filename) throws Exception{
        File file = new File(getClass().getResource("script.py").toURI());
        String command = "C:\\ProgramData\\Anaconda3\\python.exe " + file.getAbsolutePath();
        command = command.replace("\\", "/");
        String param = " -f ";
        File file1 = new File(filename);
        param += file1.getAbsolutePath();
        param = param.replace("\\", "/");
        Process p = Runtime.getRuntime().exec(command + param );
        System.out.println(command + param);
        System.out.println();
        System.out.println(p.info());
        InputStream reader = (p.getInputStream());
        InputStream stream = p.getErrorStream();
        reader.transferTo(System.out);
        stream.transferTo(System.out);
        System.out.println(p.pid());
        System.out.println(p.exitValue());
    }

    private static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }


}
