import java.io.*; import java.net.*; import java.util.*;
public class TutorServer {
 public static void main(String[] a) throws Exception {
  int port=10000; String e=System.getenv("PORT"); if(e!=null) port=Integer.parseInt(e);
  ServerSocket ss=new ServerSocket(port); System.out.println("LIVE "+port);
  while(true){ Socket s=ss.accept(); new Thread(()->{ try{
   BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
   String first=in.readLine(); if(first==null){s.close();return;}
   String path=first.split(" ")[1]; String l; while((l=in.readLine())!=null &&!l.isEmpty()){}
   String q=""; if(path.contains("q=")){ q=path.split("q=")[1]; if(q.contains("&")) q=q.split("&")[0]; q=URLDecoder.decode(q,"UTF-8"); }
   String body=path.startsWith("/api/ask")? "MATERIA: TUTTE\nARGOMENTO: "+q+"\n\n1)COS'E': "+q+" spiegato semplice.\n2)ESEMPIO: pensa a "+q+" nella vita vera.\n3)TRUCCO 10: Definizione->Esempio->Perche serve.\n\nVuoi esercizio su "+q+"? Chiedimelo!" : "<!doctype html><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'><title>TutorOnline</title><style>body{font-family:system-ui;background:#111;color:#fff;text-align:center;padding:20px}h1 span{color:#0f8}#box{background:#222;padding:20px;border-radius:15px;max-width:600px;margin:auto}input{width:80%;padding:15px;border-radius:10px;border:0}button{padding:15px 20px;background:#0f8;border:0;border-radius:10px;margin-top:10px;font-weight:bold}#r{margin-top:20px;text-align:left;background:#333;padding:15px;border-radius:10px;white-space:pre-wrap}</style><h1>Tutor<span>Online</span></h1><p>Sa TUTTE le materie!</p><div id=box><input id=q placeholder='Es: equazioni, storia, dna...'><br><button onclick='go()'>Chiedi</button><div id=r>Scrivi una domanda...</div></div><script>async function go(){let d=document.getElementById('q').value;let r=document.getElementById('r');if(!d){r.innerText='Scrivi!';return;}r.innerText='...';let t=await (await fetch('/api/ask?q='+encodeURIComponent(d))).text();r.innerText=t;}</script>";
   byte[] b=body.getBytes("UTF-8");
   String h="HTTP/1.1 200 OK\r\nContent-Type: "+(path.startsWith("/api/ask")?"text/plain":"text/html")+"; charset=UTF-8\r\nAccess-Control-Allow-Origin: *\r\nContent-Length: "+b.length+"\r\n\r\n";
   s.getOutputStream().write(h.getBytes()); s.getOutputStream().write(b); s.close();
  }catch(Exception ex){ex.printStackTrace();} }).start(); }
 }
}
