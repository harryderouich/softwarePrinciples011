package testPlatform;

import accounts.Account;
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

    static final ArrayList<String> loginKeyFields = new ArrayList<>(Arrays.asList("Participant Name", "Instructor Name"));
    static final int NUMBER_OF_QUESTIONS = 3; // Constant for the number of questions quizzes should have
    static final int NUMBER_OF_ANSWERS = 3; // Constant for the number of answers each question should have

    // Create a new quiz and save to questions.json
    public static void createNewQuiz() throws IOException {
        InputReader input = new InputReader();
        // Prompt for title/name of the quiz
        String quizTitle = input.readString("Enter the quiz title");

        // Empty JSONArray to store the questions
        JSONArray questions = new JSONArray();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) { // For each question
            System.out.println("Entering Question " + (i + 1));
            // Prompt for a string that asks the question
            String question = input.readString("Question");

            // Create an empty ArrayList to store the answers
            ArrayList<String> answers = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_ANSWERS; j++) { // For each answer
                // Prompt for an answer and store it in the answers ArrayList
                answers.add(input.readString("Answer " + (j + 1)));
            }
            // Prompt for the index of the correct answer
            int correctAnswerIndex = input.readValidInt("Enter the index of the correct answer 1-3", new ArrayList<>(Arrays.asList(1, 2, 3)));

            // Create a new JSONObject for the question
            JSONObject questionObject = new JSONObject();
            // Add the question string, array of answers and index of correct answer
            questionObject.put("question", question);
            questionObject.put("answers", new JSONArray(answers));
            questionObject.put("correctAnswerIndex", correctAnswerIndex);

            // Add the question object to the questions array
            questions.put(questionObject);
        }
        // Prompt for the pass percentage for the quiz as a whole
        int passPercentage = input.readIntInRange("Enter the pass rate percentage", 0, 100);

        // Create an empty JSONObject to store the new quiz
        JSONObject newQuizObject = new JSONObject();

        // Add the title, questions array and passmark to the new quiz JSONObject
        newQuizObject.put("title", quizTitle);
        newQuizObject.put("questions", questions);
        newQuizObject.put("passmark", passPercentage);

        // Initialise a JSONObject for use in upcoming if-else
        JSONObject existingQuizJson;

        // Load the current file's contents into a string
        String fileContent = UserPlatform.readFromFile(UserPlatform.questionsJson);

        // Check if JSON file exists/is empty
        if (fileContent.isEmpty()) {
            existingQuizJson = new JSONObject(); // Create a new empty JSONObject
            existingQuizJson.put("quizzes", new JSONArray()); // Create a new JSONArray with key "quizzes"
        } else {
            existingQuizJson = new JSONObject(fileContent); // Create a new JSONObject using the parsed file content
        }

        // Retrieve the existing quizzes
        JSONArray quizzesArray = existingQuizJson.getJSONArray("quizzes");

        // Add the new quiz to the existing quizzes
        quizzesArray.put(newQuizObject);

        // Write the combined quiz array back to the file
        try (FileWriter fileWriter = new FileWriter(UserPlatform.questionsJson)) {
            fileWriter.write(existingQuizJson.toString(4));
        }

        System.out.println("'" + quizTitle + "' has been added successfully");
    }

    // Generate a login key for a selected quiz
    public static void generateLoginKey(Account loggedInAccount) throws IOException {
        InputReader input = new InputReader();

        // Prompt for the quiz a login key should be generated for
        System.out.println("Choose a quiz to generate a login key for");
        UserPlatform.displayQuizzes();
        int chosenQuiz = input.readValidInt("Select an option", UserPlatform.generateArrayListSequence(1, UserPlatform.getNumberOfQuizzes()));

        // Generate a loginKey consisting of 10 random alphanumeric characters
        String loginKey = UUID.randomUUID().toString().replaceAll("[^a-z0-9]", "").substring(0, 10);

        // Capture details from the user to prefill certificate
        HashMap<String, String> loginKeyData = captureLoginKeyUserData(); // Participant Name & Instructor Name
        loginKeyData.put("Business Name", loggedInAccount.userDetails.get("businessName")); // Business name from loggedInAccount

        // Add generated loginKey, index of chosen quiz (for later retrieval) and fetched quiz name to data HashMap
        loginKeyData.put("loginKey", loginKey);
        loginKeyData.put("quizIndex", String.valueOf(chosenQuiz));
        loginKeyData.put("Course Name", UserPlatform.getQuizName(chosenQuiz));

        // Write the HashMap to the login key database
        FileHandling.writeLoginKeyAndUserDetails(loginKeyData);
        System.out.println("\nLogin Key '" + loginKey + "' generated for quiz '" + UserPlatform.getQuizName(chosenQuiz) + "' for user: "
        + loginKeyData.get("Participant Name"));
    }

    // Capture details from admin to prefill certificate
    public static HashMap<String, String> captureLoginKeyUserData() {
        System.out.println("Please enter the data to prefill the user's certificate");
        HashMap<String, String> userData = new HashMap<>();

        // Loop a shorter list of data fields as others are obtained elsewhere
        for (String s : loginKeyFields) {
            InputReader input = new InputReader();
            userData.put(s, input.readString(s));
        }
        return userData;
    }
}
