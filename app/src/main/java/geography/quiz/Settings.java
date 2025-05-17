package geography.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings extends AppCompatActivity {
    ImageView settingsIcon, leaderboardIcon, accountIcon;
    Switch switchDarkMode, switchShowHints, switchEnableTimer;
    Button buttonSaveSettings, buttonResetSettings;

    SharedPreferences preferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Bottom nav
        settingsIcon = findViewById(R.id.navigation_quiz);
        leaderboardIcon = findViewById(R.id.navigation_leaderboard);
        accountIcon = findViewById(R.id.navigation_account);

        settingsIcon.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        leaderboardIcon.setOnClickListener(view -> startActivity(new Intent(this, Leaderboard.class)));
        accountIcon.setOnClickListener(view -> startActivity(new Intent(this, Account.class)));

        // SharedPreferences
        preferences = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE);

        // Switches
        switchDarkMode = findViewById(R.id.switch_dark_mode);
        switchShowHints = findViewById(R.id.switch_show_hints);
        switchEnableTimer = findViewById(R.id.switch_enable_timer);

        // Buttons
        buttonSaveSettings = findViewById(R.id.button_save_settings);
        buttonResetSettings = findViewById(R.id.button_reset_settings);

        // Load saved preferences
        loadSettings();

        // Save button functionality
        buttonSaveSettings.setOnClickListener(v -> saveSettings());

        // Reset button functionality
        buttonResetSettings.setOnClickListener(v -> {
            resetSettings();
            loadSettings(); // reflect reset
        });
    }

    private void loadSettings() {
        boolean darkMode = preferences.getBoolean("dark_mode", false);
        boolean showHints = preferences.getBoolean("show_hints", true);
        boolean enableTimer = preferences.getBoolean("enable_timer", true);

        switchDarkMode.setChecked(darkMode);
        switchShowHints.setChecked(showHints);
        switchEnableTimer.setChecked(enableTimer);

        // Apply dark mode
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("dark_mode", switchDarkMode.isChecked());
        editor.putBoolean("show_hints", switchShowHints.isChecked());
        editor.putBoolean("enable_timer", switchEnableTimer.isChecked());

        editor.apply();

        // Apply dark mode immediately
        if (switchDarkMode.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void resetSettings() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("dark_mode", false);
        editor.putBoolean("show_hints", true);
        editor.putBoolean("enable_timer", true);

        editor.apply();
    }
}
