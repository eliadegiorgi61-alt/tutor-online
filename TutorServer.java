import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
public class TutorServer {
 public static void main(String[] a) throws Exception {
  var s = HttpServer.create(new InetSocketAddress(8080),0);
  s.createContext("/", e->{
   var html = """
   <html><head><meta name="google-site-verification" content="XJpj2uOWdgSI0OLUZCetaPH-z9KfVVddObUjV71W6Dw" />
   <title>Tutor Online - Ripetizioni e Aiuto Compiti</title>
   <meta name="description" content="Tutor Online: aiuto compiti, ripetizioni di matematica, italiano, inglese per ragazzi.">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   </head><body>
   <h1>Tutor Online 📚</h1>
   <p>Ripetizioni online e aiuto compiti per studenti!</p>
   <p>Presto online con tante materie.</p>
   </body></html>
   """;
   var b=html.getBytes();
   e.getResponseHeaders().add("Content-Type","text/html; charset=UTF-8");
   e.sendResponseHeaders(200,b.length);
   e.getResponseBody().write(b);
   e.getResponseBody().close();
  });
  s.createContext("/sitemap.xml", e->{
var xml = """
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
  <url><loc>https://tutor-online-e5r1.onrender.com/</loc><priority>1.0</priority></url>
</urlset>
""";
var b2=xml.getBytes();
e.getResponseHeaders().add("Content-Type","application/xml");
e.sendResponseHeaders(200,b2.length);
e.getResponseBody().write(b2);
e.getResponseBody().close();
});
  s.start();
 }
 
}
