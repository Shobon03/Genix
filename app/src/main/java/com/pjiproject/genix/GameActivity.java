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
    private ImageView closeGame;


    // Buttons
    private Button btnChoice1;
    private Button btnChoice2;
    private Button btnChoice3;
    private Button btnChoice4;


    // Texts
    private TextView lblCurrentQuestion;
    private TextView lblCurrentQuestionNumber;
    private TextView lblTimeRemaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        closeGame = (ImageView) findViewById(R.id.imgCloseGame);
        closeGame.setOnClickListener(new CloseGameListener());

        lblCurrentQuestion = (TextView) findViewById(R.id.lblCurrentQuestion);
        lblCurrentQuestionNumber = (TextView) findViewById(R.id.lblCurrentQuestionNumber);

        btnChoice1 = (Button) findViewById(R.id.btnChoice1);
        // btnChoice1.setOnClickListener(new ChooseAnswerListener());


        // FOR TIME REMAINING CREATE THREAD -> https://stackoverflow.com/questions/14814714/update-textview-every-second
        // Get questions from MainActivity intent
        Intent mainActivityIntent = getIntent();
        String questions = mainActivityIntent.getStringExtra("questionsString");


        // Parse string data to JSON data
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonQuestionsObject = new JSONObject();

        try {

            jsonQuestionsObject = (JSONObject) jsonParser.parse(questions);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        JSONArray jsonQuestionsArray = (JSONArray) jsonQuestionsObject.get("results");

        // Write on game
        Thread thread = new Thread() {

            @Override
            public void run() {

                for (int i = 0; i < jsonQuestionsArray.size(); i++) {

                    JSONObject jsonQuestion;
                    while (true) {

                        jsonQuestion = (JSONObject) jsonQuestionsArray.get(i);
                        // System.out.println(jsonQuestion.get("question").toString());
                        lblCurrentQuestion.setText(jsonQuestion.get("question").toString());

                        // String questionNumber = Integer.toString(i + 1);
                        // lblCurrentQuestionNumber.setText(questionNumber);

                        if (btnChoice1.isPressed()) {

                            break;

                        }

                    }

                }

            }

        };
        thread.start();

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
