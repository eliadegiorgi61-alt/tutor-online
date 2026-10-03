import com.sun.net.httpserver.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String p = System.getenv("PORT");
        if (p!= null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", ex -> {
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V10000</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#ff00ff,#ffff00);color:#000;padding:14px;text-align:center;font-weight:900}.v{background:#000;color:#ff0;padding:3px 8px;border-radius:10px;font-size:11px}#c{height:75vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap}.u{background:#ff00ff;color:#fff;margin-left:auto;max-width:88%}.b{background:#111;border-left:4px solid #ff00ff}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#ff00ff;color:#fff;border:none;padding:12px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:10px}</style></head><body><div class=top>V10000 GOD <span class=v>10000X</span></div><div id=c><div class='msg b'>V10000 GOD ATTIVA - Oltre umano</div></div><div class=row><input id=q placeholder='Chiedi a V10000 GOD...'><button onclick=s()>></button></div><div class=row><button onclick=foto() style='flex:1;background:#111;color:#ff00ff;border:1px solid #ff00ff'>FOTO</button><button onclick=ver() style='flex:1;background:#111;color:#fff;border:1px solid #333'>Verifica</button></div><script>function a(t,c){var d=document.getElementById('c');var e=document.createElement('div');e.className='msg '+c;e.innerText=t;d.appendChild(e);d.scrollTop=d.scrollHeight;return e;}async function s(){var i=document.getElementById('q');var d=i.value.trim();if(!d)return;a(d,'u');i.value='';var b=a('V10000 GOD...','b');try{var r=await fetch('/ask?d='+encodeURIComponent(d));var t=await r.text();b.innerText=t;}catch(e){b.innerText='Errore';}}function ver(){var m=prompt('Materia?');if(m){document.getElementById('q').value='Verifica su '+m;s();}}function foto(){a('[Foto]','u');var b=a('V10000 vede foto...','b');setTimeout(async function(){var r=await fetch('/ask?d=foto');var t=await r.text();b.innerText=t;},500);}</script></body></html>";
            ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
            ex.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            ex.close();
        });
        server.createContext("/ask", ex -> {
            String qs = ex.getRequestURI().getQuery();
            String dom = "";
            if(qs!=null) for(String s
