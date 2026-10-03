import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class TutorServer {
 public static void main(String[] args) throws Exception {
  HttpServer s = HttpServer.create(new InetSocketAddress(8080), 0);
  s.createContext("/", e -> {
   String path = e.getRequestURI().getPath();
   
   if (path.contains("sitemap")) {
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"><url><loc>https://tutor-online-e5r1.onrender.com/</loc><priority>1.0</priority></url></urlset>";
    byte[] b = xml.getBytes();
    e.getResponseHeaders().add("Content-Type", "application/xml");
    e.sendResponseHeaders(200, b.length);
    e.getResponseBody().write(b);
    e.getResponseBody().close();
    return;
   }
   
   if (path.contains("robots")) {
    String txt = "User-agent: *\nAllow: /\nSitemap: https://tutor-online-e5r1.onrender.com/sitemap.xml";
    byte[] b = txt.getBytes();
    e.getResponseHeaders().add("Content-Type", "text/plain");
    e.sendResponseHeaders(200, b.length);
    e.getResponseBody().write(b);
    e.getResponseBody().close();
    return;
   }

   if (!path.equals("/")) {
    String nf = "Not Found";
    e.sendResponseHeaders(404, nf.length());
    e.getResponseBody().write(nf.getBytes());
    e.getResponseBody().close();
    return;
   }

   try {
    String html = Files.readString(Path.of("index.html"));
    if (html.isEmpty()) html = "<h1>Tutor Online</h1><p>Benvenuto</p>";
    byte[] b = html.getBytes();
    e.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
    e.sendResponseHeaders(200, b.length);
    e.getResponseBody().write(b);
    e.getResponseBody().close();
   } catch (Exception ex) {
    String html = "<h1>Tutor Online</h1>";
    byte[] b = html.getBytes();
    e.getResponseHeaders().add("Content-Type", "text/html");
    e.sendResponseHeaders(200, b.length);
    e.getResponseBody().write(b);
    e.getResponseBody().close();
   }
  });
  s.start();
  System.out.println("Server started");
 }
}
