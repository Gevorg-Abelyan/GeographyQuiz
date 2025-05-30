package geography.quiz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeographyGeneralKnowledge extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionsTextView;
    TextView questionTextView;
    TextView scoreTextView;
    TextView timerTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    ImageView exitImageView;
    int score = 0;
    int totalQuestion = QuestionAnswerGeneralKnowledge.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    Button selectedButton = null;
    Handler handler = new Handler();
    boolean isSubmitClicked = false;
    private static final int TIMER_DURATION = 10;
    private int timeLeft = TIMER_DURATION;
    private CountDownTimer timer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geography_general_knowledge);
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        scoreTextView = findViewById(R.id.score_text);
        timerTextView = findViewById(R.id.timer_text);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        exitImageView = findViewById(R.id.exit_image);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        exitImageView.setOnClickListener(view -> showExitConfirmationDialog());

        totalQuestionsTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestion);
        scoreTextView.setText("Score: " + score);
        loadNewQuestion();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timeLeft = TIMER_DURATION;
        timerTextView.setText(String.valueOf(timeLeft));
        
        timer = new CountDownTimer(TIMER_DURATION * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.valueOf(timeLeft));
                
                if (timeLeft <= 3) {
                    timerTextView.setTextColor(Color.RED);
                } else {
                    timerTextView.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onFinish() {
                if (!isSubmitClicked) {
                    String correctAnswer = QuestionAnswerGeneralKnowledge.correctAnswers[currentQuestionIndex];
                    highlightCorrectAnswer(correctAnswer);
                    isSubmitClicked = true;
                }
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        if (isSubmitClicked) return;

        resetButtonColors();

        if (view instanceof Button) {
            Button clickedButton = (Button) view;

            if (clickedButton.getId() == R.id.submit_btn) {
                isSubmitClicked = true;
                if (selectedButton == null) {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    isSubmitClicked = false;
                } else {
                    checkAnswer();
                }
            } else {
                selectedAnswer = clickedButton.getText().toString();
                selectedButton = clickedButton;
                clickedButton.setBackgroundColor(Color.MAGENTA);
            }
        }
    }

    void checkAnswer() {
        if (timer != null) {
            timer.cancel();
        }
        String correctAnswer = QuestionAnswerGeneralKnowledge.correctAnswers[currentQuestionIndex];

        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(Color.GREEN);
            score++;
            scoreTextView.setText("Score: " + score);
            handler.postDelayed(() -> {
                currentQuestionIndex++;
                isSubmitClicked = false;
                totalQuestionsTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestion);
                loadNewQuestion();
            }, 2000);
        } else {
            selectedButton.setBackgroundColor(Color.RED);
            highlightCorrectAnswer(correctAnswer);
        }
    }

    void highlightCorrectAnswer(String correctAnswer) {
        if (ansA.getText().toString().equals(correctAnswer)) {
            ansA.setBackgroundColor(Color.GREEN);
        } else if (ansB.getText().toString().equals(correctAnswer)) {
            ansB.setBackgroundColor(Color.GREEN);
        } else if (ansC.getText().toString().equals(correctAnswer)) {
            ansC.setBackgroundColor(Color.GREEN);
        } else if (ansD.getText().toString().equals(correctAnswer)) {
            ansD.setBackgroundColor(Color.GREEN);
        }
        handler.postDelayed(() -> {
            currentQuestionIndex++;
            isSubmitClicked = false;
            totalQuestionsTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestion);
            loadNewQuestion();
        }, 2000);
    }

    void resetButtonColors() {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        resetButtonColors();
        questionTextView.setText(QuestionAnswerGeneralKnowledge.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswerGeneralKnowledge.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswerGeneralKnowledge.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswerGeneralKnowledge.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswerGeneralKnowledge.choices[currentQuestionIndex][3]);
        selectedButton = null;
        isSubmitClicked = false;
        startTimer();
    }

    void finishQuiz() {
        String passStatus = score > totalQuestion * 0.60 ? "Passed" : "Failed";
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Exit", (dialogInterface, i) -> {
                    updateScoreInFirebase(score);
                    Intent intent = new Intent(GeographyGeneralKnowledge.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        isSubmitClicked = false;
        totalQuestionsTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + totalQuestion);
        scoreTextView.setText("Score: " + score);
        loadNewQuestion();
    }

    private void updateScoreInFirebase(int newScore) {
        FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("Users").child(uid).child("score");
            ref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                    int currentScore = 0;
                    if (snapshot.exists()) {
                        Integer value = snapshot.getValue(Integer.class);
                        if (value != null) currentScore = value;
                    }
                    int updatedScore = currentScore + newScore;
                    ref.setValue(updatedScore);
                }
                @Override
                public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {}
            });
        }
    }

    void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Intent intent = new Intent(GeographyGeneralKnowledge.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}