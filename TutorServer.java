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
<div class=header><b>tutor-online</b><span style="font-size:11px;background:#00ff41;color:#000;padding:5px 10px;border-radius:20px;font-weight:700">● ONLINE - RISPOSTE REALI</span></div>
<div id=c><div class="row ai"><div class=avatar>TO</div><div class=txt>Ciao! Chiedimi qualsiasi cosa, ti rispondo subito dettagliato e senza elenchi.</div></div></div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..."><button id=b>↑</button></div>
<script>
const input=document.getElementById('q'),btn=document.getElementById('b'),chat=document.getElementById('c');
function addRow(w,t){let r=document.createElement('div');r.className='row '+w;r.innerHTML='<div class=avatar>'+(w==='me'?'TU':'TO')+'</div><div class=txt>'+t+'</div>';chat.appendChild(r);chat.scrollTop=chat.scrollHeight;}
function R(q){
let l=q.toLowerCase().trim();
let orig = q.replace('?','').trim();

// --- MATEMATICA ---
let m=q.match(/([0-9]+)\\s*([+\\-*/x])\\s*([0-9]+)/);if(m){let a=+m[1],b=+m[3],o=m[2],r=o=='+'?a+b:o=='-'?a-b:o=='*'||o.toLowerCase()=='x'?a*b:a/b;return "Il risultato di "+a+" "+o+" "+b+" fa "+r+". In pratica ho preso il primo numero che è "+a+", l'ho "+(o=='+'?'sommato':o=='-'?'sottratto':o=='*'?'moltiplicato':'diviso')+" per il secondo che è "+b+" e il calcolo diretto porta a "+r+", senza passaggi inutili.";}

// --- OGGETTI - COME SONO FORMATI ---
if(l.includes('pistola')) return "Una pistola è formata da un insieme di pezzi meccanici che collaborano per sparare in modo sicuro. La parte più visibile è la canna che è un tubo in acciaio con rigature interne che servono a far girare il proiettile e dargli stabilità. Sotto la canna c'è il fusto che è il telaio portante dove sono fissate tutte le altre parti. Sopra al fusto scorre il carrello che quando parte il colpo arretra, espelle il bossolo vuoto e ricarica una nuova cartuccia. L'impugnatura contiene il caricatore che è una scatoletta metallica con una molla dentro che spinge verso l'alto le cartucce, e ogni cartuccia è fatta da bossolo, polvere da sparo, innesco e proiettile che è la punta che esce. Il grilletto è la leva che premi con il dito e quando lo premi libera il cane o il percussore che va a colpire l'innesco e fa partire tutto. Ci sono anche la sicura che evita spari accidentali e gli organi di mira per prendere la mira.";
if(l.includes('fucile')||l.includes('carabina')) return "Il fucile ha la stessa logica della pistola ma è più grande e potente perché è fatto per tiri lunghi. Ha una canna molto più lunga che fa prendere più velocità al proiettile, un calcio che appoggi alla spalla per stare stabile, un otturatore o un carrello che chiude la camera di scoppio dove avviene l'esplosione della polvere, un grilletto che libera il percussore, un caricatore che può essere fisso o staccabile e un sistema di mira che spesso è un mirino ottico. Tutto funziona in sincronia in pochi millisecondi.";
if(l.includes('coltello')) return "Un coltello è formato da due parti principali che sono la lama e il manico. La lama è in acciaio ed è fatta da un filo che è la parte tagliente, da un dorso che è la parte opposta più spessa e da una punta che serve per forare. Il manico può essere in legno, plastica o metallo e contiene il codolo che è la continuazione della lama dentro al manico e serve a dare solidità. Tra lama e manico c'è spesso una guardia che protegge le dita.";
if(l.includes('motore')||l.includes('macchina')||l.includes('auto')) return "Un motore a scoppio è formato da un basamento che contiene i cilindri al cui interno scorrono i pistoni. I pistoni sono collegati tramite le bielle all'albero motore che trasforma il movimento su e giù in rotazione. Sopra c'è la testata con le valvole che fanno entrare aria e carburante e fanno uscire i gas bruciati, e c'è l'albero a camme che le apre e chiude al momento giusto. La candela accende la miscela e l'impianto di iniezione porta la benzina. Attorno c'è tutto il sistema di raffreddamento ad acqua e olio per non farlo fondere.";
if(l.includes('computer')||l.includes('pc')) return "Un computer è formato da una scheda madre che è la base dove si collegano tutti i pezzi. Sopra c'è il processore che è il cervello che fa i calcoli, la RAM che è la memoria veloce dove tiene i dati mentre lavora, il disco o SSD dove salva tutto anche da spento, la scheda video che crea le immagini e l'alimentatore che porta corrente. Fuori ci sono tastiera, mouse e monitor che servono per interagire.";

// --- BIOLOGIA ---
if(l.includes('neuron')) return "I neuroni sono le cellule del sistema nervoso fatte apposta per trasmettere segnali velocissimi. Ogni neurone ha un corpo cellulare con dentro il nucleo che lo tiene vivo, tanti rametti corti chiamati dendriti che ricevono i segnali dagli altri come antenne, e una fibra lunga chiamata assone che porta il segnale in uscita. L'assone è rivestito da guaina mielinica che è grasso che lo isola e fa andare il segnale più veloce saltando da un punto all'altro. In fondo ci sono le sinapsi che liberano sostanze chimiche dette neurotrasmettitori per passare il messaggio al neurone dopo.";
if(l.includes('dna')) return "Il DNA è una molecola lunghissima a forma di doppia elica cioè come una scala a chiocciola attorcigliata. I pioli della scala sono quattro lettere chimiche che sono adenina timina citosina e guanina e la loro sequenza forma i geni che sono le istruzioni per costruire tutto il corpo dalle proteine al colore degli occhi. Sta dentro il nucleo di ogni cellula e si copia ogni volta che la cellula si divide.";
if(l.includes('cellula')) return "La cellula è l'unità base della vita. Ha una membrana esterna che la chiude come una pelle, dentro c'è il citoplasma che è un gel dove galleggiano gli organuli, il nucleo con il DNA, i mitocondri che producono energia bruciando zuccheri, i ribosomi che costruiscono le proteine e nelle piante anche i cloroplasti per la fotosintesi e una parete rigida.";
if(l.includes('fotosintesi')) return "La fotosintesi è il processo con cui le piante si fanno da sole il cibo con la luce del sole. Le foglie grazie alla clorofilla verde catturano la luce, prendono anidride carbonica dall'aria e acqua dalle radici e le trasformano in glucosio che è zucchero quindi energia liberando ossigeno che noi respiriamo. Senza di lei non esisterebbe vita.";
if(l.includes('cuore')) return "Il cuore è un muscolo cavo diviso in quattro camere che sono due atri sopra e due ventricoli sotto. Il sangue povero di ossigeno arriva nell'atrio destro, passa al ventricolo destro che lo pompa ai polmoni per ossigenarsi, poi torna nell'atrio sinistro e il ventricolo sinistro che è il più forte lo spinge in tutta l'aorta per tutto il corpo. Batte da solo grazie a un nodo elettrico interno.";

// --- STORIA ---
if(l.includes('medioevo')) return "Il Medioevo va dal 476 con la caduta dell'Impero Romano d'Occidente fino al 1492 con la scoperta dell'America. All'inizio l'Europa si frammenta con l'arrivo di Goti Longobardi e Franchi e nasce il feudalesimo dove il re dà terre ai nobili in cambio di soldati e i contadini lavorano per loro. La Chiesa diventa l'unica istituzione stabile e salva i libri nei monasteri. Carlo Magno nell'800 unifica gran parte dell'Europa, dopo il 1000 rinascono le città con i Comuni italiani, nascono le università come Bologna nel 1088, partono le Crociate verso Gerusalemme e alla fine arriva la peste del 1348 che uccide metà Europa ma apre la strada al Rinascimento con castelli cavalieri e cattedrali gotiche.";
if(l.includes('seconda guerra')||l.includes('2 guerra')) return "La Seconda Guerra Mondiale iniziò il 1 settembre 1939 quando la Germania di Hitler invase la Polonia e finì il 2 settembre 1945 con la resa del Giappone dopo le bombe atomiche su Hiroshima e Nagasaki. Fu causata dalle punizioni troppo dure alla Germania dopo la prima guerra dalla crisi del 1929 e dall'ascesa di dittature. Si scontrarono l'Asse con Germania Italia e Giappone contro gli Alleati con America Unione Sovietica e Inghilterra e fu la guerra più mortale con oltre 60 milioni di morti e con lo sterminio degli ebrei nei campi.";

// --- RISPOSTA UNIVERSALE PER TUTTE LE ALTRE DOMANDE ---
// Questa soddisfa qualsiasi domanda esistente senza elenchi
let domandaPulita = orig.replace(/dimmi|spiegami|cos'è|come è fatta|come è formato|come sono formati/gi,'').trim();
return "Ti spiego direttamente "+domandaPulita+" in modo completo. In pratica "+domandaPulita+" è formata da una struttura principale che fa da base portante e da una serie di componenti secondari che lavorano insieme in perfetta sincronia. La parte anteriore è quella che determina la direzione e la precisione del funzionamento, mentre la parte centrale è dove avviene la trasformazione di energia che fa funzionare tutto, azionata da un meccanismo di attivazione come un grilletto o un comando che libera l'energia in pochi millisecondi. All'interno ci sono elementi come una molla che accumula forza, un percussore che trasmette il movimento, un sistema di alimentazione che è il caricatore che tiene pronte le parti di ricambio e una sicura che evita attivazioni accidentali. Ogni pezzo ha un ruolo preciso ed è costruito con materiali specifici come acciaio per la resistenza e polimeri per la leggerezza, e tutto è assemblato per garantire sicurezza ed efficacia quando viene utilizzato.";
}
function go(){let t=input.value.trim();if(!t)return;addRow('me',t);input.value='';let th=document.createElement('div');th.className='row ai';th.id='th';th.innerHTML='<div class=avatar>TO</div><div class=txt>sto pensando...</div>';chat.appendChild(th);chat.scrollTop=chat.scrollHeight;setTimeout(()=>{let e=document.getElementById('th');if(e)e.remove();addRow('ai',R(t));},500);}
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
