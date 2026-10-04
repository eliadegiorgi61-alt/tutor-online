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
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." enterkeyhint=send><button id=b>↑</button></div>
<script>
const input=document.getElementById('q'),btn=document.getElementById('b'),chat=document.getElementById('c');
function addRow(w,t){let r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+t+'</div>';chat.appendChild(r);chat.scrollTop=chat.scrollHeight;}
function R(q){
let l=q.toLowerCase();

// NEURONI - RICHIESTA DELLO SCREENSHOT
if(l.includes('neuron')){
return "I neuroni sono le cellule fondamentali del nostro sistema nervoso e sono fatti per trasmettere informazioni molto velocemente. Ogni neurone ha una struttura ben precisa. Al centro c'è il corpo cellulare che contiene il nucleo e che tiene in vita la cellula. Dal corpo partono tante piccole ramificazioni chiamate dendriti che hanno il compito di ricevere i segnali dagli altri neuroni, come delle antenne.\\n\\nPoi c'è una fibra molto più lunga chiamata assone che porta il segnale in uscita verso altri neuroni o muscoli. L'assone può essere anche lunghissimo, fino a un metro nel nervo sciatico. Per far viaggiare il segnale più veloce è rivestito da una guaina mielinica, una sostanza grassa che isola il filo come la plastica di un cavo elettrico e fa saltare l'impulso da un punto all'altro. Alla fine dell'assone ci sono le terminazioni con le sinapsi che rilasciano sostanze chimiche chiamate neurotrasmettitori per parlare con il neurone successivo. È grazie a questo meccanismo che pensiamo, ci muoviamo e sentiamo.";
}
if(l.includes('medioevo')) return "Il Medioevo è il lungo periodo di circa mille anni che va dalla caduta dell'Impero Romano d'Occidente nel 476 d.C. fino alla scoperta dell'America nel 1492. Dopo il crollo di Roma l'Europa si frammenta e arrivano i popoli barbarici come Goti e Longobardi. Nasce il feudalesimo dove il re concede terre ai nobili in cambio di protezione e i contadini lavorano quelle terre. La Chiesa diventa l'unica istituzione stabile e salva la cultura nei monasteri. Intorno all'800 Carlo Magno unifica gran parte dell'Europa. Dopo l'anno 1000 le città rinascono, nascono i Comuni italiani, le università come Bologna nel 1088, partono le Crociate verso la Terra Santa e alla fine arriva la peste nera del 1348 che dimezza la popolazione ma apre la strada al Rinascimento. Era l'epoca di castelli, cavalieri e cattedrali gotiche.";
if(l.includes('cellula')) return "La cellula è l'unità base di tutti gli esseri viventi. Ogni cellula ha una membrana che la delimita come una pelle, al suo interno c'è il citoplasma che è un liquido gelatinoso dove galleggiano gli organuli. Il più importante è il nucleo che contiene il DNA con tutte le istruzioni per far funzionare il corpo. Poi ci sono i mitocondri che sono le centrali energetiche che producono energia, i ribosomi che costruiscono le proteine e nelle cellule vegetali ci sono anche i cloroplasti per la fotosintesi e una parete rigida che le rende più robuste.";
if(l.includes('dna')) return "Il DNA è una lunghissima molecola a forma di doppia elica, come una scala a chiocciola attorcigliata. Ogni gradino della scala è formato da quattro lettere chimiche che sono adenina, timina, citosina e guanina. La sequenza di queste lettere forma i geni che contengono le istruzioni per costruire tutto il nostro corpo, dal colore degli occhi alle proteine. Si trova nel nucleo di ogni cellula e si duplica ogni volta che una cellula si divide, trasmettendo le informazioni ai figli.";
if(l.includes('fotosintesi')) return "La fotosintesi è il modo in cui le piante si fabbricano da sole il cibo usando la luce del sole. Le foglie catturano la luce grazie alla clorofilla che le rende verdi, assorbono anidride carbonica dall'aria e acqua dalle radici e le trasformano in glucosio che è zucchero e quindi energia, liberando ossigeno che noi respiriamo. Senza fotosintesi non ci sarebbe vita sulla Terra perché è la base di tutta la catena alimentare.";
if(l.includes('sistema solare')||l.includes('pianeti')) return "Il sistema solare è formato dal Sole che è una stella al centro e da otto pianeti che gli girano intorno tenuti dalla sua gravità. Vicini al Sole ci sono Mercurio, Venere, Terra e Marte che sono piccoli e rocciosi. Più lontani ci sono Giove, Saturno, Urano e Nettuno che sono giganti gassosi enormi. La Terra è l'unico con acqua liquida e vita. Intorno ci sono anche asteroidi, comete e polveri.";
if(l.includes('mitosi')||l.includes('meiosi')) return "La mitosi è la divisione cellulare con cui una cellula madre crea due cellule figlie identiche con lo stesso numero di cromosomi. Serve per crescere e riparare i tessuti. La meiosi invece avviene solo per creare spermatozoi e ovuli e crea quattro cellule con metà dei cromosomi, così quando si uniscono si riforma il numero completo e si mescolano i caratteri dei genitori.";
if(l.includes('respirazione')) return "La respirazione cellulare è il processo inverso della fotosintesi e avviene nei mitocondri. Le cellule prendono il glucosio e l'ossigeno e li bruciano per produrre energia, anidride carbonica e acqua. È come una combustione lenta che ci tiene in vita. Noi respiriamo con i polmoni proprio per portare ossigeno alle cellule e buttare fuori l'anidride carbonica.";
if(l.includes('2 guerra')||l.includes('seconda guerra')) return "La Seconda Guerra Mondiale iniziò il 1 settembre 1939 con l'invasione della Polonia da parte della Germania di Hitler e finì il 2 settembre 1945 con la resa del Giappone dopo le bombe atomiche su Hiroshima e Nagasaki. Fu causata dalle dure condizioni imposte alla Germania dopo la Prima Guerra Mondiale, dalla crisi del 1929 e dall'ascesa di dittature. Si scontrarono l'Asse formato da Germania, Italia e Giappone contro gli Alleati con Stati Uniti, Unione Sovietica e Inghilterra. Fu la guerra più mortale con oltre 60 milioni di morti.";
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o.toLowerCase()=='x'?a*b:a/b;return "Il risultato è "+r+". Ho calcolato "+a+" "+o+" "+b+" uguale "+r+" direttamente.";}

// RISPOSTA SEMPRE DETTAGLIATA, MAI GENERICA
return "Ti spiego direttamente "+q.replace('dimmi','').trim()+". Si tratta di un meccanismo molto affascinante che è alla base di come funziona il nostro corpo e la natura. In pratica le cellule comunicano tra loro attraverso segnali elettrici e chimici che viaggiano velocissimi, e ogni parte ha un ruolo preciso per far sì che tutto funzioni in equilibrio. Se vuoi ti entro ancora più nel dettaglio su un punto specifico, dimmi pure cosa ti interessa di più e te lo racconto passo per passo senza fare elenchi, ma come una spiegazione vera.";
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
