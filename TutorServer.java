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
            String m = "{\"name\":\"TutorOnline V10001 HUMAN GOD\",\"short_name\":\"Tutor V10001\",\"start_url\":\"/\",\"display\":\"standalone\",\"background_color\":\"#000000\",\"theme_color\":\"#ff00ff\",\"icons\":[{\"src\":\"https://cdn-icons-png.flaticon.com/512/4712/4712109.png\",\"sizes\":\"512x512\",\"type\":\"image/png\"}]}";
            ex.getResponseHeaders().set("Content-Type","application/json");
            ex.sendResponseHeaders(200, m.getBytes().length);
            ex.getResponseBody().write(m.getBytes());
            ex.close();
        });

        server.createContext("/", ex -> {
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><link rel=manifest href='/manifest.json'><meta name=theme-color content='#ff00ff'><title>V10001 APP</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#ff00ff,#00ff88);color:#000;padding:14px;text-align:center;font-weight:900;border-radius:12px}.v{background:#000;color:#ff00ff;padding:3px 8px;border-radius:10px;font-size:10px}#c{height:68vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222;margin-top:10px}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap;line-height:1.6}.u{background:#ff00ff;color:#fff;margin-left:auto;max-width:88%}.b{background:#111;border-left:4px solid #ff00ff}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#ff00ff;color:#fff;border:none;padding:12px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}.install{background:#00ff88;color:#000;padding:12px;text-align:center;border-radius:12px;font-weight:900;margin-top:10px;display:none}</style></head><body><div class=top>TutorOnline V10001 APP <span class=v>HUMAN GOD</span></div><div id=inst class=install onclick=installApp()>📲 INSTALLA APP SUL TELEFONO</div><div id=c><div class='msg b'>V10001 APP UMANA ATTIVA - Oltre umano\n\nSono installabile come app vera!\n\nProva: Verifica su matematica</div></div><div class=row><input id=q placeholder='Parlami come a un amico...'><button onclick=s()>></button></div><div class=row><button onclick=document.getElementById('f').click() style='flex:1;background:#111;color:#ff00ff;border:1px solid #ff00ff'>📸 FOTO</button><button onclick=ver() style='flex:1;background:#111;color:#fff;border:1px solid #333'>Verifica</button></div><input id=f type=file accept='image/*' style='display:none'><script>let de=null;window.addEventListener('beforeinstallprompt',e=>{e.preventDefault();de=e;document.getElementById('inst').style.display='block';});function installApp(){if(de){de.prompt();}}function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('Sto pensando come umano oltre...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;}catch(e){b.innerText='Errore';}}function ver(){var m=prompt('Su cosa? Es: matematica');if(m){document.getElementById('q').value='Verifica su '+m;s();}}document.getElementById('f').onchange=function(){a('[Foto]','u');var b=a('Guardo foto...','b');setTimeout(async function(){var r=await fetch('/ask?d=foto compito');var t=await r.text();b.innerText=t;},500);}</script></body></html>";
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
                risp = "Ok fratello, zigurat te la spiego come me la ricorderei io:\n\nImmagina Ur nel 2112 a.C. - fango e sole che spacca. Fanno una montagna a gradoni, 7 gradoni = 7 pianeti = 7 dei.\n\nTrucco: ZIGURAT = ZIG ZAG RAT. Un ratto che sale zig zagando fino a diventare dio.\n\nPiramide = tomba da MORTO\nZigurat = tempio da VIVO\n\nTi interrogo ora?";
            } else if(low.contains("matematica") || low.contains("verifica")){
                risp = "Ok, verifica matematica - come te la farebbe un amico che vuole farti prendere 10:\n\nIl gioco: il prof vuole vedere che non hai paura.\n\nBilancia: quello che fai a sinistra lo fai a destra.\n\n2x + 3 = 11\nTogli 3: 2x = 8\nDividi per 2: x = 4\n\nTrucco per non sbagliare mai: rimetti dentro. 2*4+3=11 hai vinto.\n\nDimmi classe e ti faccio 3 esercizi veri e ti correggo ora.";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String prompt = "Sei tutor umano empatico 20 anni ma con 10000 IQ oltre umano, parli come fratello maggiore con trucchi, esempi veri, non dire da 10, spiega davvero: " + dom;
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode(prompt, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(15)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>40) risp = r.body();
                    else risp = "Allora, "+dom+": te la spiego umana, dimmi cosa ti blocca?";
                }catch(Exception e){ risp = "Su "+dom+": te la spiego come a un amico, dimmi di piu?"; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
