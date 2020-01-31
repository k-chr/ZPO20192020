package Kamil215691.ZPO.Kolokwium._2;

import com.sun.jna.platform.win32.Guid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 2137;
    private static final String USER_DIRECTORY = "D:\\Kolokwium";
    private Socket socket = null;
    private CommunicationHandler handler = null;
    private String fileName = "";
    private int id = -1;
    private int packetsToGet = 0;
    private int currentPackage = 0;
    private void cleanUp(){
        if(handler != null){
            handler.clean();
        }
    }
    public Client(){
        File file = new File (USER_DIRECTORY);
        file.mkdir();
        System.out.println(file.canRead());
    }
    FileOutputStream os = null;
    public void tryToLogin(String s) {
        fileName = s;
        try {
            Socket socket = new Socket(IP_ADDRESS, PORT);
            this.socket = socket;
            handler = new CommunicationHandler(socket) {
                @Override
                public void rcvCmd(Packet o) {
                    switch(o.getType()){
                        case COMMAND:{
                            String rcv = new String(o.getPkg());
                            if(rcv.matches("[0-9]+")){
                                packetsToGet = Integer.parseInt(rcv);
                                System.out.println("Packets to get "+ packetsToGet);
                            }
                            else{
                                System.out.println(rcv);
                                cleanUp();
                                try {
                                    wait(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.exit(0);
                            }
                            break;
                        }
                        case FILE_PACKET: {
                            if (os == null) {
                                System.out.println("Prepare to save file");
                                System.out.println(fileName);
                                try {
                                    fileName = Guid.GUID.newGuid().toGuidString() + fileName;

                                    os = new FileOutputStream(USER_DIRECTORY + "\\" + fileName);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                            ++currentPackage;
                            System.out.println("Current package: " + currentPackage + " of " + packetsToGet);
                            try {
                                os.write(o.getPkg());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (currentPackage >= packetsToGet) {
                                sendCmd(new Packet(Packet.Type_Of_Packet.CLOSE_FILE, null));
                                try {
                                    wait(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    System.out.println("All file contents have been saved on disk");
                                    rcvCmd(new Packet(Packet.Type_Of_Packet.CLOSE_FILE, null));
                                }
                            }

                            break;
                        }
                        case ID :{
                            id = Integer.parseInt(new String(o.getPkg()));
                            System.out.println("Client connected with id = " + id);
                            handler.sendCmd(new Packet(Packet.Type_Of_Packet.COMMAND, fileName.getBytes()));
                            break;
                        }
                        case CLOSE_FILE:{
                            System.out.println("I'm closing file");
                            try {
                                os.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            cleanUp();
                            try {
                                wait(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);

                            break;
                        }
                    }
                }
            };
            handler.getMessage();
        } catch (IOException e) {
            System.out.println("Couldn't connect");
        }
    }
}
