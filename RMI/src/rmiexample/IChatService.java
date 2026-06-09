package rmiexample;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatService extends Remote {
    void receiveMessage(String from, String message) throws RemoteException;
}
