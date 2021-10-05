package com.pjiproject.genix;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.Math;
import java.util.*;

public class GameActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Image
    private ImageView imgCloseGame;


    // Buttons
    private Button btnChoice1;
    private Button btnChoice2;
    private Button btnChoice3;
    private Button btnChoice4;


    // Texts
    private TextView lblCurrentQuestion;
    private TextView lblCurrentQuestionNumber;
    private TextView lblTimeRemaining;


    // Others
    int questionIndex = 0;
    int gameSeconds = 60;

    Thread updateSecondsCounter;

    ArrayList<ArrayList> userAnsweredQuestions = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgCloseGame = findViewById(R.id.imgReturnToMenu);
        imgCloseGame.setOnClickListener(new CloseGameListener());

        lblCurrentQuestion = findViewById(R.id.lblCurrentQuestion);

        lblCurrentQuestionNumber = findViewById(R.id.lblCurrentQuestionNumber);
        lblCurrentQuestionNumber.setText("1");

        lblTimeRemaining = findViewById(R.id.lblTimeRemaining);
        lblTimeRemaining.setText(Integer.toString(gameSeconds));

        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);


        // Get questions from MainActivity intent
        Intent mainActivityIntent = getIntent();
        String questions = mainActivityIntent.getStringExtra("questionsString");


        // Parse string data to JSON data
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonQuestionsObject;

        try {

            jsonQuestionsObject = (JSONObject) jsonParser.parse(questions);
            JSONArray jsonQuestionsArray = (JSONArray) jsonQuestionsObject.get("results");

            //System.out.println(jsonQuestionsArray.toString());


            // Assigns the first question
            JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(0);
            lblCurrentQuestion.setText(decodeBase64data(jsonQuestion.get("question").toString()));

            JSONArray incorrectAnswers = (JSONArray) jsonQuestion.get("incorrect_answers");

            assignAnswersToRandomButton(jsonQuestion, incorrectAnswers);


            // Create answer buttons
            class ClickAnswerListener implements View.OnClickListener {

                @Override
                public void onClick(View view) {

                    questionIndex++;

                    switch (view.getId()) {

                        case R.id.btnChoice1:
                            setUserAnsweredQuestions(lblCurrentQuestion.getText().toString(), btnChoice1.getText().toString());
                            break;

                        case R.id.btnChoice2:
                            setUserAnsweredQuestions(lblCurrentQuestion.getText().toString(), btnChoice2.getText().toString());
                            break;

                        case R.id.btnChoice3:
                            setUserAnsweredQuestions(lblCurrentQuestion.getText().toString(), btnChoice3.getText().toString());
                            break;

                        case R.id.btnChoice4:
                            setUserAnsweredQuestions(lblCurrentQuestion.getText().toString(), btnChoice4.getText().toString());
                            break;

                    }

                    if (questionIndex < jsonQuestionsArray.size() && gameSeconds >= 0) {

                        JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(questionIndex);
                        JSONArray incorrectAnswers = (JSONArray) jsonQuestion.get("incorrect_answers");

                        lblCurrentQuestion.setText(decodeBase64data(jsonQuestion.get("question").toString()));
                        lblCurrentQuestionNumber.setText(Integer.toString(questionIndex + 1));

                        assignAnswersToRandomButton(jsonQuestion, incorrectAnswers);

                    } else {

                        ArrayList<String> userTime = new ArrayList<>();
                        userTime.add("time_remaining");
                        userTime.add(lblTimeRemaining.getText().toString());
                        userAnsweredQuestions.add(userTime);
                        endGame();

                    }

                }

            }

            btnChoice1.setOnClickListener(new ClickAnswerListener());
            btnChoice2.setOnClickListener(new ClickAnswerListener());
            btnChoice3.setOnClickListener(new ClickAnswerListener());
            btnChoice4.setOnClickListener(new ClickAnswerListener());


            // Changes game seconds
            updateSecondsCounter = new Thread() {

                @Override
                public void run() {

                    try {

                        while (!this.isInterrupted()) {

                            Thread.sleep(1000);
                            runOnUiThread(() -> {

                                if (gameSeconds > 0) {

                                    gameSeconds--;
                                    lblTimeRemaining.setText(Integer.toString(gameSeconds));

                                } else {

                                    endGame();

                                }

                            });

                        }

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }

            };
            updateSecondsCounter.start();

        } catch (ParseException e) {

            e.printStackTrace();

        }

    }


    /* CLASSES */
    // Button listeners
    public class CloseGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            updateSecondsCounter.interrupt();
            finish();

        }

    }


    /* METHODS */
    // Choose a random button to assign the correct answer
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void assignAnswersToRandomButton(JSONObject jsonQuestion, JSONArray incorrectAnswers) {

        String decodedCorrectAnswerString, decodedIncorrectAnswer1String,
               decodedIncorrectAnswer2String, decodedIncorrectAnswer3String;
        int randomCorrectAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3;

        decodedCorrectAnswerString = decodeBase64data(jsonQuestion.get("correct_answer").toString());
        if (decodedCorrectAnswerString.equals("True") || decodedCorrectAnswerString.equals("False")) {

            btnChoice1.setText("True");
            btnChoice2.setText("False");
            btnChoice3.setEnabled(false);
            btnChoice3.setText("");
            btnChoice4.setEnabled(false);
            btnChoice4.setText("");

        } else {

            btnChoice3.setEnabled(true);
            btnChoice4.setEnabled(true);

            randomCorrectAnswer = (int) (Math.random() * (4 - 1 + 1) + 1);
            switch (randomCorrectAnswer) {

                case 1:
                    btnChoice1.setText(decodedCorrectAnswerString);
                    break;

                case 2:
                    btnChoice2.setText(decodedCorrectAnswerString);
                    break;

                case 3:
                    btnChoice3.setText(decodedCorrectAnswerString);
                    break;

                case 4:
                    btnChoice4.setText(decodedCorrectAnswerString);
                    break;

            }

            incorrectAnswer1 = (int) (Math.random() * (2 - 0 + 1) + 0);
            incorrectAnswer2 = (int) (Math.random() * (2 - 0 + 1) + 0);
            while (incorrectAnswer2 == incorrectAnswer1) {

                incorrectAnswer2 = (int) (Math.random() * (2 - 0 + 1) + 0);

            }

            incorrectAnswer3 = 0;
            switch (incorrectAnswer1) {

                case 0:
                    if (incorrectAnswer2 == 1) {

                        incorrectAnswer3 = 2;

                    } else {

                        incorrectAnswer3 = 1;

                    }
                    break;

                case 1:
                    if (incorrectAnswer2 == 0) {

                        incorrectAnswer3 = 2;

                    } else {

                        incorrectAnswer3 = 0;

                    }
                    break;

                case 2:
                    if (incorrectAnswer2 == 0) {

                        incorrectAnswer3 = 1;

                    } else {

                        incorrectAnswer3 = 0;

                    }
                    break;

            }

            decodedIncorrectAnswer1String = decodeBase64data(incorrectAnswers.get(incorrectAnswer1).toString());
            decodedIncorrectAnswer2String = decodeBase64data(incorrectAnswers.get(incorrectAnswer2).toString());
            decodedIncorrectAnswer3String = decodeBase64data(incorrectAnswers.get(incorrectAnswer3).toString());
            switch (randomCorrectAnswer) {

                case 1:
                    btnChoice2.setText(decodedIncorrectAnswer1String);
                    btnChoice3.setText(decodedIncorrectAnswer2String);
                    btnChoice4.setText(decodedIncorrectAnswer3String);
                    break;

                case 2:
                    btnChoice1.setText(decodedIncorrectAnswer1String);
                    btnChoice3.setText(decodedIncorrectAnswer2String);
                    btnChoice4.setText(decodedIncorrectAnswer3String);
                    break;

                case 3:
                    btnChoice1.setText(decodedIncorrectAnswer1String);
                    btnChoice2.setText(decodedIncorrectAnswer2String);
                    btnChoice4.setText(decodedIncorrectAnswer3String);
                    break;

                case 4:
                    btnChoice1.setText(decodedIncorrectAnswer1String);
                    btnChoice2.setText(decodedIncorrectAnswer2String);
                    btnChoice3.setText(decodedIncorrectAnswer3String);
                    break;

            }

        }

    }


    // Stops game when the time has ended or the user answered all questions
    public void endGame() {

        updateSecondsCounter.interrupt();

        Intent toGameResumeIntent = new Intent(getApplicationContext(), GameOverviewActivity.class);
        toGameResumeIntent.putExtra("userAnswers", userAnsweredQuestions.toString());
        startActivity(toGameResumeIntent);
        finish();

    }


    // Decoder -> B64 to UTF-8
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decodeBase64data(String stringToBeDecoded) {

        byte[] decodedBytes = Base64.getDecoder().decode(stringToBeDecoded);
        return new String(decodedBytes);

    }


    // Places user answered question on an ArrayList
    public void setUserAnsweredQuestions(String question, String answer) {

        ArrayList<String> formattedQuestion = new ArrayList<>();
        formattedQuestion.add(question);
        formattedQuestion.add(answer);

        userAnsweredQuestions.add(formattedQuestion);

    }

}
