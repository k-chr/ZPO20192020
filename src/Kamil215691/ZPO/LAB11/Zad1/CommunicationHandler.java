package Kamil215691.ZPO.LAB11.Zad1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CommunicationHandler {

    private volatile AtomicBoolean shouldExit = new AtomicBoolean(false);
    private Socket endPoint;
    private ObjectInputStream receiver;
    private ObjectOutputStream sender;
    private ExecutorService transferService = Executors.newFixedThreadPool(1);
    private ExecutorService receiverService = Executors.newFixedThreadPool(1);
    private final Object lock = new Object();

    public CommunicationHandler(Socket socket) throws Exception{
        endPoint = socket;
        sender = new ObjectOutputStream(endPoint.getOutputStream());
        sender.flush();
        receiver = new ObjectInputStream(endPoint.getInputStream());
        getMessage();
    }

    abstract public MessageToSend createMessage(Credentials.Stage o);

    public synchronized void sendMessage(MessageToSend m){
        if(endPoint.isClosed() || endPoint.isOutputShutdown()) return;
        Runnable task = ()->{
            try{
                synchronized (lock){
                    sender.writeObject(m);
                    sender.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        transferService.submit(task);
    }

    public synchronized void sendMessage(Credentials.Stage o){
        if(endPoint.isClosed() || endPoint.isOutputShutdown()) return;
        Runnable task = ()->{
            try{
                synchronized (lock){
                    sender.writeObject(createMessage(o));
                    sender.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        transferService.submit(task);
    }

    public synchronized void setShouldExit(){
        shouldExit.set(true);
    }

    public synchronized void getMessage(){
        Runnable task = ()->{
            do{
                if(shouldExit.get() || endPoint.isClosed() || endPoint.isInputShutdown()) break;
                try{
                    Object o = receiver.readObject();
                    rcvCmd((MessageToSend)o);
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                }

            }while (true);
        };
        receiverService.submit(task);
    }

    public void cleanUp(){
        if(!shouldExit.get()) setShouldExit();
        transferService.shutdown();
        receiverService.shutdown();
        try{
            sender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            receiver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!transferService.isTerminated()){
            transferService.shutdownNow();
            try {
                transferService.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!receiverService.isTerminated()){
            receiverService.shutdownNow();
            try{
                receiverService.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                receiverService.shutdownNow();
                new Thread(()->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }).start();
            }
        }
    }

    protected abstract void rcvCmd(MessageToSend command);
}
