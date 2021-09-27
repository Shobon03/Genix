// activity_main.xml

package com.pjiproject.genix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Buttons
    private Button btnPlayRandomGame;
    private Button btnChooseGame;
    private Button btnGoToRanking;

    // Others
    Trivia trivia;
    Thread makeRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + starting the activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnPlayRandomGame = (Button) findViewById(R.id.btnPlayRandomGame);
        btnPlayRandomGame.setOnClickListener(new PlayGameListener());

        // Fetching questions
        trivia = new Trivia();
        makeRequest = new Thread(trivia);
        makeRequest.start();

    }


    /* CLASSES */
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