package geography.quiz;

public class QuestionAnswerFlags {
    public static int[] flagImages = {
            R.drawable.mexico,
            R.drawable.england,
            R.drawable.armenia,
            R.drawable.china,
            R.drawable.japan,
            R.drawable.canada,
            R.drawable.france,
            R.drawable.italy,
            R.drawable.russia,
            R.drawable.germany
    };
    public static String[][] choices = {
            {"Mexico", "USA", "Brazil", "Argentina"},
            {"Scotland", "England", "Wales", "Ireland"},
            {"Armenia", "Georgia", "Azerbaijan", "Turkey"},
            {"China", "Japan", "South Korea", "Vietnam"},
            {"Thailand", "China", "South Korea", "Japan"},
            {"Canada", "USA", "Australia", "France"},
            {"Germany", "Italy", "Spain", "France"},
            {"France", "Italy", "Portugal", "Spain"},
            {"Russia", "Ukraine", "Belarus", "Poland"},
            {"Germany", "Austria", "Netherlands", "Sweden"}
    };

    public static String[] correctAnswers = {"Mexico", "England", "Armenia", "China", "Japan", "Canada", "France", "Italy", "Russia", "Germany"};
}