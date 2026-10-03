import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String p = System.getenv("PORT");
        if (p != null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", (HttpExchange t) -> {
            String html = "<h1 style='font-family:sans-serif;text-align:center;margin-top:50px'>Tutor Online LIVE - Server funziona! ✅<br><br><a href='/'>Refresh</a></h1>";
            t.getResponseHeaders().add("Content-Type", "text/html");
            t.sendResponseHeaders(200, html.length());
            OutputStream os = t.getResponseBody();
            os.write(html.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
        System.out.println("Live on " + port);
    }
}
