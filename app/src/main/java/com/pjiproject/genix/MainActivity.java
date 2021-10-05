package com.pjiproject.genix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    Thread makeRequest;


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


        // Fetching questions
        fetchQuestions = new FetchQuestions("");
        makeRequest = new Thread(fetchQuestions);
        makeRequest.start();

    }


    /* CLASSES */
    // Button listeners
    public class PlayGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Goto game activity
            Intent toGameActivityIntent = new Intent(getApplicationContext(), GameActivity.class);
            toGameActivityIntent.putExtra("questionsString", fetchQuestions.getQuestions());

            startActivity(toGameActivityIntent);

        }

    }

    public class CreateGameListener implements View.OnClickListener {

        @Override
        public void onClick (View view){

            // Goto create game activity
            Intent toCreateGameActivityIntent = new Intent(getApplicationContext(), CreateGameActivity.class);
            startActivity(toCreateGameActivityIntent);

        }

    }

    public class AboutActivityListener implements View.OnClickListener {

        @Override
        public void onClick (View view){

            // Goto about activity
            Intent toCreateGameActivityIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(toCreateGameActivityIntent);

        }

    }

}