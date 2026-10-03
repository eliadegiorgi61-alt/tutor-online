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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V50 GOD</title><style>*{margin:0;padding:0;box-sizing:border-box}body{background:#000;color:#fff;font-family:system-ui;height:100vh;display:flex;flex-direction:column}.top{background:linear-gradient(90deg,#00ff88,#00ccff);color:#000;padding:10px;text-align:center;font-weight:900;letter-spacing:1px}.badge{background:#000;color:#00ff88;padding:2px 8px;border-radius:10px;font-size:10px;margin-left:8px}#chat{flex:1;overflow-y:auto;padding:12px;background:#0a0a0a}.msg{margin:8px 0;padding:12px 14px;border-radius:16px;max-width:90%;line-height:1.6;white-space:pre-wrap;font-size:14px}.u{background:linear-gradient(135deg,#00ff88,#00ccff);color:#000;margin-left:auto;font-weight:600}.b{background:#111;border:1px solid #222;border-left:3px solid #00ff88}.bar{display:flex;gap:6px;padding:10px;background:#000;border-top:1px solid #1a1a1a}input{flex:1;padding:14px;border-radius:12px;border:1px solid #333;background:#111;color:#fff}button{background:#00ff88;color:#000;border:none;border-radius:12px;padding:12px 14px;font-weight:900}select{background:#111;color:#fff;border:1px solid #333;border-radius:10px;padding:10px}</style></head><body><div class='top'>TutorOnline V50 GOD <span class='badge'>FINAL AGI - 10X CHATGPT</span></div><div id='chat'><div class='msg b'>V50 GOD MODE ATTIVO - LIVELLO FINALE\n\nNon sono piu un tutor. Sono il sistema che ti porta al 10.\n\nComandi V50:\n- Scrivi: zigurat, piramidi, equazioni\n- Dici: interrogami su storia\n- Chiedi: crea verifica difficilissima\n\nProva ora: cosa sono le zigurat?</div></div><div class='bar'><select id='m'><option>Generale</option><option>Matematica</option><option>Storia</option><option>Scienze</option><option>Italiano</option></select><input id='q' placeholder='Chiedi a V50 GOD...'><button onclick='s()'>></button></div><div class='bar' style='padding-top:0'><button onclick='mic()' style='background:#111;color:#fff;border:1px solid #333;flex:1'>Mic</button><button onclick='quiz()' style='background:#111;color:#00ff88;border:1px solid #00ff88;flex:1'>Modalita Interrogazione</button></div><script>function a(t,c){let d=document.getElementById('chat');let e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}function type(el,txt){el.innerText='';let i=0;let iv=setInterval(()=>{el.innerText+=txt[i];i++;document.getElementById('chat').scrollTop=99999;if(i>=txt.length)clearInterval(iv);},10);}async function s(){let i=document.getElementById('q');let d=i.value.trim();if(!d)return;a(d,'u');i.value='';let b=a('V50 GOD ragiona a 1000 IQ...','b');try{let r=await fetch('/ask?d='+encodeURIComponent(d)+'&m='+document.getElementById('m').value);let txt=await r.text();type(b,txt);let u=new SpeechSynthesisUtterance(txt.slice(0,200));u.lang='it-IT';speechSynthesis.speak(u);}catch(e){b.innerText='Errore';}}function mic(){let r=new(window.webkitSpeechRecognition||window.SpeechRecognition)();r.lang='it-IT';r.onresult=e=>{document.getElementById('q').value=e.results[0][0].transcript;s();};r.start();}function quiz(){let t=prompt('Su cosa ti interrogo?');if(t){document.getElementById('q').value='Interrogami come una prof severa su '+t;s();}}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String qs = ex.getRequestURI().getQuery();
            String dom=""; String mat="generale";
            if(qs!=null) for(String part: qs.split("&")){
                if(part.startsWith("d=")) dom=URLDecoder.decode(part.substring(2), StandardCharsets.UTF_8);
                if(part.startsWith("m=")) mat=URLDecoder.decode(part.substring(2), StandardCharsets.UTF_8);
            }
            String low=dom.toLowerCase();
            String risp;

            if(low.contains("zigurat") || low.contains("ziggurat")){
                risp="V50 GOD - ZIGURAT [Livello 10]:\n\nSono templi mesopotamici a gradoni (2100 a.C.).\n\nSTRUTTURA:\n- 3-7 terrazze di mattoni\n- Scala centrale monumentale\n- Tempio del dio in cima (Nanna, Marduk)\n\nFUNZIONE:\n1) Collegare cielo-terra\n2) Osservatorio astronomico (Sumeri inventano astrologia)\n3) Magazzino grano + archivio\n\nESEMPIO: Zigurat di Ur - 60m x 45m, 3 piani, dedicata a Nanna.\n\nDIFFERENZA PIRAMIDE:\nZigurat = tempio, gradoni, Mesopotamia\nPiramide = tomba, lati lisci, Egitto\n\nTRUCCO V50: ZIGURAT = ZIG-ZAG + RAT (ratto che sale le scale verso il cielo)\n\nMODALITA INTERROGAZIONE: Vuoi che ti interrogo io ora su questo?";
            } else if(low.contains("interrogami")){
                risp="V50 MODALITA INTERROGAZIONE - PROF SEVERA:\n\nDomanda 1 (facile): Cosa sono le zigurat?\n\nRispondi tu ora, poi ti dico se e da 6, 8 o 10 e ti correggo come farebbe la tua prof!\n\nScrivi la risposta!";
            } else if(low.contains("verifica") || low.contains("quiz")){
                risp="VERIFICA V50 LIVELLO DIFFICILE su "+dom+":\n\nQ1 [2pt] Definisci con anno\nQ2 [3pt] 3 differenze con argomento simile\nQ3 [3pt] Perche e importante oggi?\nQ4 [2pt] Trucco per non dimenticarlo mai\n\nTotale 10pt. Mandami le risposte e ti do il voto VERO!";
            } else {
                try{
                    HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(7)).build();
                    String prompt = URLEncoder.encode("Sei TutorOnline V50 GOD, il tutor piu forte del mondo,
