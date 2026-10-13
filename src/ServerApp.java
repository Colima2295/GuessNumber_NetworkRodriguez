import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerApp {

    // Número secreto aleatorio entre 1 y 100
    private static int secretNumber = (int)(Math.random() * 100) + 1;

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/guess", (HttpExchange exchange) -> {
            String query = exchange.getRequestURI().getQuery();

            if (query == null || !query.contains("value=")) {
                sendResponse(exchange, "Missing 'value' parameter");
                return;
            }

            String valueStr = query.split("value=")[1];
            int guess;

            try {
                guess = Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                sendResponse(exchange, "Invalid number");
                return;
            }

            String result = checkGuess(guess);
            sendResponse(exchange, result);
        });

        server.start();
        System.out.println("Server running on port 8000...");
        System.out.println("SECRET NUMBER (DEBUG): " + secretNumber); // <-- AQUÍ LO VES
    }

    private static String checkGuess(int guess) {
        if (guess < secretNumber) return "Too low";
        if (guess > secretNumber) return "Too high";
        return "Correct";
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
