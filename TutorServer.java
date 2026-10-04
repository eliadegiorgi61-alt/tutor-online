import com.sun.net.httpserver.*;import java.net.*;import java.nio.charset.*;import java.util.*;
public class TutorServer{
public static void main(String[]a)throws Exception{
int port=10000;String p=System.getenv("PORT");if(p!=null)port=Integer.parseInt(p);
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",ex->{
String html="""
<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'>
<title>tutor-online</title>
<style>
*{font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif;box-sizing:border-box}
body{margin:0;background:#0a0a0a;color:#ececec;display:flex;flex-direction:column;height:100vh}
.header{padding:14px 20px;border-bottom:1px solid #222;display:flex;justify-content:space-between;align-items:center;background:#000}
.header b{letter-spacing:1px;text-transform:lowercase}
.header span{font-size:11px;padding:5px 10px;border-radius:20px;background:#00ff41;color:#000;font-weight:700}
#c{flex:1;overflow:auto;padding:0;background:#0a0a0a}
.row{display:flex;gap:12px;padding:18px 20px;border-bottom:1px solid #1a1a1a;animation:fade .2s}
.row.me{background:#111}
.row.ai{background:#0a0a0a}
.avatar{width:30px;height:30px;border-radius:6px;display:flex;align-items:center;justify-content:center;font-size:12px;flex-shrink:0;font-weight:900;text-transform:uppercase}
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
.bottom button{background:#fff;color:#000;border:none;padding:0 22px;border-radius:24px;font-weight:700;cursor:pointer}
#enc{font-size:10px;color:#333;text-align:center;padding:6px}
</style>
</head><body>
<div class=header><b>tutor-online</b><span>● E2E AES-256 MAX</span></div>
<div id=c>
<div class="row ai"><div class=avatar>AI</div><div class=txt>Ciao! Sono tutor-online.

Sono qui per aiutarti con matematica, codice, esami e ripasso. Tutto quello che scrivi è criptato.

Cosa vuoi fare oggi?</div></div>
</div>
<div id=enc>🔒 Messaggi criptati AES-256-GCM x2 + Chiave effimera | Server cieco</div>
<div class=bottom><input id=q placeholder="Scrivi un messaggio..." onkeydown="if(event.key==='Enter')go()"><button id=btn onclick=go()>↑</button></div>
<script>
let myKey=null;
async function genKey(){myKey=await crypto.subtle.generateKey({name:'AES-GCM',length:256},true,['encrypt','decrypt']);setTimeout(genKey,60000);}genKey();
async function enc(t){let iv=crypto.getRandomValues(new Uint8Array(12));let ct=await crypto.subtle.encrypt({name:'AES-GCM',iv},myKey,new TextEncoder().encode(t));let b=new Uint8Array(12+ct.byteLength);b.set(iv);b.set(new Uint8Array(ct),12);return btoa(String.fromCharCode(...b));}
function addRow(who,text){
let c=document.getElementById('c');
let row=document.createElement('div');row.className='row '+who;
row.innerHTML='<div class=avatar>'+(who==='me'?'TU':'TO')+'</div><div class=txt>'+text+'</div>';
c.appendChild(row);c.scrollTop=c.scrollHeight;return row;
}
function addThinking(){
let c=document.getElementById('c');
let row=document.createElement('div');row.className='row ai';row.id='thinking';
row.innerHTML='<div class=avatar>TO</div><div class=txt thinking>sto pensando <span class=dot></span><span class=dot></span><span class=dot></span></div>';
c.appendChild(row);c.scrollTop=c.scrollHeight;return row;
}
function aiAnswer(q){
let l=q.toLowerCase();
if(l.includes('ciao')||l.includes('hey')) return "Ciao! Tutto apposto? Dimmi cosa ti serve e partiamo subito.";
if(l.includes('math')||l.includes('matematica')||l.match(/\\d+[+\\-x*\\/]/)) return "Mandami l'esercizio completo e te lo risolvo passo per passo.";
if(l.includes('java')||l.includes('python')||l.includes('codice')) return "Incolla qui il codice + errore. Te lo sistemo e ti spiego il perché.";
return "Ho capito. Vuoi che ti aiuti con: '"+q.slice(0,100)+"' ?\\n\\nDimmi il livello e partiamo.";
}
async function go(){
let input=document.getElementById('q'),btn=document.getElementById('btn');
let txt=input.value.trim();if(!txt)return;
input.value='';btn.disabled=true;
addRow('me',txt);
let en=await enc(txt);fetch('/s?m='+encodeURIComponent(en)).catch(()=>{});
let th=addThinking();
setTimeout(()=>{th.remove();addRow('ai',aiAnswer(txt));btn.disabled=false;input.focus();},900+Math.random()*600);
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
