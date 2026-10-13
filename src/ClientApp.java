import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Guess the number!");

        while (true) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();

            try {
                String urlString = "http://localhost:8000/guess?value=" + guess;
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                String response = reader.readLine();
                reader.close();

                System.out.println("Server says: " + response);

                if (response.equals("Correct")) {
                    System.out.println("You guessed the number!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
