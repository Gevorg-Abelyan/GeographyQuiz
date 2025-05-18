package geography.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionAnswerAres {
    public static int[] areaImages;
    public static String[][] choices;
    public static String[] correctAnswers;
    public static Random random = new Random();
    private static final int QUESTIONS_PER_QUIZ = 10;

    // Initialize the questions and answers
    static {
        int[] originalAreaImages = {
            R.drawable.argentina_area,
            R.drawable.armenia_area,
            R.drawable.australia_area,
            R.drawable.brazil_area,
            R.drawable.kazakhstan_area,
            R.drawable.poland_area,
            R.drawable.portugal_area,
            R.drawable.spain_area,
            R.drawable.uk_area,
            R.drawable.ukraine_area,
            R.drawable.austria_area,
            R.drawable.southkorea_area,
            R.drawable.china_area,
            R.drawable.germany_area,
            R.drawable.france,
            R.drawable.canada_area,
            R.drawable.mexico_area,
            R.drawable.us_area,
            R.drawable.senegal_area,
            R.drawable.japan_area,
            R.drawable.india_area,
            R.drawable.russia_area,
            R.drawable.southafrica_area,
            R.drawable.egypt_area,
            R.drawable.nigeria_area,
            R.drawable.kenya_area,
            R.drawable.ethiopia_area,
            R.drawable.saudiarabia_area,
            R.drawable.bangladesh_area,
            R.drawable.greece_area,
            R.drawable.norway_area,
            R.drawable.sweden_area,
            R.drawable.finland_area,
            R.drawable.denmark_area,
            R.drawable.iceland_area,
            R.drawable.ireland_area,
            R.drawable.newzealand_area,
            R.drawable.indonesia_area,
            R.drawable.philippines_area,
            R.drawable.vietnam_area,
            R.drawable.thailand_area,
            R.drawable.malaysia_area,
            R.drawable.pakistan_area,
            R.drawable.bolivia_area,
            R.drawable.nepal_area,
            R.drawable.iran_area,
            R.drawable.iraq_area,
            R.drawable.israel_area,
            R.drawable.chile_area,
            R.drawable.colombia_area
        };

        String[][] originalChoices = {
            {"Argentina", "Brazil", "Mexico", "Chile"},
            {"Armenia", "Georgia", "Azerbaijan", "Turkey"},
            {"Australia", "New Zealand", "Canada", "USA"},
            {"Brazil", "Argentina", "Colombia", "Peru"},
            {"Kazakhstan", "Uzbekistan", "Mongolia", "Russia"},
            {"Poland", "Ukraine", "Germany", "Hungary"},
            {"Portugal", "Spain", "Italy", "France"},
            {"Spain", "Portugal", "France", "Italy"},
            {"UK", "Ireland", "France", "Germany"},
            {"Ukraine", "Poland", "Russia", "Belarus"},
            {"Austria", "Switzerland", "Germany", "Hungary"},
            {"South Korea", "North Korea", "Japan", "China"},
            {"China", "Japan", "South Korea", "Vietnam"},
            {"Germany", "Austria", "Belgium", "Netherlands"},
            {"France", "Italy", "Spain", "Belgium"},
            {"Canada", "USA", "Russia", "Greenland"},
            {"Mexico", "USA", "Brazil", "Colombia"},
            {"USA", "Canada", "Mexico", "Brazil"},
            {"Senegal", "Mali", "Nigeria", "Guinea"},
            {"Japan", "China", "South Korea", "Philippines"},
            {"India", "Pakistan", "Bangladesh", "Nepal"},
            {"Russia", "Ukraine", "Kazakhstan", "Belarus"},
            {"South Africa", "Nigeria", "Egypt", "Kenya"},
            {"Egypt", "Libya", "Sudan", "Morocco"},
            {"Nigeria", "Ghana", "Cameroon", "Niger"},
            {"Kenya", "Tanzania", "Uganda", "Ethiopia"},
            {"Ethiopia", "Kenya", "Somalia", "Sudan"},
            {"Saudi Arabia", "UAE", "Oman", "Qatar"},
            {"Bangladesh", "India", "Nepal", "Myanmar"},
            {"Greece", "Italy", "Turkey", "Albania"},
            {"Norway", "Sweden", "Finland", "Denmark"},
            {"Sweden", "Norway", "Finland", "Denmark"},
            {"Finland", "Sweden", "Norway", "Estonia"},
            {"Denmark", "Sweden", "Norway", "Netherlands"},
            {"Iceland", "Greenland", "Norway", "Denmark"},
            {"Ireland", "UK", "Scotland", "Wales"},
            {"New Zealand", "Australia", "Fiji", "Samoa"},
            {"Indonesia", "Malaysia", "Philippines", "Thailand"},
            {"Philippines", "Indonesia", "Malaysia", "Vietnam"},
            {"Vietnam", "Laos", "Cambodia", "Thailand"},
            {"Thailand", "Vietnam", "Cambodia", "Malaysia"},
            {"Malaysia", "Indonesia", "Thailand", "Philippines"},
            {"Pakistan", "India", "Bangladesh", "Afghanistan"},
            {"Bolivia", "Peru", "Chile", "Argentina"},
            {"Nepal", "India", "Bhutan", "Bangladesh"},
            {"Iran", "Iraq", "Turkey", "Afghanistan"},
            {"Iraq", "Iran", "Syria", "Jordan"},
            {"Israel", "Palestine", "Lebanon", "Jordan"},
            {"Chile", "Argentina", "Peru", "Bolivia"},
            {"Colombia", "Venezuela", "Ecuador", "Peru"}
        };

        String[] originalCorrectAnswers = {
            "Argentina",
            "Armenia",
            "Australia",
            "Brazil",
            "Kazakhstan",
            "Poland",
            "Portugal",
            "Spain",
            "UK",
            "Ukraine",
            "Austria",
            "South Korea",
            "China",
            "Germany",
            "France",
            "Canada",
            "Mexico",
            "USA",
            "Senegal",
            "Japan",
            "India",
            "Russia",
            "South Africa",
            "Egypt",
            "Nigeria",
            "Kenya",
            "Ethiopia",
            "Saudi Arabia",
            "Bangladesh",
            "Greece",
            "Norway",
            "Sweden",
            "Finland",
            "Denmark",
            "Iceland",
            "Ireland",
            "New Zealand",
            "Indonesia",
            "Philippines",
            "Vietnam",
            "Thailand",
            "Malaysia",
            "Pakistan",
            "Bolivia",
            "Nepal",
            "Iran",
            "Iraq",
            "Israel",
            "Chile",
            "Colombia"
        };

        // Create a list of indices to shuffle
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < originalAreaImages.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Take only first 10 indices
        indices = indices.subList(0, Math.min(QUESTIONS_PER_QUIZ, indices.size()));

        // Initialize the arrays with only 10 questions
        areaImages = new int[QUESTIONS_PER_QUIZ];
        choices = new String[QUESTIONS_PER_QUIZ][4];
        correctAnswers = new String[QUESTIONS_PER_QUIZ];

        // Shuffle questions and maintain correct answer mapping
        for (int i = 0; i < indices.size(); i++) {
            int originalIndex = indices.get(i);
            areaImages[i] = originalAreaImages[originalIndex];
            correctAnswers[i] = originalCorrectAnswers[originalIndex];
            
            // Shuffle choices while keeping track of correct answer
            String correctChoice = originalCorrectAnswers[originalIndex];
            List<String> choiceList = new ArrayList<>();
            for (String choice : originalChoices[originalIndex]) {
                choiceList.add(choice);
            }
            Collections.shuffle(choiceList);
            
            // Ensure correct answer is in the choices
            if (!choiceList.contains(correctChoice)) {
                int randomPosition = random.nextInt(4);
                choiceList.set(randomPosition, correctChoice);
            }
            
            choices[i] = choiceList.toArray(new String[0]);
        }
    }

    public static int[] getAreaImages() {
        return areaImages;
    }

    public static String[][] getChoices() {
        return choices;
    }

    public static String[] getCorrectAnswers() {
        return correctAnswers;
    }
}
