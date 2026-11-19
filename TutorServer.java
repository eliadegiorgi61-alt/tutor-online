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
            String html = "<h1>TutorOnline</h1><p>Chiedimi tutto, tranne le armi!</p><input id='q' placeholder='Chiedimi qualsiasi cosa...'><button onclick='chiedi()'>Chiedi</button><div id='risposta'>Scrivi una domanda!</div><script>async function chiedi(){let d=document.getElementById('q').value;if(!d)return;let r=document.getElementById('risposta');r.innerText='Sto pensando...';let res=await fetch('/ask?domanda='+encodeURIComponent(d));r.innerText=await res.text();}</script>";
            ex.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes().length);
            ex.getResponseBody().write(html.getBytes());
            ex.close();
        });
        server.createContext("/ask", ex -> {
            String q = ex.getRequestURI().getQuery();
            String domanda = "";
            if (q != null && q.startsWith("domanda=")) domanda = URLDecoder.decode(q.substring(8), StandardCharsets.UTF_8);
            String risp = chiediAIGratis(domanda);
            ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
        System.out.println("Avviato su " + port);
    }
    static String chiediAIGratis(String domanda) {
        String low = domanda.toLowerCase();
        if (low.contains("pistola") || low.contains("arma") || low.contains("bomba") || low.contains("fucile") || low.contains("coltello")) {
            return "Non posso spiegare come e fatta o come si usa un arma, e pericoloso. Chiedimi invece una cosa di scuola o qualsiasi altra domanda, per esempio: Chi ha scoperto l America?";
        }
        if (low.contains("america") && low.contains("scopert")) {
            return "Chi ha scoperto l America? L ha scoperta Cristoforo Colombo il 12 ottobre 1492 con 3 caravelle: Nina, Pinta e Santa Maria.";
        }
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            String prompt = URLEncoder.encode("Sei TutorOnline, assistente intelligente per ragazzi. Rispondi a qualsiasi domanda in modo semplice, chiaro e in italiano. Non parlare mai di armi. Domanda: " + domanda, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://text.pollinations.ai/" + prompt)).timeout(Duration.ofSeconds(20)).header("User-Agent", "TutorOnline").GET().build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 && res.body().length() > 10) return res.body();
            return "Non ho capito, puoi ripetere?";
        } catch (Exception e) {
            return "Problema di connessione, riprova tra poco.";
        }
    }
}
