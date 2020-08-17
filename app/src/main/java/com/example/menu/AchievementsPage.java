package com.example.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.FunAlgo.R;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;
/**
 * Achievements page informs user about the stars number for every level
 * @author Arman Engin Sucu
 * @version 13.05.2020
 */

public class AchievementsPage extends AppCompatActivity {
    //properties
    private Button volume;
    private boolean isVolumeOn;
    private Drawable volumeoff;
    private Drawable volumeon;
    private int volumeoffID;
    private int volumeonID;
    private Button back;
    private String userName;
    private int avatarID;
    private Drawable avatar;
    private ImageView avatarPg;
    private TextView tv;
    private int background;
    private ConstraintLayout AchievementsPageLayout;
    private Graph graph;
    private MediaPlayer mediaPlayer;
    //For every level creating starsCountLevel
    private int starsCountLevel1;
    private int starsCountLevel2;
    private int starsCountLevel3;
    private int starsCountLevel4;
    private int starsCountLevel5;
    private int starsCountLevel6;
    private int starsCountLevel7;
    private int starsCountLevel8;
    private int starsCountLevel9;
    //for graph
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button wipeData;
    //for avatar and nickname
    private SharedPreferences sharedPreferencesUser;
    private SharedPreferences.Editor editorUser;

    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //creating the achievements page from the layout
        setContentView(R.layout.activity_achievements_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting data from home page about avatar and user name
        sharedPreferencesUser = getSharedPreferences("data", MODE_PRIVATE);
        //get user name from sharedPref and set userName
        userName = sharedPreferencesUser.getString("nickname", "User");
        tv = findViewById(R.id.userName);
        tv.setText(userName);
        //get avatar from sharedPref and set Avatar
        avatarID = sharedPreferencesUser.getInt("avatar", 0);
        avatarPg = findViewById(R.id.user);
        avatar = AppCompatDrawableManager.get().getDrawable(AchievementsPage.this, avatarID);
        avatarPg.setBackground(avatar);
       //media player for sound
        mediaPlayer = new MediaPlayer();
        mediaPlayer.start();
        //setting background with shared pref from theme page
        AchievementsPageLayout = findViewById(R.id.achievements_page_layout);
        background = getSharedPreferences("ShareTheme", MODE_PRIVATE).getInt("theme", 0);
        AchievementsPageLayout.setBackgroundResource(background);

        volume = findViewById(R.id.achievementsPage_voice_button);
        back = findViewById(R.id.achievementsPage_back_button);
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        wipeData = findViewById(R.id.button_wipeData);

        //creating the graph with datas from the levels( according to stars numbers)
        setMyGraph();

        setTitle("Achievements");
        TextView textView = findViewById(R.id.graphAchievements_label);
        textView.setText("Your Achievements");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchievementsPage.this, HomePage.class);
                startActivity(intent);
            }
        });


        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeOn) {
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //initialising the starsCountLevels

        wipeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                setMyGraph();
            }
        });
    }

    //methods
    /**
     * graphlib library set the graph  and its x and y variables according to shared-preferences outputs from other pages.
     */
    public void setMyGraph() {
        sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
        starsCountLevel1 = sharedPreferences.getInt("starsCountLevel1", 0);
        starsCountLevel2 = sharedPreferences.getInt("starsCountLevel2", 0);
        starsCountLevel3 = sharedPreferences.getInt("starsCountLevel3", 0);
        starsCountLevel4 = sharedPreferences.getInt("starsCountLevel4", 0);
        starsCountLevel5 = sharedPreferences.getInt("starsCountLevel5", 0);
        starsCountLevel6 = sharedPreferences.getInt("starsCountLevel6", 0);
        starsCountLevel7 = sharedPreferences.getInt("starsCountLevel7", 0);
        starsCountLevel8 = sharedPreferences.getInt("starsCountLevel8", 0);
        starsCountLevel9 = sharedPreferences.getInt("starsCountLevel9", 0);
        Point[] points =
                {
                        new Point(1, starsCountLevel1), new Point(2, starsCountLevel2), new Point(3, starsCountLevel3),
                        new Point(4, starsCountLevel4), new Point(5, starsCountLevel5), new Point(6, starsCountLevel6),
                        new Point(7, starsCountLevel7), new Point(8, starsCountLevel8), new Point(9, starsCountLevel9)

                };

        Label[] xLabels =
                {
                        new Label(1, "level1"), new Label(2, "level2"), new Label(3, "level3"),
                        new Label(4, "level4"), new Label(5, "level5"), new Label(6, "level6"),
                        new Label(7, "level7"), new Label(8, "level8"), new Label(9, "level9")
                };

        graph = new Graph.Builder()
                .setWorldCoordinates(-1, 11, -1, 4)
                .setXLabels(xLabels)
                .setYTicks(new double[]{1, 2, 3,})
                .addLineGraph(points, Color.RED)
                .build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
    }

}