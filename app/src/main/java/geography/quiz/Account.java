package geography.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private ImageView settingsIcon, leaderboardIcon, accountIcon;
    private TextView headerUsername, headerEmail, headerScore;
    private TextInputEditText currentPasswordInput, newPasswordInput, newUsernameInput;
    private MaterialButton btnChangePassword, btnLogout, btnDeleteAccount, btnChangeUsername;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadUserData();
    }

    private void initializeViews() {
        // Navigation icons
        settingsIcon = findViewById(R.id.navigation_quiz);
        leaderboardIcon = findViewById(R.id.navigation_leaderboard);
        accountIcon = findViewById(R.id.navigation_account);

        // Text views
        headerUsername = findViewById(R.id.headerUsername);
        headerEmail = findViewById(R.id.headerEmail);
        headerScore = findViewById(R.id.headerScore);

        // Input fields
        currentPasswordInput = findViewById(R.id.currentPassword);
        newPasswordInput = findViewById(R.id.newPassword);
        newUsernameInput = findViewById(R.id.newUsername);

        // Buttons
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangeUsername = findViewById(R.id.btnChangeUsername);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
    }

    private void setupClickListeners() {
        // Navigation
        settingsIcon.setOnClickListener(view -> startActivity(new Intent(this, Settings.class)));
        leaderboardIcon.setOnClickListener(view -> startActivity(new Intent(this, Leaderboard.class)));
        accountIcon.setOnClickListener(view -> startActivity(new Intent(this, Account.class)));

        // Account management
        btnChangePassword.setOnClickListener(view -> changePassword());
        btnChangeUsername.setOnClickListener(view -> changeUsername());
        btnLogout.setOnClickListener(view -> logout());
        btnDeleteAccount.setOnClickListener(view -> showDeleteAccountConfirmation());

        // Add long press on username to verify database
        headerUsername.setOnLongClickListener(view -> {
            verifyDatabaseContents();
            return true;
        });
    }

    private void loadUserData() {
        if (currentUser != null) {
            // First try to get data from Firebase Auth
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            
            // Set initial values from Firebase Auth
            if (displayName != null && !displayName.isEmpty()) {
                headerUsername.setText(displayName);
            }
            if (email != null) {
                headerEmail.setText(email);
            }

            // Then try to get more detailed data from Realtime Database
            String uid = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            // Update with database data if available
                            if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                                headerUsername.setText(user.getUsername());
                            }
                            if (user.getEmail() != null) {
                                headerEmail.setText(user.getEmail());
                            }
                            // Display score
                            headerScore.setText("Score: " + user.getScore());
                        }
                    } else {
                        // If no data in database, create a new user entry
                        User newUser = new User(email, "", displayName != null ? displayName : email.split("@")[0]);
                        userRef.setValue(newUser)
                            .addOnSuccessListener(aVoid -> {
                                headerUsername.setText(newUser.getUsername());
                                headerEmail.setText(newUser.getEmail());
                                headerScore.setText("Score: 0");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Account.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                            });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Account.this, "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            startActivity(new Intent(Account.this, Login.class));
            finish();
        }
    }

    // Method to update user's score
    public void updateScore(int points) {
        if (currentUser != null && userRef != null) {
            userRef.child("score").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        int currentScore = snapshot.getValue(Integer.class);
                        int newScore = currentScore + points;
                        userRef.child("score").setValue(newScore)
                            .addOnSuccessListener(aVoid -> {
                                headerScore.setText("Score: " + newScore);
                                Toast.makeText(Account.this, "Score updated!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Account.this, "Failed to update score", Toast.LENGTH_SHORT).show();
                            });
                    }
                }
            });
        }
    }

    private void changeUsername() {
        String newUsername = newUsernameInput.getText().toString().trim();

        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Please enter a new username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newUsername)
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Account.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                        newUsernameInput.setText("");
                        loadUserData(); // Refresh the displayed username
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Account.this, "Failed to update username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void changePassword() {
        String currentPassword = currentPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentUser != null) {
            // Reauthenticate user before changing password
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
            currentUser.reauthenticate(credential)
                    .addOnSuccessListener(aVoid -> {
                        // Password change after reauthentication
                        currentUser.updatePassword(newPassword)
                                .addOnSuccessListener(aVoid1 -> {
                                    Toast.makeText(Account.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    currentPasswordInput.setText("");
                                    newPasswordInput.setText("");
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(Account.this, "Failed to update password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Account.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mAuth.signOut();
                    startActivity(new Intent(Account.this, Login.class));
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showDeleteAccountConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> showPasswordConfirmation())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showPasswordConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Password");
        builder.setMessage("Please enter your password to confirm account deletion");

        final TextInputEditText passwordInput = new TextInputEditText(this);
        passwordInput.setHint("Password");
        builder.setView(passwordInput);

        builder.setPositiveButton("Delete", (dialog, which) -> {
            String password = passwordInput.getText().toString().trim();
            if (!password.isEmpty()) {
                deleteAccount(password);
            } else {
                Toast.makeText(Account.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteAccount(String password) {
        if (currentUser != null && currentUser.getEmail() != null) {
            // Reauthenticate user before deleting account
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), password);
            currentUser.reauthenticate(credential)
                    .addOnSuccessListener(aVoid -> {
                        // Delete account after reauthentication
                        currentUser.delete()
                                .addOnSuccessListener(aVoid1 -> {
                                    Toast.makeText(Account.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Account.this, Login.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(Account.this, "Failed to delete account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Account.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void verifyDatabaseContents() {
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Get all data as a map
                        Object data = snapshot.getValue();
                        // Create a detailed message
                        StringBuilder message = new StringBuilder("Database Contents:\n");
                        message.append("Username: ").append(snapshot.child("username").getValue()).append("\n");
                        message.append("Email: ").append(snapshot.child("email").getValue()).append("\n");
                        message.append("Score: ").append(snapshot.child("score").getValue()).append("\n");
                        
                        // Show the data in a dialog
                        new AlertDialog.Builder(Account.this)
                            .setTitle("Database Verification")
                            .setMessage(message.toString())
                            .setPositiveButton("OK", null)
                            .show();
                    } else {
                        Toast.makeText(Account.this, "No data found in database", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Account.this, "Failed to read database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in
        if (currentUser == null) {
            startActivity(new Intent(Account.this, Login.class));
            finish();
        }
    }
}
