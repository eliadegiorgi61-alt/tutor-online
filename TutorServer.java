import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
public class TutorServer {
 public static void main(String[] a) throws Exception {
  var s = HttpServer.create(new InetSocketAddress(8080),0);
  s.createContext("/", e->{ var b="<h1>AI Fatta!</h1>".getBytes(); e.sendResponseHeaders(200,b.length); e.getResponseBody().write(b); e.close(); });
  s.start();
 }
}