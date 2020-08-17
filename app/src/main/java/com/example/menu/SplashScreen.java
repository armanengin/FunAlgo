package com.example.menu;
/**
 *SplashScreen is the first transition page when opening the game
 *@version 14.05.2020
 *@author Fatih Kaplama
 */
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.FunAlgo.R;

public class SplashScreen extends AppCompatActivity {
    //properties
    private ProgressBar progressBar;
    private int progress;
    private Handler handler;
    private ImageView imageView;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Find all properties below in xml codes
        handler = new Handler();
        progress = 0;
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        progressBar.setProgress(0);

        // Animation loading
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageView.startAnimation(animation);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < 100){
                    progress +=1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                        }
                    });
                    try {
                        Thread.sleep(35);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                if (progress == 100) {
                    Intent intent = new Intent(SplashScreen.this, FirstPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();
    }
}
