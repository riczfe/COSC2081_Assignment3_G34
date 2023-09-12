import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        boolean loginSuccessful = false;
        String username = this.username;
        String password = this.password;
        try (Scanner scanner = new Scanner(new File("./src/data/login.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(";");
                String storedUsername = credentials[0];
                String storedPassword = credentials[1];

                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    System.out.println("Login successful!");
                    loginSuccessful = true;
                    break;
                }
            }

            if (!loginSuccessful) {
                System.out.println("Invalid username or password.");
            }

            return loginSuccessful;
        } catch (FileNotFoundException e) {
            System.out.println("The login file was not found.");
            e.printStackTrace();
            return false;
        }
    }
}