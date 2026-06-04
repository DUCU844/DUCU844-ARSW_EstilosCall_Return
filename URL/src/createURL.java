import java.net.MalformedURLException;
import java.net.URL;

public class createURL{

    public static void main(String[] args) {
        try {
            URL escuelaSite = new URL("https://escuelaing.s3.amazonaws.com/production/documents/RR13-2025_-_Derechos_de_Matr%C3%ADcua_2026_para_los_Programas_de_Pregrado.pdf?AWSAccessKeyId=AKIAWFY3NGTFJHVI634A&Signature=Zp%2BWV9j33PgL%2BqKePzGDCY37XkM%3D&Expires=1783006504");

            System.out.println("Protocol: " + escuelaSite.getProtocol());
            System.out.println("Authority: " + escuelaSite.getAuthority());
            System.out.println("Host: " + escuelaSite.getHost());
            System.out.println("Port: " + escuelaSite.getDefaultPort());
            System.out.println("Path: " + escuelaSite.getPath());
            System.out.println("Query: " + escuelaSite.getQuery());
            System.out.println("File: " + escuelaSite.getFile());
            System.out.println("Ref: " + escuelaSite.getRef());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}