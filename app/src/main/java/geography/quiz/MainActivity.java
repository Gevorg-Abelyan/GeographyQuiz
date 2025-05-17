package geography.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textView;
    FirebaseUser user;
    ImageView settingsIcon, leaderboardIcon, accountIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        // Check if user is logged in
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        // Logout functionality

        // Category selection
        LinearLayout geographyLayout = findViewById(R.id.geographyLayout);
        LinearLayout flagLayout = findViewById(R.id.flagLayout);
        LinearLayout areaLayout = findViewById(R.id.areaLayout);
        LinearLayout capitalLayout = findViewById(R.id.capitalLayout);

        geographyLayout.setOnClickListener(view -> openActivity(GeographyGeneralKnowledge.class));
        flagLayout.setOnClickListener(view -> openActivity(GuessCountryByItsFlag.class));
        areaLayout.setOnClickListener(view -> openActivity(GuessCountryByItsArea.class));
        capitalLayout.setOnClickListener(view -> openActivity(GuessTheCapitalOfCountry.class));
        settingsIcon = findViewById(R.id.navigation_settings);
        leaderboardIcon = findViewById(R.id.navigation_quiz);
        accountIcon = findViewById(R.id.navigation_account);
        settingsIcon.setOnClickListener(view -> startActivity(new Intent(this, Settings.class)));
        leaderboardIcon.setOnClickListener(view -> startActivity(new Intent(this, Leaderboard.class)));
        accountIcon.setOnClickListener(view -> startActivity(new Intent(this, Account.class)));
    }


    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }
}