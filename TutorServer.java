import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TutorServer {
    public static void main(String[] args) throws Exception {
        int port = 10000;
        String envPort = System.getenv("PORT");
        if (envPort != null) port = Integer.parseInt(envPort);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", (HttpExchange t) -> {
            String html = """
            <!DOCTYPE html>
            <html lang="it">
            <head>
            <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Tutor Online - Trova il tuo tutor</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;700;800&display=swap" rel="stylesheet">
            <style>body{font-family:'Inter',sans-serif}</style>
            </head>
            <body class="bg-white">
            <nav class="flex justify-between items-center p-6 max-w-7xl mx-auto">
              <div class="font-extrabold text-2xl">Tutor<span class="text-indigo-600">Online</span></div>
              <div class="hidden md:flex gap-6 text-sm font-medium"><a href="#">Come funziona</a><a href="#">Tutor</a><a href="#">Materie</a></div>
              <button class="bg-black text-white px-5 py-2.5 rounded-full text-sm font-bold">Diventa Tutor</button>
            </nav>
            <section class="max-w-7xl mx-auto px-6 py-12 md:py-20 grid md:grid-cols-2 gap-10 items-center">
              <div>
                <h1 class="text-5xl md:text-7xl font-extrabold leading-[0.9]">Trova il<br>tutor <span class="text-indigo-600">giusto</span><br>per te.</h1>
                <p class="mt-6 text-gray-500 text-lg">Lezioni private online e in presenza. Matematica, Inglese, Informatica e altro con i migliori tutor verificati.</p>
                <div class="mt-8 flex gap-3 bg-white border rounded-full p-2 shadow-lg max-w-md">
                  <input id="search" placeholder="Cosa vuoi imparare? es. Matematica" class="flex-1 px-4 outline-none text-sm">
                  <button onclick="cerca()" class="bg-indigo-600 text-white px-6 py-3 rounded-full font-bold text-sm">Cerca</button>
                </div>
                <div class="mt-6 flex items-center gap-3 text-sm"><div class="flex -space-x-2"><div class="w-8 h-8 rounded-full bg-orange-300 border-2 border-white"></div><div class="w-8 h-8 rounded-full bg-blue-300 border-2 border-white"></div><div class="w-8 h-8 rounded-full bg-green-300 border-2 border-white"></div></div><span class="text-gray-500"><b class="text-black">500+</b> studenti soddisfatti</span></div>
              </div>
              <div class="bg-indigo-50 rounded-[2.5rem] p-8 relative">
                <div class="bg-white rounded-3xl p-6 shadow-xl">
                  <div class="flex justify-between items-center"><div class="flex items-center gap-3"><div class="w-12 h-12 rounded-full bg-indigo-600 text-white flex items-center justify-center font-bold">M</div><div><div class="font-bold text-sm">Marco R.</div><div class="text-xs text-gray-400">Matematica • 4.9 ★</div></div></div><span class="bg-green-100 text-green-700 text-xs px-3 py-1 rounded-full font-bold">Online</span></div>
                  <div class="mt-6 p-4 bg-gray-50 rounded-2xl"><div class="text-xs text-gray-400">Prossima lezione</div><div class="font-bold text-sm mt-1">Equazioni di 2° grado - Gio 18:00</div><div class="mt-3 w-full bg-gray-200 rounded-full h-2"><div class="bg-indigo-600 h-2 rounded-full w-[75%]"></div></div></div>
                  <button class="w-full mt-6 bg-black text-white py-3 rounded-full font-bold text-sm">Prenota lezione - €20/h</button>
                </div>
              </div>
            </section>
            <section class="max-w-7xl mx-auto px-6 pb-20">
              <h2 class="font-bold text-xl mb-6">Materie più richieste</h2>
              <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div class="border rounded-3xl p-6 hover:shadow-lg transition cursor-pointer"><div class="text-2xl">📐</div><div class="font-bold mt-3">Matematica</div><div class="text-xs text-gray-400 mt-1">124 tutor</div></div>
                <div class="border rounded-3xl p-6 hover:shadow-lg transition cursor-pointer"><div class="text-2xl">🇬🇧</div><div class="font-bold mt-3">Inglese</div><div class="text-xs text-gray-400 mt-1">98 tutor</div></div>
                <div class="border rounded-3xl p-6 hover:shadow-lg transition cursor-pointer"><div class="text-2xl">💻</div><div class="font-bold mt-3">Informatica</div><div class="text-xs text-gray-400 mt-1">76 tutor</div></div>
                <div class="border rounded-3xl p-6 hover:shadow-lg transition cursor-pointer"><div class="text-2xl">🧪</div><div class="font-bold mt-3">Fisica</div><div class="text-xs text-gray-400 mt-1
