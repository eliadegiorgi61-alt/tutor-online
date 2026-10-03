import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", exchange -> {
            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>Tutor Online</title><style>body{margin:0;font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);min-height:100vh;display:flex;align-items:center;justify-content:center;color:white;text-align:center} .box{background:rgba(255,255,255,0.15);padding:40px;border-radius:20px;backdrop-filter:blur(10px)} h1{font-size:48px;margin:0} p{font-size:20px} a{display:inline-block;margin-top:20px;padding:15px 30px;background:white;color:#764ba2;border-radius:30px;text-decoration:none;font-weight:bold}</style></head><body><div class='box'><h1>🎓 Tutor Online</h1><p>Il tuo sito è ONLINE!</p><p>Funziona alla grande!</p><a href='/'>Aggiorna</a><br><br><small>tutor-online-e5r1.onrender.com</small></div></body></html>";
            byte[] bytes = html.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });
        server.start();
        System.out.println("Server OK porta " + port);
    }
}
