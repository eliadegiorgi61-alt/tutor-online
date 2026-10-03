import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            try { port = Integer.parseInt(envPort); } catch (Exception e) {}
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", (HttpExchange t) -> {
            String html = "<!DOCTYPE html><html lang=\"it\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\"><title>Tutor Online - Ripetizioni Brindisi</title><script src=\"https://cdn.tailwindcss.com\"></script></head><body style=\"font-family:sans-serif;text-align:center;padding:50px\"><h1 style=\"font-size:40px;font-weight:800\">Tutor<span style=\"color:#4f46e5\">Online</span> e LIVE</h1><p>Matematica, Inglese, Informatica - Brindisi e Online</p><p style=\"margin-top:20px\"><a href=\"/\" style=\"background:black;color:white;padding:12px 20px;border-radius:20px;text-decoration:none\">Prenota 20 euro/h</a></p><footer style=\"margin-top:50px;color:#aaa;font-size:12px\">2026 TutorOnline</footer></body></html>";
            t.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            byte[] bytes = html.getBytes("UTF-8");
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + port);
    }
}
