package Kamil215691.ZPO.Kolokwium._2;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class CommunicationHandler {
    private Socket socket = null;
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;
    private ExecutorService sendingPool = Executors.newFixedThreadPool(1);
    private ExecutorService receivingPool = Executors.newFixedThreadPool(1);
    private final Object lock = new Object();

    public CommunicationHandler (Socket socket){
        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            output = null;
        }
        try {
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            input = null;
        }

    }

    public synchronized void getMessage() {
        Runnable task = () -> {
            do {
                if (socket.isClosed() || socket.isInputShutdown()) break;
                try {
                    Object o = input.readObject();
                    rcvCmd((Packet) o);
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                }

            } while (true);
        };
        receivingPool.submit(task);
    }
    public void clean() {
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
        if(input != null){
            try {
                input.close();
            } catch (IOException e) {

            }
        }
        if(output != null){
            try {
                output.close();
            } catch (IOException e) {

            }
        }
        if(!sendingPool.isTerminated() || !sendingPool.isShutdown()){
            sendingPool.shutdown();
            try {
                sendingPool.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                sendingPool.shutdownNow();
            }
        }

        if(!receivingPool.isTerminated() || !receivingPool.isShutdown()){
            receivingPool.shutdown();
            try {
                receivingPool.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                receivingPool.shutdownNow();
            }
        }
    }

    public abstract void rcvCmd(Packet o);

    public void sendCmd(Packet o){
        Runnable task = ()->{
            try{
                if(socket.isClosed() ||socket.isOutputShutdown()) return;
                synchronized (lock){
                    try {
                        output.writeObject(o);
                        output.flush();
                    }
                    catch(SocketException se){
                        System.out.println("Couldn't send");
                        clean();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        sendingPool.submit(task);
    }

    public void sendCmd(Long num){
        Packet packet = new Packet(Packet.Type_Of_Packet.ID, String.valueOf(num).getBytes());
        sendCmd(packet);
    }
}

