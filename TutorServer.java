import java.io.*;
import java.net.*;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String envPort = System.getenv("PORT");
        if (envPort!= null) port = Integer.parseInt(envPort);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("TutorOnline UNIVERSALE LIVE su porta " + port);
        while (true) {
            Socket client = serverSocket.accept();
            new Thread(() -> handleClient(client)).start();
        }
    }

    static void handleClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();
            String firstLine = in.readLine();
            if (firstLine == null) { client.close(); return; }
            String path = "/";
            if (firstLine.startsWith("GET")) {
                path = firstLine.split(" ")[1];
            }
            String line;
            while ((line = in.readLine())!= null &&!line.isEmpty()) {}

            String body;
            String contentType = "text/html; charset=UTF-8";
            if (path.startsWith("/api/ask")) {
                String domanda = "";
                if (path.contains("q=")) {
                    domanda = path.split("q=")[1];
                    if (domanda.contains("&")) domanda = domanda.split("&")[0];
                    domanda = URLDecoder.decode(domanda, "UTF-8");
                }
                body = generaRisposta(domanda);
                contentType = "text/plain; charset=UTF-8";
            } else {
                body = getHtml();
            }

            byte[] bodyBytes = body.getBytes("UTF-8");
            String http = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nAccess-Control-Allow-Origin: *\r\nContent-Length: " + bodyBytes.length + "\r\n\r\n";
            out.write(http.getBytes("UTF-8"));
            out.write(bodyBytes);
            out.flush();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getHtml() {
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><title>TutorOnline - Sa Tutto</title><style>body{font-family:system-ui;background:#0f0f0f;color:white;text-align:center;padding:20px}h1{font-size:32px}span{color:#00ff88}#box{background:#1e1e1e;padding:20px;border-radius:15px;max-width:600px;margin:20px auto}input{width:80%;padding:15px;border-radius:10px;border:none;font-size:16px}button{padding:15px 25px;background:#00ff88;border:none;border-radius:10px;font-weight:bold;margin-top:10px;cursor:pointer}#r{margin-top:20px;text-align:left;background:#2a2a2a;padding:15px;border-radius:10px;white-space:pre-wrap;line-height:1.5}</style></head><body><h1>Tutor<span>Online</span> - SA TUTTO!</h1><p>Chiedimi QUALSIASI materia e QUALSIASI argomento!</p><div id='box'><input id='q' placeholder='Es: equazioni, rivoluzione francese, fotosintesi...'><br><button onclick='chiedi()'>Chiedi al Tutor</button><div id='r'>Scrivi una domanda e ti spiego tutto...</div></div><script>async function chiedi(){let d=document.getElementById('q').value;let r=document.getElementById('r');if(!d){r.innerText='Scrivi qualcosa!';return;}r.innerText='Sto pensando...';let res=await fetch('/api/ask?q='+encodeURIComponent(d));let t=await res.text();r.innerText=t;}</script></body></html>";
    }

    static String generaRisposta(String domanda) {
        if (domanda == null || domanda.trim().isEmpty()) return "Fammi una domanda su qualsiasi materia! Es: matematica, storia, scienze, italiano...";
        String d = domanda.toLowerCase();
        String materia = "GENERALE";
        if (d.contains("mate") || d.contains("equaz") || d.contains("geometr") || d.contains("algebr") || d.contains("funzione")) materia = "MATEMATICA";
        else if (d.contains("storia") || d.contains("guerra") || d.contains("rivoluzione") || d.contains("roma")) materia = "STORIA";
        else if (d.contains("italiano") || d.contains("grammatica") || d.contains("dante") || d.contains("manzoni")) materia = "ITALIANO";
        else if (d.contains("inglese") || d.contains("english")) materia = "INGLESE";
        else if (d.contains("scienze") || d.contains("biologia") || d.contains("dna") || d.contains("cellula") || d.contains("fotosintesi")) materia = "SCIENZE";
        else if (d.contains("fisica")) materia = "FISICA";
        else if (d.contains("chimica") || d.contains("atomo")) materia = "CHIMICA";
        else if (d.contains("geografia")) materia = "GEOGRAFIA";
        else if (d.contains("filosofia")) materia = "FILOSOFIA";
        else if (d.contains("informatica") || d.contains("java") || d.contains("python") || d.contains("codice")) materia = "INFORMATICA";

        return "MATERIA: " + materia + "\nARGOMENTO: " + domanda + "\n\n1) COS'E' IN BREVE:\n" + domanda + " e' un argomento chiave di " + materia + ".\n\n2) SPIEGAZIONE SEMPLICE:\n- Parti dalla base che gia' conosci.\n- Per " + domanda + " devi capire PERCHE' esiste e A COSA serve.\n- Collegalo a un esempio reale, lo ricordi al 300% in piu'!\n\n3) ESEMPIO FACILE:\nSpiega " + domanda + " a un bambino di 10 anni con parole semplici.\n\n4) TRUCCO DA 10:\nSchema: Definizione -> Esempio -> Perche' serve -> Esercizio.\n\n5) PROVA TU:\nRiscrivi con parole tue cos'e' " + domanda + ". Se ci riesci, l'hai imparato!\n\nVuoi un esercizio o un riassunto su " + domanda + "? Chiedimelo!";
    }
}
