import com.sun.net.httpserver.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String p = System.getenv("PORT");
        if (p!= null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", ex -> {
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V159</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:#00ff88;color:#000;padding:12px;text-align:center;font-weight:900}#c{height:70vh;overflow-y:auto;background:#0a0a0a;padding:10px;border-radius:12px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:12px;white-space:pre-wrap}.u{background:#00ff88;color:#000;margin-left:auto;max-width:85%}.b{background:#111;border-left:3px solid #00ff88}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#00ff88;color:#000;border:none;padding:12px 16px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}</style></head><body><div class=top>TutorOnline V159 - 159X</div><div id=c><div class='msg b'>V159 LIVE - Chiedi: zigurat</div></div><div class=row><input id=q placeholder='Scrivi...'><button onclick=s()>></button></div><script>function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;}catch(e){b.innerText='Errore';}}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.createContext("/ask", ex -> {
            String qs = ex.getRequestURI().getQuery();
            String dom = "";
            if(qs!=null) for(String s: qs.split("&")) if(s.startsWith("d=")) dom=URLDecoder.decode(s.substring(2), StandardCharsets.UTF_8);
            String low = dom.toLowerCase();
            String risp;
            if(low.contains("zigurat")){
                risp = "V159 - ZIGURAT da 10:\nTempli a gradoni Mesopotamia 2100 a.C.\n3-7 terrazze, tempio dio in cima.\nEs: Ur 60x45m.\nDiff: Zigurat=tempio vivo, Piramide=tomba.\nTrucco: ZIG ZAG RAT verso cielo";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode("Sei prof italiano spiega semplice: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>20) risp = r.body();
                    else risp = "V159 su "+dom+": definizione + esempio + trucco";
                }catch(Exception e){ risp = "V159 su "+dom+": spiegazione da 10"; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
