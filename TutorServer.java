import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.file.*;
public class TutorServer{
public static void main(String[]args)throws Exception{
int port=Integer.parseInt(System.getenv().getOrDefault("PORT","10000"));
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",e->{
String path=e.getRequestURI().getPath();
try{
if(path.contains("sitemap")){
String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"><url><loc>https://tutor-online-e5r1.onrender.com/</loc></url></urlset>";
byte[] b=xml.getBytes(); e.getResponseHeaders().add("Content-Type","application/xml"); e.sendResponseHeaders(200,b.length); e.getResponseBody().write(b); e.getResponseBody().close(); return;
}
if(path.contains("robots")){
String txt="User-agent: *\nAllow: /\nSitemap: https://tutor-online-e5r1.onrender.com/sitemap.xml";
byte[] b=txt.getBytes(); e.getResponseHeaders().add("Content-Type","text/plain"); e.sendResponseHeaders(200,b.length); e.getResponseBody().write(b); e.getResponseBody().close(); return;
}
Path p=Path.of("index.html"); String html=Files.exists(p)?Files.readString(p):"<h1>Tutor Online</h1>"; if(html.isEmpty()) html="<h1>Tutor Online</h1>";
byte[] b=html.getBytes(); e.getResponseHeaders().add("Content-Type","text/html; charset=utf-8"); e.sendResponseHeaders(200,b.length); e.getResponseBody().write(b); e.getResponseBody().close();
}catch(Exception ex){ try{ String m="Error"; e.sendResponseHeaders(500,m.length()); e.getResponseBody().write(m.getBytes()); e.getResponseBody().close(); }catch(Exception ignore){} }
}); s.start(); System.out.println("Server started "+port);
}
}
