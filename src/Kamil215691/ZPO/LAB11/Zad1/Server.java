package Kamil215691.ZPO.LAB11.Zad1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    final Object lock = new Object();
    private ArrayList<CommunicationHandler> handlers = new ArrayList<>();
    private Boolean[] states = new Boolean[]{false,false,false};
    private ConcurrentHashMap<Credentials.Stage, ConcurrentLinkedQueue<Credentials.Identification>> stageMap = new ConcurrentHashMap<>();
    private final int countOfPeers = 3;
    private ServerSocket serverSocket = new ServerSocket(2137);
    private AtomicInteger counter = new AtomicInteger(0);
    public Server() throws Exception {
        while(counter.get() < countOfPeers){
            Socket socket = serverSocket.accept();
            processSocket(socket);
        }
        while(!Arrays.stream(states).allMatch(state -> state)){

        }
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private synchronized void processSocket(Socket socket) {
        counter.incrementAndGet();
        Runnable task = () -> {
            try {

                final CommunicationHandler handler = new CommunicationHandler(socket) {
                    final AtomicBoolean set = new AtomicBoolean(false);

                    private Credentials.Identification client = null;
                    @Override
                    public synchronized MessageToSend createMessage(Credentials.Stage o) {
                        MessageToSend messageToSend = new MessageToSend();
                        switch (o) {
                            case START: {
                                messageToSend.setType(MessageToSend.TypeOfMessage.START);
                                break;
                            }
                            case FIRST_IS:
                            case SECOND_IS:
                            case THIRD_IS:
                            case FOURTH_IS:
                            case FIFTH_IS:
                            case SIXTH_IS:
                                break;
                            case FINNISH: {
                                messageToSend.setType(MessageToSend.TypeOfMessage.END);
                                ConcurrentLinkedQueue<Credentials.Identification> queue = new ConcurrentLinkedQueue<>( stageMap.get(Credentials.Stage.FINNISH));
                                int pos = 1;
                                for(Credentials.Identification id = queue.poll(); queue.size() > 0 && id != null && (!id.getLastName().equals(client.getLastName()) || !id.getName().equals(client.getName())); id = queue.poll(), ++pos) {
                                }
                                messageToSend.setContent(pos);
                                break;
                            }
                        }
                        return messageToSend;
                    }

                    @Override
                    protected void rcvCmd(MessageToSend command) {
                        switch (command.getType()) {
                            case START:
                                break;
                            case LOGIN:
                                client = (Credentials.Identification) command.getContent();
                                System.out.println(client.getName() + " " + client.getLastName() + " has joined T H E  R A C E");
                                set.set(true);
                                int idx = handlers.indexOf(this);
                                states[idx] = true;
                                synchronized (lock) {
                                    try {
                                        lock.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                sendMessage(Credentials.Stage.START);
                                break;
                            case UPDATE:
                                synchronized (lock) {
                                    Credentials.Stage stage = (Credentials.Stage) command.getContent();
                                    switch (stage) {
                                        case FIRST_IS:
                                        case SECOND_IS:
                                        case THIRD_IS:
                                        case FOURTH_IS:
                                        case FIFTH_IS:
                                        case SIXTH_IS:
                                            if(!stageMap.containsKey(stage)){
                                                System.out.println(client.getName() + " " + client.getLastName() + " has won the Intermediate Sprint No. " + ((~stage.getValue()) >> 1));
                                                stageMap.put(stage, new ConcurrentLinkedQueue<>(Arrays.asList(client)));
                                            }
                                            else{
                                                stageMap.get(stage).add(client);
                                            }
                                            break;
                                        case FINNISH:
                                            if(!stageMap.containsKey(stage)){
                                                System.out.println(client.getName() + " " + client.getLastName() + " has won the race");
                                                stageMap.put(stage, new ConcurrentLinkedQueue<>(Arrays.asList(client)));
                                            }
                                            else{
                                                stageMap.get(stage).add(client);
                                            }
                                            sendMessage(stage);
                                            break;
                                    }
                                    break;
                                }
                            case END:
                                setShouldExit();
                                cleanUp();
                                break;
                        }
                    }
                };
                handlers.add(handler);
            } catch (Exception ignored) {
            }

        };
        new Thread(task).start();
    }
}
