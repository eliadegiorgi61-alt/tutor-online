import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", exchange -> {
            try {
                String path = exchange.getRequestURI().getPath();
                if (path.equals("/")) path = "/index.html";
                
                File file = new File("." + path);
                if (!file.exists()) {
                    String notFound = "Not Found";
                    exchange.sendResponseHeaders(404, notFound.length());
                    exchange.getResponseBody().write(notFound.getBytes());
                } else {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String ct = "text/html";
                    if (path.endsWith(".css")) ct = "text/css";
                    if (path.endsWith(".js")) ct = "application/javascript";
                    exchange.getResponseHeaders().set("Content-Type", ct);
                    exchange.sendResponseHeaders(200, bytes.length);
                    exchange.getResponseBody().write(bytes);
                }
            } catch (Exception e) { e.printStackTrace(); }
            finally { exchange.close(); }
        });
        
        server.start();
        System.out.println("Server started on port " + port);
    }
}
