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
            String html = "<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>V1000</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;padding:10px}.top{background:linear-gradient(90deg,#ff00ff,#00ff88,#00ccff);color:#000;padding:14px;text-align:center;font-weight:900;font-size:18px}.v{background:#000;color:#ff00ff;padding:4px 10px;border-radius:10px;font-size:11px}#c{height:60vh;overflow-y:auto;background:#0a0a0a;padding:12px;border-radius:14px;border:1px solid #222}.msg{margin:8px 0;padding:12px;border-radius:14px;white-space:pre-wrap;line-height:1.6;font-size:14px}.u{background:linear-gradient(135deg,#ff00ff,#00ff88);color:#000;margin-left:auto;max-width:88%;font-weight:700}.b{background:#111;border-left:4px solid #ff00ff}input{flex:1;padding:12px;background:#111;color:#fff;border:1px solid #333;border-radius:10px}button{background:#ff00ff;color:#fff;border:none;padding:11px 14px;border-radius:10px;font-weight:900}.row{display:flex;gap:6px;margin-top:8px}.up{border:2px dashed #ff00ff;padding:10px;text-align:center;border-radius:12px;margin-top:8px;color:#ff00ff}</style></head><body><div class=top>TutorOnline V1000 AGI <span class=v>1000X - FOTO COMPITI</span></div><div id=c><div class='msg b'>V1000 ATTIVA - L AI CHE FA I COMPITI DA FOTO\n\n1) Scrivi: zigurat\n2) Oppure: clicca FOTO e fotografa il libro\n3) Oppure: interrogami su storia</div></div><div class=up onclick=document.getElementById('f').click()>FOTO COMPITI - CL
