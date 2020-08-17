package com.example.menu;
/**
 *Instructions Page class includes instructions for FunAlgo
 *@version 18.05.2020
 *@author Huseyin Uzun & Fatih Kaplama
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatDrawableManager;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.example.FunAlgo.R;

public class InstructionsPage extends AppCompatActivity {
    //properties
    private boolean isVolumeon;
    private Button b;
    private Button settingsButton;
    private int background;
    private Drawable volumeon;
    private Drawable volumeoff;
    private int volumeOffID;
    private int volumeOnID;
    private Button volume;
    private ConstraintLayout instructionsPageLayout;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_instructions_page);
        instructionsPageLayout = findViewById(R.id.instructions_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        instructionsPageLayout.setBackgroundResource(background);

        Intent i = getIntent();
        //Get shared preferences for background
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        //buttons
        b= findViewById(R.id.return_button_instructionsPage);
        settingsButton = findViewById(R.id.settings_button_instructionsPage);
        //volume
        volume = findViewById(R.id.volume_button_instructionsPage);
        volumeOnID = R.drawable.volumeon;
        volumeOffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeOnID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeOffID);
        //go to home page if click return button
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructionsPage.this, HomePage.class);
                startActivity(i);
            }
        });
        //go to settings page if click settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructionsPage.this, SettingsPage.class);
                startActivity(i);
            }
        });
        //volume
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVolumeon) {
                    volume.setBackground(volumeoff);
                    isVolumeon = false;
                }
                else {
                    volume.setBackground(volumeon);
                isVolumeon = true;
                }
            }
        });
    }
}
