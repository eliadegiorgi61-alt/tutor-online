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
                String uriPath = exchange.getRequestURI().getPath();
                if (uriPath.equals("/")) uriPath = "/index.html";
                
                // Prova 2 posti diversi dove Render mette i file
                File file = new File("." + uriPath);
                if (!file.exists()) file = new File(uriPath.substring(1));
                
                if (file.exists() && !file.isDirectory()) {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String contentType = "text/html";
                    if (uriPath.endsWith(".css")) contentType = "text/css";
                    if (uriPath.endsWith(".js")) contentType = "application/javascript";
                    exchange.getResponseHeaders().set("Content-Type", contentType);
                    exchange.sendResponseHeaders(200, bytes.length);
                    exchange.getResponseBody().write(bytes);
                } else {
                    // Se non trova il file, mostra index.html comunque
                    File index = new File("index.html");
                    if (index.exists()) {
                        byte[] bytes = Files.readAllBytes(index.toPath());
                        exchange.getResponseHeaders().set("Content-Type", "text/html");
                        exchange.sendResponseHeaders(200, bytes.length);
                        exchange.getResponseBody().write(bytes);
                    } else {
                        String msg = "File non trovato: " + uriPath + " - Files in dir: " + String.join(",", new File(".").list());
                        exchange.sendResponseHeaders(404, msg.length());
                        exchange.getResponseBody().write(msg.getBytes());
                    }
                }
            } catch (Exception e) {
                String err = "Errore: " + e.getMessage();
                exchange.sendResponseHeaders(500, err.length());
                exchange.getResponseBody().write(err.getBytes());
            } finally {
                exchange.close();
            }
        });
        
        server.start();
        System.out.println("Server OK su porta " + port);
    }
}
