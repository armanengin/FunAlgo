package com.example.menu;
/**
 *ThemePage class includes themes
 *@version 28.04.2020
 *@author Deniz Semih Özal
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.FunAlgo.R;


public class ThemePage extends AppCompatActivity implements View.OnClickListener{
    //properties
    private Button wallpaper1_button, wallpaper2_button, wallpaper3_button;
    private Button voiceButton;
    private Button returnButton;
    private boolean isPressed;
    private AudioManager audioManager;
    private ConstraintLayout themesPageLayout;
    private int wallpaperID;
    private int rainbowTheme, orangeTheme, nightTheme;
    private int volumeOn, volumeOff;
    private SharedPreferences.Editor editor;
    private int background;

    /** This method gets wallpaper
     * @return wallpaperID
     **/
    public int getWallpaperID() {
        return wallpaperID;
    }

    /** This method sets wallpaper
     * @param wallpaperID
     **/
    public void setWallpaperID(int wallpaperID) {
        this.wallpaperID = wallpaperID;
    }


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Find all properties below in xml codes
        rainbowTheme = R.drawable.background3;
        orangeTheme = R.drawable.back4;
        nightTheme = R.drawable.back5;
        volumeOn = R.drawable.volumeon;
        volumeOff = R.drawable.volumeoff;
        themesPageLayout = findViewById(R.id.themes_page_layout);
        setWallpaperID(rainbowTheme);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        wallpaper1_button = findViewById(R.id.wallpaper1_button);
        wallpaper2_button = findViewById(R.id.wallpaper2_button);
        wallpaper3_button = findViewById(R.id.wallpaper3_button);
        voiceButton = findViewById(R.id.volume_button_themesPage);
        returnButton = findViewById(R.id.return_button_themesPage);

        // Sets all listeners
        wallpaper1_button.setOnClickListener(this);
        wallpaper2_button.setOnClickListener(this);
        wallpaper3_button.setOnClickListener(this);
        returnButton.setOnClickListener(this);

        // Takes background image from shared preferences method
        editor = getSharedPreferences("ShareTheme",MODE_PRIVATE).edit();
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",rainbowTheme);
        themesPageLayout.setBackgroundResource(background);

        voiceButton.setOnClickListener(new View.OnClickListener() { // Changing voice button
            @Override
            public void onClick(View v) {
                if (isPressed) {
                    voiceButton.setBackgroundResource(volumeOn);
                    audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                } else {
                    voiceButton.setBackgroundResource(volumeOff);
                    audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                }
                isPressed = !isPressed;
            }
        });
    }

    @Override
    public void onClick(View v) { // When clicking the button
        if (v.getId() == wallpaper1_button.getId()) { // Get & set wallpaper1
            themesPageLayout.setBackgroundResource(rainbowTheme);
            setWallpaperID(rainbowTheme);
            //getWindow().setBackgroundDrawableResource(R.drawable.wallpaper1);
        }
        else if (v.getId() == wallpaper2_button.getId()) {  // Get & set wallpaper2
            themesPageLayout.setBackgroundResource(orangeTheme);
            setWallpaperID(orangeTheme);

        }
        else if (v.getId() == wallpaper3_button.getId()) {  // Get & set wallpaper3
            themesPageLayout.setBackgroundResource(nightTheme);
            setWallpaperID(nightTheme);
        }
        else if (v.getId() == returnButton.getId()){ // Return Button
            editor.putInt("theme",getWallpaperID());
            editor.apply();
            Intent intent = new Intent(ThemePage.this, SettingsPage.class);
            startActivity(intent);
        }
    }
}
