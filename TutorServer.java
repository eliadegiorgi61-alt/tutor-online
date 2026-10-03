import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            port = Integer.parseInt(envPort);
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", (HttpExchange t) -> {
            String html = "<html><body style='font-family:Arial;text-align:center;padding:50px'><h1>Tutor Online LIVE</h1><p>Server funziona!</p></body></html>";
            t.getResponseHeaders().add("Content-Type", "text/html");
            t.sendResponseHeaders(200, html.length());
            OutputStream os = t.getResponseBody();
            os.write(html.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Started on " + port);
    }
}
