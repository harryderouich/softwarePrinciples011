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

    private static final String filename = "questions.json";

    public static void displayQuizzes() throws IOException {
        InputReader input = new InputReader();
        JSONObject json = new JSONObject(readFile());
        JSONArray quizzes = json.getJSONArray("quizzes");

        System.out.println("\nAvailable Quizzes");
        for (int i = 0; i < quizzes.length(); i++) {
            JSONObject quiz = quizzes.getJSONObject(i);
            System.out.println(i+1 + ". " + quiz.getString("title"));
        }
    }

    public static void runQuiz(int chosenQuiz) throws IOException {
        InputReader input = new InputReader();
        JSONObject json = new JSONObject(Files.readAllBytes(Paths.get(UserPlatform.filename)));

        JSONArray quizzes = json.getJSONArray("quizzes");
        JSONObject quiz = quizzes.getJSONObject(chosenQuiz - 1);
        JSONArray questions = quiz.getJSONArray("questions");

        int marks = 0;

        System.out.println(quiz.getString("title"));

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
                System.out.println("Your answer was correct");
                marks += 1;
            } else {
                System.out.println("Your answer was incorrect");
            }
        }

        int numQuestions = 3;
        double percentage = (double) marks / numQuestions * 100;
        System.out.println("Total: " + marks + "/" + numQuestions);
        System.out.println(percentage + "%");
    }

    private static String readFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(UserPlatform.filename)));
    }

}
