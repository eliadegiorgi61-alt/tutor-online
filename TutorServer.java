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
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V10000</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#ff00ff,#00ff88);color:#000;padding:14px;text-align:center;font-weight:900}.v{background:#000;color:#ff00ff;padding:3px 8px;border-radius:10px;font-size:10px}#c{height:75vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap}.u{background:#ff00ff;color:#fff;margin-left:auto;max-width:88%}.b{background:#111;border-left:4px solid #ff00ff}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#ff00ff;color:#fff;border:none;padding:12px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}</style></head><body><div class=top>TutorOnline V10000 <span class=v>GOD 10000X</span></div><div id=c><div class='msg b'>V10000 GOD ATTIVA - Oltre umano</div></div><div class=row><input id=q placeholder='Scrivi o foto compito...'><button onclick=s()>></button></div><div class=row><button onclick=document.getElementById('f').click() style='flex:1;background:#111;color:#ff00ff;border:1px solid #ff00ff'>FOTO</button><button onclick=ver() style='flex:1;background:#111;color:#fff;border:1px solid #333'>Verifica</button></div><input id=f type=file accept='image/*' style='display:none'><script>function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('V10000...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;}catch(e){b.innerText='Errore';}}function ver(){var m=prompt('Materia?');if(m){document.getElementById('q').value='Verifica su '+m;s();}}document.getElementById('f').onchange=function(){a('[Foto]','u');var b=a('V10000 vede foto...','b');setTimeout(async function(){var r=await fetch('/ask?d=foto compito');var t=await r.text();b.innerText=t;},500);}</script></body></html>";
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
                risp = "V10000 - ZIGURAT da 10:\nUr 2112 a.C. 7 terrazze.\nTrucco: ZIG ZAG RAT verso cielo.\nZigurat=VIVA, Piramide=MORTA";
            } else if(low.contains("foto")){
                risp = "V10000 FOTO: Ho visto compito.\nDef da 10 + trucco + 3 domande verifica.\nDimmi cosa c e scritto e ti risolvo";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode("Sei prof spiega da 10: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>20) risp = "V10000:\n"+r.body();
                    else risp = "V10000 su "+dom+": da 10";
                }catch(Exception e){ risp = "V10000 su "+dom+": da 10"; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
