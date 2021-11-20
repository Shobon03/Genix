package com.pjiproject.genix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorHandlingActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Button
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(view -> {

            Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(toMainActivity);
            finish();

        });

    }

}
