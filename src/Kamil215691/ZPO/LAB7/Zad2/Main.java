package Kamil215691.ZPO.LAB7.Zad2;

public class Main {
    public static void main(String[] args) throws Exception{
        RandomGaussianFileGenerator generator = new RandomGaussianFileGenerator(args);
        generator.generateAndDumpIntoBinary("numbers.bin");
        generator.readFromBinaryAndDumpToText("numbers.bin");
        generator.showData("numbers.txt");
    }
}
