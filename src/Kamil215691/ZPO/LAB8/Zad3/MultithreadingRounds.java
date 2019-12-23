package Kamil215691.ZPO.LAB8.Zad3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingRounds {

    private static char status;
    private static LinkedHashSet<Character> queue = new LinkedHashSet<>();

    private String[] strings = {"aaaa", "bb", "ccccccccccccc", "dddddd", "eeeee", "ffffffff", "gggggg", "hhh"};
    private ExecutorService service = Executors.newFixedThreadPool(strings.length + 1);
    private final Object lock = new Object();

    private Runnable printSpace = ()->{
        int maxLen = Arrays.stream(strings).map(String::length).max(Integer::compareTo).orElseGet(() -> 0);
        for(int i = 0; i < maxLen; ++i) {
            synchronized (lock) {
                if(queue.contains(' ')) {
                    while (MultithreadingRounds.status != ' ') {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else{queue.add(' ');}
                System.out.print(' ');
                MultithreadingRounds.status = queue.iterator().next();
                lock.notifyAll();
            }
        }
    };

    private static Character getNext(Character c){
        Character rV = null;

        Iterator<Character> iterator = queue.iterator();
        while(iterator.hasNext()){
            if(c.equals(iterator.next())){
                if(iterator.hasNext()){
                    rV = iterator.next();
                }
                else{
                    iterator = queue.iterator();
                    rV = iterator.next();
                }
                break;
            }
        }

        return rV;
    }
    public MultithreadingRounds(){

    }

    public void runQueue(){
        for(String string : strings){
            Runnable runnable = () -> {
                char status = string.toCharArray()[0];
                for(Character c : string.toCharArray()) {
                    synchronized (lock) {
                        try {
                            if (!queue.contains(c)) {
                                System.out.print(c);
                                queue.add(c);
                            } else {
                                while (MultithreadingRounds.status != status) {
                                    lock.wait();
                                }
                                System.out.print(c);
                                MultithreadingRounds.status = MultithreadingRounds.getNext(status);
                                lock.notifyAll();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                queue.remove(status);
            };
            service.submit(runnable);
        }
        service.submit(printSpace);
        service.shutdown();
    }
}
