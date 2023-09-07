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

    public void login() {
        // Implement login logic here
        // For example, you can check if the provided username and password match the stored credentials
        if (isValidCredentials(username, password)) {
            System.out.println("Login successful. Performing necessary actions...");
            // Perform necessary actions after successful login
        } else {
            System.out.println("Invalid username or password. Login failed.");
            // Handle the error condition appropriately
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Compare the provided username and password with the stored credentials
        // Return true if they match, false otherwise
        // You can implement your own logic here, such as checking against a database or predefined values
        return username.equals("admin") && password.equals("password");
    }
}