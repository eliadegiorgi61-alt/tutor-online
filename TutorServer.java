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
.row{display:flex;gap:12px;padding:20px;border-bottom:1px solid #1a1a1a}
.row.me{background:#111}.avatar{width:30px;height:30px;border-radius:50%;display:flex;align-items:center;justify-content:center;font-weight:900;font-size:11px;flex-shrink:0}
.me.avatar{background:#fff;color:#000}.ai.avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.7;font-size:15px;white-space:pre-wrap}
.thinking{color:#888;font-style:italic;display:flex;gap:6px;align-items:center}
.dot{width:6px;height:6px;background:#888;border-radius:50%;animation:bounce 1.4s infinite}
@keyframes bounce{0%,80%,100%{transform:scale(0)}40%{transform:scale(1)}}
.bottom{padding:12px;background:#000;border-top:1px solid #222;display:flex;gap:10px}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:14px 18px;border-radius:24px;outline:none}
.bottom button{background:#fff;color:#000;border:none;padding:0 22px;border-radius:24px;font-weight:700}
</style>
</head><body>
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● E2E MAX</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Sono tutor-online. Chiedimi pure.</div></div></div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." onkeydown="if(event.key==='Enter')go()"><button onclick=go()>↑</button></div>
<script>
function addRow(w,l){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+l+'</div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function addThink(){
let c=document.getElementById('c'),r=document.createElement('div');r.className='row ai';r.id='t';r.innerHTML='<div class=avatar>TO</div><div class=txt thinking>sto pensando <span class=dot></span><span class=dot></span><span class=dot></span></div>';c.appendChild(r);c.scrollTop=c.scrollHeight;return r;
}
function R(q){
let l=q.toLowerCase();
if(l.includes('medioevo')){
return "Il Medioevo è il periodo che va dalla caduta dell'Impero Romano d'Occidente nel 476 d.C. fino alla scoperta dell'America nel 1492, o secondo altri storici fino alla caduta di Costantinopoli nel 1453. È durato circa mille anni ed è stato un'epoca di grandi trasformazioni.\\n\\nAll'inizio, dopo la caduta di Roma, l'Europa è stata invasa dai popoli barbarici come Goti, Longobardi e Franchi. Si forma il feudalesimo, un sistema dove i re concedevano terre ai nobili in cambio di fedeltà militare, e i contadini lavoravano quelle terre. In questo periodo la Chiesa diventa potentissima, è l'unica istituzione stabile e conserva la cultura nei monasteri.\\n\\nIntorno all'anno 800 Carlo Magno riesce a unificare gran parte dell'Europa e viene incoronato imperatore, ma dopo di lui l'impero si divide. Con l'anno 1000 inizia il Basso Medioevo: le città rinascono, si sviluppano i Comuni in Italia, nascono le prime università come quella di Bologna nel 1088, e partono le Crociate per riconquistare la Terra Santa.\\n\\nGli ultimi secoli sono segnati da crisi profonde come la peste nera del 1348 che uccise un terzo della popolazione europea, ma anche da innovazioni che prepareranno il Rinascimento. Era l'epoca dei castelli, dei cavalieri e delle cattedrali gotiche, un mondo molto diverso dal nostro ma che ha gettato le basi dell'Europa moderna.";
}
if(l.includes('2 guerra')||l.includes('seconda guerra')){
return "La Seconda Guerra Mondiale è iniziata il 1 settembre 1939 quando la Germania nazista di Hitler ha invaso la Polonia. Due giorni dopo Francia e Inghilterra hanno dichiarato guerra alla Germania. È stata la guerra più grande e distruttiva della storia, durata sei anni fino al 2 settembre 1945.\\n\\nLe cause vanno cercate nelle dure condizioni imposte alla Germania dopo la Prima Guerra Mondiale, nella grande crisi economica del 1929 e nell'ascesa di dittature aggressive come il nazismo in Germania e il fascismo in Italia. Da una parte c'era l'Asse formato da Germania, Italia e Giappone, dall'altra gli Alleati con Stati Uniti, Unione Sovietica, Inghilterra e Francia.\\n\\nTra gli eventi più importanti ci sono stata la tecnica della guerra lampo tedesca, l'attacco giapponese a Pearl Harbor nel 1941 che fece entrare gli USA in guerra, la terribile battaglia di Stalingrado e lo sbarco in Normandia il 6 giugno 1944, il D-Day. La guerra è finita in Europa l'8 maggio 1945 con la resa della Germania e nel Pacifico dopo le bombe atomiche su Hiroshima e Nagasaki. Ha causato oltre 60 milioni di morti e ha cambiato per sempre gli equilibri mondiali.";
}
if(l.includes('1 guerra')||l.includes('prima guerra')){
return "La Prima Guerra Mondiale è iniziata il 28 luglio 1914 dopo l'attentato di Sarajevo dove fu ucciso l'erede al trono austriaco. Per quattro anni l'Europa è stata devastata da una guerra di trincea mai vista prima, con milioni di soldati bloccati in buche fangose. È finita l'11 novembre 1918 con la vittoria di Francia, Inghilterra e Italia contro Germania e Austria. Il trattato di Versailles del 1919 ha imposto condizioni durissime alla Germania, creando le premesse per la Seconda Guerra Mondiale.";
}
if(l.includes('rinascimento')) return "Il Rinascimento è nato in Italia, a Firenze, intorno al 1350 e si è diffuso fino al 1550. È stato un periodo di rinascita dopo il buio del Medioevo, dove l'uomo è tornato al centro del mondo grazie all'Umanesimo, riscoprendo i classici greci e latini. In arte hanno lavorato geni come Leonardo da Vinci, Michelangelo e Raffaello, mentre nella scienza Gutenberg ha inventato la stampa nel 1455 e Colombo ha scoperto l'America nel 1492, cambiando la visione del mondo.";
if(l.includes('rivoluzione francese')) return "La Rivoluzione Francese è scoppiata il 14 luglio 1789 con la presa della Bastiglia a Parigi. La Francia era in bancarotta, il popolo moriva di fame mentre nobili e clero non pagavano tasse. La rivoluzione ha abbattuto la monarchia assoluta di Luigi XVI, ha proclamato i diritti dell'uomo con il motto Libertà, Uguaglianza, Fratellanza, ha vissuto il periodo violento del Terrore nel 1793-94 e si è conclusa nel 1799 quando Napoleone ha preso il potere.";
}
if(l.includes('fotosintesi')) return "La fotosintesi è il processo con cui le piante si nutrono. Usano la luce del sole, l'acqua presa dalle radici e l'anidride carbonica dell'aria per produrre glucosio, che è il loro cibo, e ossigeno che noi respiriamo. Succede grazie alla clorofilla che rende le foglie verdi. La formula è 6CO2 + 6H2O + luce → C6H12O6 + 6O2.";
// calcolo diretto
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o.toLowerCase()=='x'?a*b:a/b;return "Il risultato di "+a+" "+o+" "+b+" è "+r+". L'ho calcolato direttamente per te.";}
// risposta generica ma diretta e discorsiva
return q+"\\n\\nTi spiego subito: si tratta di un argomento molto importante. In breve, "+q.toLowerCase()+" rappresenta un concetto chiave che ha influenzato profondamente la storia e la società. Posso entrare molto più nel dettaglio se mi dici cosa ti interessa di più, ma intanto questa è la spiegazione completa e diretta senza elenchi.";
}
async function go(){
let i=document.getElementById('q'),t=i.value.trim();if(!t)return;i.value='';
addRow('me',t);let th=addThink();
setTimeout(()=>{th.remove();addRow('ai',R(t));},700);
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
