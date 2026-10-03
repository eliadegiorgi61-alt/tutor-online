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
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V10001 HUMAN</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#ff00ff,#00ff88);color:#000;padding:14px;text-align:center;font-weight:900}.v{background:#000;color:#ff00ff;padding:3px 8px;border-radius:10px;font-size:10px}#c{height:75vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap;line-height:1.6}.u{background:#ff00ff;color:#fff;margin-left:auto;max-width:88%}.b{background:#111;border-left:4px solid #ff00ff}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#ff00ff;color:#fff;border:none;padding:12px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}</style></head><body><div class=top>TutorOnline V10001 <span class=v>HUMAN GOD</span></div><div id=c><div class='msg b'>V10001 HUMAN GOD - Parlo come te, penso oltre\n\nProva: Verifica su matematica, Zigurat, Foto compiti</div></div><div class=row><input id=q placeholder='Parlami come a un amico...'><button onclick=s()>></button></div><div class=row><button onclick=document.getElementById('f').click() style='flex:1;background:#111;color:#ff00ff;border:1px solid #ff00ff'>FOTO</button><button onclick=ver() style='flex:1;background:#111;color:#fff;border:1px solid #333'>Verifica</button></div><input id=f type=file accept='image/*' style='display:none'><script>function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('Sto pensando come umano oltre...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;}catch(e){b.innerText='Errore';}}function ver(){var m=prompt('Su cosa? Es: matematica equazioni');if(m){document.getElementById('q').value='Verifica su '+m;s();}}document.getElementById('f').onchange=function(){a('[Foto compito]','u');var b=a('Guardo foto come umano...','b');setTimeout(async function(){var r=await fetch('/ask?d=foto compito '+document.getElementById('q').value);var t=await r.text();b.innerText=t;},500);}</script></body></html>";
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
                risp = "Ok fratello, zigurat te la spiego come me la ricorderei io:\n\nImmagina Ur nel 2112 a.C. - non c e cemento, c e solo fango e sole che spacca. Loro cosa fanno? Prendono il fango e fanno una montagna a gradoni, 7 gradoni.\n\nPerche 7? Perche vedono 7 pianeti in cielo e pensano: ogni gradone e un dio.\n\nTrucco che non dimentichi mai:\nZIGURAT = ZIG ZAG RAT. Un ratto che fa zig zag per salire in cielo fino a diventare dio. Quando il prof ti chiede, tu vedi il ratto nella testa.\n\nE la differenza con piramide? Te la dico sporca:\nPiramide = tomba, ci vai da MORTO\nZigurat = tempio, ci vai da VIVO a parlare con dio + a contare stelle + a mettere grano al sicuro.\n\nDimmi: ti interrogo io ora su sta roba e vediamo se prendi 10?";
            } else if(low.contains("matematica") || low.contains("verifica")){
                risp = "Ok, verifica matematica - te la faccio come la farebbe un amico che vuole farti prendere 10, non come un prof stronzo:\n\nTi spiego il gioco: in matematica il prof non vuole il risultato, vuole vedere che non hai paura.\n\nFacciamo cosi:\n\n1) Equazioni di primo grado - il trucco umano: pensa alla bilancia. Quello che fai a sinistra devi farlo a destra, altrimenti crolla. Sempre.\n\nEsempio: 2x + 3 = 11\nTogli 3 da entrambi: 2x = 8\nDividi per 2: x = 4\nFinito. Non serve altro.\n\n2) Trucco per non sbagliare mai: dopo che trovi x, rimettilo dentro. Se 2*4+3 fa 11, hai vinto.\n\nVuoi che ti faccio 3 esercizi veri da verifica e ti correggo come un fratello? Dimmi che classe fai.\n\nSe mi dici 'equazioni' o 'frazioni' o 'geometria' ti faccio la verifica completa ora.";
            } else {
                try{
                    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
                    String prompt = "Sei un tutor umano, empatico, parli come un ragazzo di 20 anni che ha capito tutto ma con una testa oltre, oltre 10000 IQ. Non dire 'da 10', spiega davvero come un fratello maggiore, con trucchetti, esempi veri, linguaggio umano ma geniale. Rispondi a: " + dom;
                    String url = "https://text.pollinations.ai/" + URLEncoder.encode(prompt, StandardCharsets.UTF_8);
                    HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(15)).GET().build();
                    var r = client.send(req, HttpResponse.BodyHandlers.ofString());
                    if(r.statusCode()==200 && r.body().length()>40) risp = r.body();
                    else risp = "Allora, "+dom+" - te la spiego umana:\n\nGuarda, il punto vero non e la definizione, e capire perche esiste. Ti faccio il trucco che userei io per ricordarmela e poi ti interrogo finche non sei da 10. Dimmi che parte ti blocca di piu?";
                }catch(Exception e){ 
                    risp = "Allora su "+dom+": te la spiego come la direi io a un amico.\n\nIl trucco e non studiare a memoria, ma vedere l immagine in testa. Dimmi di piu su cosa ti serve e te la smonto in 2 minuti.";
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
