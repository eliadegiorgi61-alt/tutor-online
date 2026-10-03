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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V5 AGI</title><meta name='theme-color' content='#00ff88'><style>body{background:#050505;color:#fff;font-family:system-ui;margin:0}header{background:#0a0a0a;padding:12px;text-align:center;border-bottom:1px solid #1a1a1a;position:sticky;top:0}h1{margin:0;font-size:28px}span{color:#00ff88}.badge{color:#00ff88;font-size:11px;font-weight:bold;letter-spacing:2px}.wrap{max-width:800px;margin:0 auto;padding:12px}#chat{height:60vh;overflow-y:auto;background:#0a0a0a;border-radius:20px;padding:14px;border:1px solid #1a1a1a}.msg{margin:8px 0;padding:10px 14px;border-radius:16px;max-width:85%;line-height:1.5;font-size:14px}.user{background:#00ff88;color:#000;margin-left:auto;text-align:right}.bot{background:#1a1a1a;border-left:3px solid #00ff88}.bar{display:flex;gap:8px;margin-top:12px}input,select{flex:1;padding:14px;border-radius:14px;border:1px solid #333;background:#111;color:white;font-size:15px}button{background:#00ff88;color:black;border:none;border-radius:14px;padding:14px 20px;font-weight:900;cursor:pointer}.subjects{display:flex;gap:6px;flex-wrap:wrap;justify-content:center;margin:10px 0}.sub{padding:6px 10px;border-radius:20px;background:#111;border:1px solid #333;color:#888;font-size:12px;cursor:pointer}.sub.active{background:#00ff88;color:black;font-weight:bold}</style></head><body><header><h1>Tutor<span>Online</span> V5</h1><div class='badge'>AGI - OLTRE CHATGPT</div></header><div class='wrap'><div class='subjects'><div class='sub active' onclick='sel(this,\"generale\")'>Generale</div><div class='sub' onclick='sel(this,\"matematica\")'>Matematica</div><div class='sub' onclick='sel(this,\"storia\")'>Storia</div><div class='sub' onclick='sel(this,\"scienze\")'>Scienze</div><div class='sub' onclick='sel(this,\"inglese\")'>Inglese</div><div class='sub' onclick='sel(this,\"italiano\")'>Italiano</div></div><div id='chat'><div class='msg bot'>🚀 V5 AGI pronta.\n\nSono oltre ChatGPT perche:\n✓ Ragiono step-by-step come prof vero\n✓ Ho trucchi per interrogazione italiana\n✓ Foto compiti + Voce + Verifiche\n✓ Memoria di cosa sbagli\n\nScegli materia sopra e chiedi!</div></div><div class='bar'><select id='mat' style='max-width:130px'><option value='generale'>Generale</option><option value='matematica'>Matematica</option><option value='storia'>Storia</option><option value='scienze'>Scienze</option></select><input id='q' placeholder='Chiedi qualsiasi cosa...'><button onclick='send()'>➤</button></div><div class='bar'><button onclick='voice()' style='background:#111;color:white;border:1px solid #333'>🎤</button><button onclick=\"document.getElementById('f').click()\" style='background:#111;color:white;border:1px solid #333'>📸</button><button onclick='quiz()' style='background:#111;color:#00ff88;border:1px solid #00ff88'>🎯 Crea verifica</button></div><input type='file' id='f' accept='image/*' style='display:none'></div><script>let materia='generale';function sel(el,m){materia=m;document.getElementById('mat').value=m;document.querySelectorAll('.sub').forEach(s=>s.classList.remove('active'));el.classList.add('active');}function add(t,c){let d=document.getElementById('chat');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;}async function send(){let i=document.getElementById('q');let d=i.value;if(!d)return;add(d,'user');i.value='';add('V5 AGI sta ragionando come un prof con 20 anni di esperienza...','bot');let mat=document.getElementById('mat').value;let res=await fetch('/ask?domanda='+encodeURIComponent(d)+'&materia='+mat);let txt=await res.text();document.getElementById('chat').lastChild.innerText=txt;}function voice(){try{let r=new(window.webkitSpeechRecognition||window.SpeechRecognition)();r.lang='it-IT';r.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;send();};r.start();}catch{alert('Usa Chrome per voce');}}function quiz(){let d=prompt('Su cosa vuoi la verifica? Es: 2 guerra mondiale');if(d){document.getElementById('q').value='Crea verifica di 5 domande con risposte su: '+d;send();}}document.getElementById('q').addEventListener('keydown',e=>{if(e.key==='Enter')send();});</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes().length);
            ex.getResponseBody().write(html.getBytes());
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String q = ex.getRequestURI().getQuery();
            String domanda=""; String materia="generale";
            if(q!=null) for(String part: q.split("&")){
                if(part.startsWith("domanda=")) domanda=URLDecoder.decode(part.substring(8), StandardCharsets.UTF_8);
                if(part.startsWith("materia=")) materia=URLDecoder.decode(part.substring(8), StandardCharsets.UTF_8);
            }
            String low=domanda.toLowerCase();
            String risp;
            if(low.contains("guerra") && low.contains("2")) risp="🔥 V5 AGI - 2a GUERRA MONDIALE [Modalita "+materia+"]\n\nSTEP 1 - QUANDO: 1 Settembre 1939 - 2 Settembre 1945\nSTEP 2 - PERCHE: Versailles + Crisi 1929 + Espansionismo Hitler\nSTEP 3 - COME: Blitzkrieg, poi 2 fronti, poi atomiche\n\nTRUCCO V5: 1-9-39\nVERIFICA V5: Ti faccio 3 domande?\n1) Cosa fu il Patto Molotov-Ribbentrop?\n2) Perche USA entrano nel 1941?\n3) Quando lo sbarco in Normandia?";
            else if(low.contains("verifica") || low.contains("quiz")) risp="🎯 VERIFICA V5 GENERATA su: "+domanda+"\n\n1) [Domanda facile] Definisci con parole tue\n2) [Media] Fai un esempio concreto\n3) [Difficile] Perche e importante? Collegamento?\n4) [Trucco] Come lo ricordi domani?\n5) [Bonus maturita] Collegamento con altra materia\n\nVuoi che ti corregga le risposte?";
            else if(low.contains("mcd") || low.contains("matematica") || low.contains("2x")) risp="🔥 V5 MATEMATICA AGI\n\nRagionamento step-by-step, non solo risultato.\nEsempio MCD 12,18:\nStep 1: scomponi\nStep 2: prendi comuni minimi\nStep 3: moltiplica = 6\n\nMetodo V5: ti chiedo io il prossimo step per farti capire davvero.";
            else {
                try{
                    HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
                    String prompt = "Sei TutorOnline V5 AGI, tutor italiano oltre ChatGPT. Materia: "+materia+". Spiega a step (1,2,3), con trucco mnemonico e domanda di verifica finale. Domanda: "+domanda;
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/"+URLEncoder.encode(prompt, StandardCharsets.UTF_8))).timeout(Duration.ofSeconds(12)).GET().build();
                    var r = c.send(req, HttpResponse.BodyHandlers.ofString());
                    risp =
