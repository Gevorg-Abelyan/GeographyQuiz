package geography.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
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
        button.setOnClickListener(view -> showLogoutConfirmationDialog());

        // Category selection
        LinearLayout geographyLayout = findViewById(R.id.geographyLayout);
        LinearLayout flagLayout = findViewById(R.id.flagLayout);
        LinearLayout areaLayout = findViewById(R.id.areaLayout);
        LinearLayout capitalLayout = findViewById(R.id.capitalLayout);

        geographyLayout.setOnClickListener(view -> openActivity(GeographyGeneralKnowledge.class));
        flagLayout.setOnClickListener(view -> openActivity(GuessCountryByItsFlag.class));
        areaLayout.setOnClickListener(view -> openActivity(GuessCountryByItsArea.class));
        capitalLayout.setOnClickListener(view -> openActivity(GuessTheCapitalOfCountry.class));
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Perform logout
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }
}