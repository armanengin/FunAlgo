package com.example.menu;
/**
 *HomePage class includes main buttons
 *@version 29.04.2020
 *@author Alperen Alkan
 */
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.FunAlgo.R;

public class HomePage extends FirstPage {
    //properties
    private String userName;
    private Drawable avatar;
    private ImageView avatarPg;
    private int avatarId;
    private TextView tv;
    private Button b;
    private Button play;
    private Button settingsButton;
    private Button achievements;
    private Button instructions;
    private int background;
    private int avatarID;
    private ConstraintLayout homePageLayout;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set Layout
        setContentView(R.layout.activity_home_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i = getIntent();
        //get shared preferences for the common background
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        homePageLayout = findViewById(R.id.home_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        homePageLayout.setBackgroundResource(background);
        //get nick name from sharedPref and set nickname
        userName = sharedPreferences.getString("nickname", "User");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        // buttons
        b = findViewById(R.id.return_button_homePage);
        play = findViewById(R.id.play2);
        settingsButton = findViewById(R.id.settings_button_homePage);
        achievements = findViewById(R.id.achievements);
        instructions = findViewById(R.id.instructions);
        //get avatar from sharedPref and set Avatar
        avatarID = sharedPreferences.getInt("avatar", 0);
        avatarPg = findViewById(R.id.avatarH);
        avatar = AppCompatDrawableManager.get().getDrawable(HomePage.this, avatarID);
        avatarPg.setBackground(avatar);
        // go to FirstPage if click return button
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,FirstPage.class);
                startActivity(i);
            }
        });
        // go to PlayPage if click play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(HomePage.this,PlayPage.class);
                startActivity(i);
            }
        });
        // go to SettingsPage if click return settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, SettingsPage.class);
                startActivity(i);
            }
        });
        // go to Achievements if click return achievements button
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, AchievementsPage.class);
                startActivity(i);
            }
        });
        // go to Instruction  if click return Instruction button
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, InstructionsPage.class);
                startActivity(i);
            }
        });
    }
}
