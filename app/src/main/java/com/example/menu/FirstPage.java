package com.example.menu;
/**
 *FirstPage class is the the first page and it provides the user enter nickname and avatar selection
 *@version 29.04.2020
 *@author Fatih Kaplama
 */
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.FunAlgo.R;

public class FirstPage extends AppCompatActivity {
    //properties
    private boolean isAvatarSelected;
    private boolean isVolumeOn;
    private ImageView user;
    private Button avatar1;
    private Button avatar2;
    private Button avatar3;
    private Button avatar4;
    private Button avatar5;
    private Button avatar6;
    private Button avatar7;
    private Button avatar8;
    private Button avatar9;
    private Button volume;
    private Button settings;
    private Button apply;
    private Button start;
    private int avatarID;
    private int volumeoffID;
    private int volumeonID;
    private int background;
    private ConstraintLayout mainPageLayout;
    private Drawable volumeoff;
    private Drawable volumeon;
    private Drawable avatar;
    private EditText nickname;

    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //to get the saved data
        mainPageLayout = findViewById(R.id.main_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        mainPageLayout.setBackgroundResource(background);
        isVolumeOn = true;

        //defining variables
        nickname = findViewById(R.id.nickname);
        mainPageLayout = findViewById(R.id.main_layout);
        user = findViewById(R.id.user);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        avatar9 = findViewById(R.id.avatar9);
        volume = findViewById(R.id.volume_button_main);
        settings = findViewById(R.id.settings_button_main);
        apply = findViewById(R.id.apply);
        start = findViewById(R.id.start);
        start.setEnabled(false);

        //to change the volume PNG
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);

        //for user to select an avatar
        //first avatar
        avatar1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.batman;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //second avatar
        avatar2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.captainamerica;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //third avatar
        avatar3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.joker;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //fourth avatar
        avatar4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.herofemale;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //fifth avatar
        avatar5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.ironman;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //sixth avatar
        avatar6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.heromale;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //seventh avatar
        avatar7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.thor;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //eighth avatar
        avatar8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.spider;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });
        //ninth avatar
        avatar9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                avatarID = R.drawable.thanos;
                avatar = AppCompatDrawableManager.get().getDrawable(FirstPage.this, avatarID);
                user.setBackground(avatar);
                isAvatarSelected = true;
            }
        });

        //when the user click the apply button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the user enter a nickname and select an avatar, nickname and avatar selected by the user are saved
                if (!nickname.getText().toString().isEmpty() && isAvatarSelected) {
                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nickname", nickname.getText().toString());
                    editor.putInt("avatar", avatarID);
                    editor.commit();
                    //apply.setEnabled(false);
                    Toast.makeText(FirstPage.this, "User was created", Toast.LENGTH_SHORT).show();
                    start.setEnabled(true);
                }
                //Otherwise, warning message appears
                else {
                    Toast.makeText(FirstPage.this, "Please enter your nickname and select an avatar", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //when the user presses the start button, he switches to the Home Page
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirstPage.this, HomePage.class);
                startActivity(i);
            }
        });

        //the user can turn on or off the sound when pressing the sound icon
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeOn) {
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
//                    mediaPlayer.pause();
                } else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
//                    mediaPlayer.start();
                }
            }
        });

        //when the user presses the settings, he switches to the Settings Page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, SettingsPage.class);
                startActivity(intent);
            }
        });
    }
}