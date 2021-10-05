package com.pjiproject.genix;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Button
    private Button btnGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + starting activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(view -> finish());

    }

}
