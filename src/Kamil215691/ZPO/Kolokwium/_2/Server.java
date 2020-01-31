package Kamil215691.ZPO.Kolokwium._2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;

import java.net.Socket;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Server {

    private static final String HOME_DIRECTORY = "D:\\Project\\Kolokwium";
    private static final int PORT = 2137;
    private static final int USER_LIMIT = 10;
    private static final int PACKAGE_SIZE = 512;
    private boolean stop = false;
    private AtomicLong idGenerator = new AtomicLong(0);
    private ServerSocket server = null;
    private ConcurrentHashMap<Long,CommunicationHandler> handlers = new ConcurrentHashMap<>();
    private ExecutorService service = Executors.newFixedThreadPool(1);

    public Server(){
        File file = new File(HOME_DIRECTORY);
        file.mkdir();
    }
    public void cleanUp(){
        if(server != null){
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Map.Entry<Long, CommunicationHandler> entry : handlers.entrySet()){
            entry.getValue().clean();
        }
    }

    public void startListen() {
        Runnable task = () -> {
            try {
                server = new ServerSocket(PORT);
                while (!stop) {
                    Socket socket = server.accept();
                    processSocket(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        service.submit(task);
    }

    private synchronized void processSocket(Socket socket) {
        Runnable task = ()->{
            CommunicationHandler handler = new CommunicationHandler(socket) {
                private long ID = idGenerator.get();

                @Override
                public synchronized void rcvCmd(Packet o) {
                    if(o.getType() == Packet.Type_Of_Packet.COMMAND) {
                        File f = new File(Server.HOME_DIRECTORY +"\\"+new String(o.getPkg()));
                        FileInputStream fileInputStream = null;
                        try {
                            fileInputStream = new FileInputStream(f);
                        } catch (IOException e) {
                            System.out.println("File not found");
                            fileInputStream = null;
                            Packet packet = new Packet(Packet.Type_Of_Packet.COMMAND, "File not found".getBytes());
                            sendCmd(packet);
                            try {
                                wait(2000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            handlers.get(ID).clean();
                            handlers.remove(ID);
                            return;
                        }
                        int loops = (int) Math.ceil(f.length() / (double) PACKAGE_SIZE);
                        Packet packet1 = new Packet(Packet.Type_Of_Packet.COMMAND, String.valueOf(loops).getBytes());
                        sendCmd(packet1);
                        int currIndex = 0;
                        String filename = new String(o.getPkg());
                        for (int i = 0; i < loops; ++i) {
                            System.out.println("Current packet: " + (i + 1) + " of " + loops);
                            int limit = (int) Math.min(f.length() - currIndex, PACKAGE_SIZE);
                            byte[] bytesToSend = new byte[limit];
                            System.out.println("File: " + filename + " | Sent: " + currIndex + "B/ To send: " + f.length() + "B");
                            try {
                                fileInputStream.read(bytesToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Packet packet = new Packet(Packet.Type_Of_Packet.FILE_PACKET, bytesToSend);
                            sendCmd(packet);
                            currIndex += limit;
                        }

                        System.out.println("File: " + filename + "| Sent: " + f.length() + "B/ To send: " + f.length() + "B");
                    }
                    else if(o.getType() == Packet.Type_Of_Packet.CLOSE_FILE){
                        System.out.println("I'm disconnecting: " + ID);
                        handlers.get(ID).clean();
                        handlers.remove(ID);
                    }
                }
            };
            handler.getMessage();
            handlers.put(idGenerator.get(), handler);
            notifyClientToServe(idGenerator.getAndIncrement(), handler);
        };
        new Thread(task).start();
    }
    private synchronized void notifyClientToServe(long val, CommunicationHandler handler){
        Runnable task = ()-> {
            while (handlers.size() >= USER_LIMIT) ;
            handler.sendCmd(val);
        };
        new Thread(task).start();
    }
}
