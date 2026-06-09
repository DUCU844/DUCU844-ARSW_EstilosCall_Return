import java.io.*;
import java.net.*;

public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35001);
        System.out.println("Trig server ready on port 35001 (default: cos)...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String currentFunction = "cos";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                currentFunction = inputLine.substring(4).trim();
                System.out.println("Function changed to: " + currentFunction);
                out.println("OK: function is now " + currentFunction);
            } else {
                try {
                    double number = Double.parseDouble(inputLine.trim());
                    double result;
                    switch (currentFunction) {
                        case "sin": result = Math.sin(number); break;
                        case "tan": result = Math.tan(number); break;
                        default:    result = Math.cos(number); break;
                    }
                    System.out.println(currentFunction + "(" + number + ") = " + result);
                    out.println(result);
                } catch (NumberFormatException e) {
                    out.println("Error: invalid number");
                }
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
