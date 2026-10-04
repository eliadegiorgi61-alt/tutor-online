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
*{font-family:system-ui,sans-serif;box-sizing:border-box;-webkit-tap-highlight-color:transparent}
body{margin:0;background:#0a0a0a;color:#ececec;display:flex;flex-direction:column;height:100vh;height:100dvh}
.header{padding:14px 20px;border-bottom:1px solid #222;display:flex;justify-content:space-between;background:#000}
#c{flex:1;overflow:auto;-webkit-overflow-scrolling:touch}.row{display:flex;gap:12px;padding:20px;border-bottom:1px solid #1a1a1a}
.row.me{background:#111}.avatar{width:30px;height:30px;border-radius:50%;display:flex;align-items:center;justify-content:center;font-weight:900;font-size:11px;flex-shrink:0}
.me.avatar{background:#fff;color:#000}.ai.avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.75;font-size:15px;white-space:pre-wrap;word-break:break-word}
.bottom{padding:12px 12px calc(12px + env(safe-area-inset-bottom));background:#000;border-top:1px solid #222;display:flex;gap:10px;align-items:center;position:sticky;bottom:0}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:18px 18px;border-radius:24px;outline:none;font-size:16px;min-width:0}
.bottom button{background:#fff;color:#000;border:none;min-width:56px;min-height:56px;width:56px;height:56px;border-radius:50%;font-weight:900;font-size:22px;cursor:pointer;display:flex;align-items:center;justify-content:center;touch-action:manipulation;flex-shrink:0}
.bottom button:active{transform:scale(0.95);background:#e0e0e0}
</style>
</head><body>
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● LIVE</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Chiedimi qualsiasi cosa.</div></div></div>
<div class=bottom>
<input id=q placeholder="Scrivi un messaggio..." autocomplete=off enterkeyhint=send inputmode=text>
<button id=b type=button>↑</button>
</div>
<script>
const input=document.getElementById('q'),btn=document.getElementById('b'),chat=document.getElementById('c');
function addRow(w,t){let r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+t+'</div>';chat.appendChild(r);chat.scrollTop=chat.scrollHeight;}
function R(q){
let l=q.toLowerCase();
if(l.includes('pistola')) return "Una pistola è formata da un insieme di pezzi che collaborano per sparare in modo sicuro. Davanti c'è la canna che è un tubo d'acciaio con rigature interne che stabilizzano il proiettile. Sotto c'è il fusto che è il telaio che tiene tutto. Sopra scorre il carrello che arretra espelle il bossolo e ricarica una nuova cartuccia. Nell'impugnatura c'è il caricatore che è una scatoletta con molla che spinge le cartucce verso l'alto e ogni cartuccia è fatta da bossolo polvere innesco e proiettile. Il grilletto libera il cane o percussore che colpisce l'innesco e fa partire tutto. Ci sono anche sicura e organi di mira.";
if(l.includes('neuron')) return "I neuroni hanno un corpo cellulare con nucleo, tanti dendriti corti che ricevono segnali come antenne, e un assone lungo rivestito da guaina mielinica che porta il segnale in uscita fino alle sinapsi dove libera neurotrasmettitori per parlare col neurone dopo.";
if(l.includes('medioevo')) return "Il Medioevo va dal 476 caduta di Roma al 1492 scoperta America. Nasce il feudalesimo con re nobili e contadini, la Chiesa salva la cultura nei monasteri, Carlo Magno unifica l'Europa nell'800, dopo il 1000 rinascono città Comuni università come Bologna 1088 Crociate e poi peste del 1348.";
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o.toLowerCase()=='x'?a*b:a/b;return "Risultato "+a+" "+o+" "+b+" = "+r;}
return "Ti spiego direttamente "+q.replace('?','').trim()+" in modo completo e discorsivo. È composta da una struttura principale portante che tiene insieme tutti i componenti. La parte anteriore determina direzione e precisione, la parte centrale è dove avviene la trasformazione di energia azionata da grilletto o comando che libera forza in millisecondi. Dentro ci sono molla percussore caricatore che alimenta e sicura per evitare attivazioni accidentali. Ogni pezzo è in acciaio o polimero e lavora in sincronia.";
}
function go(){let t=input.value.trim();if(!t)return;addRow('me',t);input.value='';input.focus();let th=document.createElement('div');th.className='row ai';th.id='th';th.innerHTML='<div class=avatar>TO</div><div class=txt>sto pensando...</div>';chat.appendChild(th);chat.scrollTop=chat.scrollHeight;setTimeout(()=>{let e=document.getElementById('th');if(e)e.remove();addRow('ai',R(t));},400);}
btn.addEventListener('click',e=>{e.preventDefault();go();});
btn.addEventListener('touchstart',e=>{e.preventDefault();go();},{passive:false});
input.addEventListener('keydown',e=>{if(e.key==='Enter'){e.preventDefault();go();}});
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
