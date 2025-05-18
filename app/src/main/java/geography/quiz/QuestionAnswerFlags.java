package geography.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionAnswerFlags {
    public static int[] flagImages;
    public static String[][] choices;
    public static String[] correctAnswers;
    public static Random random = new Random();
    private static final int QUESTIONS_PER_QUIZ = 10;

    // Initialize the questions and answers
    static {
        int[] originalFlagImages = {
            R.drawable.mexico,
            R.drawable.england,
            R.drawable.armenia,
            R.drawable.china,
            R.drawable.japan,
            R.drawable.canada,
            R.drawable.france,
            R.drawable.italy,
            R.drawable.russia,
            R.drawable.germany,
            R.drawable.spain,
            R.drawable.portugal,
            R.drawable.brazil,
            R.drawable.argentina,
            R.drawable.chile,
            R.drawable.colombia,
            R.drawable.peru,
            R.drawable.venezuela,
            R.drawable.bolivia,
            R.drawable.ecuador,
            R.drawable.uruguay,
            R.drawable.paraguay,
            R.drawable.sweden,
            R.drawable.norway,
            R.drawable.finland,
            R.drawable.denmark,
            R.drawable.iceland,
            R.drawable.ireland,
            R.drawable.ukraine,
            R.drawable.poland,
            R.drawable.czechrepublic,
            R.drawable.hungary,
            R.drawable.austria,
            R.drawable.switzerland,
            R.drawable.belgium,
            R.drawable.netherlands,
            R.drawable.greece,
            R.drawable.israel,
            R.drawable.saudiarabia,
            R.drawable.india,
            R.drawable.pakistan,
            R.drawable.bangladesh,
            R.drawable.nepal,
            R.drawable.southafrica,
            R.drawable.nigeria,
            R.drawable.egypt,
            R.drawable.kenya,
            R.drawable.morocco,
            R.drawable.indonesia,
            R.drawable.philippines
        };

        String[][] originalChoices = {
            {"Mexico", "Spain", "Brazil", "Argentina"},
            {"England", "Scotland", "Ireland", "Wales"},
            {"Armenia", "Georgia", "Azerbaijan", "Turkey"},
            {"China", "Japan", "South Korea", "Vietnam"},
            {"Japan", "China", "South Korea", "Philippines"},
            {"Canada", "USA", "Russia", "Greenland"},
            {"France", "Italy", "Spain", "Belgium"},
            {"Italy", "France", "Spain", "Portugal"},
            {"Russia", "Ukraine", "Kazakhstan", "Belarus"},
            {"Germany", "Austria", "Belgium", "Netherlands"},
            {"Spain", "Italy", "France", "Portugal"},
            {"Portugal", "Spain", "Brazil", "Angola"},
            {"Brazil", "Argentina", "Portugal", "Colombia"},
            {"Argentina", "Brazil", "Chile", "Uruguay"},
            {"Chile", "Argentina", "Peru", "Ecuador"},
            {"Colombia", "Venezuela", "Peru", "Brazil"},
            {"Peru", "Bolivia", "Ecuador", "Chile"},
            {"Venezuela", "Colombia", "Ecuador", "Peru"},
            {"Bolivia", "Paraguay", "Peru", "Uruguay"},
            {"Ecuador", "Peru", "Colombia", "Venezuela"},
            {"Uruguay", "Paraguay", "Argentina", "Brazil"},
            {"Paraguay", "Uruguay", "Bolivia", "Argentina"},
            {"Sweden", "Norway", "Finland", "Denmark"},
            {"Norway", "Sweden", "Finland", "Denmark"},
            {"Finland", "Sweden", "Norway", "Estonia"},
            {"Denmark", "Norway", "Sweden", "Netherlands"},
            {"Iceland", "Greenland", "Norway", "Ireland"},
            {"Ireland", "Scotland", "Wales", "England"},
            {"Ukraine", "Poland", "Russia", "Belarus"},
            {"Poland", "Ukraine", "Germany", "Czech Republic"},
            {"Czech Republic", "Slovakia", "Poland", "Hungary"},
            {"Hungary", "Austria", "Slovakia", "Poland"},
            {"Austria", "Switzerland", "Germany", "Hungary"},
            {"Switzerland", "Austria", "Belgium", "Netherlands"},
            {"Belgium", "Netherlands", "France", "Germany"},
            {"Netherlands", "Belgium", "Germany", "Denmark"},
            {"Greece", "Italy", "Turkey", "Cyprus"},
            {"Israel", "Lebanon", "Jordan", "Egypt"},
            {"Saudi Arabia", "UAE", "Oman", "Qatar"},
            {"India", "Pakistan", "Bangladesh", "Nepal"},
            {"Pakistan", "India", "Afghanistan", "Bangladesh"},
            {"Bangladesh", "India", "Nepal", "Myanmar"},
            {"Nepal", "India", "Bhutan", "Bangladesh"},
            {"South Africa", "Nigeria", "Egypt", "Morocco"},
            {"Nigeria", "Ghana", "Cameroon", "Niger"},
            {"Egypt", "Morocco", "Libya", "Algeria"},
            {"Kenya", "Tanzania", "Uganda", "Ethiopia"},
            {"Morocco", "Egypt", "Algeria", "Tunisia"},
            {"Indonesia", "Malaysia", "Philippines", "Vietnam"},
            {"Philippines", "Indonesia", "Malaysia", "Thailand"}
        };

        String[] originalCorrectAnswers = {
            "Mexico",
            "England",
            "Armenia",
            "China",
            "Japan",
            "Canada",
            "France",
            "Italy",
            "Russia",
            "Germany",
            "Spain",
            "Portugal",
            "Brazil",
            "Argentina",
            "Chile",
            "Colombia",
            "Peru",
            "Venezuela",
            "Bolivia",
            "Ecuador",
            "Uruguay",
            "Paraguay",
            "Sweden",
            "Norway",
            "Finland",
            "Denmark",
            "Iceland",
            "Ireland",
            "Ukraine",
            "Poland",
            "Czech Republic",
            "Hungary",
            "Austria",
            "Switzerland",
            "Belgium",
            "Netherlands",
            "Greece",
            "Israel",
            "Saudi Arabia",
            "India",
            "Pakistan",
            "Bangladesh",
            "Nepal",
            "South Africa",
            "Nigeria",
            "Egypt",
            "Kenya",
            "Morocco",
            "Indonesia",
            "Philippines"
        };

        // Create a list of indices to shuffle
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < originalFlagImages.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Take only first 10 indices
        indices = indices.subList(0, Math.min(QUESTIONS_PER_QUIZ, indices.size()));

        // Initialize the arrays with only 10 questions
        flagImages = new int[QUESTIONS_PER_QUIZ];
        choices = new String[QUESTIONS_PER_QUIZ][4];
        correctAnswers = new String[QUESTIONS_PER_QUIZ];

        // Shuffle questions and maintain correct answer mapping
        for (int i = 0; i < indices.size(); i++) {
            int originalIndex = indices.get(i);
            flagImages[i] = originalFlagImages[originalIndex];
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

    public static int[] getFlagImages() {
        return flagImages;
    }

    public static String[][] getChoices() {
        return choices;
    }

    public static String[] getCorrectAnswers() {
        return correctAnswers;
    }
}