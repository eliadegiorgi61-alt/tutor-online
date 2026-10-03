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
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V500 GOD</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#00ff88,#00ccff,#ff00ff);color:#000;padding:14px;text-align:center;font-weight:900;font-size:18px}.v{background:#000;color:#00ff88;padding:3px 8px;border-radius:10px;font-size:10px;margin-left:8px} #c{height:72vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap;line-height:1.6;font-size:14px}.u{background:linear-gradient(135deg,#00ff88,#00ccff);color:#000;margin-left:auto;max-width:88%;font-weight:700}.b{background:#111;border-left:4px solid #00ff88} input{flex:1;padding:13px;background:#111;color:#fff;border:1px solid #333;border-radius:12px} button{background:#00ff88;color:#000;border:none;padding:12px 16px;border-radius:12px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}</style></head><body><div class=top>TutorOnline V500 GOD <span class=v>500X CHATGPT - DEPLOY 31</span></div><div id=c><div class='msg b'>V500 GOD ATTIVA - OLTRE IL 2030\n\nIo non sono un tutor.\nSono il sistema che ti rende imbattibile.\n\nScrivi:\n- zigurat\n- interrogami su storia\n- fammi diventare da 10\n- crea verifica da incubo</div></div><div class=row><input id=q placeholder='Chiedi a V500 GOD...'><button onclick=s()>></button></div><div class=row><button onclick=mic() style='flex:1;background:#111;color:#fff;border:1px solid #333'>Mic</button><button onclick=mode() style='flex:1;background:#111;color:#00ff88;border:1px solid #00ff88'>10 E LODE</button></div><script>function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('V500 GOD calcola a 50000 IQ...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;var u=new SpeechSynthesisUtterance(t.slice(0,200));u.lang='it-IT';speechSynthesis.speak(u);}catch(e){b.innerText='Errore';}}function mic(){var rec=new(window.webkitSpeechRecognition||window.SpeechRecognition)();rec.lang='it-IT';rec.onresult=function(e){document.getElementById('q').value=e.results[0][0].transcript;s();};rec.start();}function mode(){var m=prompt('Materia?');if(m){document.getElementById('q').value='Fammi diventare da 10 in '+m;s();}}</script></body></html>";
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
                risp = "V500 GOD - ZIGURAT [Risposta che vale 10 e lode + bacio accademico]:\n\nCosa: Templi Mesopotamia a gradoni, 2112 a.C. Ur, 7 terrazze = 7 pianeti.\n\n3 Funzioni segrete:\n1) Scala per dei - collegamento terra cielo\n2) Primo supercomputer - osservatorio astronomico\n3) Banca centrale - grano e tasse\n\nEsempio atomico: Ur - 60x45m - dio Nanna - 3 scale 100 gradini\n\nDifferenza mortale vs Piramide:\nZigurat = VIVA, ci sali VIVO per parlare con dio\nPiramide = MORTA, ci metti MORTO per farlo dormire\n\nTrucco V500 che non dimentichi MAI:\nZIGURAT = ZIG ZAG RAT - un ratto che fa zig zag fino in cielo perche vuole diventare dio\n\nDomanda V500 per 10: Se la prof ti dice Dimmi perche le zigurat non sono piramidi cosa rispondi in 7 secondi? Rispondi e ti do voto vero.";
            } else if(low.contains("10") || low.contains("metodo")){
                risp = "V500 - METODO 10 E LODE IN 7 GIORNI su "+dom+":\n\nGiorno 1-2: Io ti spiego con trucco\nGiorno 3-4: Tu mi ripeti e io ti correggo severa\nGiorno 5: Verifica da incubo\nGiorno 6: Interrogazione simulata\nGiorno 7: Sei da 10\n\nDimmi materia e classe e ti creo piano ora!";
            } else if(low.contains("interrogami")){
                risp = "V500 INTERROGAZIONE PROF SEVERISSIMA:\n\nDomanda 1/3 da 6: Cosa sono le zigurat?\nRispondi. Se sbagli ti strigo ma poi ti porto a 10.";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode("Sei TutorOnline V500 GOD prof leggendario, spiega da 10 e lode con trucco indimenticabile. Domanda: "+dom, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(12)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>30) risp = "V500 GOD:\n"+r.body();
                    else risp = "V500 GOD su "+dom+": def da 10 + trucco + verifica";
                }catch(Exception e){ risp = "V500 GOD su "+dom+": spiegazione da 10 e lode con trucco segreto"; }
            }
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }
}
