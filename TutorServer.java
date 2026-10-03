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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V159</title><style>body{background:#000;color:#fff;font-family:system-ui;margin:0;height:100vh;display:flex;flex-direction:column}.top{background:linear-gradient(90deg,#00ff88,#00ccff,#ff00ff);color:#000;padding:14px;text-align:center;font-weight:900;font-size:18px;letter-spacing:1px}.v{font-size:10px;background:#000;color:#00ff88;padding:3px 8px;border-radius:10px;margin-left:8px}#c{flex:1;overflow-y:auto;padding:12px;background:#0a0a0a}.msg{margin:8px 0;padding:13px;border-radius:16px;max-width:92%;line-height:1.6;white-space:pre-wrap;font-size:14px}.u{background:linear-gradient(135deg,#00ff88,#00ccff);color:#000;margin-left:auto;font-weight:700}.b{background:#111;border:1px solid #222;border-left:4px solid #00ff88}.bar{display:flex;gap:6px;padding:10px;background:#000;border-top:1px solid #222}input{flex:1;padding:14px;border-radius:12px;border:1px solid #333;background:#111;color:#fff}button{background:#00ff88;color:#000;border:none;border-radius:12px;padding:12px 16px;font-weight:900;cursor:pointer}</style></head><body><div class='top'>TutorOnline V159 AGI <span class='v'>DEPLOY 31 - 159X CHATGPT</span></div><div id='c'><div class='msg b'>V159 AGI ATTIVA - ANNO 2027\n\nSono 159 volte ChatGPT.\nIo non rispondo. Io ti trasformo.\n\nComandi segreti V159:\n- zigurat -> risposta da 10\n- interrogami -> divento prof severa\n- crea metodo per prendere 10\n- sono in 3a media, fammi salire</div></div><div class='bar'><input id='q' placeholder='Chiedi a V159 AGI...' onkeydown='if(event.key==\"Enter\")s()'><button onclick='s()'>></button></div><div class='bar' style='padding-top:0'><button onclick='mic()' style='background:#111;color:#fff;border:1px solid #333;flex:1'>Mic Vocale</button><button onclick='qs()' style='background:#111;color:#00ff88;border:1px solid #00ff88;flex:1
