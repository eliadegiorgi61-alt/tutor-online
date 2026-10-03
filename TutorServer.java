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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V50</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;height:100vh;display:flex;flex-direction:column}.top{background:#00ff88;color:#000;padding:12px;text-align:center;font-weight:900}#c{flex:1;overflow-y:auto;padding:12px;background:#0a0a0a}.msg{margin:8px 0;padding:12px;border-radius:14px;max-width:90%;line-height:1.6;white-space:pre-wrap;font-size:14px}.u{background:#00ff88;color:#000;margin-left:auto}.b{background:#111;border-left:3px solid #00ff88}.bar{display:flex;gap:6px;padding:10px;background:#000;border-top:1px solid #222}input{flex:1;padding:13px;border-radius:12px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:#000;border:none;border-radius:10px;padding:12px 14px;font-weight:900}</style></head><body><div class='top'>TutorOnline V50 GOD - FINAL</div><div id='c'><div class='msg b'>V50 ATTIVO - 10x ChatGPT\n\nScrivi: cosa sono le zigurat\nOppure: interrogami su storia\nOppure: crea verifica</div></div><div class='bar'><input id='q' placeholder='Chiedi a V50...' onkeydown='if(event.key==\"Enter\")s()'><button onclick='s()'>></button></div><div class='bar' style='padding-top:0'><button onclick='m()' style='background:#111;color:#fff;border:1px solid #333;flex:1'>Mic</button><button onclick='quiz()' style='background:#111;color:#00ff88;border:1px solid #00ff88;flex:1'>Interrogazione</button></div><script>function a(t,c){let d=document.getElementById('c');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}function type(el,txt){el.innerText='';let i=0;let iv=setInterval(()=>{el.innerText+=txt.charAt(i);i++;document.getElementById('c').scrollTop=99999;if(i>=txt.length)clearInterval(iv);},12);}async function s(){let i=document.getElementById('q');let d=i.value.trim();if(!d)return;a(d,'u');i.value='';let b=a('V50 ragiona...','b');try{let r=await fetch('/ask?d='+encodeURIComponent(d));let t=await r.text();type(b,t);}catch(e){b.innerText='Errore';}}function m(){let rec=new(window.webkitSpeechRecognition||window.SpeechRecognition)();rec.lang='it-IT';rec.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;s();};rec.start();}function quiz(){let t=prompt('Su cosa ti interrogo?');if(t){document.getElementById('q').value='Interrogami severa su '+t;s();}}</script></body></html>";
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
                risp = "V50 GOD - ZIGURAT da 10 e lode:\n\nCosa sono: Templi mesopotamici a gradoni, 2100 a.C., mattoni crudi, 3-7 terrazze.\n\nFunzione:\n1) Tempio dio in cima\n2) Osservatorio stelle\n3) Magazzino e potere re\n\nEsempio: Zigurat di Ur 60x45m per dio Nanna\n\nDifferenza Piramide:\n- Zigurat = tempio a gradoni Mesopotamia\n- Piramide = tomba lati lisci Egitto\n\nTrucco: ZIGURAT = ZIG ZAG verso il cielo\n\nVuoi che ti interrogo ora?";
            } else if(low.contains("interrogami")){
                risp = "V50 INTERROGAZIONE - Prof severa:\n\nDomanda 1: Cosa sono le zigurat? Rispondi.\n\nPoi ti do voto da 4 a 10 e ti correggo!";
            } else if(low.contains("verifica")){
                risp = "VERIFICA V50 su "+dom+":\nQ1 Definizione [2pt]\nQ2 3 differenze [3pt]\nQ3 Perche importante [3pt]\nQ4 Trucco [2pt]\nMandami risposte e ti do voto!";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode("Sei TutorOnline V50 prof top italiano. Spiega con trucco. Domanda: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent","V50").timeout(Duration.ofSeconds(12)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>40) risp = "V50 GOD:\n"+r.body();
                    else risp = "V50 su "+dom+": definizione + esempio + trucco. Dimmi di piu!";
                }catch(Exception e){ risp = "V50 su "+dom+": spiegazione da 10 con trucco!"; }
            }

            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
