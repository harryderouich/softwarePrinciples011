package testPlatform;

import certificateGenerator.BasicCertificate;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.FileHandling;
import utils.InputReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class AdminPlatform {

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

            // Create a new JSONObject for the question
            JSONObject questionObject = new JSONObject();
            questionObject.put("question", question);
            questionObject.put("answers", new JSONArray(answers));
            questionObject.put("correctAnswerIndex", correctAnswerIndex);

            // Add the question object to the questions array
            questions.put(questionObject);
        }
        int passPercentage = input.readIntInRange("Enter the pass rate percentage", 0, 100);

        JSONObject newQuizObject = new JSONObject();
        newQuizObject.put("title", quizTitle);
        newQuizObject.put("questions", questions);
        newQuizObject.put("passmark", passPercentage);

        // Todo remove check?
        JSONObject jsonObject;
        String fileContent = UserPlatform.readFromFile();
        if (fileContent.isEmpty()) {
            jsonObject = new JSONObject();
            jsonObject.put("quizzes", new JSONArray());
        } else {
            jsonObject = new JSONObject(fileContent);
        }

        // Retrieve the existing quizzes
        JSONArray quizzesArray = jsonObject.getJSONArray("quizzes");

        // Add the new quiz to the existing quizzes
        quizzesArray.put(newQuizObject);

        // Write the combined quiz array back to the file
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(jsonObject.toString(4));
        }

        System.out.println("'" + quizTitle + "' has been added successfully");
    }

    public static void generateLoginKeys() throws IOException {
        InputReader input = new InputReader();
        System.out.println("Choose a quiz to generate a login key for");
        UserPlatform.displayQuizzes();

        int chosenQuiz = input.readValidInt("Select an option", UserPlatform.generateQuizIntList(UserPlatform.getNumberOfQuizzes()));
        String loginKey = UUID.randomUUID().toString().replaceAll("[^a-z0-9]", "").substring(0, 10);

        System.out.println("Login Key '" + loginKey + "' generated for quiz " + chosenQuiz); // Todo create method to retrieve quiz title

        FileHandling.writeLoginKeyCSV(loginKey, chosenQuiz);

        // todo generate prefilled data for user
        // HashMap<String, String> userData = BasicCertificate.singleCertCapture();
    }
}
