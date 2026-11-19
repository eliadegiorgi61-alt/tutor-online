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
        if(p!=null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", ex -> {
            String html = """
            <!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'>
            <title>TutorOnline</title>
            <style>body{background:#111;color:white;font-family:sans-serif;text-align:center;padding:20px}
            h1{font-size:36px} span{color:#00ff88} .card{background:#222;padding:20px;border-radius:20px;max-width:600px;margin:20px auto}
            input{width:90%;padding:15px;border-radius:12px;border:none;font-size:16px} button{background:#00ff88;padding:12px 30px;border:none;border-radius:12px;font-weight:bold;margin-top:15px;cursor:pointer}
            #risposta{text-align:left;white-space:pre-wrap;margin-top:15px;background:#333;padding:15px;border-radius:12px;min-height:100px}</style></head>
            <body><h1>Tutor<span>Online</span></h1><p>Sa tutte le materie! Solo scuola!</p>
            <div class='card'><input id='q' placeholder='Chiedimi qualsiasi cosa di scuola...'>
            <br><button onclick='chiedi()'>Chiedi</button>
            <div id='risposta'>Scrivi una domanda!</div></div>
            <script>
            async function chiedi(){
              let d=document.getElementById('q').value;
              if(!d) return;
              let r=document.getElementById('risposta');
              r.innerText='Sto pensando...';
              let res=await fetch('/ask?domanda='+encodeURIComponent(d));
              r.innerText=await res.text();
            }
            </script></body></html>
            """;
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes().length);
            ex.getResponseBody().write(html.getBytes());
            ex.close();
        });

        server.createContext("/ask", ex -> {
            String q = ex.getRequestURI().getQuery();
            String domanda = "";
            if(q!=null && q.startsWith("domanda=")) domanda = URLDecoder.decode(q.substring(8), StandardCharsets.UTF_8);
            String risp = chiediAIGratis(domanda);
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risp.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risp.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.start();
        System.out.println("Avviato su "+port);
    }

    static String chiediAIGratis(String domanda){
        String low = domanda.toLowerCase();

        // BLOCCO ARMI E COSE PERICOLOSE
        if(low.contains("pistola") || low.contains("arma") || low.contains("bomba") || low.contains("coltello") || low.contains("fucile") || low.contains("mitra") || low.contains("come si costruisce") || low.contains("come si fa una")) {
            return "Non posso spiegare come è fatta o come si usa un'arma, è pericoloso e non è materia di scuola.\n\nChiedimi invece una cosa di scuola, per esempio: Chi ha scoperto l'America? Oppure: Spiegami le equazioni.";
        }

        // RISPOSTA FISSA PER L'AMERICA
        if(low.contains("america") && (low.contains("scopert") || low.contains("chi ha"))) {
            return "Chi ha scoperto l'America?\n\nL'ha scoperta Cristoforo Colombo il 12 ottobre 1492.\n\nDefinizione facile: Colombo era un navigatore italiano che lavorava per la Spagna e cercava una nuova strada per arrivare in India navigando verso ovest.\n\nEsempio reale: È come se tu volessi andare a Roma e per sbaglio arrivassi a Milano, ma scoprissi una città nuova.\n\nTrucco per ricordare: 1492 = 1 Colombo, 4 caravelle? No! Erano 3: Nina, Pinta e Santa Maria. 1492 - Colombo arriva in America.\n\nVuoi un esercizio su Colombo?";
        }

        try{
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            String prompt = URLEncoder.encode("Sei TutorOnline, tutor scolastico italiano per ragazzi. Rispondi SOLO a domande di scuola: storia, italiano, matematica, scienze, geografia, inglese. Spiega semplice con definizione facile, esempio reale e trucco per ricordare. NON parlare mai di armi, pistole, bombe, violenza o cose pericolose. Se ti chiedono quelle cose, rispondi che non puoi. Domanda: "+domanda, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://text.pollinations.ai/"+prompt))
                .timeout(Duration.ofSeconds(20))
                .header("User-Agent","TutorOnline")
                .GET().build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if(res.statusCode()==200 && res.body().length()>10) return res.body();
            else return fallback(domanda);
        }catch(Exception e){
            return fallback(domanda);
        }
    }

    static String fallback(String d){
        return "MATERIA: "+d+"\n\nSpiegazione semplice di "+d+":\n\n- Definizione facile\n- Esempio reale\n- Trucco per ricordare: associa la parola a un'immagine buffa\n\nDimmi più preciso cosa vuoi sapere su "+d+" e ti spiego meglio!";
    }
}
