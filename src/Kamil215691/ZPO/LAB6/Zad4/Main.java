package Kamil215691.ZPO.LAB6.Zad4;

public class Main {
    public static void main(String []args){
        BisectionAlgorithmForFindingRootOfEquation bisectionAlgorithm = new BisectionAlgorithmForFindingRootOfEquation(2,0,-1,-1,0);
        System.out.println(bisectionAlgorithm.find());
        BisectionAlgorithmForFindingRootOfEquation parsedAlgorithm = BisectionAlgorithmForFindingRootOfEquation.of("2x^3+3", -3, 3);
        System.out.println(parsedAlgorithm.findParsed());
    }
}
