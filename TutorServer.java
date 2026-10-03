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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V5 AGI</title><style>body{background:#050505;color:#fff;font-family:system-ui;margin:0}header{background:#0a0a0a;padding:12px;text-align:center;border-bottom:1px solid #1a1a1a}h1{margin:0;font-size:26px}span{color:#00ff88}.badge{color:#00ff88;font-size:10px;letter-spacing:2px;font-weight:bold}#chat{height:55vh;overflow-y:auto;background:#0a0a0a;border-radius:18px;padding:12px;border:1px solid #222;margin:10px}.msg{margin:8px 0;padding:10px 12px;border-radius:14px;max-width:90%;line-height:1.5;font-size:14px}.user{background:#00ff88;color:#000;margin-left:auto}.bot{background:#1a1a1a;border-left:3px solid #00ff88;white-space:pre-wrap}.bar{display:flex;gap:6px;margin:10px}input,select{flex:1;padding:12px;border-radius:12px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:black;border:none;border-radius:12px;padding:12px 16px;font-weight:900}</style></head><body><header><h1>Tutor<span>Online</span> V5 AGI</h1><div class='badge'>OLTRE CHATGPT - V5</div></header><div style='max-width:800px;margin:0 auto;padding:10px'><div id='chat'><div class='msg bot'>V5 AGI pronta. Piu forte di ChatGPT:\n- Ragiono step-by-step\n- Trucchi italiani\n- Foto compiti + Voce\n- Creo verifiche\n\nScegli materia e chiedi!</div></div><div class='bar'><select id='mat'><option value='generale'>Generale</option><option value='matematica'>Matematica</option><option value='storia'>Storia</option><option value='scienze'>Scienze</option><option value='inglese'>Inglese</option></select><input id='q' placeholder='Chiedi...'><button onclick='send()'>></button></div><div class='bar'><button onclick='voice()' style='background:#111;color:white;border:1px solid #333'>Mic</button><button onclick='quiz()' style='background:#111;color:#00ff88;border:1px solid #00ff88'>Crea verifica</button></div></div><script>let materia='generale';function add(t,c){let d=document.getElementById('chat');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;}async function send(){let i=document.getElementById('q');let d=i.value;if(!d)return;let mat=document.getElementById('mat').value;add(d,'user');i.value='';add('V5 ragiona...','bot');let res=await fetch('/ask?domanda='+encodeURIComponent(d)+'&materia='+mat);let txt=await res.text();document.getElementById('chat').lastChild.innerText=txt;}function voice(){try{let r=new(window.webkitSpeechRecognition||window.SpeechRecognition)();r.lang='it-IT';r.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;send();};r.start();}catch{alert('Usa Chrome');}}function quiz(){let d=prompt('Su cosa verifica?');if(d){document.getElementById('q').value='Crea verifica 5 domande su: '+d;send();}}document.getElementById('q').addEventListener('keydown',e=>{if(e.key==='Enter')send();});</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String q = ex.getRequestURI().getQuery();
            String domanda=""; String mat="generale";
            if(q!=null) for(String part: q.split("&")){
                if(part.startsWith("domanda=")) domanda=URLDecoder.decode(part.substring(8), StandardCharsets.UTF_8);
                if(part.startsWith("materia=")) mat=URLDecoder.decode(part.substring(8), StandardCharsets.UTF_8);
            }
            String low=domanda.toLowerCase();
            String risp;
            if(low.contains("guerra") && low.contains("2")) risp="V5 AGI STORIA - 2a Guerra Mondiale\n1 Settembre 1939 -> 2 Settembre 1945\nCause: Versailles + Crisi 29 + Hitler\nTrucco: 1-9-39\nVuoi verifica?";
            else if(low.contains("verifica") || low.contains("quiz")) risp="VERIFICA V5 su: "+domanda+"\n1) Definizione facile\n2) Esempio concreto\n3) Perche importante?\n4) Trucco mnemonico\n5) Collegamento altra materia";
            else {
                try{
                    HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
                    String prompt = URLEncoder.encode("Sei TutorOnline V5 AGI tutor italiano, materia "+mat+". Spiega a step con trucco: "+domanda, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/"+prompt)).timeout(Duration.ofSeconds(10)).GET().build();
                    var r = c.send(req, HttpResponse.BodyHandlers.ofString());
                    risp = (r.statusCode()==200 && r.body().length()>20)? "V5 AGI ["+mat+"]:\n"+r.body() : "V5 AGI su: "+domanda;
                }catch(Exception e){ risp="V5 AGI su: "+domanda+" ["+mat+"]"; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
