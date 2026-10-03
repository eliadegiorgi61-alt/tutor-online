import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TutorServer {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // API UNIVERSALE - SA TUTTO
        server.createContext("/api/ask", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = queryToMap(query);
            String domanda = params.getOrDefault("q", "");
            domanda = URLDecoder.decode(domanda, StandardCharsets.UTF_8);

            String risposta = generaRispostaUniversale(domanda);

            byte[] bytes = risposta.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        });

        server.createContext("/", exchange -> {
            String html = """
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>TutorOnline - Sa Tutto</title>
<style>
body{font-family:system-ui;background:#0f0f0f;color:white;text-align:center;padding:20px}
h1{font-size:32px} span{color:#00ff88}
#box{background:#1e1e1e;padding:20px;border-radius:15px;max-width:600px;margin:20px auto}
input{width:80%;padding:15px;border-radius:10px;border:none;font-size:16px}
button{padding:15px 25px;background:#00ff88;border:none;border-radius:10px;font-weight:bold;margin-top:10px;cursor:pointer}
#r{margin-top:20px;text-align:left;background:#2a2a2a;padding:15px;border-radius:10px;white-space:pre-wrap;line-height:1.5}
</style>
</head>
<body>
<h1>Tutor<span>Online</span> 🎓</h1>
<p>Chiedimi QUALSIASI materia e QUALSIASI argomento!</p>
<div id="box">
<input id="q" placeholder="Es: equazioni di secondo grado, rivoluzione francese, fotosintesi...">
<br><button onclick="chiedi()">Chiedi al Tutor 🚀</button>
<div id="r">Scrivi una domanda e ti spiego tutto...</div>
</div>
<script>
async function chiedi(){
 let d=document.getElementById('q').value;
 let r=document.getElementById('r');
 if(!d){r.innerText='Scrivi qualcosa!'; return;}
 r.innerText='Sto pensando... 🤔';
 let res=await fetch('/api/ask?q='+encodeURIComponent(d));
 let t=await res.text();
 r.innerText=t;
}
</script>
</body>
</html>
                    """;
            byte[] b = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, b.length);
            exchange.getResponseBody().write(b);
            exchange.getResponseBody().close();
        });

        server.start();
        System.out.println("TutorOnline UNIVERSALE avviato su porta " + port);
    }

    static String generaRispostaUniversale(String domanda) {
        if (domanda.isBlank()) return "Fammi una domanda su qualsiasi materia! 😊";
        String d = domanda.toLowerCase();

        String materia = "Generale";
        if (d.matches(".*(matematica|equazion|geometria|algebra|trigonometria|funzione|derivat|integral).*")) materia = "MATEMATICA";
        else if (d.matches(".*(storia|guerra|rivoluzione|impero|roma|medioevo|fascismo|nazismo).*")) materia = "STORIA";
        else if (d.matches(".*(italiano|grammatica|poesia|dante|manzoni|analisi logica|promessi).*")) materia = "ITALIANO";
        else if (d.matches(".*(inglese|english|verbo|past|present).*")) materia = "INGLESE";
        else if (d.matches(".*(scienze|biologia|dna|cellula|fotosintesi|cor
