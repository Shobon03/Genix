// activity_game.xml

package com.pjiproject.genix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

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


    // Other
    int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgCloseGame = (ImageView) findViewById(R.id.imgReturnToMenu);
        imgCloseGame.setOnClickListener(new CloseGameListener());

        lblCurrentQuestion = (TextView) findViewById(R.id.lblCurrentQuestion);

        lblCurrentQuestionNumber = (TextView) findViewById(R.id.lblCurrentQuestionNumber);
        lblCurrentQuestionNumber.setText("1");

        btnChoice1 = (Button) findViewById(R.id.btnChoice1);


        // FOR TIME REMAINING CREATE THREAD -> https://stackoverflow.com/questions/14814714/update-textview-every-second
        // Get questions from MainActivity intent
        Intent mainActivityIntent = getIntent();
        String questions = mainActivityIntent.getStringExtra("questionsString");


        // Parse string data to JSON data
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonQuestionsObject = new JSONObject();

        try {

            jsonQuestionsObject = (JSONObject) jsonParser.parse(questions);
            JSONArray jsonQuestionsArray = (JSONArray) jsonQuestionsObject.get("results");

            System.out.println(jsonQuestionsArray.toString());

            JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(0);
            lblCurrentQuestion.setText(jsonQuestion.get("question").toString());

            btnChoice1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    questionIndex++;

                    if (questionIndex < jsonQuestionsArray.size()) {

                        JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(questionIndex);
                        lblCurrentQuestion.setText(jsonQuestion.get("question").toString());
                        lblCurrentQuestionNumber.setText(Integer.toString(questionIndex + 1));

                    } else {

                        Intent toGameResumeIntent = new Intent(getApplicationContext(), GameResumeActivity.class);
                        startActivity(toGameResumeIntent);
                        finish();

                    }

                }

            });

            /*Thread changeQuestions = new Thread() {

                public void run() {

                    JSONObject jsonQuestion = (JSONObject) jsonQuestionsArray.get(0);
                    lblCurrentQuestion.setText(jsonQuestion.get("question").toString());

                }

            };
            changeQuestions.start();*/

        } catch (ParseException e) {

            e.printStackTrace();

        }

    }


    // Button listeners
    // Close game
    public class CloseGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            finish();

        }

    }

    // TODO
    public class ChooseAnswerListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // TODO...

        }

    }

}
