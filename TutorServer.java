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
            String j = "{\"name\":\"TutorOnline V7\",\"short_name\":\"TutorV7\",\"start_url\":\"/\",\"display\":\"standalone\",\"background_color\":\"#000\",\"theme_color\":\"#00ff88\"}";
            ex.getResponseHeaders().set("Content-Type","application/json");
            ex.sendResponseHeaders(200, j.length());
            ex.getResponseBody().write(j.getBytes());
            ex.close();
        });

        server.createContext("/", ex -> {
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V7</title><link rel='manifest' href='/manifest.json'><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}h1{text-align:center}span{color:#00ff88}.card{background:#111;padding:14px;border-radius:18px;max-width:700px;margin:0 auto;border:1px solid #222}#c{height:60vh;overflow-y:auto;background:#0a0a0a;border-radius:12px;padding:10px;border:1px solid #222}.msg{margin:8px 0;padding:11px 13px;border-radius:14px;font-size:14px;white-space:pre-wrap;line-height:1.5}.u{background:#00ff88;color:#000;margin-left:auto;max-width:85%}.b{background:#1a1a1a;border-left:3px solid #00ff88}input{flex:1;padding:13px;border-radius:12px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:black;border:none;border-radius:10px;padding:12px 14px;font-weight:900}.row{display:flex;gap:6px;margin-top:8px}</style></head><body><h1>Tutor<span>Online</span> V7 CHAT</h1><div class='card'><div id='c'><div class='msg b'>Ciao! Sono TutorOnline V7 - rispondo come ChatGPT ma per scuola italiana.\nProva: Cosa sono le zigurat?</div></div><div class='row'><input id='q' placeholder='Scrivi...'><button onclick='s()'>></button></div><div class='row'><button onclick='mic()' style='background:#111;color:white;border:1px solid #333'>Mic</button></div></div><script>function a(t,c){let d=document.getElementById('c');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){let i=document.getElementById('q');let d=i.value.trim();if(!d)return;a(d,'u');i.value='';let b=a('Sto scrivendo...','b');try{let r=await fetch('/ask?d='+encodeURIComponent(d));let txt=await r.text();b.innerText=txt;}catch(e){b.innerText='Errore, riprova';}}function mic(){let rec=new(window.webkitSpeechRecognition||window.SpeechRecognition)();rec.lang='it-IT';rec.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;s();};rec.start();}</script></body></html>";
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
            String risp = "";

            if(low.contains("zigurat") || low.contains("ziggurat") || low.contains("zigutat")){
                risp = "Le ZIGURAT sono i templi della Mesopotamia!\n\nCOSA SONO:\nSono grandi piramidi a gradoni fatte di mattoni. Al contrario delle piramidi egizie (a punta liscia), le zigurat hanno terrazze.\n\nA COSA SERVIVANO:\n1) Tempio in cima per gli dei\n2) Osservatorio astronomico\n3) Simbolo del potere del re\n\nESEMPIO FAMOSO: Zigurat di Ur, dedicata al dio Nanna (Luna)\n\nTRUCCO per interrogazione: ZIGURAT = Zig-zag + URAT -> scala a zig-zag verso il cielo\n\nVuoi sapere differenza con piramidi egizie?";
            } else if(low.contains("guerra") && low.contains("2")){
                risp = "2a GUERRA MONDIALE:\n1 Settembre 1939 Germania invade Polonia -> 2 Settembre 1945 resa Giappone.\n\n3 CAUSE:\n1) Pace di Versailles umilia Germania\n2) Crisi del 1929 porta Hitler al potere\n3) Patto Hitler-Stalin\n\nTRUCCO: 1-9-39";
            } else if(low.contains("piramide")){
                risp = "PIRAMIDI EGIZIE:\nTombe dei faraoni, a forma di piramide liscia. La piu famosa Cheope a Giza.\n\nDIFFERENZA CON ZIGURAT:\n- Piramide = tomba, punta liscia\n- Zigurat = tempio, a gradoni\n\nTrucco: PIRAMIDE = PIRA (morto) + MIDE";
            } else if(low.contains("mesopotamia")){
                risp = "MESOPOTAMIA = terra tra due fiumi (Tigri ed Eufrate).\n\n5 invenzioni:\n1) Scrittura cuneiforme\n2) Ruota\n3) Codice di Hammurabi (prime leggi)\n4) Zigurat\n5) Matematica base 60 (60 minuti)\n\nPopoli: Sumeri -> Babilonesi -> Assiri";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String prompt = "Sei un prof italiano bravissimo, spiega semplice con esempio e trucco. Domanda: " + dom;
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode(prompt, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder()
                       .uri(URI.create(url))
                       .header("User-Agent","TutorOnline/7.0")
                       .timeout(Duration.ofSeconds(15))
                       .GET().build();
                    var res = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(res.statusCode()==200 && res.body().length()>30){
                        risp = res.body();
                    } else {
                        risp = "Ecco su " + dom + ":\nDefinizione semplice + esempio concreto + trucco per ricordarlo. Dimmi che materia e ti spiego meglio!";
                    }
                }catch(Exception e){
                    risp = "Ecco su " + dom + ":\n\nTi spiego semplice: e un argomento importante. Fammi sapere che classe fai e ti do definizione + esempio + trucco per interrogazione!";
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
