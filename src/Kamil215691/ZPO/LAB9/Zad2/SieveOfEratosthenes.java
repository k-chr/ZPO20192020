package Kamil215691.ZPO.LAB9.Zad2;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class SieveOfEratosthenes {
    private final ArrayList<Long> smallPrimes = new ArrayList<>();
    private int n;
    private boolean[] numbers;
    private final Object lock = new Object();


    private void findSmallPrimes(){
        int sqrtSqrtN = (int)Math.sqrt(Math.sqrt(n));
        int sqrtN = (int)Math.sqrt(n);
        for(int i = 2; i <sqrtSqrtN; ++i){
            if(numbers[i]){
                for(int k=2, j = k*i; j < sqrtN; ++k, j = k*i){
                    numbers[j] = false;
                }
            }
        }
        IntStream.range(2, sqrtN).forEach(num -> {
            if (numbers[num]) {
                smallPrimes.add((long) num);
            }
        });
    }

    public SieveOfEratosthenes(int max){
        n = max;
        numbers = new boolean[max];
        Arrays.fill(numbers, true);
        numbers[0] = false;
        numbers[1] = false;
        findSmallPrimes();
    }

    public void runAlgorithm(String method, int cores) throws Exception{
        Method method1 = Arrays.stream(this.getClass().getDeclaredMethods()).filter(method2 -> method2.getName().equals(method)).findAny().orElse(null);
        boolean[] arr = Arrays.copyOf(numbers, numbers.length);
        System.out.println("========================= Method: " + method1.getName() + " ========= Cores: "+ cores + " ================= Started");
        long time = System.nanoTime();
        boolean[] array = (boolean[]) method1.invoke(this,cores, arr);
        time = System.nanoTime() - time;
        printStatistics(array);
        System.out.println("========================= Method: " + method1.getName() + " ========= Cores: "+ cores + " ================= Ended. Duration time: " + ((double)time)/1_000_000_000 + "s");
    }

    private synchronized Long getNext(Long curr){
        synchronized (smallPrimes) {
            int val = smallPrimes.indexOf(curr) + 1;
            if (val >= smallPrimes.size()) {
                return smallPrimes.get(0);
            } else return smallPrimes.get(val);
        }
    }

    private boolean[] a(int cores, boolean[] array) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(cores);
        final AtomicLong status = new AtomicLong(smallPrimes.get(0));
        List<Callable<Object>> actions = new ArrayList<>();
        for(int i = 0; i < cores; ++i){
            Callable<Object> task = ()->{
                synchronized (lock) {
                    long status1 = status.getAndSet(getNext(status.get()));
                    for (Long val : smallPrimes) {

                        if (val.equals(status1)) {
                            for (int k = 2; k * status1 < n; ++k) {
                                array[(int) (k * status1)] = false;
                            }
                            status1 = status.getAndSet(getNext(status.get()));
                        }else {
                            status1 = status.get();
                        }
                    }
                }

              return new Object();
            };
            actions.add(task);
        }
        List<Future<Object>> rV = service.invokeAll(actions);
        service.shutdown();
        for(Future f:rV){
            f.get();
        }
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        return array;
    }

    private boolean[] b(int cores, boolean[] array) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(cores);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Long prime : smallPrimes) {
            List<Callable<Object>> actions = new ArrayList<>();
            for (int i = 0; i < cores; ++i) {
                Callable<Object> task = () -> {
                    int id = atomicInteger.getAndIncrement();
                    if(cores == 2){
                        if (id >= 1) {
                            atomicInteger.set(0);
                        }
                    }else {
                        if (id >= 3) {
                            atomicInteger.set(0);
                        }
                    }
                    if (prime != 2) {
                        for (int k = 0; prime * prime + id * 2 * prime + k * cores * prime * 2 < n; ++k) {
                            array[(int) (prime * prime + id * 2 * prime + k * cores * prime * 2)] = false;
                        }
                    } else {
                        for (int k = 0; prime * prime + id * prime + k * cores * prime < n; ++k) {
                            array[(int) (prime * prime + id * prime + k * cores * prime)] = false;
                        }
                    }
                    return new Object();
                };
                actions.add(task);
            }
            List<Future<Object>> rV = service.invokeAll(actions);
            for(Future f:rV){
                f.get();
            }
        }
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        return array;
    }
    private Long findNearest(Long num, Long divisor){
        if(num < divisor){
            num = divisor + 1;
        }
        for(long i = num; i < n; ++i){
            if(i % divisor == 0){
                return i;
            }
        }
        return null;
    }
    private boolean[] c(int cores, boolean[] array) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(cores);
        List<Callable<Object>> actions = new ArrayList<>();

        for(int i = 0; i < cores; ++i){
            final int id = i;
            Callable<Object> task = ()->{
                int idThread = id;
                long startIdx = id* (n/cores);
                int endIdx = (id+1) * (n/cores);
                for(Long prime: smallPrimes){
                    for(long k = findNearest(startIdx, prime)/prime; k*prime < endIdx; ++k){
                        array[(int) (k * prime)] = false;
                    }
                }
                return new Object();
            };
            actions.add(task);
        }
        List<Future<Object>> rV = service.invokeAll(actions);
        service.shutdown();
        for(Future f:rV){
            f.get();
        }
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        return array;
    }

    private void printStatistics(boolean[] arr){
        Statistics statistics = new Statistics();
        long len1bil = IntStream.range(0, arr.length).filter(idx -> arr[idx]).count();
        long len1mil = IntStream.range(0, 1_000_000).filter(idx -> arr[idx]).count();
        long len100mil = IntStream.range(0, 100_000_000).filter(idx -> arr[idx]).count();
        ArrayList<Long> arrayList = (ArrayList<Long>) LongStream.range(0,10_000_000).filter(idx -> arr[(int) idx]).boxed().collect(Collectors.toList());
        statistics.setCount1bil(len1bil);
        statistics.setCount1mil(len1mil);
        statistics.setCount100mil(len100mil);
        statistics.setTenMillionPrimes(arrayList);
        System.out.println("count of primes lesser than 1 000 000: " + statistics.getCount1mil());
        System.out.println("count of primes lesser than 10 000 000: " + statistics.getTenMillionPrimes().size());
        System.out.println("count of primes lesser than 100 000 000: " + statistics.getCount100mil());
        System.out.println("count of primes lesser than 1 000 000 000: " + statistics.getCount1bil());
    }

    private static class Statistics{
        private ArrayList<Long> tenMillionPrimes;
        private Long count1bil;
        private Long count1mil;
        private Long count100mil;

        public ArrayList<Long> getTenMillionPrimes() {
            return tenMillionPrimes;
        }

        public void setTenMillionPrimes(ArrayList<Long> tenMillionPrimes) {
            this.tenMillionPrimes = tenMillionPrimes;
        }

        public Long getCount1bil() {
            return count1bil;
        }

        public void setCount1bil(Long count1bil) {
            this.count1bil = count1bil;
        }

        public Long getCount1mil() {
            return count1mil;
        }

        public void setCount1mil(Long count1mil) {
            this.count1mil = count1mil;
        }

        public Long getCount100mil() {
            return count100mil;
        }

        public void setCount100mil(Long count100mil) {
            this.count100mil = count100mil;
        }

    }
}
