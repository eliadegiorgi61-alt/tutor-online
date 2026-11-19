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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline</title><style>body{background:#111;color:white;font-family:sans-serif;text-align:center;padding:20px}h1{font-size:36px}span{color:#00ff88}.card{background:#222;padding:20px;border-radius:20px;max-width:600px;margin:20px auto}input{width:90%;padding:15px;border-radius:12px;border:none;font-size:16px}button{background:#00ff88;padding:12px 30px;border:none;border-radius:12px;font-weight:bold;margin-top:15px} #risposta{text-align:left;white-space:pre-wrap;margin-top:15px;background:#333;padding:15px;border-radius:12px;min-height:100px}</style></head><body><h1>Tutor<span>Online</span></h1><p>Chiedimi tutto, tranne le armi!</p><div class='card'><input id='q' placeholder='Chiedimi qualsiasi cosa...'><br><button onclick='chiedi()'>Chiedi</button><div id='risposta'>Scrivi una domanda!</div></div><script>async function chiedi(){let d=document.getElementById('q').value;if(!d)return;let r=document.getElementById('risposta');r.innerText='Sto pensando...';let res=await fetch('/ask?domanda='+encodeURIComponent(d));r.innerText=await res.text();}</script></body></html>";
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
            return "Non posso spiegare come e fatta o come si usa un arma, e pericoloso.\n\nChiedimi invece una cosa di scuola o qualsiasi altra domanda, per esempio: Chi ha scoperto l America?";
        }
        if (low.contains("america") && low.contains("scopert")) {
            return "Chi ha scoperto l America?\n\nL ha scoperta Cristoforo Colombo il 12 ottobre 1492.\n\nColombo era un navigatore italiano che lavorava per la Spagna. Cercava l India e ha trovato l America.\n\nTrucco per ricordare: 1492 - Colombo arriva in America con 3 caravelle: Nina, Pinta e Santa Maria.";
        }
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            String
