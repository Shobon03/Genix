package com.pjiproject.genix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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


    // Others
    ArrayList<String> answeredQuestions;
    ArrayList<String> correctAnswers;
    ArrayList<String> userAnswers;

    private QuestionsAdapter qa;
    private ArrayList<Question> questions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        returnToMenu = findViewById(R.id.imgReturnToMenu);
        returnToMenu.setOnClickListener(view -> finish());


        // Get data from Game Activity
        Intent gameActivityIntent = getIntent();
        answeredQuestions = gameActivityIntent.getStringArrayListExtra("questions");
        correctAnswers = gameActivityIntent.getStringArrayListExtra("correctAnswers");
        userAnswers = gameActivityIntent.getStringArrayListExtra("userAnswers");
        String time = gameActivityIntent.getStringExtra("userTime");

        int correctAnsweredQuestions = 0;
        for (int i = 0; i < correctAnswers.size(); i++) {

            // Create a question object and append it on the ArrayList
            Question q = new Question(answeredQuestions.get(i), "Correct answer: " + correctAnswers.get(i), "You answered: " + userAnswers.get(i));
            questions.add(q);

            // Check which questions the user has answered correctly
            if (correctAnswers.get(i).equals(userAnswers.get(i))) {

                correctAnsweredQuestions++;

            }

        }

        // Set labels
        lblTime = findViewById(R.id.lblTimeInSeconds);
        lblTime.setText(time + "s");

        lblAnswers = findViewById(R.id.lblAnswers);
        lblAnswers.setText(
                Integer.toString(correctAnsweredQuestions) +
                " / " +
                Integer.toString(answeredQuestions.size())
        );

        listUserAnswers = findViewById(R.id.listUserAnswers);

        qa = new QuestionsAdapter(this, questions);
        listUserAnswers.setAdapter(qa);

        /*
        // Create ListView Adapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter(

            getApplicationContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            answeredQuestions,

        );
        listUserAnswers.setAdapter(listAdapter);*/

    }

}
