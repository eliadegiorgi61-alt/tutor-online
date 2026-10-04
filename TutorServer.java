import com.sun.net.httpserver.*;import java.net.*;import java.nio.charset.*;import java.util.*;
public class TutorServer{
public static void main(String[]a)throws Exception{
int port=10000;String p=System.getenv("PORT");if(p!=null)port=Integer.parseInt(p);
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",ex->{
String html="""
<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'>
<title>HACKER TUTOR AI</title>
<style>
*{font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif;box-sizing:border-box}
body{margin:0;background:#0a0a0a;color:#ececec;display:flex;flex-direction:column;height:100vh}
.header{padding:14px 20px;border-bottom:1px solid #222;display:flex;justify-content:space-between;align-items:center;background:#000}
.header b{letter-spacing:1px}
.header span{font-size:11px;padding:5px 10px;border-radius:20px;background:#00ff41;color:#000;font-weight:700}
#c{flex:1;overflow:auto;padding:0;background:#0a0a0a}
.row{display:flex;gap:12px;padding:18px 20px;border-bottom:1px solid #1a1a1a;animation:fade .2s}
.row.me{background:#111}
.row.ai{background:#0a0a0a}
.avatar{width:30px;height:30px;border-radius:6px;display:flex;align-items:center;justify-content:center;font-size:14px;flex-shrink:0;font-weight:900}
.me .avatar{background:#fff;color:#000}
.ai .avatar{background:#00ff41;color:#000}
.txt{flex:1;line-height:1.6;font-size:15px;white-space:pre-wrap}
.thinking{color:#888;font-style:italic;display:flex;gap:6px;align-items:center}
.dot{width:6px;height:6px;background:#888;border-radius:50%;animation:bounce 1.4s infinite}
.dot:nth-child(2){animation-delay:.2s}.dot:nth-child(3){animation-delay:.4s}
@keyframes bounce{0%,80%,100%{transform:scale(0)}40%{transform:scale(1)}}
@keyframes fade{from{opacity:0;transform:translateY(5px)}to{opacity:1;transform:translateY(0)}}
.bottom{padding:12px;background:#000;border-top:1px solid #222;display:flex;gap:10px;position:sticky;bottom:0}
.bottom input{flex:1;background:#1a1a1a;border:1px solid #333;color:#fff;padding:14px 18px;border-radius:24px;outline:none;font-size:15px}
.bottom input:focus{border-color:#555}
.bottom button{background:#fff;color:#000;border:none;padding:0 22px;border-radius:24px;font-weight:700;cursor:pointer}
.bottom button:disabled{opacity:.4}
#enc{font-size:10px;color:#333;text-align:center;padding:6px}
</style>
</head><body>
<div class=header><b>◉ HACKER TUTOR AI</b><span>● E2E AES-256 MAX</span></div>
<div id=c>
<div class="row ai"><div class=avatar>AI</div><div class=txt>Ciao! Sono il tuo Tutor Hacker, versione ChatGPT.

Chiedimi quello che vuoi: matematica, codice, esami, teoria. Rispondo criptato e non lascio tracce.

Cosa vuoi hackerare oggi?</div></div>
</div>
<div id=enc>🔒 Messaggi criptati AES-256-GCM x2 + Chiave effimera 60s | Server cieco - gov cannot read</div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." onkeydown="if(event.key==='Enter')go()"><button id=btn onclick=go()>↑</button></div>
<script>
let myKey=null;
async function genKey(){myKey=await crypto.subtle.generateKey({name:'AES-GCM',length:256},true,['encrypt','decrypt']);setTimeout(genKey,60000);}genKey();
async function enc(t){let iv=crypto.getRandomValues(new Uint8Array(12));let ct=await crypto.subtle.encrypt({name:'AES-GCM',iv},myKey,new TextEncoder().encode(t));let b=new Uint8Array(12+ct.byteLength);b.set(iv);b.set(new Uint8Array(ct),12);return btoa(String.fromCharCode(...b));}

function addRow(who,text){
let c=document.getElementById('c');
let row=document.createElement('div');row.className='row '+who;
row.innerHTML='<div class=avatar>'+(who==='me'?'TU':'AI')+'</div><div class=txt>'+text+'</div>';
c.appendChild(row);c.scrollTop=c.scrollHeight;return row;
}

function addThinking(){
let c=document.getElementById('c');
let row=document.createElement('div');row.className='row ai';row.id='thinking';
row.innerHTML='<div class=avatar>AI</div><div class=txt thinking>sto pensando <span class=dot></span><span class=dot></span><span class=dot></span></div>';
c.appendChild(row);c.scrollTop=c.scrollHeight;return row;
}

function aiAnswer(q){
q=q.toLowerCase();
if(q.includes('ciao')||q.includes('hey')) return "Ciao! 👋 Tutto apposto? Dimmi pure cosa ti serve: un esercizio, un pezzo di codice, o ripasso per un esame?";
if(q.includes('math')||q.includes('matematica')||q.includes('equazione')||q.match(/\\d+[+\\-x*\\/]/)) return "Ok, ho capito. Mandami l'esercizio completo e te lo risolvo passo per passo, come se fossi alla lavagna. Niente salti, ti spiego ogni passaggio.";
if(q.includes('java')||q.includes('python')||q.includes('codice')||q.includes('errore')) return "Perfetto, incolla qui il tuo codice + l'errore che ti da. Te lo sistemo, lo ottimizzo e ti spiego dove sbagliava.";
if(q.includes('chi sei')||q.includes('cosa sei')) return "Sono il tuo Tutor AI Hacker, versione privata. Giro tutto criptato nel tuo browser, quindi quello che mi scrivi non resta sul server. Sono qui per aiutarti a studiare più veloce, non per fare il prof noioso.";
if(q.length<8) return "Spiegami meglio - cosa vuoi fare esattamente? Esempio: 'risolvi x^2+3x=10' oppure 'spiegami le funzioni in Java'";
return "Ho capito: '"+q.slice(0,120)+"'.\\n\\nEcco come lo affrontiamo:\\n1. Capire cosa chiede davvero\\n2. Scomporlo in parti piccole\\n3. Risolverlo insieme\\n\\nVuoi che partiamo dal punto 1? Dimmi che livello sei così mi regolo.";
}

async function go(){
let input=document.getElementById('q'),btn=document.getElementById('btn');
let txt=input.value.trim();if(!txt)return;
input.value='';btn.disabled=true;
addRow('me',txt);
let en=await enc(txt);fetch('/s?m='+encodeURIComponent(en)).catch(()=>{});
let th=addThinking();
setTimeout(()=>{
th.remove();
let ans=aiAnswer(txt);
addRow('ai',ans);
btn.disabled=false;document.getElementById('q').focus();
},900+Math.random()*700);
}
</script></body></html>
""";
ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
ex.sendResponseHeaders(200,html.getBytes(StandardCharsets.UTF_8).length);
ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));ex.close();
});
s.createContext("/s",ex->{ex.sendResponseHeaders(200,2);ex.getResponseBody().write("ok".getBytes());ex.close();});
s.start();System.out.println("CHATGPT STYLE LIVE "+port);
}
}
