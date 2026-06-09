import java.io.*;
import java.net.*;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Square server ready on port 35000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                int number = Integer.parseInt(inputLine.trim());
                int square = number * number;
                System.out.println("Received: " + number + " -> Square: " + square);
                out.println(square);
            }

            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
