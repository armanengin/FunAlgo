package com.example.educational;
/**
 *Level7Page class for level7
 *@version 10.05.2020
 *@author Alperen Alkan & Fatih Kaplama & Arman Engin Sucu
 */
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;

import java.util.ArrayList;

public class Level7Page extends Level1Page {
    //properties
    //to determine the finish point
    final private int[] targetArea = { 532 , 9 };

    //to determine the X values that the user can go to
    final private int[] nonForbiddenAreaX = { 0 , 133 , 266 , 399 , 399 , 399, 399 , 266 , 399 , 532 , 532};

    //to determine the Y values that the user can go to
    final private int[] nonForbiddenAreaY = { 493 , 493 , 493 , 493 , 372 , 251, 130 , 130 , 130 , 130 , 9};
    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Spinner spinnerKey;
    private Integer[] times = {1,2,3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView hero;
    private ImageView key;
    private ImageView prisoner;
    private Button goForward;
    private Button turnRight;
    private Button turnLeft;
    private Button getKey;
    private Button settings;
    private Button volume;
    private Button back;
    private Button info;
    private Button apply;
    private Button reset;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout.LayoutParams params;
    private int volumeoffID;
    private int volumeonID;
    private int princessID;
    private Drawable volumeoff;
    private Drawable volumeon;
    private float x;
    private float y;
    private int count = 0;
    private int starsCount;
    private int timesForward;
    private int timesLeft;
    private int timesRight;
    private int timesKey;
    private boolean isGameOver;
    private boolean isVolumeOn;
    private boolean heroHasKey;
    private int movementsCount;
    private int avatarID;
    private Drawable avatar;
    private Button show;
    private String code;
    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;
    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferencesA;
    private SharedPreferences.Editor editor;
    private int background;
    private ConstraintLayout level7Page;
    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level7_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        level7Page = findViewById(R.id.level7_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        level7Page.setBackgroundResource(background);
        //starting activity
        Intent i = getIntent();
        final SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        //setting the avatar
        avatarID = sharedPreferences.getInt("avatar", 0);
        avatar = AppCompatDrawableManager.get().getDrawable(Level7Page.this, avatarID);
        hero = findViewById(R.id.hero);
        hero.setBackground(avatar);

        movementsCount = 0;
        //Views
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        key = findViewById(R.id.key);
        prisoner = findViewById(R.id.prisoner);
        goForward = findViewById(R.id.goForward);
        turnLeft = findViewById(R.id.turnLeft);
        turnRight = findViewById(R.id.turnRight);
        settings = findViewById(R.id.settings);
        volume = findViewById(R.id.volume);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        layout1 = findViewById(R.id.leftLayout1);
        layout2 = findViewById(R.id.leftLayout2);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerKey = findViewById(R.id.spinnerKey);
        movements = findViewById(R.id.movements);
        getKey = findViewById(R.id.getKey);
        show = findViewById(R.id.showCode_button);
        code = "";

        //volume
        isVolumeOn = true;
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        //spinner
        timesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForward.setAdapter(timesAdapter);
        spinnerRight.setAdapter(timesAdapter);
        spinnerLeft.setAdapter(timesAdapter);
        spinnerKey.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        spinnerKey = findViewById(R.id.spinnerKey);

        //to add the buttons to the linear layout
        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        princessID = R.drawable.princess;
        heroHasKey = false;
        isGameOver = false;

        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans",MODE_PRIVATE);
        et = sp.edit();

        isFinished(Level7Page.this, "7", 11, 14);

        //when the user presses the back button, he switches to Level Page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this,LevelPage.class);
                startActivity(i);
            }
        });

        //when the user presses the settings, he switches to Settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level7Page.this, SettingsPage.class);
                startActivity(i);
            }
        });

        //the user can turn on or off the sound when pressing the sound icon
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVolumeOn){
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                }
                else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();;
                }
            }
        });

        //when the user presses the info, he can get information about level
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Hero wants to free princess. Help him with your algorithm!\n" +
                        "Hint: Careful about hero's direction. Make sure it is forward before press to go forward button.", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        //to show the current codes
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                if (code == "") {
                    toast = Toast.makeText(getApplicationContext(), "No code here yet.", Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG);
                }
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        //to reset the game
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                code = "";
            }
        });

        //to apply the activities selected by the user
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply.setEnabled(false);
                //to create the object of the ApplyMove class
                ApplyMove applyMove = new ApplyMove(hero,list,133,121,targetArea,nonForbiddenAreaX,nonForbiddenAreaY,0,0,0,0,null,null,key,266,130, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start(); //starting thread
            }
        });

        //when the user click the GO FORWARD
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = goForwardButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerForward);

            }
        });

        //when the user click the TURN LEFT
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnLeftButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerLeft);
            }
        });

        //when the user click the TURN RIGHT
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnRightButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerRight);
            }
        });

        //when the user click the GET KEY
        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = getNectarButton(timesKey, layout1, layout2, list, movementsCount, movements, spinnerKey,"KEY");
            }
        });
    }

    //methods
    /** This method saves the data
     * @param, codeMessage
     * @return
     **/
    @Override
    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level7Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    /** This method sets the code message
     * @param
     * @return
     **/
    @Override
    public void setCodeMessage() {
        SharedPreferences sharedPref = Level7Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }
}
