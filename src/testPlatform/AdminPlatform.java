package testPlatform;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.FileHandling;
import utils.InputReader;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminPlatform {

    // TODO needs work

    private static final String filename = "questions.json";

    public static void createNewQuiz() throws IOException {

        InputReader input = new InputReader();

        String quizTitle = input.readString("Enter the quiz title");

        JSONArray questions = new JSONArray();
        for (int i = 0; i < 3; i++) { // For each question
            System.out.println("Entering Question " + (i + 1));
            String question = input.readString("Question");

            ArrayList<String> answers = new ArrayList<>();
            for (int j = 0; j < 3; j++) { // For each answer
                answers.add(input.readString("Answer " + (j + 1)));
            }
            int correctAnswerIndex = input.readValidInt("Enter the index of the correct answer 1-3", new ArrayList<>(Arrays.asList(1, 2, 3)));

            JSONObject questionObject = new JSONObject();
            questionObject.put("question", question);
            questionObject.put("answers", answers);
            questionObject.put("correctAnswerIndex", correctAnswerIndex);
            questions.put(questionObject);
        }
        int passPercentage = input.readIntInRange("Enter the pass rate percentage", 0, 100);

        JSONObject newQuizObject = new JSONObject();
        newQuizObject.put("title", quizTitle);
        newQuizObject.put("questions", questions);
        newQuizObject.put("passmark", passPercentage);

        JSONObject jsonObject = new JSONObject();
        JSONArray quizzesArray;
        try {
            String jsonFile = FileHandling.readFile();
            jsonObject = new JSONObject(jsonFile);
            quizzesArray = jsonObject.getJSONArray("quizzes");
        } catch (IOException e) {
            quizzesArray = new JSONArray();
        }

        quizzesArray.put(newQuizObject);
        jsonObject.put("quizzes", quizzesArray);

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(jsonObject.toString(4));
        }

        System.out.println("'" + quizTitle + "' has been added successfully");

    }
}
