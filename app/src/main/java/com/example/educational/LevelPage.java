package com.example.educational;
/**
 *LevelPage class includes all level buttons
 *@version 06.05.2020
 *@author Alperen Alkan & Fatih Kaplama & Remzi Tepe
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.menu.PlayPage;
import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;

public class LevelPage extends AppCompatActivity {
    //properties
    private boolean isLevel1Finished;
    private boolean isLevel2Finished;
    private boolean isLevel3Finished;
    private boolean isLevel4Finished;
    private boolean isLevel5Finished;
    private boolean isLevel6Finished;
    private boolean isLevel7Finished;
    private boolean isLevel8Finished;
    private boolean isVolumeOn;
    private Drawable volumeoff;
    private Drawable volumeon;
    private int volumeoffID;
    private int volumeonID;
    private Button volumeB;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;
    private Button button21;
    private Button button22;
    private int background;
    private ConstraintLayout levelPage;
    // get settings return and volume
    private Button settingsB;
    private Button returnB;
    //sharedPreferences
    private SharedPreferences sp;
    private SharedPreferences.Editor et;

    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_page);
        levelPage = findViewById(R.id.level_page_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        levelPage.setBackgroundResource(background);
        //get buttons
         button13 = (Button) findViewById(R.id.button13);
         button14 = (Button) findViewById(R.id.button14);
         button15 = (Button) findViewById(R.id.button15);
         button17 = (Button) findViewById(R.id.button17);
         button18 = (Button) findViewById(R.id.button18);
         button19 = (Button) findViewById(R.id.button19);
         button20 = (Button) findViewById(R.id.button20);
         button21 = (Button) findViewById(R.id.button21);
         button22 = (Button) findViewById(R.id.button22);
        // get settings return and volume
        settingsB = (Button) findViewById(R.id.settings_button_LevelPage);
        returnB = (Button) findViewById(R.id.return_button_LevelPage);
        volumeB = (Button) findViewById(R.id.volume_button_LevelPage);
        //getting information from Level 1 Page
        isLevel1Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished1",false);
        //getting info from Level 2 Page
        isLevel2Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished2",false);
        //getting info from Level 3 Page
        isLevel3Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished3",false);
        //getting info from Level 4 Page
        isLevel4Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished4",false);
        //getting info from Level 5 Page
        isLevel5Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished5",false);
        //getting info from Level 6 Page
        isLevel6Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished6",false);
        //getting info from Level 7 Page
        isLevel7Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished7", false);
        //getting info from Level 8 Page
        isLevel8Finished = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE).getBoolean("finished8", false);
        //volume
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        isVolumeOn = true;
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        // set buttons disabled
        button13.setEnabled(true);
        button14.setEnabled(false);
        button15.setEnabled(false);
        button17.setEnabled(false);
        button18.setEnabled(false);
        button19.setEnabled(false);
        button20.setEnabled(false);
        button21.setEnabled(false);
        button22.setEnabled(false);
        // set Enabled level buttons accompished
        if (isLevel1Finished){
            button14.setEnabled(true);
        }
        if (isLevel2Finished){
            button15.setEnabled(true);
        }
        if (isLevel3Finished){
            button17.setEnabled(true);
        }
        if (isLevel4Finished){
            button18.setEnabled(true);
        }
        if (isLevel5Finished){
            button19.setEnabled(true);
        }
        if (isLevel6Finished){
            button20.setEnabled(true);
        }

        if (isLevel7Finished){
            button21.setEnabled(true);
        }

        if(isLevel8Finished){
            button22.setEnabled(true);
        }
        //add clicklistener to buttons to go to correct page
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level1Page.class);
                startActivity(i);
                //button14.setEnabled(true);

            }
        });
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(LevelPage.this, Level2Page.class);
                 startActivity(i);
            }
        });
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level3Page.class);
                startActivity(i);
            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level4Page.class);
                startActivity(i);
            }
        });
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level5Page.class);
                startActivity(i);
            }
        });
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level6Page.class);
                startActivity(i);

            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LevelPage.this, Level7Page.class);
                startActivity(i);
            }
        });
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(LevelPage.this, Level8Page.class);
                startActivity(i);
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(LevelPage.this, Level9Page.class);
                 startActivity(i);
            }
        });

        //return settings and volume buttons
        settingsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelPage.this, SettingsPage.class);
//                intent.putExtra("toSettingsPage", "MainActivity");
                startActivity(intent);
            }
        });
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( LevelPage.this, PlayPage.class);
                startActivity(i);
            }
        });
        volumeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isVolumeOn) {
                    volumeB.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volumeB.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                }
            }
        });
    }
}
