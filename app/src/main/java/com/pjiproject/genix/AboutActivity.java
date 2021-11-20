package com.pjiproject.genix;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    /* ATTRIBUTES */
    // Buttons
    private Button btnSource;
    private Button btnOSLicenses;


    // Image
    private ImageView imgReturnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + starts activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imgReturnToMenu = findViewById(R.id.imgReturnToMenu);
        imgReturnToMenu.setOnClickListener(view -> finish());

        btnSource = findViewById(R.id.btnBack);
        // btnSource.setOnClickListener();

        btnOSLicenses = findViewById(R.id.btnOSLicenses);
        // btnOSLicenses.setOnClickListener();

    }

}
