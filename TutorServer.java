import java.io.*;
import java.net.*;

public class TutorServer {
  public static void main(String[] args) throws Exception {
    int port = 10000;
    String p = System.getenv("PORT");
    if (p!= null) port = Integer.parseInt(p);
    ServerSocket server = new ServerSocket(port);
    System.out.println("LIVE " + port);
    while (true) {
      Socket client = server.accept();
      new Thread(() -> {
        try {
          BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
          String first = in.readLine();
          if (first == null) { client.close(); return; }
          String path = "/";
          try { path = first.split(" ")[1]; } catch (Exception e) {}
          String line;
          while ((line = in.readLine())!= null &&!line.isEmpty()) {}
          String q = "";
          if (path.contains("q=")) {
            q = path.split("q=")[1];
            if (q.contains("&")) q = q.split("&")[0];
            q = URLDecoder.decode(q, "UTF-8");
          }
          String body;
          String contentType;
          if (path.startsWith("/api/ask")) {
            contentType = "text/plain; charset=UTF-8";
            if (q.isEmpty()) body = "Scrivi una domanda!";
            else body = "MATERIA: AUTO\nARGOMENTO: " + q + "\n\n" + q + " spiegato semplice:\n- Definizione facile\n- Esempio reale\n- Trucco per ricordare\n\nChiedimi un esercizio su " + q + "!";
          } else {
            contentType = "text/html; charset=UTF-8";
            body = "<!doctype html><html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>TutorOnline</title><style>body{font-family:system-ui;background:#111;color:#fff;text-align:center;padding:20px}h1 span{color:#0f8}#box{background:#222;padding:20px;border-radius:15px;max-width:600px;margin:auto}input{width:85%;padding:15px;border-radius:10px;border:0}button{padding:15px 25px;background:#0f8;border:0;border-radius:10px;margin-top:10px;font-weight:bold}#r{margin-top:15px;background:#333;padding:15px;border-radius:10px;text-align:left;white-space:pre-wrap}</style></head><body><h1>Tutor<span>Online</span></h1><p>Sa tutte le materie!</p><div id=box><input id=q placeholder='Scrivi: equazioni, Dante, fotosintesi...'><br><button onclick='go()'>Chiedi</button><div id=r>...</div></div><script>async function go(){let d=document.getElementById('q').value;let r=document.getElementById('r');if(!d){r.innerText='Scrivi!';return;}r.innerText='...';let t=await (await fetch('/api/ask?q='+encodeURIComponent(d))).text();r.innerText=t;}</script></body></html>";
          }
          byte[] bytes = body.getBytes("UTF-8");
          String head = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nAccess-Control-Allow-Origin: *\r\nContent-Length: " + bytes.length + "\r\n\r\n";
          OutputStream out = client.getOutputStream();
          out.write(head.getBytes("UTF-8"));
          out.write(bytes);
          out.flush();
          client.close();
        } catch (Exception e) { e.printStackTrace(); }
      }).start();
    }
  }
}
