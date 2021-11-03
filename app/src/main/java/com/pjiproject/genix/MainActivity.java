package com.pjiproject.genix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Buttons
    private Button btnPlayRandomGame;
    private Button btnChooseGame;
    private Button btnGoToRanking;


    // Image
    private ImageView imgAbout;


    // Others
    FetchQuestions fetchQuestions;
    String questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);


        // Checks if it's the first time that the uses launches app
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {

            startActivity(new Intent(getApplicationContext(), TutorialActivity.class));

        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();


        // Starts activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnPlayRandomGame = findViewById(R.id.btnPlayRandomGame);
        btnPlayRandomGame.setOnClickListener(new PlayGameListener());

        btnChooseGame = findViewById(R.id.btnChooseGame);
        btnChooseGame.setOnClickListener(new CreateGameListener());

        /* btnGoToRanking = findViewById(R.id.btnGoToRanking);
        btnGoToRanking.setOnClickListener(new PlayGameListener()); */

        imgAbout = findViewById(R.id.imgAbout);
        imgAbout.setOnClickListener(new AboutActivityListener());


    }


    /* CLASSES */
    // Button listeners
    public class PlayGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Fetching questions
            fetchQuestions = new FetchQuestions("&amount=10");

            ExecutorService service;
            Future<String> task;

            service = Executors.newFixedThreadPool(1);
            task = service.submit(fetchQuestions);

            try {

                questions = task.get();
                System.out.println(questions);

            } catch(InterruptedException | ExecutionException e) {

                e.printStackTrace();

            }

            // Go to game activity
            Intent toGameActivityIntent = new Intent(getApplicationContext(), GameActivity.class);
            toGameActivityIntent.putExtra("questionsString", questions);
            toGameActivityIntent.putExtra("type", "0");

            startActivity(toGameActivityIntent);

        }

    }

    public class CreateGameListener implements View.OnClickListener {

        @Override
        public void onClick (View view) {

            // Go to create game activity
            Intent toCreateGameActivityIntent = new Intent(getApplicationContext(), CreateGameActivity.class);
            startActivity(toCreateGameActivityIntent);

        }

    }

    public class AboutActivityListener implements View.OnClickListener {

        @Override
        public void onClick (View view) {

            // Go to about activity
            Intent toCreateGameActivityIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(toCreateGameActivityIntent);

        }

    }

}