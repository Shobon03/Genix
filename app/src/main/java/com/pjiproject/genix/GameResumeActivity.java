// activity_game_resume.xml

package com.pjiproject.genix;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GameResumeActivity extends AppCompatActivity {

    // Image
    private ImageView returnToMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the theme + creating activity
        getApplication().setTheme(R.style.Theme_Design_Light_NoActionBar);
        setTheme(R.style.Theme_Design_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_resume);

        returnToMenu = (ImageView) findViewById(R.id.imgReturnToMenu);
        returnToMenu.setOnClickListener(new toMenuGameListener());

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
