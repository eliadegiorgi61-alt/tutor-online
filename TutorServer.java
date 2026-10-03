import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", exchange -> {
            String html = """
                <!DOCTYPE html>
                <html lang="it"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>TutorOnline</title>
                <style>
                body{font-family:sans-serif; text-align:center; padding:30px 20px; margin:0; background:#f8f9ff}
                h1{font-size:36px; margin:0} h1 span{color:#4f46e5}
                .box{background:white; max-width:450px; margin:25px auto; padding:22px; border-radius:16px; box-shadow:0 4px 20px rgba(0,0,0,0.08); text-align:left}
                input{width:100%; padding:14px; border:1px solid #ddd; border-radius:10px; font-size:16px; box-sizing:border-box}
                .btn{width:100%; margin-top:12px; background:#4f46e5; color:white; padding:14px; border:none; border-radius:10px; font-weight:bold; font-size:16px; cursor:pointer}
                #risposta{margin-top:15px; background:#eef2ff; padding:15px; border-radius:10px; display:none; white-space:pre-wrap; line-height:1.5}
                </style></head>
                <body>
                <h1>Tutor<span>Online</span> LIVE</h1>
                <p>Matematica, Inglese, Informatica - Brindisi</p>
                <div class="box">
                  <b>Fai una domanda a TutorOnline:</b>
                  <input id="q" placeholder="Es. cos'e' il comun divisore?">
                  <button class="btn" onclick="rispondi()">Chiedi</button>
                  <div id="risposta"></div>
                </div>
                <script>
                function rispondi(){
                  var d = document.getElementById('q').value.toLowerCase();
                  var r = document.getElementById('risposta');
                  r.style.display='block';
                  if(d.includes('comun divisore') || d.includes('mcd')){
                    r.innerText = "MASSIMO COMUN DIVISORE (MCD):\\nE' il numero piu' grande che divide due numeri.\\n\\nEsempio: MCD di 12 e 18 = 6\\n- Divisori di 12: 1,2,3,4,6,12\\n- Divisori di 18: 1,2,3,6,9,18\\nIl piu' grande in comune e' 6.\\n\\nServe per semplificare le frazioni!";
                  } else if(d.includes('equazione')){
                    r.innerText = "Un'equazione e' una uguaglianza con una incognita (x).\\nEs: x + 5 = 12 -> x = 7";
                  } else if(d!=''){
                    r.innerText = "Bella domanda! '" + d + "'\\n\\nPer ora so rispondere a: comun divisore, equazioni. Scrivi 'MCD' per la spiegazione completa!";
                  } else {
                    r.innerText = "Scrivi una domanda sopra! Prova con: cos'e' il comun divisore?";
                  }
                }
                </script>
                </body></html>
                """;
            byte[] b = html.getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, b.length);
            exchange.getResponseBody().write(b);
            exchange.getResponseBody().close();
        });
        server.start();
    }
}
