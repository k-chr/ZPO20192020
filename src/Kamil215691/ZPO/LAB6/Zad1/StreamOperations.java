package Kamil215691.ZPO.LAB6.Zad1;

import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class StreamOperations {

    private volatile File fileToStreaming;

    public StreamOperations(String path){
        try {
            fileToStreaming = new File(getClass().getResource(path).toURI());
            if(!fileToStreaming.exists()){
                throw new UnsupportedOperationException("File not found to perform streaming operations");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Integer getSumOfSalariesOfEmployeesFromPolandHavingTheLowestSalaries() throws Exception{
        return Files.lines(fileToStreaming.toPath()).
                map(line -> line.split(" ")).
                filter(line -> line[2].equals("PL")).sorted(Comparator.comparingInt(line -> (Integer.parseInt(line[3])))).limit(2).mapToInt(line -> Integer.parseInt(line[3])).sum();
    }

    public Integer getSumOfSalariesOfEmployeesFromPolandHavingTheHighestSalaries() throws Exception{
        return Files.lines(fileToStreaming.toPath()).
                map(line -> line.split(" ")).
                filter(line -> line[2].equals("PL")).sorted(Comparator.comparingInt(line -> Integer.parseInt(((String[])line)[3])).reversed()).limit(2).mapToInt(line -> Integer.parseInt(line[3])).sum();
    }

    public void getCountOfEmployeesFromCountries() throws Exception{
        Files.lines(fileToStreaming.toPath()).map(line -> line.split(" ")).map(line -> line[2]).collect(Collectors.groupingBy(line -> line, LinkedHashMap::new, Collectors.counting())).forEach((k, v) -> System.out.println(k+ " - " + v));
    }
}
