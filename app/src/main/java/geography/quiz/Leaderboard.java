package geography.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.*;

public class Leaderboard extends AppCompatActivity {
    private RecyclerView leaderboardRecyclerView;
    private LeaderboardAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private TextView firstUsername, firstScore, secondUsername, secondScore, thirdUsername, thirdScore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firstUsername = findViewById(R.id.firstUsername);
        firstScore = findViewById(R.id.firstScore);
        secondUsername = findViewById(R.id.secondUsername);
        secondScore = findViewById(R.id.secondScore);
        thirdUsername = findViewById(R.id.thirdUsername);
        thirdScore = findViewById(R.id.thirdScore);

        fetchLeaderboard();


        ImageView leaderboardIcon = findViewById(R.id.navigation_leaderboard);
        ImageView accountIcon = findViewById(R.id.navigation_account);
        ImageView quizIcon = findViewById(R.id.navigation_quiz);


        
        leaderboardIcon.setOnClickListener(view -> {
            if (!getClass().getSimpleName().equals("Leaderboard")) {
                startActivity(new Intent(this, Leaderboard.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        
        accountIcon.setOnClickListener(view -> {
            if (!getClass().getSimpleName().equals("Account")) {
                startActivity(new Intent(this, Account.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        
        quizIcon.setOnClickListener(view -> {
            if (!getClass().getSimpleName().equals("MainActivity")) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void fetchLeaderboard() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    User user = userSnap.getValue(User.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                // Sort by score descending
                Collections.sort(userList, (u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
                updateLeaderboardUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void updateLeaderboardUI() {
        // Top 3 cards
        if (userList.size() > 0) {
            User first = userList.get(0);
            firstUsername.setText(first.getUsername());
            firstScore.setText(String.valueOf(first.getScore()));
        } else {
            firstUsername.setText("-");
            firstScore.setText("-");
        }

        if (userList.size() > 1) {
            User second = userList.get(1);
            secondUsername.setText(second.getUsername());
            secondScore.setText(String.valueOf(second.getScore()));
        } else {
            secondUsername.setText("-");
            secondScore.setText("-");
        }

        if (userList.size() > 2) {
            User third = userList.get(2);
            thirdUsername.setText(third.getUsername());
            thirdScore.setText(String.valueOf(third.getScore()));
        } else {
            thirdUsername.setText("-");
            thirdScore.setText("-");
        }

        // Next 7 users in RecyclerView
        int end = Math.min(userList.size(), 10); // Total top 10
        List<User> rest = userList.size() > 3 ? userList.subList(3, end) : new ArrayList<>();
        adapter = new LeaderboardAdapter(rest, 4); // Start rank at 4
        leaderboardRecyclerView.setAdapter(adapter);
    }

}
