package rmiexample;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Your name: ");
        String name = scanner.nextLine();

        System.out.print("Port to publish your service on: ");
        int ownPort = Integer.parseInt(scanner.nextLine().trim());

        ChatServiceImpl service = new ChatServiceImpl(name, ownPort);

        System.out.print("Remote IP to connect to: ");
        String remoteIp = scanner.nextLine().trim();

        System.out.print("Remote port to connect to: ");
        int remotePort = Integer.parseInt(scanner.nextLine().trim());

        Registry remoteRegistry = LocateRegistry.getRegistry(remoteIp, remotePort);
        IChatService remote = (IChatService) remoteRegistry.lookup("ChatService");

        System.out.println("Connected! Type messages (empty line to quit):");

        String line;
        while ((line = scanner.nextLine()) != null && !line.isEmpty()) {
            remote.receiveMessage(name, line);
        }

        scanner.close();
    }
}
