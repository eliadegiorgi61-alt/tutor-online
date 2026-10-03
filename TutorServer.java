import com.sun.net.httpserver.*;
import java.io.*;
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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline</title><style>body{background:#111;color:white;font-family:sans-serif;text-align:center;padding:20px}h1{font-size:36px}span{color:#00ff88}.card{background:#222;padding:20px;border-radius:20px;max-width:600px;margin:20px auto}input{width:90%;padding:15px;border-radius:12px;border:none;font-size:16px}button{background:#00ff88;padding:12px 30px;border:none;border-radius:12px;font-weight:bold;margin-top:15px;cursor:pointer}#risposta{text-align:left;white-space:pre-wrap;margin-top:15px;background:#333;padding:15px;border-radius:12px;min-height:100px}</style></head><body><h1>Tutor<span>Online</span></h1><p>Sa tutte le materie! AI gratis attiva</p><div class='card'><input id='q' placeholder='Chiedimi qualsiasi cosa...'><br><button onclick='chiedi()'>Chiedi</button><div id='risposta'>Scrivi una domanda!</div></div><script>async function chiedi(){let d=document.getElementById('q').value;if(!d)return;let r=document.getElementById('risposta');r.innerText='Sto pensando... 3 sec';let res=await fetch('/ask?domanda='+encodeURIComponent(d));r.innerText=await res.text();}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes().length);
            ex.getResponseBody().write(html.getBytes());
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String q = ex.getRequestURI().getQuery();
            String domanda = "";
            if (q != null && q.startsWith("domanda=")) domanda = URLDecoder.decode(q.substring(8), StandardCharsets.UTF_8);
            String risp = chiediAIGratis(domanda);
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
        System.out.println("Avviato su " + port);
    }

    static String chiediAIGratis(String domanda){
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            String prompt = URLEncoder.encode("Sei TutorOnline, tutor italiano bravissimo. Spiega in modo semplice con definizione facile, esempio reale e trucco per ricordare. Rispondi in italiano, breve e chiaro. Domanda: " + domanda, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://text.pollinations.ai/" + prompt))
                .timeout(Duration.ofSeconds(20))
                .GET().build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 && res.body().length() > 10) return res.body();
            else return fallback(domanda);
        } catch (Exception e){
            return fallback(domanda);
        }
    }

    static String fallback(String d){
        String low = d.toLowerCase();
        if (low.contains("guerra mondiale")) {
            return "2a Guerra Mondiale: 1 Settembre 1939.\n\nDefinizione: Germania invade Polonia, Francia e Inghilterra dichiarano guerra.\nEsempio: Come primo domino che fa cadere tutti gli altri.\nTrucco: 1-9-39 = 1 bambino con 39 di febbre fa scoppiare la guerra.\nCause: Trattato Versailles duro + crisi 1929 + Hitler.";
        }
        if (low.contains("fotosintesi")) return "Fotosintesi: piante usano luce + acqua + CO2 per fare zucchero e ossigeno. Formula: 6CO2 + 6H2O + luce -> C6H12O6 + 6O2. Trucco: FOTO=luce, SINTESI=costruire.";
        return "Risposta su: " + d + "\n\nDefinizione facile, esempio reale e trucco per ricordare.\n\nDimmi piu' dettagli e ti spiego meglio!";
    }
}
