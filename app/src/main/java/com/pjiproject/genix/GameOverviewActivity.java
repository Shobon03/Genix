// activity_game_resume.xml

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
    //private ListView listUserAnswers;


    // TextView
    private TextView lblGameOverview;


    // Others
    //ArrayList<String[]> fetchedUserAnsweredQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        returnToMenu = findViewById(R.id.imgReturnToMenu);
        returnToMenu.setOnClickListener(new toMenuGameListener());

        lblGameOverview = findViewById(R.id.lblGameOverview);
        lblGameOverview.setText("This is still a WIP :)");

        /*
        listUserAnswers = findViewById(R.id.listUserAnswers);

        lblTimeInSeconds = findViewById(R.id.lblTimeInSeconds);


        // Get user's answers and parse them back to ArrayList
        Intent gameActivityIntent = getIntent();
        String[] lastGameAsArray = gameActivityIntent.getStringExtra("userAnswers").split(",");
        fetchedUserAnsweredQuestions = new ArrayList(Arrays.asList(lastGameAsArray));

        // Create ListView Adapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter(

                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                fetchedUserAnsweredQuestions

        );
        listUserAnswers.setAdapter(listAdapter);
         */
    }


    // Button listeners
    // Go to menu
    public class toMenuGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            finish();

        }

    }

}
