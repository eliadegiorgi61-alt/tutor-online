import com.sun.net.httpserver.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000; 
        String p = System.getenv("PORT"); 
        if (p != null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", ex -> {
            String html = """
            <!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'>
            <title>TutorOnline ULTRA V3</title>
            <style>
            body{background:#0a0a0a;color:white;font-family:system-ui;text-align:center;padding:15px;margin:0}
            h1{font-size:38px;margin:10px}span{color:#00ff88}
            .badge{background:#00ff88;color:black;padding:5px 12px;border-radius:20px;font-size:12px;font-weight:bold}
            .card{background:#1c1c1e;padding:20px;border-radius:24px;max-width:700px;margin:20px auto}
            input{width:100%;padding:16px;border-radius:14px;border:1px solid #333;background:#2c2c2e;color:white;font-size:16px;box-sizing:border-box}
            .modes{display:flex;gap:8px;margin:12px 0;flex-wrap:wrap;justify-content:center}
            .mode{padding:8px 14px;border-radius:20px;background:#2c2c2e;border:1px solid #444;color:#aaa;font-size:13px;cursor:pointer}
            .mode.active{background:#00ff88;color:black;font-weight:bold}
            button.main{background:#00ff88;color:black;padding:14px 40px;border:none;border-radius:14px;font-weight:900;font-size:16px;margin-top:10px;cursor:pointer;width:100%}
            #risposta{text-align:left;white-space:pre-wrap;margin-top:16px;background:#2c2c2e;padding:18px;border-radius:14px;min-height:120px;line-height:1.6;font-size:15px;border-left:4px solid #00ff88}
            </style></head><body>
            <h1>Tutor<span>Online
