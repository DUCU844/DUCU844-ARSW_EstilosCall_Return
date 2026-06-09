import java.io.IOException;
import java.net.*;
import java.util.logging.*;

public class DatagramTimeClient {
    private static final int SERVER_PORT = 4445;
    private static final int POLL_INTERVAL_MS = 5000;
    private static final int TIMEOUT_MS = 1000;

    public static void main(String[] args) {
        String lastKnownTime = "No time received yet";

        while (true) {
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.setSoTimeout(TIMEOUT_MS);

                byte[] sendBuf = new byte[256];
                InetAddress address = InetAddress.getByName("127.0.0.1");
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, SERVER_PORT);
                socket.send(sendPacket);

                byte[] recvBuf = new byte[256];
                DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(recvPacket);

                lastKnownTime = new String(recvPacket.getData(), 0, recvPacket.getLength());
                System.out.println("Server time: " + lastKnownTime);

                socket.close();
            } catch (SocketTimeoutException e) {
                System.out.println("Server not responding. Last known time: " + lastKnownTime);
            } catch (IOException ex) {
                Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
