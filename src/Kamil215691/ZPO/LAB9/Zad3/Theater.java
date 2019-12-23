package Kamil215691.ZPO.LAB9.Zad3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Theater {

    double p1 = 0.0;
    double p2 = 0.0;
    final int N;
    final int limit;
    final int maxSeconds;
    int expectedSpectators = 0;
    final String normalAnnouncement = "We are really sorry, this movie couldn't be played in your location";
    final String offensiveAnnouncement = "In the perfect world, people like you all bastards would not exist,\n\t\t\tBUT IT'S NOT THE PERFECT WORLD";
    public Theater(double probability1, double probability2, int person, int min, int maxCountOfSec){
        if(probability1 > 1 || probability2 > 1 || probability1 < 0 || probability2 < 0) throw  new UnsupportedOperationException("You messed with the crabbo you get the stabbo");
        p1 = probability1;
        p2 = probability2;
        N = person;
        limit = min;
        maxSeconds = maxCountOfSec;
    }

    public void runYourAnotherDayFullOfSadness() throws Exception{
        ArrayList<Person> persons = new ArrayList<>();
        for(int i = 0; i < N; ++i){
            persons.add(new Person(maxSeconds, p1));
        }
        ExecutorService service = Executors.newFixedThreadPool(8);
        List<Future<Boolean>> rV = service.invokeAll(persons);
        for(Future<Boolean> f : rV){
            if(f.get()){
                expectedSpectators++;
            }
        }
        if(expectedSpectators < limit){
            System.out.println(normalAnnouncement);
        }
        else {
            System.out.println("Movie is playing now");
            ArrayList<Spectator> spectators = new ArrayList<>();
            for(int j = 0; j < expectedSpectators; ++j){
                spectators.add(new Spectator(maxSeconds/2, p2));
            }
            expectedSpectators = 0;
            rV = service.invokeAll(spectators);
            for(Future<Boolean> f : rV){
                if(!f.get()){
                    expectedSpectators++;
                }
            }
            if(expectedSpectators < limit){
                System.out.println(offensiveAnnouncement);
            }
            else {
                rV = service.invokeAll(spectators);
                for (Future<Boolean> f : rV) {
                    if (!f.get()) {
                        expectedSpectators++;
                    }
                }
            }
        }
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    private static class Spectator implements Callable<Boolean> {
        private final int seconds;
        double probability = 0.0;

        public Spectator(int maxSeconds, double probability){
            super();
            seconds = maxSeconds;
            this.probability = probability;
        }
        Random random = new Random();
        @Override
        public Boolean call() throws Exception {
            Thread.sleep(seconds * 1_000);
            return random.nextDouble() < probability;
        }
    }

    private static class Person implements Callable<Boolean> {
        private final int seconds;
        double probability = 0.0;

        public Person(int maxSeconds, double probability){
            super();
            seconds = maxSeconds;
            this.probability = probability;
        }
        Random random = new Random();
        @Override
        public Boolean call() throws Exception {
            Thread.sleep(random.nextInt(seconds)*1000);
            return random.nextDouble() < probability;
        }
    }


}
