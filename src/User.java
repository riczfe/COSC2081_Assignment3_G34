import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String password;
    private String role; // Add a role attribute

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role; // Initialize the role
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

    // Getter for the role
    public String getRole() {
        return role;
    }

    public boolean login() {
        BufferedReader reader = null;

        try {
            // Open and read the account.json file
            reader = new BufferedReader(new FileReader("/Users/erictran/eclipse-workspace/COSC2081_Assignment3_G34/src/account.json"));
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Manually parse the JSON content
            String jsonString = jsonContent.toString();
            Pattern pattern = Pattern.compile("\"username\":\"(.*?)\",\"password\":\"(.*?)\",\"role\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(jsonString);

            while (matcher.find()) {
                String storedUsername = matcher.group(1);
                String storedPassword = matcher.group(2);
                String storedRole = matcher.group(3);

                // Compare the provided username, password, and role with the stored values
                if (username.equals(storedUsername) && password.equals(storedPassword) && role.equals(storedRole)) {
                    System.out.println("Login successful!");
                    return true;
                }
            }

            // If no matching account is found
            System.out.println("Invalid username, password, or role.");
            return false;
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
}
