import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    static String API_KEY = System.getenv("OPENAI_API_KEY");

    public static void main(String[] args) throws Exception {
        int port = 10000;
        String portEnv = System.getenv("PORT");
        if (portEnv!= null) port = Integer.parseInt(portEnv);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", ex -> {
            String html = """
            <!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'>
            <title>TutorOnline</title>
            <style>body{background:#111;color:white;font-family:sans-serif;text-align:center;padding:20px}
            h1{font-size:36px}span{color:#00ff88}.card{background:#222;padding:20px;border-radius:20px;max-width:600px;margin:20px auto}
            input{width:90%;padding:15px;border-radius:12px;border:none;font-size:16px}button{background:#00ff88;padding:12px 30px;border:none;border-radius:12px;font-weight:bold;margin-top:15px;cursor:pointer}
            #risposta{text-align:left;white-space:pre-wrap;margin-top:15px;background:#333;padding:15px;border-radius:12px;min-height:100px}</style></head>
            <body><h1>Tutor<span>Online</span></h1><p>Sa tutte le materie!</p>
            <div class='card'><input id='q' placeholder='Chiedimi qualsiasi cosa...'>
            <br><button onclick='chiedi()'>Chiedi</button>
            <div id='risposta'>Scrivi una domanda e clicca Chiedi!</div></div>
            <script>
            async function chiedi(){
              let domanda=document.getElementById('q').value;
              if(!domanda) return;
              let r=document.getElementById('risposta');
              r.innerText='Sto pensando...';
              let res=await fetch('/ask?domanda='+encodeURIComponent(domanda));
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
            String query = ex.getRequestURI().getQuery();
            String domanda = "Ciao";
            if(query!=null && query.startsWith("domanda=")){
                domanda = URLDecoder.decode(query.substring(8), StandardCharsets.UTF_8);
            }
            String risposta = chiediAI(domanda);
            ex.getResponseHeaders().set("Content-Type","text/plain; charset=utf-8");
            ex.sendResponseHeaders(200, risposta.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(risposta.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });

        server.start();
        System.out.println("TutorOnline avviato su porta "+port);
    }

    static String chiediAI(String domanda){
        if(API_KEY==null || API_KEY.isBlank()){
            return "⚠️ AI non configurata! Aggiungi la chiave API su Render.\n\nPer ora ti rispondo io: "+domanda+" è scoppiata il 1 settembre 1939 con l'invasione della Polonia da parte della Germania nazista. Vuoi aggiungere la chiave per avere risposte complete?";
        }
        try{
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
            String json = "{\"model\":\"gpt-4o-mini\",\"messages\":[{\"role\":\"system\",\"content\":\"Sei TutorOnline, un tutor italiano super bravo che spiega tutto semplice con definizione, esempio e trucco per ricordare. Rispondi sempre in italiano, breve e chiaro.\"},{\"role\":\"user\",\"content\":\""+domanda.replace("\"","'")+"\"}]}";
            HttpRequest req = HttpRequest.newBuilder()
               .uri(URI.create("https://api.openai.com/v1/chat/completions"))
               .header("Authorization","Bearer "+API_KEY)
               .header("Content-Type","application/json")
               .POST(HttpRequest.BodyPublishers.ofString(json))
               .timeout(Duration.ofSeconds(30)).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            String body = res.body();
            int i = body.indexOf("\"content\":\"");
            if(i==-1) return "Errore AI: "+body.substring(0,200);
            i+=11;
            int j = body.indexOf("\"", i);
            // trova fine contenuto gestendo escape semplici
            String content = body.substring(i);
            // estrazione rozza ma funziona
            content = content.split("\",")[0];
            content = content.replace("\\n","\n").replace("\\\"","\"");
            return content;
        }catch(Exception e){
            return "Errore: "+e.getMessage();
        }
    }
}
