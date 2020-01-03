package Kamil215691.ZPO.LAB11.Zad1;

public class ClientApp {
    public static void main(String ... args) throws Exception {
        if(args.length != 2) throw new UnsupportedOperationException("Too few arguments");
        ClientCyclist cyclist = new ClientCyclist();
        cyclist.setName(args[0]);
        cyclist.setLastName(args[1]);
        cyclist.login();
    }
}
