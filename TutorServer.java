import com.sun.net.httpserver.*;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", exchange -> {
            String html = """
                <!DOCTYPE html>
                <html lang="it">
                <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>TutorOnline - Brindisi</title>
                <style>
                body{font-family:sans-serif; text-align:center; padding:40px 20px; margin:0; background:#f8f9ff}
                h1{font-size:44px; margin:0; line-height:1.1} h1 span{color:#4f46e5}
                p{color:#555; font-size:18px; margin-top:15px}
                .box{background:white; max-width:400px; margin:30px auto; padding:25px; border-radius:16px; box-shadow:0 4px 20px rgba(0,0,0,0.08)}
                input{width:100%; padding:14px; border:1px solid #ddd; border-radius:10px; font-size:16px; box-sizing:border-box}
                .btn{display:block; width:100%; margin-top:15px; background:#25D366; color:white; padding:16px; border-radius:12px; text-decoration:none; font-weight:bold; font-size:18px}
                .btn2{background:#4f46e5; margin-top:10px}
                </style></head>
                <body>
                <h1>Tutor<span>Online</span> e' <br>LIVE</h1>
                <p>Matematica, Inglese, Informatica - Brindisi e Online</p>
                
                <div class="box">
                  <p style="margin-top:0; color:#111; font-weight:bold">Hai una domanda?</p>
                  <input id="q" placeholder="Es. Mi aiuti con le equazioni?">
                  <a class="btn" onclick="domanda()" href="#">💬 Chiedi su WhatsApp</a>
                  <p style="font-size:13px; color:#999; margin-top:15px">Risposta in 5 minuti - Elia</p>
                </div>

                <script>
                function domanda(){
                  var testo = document.getElementById('q').value;
                  if(testo == '') testo = 'Ciao Elia, ho una domanda per le ripetizioni!';
                  var numero = '393331234567'; // <--- CAMBIA QUI IL TUO NUMERO
                  window.open('https://wa.me/'+numero+'?text='+encodeURIComponent(testo), '_blank');
                }
                </script>
                <p style="margin-top:30px; color:#aaa; font-size:14px">2026 TutorOnline</p>
                </body></html>
                """;
            byte[] bytes = html.getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        });
        
        server.start();
        System.out.println("Server LIVE");
    }
}
