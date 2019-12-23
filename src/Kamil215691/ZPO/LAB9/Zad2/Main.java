package Kamil215691.ZPO.LAB9.Zad2;

public class Main {
    public static void main(String[] args) throws Exception{
        SieveOfEratosthenes sieve = new SieveOfEratosthenes(1_000_000_000);
        sieve.runAlgorithm("a", 4);
        sieve.runAlgorithm("a", 2);
        sieve.runAlgorithm("a", 1);
        sieve.runAlgorithm("b", 4);
        sieve.runAlgorithm("b", 2);
        sieve.runAlgorithm("c", 4);
        sieve.runAlgorithm("c", 2);
    }
}
