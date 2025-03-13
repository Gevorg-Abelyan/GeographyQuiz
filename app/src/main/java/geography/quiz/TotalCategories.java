package geography.quiz; // Change to your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import geography.quiz.GeographyGeneralKnowledge;
import geography.quiz.GuessCountryByItsArea;
import geography.quiz.GuessCountryByItsFlag;
import geography.quiz.GuessTheCapitalOfCountry;
import geography.quiz.R;

public class TotalCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_categories);


        LinearLayout geographyLayout = findViewById(R.id.geographyLayout);
        LinearLayout flagLayout = findViewById(R.id.flagLayout);
        LinearLayout areaLayout = findViewById(R.id.areaLayout);
        LinearLayout capitalLayout = findViewById(R.id.capitalLayout);


        geographyLayout.setOnClickListener(view -> openActivity(GeographyGeneralKnowledge.class));
        flagLayout.setOnClickListener(view -> openActivity(GuessCountryByItsFlag.class));
        areaLayout.setOnClickListener(view -> openActivity(GuessCountryByItsArea.class));
        capitalLayout.setOnClickListener(view -> openActivity(GuessTheCapitalOfCountry.class));
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(TotalCategories.this, activityClass);
        startActivity(intent);
    }
}
