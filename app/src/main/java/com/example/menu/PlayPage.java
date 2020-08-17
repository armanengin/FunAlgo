package com.example.menu;
/**
 *PlayPage class includes buttons goes to creative and educational mode
 *@version 02.05.2020
 *@author Huseyin Uzun
 */

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.FunAlgo.R;
import com.example.creative.CreativeMode;
import com.example.educational.LevelPage;

public class PlayPage extends AppCompatActivity {
    //properties
    private boolean isVolumeon;
    private String userName;
    private Drawable avatar;
    private ImageView user;
    private int avatarId;
    private TextView tv;
    private Button b;
    private Button educationalMode;
    private Button creativeMode;
    private Button settingsButton;
    private Button volume;
    private Drawable volumeon;
    private Drawable volumeoff;
    private int background;
    private int volumeOffID;
    private int volumeOnID;
    private ConstraintLayout playPageLayout;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        setContentView(R.layout.activity_play_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i =getIntent();
        //get shared preferences for background
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        playPageLayout =findViewById(R.id.play_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        playPageLayout.setBackgroundResource(background);

        //setting nickname
        userName = sharedPreferences.getString("nickname", "user");
        tv = findViewById(R.id.userName);
        tv.setText(userName);

        //setting avatar
        avatarId = sharedPreferences.getInt("avatar",0);
        avatar = AppCompatDrawableManager.get().getDrawable(PlayPage.this, avatarId);
        user = findViewById(R.id.user);
        user.setBackground(avatar);
        //volume
        isVolumeon = true;
        volume = findViewById(R.id.volume_button_playPage);
        volumeOnID = R.drawable.volumeon;
        volumeOffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeOnID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this,volumeOffID);
        //buttons
        b = findViewById(R.id.return_button_playPage);
        educationalMode = findViewById(R.id.educationalMode);
        settingsButton = findViewById(R.id.settings_button_playPage);
        creativeMode = findViewById(R.id.creativeMode);
        //go to home page if click return button
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this,HomePage.class);
                startActivity(i);
            }
        });

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeon) {
                    volume.setBackground(volumeoff);
                    isVolumeon = false;
                }
                else {
                    volume.setBackground(volumeon);
                    isVolumeon = true;
                }
            }
        });
        //go to level page if click educational moe button
        educationalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, LevelPage.class);
                startActivity(i);
            }
        });
        //go to settings page if click settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, SettingsPage.class);
                startActivity(i);
            }
        });
        //go to creative mode if click creative mode button
        creativeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayPage.this, CreativeMode.class);
                startActivity(i);
            }
        });
    }

}
