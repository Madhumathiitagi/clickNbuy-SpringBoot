import com.google.genai.Client;
import com.google.genai.models.Model;
import java.util.List;

public class ListModels {
    public static void main(String[] args) {
        String apiKey = "AIzaSyDQF6opzpgHISrFZc4Y3Gncjh1yDDnNeyc";
        try {
            Client client = Client.builder().apiKey(apiKey).build();
            // The Java SDK might have a different way to list models
            // Let's just try to generate a simple response first to see if the key works
            System.out.println("Testing API key...");
            var response = client.models.generateContent("gemini-1.5-flash", "Hello");
            System.out.println("Success: " + response.text());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
