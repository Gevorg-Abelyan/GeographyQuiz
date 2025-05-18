package geography.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionAnswerGeneralKnowledge {
    public static String[] question;
    public static String[][] choices;
    public static String[] correctAnswers;
    public static Random random = new Random();
    private static final int QUESTIONS_PER_QUIZ = 10;

    // Initialize the questions and answers
    static {
        String[] originalQuestions = {
            "What is the longest river in the world?",
            "Which country has the most time zones?",
            "What is the smallest country in the world by land area?",
            "Which continent has the most countries?",
            "What is the capital of Canada?",
            "Which desert is the largest in the world?",
            "What is the deepest ocean in the world?",
            "Which country has the most natural lakes?",
            "What is the highest mountain in North America?",
            "Which of these countries is landlocked?",
            "What is the capital of Australia?",
            "Which country is both in Europe and Asia?",
            "What ocean lies between Africa and Australia?",
            "Which country has the most islands?",
            "Which is the highest waterfall in the world?",
            "What is the capital of South Korea?",
            "Which U.S. state has the most active volcanoes?",
            "Which country is home to the Great Barrier Reef?",
            "What is the name of the imaginary line that divides Earth into Northern and Southern Hemispheres?",
            "What is the currency used in Japan?",
            "Which African country has the highest population?",
            "What is the capital of Brazil?",
            "Which is the largest continent by land area?",
            "Which mountain range separates Europe and Asia?",
            "Which country is famous for tulips and windmills?",
            "What is the capital of Egypt?",
            "Which is the driest place on Earth?",
            "Which ocean is the warmest?",
            "Which country has the largest population?",
            "Which European city is known as the City of Canals?",
            "What is the tallest building in the world located?",
            "Which country has the shape of a boot?",
            "Which river flows through Paris?",
            "What is the capital of Germany?",
            "Which U.S. state is the Grand Canyon located in?",
            "Which country is home to Mount Everest?",
            "Which city is known as the Big Apple?",
            "Which is the southernmost continent?",
            "Which country borders both the Atlantic and Pacific Oceans?",
            "What is the national language of Brazil?",
            "Which country is made up of over 17,000 islands?",
            "Which desert covers most of northern Africa?",
            "Which continent is the Sahara Desert located in?",
            "What is the capital of Japan?",
            "Which sea is shrinking due to irrigation?",
            "Which country has red, white, and blue horizontal stripes in its flag and is in Europe?",
            "What is the largest country in South America by area?",
            "Which country is known for its maple syrup?",
            "What is the capital city of Kenya?",
            "Which U.S. state is known as the Sunshine State?"
        };

        String[][] originalChoices = {
            {"Amazon River", "Nile River", "Yangtze River", "Mississippi River"},
            {"Russia", "China", "USA", "France"},
            {"Monaco", "San Marino", "Vatican City", "Liechtenstein"},
            {"Asia", "Europe", "Africa", "South America"},
            {"Toronto", "Vancouver", "Ottawa", "Montreal"},
            {"Gobi Desert", "Sahara Desert", "Arabian Desert", "Antarctic Desert"},
            {"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"},
            {"Brazil", "Canada", "USA", "Russia"},
            {"Mount Kilimanjaro", "Mount Logan", "Denali (Mount McKinley)", "Mount Whitney"},
            {"Spain", "Mongolia", "Japan", "Italy"},
            {"Sydney", "Melbourne", "Canberra", "Brisbane"},
            {"Russia", "Turkey", "Kazakhstan", "All of the above"},
            {"Pacific Ocean", "Indian Ocean", "Atlantic Ocean", "Southern Ocean"},
            {"Indonesia", "Sweden", "Philippines", "Norway"},
            {"Niagara Falls", "Angel Falls", "Victoria Falls", "Yosemite Falls"},
            {"Busan", "Seoul", "Incheon", "Daegu"},
            {"California", "Hawaii", "Alaska", "Washington"},
            {"New Zealand", "Fiji", "Australia", "Indonesia"},
            {"Tropic of Cancer", "Prime Meridian", "Equator", "Greenwich Line"},
            {"Won", "Yuan", "Yen", "Baht"},
            {"Egypt", "Nigeria", "South Africa", "Kenya"},
            {"Brasília", "Rio de Janeiro", "São Paulo", "Salvador"},
            {"Africa", "Asia", "North America", "Europe"},
            {"Rocky Mountains", "Himalayas", "Andes", "Ural Mountains"},
            {"Netherlands", "Denmark", "Belgium", "Germany"},
            {"Cairo", "Alexandria", "Giza", "Luxor"},
            {"Sahara", "Atacama", "Namib", "Kalahari"},
            {"Arctic Ocean", "Indian Ocean", "Pacific Ocean", "Atlantic Ocean"},
            {"USA", "India", "China", "Indonesia"},
            {"Venice", "Amsterdam", "Paris", "Copenhagen"},
            {"Shanghai", "Dubai", "New York", "Tokyo"},
            {"Spain", "Italy", "Portugal", "Greece"},
            {"Seine", "Rhine", "Danube", "Loire"},
            {"Berlin", "Munich", "Frankfurt", "Hamburg"},
            {"Nevada", "Arizona", "Utah", "Colorado"},
            {"India", "China", "Nepal", "Pakistan"},
            {"Los Angeles", "Chicago", "New York", "Washington D.C."},
            {"Australia", "South America", "Antarctica", "Asia"},
            {"USA", "Russia", "Chile", "Mexico"},
            {"Spanish", "Portuguese", "French", "English"},
            {"Philippines", "Japan", "Indonesia", "Malaysia"},
            {"Sahara", "Gobi", "Kalahari", "Thar"},
            {"Africa", "Asia", "Australia", "Europe"},
            {"Osaka", "Tokyo", "Kyoto", "Nagoya"},
            {"Baltic Sea", "Aral Sea", "Black Sea", "Red Sea"},
            {"Croatia", "Netherlands", "Luxembourg", "Russia"},
            {"Argentina", "Colombia", "Peru", "Brazil"},
            {"USA", "Canada", "Norway", "Sweden"},
            {"Nairobi", "Mombasa", "Kampala", "Addis Ababa"},
            {"California", "Florida", "Texas", "Nevada"}
        };

        String[] originalCorrectAnswers = {
            "Nile River",
            "France",
            "Vatican City",
            "Africa",
            "Ottawa",
            "Antarctic Desert",
            "Pacific Ocean",
            "Canada",
            "Denali (Mount McKinley)",
            "Mongolia",
            "Canberra",
            "All of the above",
            "Indian Ocean",
            "Sweden",
            "Angel Falls",
            "Seoul",
            "Alaska",
            "Australia",
            "Equator",
            "Yen",
            "Nigeria",
            "Brasília",
            "Asia",
            "Ural Mountains",
            "Netherlands",
            "Cairo",
            "Atacama",
            "Indian Ocean",
            "China",
            "Venice",
            "Dubai",
            "Italy",
            "Seine",
            "Berlin",
            "Arizona",
            "Nepal",
            "New York",
            "Antarctica",
            "USA",
            "Portuguese",
            "Indonesia",
            "Sahara",
            "Africa",
            "Tokyo",
            "Aral Sea",
            "Netherlands",
            "Brazil",
            "Canada",
            "Nairobi",
            "Florida"
        };

        // Create a list of indices to shuffle
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < originalQuestions.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Take only first 10 indices
        indices = indices.subList(0, Math.min(QUESTIONS_PER_QUIZ, indices.size()));

        // Initialize the arrays with only 10 questions
        question = new String[QUESTIONS_PER_QUIZ];
        choices = new String[QUESTIONS_PER_QUIZ][4];
        correctAnswers = new String[QUESTIONS_PER_QUIZ];

        // Shuffle questions and maintain correct answer mapping
        for (int i = 0; i < indices.size(); i++) {
            int originalIndex = indices.get(i);
            question[i] = originalQuestions[originalIndex];
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

    public static String[] getQuestion() {
        return question;
    }

    public static String[][] getChoices() {
        return choices;
    }

    public static String[] getCorrectAnswers() {
        return correctAnswers;
    }
}
