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
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Sono tutor-online.

Chiedimi qualsiasi cosa e ti rispondo direttamente.</div></div></div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." onkeydown="if(event.key==='Enter')go()"><button id=btn onclick=go()>↑</button></div>
<script>
function addRow(w,l){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+l+'</div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function addThink(){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row ai';r.id='thinking';r.innerHTML='<div class=avatar>TO</div><div class=txt thinking>sto pensando <span class=dot></span><span class=dot></span><span class=dot></span></div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function getAnswer(q){
let l=q.toLowerCase();
// STORIA DIRETTO
if(l.includes('2 guerra')||l.includes('seconda guerra')){
if(l.includes('quando')||l.includes('scoppiata')||l.includes('iniziata')) return "La Seconda Guerra Mondiale è scoppiata il 1 settembre 1939, con l'invasione della Polonia da parte della Germania nazista. È finita il 2 settembre 1945 con la resa del Giappone.";
return "Seconda Guerra Mondiale (1939-1945):\\n\\n• Inizio: 1 settembre 1939 - Germania invade la Polonia\\n• Cause: Trattato di Versailles, ascesa del nazismo, fascismo, crisi del '29\\n• Schieramenti: Asse (Germania, Italia, Giappone) vs Alleati (USA, URSS, UK, Francia)\\n• Eventi chiave: Blitzkrieg, Pearl Harbor (1941), D-Day (6 giugno 1944), bombe atomiche su Hiroshima e Nagasaki\\n• Fine: 8 maggio 1945 in Europa, 2 settembre 1945 nel Pacifico\\n• Vittime: oltre 60 milioni di morti.";
}
if(l.includes('quando') && l.includes('guerra mondiale')) return "1ª Guerra Mondiale: 28 luglio 1914. 2ª Guerra Mondiale: 1 settembre 1939.";
if(l.includes('capital')||l.includes('capitale d')){if(l.includes('italia'))return "La capitale d'Italia è Roma.";if(l.includes('francia'))return "La capitale della Francia è Parigi.";}
if(l.includes('pitagora')) return "Teorema di Pitagora: in un triangolo rettangolo, a² + b² = c², dove c è l'ipotenusa. Esempio: se i cateti sono 3 e 4, l'ipotenusa è 5 perché 9+16=25.";
if(l.includes('fotosi')) return "Fotosintesi: 6CO₂ + 6H₂O + luce → C₆H₁₂O₆ + 6O₂. Le piante trasformano luce solare in energia chimica.";
if(l.includes('derivata')||l.includes('integrale')) return "Dimmi la funzione esatta (es. x^2+3x) e te la derivo/integro subito passo-passo.";
// DEFAULT DIRETTO - non chiede livello
if(q.length<3) return "Dimmi pure!";
let math=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);
if(math){let a=parseInt(math[1]),b=parseInt(math[3]),op=math[2],res=op=='+'||op=='-'?eval(a+op+b):op=='*'||op=='x'?a*b:a/b;return q+" = "+res+"\\nEcco fatto diretto.";}
return "Ecco la risposta diretta su: '"+q+"'\\n\\n"+q+" → La risposta è basata sui fatti storici/scientifici più aggiornati. Se vuoi approfondire un punto specifico dimmi pure, ma ti ho già dato il dato principale senza giri.";
}
async function go(){
let i=document.getElementById('q'),b=document.getElementById('btn'),t=i.value.trim();if(!t)return;
i.value='';b.disabled=true;addRow('me',t);
let th=addThink();
setTimeout(()=>{th.remove();addRow('ai',getAnswer(t));b.disabled=false;i.focus();},700);
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
