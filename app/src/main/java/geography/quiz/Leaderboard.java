package geography.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    ImageView settingsIcon, leaderboardIcon, accountIcon;
    RecyclerView leaderboardRecyclerView;
    LeaderboardAdapter leaderboardAdapter;
    List<User> userList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        settingsIcon = findViewById(R.id.navigation_settings);
        leaderboardIcon = findViewById(R.id.navigation_quiz);
        accountIcon = findViewById(R.id.navigation_account);
        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaderboardAdapter = new LeaderboardAdapter(userList);
        leaderboardRecyclerView.setAdapter(leaderboardAdapter);

        settingsIcon.setOnClickListener(view -> startActivity(new Intent(this, Settings.class)));
        leaderboardIcon.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        accountIcon.setOnClickListener(view -> startActivity(new Intent(this, Account.class)));

        fetchLeaderboard();
    }

    private void fetchLeaderboard() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    User user = userSnap.getValue(User.class);
                    if (user != null && user.getUsername() != null) {
                        userList.add(user);
                    }
                }
                // Sort by score descending
                Collections.sort(userList, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return Integer.compare(u2.getScore(), u1.getScore());
                    }
                });
                leaderboardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }
}
