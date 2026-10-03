import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class TutorServer {
 public static void main(String[] args) throws Exception {
  HttpServer s = HttpServer.create(new InetSocketAddress(8080), 0);
  s.createContext("/", e -> {
   String path = e.getRequestURI().getPath();
   
   // SITEMAP
   if ("/sitemap.xml".equals(path)) {
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n  <url><loc>https://tutor-online-e5r1.onrender.com/</loc><priority>1.0</priority></url>\n</urlset>";
    byte[] b = xml.getBytes();
    e.getResponseHeaders().add("Content-Type", "application/xml");
    e.sendResponseHeaders(200, b.length);
    e.getResponseBody().write(b);
    e.getResponseBody().close();
    return;
   }
   
   if (!"/".equals(path)) {
    String nf = "Not Found";
    e.sendResponseHeaders(404, nf.length());
    e.getResponseBody().write(nf.getBytes());
    e.getResponseBody().close();
    return;
   }

   String html = "<!DOCTYPE html><html><body><h1>Tutor Online</h1><p>Benvenuto su tutor-online</p></body></html>";
   byte[] b = html.getBytes();
   e.getResponseHeaders().add("Content-Type", "text/html");
   e.sendResponseHeaders(200, b.length);
   e.getResponseBody().write(b);
   e.getResponseBody().close();
  });
  s.start();
 }
}
