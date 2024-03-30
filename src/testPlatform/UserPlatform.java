package testPlatform;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.InputReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UserPlatform {

    public static final String QUESTIONS_JSON_FILENAME = "questions.json";

    public static void displayQuizzes() throws IOException {
        JSONObject json = new JSONObject(readFromFile());
        JSONArray allQuizzes = json.getJSONArray("quizzes");

        System.out.println("\nAvailable Quizzes");
        for (int i = 0; i < allQuizzes.length(); i++) {
            JSONObject quiz = allQuizzes.getJSONObject(i);
            System.out.println(i+1 + ". " + quiz.getString("title"));
        }
    }

    public static String getQuizName(int quizIndex) throws IOException {
        JSONObject json = new JSONObject(readFromFile());
        JSONArray allQuizzes = json.getJSONArray("quizzes");
        JSONObject quiz = allQuizzes.getJSONObject(quizIndex-1);
        return quiz.getString("title");
    }

    public static boolean runQuiz(int chosenQuiz) throws IOException {
        InputReader input = new InputReader();
        JSONObject json = new JSONObject(readFromFile());

        JSONArray quizzes = json.getJSONArray("quizzes");
        JSONObject quiz = quizzes.getJSONObject(chosenQuiz - 1);

        JSONArray questions = quiz.getJSONArray("questions");
        int passMark = quiz.getInt("passmark");
        String title = quiz.getString("title");

        int marks = 0;

        System.out.println(title);

        for (int i = 0; i < questions.length(); i++) {
            JSONObject questionObject = questions.getJSONObject(i);
            String question = questionObject.getString("question");
            JSONArray answersArray = questionObject.getJSONArray("answers");
            System.out.println("Question " + (i + 1) + ": " + question);

            for (int j = 0; j < answersArray.length(); j++) {
                System.out.println((j + 1) + ". " + answersArray.getString(j));
            }

            int userAnswerIndex = input.readValidInt("Select an answer", new ArrayList<>(Arrays.asList(1, 2, 3)));

            if (userAnswerIndex - 1 == questionObject.getInt("correctAnswerIndex")) {
                System.out.println("Correct!");
                marks += 1;
            } else {
                System.out.println("Incorrect.");
            }
        }

        int numQuestions = questions.length();
        double percentageScored = (double) marks / numQuestions * 100;
        System.out.println("Total: " + marks + "/" + numQuestions);
        // todo round percentage to 0 or 1 decimal places
        System.out.println(percentageScored + "%");

        if (percentageScored >= passMark) {
            System.out.println("You have passed!");
            return true;
        }

        System.out.println("Sorry, you have not passed.");
        return false;
    }


    public static String readFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(UserPlatform.QUESTIONS_JSON_FILENAME)));
    }

    public static int getNumberOfQuizzes() throws IOException {
        JSONObject json = new JSONObject(readFromFile());
        JSONArray allQuizzes = json.getJSONArray("quizzes");
        return allQuizzes.length();
    }

    public static ArrayList<Integer> generateQuizIntList(int numberOfQuizzes) {
        ArrayList<Integer> quizIntList = new ArrayList<>();
        for (int i = 1; i <= numberOfQuizzes; i++) {
            quizIntList.add(i);
        }
        return quizIntList;
    }
}