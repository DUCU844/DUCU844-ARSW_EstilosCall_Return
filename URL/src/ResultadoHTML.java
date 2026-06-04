import java.util.Scanner;
import java.net.*;
import java.io.*;

public class ResultadoHTML {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Write address to consult: ");
        String url = scanner.nextLine();
        System.out.println("Address is: " + url);

        try {
            FileWriter writer = new FileWriter("resultado.html");
            URL query = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(query.openStream()));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null){
                writer.write(inputLine);
            }
            writer.close();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException i){
            i.printStackTrace();
        }
        scanner.close();

    }

}
