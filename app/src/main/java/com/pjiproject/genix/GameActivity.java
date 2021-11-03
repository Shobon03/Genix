package com.pjiproject.genix;

import android.content.Intent;
import android.graphics.Color;
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
    private TextView lblSlashAllQuestionsNumber;
    private TextView lblTimeRemaining;


    // Colors
    int disabledButtonColor = Color.rgb(212, 212, 212);
    int yellowColor = Color.rgb(255, 206, 132);
    int falseColor = Color.rgb(255,  89, 89);
    int trueColor = Color.rgb(34,  207, 0);


    // Others
    int questionIndex = 0;
    int gameSeconds = 0;

    String questions, time;

    Thread updateSecondsCounter;

    ArrayList<String> justQuestions = new ArrayList<>();
    ArrayList<String> correctAnswers = new ArrayList<>();
    ArrayList<String> userAnswers = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgCloseGame = findViewById(R.id.imgReturnToMenu);
        imgCloseGame.setOnClickListener(view -> {

            updateSecondsCounter.interrupt();
            finish();

        });

        lblCurrentQuestion = findViewById(R.id.lblCurrentQuestion);

        lblSlashAllQuestionsNumber = findViewById(R.id.lblSlashAllQuestionsNumber);

        lblCurrentQuestionNumber = findViewById(R.id.lblCurrentQuestionNumber);
        lblCurrentQuestionNumber.setText("1");

        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);


        // Get questions
        Intent activityIntent = getIntent();
        questions = activityIntent.getStringExtra("questionsString");


        // Checks if it's a game created by player
        if (activityIntent.getStringExtra("type").equals("0")) {

            time = "60";

        } else {

            time = activityIntent.getStringExtra("time");

        }

        lblTimeRemaining = findViewById(R.id.lblTimeRemaining);
        lblTimeRemaining.setText(time);

        gameSeconds = Integer.parseInt(time);


        // Parse string data to JSON data
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonQuestionsObject;

        try {

            jsonQuestionsObject = (JSONObject) jsonParser.parse(questions);
            JSONArray jsonQuestionsArray = (JSONArray) jsonQuestionsObject.get("results");

            lblSlashAllQuestionsNumber.setText("/ " + jsonQuestionsArray.size());

            // Assigns the first question
            JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(0);
            lblCurrentQuestion.setText(decodeBase64data(jsonQuestion.get("question").toString()));

            justQuestions.add(decodeBase64data(jsonQuestion.get("question").toString()));

            JSONArray incorrectAnswers = (JSONArray) jsonQuestion.get("incorrect_answers");
            assignAnswersToRandomButton(jsonQuestion, incorrectAnswers);


            // Create answer buttons
            class ClickAnswerListener implements View.OnClickListener {

                @Override
                public void onClick(View view) {

                    questionIndex++;

                    switch (view.getId()) {

                        case R.id.btnChoice1:
                            userAnswers.add(btnChoice1.getText().toString());
                            break;

                        case R.id.btnChoice2:
                            userAnswers.add(btnChoice2.getText().toString());
                            break;

                        case R.id.btnChoice3:
                            userAnswers.add(btnChoice3.getText().toString());
                            break;

                        case R.id.btnChoice4:
                            userAnswers.add(btnChoice4.getText().toString());
                            break;

                    }

                    if (questionIndex < jsonQuestionsArray.size() && gameSeconds >= 0) {

                        JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(questionIndex);
                        JSONArray incorrectAnswers = (JSONArray) jsonQuestion.get("incorrect_answers");

                        lblCurrentQuestion.setText(decodeBase64data(jsonQuestion.get("question").toString()));
                        lblCurrentQuestionNumber.setText(Integer.toString(questionIndex + 1));

                        justQuestions.add(decodeBase64data(jsonQuestion.get("question").toString()));

                        assignAnswersToRandomButton(jsonQuestion, incorrectAnswers);

                    } else {

                        endGame(justQuestions, correctAnswers, userAnswers, lblTimeRemaining.getText().toString());

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

                                    endGame(justQuestions, correctAnswers, userAnswers, lblTimeRemaining.getText().toString());

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


    /* METHODS */
    // Choose a random button to assign the correct answer
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void assignAnswersToRandomButton(JSONObject jsonQuestion, JSONArray incorrectAnswers) {

        String decodedCorrectAnswerString, decodedIncorrectAnswer1String,
               decodedIncorrectAnswer2String, decodedIncorrectAnswer3String;
        int randomCorrectAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3;

        decodedCorrectAnswerString = decodeBase64data(jsonQuestion.get("correct_answer").toString());
        correctAnswers.add(decodedCorrectAnswerString);
        if (decodedCorrectAnswerString.equals("True") || decodedCorrectAnswerString.equals("False")) {

            btnChoice1.setText(R.string.game_true);
            btnChoice2.setText(R.string.game_false);

            btnChoice1.setBackgroundColor(trueColor);
            btnChoice2.setBackgroundColor(falseColor);

            btnChoice3.setEnabled(false);
            btnChoice4.setEnabled(false);

            btnChoice3.setText("");
            btnChoice4.setText("");

            btnChoice3.setBackgroundColor(disabledButtonColor);
            btnChoice4.setBackgroundColor(disabledButtonColor);

        } else {

            btnChoice3.setEnabled(true);
            btnChoice4.setEnabled(true);

            btnChoice1.setBackgroundColor(yellowColor);
            btnChoice2.setBackgroundColor(yellowColor);
            btnChoice3.setBackgroundColor(yellowColor);
            btnChoice4.setBackgroundColor(yellowColor);

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
    public void endGame(ArrayList<String> justQuestions, ArrayList<String> correctAnswers, ArrayList<String> userAnswers, String userTime) {

        updateSecondsCounter.interrupt();

        Intent toGameResumeIntent = new Intent(getApplicationContext(), GameOverviewActivity.class);
        toGameResumeIntent.putExtra("questions", justQuestions.toString());
        toGameResumeIntent.putExtra("correctAnswers", correctAnswers.toString());
        toGameResumeIntent.putExtra("userAnswers", userAnswers.toString());
        toGameResumeIntent.putExtra("userTime", Integer.toString(60 - Integer.parseInt(userTime)));

        questions = "";

        startActivity(toGameResumeIntent);
        finish();

    }


    // Decoder -> B64 to UTF-8
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decodeBase64data(String stringToBeDecoded) {

        byte[] decodedBytes = Base64.getDecoder().decode(stringToBeDecoded);
        return new String(decodedBytes);

    }

}
