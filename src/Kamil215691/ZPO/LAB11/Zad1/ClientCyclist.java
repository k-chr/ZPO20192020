package Kamil215691.ZPO.LAB11.Zad1;

import java.net.Socket;
import java.util.Random;
//import com.sun.jna.*;
//import com.sun.jna.platform.win32.WinDef.*;
//import com.sun.jna.platform.win32.WinNT.HANDLE;
public class ClientCyclist {

    private final String cyclist  = "o&o";
    private String route = "S                   |       |         |                |           |           |      M";
    private Credentials.Identification thisIsMeTheCyclist;
    private volatile CommunicationHandler handler;
    public void setName(String name){
        thisIsMeTheCyclist.setName(name);
    }

    public void setLastName(String lastName){
        thisIsMeTheCyclist.setLastName(lastName);
    }
    public void login(){
        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setType(MessageToSend.TypeOfMessage.LOGIN);
        messageToSend.setContent(thisIsMeTheCyclist);
        handler.sendMessage(messageToSend);
    }
    public ClientCyclist() throws Exception{
//        if(System.getProperty("os.name").startsWith("Windows"))
//        {
//            // Set output mode to handle virtual terminal sequences
//            Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
//            DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
//            HANDLE hOut = (HANDLE)GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});
//
//            DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
//            Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
//            GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});
//
//            int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
//            DWORD dwMode = p_dwMode.getValue();
//            dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
//            Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
//            SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
//        }
        thisIsMeTheCyclist = new Credentials.Identification();
        Socket socket = new Socket("127.0.0.1", 2137);
        try {
            handler = new CommunicationHandler(socket) {

                @Override
                public MessageToSend createMessage(Credentials.Stage o) {

                    MessageToSend messageToSend = new MessageToSend();
                    switch (o) {

                        case START:
                            break;
                        case FIRST_IS:

                        case SECOND_IS:

                        case THIRD_IS:

                        case FOURTH_IS:

                        case FIFTH_IS:

                        case SIXTH_IS:

                        case FINNISH:
                            messageToSend.setType(MessageToSend.TypeOfMessage.UPDATE);
                            messageToSend.setContent(o);
                            break;
                    }
                    return messageToSend;
                }

                @Override
                protected void rcvCmd(MessageToSend command) {
                    switch (command.getType()) {
                        case START:
                            runRace();
                            break;
                        case LOGIN:
                        case UPDATE:
                            break;
                        case END:
                            Integer finalPosition = (Integer) command.getContent();
                            System.out.println("\n\nI am " + thisIsMeTheCyclist.getName() + " " + thisIsMeTheCyclist.getLastName() + " and I took the " + finalPosition + (finalPosition == 1 ? "st" : finalPosition == 2 ? "nd" : "rd") + " place in the race!");
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                MessageToSend m = new MessageToSend();
                                m.setType(MessageToSend.TypeOfMessage.END);
                                sendMessage(m);
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                setShouldExit();
                                cleanUp();
                            }).start();
                            break;
                    }
                }
            };
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runRace() {
        Random random = new Random();
        int i = 0;
        int is = 0;
        for(;i < route.length() ; ++i){
            System.out.printf("%s\r",route );
            if(i < cyclist.length())
                System.out.printf("%s", cyclist.substring(cyclist.length() - (i+1), 3));
            else{
                System.out.printf("%s",route.substring(0, i-cyclist.length()+1) +cyclist);
            }
            if(i < route.length() && route.charAt(i) == '|'){
                is+=2;
                System.out.print(route.substring(i+1));
                System.out.printf(" Reached an IS No. %d\r", (is/2));
                handler.sendMessage(Credentials.Stage.valueOf(0xFFFFFFFF - is));
            }
            else{
                System.out.print(route.substring(i+1) + "                        \r");
            }
            if(i < route.length() && route.charAt(i) == 'M'){
                System.out.print("\nReached a FINNISH\r");
                handler.sendMessage(Credentials.Stage.FINNISH);
            }
            try {
                Thread.sleep(10*(random.nextInt(13) + 3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
