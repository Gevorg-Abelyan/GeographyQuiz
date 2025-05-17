package geography.quiz;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String password;
    private String username;
    private int score;

    // Default constructor required for Firebase
    public User() {
    }

    // Constructor for new user registration
    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.score = 0;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Helper method to update score
    public void updateScore(int points) {
        this.score += points;
    }

    // Method to convert User object to Map for Firebase
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("username", username);
        result.put("score", score);
        return result;
    }
} 