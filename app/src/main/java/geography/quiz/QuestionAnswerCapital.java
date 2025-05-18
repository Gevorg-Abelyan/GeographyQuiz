package geography.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionAnswerCapital {
    public static String[] question;
    public static String[][] choices;
    public static String[] correctAnswers;
    public static Random random = new Random();
    private static final int QUESTIONS_PER_QUIZ = 10;

    // Initialize the questions and answers
    static {
        String[] originalQuestions = {
            "What is the capital of Chile?",
            "What is the capital of Portugal?",
            "What is the capital of Mexico?",
            "What is the capital of South Korea?",
            "What is the capital of the United Kingdom?",
            "What is the capital of Ecuador?",
            "What is the capital of Switzerland?",
            "What is the capital of Poland?",
            "What is the capital of Norway?",
            "What is the capital of Sweden?",
            "What is the capital of Canada?",
            "What is the capital of France?",
            "What is the capital of Germany?",
            "What is the capital of Spain?",
            "What is the capital of Italy?",
            "What is the capital of Greece?",
            "What is the capital of Japan?",
            "What is the capital of China?",
            "What is the capital of Australia?",
            "What is the capital of New Zealand?",
            "What is the capital of Brazil?",
            "What is the capital of Argentina?",
            "What is the capital of Russia?",
            "What is the capital of the United States?",
            "What is the capital of India?",
            "What is the capital of Pakistan?",
            "What is the capital of Bangladesh?",
            "What is the capital of Egypt?",
            "What is the capital of Kenya?",
            "What is the capital of South Africa?",
            "What is the capital of Nigeria?",
            "What is the capital of Turkey?",
            "What is the capital of Iran?",
            "What is the capital of Iraq?",
            "What is the capital of Thailand?",
            "What is the capital of Indonesia?",
            "What is the capital of Malaysia?",
            "What is the capital of Philippines?",
            "What is the capital of Vietnam?",
            "What is the capital of Saudi Arabia?",
            "What is the capital of UAE?",
            "What is the capital of Afghanistan?",
            "What is the capital of Ukraine?",
            "What is the capital of Finland?",
            "What is the capital of Denmark?",
            "What is the capital of Netherlands?",
            "What is the capital of Belgium?",
            "What is the capital of Austria?",
            "What is the capital of Hungary?",
            "What is the capital of Czech Republic?"
        };

        String[][] originalChoices = {
            {"Buenos Aires", "Santiago", "Lima", "Bogota"},
            {"Lisbon", "Madrid", "Rome", "Athens"},
            {"Mexico City", "Cancun", "Guadalajara", "Tijuana"},
            {"Seoul", "Tokyo", "Beijing", "Bangkok"},
            {"London", "Edinburgh", "Dublin", "Cardiff"},
            {"Quito", "Lima", "Bogota", "Caracas"},
            {"Zurich", "Bern", "Geneva", "Basel"},
            {"Warsaw", "Krakow", "Gdansk", "Poznan"},
            {"Oslo", "Stockholm", "Copenhagen", "Helsinki"},
            {"Stockholm", "Oslo", "Copenhagen", "Helsinki"},
            {"Ottawa", "Toronto", "Vancouver", "Montreal"},
            {"Paris", "Marseille", "Lyon", "Nice"},
            {"Berlin", "Munich", "Hamburg", "Frankfurt"},
            {"Madrid", "Barcelona", "Valencia", "Seville"},
            {"Rome", "Milan", "Venice", "Naples"},
            {"Athens", "Thessaloniki", "Patras", "Heraklion"},
            {"Tokyo", "Osaka", "Kyoto", "Nagoya"},
            {"Beijing", "Shanghai", "Guangzhou", "Shenzhen"},
            {"Canberra", "Sydney", "Melbourne", "Brisbane"},
            {"Wellington", "Auckland", "Christchurch", "Hamilton"},
            {"Brasília", "Rio de Janeiro", "São Paulo", "Salvador"},
            {"Buenos Aires", "Cordoba", "Rosario", "Mendoza"},
            {"Moscow", "St. Petersburg", "Kazan", "Sochi"},
            {"Washington D.C.", "New York", "Los Angeles", "Chicago"},
            {"New Delhi", "Mumbai", "Bangalore", "Kolkata"},
            {"Islamabad", "Karachi", "Lahore", "Rawalpindi"},
            {"Dhaka", "Chittagong", "Khulna", "Sylhet"},
            {"Cairo", "Alexandria", "Giza", "Luxor"},
            {"Nairobi", "Mombasa", "Kisumu", "Eldoret"},
            {"Pretoria", "Cape Town", "Johannesburg", "Durban"},
            {"Abuja", "Lagos", "Kano", "Ibadan"},
            {"Ankara", "Istanbul", "Izmir", "Bursa"},
            {"Tehran", "Isfahan", "Mashhad", "Tabriz"},
            {"Baghdad", "Basra", "Mosul", "Erbil"},
            {"Bangkok", "Chiang Mai", "Phuket", "Pattaya"},
            {"Jakarta", "Bali", "Surabaya", "Medan"},
            {"Kuala Lumpur", "Penang", "Johor Bahru", "Malacca"},
            {"Manila", "Cebu", "Davao", "Quezon City"},
            {"Hanoi", "Ho Chi Minh City", "Da Nang", "Hue"},
            {"Riyadh", "Jeddah", "Mecca", "Medina"},
            {"Abu Dhabi", "Dubai", "Sharjah", "Ajman"},
            {"Kabul", "Kandahar", "Herat", "Mazar-i-Sharif"},
            {"Kyiv", "Lviv", "Kharkiv", "Odesa"},
            {"Helsinki", "Espoo", "Tampere", "Turku"},
            {"Copenhagen", "Aarhus", "Odense", "Aalborg"},
            {"Amsterdam", "Rotterdam", "The Hague", "Utrecht"},
            {"Brussels", "Antwerp", "Ghent", "Bruges"},
            {"Vienna", "Salzburg", "Graz", "Innsbruck"},
            {"Budapest", "Debrecen", "Szeged", "Miskolc"},
            {"Prague", "Brno", "Ostrava", "Plzen"}
        };

        String[] CorrectAnswers = {
            "Santiago",
            "Lisbon",
            "Mexico City",
            "Seoul",
            "London",
            "Quito",
            "Bern",
            "Warsaw",
            "Oslo",
            "Stockholm",
            "Ottawa",
            "Paris",
            "Berlin",
            "Madrid",
            "Rome",
            "Athens",
            "Tokyo",
            "Beijing",
            "Canberra",
            "Wellington",
            "Brasília",
            "Buenos Aires",
            "Moscow",
            "Washington D.C.",
            "New Delhi",
            "Islamabad",
            "Dhaka",
            "Cairo",
            "Nairobi",
            "Pretoria",
            "Abuja",
            "Ankara",
            "Tehran",
            "Baghdad",
            "Bangkok",
            "Jakarta",
            "Kuala Lumpur",
            "Manila",
            "Hanoi",
            "Riyadh",
            "Abu Dhabi",
            "Kabul",
            "Kyiv",
            "Helsinki",
            "Copenhagen",
            "Amsterdam",
            "Brussels",
            "Vienna",
            "Budapest",
            "Prague"
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
            correctAnswers[i] = CorrectAnswers[originalIndex];
            
            // Shuffle choices while keeping track of correct answer
            String correctChoice = CorrectAnswers[originalIndex];
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
