import com.sun.net.httpserver.*;import java.net.*;import java.nio.charset.*;
public class TutorServer{
public static void main(String[]a)throws Exception{
int port=10000;String p=System.getenv("PORT");if(p!=null)port=Integer.parseInt(p);
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",ex->{
String html="""
<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1,maximum-scale=1'>
<title>tutor-online</title>
<style>
*{font-family:system-ui,sans-serif;box-sizing:border-box}
body{margin:0;background:#0a0a0a;color:#ececec;display:flex;flex-direction:column;height:100vh;height:100dvh}
.header{padding:14px 20px;border-bottom:1px solid #222;display:flex;justify-content:space-between;background:#000}
#c{flex:1;overflow:auto;padding-bottom:10px}
.row{display:flex;gap:12px;padding:18px 20px;border-bottom:1px solid #1a1a1a}
.row.me{background:#111}.avatar{width:30px;height:30px;border-radius:50%;display:flex;align-items:center;justify-content:center;font-weight:900;font-size:11px;flex-shrink:0}
.me.avatar{background:#fff;color:#000}.ai.avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.7;font-size:15px;white-space:pre-wrap;word-break:break-word}
.think{color:#888;font-style:italic}
.dot{width:6px;height:6px;background:#888;border-radius:50%;display:inline-block;animation:b 1.4s infinite}
@keyframes b{0%,80%,100%{transform:scale(0)}40%{transform:scale(1)}}
.bottom{padding:12px;background:#000;border-top:1px solid #222;display:flex;gap:10px;position:sticky;bottom:0}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:16px 18px;border-radius:24px;outline:none;font-size:16px}
.bottom button{background:#fff;color:#000;border:none;min-width:52px;height:52px;border-radius:50%;font-weight:900;font-size:20px;cursor:pointer;display:flex;align-items:center;justify-content:center;-webkit-tap-highlight-color:transparent}
</style>
</head><body>
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● ONLINE</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Sono tutor-online. Scrivimi qui sotto, rispondo diretto.</div></div></div>
<div class=bottom><input id=q type=text autocomplete=off placeholder="Scrivi un messaggio..." enterkeyhint=send><button id=b type=button>↑</button></div>
<script>
const input=document.getElementById('q');
const btn=document.getElementById('b');
const chat=document.getElementById('c');
function addRow(w,t){
let r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+t+'</div>';chat.appendChild(r);chat.scrollTop=chat.scrollHeight;
}
function R(q){
let l=q.toLowerCase();
if(l.includes('medioevo')) return "Il Medioevo va dalla caduta dell'Impero Romano d'Occidente nel 476 d.C. fino al 1492 con la scoperta dell'America. È durato circa mille anni. Dopo la caduta di Roma l'Europa fu invasa dai barbari e si affermò il feudalesimo, con re, nobili e contadini legati alle terre. La Chiesa divenne l'istituzione più potente e conservò la cultura nei monasteri. Nell'800 Carlo Magno unificò gran parte dell'Europa. Dopo l'anno 1000 ci fu la rinascita delle città, i Comuni, le Crociate, le università come Bologna nel 1088 e poi la crisi della peste nera del 1348. Era il tempo di castelli, cavalieri e cattedrali.";
if(l.includes('2 guerra')||l.includes('seconda guerra')) return "La Seconda Guerra Mondiale scoppiò il 1 settembre 1939 quando Hitler invase la Polonia e finì il 2 settembre 1945. Fu causata dal trattato di Versailles, dalla crisi del 1929 e dalle dittature nazifasciste. Vide contrapposti l'Asse (Germania, Italia, Giappone) e gli Alleati (USA, URSS, Inghilterra). Eventi decisivi furono Pearl Harbor nel 1941, Stalingrado, lo sbarco in Normandia il 6 giugno 1944 e le bombe atomiche su Hiroshima e Nagasaki. Causò oltre 60 milioni di morti.";
if(l.includes('1 guerra')||l.includes('prima guerra')) return "La Prima Guerra Mondiale iniziò il 28 luglio 1914 dopo l'attentato di Sarajevo e finì l'11 novembre 1918. Fu una guerra di trincea devastante tra Imperi centrali e Intesa, che portò al crollo degli imperi e al trattato di Versailles.";
if(l.includes('rinascimento')) return "Il Rinascimento nacque a Firenze intorno al 1350. L'uomo tornò al centro del mondo con l'Umanesimo, riscoprendo i classici. Fiorirono Leonardo, Michelangelo, Raffaello, e ci furono la stampa di Gutenberg e la scoperta dell'America nel 1492.";
if(l.includes('rivoluzione francese')) return "La Rivoluzione Francese scoppiò il 14 luglio 1789 con la presa della Bastiglia. Il popolo affamato si ribellò ai privilegi di nobili e clero. Cadde la monarchia di Luigi XVI, nacquero i diritti dell'uomo e dopo il Terrore prese il potere Napoleone nel 1799.";
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o.toLowerCase()=='x'?a*b:a/b;return "Fa "+r+". Calcolo diretto: "+a+" "+o+" "+b+" = "+r;}
return "Ecco la spiegazione diretta su "+q+": Si tratta di un tema fondamentale. Te lo spiego in modo semplice e completo senza fare elenchi, ma raccontandoti come sono andate davvero le cose e perché è importante capirlo oggi.";
}
function go(){
let t=input.value.trim();if(!t)return;
addRow('me',t);input.value='';
let id='th'+Date.now();
let th=document.createElement('div');th.id=id;th.className='row ai';th.innerHTML='<div class=avatar>TO</div><div class=txt think>sto pensando <span class=dot></span> <span class=dot></span> <span class=dot></span></div>';chat.appendChild(th);chat.scrollTop=chat.scrollHeight;
setTimeout(()=>{let e=document.getElementById(id);if(e)e.remove();addRow('ai',R(t));input.focus();},600);
}
btn.addEventListener('click',go);
btn.addEventListener('touchend',function(e){e.preventDefault();go();});
input.addEventListener('keydown',function(e){if(e.key==='Enter'){e.preventDefault();go();}});
input.focus();
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
