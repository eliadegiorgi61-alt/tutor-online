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
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>TutorOnline V5 AGI</title><style>body{background:#050505;color:#fff;font-family:system-ui;margin:0}header{background:#0a0a0a;padding:12px;text-align:center;border-bottom:1px solid #1a1a1a}h1{margin:0;font-size:26px}span{color:#00ff88}.badge{color:#00ff88;font-size:10px;letter-spacing:2px;font-weight:bold}#chat{height:55vh;overflow-y:auto;background:#0a0a0a;border-radius:18px;padding:12px;border:1px solid #222;margin:10px}.msg{margin:8px 0;padding:10px 12px;border-radius:14px;max-width:90%;line-height:1.5;font-size:14px}.user{background:#00ff88;color:#000;margin-left:auto}.bot{background:#1a1a1a;border-left:3px solid #00ff88;white-space:pre-wrap}.bar{display:flex;gap:6px;margin:10px}input,select{flex:1;padding:12px;border-radius:12px;border:1px solid #333;background:#111;color:white}button{background:#00ff88;color:black;border:none;border-radius:12px;padding:12px 16px;font-weight:900}</style></head><body><header><h1>Tutor<span>Online</span> V5 AGI</h1><div class='badge'>OLTRE CHATGPT - V5</div></header><div style='max-width:800px;margin:0 auto;padding:10px'><div id='chat'><div class='msg bot'>V5 AGI pronta. Piu forte di ChatGPT:\n- Ragiono step-by-step\n- Trucchi italiani\n- Foto compiti + Voce\n- Creo verifiche\n\nScegli materia e chiedi!</div></div><div class='bar'><select id='mat'><option value='generale'>Generale</option><option value='matematica'>Matematica</option><option value='storia'>Storia</option><option value='scienze'>Scienze</option><option value='inglese'>Inglese</option></
