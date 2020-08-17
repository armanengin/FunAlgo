package com.example.menu;
/**
 *SettingsPage class includes theme and music buttons
 *@version 28.04.2020
 *@author Deniz Semih Özal
 */
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.FunAlgo.R;

public class SettingsPage extends ThemePage implements View.OnClickListener{
    //properties
    private Button returnButton;
    private Button themesButton;
    private Button musicsButton;
    private ConstraintLayout settingsPage;
    private int background;
    private SharedPreferences sharedPreferences;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        // Find all properties below in xml codes
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        returnButton = findViewById(R.id.return_button_settingsPage);
        themesButton = findViewById(R.id.themes_button_settingsPage);
        musicsButton = findViewById(R.id.musics_button_SettingPage);

        // Takes background image from shared preferences method
        settingsPage = findViewById(R.id.settings_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        settingsPage.setBackgroundResource(background);

        // Sets all the listeners
        returnButton.setOnClickListener(this);
        themesButton.setOnClickListener(this);
        musicsButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) { // When clicking the button
        if ( v.getId() == themesButton.getId()) {
            Intent intent = new Intent(SettingsPage.this, ThemePage.class); // Go to ThemePage
            startActivity(intent);
        }
        else if ( v.getId() == musicsButton.getId()){
            Intent intent = new Intent(SettingsPage.this, MusicsPage.class); // Go to MusicsPage
            startActivity(intent);
        }
        else if ( v.getId() == returnButton.getId()){
                Intent intent = new Intent(SettingsPage.this, HomePage.class); // Return Button
                startActivity(intent);
        }
    }
}