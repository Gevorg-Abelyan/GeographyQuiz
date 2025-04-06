package geography.quiz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuessCountryByItsArea extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionsTextView, scoreTextView;
    ImageView flagImageView, exitImageView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    int score = 0;
    int totalQuestion = QuestionAnswerAres.areaImages.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    Button selectedButton = null;
    Handler handler = new Handler();
    boolean isSubmitClicked = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_country_by_its_flag);
        totalQuestionsTextView = findViewById(R.id.total_question);
        scoreTextView = findViewById(R.id.score_text);
        flagImageView = findViewById(R.id.flag_image);
        exitImageView = findViewById(R.id.exit_image);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

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
        String correctAnswer = QuestionAnswerAres.correctAnswers[currentQuestionIndex];

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
        flagImageView.setImageResource(QuestionAnswerAres.areaImages[currentQuestionIndex]);
        ansA.setText(QuestionAnswerAres.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswerAres.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswerAres.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswerAres.choices[currentQuestionIndex][3]);
        selectedButton = null;
    }

    void finishQuiz() {
        String passStatus = score > totalQuestion * 0.60 ? "Passed" : "Failed";
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
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

    void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Intent intent = new Intent(GuessCountryByItsArea.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .show();
    }
}