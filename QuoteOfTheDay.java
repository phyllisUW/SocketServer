import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Random;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.io.BufferedReader;

public class QuoteOfTheDay {
    static final int PORT = 17;
    static final String[] QUOTES = {
            "Hello. My name is Inigo Montoya. You killed my father. Prepare to die.",
            "Death cannot stop true love. All it can do is delay it for a while.",
            "Have fun storming the castle!",
            "Life is pain, Highness. Anyone who says differently is selling something.",
 
            // some quotes from Spiderman movie
            "Not everyone is meant to make a difference. But for me, the choice to lead an ordinary life is no longer an option.",
            "Peter Parker: You don't trust anyone, that's your problem."
    };
    static final Random randomQuote = new Random();

    public static void main(String[] args) {
        try (ServerSocket Socket = new ServerSocket(PORT)) {
            System.out.println("Quote of the Day Server");

            while (true) {
                System.out.println("Connecting");
                Socket client = Socket.accept();
                System.out.println("Connected");

                new Thread(() -> {
                    try {
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        System.out.println("Get a random movie QUote");

                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String request = in.readLine();

                        if (request != null && request.equals("GET")) {
                            String quote = getQuote();
                            System.out.println(quote);
                            out.println(quote);
                        }

                        client.close();
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static String getQuote() {
        return QUOTES[randomQuote.nextInt(QUOTES.length)];
    }
}
