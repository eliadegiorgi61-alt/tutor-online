import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.file.*;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", exchange -> {
            try {
                String html = """
                <!DOCTYPE html>
                <html lang="it">
                <head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>TutorOnline</title>
                <style>
                body{font-family:sans-serif; text-align:center; padding:80px 20px; margin:0; background:#fff}
                h1{font-size:44px; margin:0} h1 span{color:#4f46e5}
                p{color:#555; font-size:18px; margin-top:15px}
                </style></head>
                <body>
                <h1>Tutor<span>Online</span> e' <br>LIVE</h1>
                <p>Matematica, Inglese, Informatica -<br>Brindisi e Online</p>
                <p style="margin-top:50px; color:#aaa; font-size:14px">2026 TutorOnline</p>
                </body></html>
                """;
                byte[] bytes = html.getBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.getResponseBody().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        server.start();
        System.out.println("Server LIVE on port " + port);
    }
}
