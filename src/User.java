import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login() {
        BufferedReader reader = null;

        try {
            // Open and read the account.json file
            reader = new BufferedReader(new FileReader("account.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Manually parse the JSON content
            String jsonString = jsonContent.toString();
            String storedUsername = extractValue(jsonString, "\"username\":");
            String storedPassword = extractValue(jsonString, "\"password\":");

            // Compare the provided username and password with the stored values
            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    }

    // Helper method to extract values from JSON strings
    private String extractValue(String jsonString, String key) {
        int startIndex = jsonString.indexOf(key) + key.length();
        int endIndex = jsonString.indexOf(",", startIndex);

        if (endIndex == -1) {
            endIndex = jsonString.indexOf("}", startIndex);
        }

        return jsonString.substring(startIndex, endIndex).trim().replace("\"", "");
    }
}
