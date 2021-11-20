package com.pjiproject.genix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    FetchQuestions fetchQuestions;
    String questions;

    int categoryId, amountId, difficultyId, questionTypeId, timeId;
    String category, amount, difficulty, type, time, urlParameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + starting activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        imgReturnToMenu = findViewById(R.id.imgReturnToMenu);
        imgReturnToMenu.setOnClickListener(view -> finish());

        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(view -> {

            // Get data from spinners and generate a URL
            spinnerCategory = findViewById(R.id.spinnerCategory);
            categoryId = (int) spinnerCategory.getSelectedItemId();

            spinnerQuestions = findViewById(R.id.spinnerQuestions);
            amountId = (int) spinnerQuestions.getSelectedItemId();

            spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
            difficultyId = (int) spinnerDifficulty.getSelectedItemId();

            spinnerType = findViewById(R.id.spinnerType);
            questionTypeId = (int) spinnerType.getSelectedItemId();

            spinnerTime = findViewById(R.id.spinnerTime);
            timeId = (int) spinnerTime.getSelectedItemId();


            // Get category -> OpenTriviaDs's categories are divided in ids, ranging from 9 to 32
            switch(categoryId) {

                case 0:
                    category = "";
                    break;

                case 1:
                    category = "9";
                    break;

                case 2:
                    category = "10";
                    break;

                case 3:
                    category = "11";
                    break;

                case 4:
                    category = "12";
                    break;

                case 5:
                    category = "13";
                    break;

                case 6:
                    category = "14";
                    break;

                case 7:
                    category = "15";
                    break;

                case 8:
                    category = "16";
                    break;

                case 9:
                    category = "17";
                    break;

                case 10:
                    category = "18";
                    break;

                case 11:
                    category = "19";
                    break;

                case 12:
                    category = "20";
                    break;

                case 13:
                    category = "21";
                    break;

                case 14:
                    category = "22";
                    break;

                case 15:
                    category = "23";
                    break;

                case 16:
                    category = "24";
                    break;

                case 17:
                    category = "25";
                    break;

                case 18:
                    category = "26";
                    break;

                case 19:
                    category = "27";
                    break;

                case 20:
                    category = "28";
                    break;

                case 21:
                    category = "29";
                    break;

                case 22:
                    category = "30";
                    break;

                case 23:
                    category = "31";
                    break;

                case 24:
                    category = "32";
                    break;
            }


            // Get amount of questions
            switch(amountId) {

                case 0:
                    amount = "10";
                    break;

                case 1:
                    amount = "5";
                    break;
                case 2:
                    amount = "15";
                    break;

            }


            // Get difficulty
            switch(difficultyId) {

                case 0:
                    difficulty = "";
                    break;

                case 1:
                    difficulty = "easy";
                    break;

                case 2:
                    difficulty = "medium";
                    break;

                case 3:
                    difficulty = "hard";
                    break;
            }


            // Get question type
            switch(questionTypeId) {

                case 0:
                    type = "";
                    break;

                case 1:
                    type = "multiple";
                    break;

                case 2:
                    type = "boolean";
                    break;

            }


            // Get game time
            switch(timeId) {

                case 0:
                    time = "60";
                    break;

                case 1:
                    time = "90";
                    break;
                case 2:
                    time = "30";
                    break;

            }


            // Fetch questions
            urlParameters = "&amount=" + amount +
                    "&category=" + category +
                    "&difficulty=" + difficulty +
                    "&type=" + type;

            fetchQuestions = new FetchQuestions(urlParameters);

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

            if (questions == null) {

                Toast.makeText(
                        getApplicationContext(),
                        "Make sure you have an internet connection!",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Intent toGameActivityIntent = new Intent(getApplicationContext(), GameActivity.class);
                toGameActivityIntent.putExtra("questionsString", questions);
                toGameActivityIntent.putExtra("time", time);
                toGameActivityIntent.putExtra("type", "1");

                startActivity(toGameActivityIntent);
                finish();

            }

        });

    }

}
