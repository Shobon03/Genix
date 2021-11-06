package com.pjiproject.genix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class GameOverviewActivity extends AppCompatActivity {

    // Image
    private ImageView returnToMenu;


    // ListView
    private ListView listUserAnswers;


    // TextView
    private TextView lblTime;
    private TextView lblAnswers;


    /* Others
    ArrayList<String> answeredQuestions;
    ArrayList<String> correctAnswers;
    ArrayList<String> userAnswers;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        returnToMenu = findViewById(R.id.imgReturnToMenu);
        returnToMenu.setOnClickListener(view -> finish());


        /* TODO: a proper way top get questions and show the user
        // Create list
        Intent gameActivityIntent = getIntent();
        String questions = gameActivityIntent.getStringExtra("questions");
        String correctAnswersString = gameActivityIntent.getStringExtra("correctAnswers");
        String answers = gameActivityIntent.getStringExtra("userAnswers");
        String time = gameActivityIntent.getStringExtra("userTime");

        lblTime = findViewById(R.id.lblTimeInSeconds);
        lblTime.setText(time + " s");

        answeredQuestions = new ArrayList<>(Arrays.asList(questions.split("\\s*,\\s*")));
        correctAnswers = new ArrayList<>(Arrays.asList(correctAnswersString.split("\\s*,\\s*")));
        userAnswers = new ArrayList<>(Arrays.asList(answers.split("\\s*,\\s*")));

        lblAnswers = findViewById(R.id.lblAnswers);
        lblAnswers.setText(Integer.toString(answeredQuestions.size() - correctAnswers.size()) + " / " + Integer.toString(answeredQuestions.size()));

        listUserAnswers = findViewById(R.id.listUserAnswers);


        // Create ListView Adapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter(

            getApplicationContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            answeredQuestions

        );
        listUserAnswers.setAdapter(listAdapter);*/

    }

}
