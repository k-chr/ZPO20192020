package Kamil215691.ZPO.Kolokwium._2;

public class ClientMain {
    public static void main(String ...args){
        Client client = new Client();
        if(args.length <= 0){
            System.out.println("Lack of provided argument");
            System.exit(1);
        }
        String s = args[0];

        System.out.println("Waiting for server response....");
        client.tryToLogin(s);
    }
}
