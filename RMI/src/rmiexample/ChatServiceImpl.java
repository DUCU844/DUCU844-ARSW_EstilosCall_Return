package rmiexample;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServiceImpl implements IChatService {
    private final String name;

    public ChatServiceImpl(String name, int port) throws RemoteException {
        this.name = name;
        IChatService stub = (IChatService) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("ChatService", stub);
        System.out.println("[" + name + "] Chat service published on port " + port);
    }

    @Override
    public void receiveMessage(String from, String message) throws RemoteException {
        System.out.println("[" + from + "]: " + message);
    }
}
