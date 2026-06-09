import java.net.*;
import java.io.*;
import java.nio.file.*;

public class MultiHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Multi-request HTTP server ready on port 35000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream rawOut = clientSocket.getOutputStream();
             PrintWriter out = new PrintWriter(rawOut, true)) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            System.out.println("Request: " + requestLine);

            while (in.ready()) in.readLine();

            String[] parts = requestLine.split(" ");
            String path = (parts.length > 1) ? parts[1] : "/";
            if (path.equals("/")) path = "/index.html";

            File file = new File("." + path);
            if (!file.exists() || !file.isFile()) {
                String body = "<h1>404 Not Found</h1>";
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println("Content-Length: " + body.length());
                out.println("Connection: close");
                out.println();
                out.println(body);
                return;
            }

            String contentType = getContentType(path);
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-Type: " + contentType + "\r\n");
            out.print("Content-Length: " + fileBytes.length + "\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");
            out.flush();
            rawOut.write(fileBytes);
            rawOut.flush();

        } catch (IOException e) {
            System.err.println("Error handling request: " + e.getMessage());
        }
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css"))  return "text/css";
        if (path.endsWith(".js"))   return "application/javascript";
        if (path.endsWith(".png"))  return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif"))  return "image/gif";
        if (path.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }
}
