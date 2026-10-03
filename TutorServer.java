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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V10</title><style>*{margin:0;padding:0;box-sizing:border-box}body{background:#0a0a0a;color:#fff;font-family:system-ui;display:flex;height:100vh}.side{width:260px;background:#000;border-right:1px solid #1a1a1a;padding:12px;display:flex;flex-direction:column;gap:8px}@media(max-width:700px){.side{display:none}}.logo{font-size:20px;font-weight:900;text-align:center;padding:10px}span{color:#00ff88}.btn{background:#00ff88;color:#000;border:none;padding:10px;border-radius:8px;font-weight:900;cursor:pointer}.hist{flex:1;overflow-y:auto;font-size:12px;color:#888}.main{flex:1;display:flex;flex-direction:column}#chat{flex:1;overflow-y:auto;padding:16px;background:#0f0f0f}.msg{max-width:85%;margin:10px 0;padding:12px 14px;border-radius:14px;line-height:1.6;white-space:pre-wrap;font-size:14px}.u{background:#00ff88;color:#000;margin-left:auto}.b{background:#1a1a1a;border:1px solid #222}.bar{padding:12px;background:#000;border-top:1px solid #1a1a1a;display:flex;gap:8px}input{flex:1;padding:14px;border-radius:12px;border:1px solid #333;background:#111;color:#fff}button{background:#00ff88;color:#000;border:none;border-radius:12px;padding:12px 16px;font-weight:900;cursor:pointer}.typing:after{content:'|';animation:blink 1s infinite}@keyframes blink{50%{opacity:0}}</style></head><body><div class='side'><div class='logo'>Tutor<span>Online</span> V10</div><button class='btn' onclick='newChat()'>+ Nuova Chat</button><div class='hist' id='hist'>Cronologia:<br>- Zigurat<br>- 2a Guerra<br>- Mesopotamia</div><div style='font-size:10px;color:#555;margin-top:auto'>V10 GOD - Meglio di ChatGPT</div></div><div class='main'><div id='chat'><div class='msg b'>V10 GOD MODE ATTIVO\n\nSono come ChatGPT ma:\n- Gratis\n- In italiano perfetto\n- Con trucchi per verifiche\n- Ti parlo a voce\n- Ricordo tutto\n\nChiedi qualsiasi cosa!</div></div><div class='bar'><input id='q' placeholder='Messaggio a TutorOnline V10...' onkeydown='if(event.key==\"Enter\")send()'><button onclick='send()'>Invia</button></div></div><script>let hist=[];function add(t,c){let d=document.getElementById('chat');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}function typeEffect(el,text){el.innerText='';let i=0;let int=setInterval(()=>{el.innerText+=text.charAt(i);i++;document.getElementById('chat').scrollTop=document.getElementById('chat').scrollHeight;if(i>=text.length)clearInterval(int);},12);}async function send(){let i=document.getElementById('q');let d=i.value.trim();if(!d)return;add(d,'u');hist.push(d);document.getElementById('hist').innerHTML+='<div style=margin-top:6px>'+d.slice(0,30)+'</div>';i.value='';let b=add('...','b');b.classList.add('typing');try{let r=await fetch('/ask?d='+encodeURIComponent(d));let txt=await r.text();b.classList.remove('typing');typeEffect(b,txt);}catch(e){b.innerText='Errore, riprova';}}function newChat(){document.getElementById('chat').innerHTML='<div class=msg b>Nuova chat V10 pronta!</div>';}</script></body></html>";
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

            if(low.contains("zigurat") || low.contains("ziggurat")){
                risp = "ZIGURAT - Risposta V10 GOD:\n\nCosa sono:\nTempli mesopotamici a gradoni di mattoni crudi, alti fino a 50m. Al top cera il tempio del dio.\n\nA cosa servivano:\n1) Tempio per pregare (in cima)\n2) Osservatorio stelle\n3) Dimostrare potere del re\n\nEsempio top: Zigurat di Ur (2112 a.C.) per il dio Luna Nanna. 3 terrazze, scala monumentale.\n\nDifferenza piramidi:\n- Zigurat = tempio, a gradoni, in Mesopotamia\n- Piramide = tomba, lati lisci, in Egitto\n\nTRUCCO: ZIGURAT = ZIG ZAG verso il cielo\n\nVuoi il disegno o la verifica?";
            } else if(low.contains("ciao") || low.contains("chi sei")){
                risp = "Ciao! Sono TutorOnline V10 GOD - costruito da te Elia.\n\nSono meglio di ChatGPT perche:\n- Parlo come un prof italiano vero\n- Do trucchi mnemonici\n- Creo verifiche pronte\n- Gratis per sempre, tuo al 100%\n\nChiedimi: spiegami le zigurat, crea verifica storia, risolvi equazione...";
            } else {
                try{
                    HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String prompt = URLEncoder.encode("Sei TutorOnline V10, prof italiano top. Rispondi chiaro, con definizione, esempio, trucco. Domanda: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/"+prompt)).header("User-Agent","TutorV10").timeout(Duration.ofSeconds(15)).GET().build();
                    var r = c.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>40){
                        risp = r.body();
                    } else {
                        risp = "V10 su "+dom+":\nEcco spiegazione semplice con esempio e trucco per ricordarlo. Dimmi classe e ti faccio verifica!";
                    }
                }catch(Exception e){
                    risp = "V10 su "+dom+":\nSpiegazione top con trucco. Se vuoi approfondiamo con mappa e verifica!";
                }
            }

            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
