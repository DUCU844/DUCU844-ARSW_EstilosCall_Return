import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SquareClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket clientSocket = null;

        try{
            clientSocket = new Socket( "localhost", 35000);
        } catch (IOException e ){
            System.err.println("Could not lister port: 35000");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        int number = scanner.nextInt();
        out.println(number);

        String result= in.readLine();

        System.out.println(result);

        out.close();
        in.close();
        clientSocket.close();
        scanner.close();
    }
}