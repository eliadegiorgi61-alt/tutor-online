import java.io.*;
import java.net.*;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String env = System.getenv("PORT");
        if (env!= null) port = Integer.parseInt(env);
        ServerSocket ss = new ServerSocket(port);
        System.out.println("TutorOnline LIVE su " + port);
        while (true) {
            Socket s = ss.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String first = in.readLine();
                    if (first == null) { s.close(); return; }
                    String path = first.split(" ")[1];
                    String line;
                    while ((line = in.readLine())!= null &&!line.isEmpty()) {}

                    String domanda = "";
                    if (path.contains("q=")) {
                        domanda = path.split("q=")[1];
                        if (domanda.contains("&")) domanda = domanda.split("&")[0];
                        domanda = URLDecoder.decode(domanda, "UTF-8");
                    }

                    String body;
                    String type;
                    if (path.startsWith("/api/ask")) {
                        type = "text/plain";
                        if (domanda.isEmpty()) body = "Scrivi una domanda!";
                        else body = "MATERIA: RILEVATA AUTOMATICAMENTE\nARGOMENTO: " + domanda + "\n\n1) SPIEGAZIONE SEMPLICE:\n" + domanda + " spiegato facile.\n\n2) ESEMPIO REALE:\nPensa a " + domanda + " nella vita di tutti i giorni.\n\n3) TRUCCO PER IL 10:\nDefinizione -> Esempio -> Perche serve -> Esercizio.\n\nVuoi un riassunto o un esercizio su " + domanda + "?";
                    } else {
                        type = "text/html";
                        body = "<!doctype html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline</title><style>body{font-family:system-ui;background:#111;color:#fff;text-align:center;padding:20px}h1 span{color:#0f8}#box{background:#222;padding:20px;border-radius:15px;max-width:600px;margin:20px auto}input{width:85%;padding:15px;border-radius:10px;border:0;font-size:16px}button{padding:15px 25px;background:#0f8;border:0;border-radius:10px;margin-top:12px;font-weight:bold;cursor:pointer}#r{margin-top:20px;text-align:left;background:#333;padding:15px;border-radius:10px;white-space:pre-wrap;line-height:1.6}</style></head><body><h1>Tutor<span>Online</span></h1><p>Chiedimi QUALSIASI materia!</p><div id=box><input id=q placeholder='Es: equazioni, fotosintesi, Dante...'><br><button onclick='go()'>Chiedi al Tutor</button><div id=r>Scrivi qualcosa...</div></div><script>async function go(){let d=document.getElementById('q').value;let r=document.getElementById('r');if(!d){r.innerText='Scrivi una domanda!';return;}r.innerText='Sto pensando...';let res=await fetch('/api/ask?q='+encodeURIComponent(d));r.innerText=await res.text();}</script></body></html>";
                    }
                    byte[] b = body.getBytes("UTF-8");
                    String header = "HTTP/1.1 200 OK\r\nContent-Type: " + type + "; charset=UTF-8\r\nAccess-Control-Allow-Origin: *\r\nContent-Length: " + b.length + "\r\n\r\n";
                    OutputStream out = s.getOutputStream();
                    out.write(header.getBytes("UTF-8"));
                    out.write(b);
                    out.flush();
                    s.close();
                } catch (Exception e) { e.printStackTrace(); }
            }).start();
        }
    }
}
