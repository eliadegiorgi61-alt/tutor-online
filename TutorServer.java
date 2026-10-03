import static spark.Spark.*;

public class TutorServer {
    public static void main(String[] args) {
        port(Integer.parseInt(System.getenv().getOrDefault("PORT", "10000")));
        
        get("/", (req, res) -> {
            res.type("text/html");
            return "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Tutor Online</title><style>body{background:#0a0a0a;color:white;font-family:sans-serif;display:flex;align-items:center;justify-content:center;height:100vh;margin:0;text-align:center}h1{font-size:3rem;color:#8a2be2}p{color:#aaa}.btn{background:#8a2be2;color:white;padding:15px 30px;border-radius:30px;text-decoration:none;display:inline-block;margin-top:20px}</style></head><body><div><h1>Tutor Online</h1><p>Il tuo tutor e' ONLINE</p><p>Il sito funziona!</p><a class='btn' href='#'>Contattami</a></div></body></html>";
        });
    }
}
