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

        server.createContext("/manifest.json", ex -> {
            String j = "{\"name\":\"TutorOnline V6\",\"short_name\":\"TutorV6\",\"start_url\":\"/\",\"display\":\"standalone\",\"background_color\":\"#000\",\"theme_color\":\"#00ff88\"}";
            ex.getResponseHeaders().set("Content-Type","application/json");
            ex.sendResponseHeaders(200, j.length());
            ex.getResponseBody().write(j.getBytes());
            ex.close();
        });

        server.createContext("/sw.js", ex -> {
            String sw = "self.addEventListener('install',e=>self.skipWaiting());";
            ex.getResponseHeaders().set("Content-Type","text/javascript");
            ex.sendResponseHeaders(200, sw.length());
            ex.getResponseBody().write(sw.getBytes());
            ex.close();
        });

        server.createContext("/", ex -> {
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V6</title><link rel='manifest' href='/manifest.json'><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}h1{text-align:center}span{color:#00ff88}.card{background:#111;padding:14px;border-radius:18px;max-width:700px;margin:0 auto;border:1px solid #222}#c{height:50vh;overflow-y:auto;background:#0a0a0a;border-radius:12px;padding:10px;border:1px solid #222}.msg{margin:6px 0;padding:10px;border-radius:12px;font-size:14px;white-space:pre-wrap}.u{background:#00ff88;color:#000;margin-left:auto;max-width:80%}.b{background:#1a1a1a;border-left:3px solid #00ff88}input{flex:1;padding:12px;border-radius:10px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:black;border:none;border-radius:10px;padding:12px;font-weight:900}.row{display:flex;gap:6px;margin-top:8px}</style></head><body><h1>Tutor<span>Online</span> V6 APP</h1><div class='card'><div id='c'><div class='msg b'>V6 APP installabile pronta!\n- Installa: condividi > Aggiungi a Home\n- Piu forte di ChatGPT per scuola italiana</div></div><div class='row'><input id='q' placeholder='Chiedi...'><button onclick='s()'>></button></div><div class='row'><button onclick='mic()' style='background:#111;color:white;border:1px solid #333'>Mic</button><button onclick='ver()' style='background:#111;color:#00ff88;border:1px solid #333'>Verifica</button></div></div><script>if('serviceWorker' in navigator)navigator.serviceWorker.register('/sw.js');function a(t,c){let d=document.getElementById('c');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){let i=document.getElementById('q');let d=i.value;if(!d)return;a(d,'u');i.value='';let b=a('V6 pensa...','b');let r=await fetch('/ask?d='+encodeURIComponent(d));let txt=await r.text();b.innerText=txt;let u=new SpeechSynthesisUtterance(txt.slice(0,250));u.lang='it-IT';speechSynthesis.speak(u);}function mic(){let rec=new(window.webkitSpeechRecognition||window.SpeechRecognition)();rec.lang='it-IT';rec.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;s();};rec.start();}function ver(){let d=prompt('Su cosa?');if(d){document.getElementById('q').value='Crea verifica su '+d;s();}}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String qs = ex.getRequestURI().getQuery();
            String dom = "";
            if(qs!=null) for(String part: qs.split("&")) if(part.startsWith("d=")) dom=URLDecoder.decode(part.substring(2), StandardCharsets.UTF_8);
            String low = dom.toLowerCase();
            String risp;
            if(low.contains("guerra") && low.contains("2")) risp="V6 APP - 2a Guerra: 1-9-1939 invasione Polonia, fine 2-9-1945. Trucco 1-9-39. Vuoi verifica?";
            else if(low.contains("verifica")) risp="VERIFICA V6 su "+dom+": 1) Definisci 2) Esempio 3) Perche 4) Trucco 5) Collegamento";
            else {
                try{
                    HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
                    String pr = URLEncoder.encode("Sei TutorOnline V6, tutor italiano. Spiega con trucco: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/"+pr)).timeout(Duration.ofSeconds(8)).GET().build();
                    var r = c.send(req, HttpResponse.BodyHandlers.ofString());
                    risp = (r.statusCode()==200 && r.body().length()>15)? "V6:\n"+r.body() : "V6 su "+dom+": spiego a step con trucco.";
                }catch(Exception e){ risp="V6 su "+dom; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
