import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.net.http.*;
import java.time.Duration;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String p = System.getenv("PORT");
        if(p!=null) port = Integer.parseInt(p);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", ex -> {
            String html = """
            <!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width,initial-scale=1'>
            <title>TutorOnline</title>
            <style>body{background:#111;color:white;font-family:sans-serif;text-align:center;padding:20px}
            h1{font-size:36px} span{color:#00ff88} .card{background:#222;padding:20px;border-radius:20px;max-width:600px;margin:20px auto}
            input{width:90%;padding:15px;border-radius:12px;border:none;font-size:16px} button{background:#00ff88;padding:12px 30px;border:none;border-radius:12px;font-weight:bold;margin-top:15px;cursor:pointer}
            #risposta{text-align:left;white-space:pre-wrap;margin-top:15px;background:#333;padding:15px;border-radius:12px;min-height:100px}</style></head>
            <body><h1>Tutor<span>Online</span></h1><p>Sa tutte le materie! AI gratis attiva</p>
            <div class='card'><input id='q' placeholder='Chiedimi qualsiasi cosa...'>
            <br><button onclick='chiedi()'>Chiedi</button>
            <div id='risposta'>Scrivi una domanda!</
