package Kamil215691.ZPO.LAB9.Zad1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskCreator {

    private volatile AtomicInteger status;
    private ExecutorService service;
    private final Object lock = new Object();
    public TaskCreator(int max){
        status = new AtomicInteger(max-1);
        service = Executors.newFixedThreadPool(max);
    }

    public void createAndRunTaskInReverseOrder(){
        int max = status.get();
        for(int i = 0; i < max+1; ++i){
            final int s = i;
            service.submit(()-> {
                synchronized (lock) {
                    while (s != this.status.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Hello darkness my old friend from task: " + this.status.getAndDecrement());
                    lock.notifyAll();
                }
            });
        }
        service.shutdown();
    }
}
