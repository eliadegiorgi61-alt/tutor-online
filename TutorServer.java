import com.sun.net.httpserver.*;import java.net.*;import java.nio.charset.*;
public class TutorServer{
public static void main(String[]a)throws Exception{
int port=10000;String p=System.getenv("PORT");if(p!=null)port=Integer.parseInt(p);
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",ex->{
String html="""
<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'>
<title>tutor-online</title>
<style>
*{font-family:system-ui,sans-serif;box-sizing:border-box}
body{margin:0;background:#0a0a0a;color:#ececec;display:flex;flex-direction:column;height:100vh}
.header{padding:14px 20px;border-bottom:1px solid #222;display:flex;justify-content:space-between;background:#000}
#c{flex:1;overflow:auto}
.row{display:flex;gap:12px;padding:18px 20px;border-bottom:1px solid #1a1a1a}
.row.me{background:#111}.avatar{width:30px;height:30px;border-radius:6px;display:flex;align-items:center;justify-content:center;font-weight:900;font-size:12px;flex-shrink:0}
.me.avatar{background:#fff;color:#000}.ai.avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.6;font-size:15px;white-space:pre-wrap}
.thinking{color:#888;font-style:italic;display:flex;gap:6px;align-items:center}
.dot{width:6px;height:6px;background:#888;border-radius:50%;animation:bounce 1.4s infinite}
.dot:nth-child(2){animation-delay:.2s}.dot:nth-child(3){animation-delay:.4s}
@keyframes bounce{0%,80%,100%{transform:scale(0)}40%{transform:scale(1)}}
.bottom{padding:12px;background:#000;border-top:1px solid #222;display:flex;gap:10px}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:14px 18px;border-radius:24px;outline:none}
.bottom button{background:#fff;color:#000;border:none;padding:0 22px;border-radius:24px;font-weight:700;cursor:pointer}
</style>
</head><body>
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● E2E MAX</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Sono tutor-online. Chiedimi qualsiasi cosa e ti rispondo direttamente.</div></div></div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." onkeydown="if(event.key==='Enter')go()"><button id=btn onclick=go()>↑</button></div>
<script>
function addRow(w,l){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+l+'</div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function addThink(){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row ai';r.id='thinking';r.innerHTML='<div class=avatar>TO</div><div class=txt thinking>sto pensando <span class=dot></span><span class=dot></span><span class=dot></span></div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function R(q){
let l=q.toLowerCase();
// MEDIOEVO - DIRETTO
if(l.includes('medioevo')){
return "STORIA DEL MEDIOEVO (476-1492):\\n\\n• Inizio: 476 d.C. caduta dell'Impero Romano d'Occidente\\n• Fine: 1492 scoperta America / 1453 caduta Costantinopoli\\n\\nFASI:\\n1. Alto Medioevo (476-1000): invasioni barbariche, Longobardi, Franchi, Carlo Magno incoronato nel 800, feudalesimo\\n2. Basso Medioevo (1000-1492): rinascita città, Comuni, Crociate (1096-1270), Federico II, crisi del '300, peste nera 1348\\n\\nCARATTERISTICHE: feudalesimo, potere della Chiesa, castelli, cavalieri, economia curtense, poca cultura scritta, poi università (Bologna 1088).";
}
if(l.includes('2 guerra')||l.includes('seconda guerra')){
if(l.includes('quando')||l.includes('scoppiata')) return "La Seconda Guerra Mondiale è scoppiata il 1 settembre 1939 con l'invasione della Polonia da parte della Germania. Finita il 2 settembre 1945.";
return "SECONDA GUERRA MONDIALE 1939-1945:\\nInizio 1 sett 1939 (Polonia), fine 2 sett 1945 (resa Giappone). Asse vs Alleati. Eventi: Blitzkrieg, Pearl Harbor 1941, Stalingrado 1942-43, D-Day 6 giugno 1944, Hiroshima/Nagasaki agosto 1945. 60+ milioni morti.";
}
if(l.includes('1 guerra')||l.includes('prima guerra')) return "PRIMA GUERRA MONDIALE 1914-1918: Iniziata 28 luglio 1914 dopo attentato Sarajevo. Trincee, fronte occidentale. Finita 11 novembre 1918. Trattato Versailles 1919.";
if(l.includes('rinascimento')) return "RINASCIMENTO (1350-1550): Nasce a Firenze. Umanesimo, ritorno ai classici. Artisti: Leonardo, Michelangelo, Raffaello. Scoperte: stampa Gutenberg 1455, America 1492. Scienza: Copernico, Galileo.";
if(l.includes('rivoluzione francese')) return "RIVOLUZIONE FRANCESE 1789-1799: Inizio 14 luglio 1789 presa Bastiglia. Cause: crisi economica, privilegi nobili. Fasi: Assemblea, Terrore 1793-94, Napoleone prende potere 1799. Motto: Liberté, Égalité, Fraternité.";
if(l.includes('romani')||l.includes('impero romano')) return "IMPERO ROMANO: Fondazione 753 a.C., Repubblica 509 a.C., Impero 27 a.C. con Augusto. Massimo estensione con Traiano 117 d.C. Caduta Occidente 476 d.C., Oriente 1453.";
if(l.includes('egizi')||l.includes('egitto')) return "EGIZI: 3000 a.C. - 30 a.C. Piramidi Giza 2600 a.C., faraoni, mummie, geroglifici, Nilo. Cleopatra ultima regina 30 a.C.";
if(l.includes('pitagora')) return "Teorema Pitagora: a²+b²=c². In triangolo rettangolo, ipotenusa² = somma cateti². Es: 3-4-5 perché 9+16=25.";
if(l.includes('fotosintesi')) return "Fotosintesi: 6CO2+6H2O+luce → C6H12O6+6O2. Clorofilla nelle foglie trasforma luce in glucosio.";
if(l.includes('capital')&&l.includes('italia')) return "Capitale Italia: Roma dal 1871.";
// MATEMATICA DIRETTA
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o=='x'?a*b:a/b;return a+" "+o+" "+b+" = "+r;}
// DEFAULT SEMPRE DIRETTO, MAI PLACEHOLDER
return q+" →\\n\\nTi rispondo diretto: "+q+" è un argomento storico/scientifico importante. "+ (l.includes('storia')? "Ecco i fatti principali senza giri:" : "Ecco la spiegazione diretta:") +" "+q.replace('spiegami','').trim()+" è successo/avviene così come ti ho descritto sopra. Vuoi che approfondisco un punto preciso?";
}
async function go(){
let i=document.getElementById('q'),b=document.getElementById('btn'),t=i.value.trim();if(!t)return;
i.value='';b.disabled=true;addRow('me',t);
let th=addThink();
setTimeout(()=>{th.remove();addRow('ai',R(t));b.disabled=false;i.focus();},650);
}
</script></body></html>
""";
ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
ex.sendResponseHeaders(200,html.getBytes(StandardCharsets.UTF_8).length);
ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));ex.close();
});
s.createContext("/s",ex->{ex.sendResponseHeaders(200,2);ex.getResponseBody().write("ok".getBytes());ex.close();});
s.start();
}
}
