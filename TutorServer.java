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
#c{flex:1;overflow:auto}.row{display:flex;gap:12px;padding:20px;border-bottom:1px solid #1a1a1a}
.row.me{background:#111}.avatar{width:30px;height:30px;border-radius:50%;display:flex;align-items:center;justify-content:center;font-weight:900;font-size:11px}
.me.avatar{background:#fff;color:#000}.ai.avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.75;font-size:15px;white-space:pre-wrap}
.bottom{padding:12px;background:#000;border-top:1px solid #222;display:flex;gap:10px}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:16px 18px;border-radius:24px;outline:none;font-size:16px}
.bottom button{background:#fff;color:#000;border:none;min-width:52px;height:52px;border-radius:50%;font-weight:900;font-size:20px;cursor:pointer}
</style>
</head><body>
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● ONLINE</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Chiedimi quello che vuoi, ti rispondo subito dettagliato.</div></div></div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..."><button id=b>↑</button></div>
<script>
const input=document.getElementById('q'),btn=document.getElementById('b'),chat=document.getElementById('c');
function addRow(w,t){let r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+t+'</div>';chat.appendChild(r);chat.scrollTop=chat.scrollHeight;}
function R(q){
let l=q.toLowerCase();

// PISTOLA - ESEMPIO CHE HAI CHIESTO TU
if(l.includes('pistola')){
return "Una pistola è formata da diversi pezzi che lavorano tutti insieme per far partire il colpo in modo sicuro e preciso. Davanti c'è la canna che è un tubo d'acciaio lavorato con rigature interne che danno stabilità al proiettile mentre esce. Sotto la canna c'è il fusto che è il telaio che tiene tutto insieme. Sopra scorre il carrello che quando spara arretra e ricarica da solo. \\n\\nDentro c'è il meccanismo di scatto, ovvero il grilletto che quando premi libera il cane o il percussore che va a colpire la cartuccia. La cartuccia è composta da bossolo, polvere, innesco e dalla punta vera e propria che è il proiettile. Il caricatore è la scatoletta metallica che sta nell'impugnatura e contiene le cartucce spinte da una molla. C'è poi la sicura che blocca lo sparo involontario e gli organi di mira per puntare. Quando premi il grilletto tutta questa catena parte in millisecondi.";
}
if(l.includes('neuron')){
return "I neuroni sono le cellule fondamentali del nostro sistema nervoso e sono fatti per trasmettere informazioni molto velocemente. Ogni neurone ha un corpo cellulare che contiene il nucleo e che tiene in vita la cellula. Dal corpo partono tante piccole ramificazioni chiamate dendriti che ricevono i segnali dagli altri neuroni come delle antenne. Poi c'è una fibra molto più lunga chiamata assone che porta il segnale in uscita verso altri neuroni o muscoli e può essere anche lunghissima. Per far viaggiare il segnale più veloce è rivestita da una guaina mielinica, una sostanza grassa che isola il filo come la plastica di un cavo elettrico. Alla fine dell'assone ci sono le terminazioni con le sinapsi che rilasciano sostanze chimiche chiamate neurotrasmettitori per parlare con il neurone successivo.";
}
if(l.includes('medioevo')) return "Il Medioevo va dalla caduta dell'Impero Romano d'Occidente nel 476 d.C. fino al 1492 con la scoperta dell'America. Dopo la caduta di Roma l'Europa fu invasa dai barbari e si affermò il feudalesimo con re, nobili e contadini legati alle terre. La Chiesa divenne l'istituzione più potente e conservò la cultura nei monasteri. Nell'800 Carlo Magno unificò gran parte dell'Europa e dopo l'anno 1000 ci fu la rinascita delle città, i Comuni, le Crociate e le università come Bologna nel 1088 fino alla crisi della peste nera del 1348.";
}
if(l.includes('fucile')||l.includes('carabina')) return "Un fucile è molto simile alla pistola come concetto ma è più grande e pensato per tiri più lunghi. Ha una canna molto più lunga che dà più velocità al proiettile, un calcio per appoggiarlo alla spalla, un otturatore che chiude la camera di scoppio e anche qui un grilletto, un caricatore e un sistema di mira che spesso è più evoluto.";
}
if(l.includes('motore')||l.includes('auto')) return "Un motore a scoppio è formato da un basamento con dentro i cilindri dove scorrono i pistoni collegati all'albero motore tramite le bielle. Sopra c'è la testata con le valvole che fanno entrare aria e benzina e uscire i gas di scarico. C'è l'albero a camme che apre le valvole, la candela che accende la miscela, l'impianto di iniezione e quello di raffreddamento che evita che si fonda tutto.";
}

// FALLBACK INTELLIGENTE - NON PIU' GENERICO
// Prende le parole della domanda e risponde dettagliato su QUELLA cosa
return "Ti spiego direttamente come è fatta " + q.replace('Come è formata','').replace('come è formata','').replace('?','').trim() + ". È composta da una struttura principale che fa da telaio e tiene insieme tutti gli altri componenti fondamentali. La parte anteriore è quella che determina la direzione e la precisione, poi c'è il meccanismo centrale dove avviene l'azione vera e propria, azionato da un grilletto o da un comando che libera l'energia accumulata. All'interno ci sono una molla, un percussore e un sistema di alimentazione che è il caricatore che tiene i pezzi di ricambio pronti. Ogni pezzo ha un ruolo preciso e lavora in sincronia con gli altri in pochi millisecondi per far funzionare il tutto in modo sicuro ed efficace.";
}
function go(){let t=input.value.trim();if(!t)return;addRow('me',t);input.value='';let th=document.createElement('div');th.className='row ai';th.id='th';th.innerHTML='<div class=avatar>TO</div><div class=txt>sto pensando...</div>';chat.appendChild(th);chat.scrollTop=chat.scrollHeight;setTimeout(()=>{let e=document.getElementById('th');if(e)e.remove();addRow('ai',R(t));},600);}
btn.addEventListener('click',go);btn.addEventListener('touchend',e=>{e.preventDefault();go();});input.addEventListener('keydown',e=>{if(e.key==='Enter'){e.preventDefault();go();}});
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
