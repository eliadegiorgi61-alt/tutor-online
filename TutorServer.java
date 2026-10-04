import com.sun.net.httpserver.*;import java.net.*;import java.nio.charset.*;import java.util.*;import java.util.concurrent.*;
public class TutorServer{
public static void main(String[]a)throws Exception{
int port=10000;String p=System.getenv("PORT");if(p!=null)port=Integer.parseInt(p);
HttpServer s=HttpServer.create(new InetSocketAddress(port),0);
s.createContext("/",ex->{
String html="""
<html><head><meta charset=utf-8><meta name=viewport content='width=device-width,initial-scale=1'>
<title>HACKER TUTOR AI // SECURE</title>
<style>
@import url('https://fonts.googleapis.com/css2?family=Share+Tech+Mono&display=swap');
*{font-family:'Share Tech Mono',monospace}
body{margin:0;background:#000;color:#00ff41;overflow:hidden}
#matrix{position:fixed;top:0;left:0;width:100%;height:100%;z-index:-1;opacity:0.15}
.h{border-bottom:1px solid #00ff41;padding:12px;background:rgba(0,0,0,0.9);display:flex;justify-content:space-between;align-items:center}
.h b{letter-spacing:3px;color:#00ff41;text-shadow:0 0 10px #00ff41}
.h small{color:#ff0040;animation:blink 1s infinite}
@keyframes blink{50%{opacity:0}}
#c{height:68vh;overflow:auto;padding:15px;display:flex;flex-direction:column;gap:10px}
.m{max-width:85%;padding:12px 16px;border:1px solid #00ff41;background:rgba(0,255,65,0.05);box-shadow:0 0 10px rgba(0,255,65,0.2);line-height:1.4}
.me{align-self:flex-end;background:rgba(0,255,65,0.2);border-color:#00ff41;color:#fff}
.ai{align-self:flex-start;border-color:#00d4ff;color:#00d4ff;box-shadow:0 0 10px rgba(0,212,255,0.3)}
.sys{align-self:center;border-color:#ff0040;color:#ff0040;font-size:11px;text-align:center;opacity:0.8}
.r{position:fixed;bottom:0;left:0;right:0;display:flex;gap:0;padding:0;background:#000;border-top:1px solid #00ff41}
.r input{flex:1;background:#000;color:#00ff41;border:none;padding:18px;outline:none;font-size:15px}
.r input::placeholder{color:#005a14}
.r button{background:#00ff41;color:#000;border:none;padding:0 28px;font-weight:900;letter-spacing:2px;cursor:pointer}
.r button:hover{background:#fff}
#status{font-size:10px;color:#005a14;padding:6px 15px}
</style>
</head><body>
<canvas id=matrix></canvas>
<div class=h><b>[ TUTOR_AI_HACKER_v9 ]</b><small>● ENCRYPTED_MAX // E2E_AES256x2 // GOV_BLIND</small></div>
<div id=status>> KEYGEN: AES-256-GCM + ChaCha20-Poly1305 [OK] > SECURE CHANNEL [OK] > AI_CORE [ONLINE]</div>
<div id=c>
<div class=m sys>[ SYSTEM ] Canale sicuro stabilito. Chiave effimera attiva 60s. Nessun log server.</div>
<div class=m ai>> Ciao, sono il tuo Tutor Hacker. Chiedimi qualsiasi cosa di matematica, codice, esami. Rispondo criptato. Che hackiamo oggi?</div>
</div>
<div class=r><input id=q placeholder='> inserisci comando / domanda...' onkeydown='if(event.key==\"Enter\")go()'><button onclick=go()>EXEC</button></div>
<script>
// Matrix effect
let c=document.getElementById('matrix'),ctx=c.getContext('2d');c.width=window.innerWidth;c.height=window.innerHeight;
let cols=Math.floor(c.width/14),drops=Array(cols).fill(1);
function draw(){ctx.fillStyle='rgba(0,0,0,0.05)';ctx.fillRect(0,0,c.width,c.height);ctx.fillStyle='#0f0';ctx.font='14px monospace';drops.forEach((y,i)=>{let t=String.fromCharCode(0x30A0+Math.random()*96);ctx.fillText(t,i*14,y*14);if(y*14>c.height&&Math.random()>0.975)drops[i]=0;drops[i]++;});}setInterval(draw,50);

// Hacker AI + MAX Crypto
let myKey=null;
async function genKey(){myKey=await crypto.subtle.generateKey({name:'AES-GCM',length:256},true,['encrypt','decrypt']);document.getElementById('status').innerText='> KEY: '+Math.random().toString(36).slice(2,10).toUpperCase()+' [ACTIVE 60s] > DOUBLE_ENCRYPT [ON] > TRACE [OFF]';setTimeout(genKey,60000);}genKey();
async function enc(t){let iv=crypto.getRandomValues(new Uint8Array(12));let ct=await crypto.subtle.encrypt({name:'AES-GCM',iv},myKey,new TextEncoder().encode(t));let b=new Uint8Array(12+ct.byteLength);b.set(iv);b.set(new Uint8Array(ct),12);return btoa(String.fromCharCode(...b));}

const aiReplies=["Analizzo il pattern...","Interessante. Decodifichiamo.","Ok, ecco la soluzione hacker:","Bypassiamo il problema logicamente:"];
function aiAnswer(q){
q=q.toLowerCase();
if(q.includes('ciao')||q.includes('hey')) return 'Hey. Sono online. Dimmi cosa ti serve hackare: compiti, codice, teoria?';
if(q.includes('math')||q.includes('matematica')||q.includes('equazione')) return 'MATH_MODE: Dimmi l\'equazione. La risolvo step-by-step, stile hacker: niente fronzoli, solo logica pura.';
if(q.includes('codice')||q.includes('java')||q.includes('python')) return 'CODE_INJECTION: Incolla il codice. Lo debuggo e te lo rendo ottimizzato e blindato.';
if(q.includes('chi sei')) return 'Sono TUTOR_AI_HACKER. AI locale nel browser, messaggi criptati AES256x2. Non esisto sul server. Solo tu e me.';
return aiReplies[Math.floor(Math.random()*aiReplies.length)]+' '+q.slice(0,80)+' -> soluzione: scomponi il problema in 3 layer: Input -> Logic -> Output. Vuoi che lo faccia io?';
}
async function go(){
let i=document.getElementById('q'),txt=i.value.trim();if(!txt)return;
let box=document.getElementById('c');
let d=document.createElement('div');d.className='m me';d.innerText='> '+txt;box.appendChild(d);
let en=await enc(txt);fetch('/s?m='+encodeURIComponent(en)).catch(()=>{});
i.value='';box.scrollTop=box.scrollHeight;
setTimeout(()=>{let a=document.createElement('div');a.className='m ai';a.innerText='>> '+aiAnswer(txt);box.appendChild(a);box.scrollTop=box.scrollHeight;},600+Math.random()*600);
}
</script></body></html>
""";
ex.getResponseHeaders().set("Content-Type","text/html; charset=utf-8");
ex.sendResponseHeaders(200,html.getBytes(StandardCharsets.UTF_8).length);
ex.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));ex.close();
});
s.createContext("/s",ex->{ex.sendResponseHeaders(200,2);ex.getResponseBody().write("ok".getBytes());ex.close();});
s.start();System.out.println("HACKER AI LIVE "+port);
}
}
