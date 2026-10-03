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
            String j = "{\"name\":\"TutorOnline V6 AGI\",\"short_name\":\"TutorV6\",\"start_url\":\"/\",\"display\":\"standalone\",\"background_color\":\"#000000\",\"theme_color\":\"#00ff88\",\"icons\":[{\"src\":\"https://cdn-icons-png.flaticon.com/512/4712/4712109.png\",\"sizes\":\"512x512\",\"type\":\"image/png\"}]}";
            ex.getResponseHeaders().set("Content-Type","application/json");
            ex.sendResponseHeaders(200, j.length());
            ex.getResponseBody().write(j.getBytes());
            ex.close();
        });

        server.createContext("/sw.js", ex -> {
            String sw = "self.addEventListener('install',e=>self.skipWaiting());self.addEventListener('fetch',e=>e.respondWith(fetch(e.request)));";
            ex.getResponseHeaders().set("Content-Type","application/javascript");
            ex.sendResponseHeaders(200, sw.length());
            ex.getResponseBody().write(sw.getBytes());
            ex.close();
        });

        server.createContext("/", ex -> {
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V6</title><link rel='manifest' href='/manifest.json'><meta name='theme-color' content='#00ff88'><style>body{background:#000;color:#fff;font-family:system-ui;margin:0}header{background:#0a0a0a;padding:14px;text-align:center;border-bottom:1px solid #1a1a1a}h1{margin:0;font-size:28px}span{color:#00ff88}.badge{background:#00ff88;color:#000;padding:3px 10px;border-radius:20px;font-size:10px;font-weight:900;letter-spacing:1px}#chat{height:52vh;overflow-y:auto;background:#0a0a0a;border-radius:18px;padding:12px;border:1px solid #222;margin:10px}.msg{margin:8px 0;padding:11px 14px;border-radius:16px;max-width:88%;line-height:1.5;font-size:14px;white-space:pre-wrap}.user{background:#00ff88;color:#000;margin-left:auto}.bot{background:#181818;border-left:3px solid #00ff88}.bar{display:flex;gap:6px;margin:8px 10px}input,select{flex:1;padding:13px;border-radius:12px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:black;border:none;border-radius:12px;padding:12px 14px;font-weight:900;cursor:pointer}#install{position:fixed;bottom:12px;left:50%;transform:translateX(-50%);background:#00ff88;color:black;padding:10px 18px;border-radius:30px;font-weight:900;display:none;box-shadow:0 0 20px #00ff88}</style></head><body><header><h1>Tutor<span>Online</span> V6</h1><div><span class='badge'>V6 AGI - APP INSTALLABILE</span></div></header><div style='max-width:800px;margin:0 auto'><div id='chat'><div class='msg bot'>V6 AGI - APP VERA\n\nInstallami sul telefono!\nSu iPhone: condividi > Aggiungi a Home\nSu Android: menu > Installa app\n\nVantaggi vs ChatGPT:\n- Gratis per sempre\n- Trucchi italiani\n- Ti parlo a voce\n- Creo verifiche PDF\n\nChiedi qualcosa!</div></div><div class='bar'><select id='mat' style='max-width:110px'><option>Generale</option><option>Matematica</option><option>Storia</option><option>Scienze</option><option>Inglese</option><option>Italiano</option></select><input id='q' placeholder='Chiedi a V6...'><button onclick='send()'>></button></div><div class='bar'><button onclick='voiceIn()' style='background:#111;color:white;border:1px solid #333'>Mic</button><button onclick='speak()' style='background:#111;color:white;border:1px solid #333'>Audio</button><button onclick='quiz()' style='background:#111;color:#00ff88;border:1px solid #00ff88'>Verifica</button></div></div><div id='install' onclick='installApp()'>Installa TutorOnline V6</div><script>let dep;window.addEventListener('beforeinstallprompt',e=>{e.preventDefault();dep=e;document.getElementById('install').style.display='block';});function installApp(){if(dep){dep.prompt();}}if('serviceWorker' in navigator){navigator.serviceWorker.register('/sw.js');}function add(t,c){let d=document.getElementById('chat');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function send(){let i=document.getElementById('q');let d=i.value;if(!d)return;let mat=document.getElementById('mat').value;add(d,'user');i.value='';let b=add('V6 AGI pensa...','bot');let res=await fetch('/ask?domanda='+encodeURIComponent(d)+'&materia='+mat);let txt=await res.text();b.innerText=txt;if(window.speechSynthesis){let u=new SpeechSynthesisUtterance(txt.slice(0,300));u.lang='it-IT';speechSynthesis.speak(u);}}function voiceIn(){try{let r=new(window.webkitSpeechRecognition||window.SpeechRecognition)();r.lang='it-IT';r.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;send();};r.start();}catch{alert('Usa Chrome');}}function speak(){let t=document.getElementById('chat').lastChild.innerText;let u=new SpeechSynthesisUtterance(t.slice(0,400));u.lang='it-IT';speechSynthesis.speak(u);}function quiz(){let d=prompt('Materia verifica?');if(d){document.getElementById('q').value='Crea verifica PDF di 5 domande su: '+d;send();}}</script></body></html>";
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
            if(low.contains("guerra") && low.contains("2")) risp="V6 AGI [Storia]\n1 Settembre 1939 -
