// activity_main.xml

package com.pjiproject.genix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Buttons
    private Button btnPlayRandomGame;
    private Button btnChooseGame;
    private Button btnGoToRanking;

    private Trivia trivia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);


        // Create activity and assign the buttons its values
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnPlayRandomGame = (Button) findViewById(R.id.btnPlayRandomGame);
        btnPlayRandomGame.setOnClickListener(new PlayGameListener());


        // Fetch questions
        trivia = new Trivia();
        Thread makeRequest = new Thread(trivia);
        makeRequest.start();

    }


    // Button listeners
    public class PlayGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // Goto game activity
            Intent toGameActivityIntent = new Intent(getApplicationContext(), GameActivity.class);
            toGameActivityIntent.putExtra("questionsString", trivia.getQuestions());

            startActivity(toGameActivityIntent);

        }

    }

}