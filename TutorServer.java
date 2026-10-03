import com.sun.net.httpserver.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String p = System.getenv("PORT");
        if (p != null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", ex -> {
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V3</title><style>body{background:#0a0a0a;color:white;font-family:system-ui;text-align:center;padding:15px}h1{font-size:38px}span{color:#00ff88}.badge{background:#00ff88;color:black;padding:5px 12px;border-radius:20px;font-size:12px;font-weight:bold}.card{background:#1c1c1e;padding:20px;border-radius:24px;max-width:700px;margin:20px auto}input{width:100%;padding:16px;border-radius:14px;border:1px solid #333;background:#2c2c2e;color:white;font-size:16px;box-sizing:border-box}.mode{padding:8px 14px;border-radius:20px;background:#2c2c2e;border:1px solid #444;color:#aaa;font-size:13px;display:inline-block;margin:4px;cursor:pointer}.mode.active{background:#00ff88;color:black;font-weight:bold}button.main{background:#00ff88;color:black;padding:14px 40px;border:none;border-radius:14px;font-weight:900;font-size:16px;margin-top:10px;width:100%}#risposta{text-align:left;white-space:pre-wrap;margin-top:16px;background:#2c2c2e;padding:18px;border-radius:14px;min-height:120px;line-height:1.6;border-left:4px solid #00ff88}</style></head><body><h1>Tutor<span>Online</span> V3</h1><div class='badge'>ULTRA - PIU FORTE DI CHATGPT</div><div class='card'><input id='q' placeholder='Es: quando e scoppiata la 2 guerra mondiale?'><br><div style='margin:12px 0'><span class='mode active' id='m1' onclick=\"setMode('spiega')\">Spiega</span><span class='mode' id='m2' onclick=\"setMode('riassunto')\">Riassunto</span><span class='mode' id='m3' onclick=\"setMode('verifica')\">Interrogami</span></div><button class='main' onclick='chiedi()'>CHIEDI AL SUPER TUTOR</button><div id='risposta'>Ciao! Sono V3 ULTRA. Chiedimi MCD, guerra mondiale, fotosintesi... ti do definizione + esempio + trucco.</div></div><script>let mode='spiega';function setMode(m){mode=m;document.querySelectorAll('.mode').forEach(e=>e.classList.remove('active'));document.getElementById(m=='spiega'?'m1':m=='riassunto'?'m2':'m3').classList.add('active');}async function chiedi(){let d=document.getElementById('q').value;if(!d)return;let r=document.getElementById('risposta');r.innerText='V3 ULTRA sta ragionando...';let res=await fetch('/ask?domanda='+encodeURIComponent(d)+'&mode='+mode);r.innerText=await res.text();}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes().length);
            ex.getResponseBody().write(html.getBytes());
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String query = ex.getRequestURI().getQuery();
            String domanda = ""; String mode="spiega";
            if(query!=null){
              for(String part: query.split("&")){
                if(part.startsWith("domanda=")) domanda = URLDecoder.decode(part.substring(8), StandardCharsets.UTF_8);
                if(part.startsWith("mode=")) mode = URLDecoder.decode(part.substring(5), StandardCharsets.UTF_8);
              }
            }
            String risp = ultraTutor(domanda, mode);
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
    }

    static String ultraTutor(String d, String mode){
        String low = d.toLowerCase();
        if(low.contains("guerra") && (low.contains("2") || low.contains("seconda"))){
            return "🔥 2a GUERRA MONDIALE - V3 ULTRA\n\n📌 QUANDO: 1 Settembre 1939 -> 2 Settembre 1945\n\n📌 3 CAUSE CHE LA PROF VUOLE:\n1. Versailles troppo duro\n2. Crisi 1929 -> Hitler al potere\n3. Hitler invade Polonia\n\n🧩 ESEMPIO: Come punire troppo un compagno, torna piu' arrabbiato.\n\n🧠 TRUCCO: 1-9-39 = 1 bimbo a settembre con 39 di febbre fa scoppiare la guerra.";
        }
        if(low.contains("mcd") || low.contains("massimo comun") || low.contains("minimo comun divisore")){
            return "🔥 MCD V3 - MASSIMO COMUN DIVISORE\n\n📌 DEFINIZIONE: Numero piu' grande che divide entrambi.\n\n🧩 ESEMPIO: MCD 12 e 18\n12=2x2x3\n18=2x3x3\nComuni col esponente piccolo: 2x3=6 => MCD=6\n\n🧠 TRUCCO: Dici MASSIMO ma prendi il MINIMO esponente!\nmcm invece = 36";
        }
        try{
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
            String prompt = URLEncoder.encode(d, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/" + prompt)).timeout(Duration.ofSeconds(10)).GET().build();
            var res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200 && res.body().length()>30) return "🔥 V3 ULTRA:\n\n" + res.body();
        }catch(Exception e){}
        return "🔥 V3 su: " + d + "\n📌 Ti spiego facile + esempio vero + trucco per interrogazione.";
    }
}
