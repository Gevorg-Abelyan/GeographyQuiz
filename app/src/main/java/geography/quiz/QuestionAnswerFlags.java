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
            {"England", "Scotland", "Wales", "Ireland"},
            {"Armenia", "Georgia", "Azerbaijan", "Turkey"},
            {"China", "Japan", "South Korea", "Vietnam"},
            {"Japan", "China", "South Korea", "Thailand"},
            {"Canada", "USA", "Australia", "France"},
            {"France", "Italy", "Spain", "Germany"},
            {"Italy", "France", "Portugal", "Spain"},
            {"Russia", "Ukraine", "Belarus", "Poland"},
            {"Germany", "Austria", "Netherlands", "Sweden"}
    };

    public static String[] correctAnswers = {"Mexico", "England", "Armenia", "China", "Japan", "Canada", "France", "Italy", "Russia", "Germany"};
}
