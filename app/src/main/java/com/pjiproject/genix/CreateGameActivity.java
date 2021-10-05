package com.pjiproject.genix;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGameActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Buttons
    private Button btnGenerate;
    private Button btnReset;
    
    
    // Image
    private ImageView imgReturnToMenu;


    // Spinners
    private Spinner spinnerCategory;
    private Spinner spinnerQuestions;
    private Spinner spinnerDifficulty;
    private Spinner spinnerType;
    private Spinner spinnerTime;


    // Others
    /* FetchQuestions fetchQuestions;
    Thread makeRequest; */


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + starting activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        btnGenerate = findViewById(R.id.btnGenerate);
        // btnGenerate.setOnClickListener();

        imgReturnToMenu = findViewById(R.id.imgReturnToMenu);
        imgReturnToMenu.setOnClickListener(view -> finish());

        spinnerCategory = findViewById(R.id.spinnerCategory);
        //

        spinnerQuestions = findViewById(R.id.spinnerQuestions);
        //

        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        //

        spinnerType = findViewById(R.id.spinnerType);
        //

        spinnerTime = findViewById(R.id.spinnerTime);
        //


        // Fetch questions
        /* TODO: PLACE WHAT USER HAS SELECTED IN SPINNER IN URL PARAMETERS
        fetchQuestions = new FetchQuestions("");
        makeRequest = new Thread(fetchQuestions);
        makeRequest.start(); */

    }

}
