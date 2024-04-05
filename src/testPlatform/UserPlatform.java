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

    // Point to file containing questions for use in quizzes
    public static final String questionsJson = "questions.json";

    public static JSONArray getQuizzesJSONArray() throws IOException {
        // Read the data from questionsJson and create a new JSONObject using it
        JSONObject json = new JSONObject(readFromFile(questionsJson));
        // Create a JSONArray using the data returned from the key 'quizzes'
        return json.getJSONArray("quizzes");
    }


    // Display list of quizzes loaded from questions JSON
    public static void displayQuizzes() throws IOException {
        // Retrieve a JSONArray containing all quizzes
        JSONArray quizzes = getQuizzesJSONArray();

        System.out.println("\nAvailable Quizzes");
        // Loop over all the quizzes one at a time
        for (int i = 0; i < quizzes.length(); i++) {
            // Retrieve the quiz name for each index
            System.out.println(i+1 + ". " + getQuizName(i+1));
        }
    }

    // Retrieve a single quiz name by quiz index
    public static String getQuizName(int quizIndex) throws IOException {
        // Retrieve a JSONArray containing all quizzes
        JSONArray quizzes = getQuizzesJSONArray();

        // For the selected quiz index (-1 to get back to index 0 numbering) create a JSONObject
        JSONObject quiz = quizzes.getJSONObject(quizIndex-1);
        return quiz.getString("title");
    }

    // Run the quiz for the user using a chosen quiz index
    public static boolean runQuiz(int chosenQuiz) throws IOException {
        InputReader input = new InputReader();

        // Retrieve a JSONArray containing all quizzes
        JSONArray quizzes = getQuizzesJSONArray();

        // Retrieve the chosen quiz
        JSONObject quiz = quizzes.getJSONObject(chosenQuiz - 1);

        // Retrieve the data for the quiz - title, questions and passmark
        String title = quiz.getString("title");
        JSONArray questions = quiz.getJSONArray("questions");
        int passMark = quiz.getInt("passmark");

        // Counter to store number of correct answers
        int marks = 0;

        System.out.println(title);

        // Loop over the list of questions
        for (int i = 0; i < questions.length(); i++) {
            // Create a JSONObject for the selected question
            JSONObject questionObject = questions.getJSONObject(i);
            // Retrieve and store the string asking the question
            String questionString = questionObject.getString("question");
            // Retrieve an array of the answers to the selected question
            JSONArray answersArray = questionObject.getJSONArray("answers");
            // Output the question string
            System.out.println("Question " + (i+1) + ": " + questionString);

            // Loop over and display the available answers with an index
            for (int j = 0; j < answersArray.length(); j++) {
                System.out.println((j+1) + ". " + answersArray.getString(j));
            }

            // Prompt the user to select an answer
            int userAnswerIndex = input.readValidInt("Select an answer", generateArrayListSequence(1, answersArray.length()));

            // Determine if the answer is correct
            if (userAnswerIndex - 1 == questionObject.getInt("correctAnswerIndex")) {
                System.out.println("Correct!");
                // Increment marks by 1
                marks += 1;
            } else {
                System.out.println("Incorrect.");
            }
        }

        // Calculate the percentage score
        int numQuestions = questions.length();
        double percentageScored = (double) marks / numQuestions * 100;
        System.out.println("Total: " + marks + "/" + numQuestions);
        // todo round percentage to 0 or 1 decimal places
        System.out.println(percentageScored + "%");

        // Evaluate if the percentage score is equal to or above the pass mark for the quiz
        if (percentageScored >= passMark) {
            System.out.println("You have passed!");
            return true;
        }
        // Else
        System.out.println("Sorry, you have not passed.");
        return false;
    }

    // Read data from a filename and return it as a string
    public static String readFromFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    // Calculate number of quizzes currently stored in questionsJson
    public static int getNumberOfQuizzes() throws IOException {
        // Retrieve questionsJson as a JSONObject
        JSONObject json = new JSONObject(readFromFile(questionsJson));
        // Load all quizzes
        JSONArray allQuizzes = json.getJSONArray("quizzes");
        // Return the length (# of quizzes)
        return allQuizzes.length();
    }

    // Generate an ArrayList of integers from a starting number to an upper limit
    public static ArrayList<Integer> generateArrayListSequence(int start, int upperLimit) {
        // Empty ArrayList to store the integers
        ArrayList<Integer> sequenceArrayList = new ArrayList<>();
        // For start-upperLimit..
        for (int i = start; i <= upperLimit; i++) {
            // Add the integer to the ArrayList
            sequenceArrayList.add(i);
        }
        return sequenceArrayList;
    }
}